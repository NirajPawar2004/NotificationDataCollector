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

        val score =
            PriorityEngine.calculatePriority(notification)

        // Emergency / Critical
        if (isEmergency(notification))
            return Priority.HIGH

        // Calls
        if (notification.category.equals("call", true))
            return Priority.HIGH

        // Messages
        if (notification.category.equals("message", true))
            return Priority.HIGH

        // Alarm
        if (notification.category.equals("alarm", true))
            return Priority.HIGH

        // Calendar
        if (notification.category.equals("event", true))
            return Priority.HIGH

        // Frequently opened
        if (notification.wasOpened)
            return Priority.HIGH

        // Score based decision
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

        return evaluate(notification) == Priority.HIGH
    }

    fun shouldDelay(
        notification: NotificationEntity
    ): Boolean {

        return evaluate(notification) == Priority.MEDIUM
    }

    fun shouldSilence(
        notification: NotificationEntity
    ): Boolean {

        return evaluate(notification) == Priority.LOW
    }

    private fun isEmergency(
        notification: NotificationEntity
    ): Boolean {

        val text = (
                (notification.title ?: "") +
                        " " +
                        (notification.text ?: "")
                ).lowercase()

        val keywords = listOf(

            "emergency",
            "urgent",
            "911",
            "ambulance",
            "hospital",
            "accident",
            "critical",
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

        return when (evaluate(notification)) {

            Priority.HIGH -> "HIGH"

            Priority.MEDIUM -> "MEDIUM"

            Priority.LOW -> "LOW"
        }
    }
}