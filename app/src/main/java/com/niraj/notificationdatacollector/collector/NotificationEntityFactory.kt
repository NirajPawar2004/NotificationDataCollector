package com.niraj.notificationdatacollector.collector

import com.niraj.notificationdatacollector.model.NotificationContext
import com.niraj.notificationdatacollector.model.NotificationEntity

object NotificationEntityFactory {

    fun create(
        context: NotificationContext
    ): NotificationEntity {

        return NotificationEntity(

            // =========================
            // Time Information
            // =========================

            timestampMillis = context.timestampMillis,

            formattedTime = context.formattedTime,

            date = context.date,

            dayOfWeek = context.dayOfWeek,

            month = context.month,

            year = context.year,

            hour = context.hour,

            minute = context.minute,

            second = context.second,

            // =========================
            // Application Information
            // =========================

            appName = context.appName,

            packageName = context.packageName,

            versionName = context.versionName,

            versionCode = context.versionCode,

            isSystemApp = context.isSystemApp,

            // =========================
            // Notification Information
            // =========================

            title = context.title,

            text = context.text,

            bigText = context.bigText,

            subText = context.subText,

            conversationTitle = context.conversationTitle,

            category = context.category,

            channelId = context.channelId,

            priority = context.priority,

            visibility = context.visibility,

            isOngoing = context.isOngoing,

            isClearable = context.isClearable,

            notificationNumber = context.notificationNumber,

            postTime = context.postTime,

            // =========================
            // Device Information
            // =========================

            screenOn = context.screenOn,

            screenOrientation = context.screenOrientation,

            batteryLevel = context.batteryLevel,

            charging = context.charging,

            batterySaver = context.batterySaver,

            ringerMode = context.ringerMode,

            mediaVolume = context.mediaVolume,

            notificationVolume = context.notificationVolume,

            alarmVolume = context.alarmVolume,

            // =========================
            // Network Information
            // =========================

            internetAvailable = context.internetAvailable,

            wifiConnected = context.wifiConnected,

            mobileDataConnected = context.mobileDataConnected,

            connectionType = context.connectionType,

            // =========================
            // AI Fields
            // =========================

            priorityLabel = "",

            userAction = "",

            responseTime = 0L,

            wasOpened = false,

            wasDismissed = false,

            notes = ""

        )
    }
}