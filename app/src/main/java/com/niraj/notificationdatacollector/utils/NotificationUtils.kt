package com.niraj.notificationdatacollector.utils

import android.app.Notification
import android.os.Build

object NotificationUtils {

    fun getTitle(
        notification: Notification
    ): String {

        return notification.extras
            ?.getCharSequence(Notification.EXTRA_TITLE)
            ?.toString()
            ?.trim()
            ?: ""
    }

    fun getText(
        notification: Notification
    ): String {

        return notification.extras
            ?.getCharSequence(Notification.EXTRA_TEXT)
            ?.toString()
            ?.trim()
            ?: ""
    }

    fun getCategory(
        notification: Notification
    ): String {

        return notification.category ?: "UNKNOWN"
    }

    fun getSenderName(
        notification: Notification
    ): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            val conversation =
                notification.extras
                    ?.getCharSequence(
                        Notification.EXTRA_CONVERSATION_TITLE
                    )
                    ?.toString()

            if (!conversation.isNullOrBlank()) {
                return conversation.trim()
            }
        }

        return getTitle(notification)
    }

    fun sanitize(
        value: String?
    ): String {

        return value
            ?.replace("\n", " ")
            ?.replace("\r", " ")
            ?.replace(",", " ")
            ?.replace("\"", "")
            ?.trim()
            ?: ""
    }

    fun normalizePackage(
        packageName: String
    ): String {

        return packageName
            .trim()
            .lowercase()
    }

    fun isMessagingApp(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_MESSAGE
    }

    fun isCallNotification(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_CALL
    }

    fun isEmailNotification(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_EMAIL
    }

    fun isAlarmNotification(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_ALARM
    }

    fun isReminderNotification(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_REMINDER
    }

    fun isEventNotification(
        category: String
    ): Boolean {

        return category == Notification.CATEGORY_EVENT
    }
}