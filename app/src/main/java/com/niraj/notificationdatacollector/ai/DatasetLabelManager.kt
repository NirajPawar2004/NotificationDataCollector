package com.niraj.notificationdatacollector.ai

import com.niraj.notificationdatacollector.model.NotificationEntity

object DatasetLabelManager {

    const val HIGH = "HIGH"
    const val MEDIUM = "MEDIUM"
    const val LOW = "LOW"

    fun generateLabel(
        notification: NotificationEntity
    ): String {

        // User interacted quickly
        if (notification.wasOpened &&
            notification.responseTime in 1..30000L
        ) {
            return HIGH
        }

        // Calls
        if (notification.category == "call") {
            return HIGH
        }

        // Messages
        if (notification.category == "msg" ||
            notification.category == "message"
        ) {
            return HIGH
        }

        // Alarm
        if (notification.category == "alarm") {
            return HIGH
        }

        // Calendar
        if (notification.category == "event") {
            return HIGH
        }

        // Email
        if (notification.category == "email") {
            return MEDIUM
        }

        // Downloads
        if (notification.category == "progress") {
            return LOW
        }

        // Promotional
        if (containsPromotion(notification)) {
            return LOW
        }

        // Social media
        if (isSocial(notification.packageName)) {
            return MEDIUM
        }

        return LOW
    }

    private fun containsPromotion(
        notification: NotificationEntity
    ): Boolean {

        val text =
            "${notification.title} ${notification.text}"
                .lowercase()

        val keywords = listOf(
            "offer",
            "sale",
            "discount",
            "coupon",
            "cashback",
            "deal",
            "buy now",
            "limited time"
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
            "snapchat",
            "messenger",
            "discord",
            "twitter",
            "x"
        )

        return socialApps.any {
            packageName.lowercase().contains(it)
        }
    }

    fun assignPriorityScore(
        label: String
    ): Int {

        return when (label) {

            HIGH -> 3

            MEDIUM -> 2

            else -> 1
        }
    }
}