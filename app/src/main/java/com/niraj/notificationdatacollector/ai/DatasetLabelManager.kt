package com.niraj.notificationdatacollector.ai

import com.niraj.notificationdatacollector.model.NotificationEntity

object DatasetLabelManager {

    const val HIGH = "HIGH"
    const val MEDIUM = "MEDIUM"
    const val LOW = "LOW"

    fun generateLabel(
        notification: NotificationEntity
    ): String {

        // =====================================
        // Favorite Contact
        // =====================================

        if (notification.favoriteContact) {

            return HIGH
        }

        // =====================================
        // Family / Emergency
        // =====================================

        if (

            notification.senderType == "FAMILY" ||

            notification.senderType == "EMERGENCY"

        ) {

            return HIGH
        }

        // =====================================
        // User Opened Quickly
        // =====================================

        if (

            notification.opened &&

            notification.responseTime in 1..30_000L

        ) {

            return HIGH
        }

        // =====================================
        // Important Categories
        // =====================================

        when (
            notification.notificationCategory.lowercase()
        ) {

            "call",
            "msg",
            "message",
            "alarm",
            "event" -> return HIGH

            "email" -> return MEDIUM
        }

        // =====================================
        // Promotional Notification
        // =====================================

        if (
            containsPromotion(notification)
        ) {

            return LOW
        }

        // =====================================
        // Frequently Ignored
        // =====================================

        if (

            notification.dismissed &&

            notification.notificationFrequency > 20

        ) {

            return LOW
        }

        // =====================================
        // Social Apps
        // =====================================

        if (
            isSocial(
                notification.packageName
            )
        ) {

            return MEDIUM
        }

        return LOW
    }

    private fun containsPromotion(
        notification: NotificationEntity
    ): Boolean {

        val text = (

                notification.notificationTitle +

                        " " +

                        notification.notificationText

                ).lowercase()

        val keywords = listOf(

            "offer",

            "sale",

            "discount",

            "coupon",

            "cashback",

            "deal",

            "buy now",

            "limited time",

            "promo"

        )

        return keywords.any {

            text.contains(it)

        }
    }

    private fun isSocial(
        packageName: String
    ): Boolean {

        val socialApps = listOf(

            "whatsapp",

            "instagram",

            "facebook",

            "telegram",

            "discord",

            "snapchat",

            "messenger",

            "twitter",

            "x"

        )

        return socialApps.any {

            packageName
                .lowercase()
                .contains(it)

        }
    }

    fun assignPriorityScore(
        label: String
    ): Int {

        return when (label) {

            HIGH -> 3

            MEDIUM -> 2

            LOW -> 1

            else -> 0
        }
    }
}