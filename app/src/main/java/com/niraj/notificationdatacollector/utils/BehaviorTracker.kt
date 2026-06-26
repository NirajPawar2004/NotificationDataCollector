package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.model.NotificationEntity

class BehaviorTracker(
    private val repository: NotificationRepository
) {

    suspend fun markOpened(
        notificationId: Long
    ) {

        val notification =
            repository.getById(notificationId)
                ?: return

        val currentTime =
            System.currentTimeMillis()

        val timeTaken =
            currentTime - notification.timestamp

        val updated =
            notification.copy(

                opened = true,

                dismissed = false,

                timeToOpen = timeTaken,

                responseTime = timeTaken,

                priorityLabel = "HIGH",

                priorityClass = 2
            )

        repository.update(updated)
    }

    suspend fun markDismissed(
        notificationId: Long
    ) {

        val notification =
            repository.getById(notificationId)
                ?: return

        val currentTime =
            System.currentTimeMillis()

        val response =
            currentTime - notification.timestamp

        val updated =
            notification.copy(

                opened = false,

                dismissed = true,

                responseTime = response,

                priorityLabel = "LOW",

                priorityClass = 0
            )

        repository.update(updated)
    }

    suspend fun markIgnored(
        notificationId: Long
    ) {

        val notification =
            repository.getById(notificationId)
                ?: return

        val updated =
            notification.copy(

                opened = false,

                dismissed = false,

                priorityLabel = "MEDIUM",

                priorityClass = 1
            )

        repository.update(updated)
    }
}