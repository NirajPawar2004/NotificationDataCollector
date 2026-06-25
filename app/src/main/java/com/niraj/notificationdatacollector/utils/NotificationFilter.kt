package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.model.NotificationEntity

object NotificationFilter {

    fun byApp(
        notifications: List<NotificationEntity>,
        appName: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.appName.equals(appName, true)
        }
    }

    fun byPackage(
        notifications: List<NotificationEntity>,
        packageName: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.packageName.equals(packageName, true)
        }
    }

    fun byCategory(
        notifications: List<NotificationEntity>,
        category: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.category.equals(category, true)
        }
    }

    fun byPriorityLabel(
        notifications: List<NotificationEntity>,
        priority: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.priorityLabel.equals(priority, true)
        }
    }

    fun byDate(
        notifications: List<NotificationEntity>,
        date: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.date == date
        }
    }

    fun byHour(
        notifications: List<NotificationEntity>,
        hour: Int
    ): List<NotificationEntity> {

        return notifications.filter {
            it.hour == hour
        }
    }

    fun openedOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.wasOpened
        }
    }

    fun dismissedOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.wasDismissed
        }
    }

    fun ongoingOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.isOngoing
        }
    }

    fun clearableOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.isClearable
        }
    }

    fun batteryBelow(
        notifications: List<NotificationEntity>,
        level: Int
    ): List<NotificationEntity> {

        return notifications.filter {
            it.batteryLevel <= level
        }
    }

    fun chargingOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.charging
        }
    }

    fun search(
        notifications: List<NotificationEntity>,
        query: String
    ): List<NotificationEntity> {

        val q = query.lowercase()

        return notifications.filter {

            (it.appName ?: "").lowercase().contains(q) ||
                    (it.packageName ?: "").lowercase().contains(q) ||
                    (it.title ?: "").lowercase().contains(q) ||
                    (it.text ?: "").lowercase().contains(q)
        }
    }

    fun sortNewest(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedByDescending {
            it.timestampMillis
        }
    }

    fun sortOldest(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedBy {
            it.timestampMillis
        }
    }

    fun sortByPriority(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedByDescending {
            it.priority
        }
    }
}