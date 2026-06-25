package com.niraj.notificationdatacollector.utils

import android.app.Notification
import android.os.Build

object NotificationUtils {

    fun getTitle(notification: Notification): String {
        return notification.extras
            ?.getCharSequence(Notification.EXTRA_TITLE)
            ?.toString()
            ?: ""
    }

    fun getText(notification: Notification): String {
        return notification.extras
            ?.getCharSequence(Notification.EXTRA_TEXT)
            ?.toString()
            ?: ""
    }

    fun getBigText(notification: Notification): String {
        return notification.extras
            ?.getCharSequence(Notification.EXTRA_BIG_TEXT)
            ?.toString()
            ?: ""
    }

    fun getSubText(notification: Notification): String {
        return notification.extras
            ?.getCharSequence(Notification.EXTRA_SUB_TEXT)
            ?.toString()
            ?: ""
    }

    fun getConversationTitle(notification: Notification): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notification.extras
                ?.getCharSequence(Notification.EXTRA_CONVERSATION_TITLE)
                ?.toString()
                ?: ""
        } else {
            ""
        }
    }

    fun getCategory(notification: Notification): String {
        return notification.category ?: "UNKNOWN"
    }

    fun getChannelId(notification: Notification): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.channelId ?: ""
        } else {
            ""
        }
    }

    fun sanitize(value: String?): String {

        return value
            ?.replace("\n", " ")
            ?.replace("\r", " ")
            ?.replace(",", " ")
            ?.trim()
            ?: ""
    }

    fun normalizePackage(packageName: String): String {

        return packageName
            .lowercase()
            .trim()
    }

    fun isMessagingNotification(category: String): Boolean {

        return category == Notification.CATEGORY_MESSAGE
    }

    fun isCallNotification(category: String): Boolean {

        return category == Notification.CATEGORY_CALL
    }

    fun isAlarmNotification(category: String): Boolean {

        return category == Notification.CATEGORY_ALARM
    }

    fun isEmailNotification(category: String): Boolean {

        return category == Notification.CATEGORY_EMAIL
    }

    fun isEventNotification(category: String): Boolean {

        return category == Notification.CATEGORY_EVENT
    }

    fun isReminderNotification(category: String): Boolean {

        return category == Notification.CATEGORY_REMINDER
    }
}