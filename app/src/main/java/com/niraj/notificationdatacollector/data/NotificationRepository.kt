package com.niraj.notificationdatacollector.data

import com.niraj.notificationdatacollector.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    val allNotifications: Flow<List<NotificationEntity>> =
        notificationDao.observeNotifications()

    suspend fun insert(
        notification: NotificationEntity
    ) {
        notificationDao.insertNotification(notification)
    }

    suspend fun insertAll(
        notifications: List<NotificationEntity>
    ) {
        notificationDao.insertNotifications(notifications)
    }

    suspend fun getAll(): List<NotificationEntity> {
        return notificationDao.getAllNotifications()
    }

    suspend fun getById(
        id: Long
    ): NotificationEntity? {
        return notificationDao.getNotificationById(id)
    }

    suspend fun getCount(): Int {
        return notificationDao.getNotificationCount()
    }

    suspend fun getTodayCount(
        today: String
    ): Int {
        return notificationDao.getTodayNotificationCount(today)
    }

    suspend fun getAppCount(
        packageName: String
    ): Int {
        return notificationDao.getNotificationCountForApp(packageName)
    }

    suspend fun getUniqueApps(): Int {
        return notificationDao.getUniqueAppCount()
    }

    suspend fun deleteAll() {
        notificationDao.deleteAllNotifications()
    }
}