package com.niraj.notificationdatacollector.data

import com.niraj.notificationdatacollector.model.NotificationEntity

class NotificationRepository(
    private val dao: NotificationDao
) {

    suspend fun insert(
        notification: NotificationEntity
    ): Long {

        return dao.insert(notification)
    }

    suspend fun update(
        notification: NotificationEntity
    ) {

        dao.update(notification)
    }

    suspend fun getAll(): List<NotificationEntity> {

        return dao.getAll()
    }

    suspend fun getById(
        id: Long
    ): NotificationEntity? {

        return dao.getById(id)
    }

    suspend fun getCount(): Int {

        return dao.getCount()
    }

    suspend fun getUniqueApps(): Int {

        return dao.getUniqueApps()
    }

    suspend fun getTodayCount(
        timestamp: Long
    ): Int {

        return dao.getTodayCount(timestamp)
    }

    suspend fun deleteAll() {

        dao.deleteAll()
    }
}