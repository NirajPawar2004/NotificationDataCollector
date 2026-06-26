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
            it.notificationCategory.equals(category, true)
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

    fun byDayOfWeek(
        notifications: List<NotificationEntity>,
        day: String
    ): List<NotificationEntity> {

        return notifications.filter {
            it.dayOfWeek.equals(day, true)
        }
    }

    fun byHour(
        notifications: List<NotificationEntity>,
        hour: Int
    ): List<NotificationEntity> {

        return notifications.filter {
            it.hourOfDay == hour
        }
    }

    fun openedOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.opened
        }
    }

    fun dismissedOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.dismissed
        }
    }

    fun favoriteContactsOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.favoriteContact
        }
    }

    fun doNotDisturbOnly(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.filter {
            it.doNotDisturb
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

            it.appName.lowercase().contains(q) ||

                    it.packageName.lowercase().contains(q) ||

                    it.notificationTitle.lowercase().contains(q) ||

                    it.notificationText.lowercase().contains(q) ||

                    it.senderName.lowercase().contains(q)
        }
    }

    fun sortNewest(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedByDescending {
            it.timestamp
        }
    }

    fun sortOldest(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedBy {
            it.timestamp
        }
    }

    fun sortByPriority(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedByDescending {

            when (it.priorityLabel.uppercase()) {

                "HIGH" -> 3

                "MEDIUM" -> 2

                "LOW" -> 1

                else -> 0
            }
        }
    }

    fun sortByConfidence(
        notifications: List<NotificationEntity>
    ): List<NotificationEntity> {

        return notifications.sortedByDescending {
            it.predictionConfidence
        }
    }
}