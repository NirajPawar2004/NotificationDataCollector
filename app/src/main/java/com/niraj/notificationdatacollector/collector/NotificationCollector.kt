package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.service.notification.StatusBarNotification
import com.niraj.notificationdatacollector.ai.NotificationFrequencyEngine
import com.niraj.notificationdatacollector.data.ContactProfileRepository
import com.niraj.notificationdatacollector.model.NotificationEntity
import com.niraj.notificationdatacollector.utils.AppInfoUtil
import com.niraj.notificationdatacollector.utils.ContactMatcher
import com.niraj.notificationdatacollector.utils.DeviceInfoUtil
import com.niraj.notificationdatacollector.utils.ForegroundAppUtil
import com.niraj.notificationdatacollector.utils.NotificationUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NotificationCollector {

    suspend fun collect(
        context: Context,
        sbn: StatusBarNotification,
        contactRepository: ContactProfileRepository
    ): NotificationEntity {

        val notification = sbn.notification

        // ==========================================
        // Application Information
        // ==========================================

        val appInfo = AppInfoUtil.collect(
            context,
            sbn.packageName
        )

        // ==========================================
        // Device Information
        // ==========================================

        val deviceInfo = DeviceInfoUtil.collect(
            context
        )

        // ==========================================
        // Notification Information
        // ==========================================

        val title = NotificationUtils.sanitize(
            NotificationUtils.getTitle(notification)
        )

        val text = NotificationUtils.sanitize(
            NotificationUtils.getText(notification)
        )

        val senderName = NotificationUtils.sanitize(
            NotificationUtils.getSenderName(notification)
        )

        val category = NotificationUtils.getCategory(
            notification
        )

        // ==========================================
        // Contact Matching
        // ==========================================

        val contact = ContactMatcher(
            contactRepository
        ).match(
            senderName = senderName
        )

        // ==========================================
        // Notification Frequency
        // ==========================================

        NotificationFrequencyEngine.increment(
            appInfo.packageName
        )

        val frequency =
            NotificationFrequencyEngine.getFrequency(
                appInfo.packageName
            )

        // ==========================================
        // Time Information
        // ==========================================

        val now = Date()

        val timestamp =
            System.currentTimeMillis()

        val dayOfWeek =
            SimpleDateFormat(
                "EEEE",
                Locale.getDefault()
            ).format(now)

        val hour =
            SimpleDateFormat(
                "HH",
                Locale.getDefault()
            ).format(now).toInt()

        // ==========================================
        // Foreground Application
        // ==========================================

        val foregroundApp =
            ForegroundAppUtil.getForegroundApp(
                context
            )

        // ==========================================
        // Create Dataset Row
        // ==========================================

        return NotificationEntity(

            notificationKey = sbn.key,

            userId = "USER_001",

            appName = appInfo.appName,

            packageName = appInfo.packageName,

            notificationCategory = category,

            notificationTitle = title,

            notificationText = text,

            senderName = senderName,

            senderId = senderName,

            senderType = contact.senderType,

            favoriteContact = contact.favoriteContact,

            notificationFrequency = frequency,

            timestamp = timestamp,

            dayOfWeek = dayOfWeek,

            hourOfDay = hour,

            screenOn = deviceInfo.screenOn,

            phoneLocked = deviceInfo.phoneLocked,

            batteryLevel = deviceInfo.batteryLevel,

            charging = deviceInfo.charging,

            internetStatus = deviceInfo.internetStatus,

            doNotDisturb = deviceInfo.doNotDisturb,

            foregroundApp = foregroundApp,

            userActivity = "UNKNOWN",

            opened = false,

            dismissed = false,

            timeToOpen = -1L,

            responseTime = -1L,

            priorityLabel = "UNKNOWN",

            priorityClass = -1,

            predictionConfidence = 0f
        )
    }
}