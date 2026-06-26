package com.niraj.notificationdatacollector.ai

import com.niraj.notificationdatacollector.model.NotificationEntity

object PriorityEngine {

    /**
     * Returns a score between 0 and 100.
     */
    fun calculatePriority(
        notification: NotificationEntity
    ): Int {

        var score = 0

        // =====================================
        // Favorite Contact
        // =====================================

        if (notification.favoriteContact)
            score += 35

        // =====================================
        // Sender Type
        // =====================================

        when (notification.senderType.uppercase()) {

            "FAMILY" -> score += 30

            "EMERGENCY" -> score += 35

            "WORK" -> score += 20

            "FRIEND" -> score += 15
        }

        // =====================================
        // Notification Category
        // =====================================

        when (
            notification.notificationCategory.lowercase()
        ) {

            "call" -> score += 30

            "message",
            "msg" -> score += 28

            "alarm" -> score += 30

            "event" -> score += 25

            "email" -> score += 15

            "reminder" -> score += 20
        }

        // =====================================
        // User Behaviour
        // =====================================

        if (notification.opened)
            score += 15

        if (notification.dismissed)
            score -= 10

        if (notification.responseTime in 1..30_000L)
            score += 10

        // =====================================
        // Device Context
        // =====================================

        if (!notification.screenOn)
            score += 5

        if (notification.phoneLocked)
            score += 5

        if (notification.batteryLevel < 20)
            score -= 3

        if (notification.charging)
            score += 2

        if (notification.internetStatus)
            score += 2

        if (notification.doNotDisturb)
            score += 10

        // =====================================
        // Time
        // =====================================

        when (notification.hourOfDay) {

            in 7..10 -> score += 3

            in 11..18 -> score += 5

            in 19..23 -> score += 2

            else -> score -= 2
        }

        // =====================================
        // Notification Frequency
        // =====================================

        when {

            notification.notificationFrequency > 30 ->
                score -= 15

            notification.notificationFrequency > 15 ->
                score -= 8
        }

        // =====================================
        // Foreground App
        // =====================================

        if (
            notification.foregroundApp.equals(
                notification.packageName,
                ignoreCase = true
            )
        ) {
            score += 8
        }

        // =====================================
        // Messaging Apps
        // =====================================

        if (isMessaging(notification.packageName))
            score += 10

        // =====================================
        // Promotional Detection
        // =====================================

        if (isPromotion(notification))
            score -= 20

        return score.coerceIn(0, 100)
    }

    private fun isMessaging(
        packageName: String
    ): Boolean {

        val apps = listOf(

            "whatsapp",
            "telegram",
            "signal",
            "messenger",
            "discord",
            "messages"

        )

        return apps.any {

            packageName
                .lowercase()
                .contains(it)

        }
    }

    private fun isPromotion(
        notification: NotificationEntity
    ): Boolean {

        val text = (

                notification.notificationTitle +

                        " " +

                        notification.notificationText

                ).lowercase()

        val words = listOf(

            "sale",
            "offer",
            "discount",
            "coupon",
            "cashback",
            "deal",
            "buy now",
            "limited",
            "promo"

        )

        return words.any {

            text.contains(it)

        }
    }
}