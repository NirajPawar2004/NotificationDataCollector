package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.model.NotificationEntity

object DatasetStatistics {

    fun totalNotifications(
        notifications: List<NotificationEntity>
    ): Int {
        return notifications.size
    }

    fun uniqueApps(
        notifications: List<NotificationEntity>
    ): Int {
        return notifications
            .map { it.packageName }
            .distinct()
            .size
    }

    fun notificationsPerApp(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.appName }
            .eachCount()
            .toSortedMap()
    }

    fun notificationsPerDay(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.date }
            .eachCount()
            .toSortedMap()
    }

    fun hourlyDistribution(
        notifications: List<NotificationEntity>
    ): Map<Int, Int> {

        return notifications
            .groupingBy { it.hour }
            .eachCount()
            .toSortedMap()
    }

    fun priorityDistribution(
        notifications: List<NotificationEntity>
    ): Map<Int, Int> {

        return notifications
            .groupingBy { it.priority }
            .eachCount()
            .toSortedMap()
    }

    fun categoryDistribution(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.category ?: "UNKNOWN" }
            .eachCount()
            .toSortedMap()
    }

    fun topApps(
        notifications: List<NotificationEntity>,
        limit: Int = 10
    ): List<Pair<String, Int>> {

        return notifications
            .groupingBy { it.appName }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
    }

    fun averageNotificationsPerDay(
        notifications: List<NotificationEntity>
    ): Double {

        if (notifications.isEmpty()) return 0.0

        val uniqueDays =
            notifications.map { it.date }.distinct().size

        return notifications.size.toDouble() / uniqueDays
    }
}