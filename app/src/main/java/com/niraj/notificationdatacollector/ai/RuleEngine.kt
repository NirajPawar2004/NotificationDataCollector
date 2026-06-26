package com.niraj.notificationdatacollector.ai

import com.niraj.notificationdatacollector.model.NotificationEntity

object RuleEngine {

    enum class Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    fun evaluate(
        notification: NotificationEntity
    ): Priority {

        // ==========================================
        // Absolute High Priority Rules
        // ==========================================

        if (notification.favoriteContact)
            return Priority.HIGH

        if (
            notification.senderType.equals(
                "FAMILY",
                true
            )
        ) {
            return Priority.HIGH
        }

        if (
            notification.senderType.equals(
                "EMERGENCY",
                true
            )
        ) {
            return Priority.HIGH
        }

        if (isEmergency(notification))
            return Priority.HIGH

        when (
            notification.notificationCategory.lowercase()
        ) {

            "call",
            "message",
            "msg",
            "alarm",
            "event" -> return Priority.HIGH
        }

        if (
            notification.opened &&
            notification.responseTime in 1..30_000L
        ) {
            return Priority.HIGH
        }

        // ==========================================
        // Score Based Decision
        // ==========================================

        val score =
            PriorityEngine.calculatePriority(
                notification
            )

        return when {

            score >= 70 ->
                Priority.HIGH

            score >= 40 ->
                Priority.MEDIUM

            else ->
                Priority.LOW
        }
    }

    fun shouldInterrupt(
        notification: NotificationEntity
    ): Boolean {

        return evaluate(notification) ==
                Priority.HIGH
    }

    fun shouldDelay(
        notification: NotificationEntity
    ): Boolean {

        return evaluate(notification) ==
                Priority.MEDIUM
    }

    fun shouldSilence(
        notification: NotificationEntity
    ): Boolean {

        return evaluate(notification) ==
                Priority.LOW
    }

    private fun isEmergency(
        notification: NotificationEntity
    ): Boolean {

        val text = (

                notification.notificationTitle +

                        " " +

                        notification.notificationText

                ).lowercase()

        val keywords = listOf(

            "emergency",
            "urgent",
            "911",
            "ambulance",
            "hospital",
            "critical",
            "accident",
            "fire",
            "police",
            "help",
            "security alert",
            "fraud alert",
            "otp",
            "verification code"

        )

        return keywords.any {

            text.contains(it)

        }
    }

    fun priorityLabel(
        notification: NotificationEntity
    ): String {

        return when (

            evaluate(notification)

        ) {

            Priority.HIGH -> "HIGH"

            Priority.MEDIUM -> "MEDIUM"

            Priority.LOW -> "LOW"
        }
    }
}