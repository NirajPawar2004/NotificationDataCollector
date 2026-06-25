package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.StatusBarNotification
import com.niraj.notificationdatacollector.model.NotificationEntity
import com.niraj.notificationdatacollector.utils.DeviceInfoUtil
import com.niraj.notificationdatacollector.utils.NotificationUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NotificationCollector {

    fun collect(
        context: Context,
        sbn: StatusBarNotification
    ): NotificationEntity {

        val notification = sbn.notification
        val device = DeviceInfoUtil.collect(context)
        val pm = context.packageManager

        val packageName = sbn.packageName

        var appName = packageName
        var versionName = ""
        var versionCode = 0L
        var isSystemApp = false

        try {

            val appInfo = pm.getApplicationInfo(packageName, 0)

            appName = pm.getApplicationLabel(appInfo).toString()

            val packageInfo = pm.getPackageInfo(packageName, 0)

            versionName = packageInfo.versionName ?: ""

            versionCode =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    packageInfo.longVersionCode
                else
                    packageInfo.versionCode.toLong()

            isSystemApp =
                (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0

        } catch (_: PackageManager.NameNotFoundException) {
        }

        val now = Date()

        val timestamp = System.currentTimeMillis()

        return NotificationEntity(

            timestampMillis = timestamp,

            formattedTime = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(now),

            date = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(now),

            dayOfWeek = SimpleDateFormat(
                "EEEE",
                Locale.getDefault()
            ).format(now),

            month = SimpleDateFormat(
                "MMMM",
                Locale.getDefault()
            ).format(now),

            year = SimpleDateFormat("yyyy", Locale.getDefault()).format(now).toInt(),
            hour = SimpleDateFormat("HH", Locale.getDefault()).format(now).toInt(),
            minute = SimpleDateFormat("mm", Locale.getDefault()).format(now).toInt(),
            second = SimpleDateFormat("ss", Locale.getDefault()).format(now).toInt(),

            appName = appName,
            packageName = packageName,
            versionName = versionName,
            versionCode = versionCode,
            isSystemApp = isSystemApp,

            title = NotificationUtils.sanitize(
                NotificationUtils.getTitle(notification)
            ),

            text = NotificationUtils.sanitize(
                NotificationUtils.getText(notification)
            ),

            bigText = NotificationUtils.sanitize(
                NotificationUtils.getBigText(notification)
            ),

            subText = NotificationUtils.sanitize(
                NotificationUtils.getSubText(notification)
            ),

            conversationTitle = NotificationUtils.sanitize(
                NotificationUtils.getConversationTitle(notification)
            ),

            category = NotificationUtils.getCategory(notification),

            channelId = NotificationUtils.getChannelId(notification),

            priority = notification.priority,

            visibility = notification.visibility,

            isOngoing = sbn.isOngoing,

            isClearable = sbn.isClearable,

            notificationNumber = notification.number,

            postTime = sbn.postTime,

            screenOn = device.screenOn,
            screenOrientation = device.screenOrientation,

            batteryLevel = device.batteryLevel,
            charging = device.charging,
            batterySaver = device.batterySaver,

            ringerMode = device.ringerMode,

            mediaVolume = device.mediaVolume,
            notificationVolume = device.notificationVolume,
            alarmVolume = device.alarmVolume,

            internetAvailable = device.internetAvailable,
            wifiConnected = device.wifiConnected,
            mobileDataConnected = device.mobileDataConnected,
            connectionType = device.connectionType,

            priorityLabel = "",
            userAction = "",
            responseTime = 0L,
            wasOpened = false,
            wasDismissed = false,
            notes = ""
        )
    }
}