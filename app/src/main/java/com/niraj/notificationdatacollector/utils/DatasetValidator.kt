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

        if (notification.timestampMillis <= 0L) {
            errors.add("Invalid timestamp")
        }

        if (notification.appName.isBlank()) {
            errors.add("App name missing")
        }

        if (notification.packageName.isBlank()) {
            errors.add("Package name missing")
        }

        if (notification.formattedTime.isBlank()) {
            errors.add("Formatted time missing")
        }

        if (notification.date.isBlank()) {
            errors.add("Date missing")
        }

        if (notification.dayOfWeek.isBlank()) {
            errors.add("Day of week missing")
        }

        if (notification.month.isBlank()) {
            errors.add("Month missing")
        }

        if (notification.year < 2020) {
            errors.add("Invalid year")
        }

        if (notification.hour !in 0..23) {
            errors.add("Invalid hour")
        }

        if (notification.minute !in 0..59) {
            errors.add("Invalid minute")
        }

        if (notification.second !in 0..59) {
            errors.add("Invalid second")
        }

        if (notification.batteryLevel !in 0..100) {
            errors.add("Invalid battery level")
        }

        if (notification.priority !in -2..2) {
            errors.add("Unexpected Android priority")
        }

        if (notification.responseTime < 0) {
            errors.add("Negative response time")
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
            if (value) completed++
        }

        check(notification.appName.isNotBlank())
        check(notification.packageName.isNotBlank())
        check(notification.title?.isNotBlank() == true)
        check(notification.text?.isNotBlank() == true)
        check(notification.category?.isNotBlank() == true)
        check(notification.channelId?.isNotBlank() == true)
        check(notification.formattedTime.isNotBlank())
        check(notification.date.isNotBlank())
        check(notification.priorityLabel.isNotBlank())

        return (completed * 100) / total
    }
}