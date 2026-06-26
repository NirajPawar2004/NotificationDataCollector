package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.model.NotificationEntity

object DatasetValidator {

    data class ValidationResult(
        val valid: Boolean,
        val errors: List<String>
    )

    fun validate(
        notification: NotificationEntity
    ): ValidationResult {

        val errors = mutableListOf<String>()

        if (notification.notificationId <= 0L) {
            errors.add("Invalid notification id")
        }

        if (notification.timestamp <= 0L) {
            errors.add("Invalid timestamp")
        }

        if (notification.userId.isBlank()) {
            errors.add("User ID missing")
        }

        if (notification.appName.isBlank()) {
            errors.add("App name missing")
        }

        if (notification.packageName.isBlank()) {
            errors.add("Package name missing")
        }

        if (notification.notificationTitle.isBlank()) {
            errors.add("Notification title missing")
        }

        if (notification.notificationCategory.isBlank()) {
            errors.add("Notification category missing")
        }

        if (notification.dayOfWeek.isBlank()) {
            errors.add("Day of week missing")
        }

        if (notification.hourOfDay !in 0..23) {
            errors.add("Invalid hour")
        }

        if (notification.batteryLevel !in 0..100) {
            errors.add("Invalid battery level")
        }

        if (notification.notificationFrequency < 0) {
            errors.add("Invalid notification frequency")
        }

        if (notification.responseTime < 0) {
            errors.add("Negative response time")
        }

        if (notification.timeToOpen < 0) {
            errors.add("Negative time to open")
        }

        if (
            notification.priorityLabel !in
            listOf("HIGH", "MEDIUM", "LOW", "UNKNOWN")
        ) {
            errors.add("Invalid priority label")
        }

        if (notification.predictionConfidence !in 0.0..1.0) {
            errors.add("Prediction confidence must be between 0 and 1")
        }

        return ValidationResult(
            valid = errors.isEmpty(),
            errors = errors
        )
    }

    fun validateAll(
        notifications: List<NotificationEntity>
    ): ValidationResult {

        val errors = mutableListOf<String>()

        notifications.forEachIndexed { index, notification ->

            val result = validate(notification)

            if (!result.valid) {

                result.errors.forEach {

                    errors.add(
                        "Row ${index + 1}: $it"
                    )
                }
            }
        }

        return ValidationResult(
            valid = errors.isEmpty(),
            errors = errors
        )
    }

    fun validNotifications(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {

            validate(it).valid

        }
    }

    fun invalidNotifications(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filterNot {

            validate(it).valid

        }
    }

    fun completionPercentage(
        notification: NotificationEntity
    ): Int {

        var total = 0
        var completed = 0

        fun check(value: Boolean) {

            total++

            if (value)
                completed++
        }

        check(notification.userId.isNotBlank())
        check(notification.appName.isNotBlank())
        check(notification.packageName.isNotBlank())
        check(notification.notificationTitle.isNotBlank())
        check(notification.notificationText.isNotBlank())
        check(notification.notificationCategory.isNotBlank())
        check(notification.senderName.isNotBlank())
        check(notification.senderType.isNotBlank())
        check(notification.dayOfWeek.isNotBlank())
        check(notification.priorityLabel.isNotBlank())

        return (completed * 100) / total
    }
}