package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.model.NotificationEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            .groupingBy {

                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).format(
                    Date(it.timestamp)
                )

            }
            .eachCount()
            .toSortedMap()
    }

    fun hourlyDistribution(
        notifications: List<NotificationEntity>
    ): Map<Int, Int> {

        return notifications
            .groupingBy { it.hourOfDay }
            .eachCount()
            .toSortedMap()
    }

    fun priorityDistribution(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.priorityLabel }
            .eachCount()
            .toSortedMap()
    }

    fun categoryDistribution(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.notificationCategory }
            .eachCount()
            .toSortedMap()
    }

    fun senderTypeDistribution(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy { it.senderType }
            .eachCount()
            .toSortedMap()
    }

    fun favoriteContactDistribution(
        notifications: List<NotificationEntity>
    ): Map<String, Int> {

        return notifications
            .groupingBy {

                if (it.favoriteContact)
                    "Favorite"
                else
                    "Normal"

            }
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

        if (notifications.isEmpty())
            return 0.0

        val uniqueDays =
            notifications
                .map {

                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(
                        Date(it.timestamp)
                    )

                }
                .distinct()
                .size

        if (uniqueDays == 0)
            return 0.0

        return notifications.size.toDouble() /
                uniqueDays
    }

    fun averageResponseTime(
        notifications: List<NotificationEntity>
    ): Double {

        val valid =
            notifications.filter {

                it.responseTime >= 0

            }

        if (valid.isEmpty())
            return 0.0

        return valid
            .map { it.responseTime }
            .average()
    }

    fun openedPercentage(
        notifications: List<NotificationEntity>
    ): Double {

        if (notifications.isEmpty())
            return 0.0

        return notifications.count {

            it.opened

        } * 100.0 /
                notifications.size
    }

    fun dismissedPercentage(
        notifications: List<NotificationEntity>
    ): Double {

        if (notifications.isEmpty())
            return 0.0

        return notifications.count {

            it.dismissed

        } * 100.0 /
                notifications.size
    }
}