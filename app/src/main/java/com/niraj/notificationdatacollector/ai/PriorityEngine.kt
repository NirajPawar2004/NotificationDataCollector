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

        // -----------------------------------
        // Category
        // -----------------------------------

        when (notification.category?.lowercase()) {

            "call" -> score += 35

            "message" -> score += 30

            "msg" -> score += 30

            "alarm" -> score += 30

            "event" -> score += 25

            "email" -> score += 18

            "reminder" -> score += 22

            "progress" -> score += 3
        }

        // -----------------------------------
        // User Behaviour
        // -----------------------------------

        if (notification.wasOpened)
            score += 15

        if (notification.wasDismissed)
            score -= 8

        if (
            notification.responseTime in 1..30000
        )
            score += 10

        // -----------------------------------
        // Screen State
        // -----------------------------------

        if (!notification.screenOn)
            score += 5

        // -----------------------------------
        // Battery
        // -----------------------------------

        if (notification.batteryLevel < 20)
            score -= 3

        if (notification.batterySaver)
            score -= 2

        // -----------------------------------
        // Charging
        // -----------------------------------

        if (notification.charging)
            score += 1

        // -----------------------------------
        // Internet
        // -----------------------------------

        if (notification.internetAvailable)
            score += 2

        // -----------------------------------
        // Time
        // -----------------------------------

        when (notification.hour) {

            in 7..10 -> score += 3

            in 11..18 -> score += 5

            in 19..23 -> score += 2

            else -> score -= 2
        }

        // -----------------------------------
        // Priority from Android
        // -----------------------------------

        score += when (notification.priority) {

            2 -> 8

            1 -> 5

            0 -> 2

            -1 -> -2

            -2 -> -5

            else -> 0
        }

        // -----------------------------------
        // Ongoing
        // -----------------------------------

        if (notification.isOngoing)
            score += 5

        // -----------------------------------
        // System App
        // -----------------------------------

        if (notification.isSystemApp)
            score += 2

        // -----------------------------------
        // Text Length
        // -----------------------------------

        val length =
            (notification.title ?: "").length +
                    (notification.text ?: "").length

        when {

            length > 120 -> score += 4

            length > 50 -> score += 2
        }

        // -----------------------------------
        // Messaging Apps
        // -----------------------------------

        if (isMessaging(notification.packageName))
            score += 15

        // -----------------------------------
        // Email Apps
        // -----------------------------------

        if (isEmail(notification.packageName))
            score += 8

        // -----------------------------------
        // Promotional
        // -----------------------------------

        if (isPromotion(notification))
            score -= 20

        // Clamp
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
            packageName.lowercase().contains(it)
        }
    }

    private fun isEmail(
        packageName: String
    ): Boolean {

        val apps = listOf(
            "gmail",
            "outlook",
            "yahoo",
            "email"
        )

        return apps.any {
            packageName.lowercase().contains(it)
        }
    }

    private fun isPromotion(
        notification: NotificationEntity
    ): Boolean {

        val text =
            "${notification.title} ${notification.text}"
                .lowercase()

        val words = listOf(
            "sale",
            "offer",
            "discount",
            "coupon",
            "cashback",
            "deal",
            "buy now",
            "limited"
        )

        return words.any {
            text.contains(it)
        }
    }
}