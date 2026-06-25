package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.service.notification.StatusBarNotification
import com.niraj.notificationdatacollector.model.NotificationContext

object NotificationContextFactory {

    fun create(
        context: Context,
        sbn: StatusBarNotification
    ): NotificationContext {

        val packageName = NotificationParser.getPackageName(sbn)

        return NotificationContext(

            // =========================
            // Time
            // =========================

            timestampMillis = TimeCollector.getTimestamp(),

            formattedTime = TimeCollector.getFormattedTime(),

            date = TimeCollector.getDate(),

            dayOfWeek = TimeCollector.getDayOfWeek(),

            month = TimeCollector.getMonth(),

            year = TimeCollector.getYear(),

            hour = TimeCollector.getHour(),

            minute = TimeCollector.getMinute(),

            second = TimeCollector.getSecond(),

            // =========================
            // App
            // =========================

            appName = AppInfoManager.getAppName(
                context,
                packageName
            ),

            packageName = packageName,

            versionName = AppInfoManager.getVersionName(
                context,
                packageName
            ),

            versionCode = AppInfoManager.getVersionCode(
                context,
                packageName
            ),

            isSystemApp = AppInfoManager.isSystemApp(
                context,
                packageName
            ),

            // =========================
            // Notification
            // =========================

            title = NotificationParser.getTitle(sbn),

            text = NotificationParser.getText(sbn),

            bigText = NotificationParser.getBigText(sbn),

            subText = NotificationParser.getSubText(sbn),

            conversationTitle =
                NotificationParser.getConversationTitle(sbn),

            category =
                NotificationParser.getCategory(sbn),

            channelId =
                NotificationParser.getChannelId(sbn),

            priority =
                NotificationParser.getPriority(sbn),

            visibility =
                NotificationParser.getVisibility(sbn),

            isOngoing =
                NotificationParser.isOngoing(sbn),

            isClearable =
                NotificationParser.isClearable(sbn),

            notificationNumber =
                NotificationParser.getNotificationNumber(sbn),

            postTime =
                NotificationParser.getPostTime(sbn),

            // =========================
            // Device
            // =========================

            screenOn =
                DeviceCollector.isScreenOn(context),

            screenOrientation =
                DeviceCollector.getScreenOrientation(context),

            batteryLevel =
                BatteryCollector.getBatteryLevel(context),

            charging =
                BatteryCollector.isCharging(context),

            batterySaver =
                BatteryCollector.isBatterySaverEnabled(context),

            ringerMode =
                DeviceCollector.getRingerMode(context),

            mediaVolume =
                DeviceCollector.getMediaVolume(context),

            notificationVolume =
                DeviceCollector.getNotificationVolume(context),

            alarmVolume =
                DeviceCollector.getAlarmVolume(context),

            // =========================
            // Network
            // =========================

            internetAvailable =
                NetworkCollector.isInternetAvailable(context),

            wifiConnected =
                NetworkCollector.isWifiConnected(context),

            mobileDataConnected =
                NetworkCollector.isMobileDataConnected(context),

            connectionType =
                NetworkCollector.getConnectionType(context)

        )
    }
}