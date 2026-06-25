package com.niraj.notificationdatacollector.repository

import com.niraj.notificationdatacollector.data.NotificationDao
import com.niraj.notificationdatacollector.model.NotificationEntity

class NotificationRepository(
    private val notificationDao: NotificationDao
) {

    fun insertNotification(notification: NotificationEntity) {
        notificationDao.insertNotification(notification)
    }

    fun getAllNotifications(): List<NotificationEntity> {
        return notificationDao.getAllNotifications()
    }

    fun deleteAllNotifications() {
        notificationDao.deleteAllNotifications()
    }
}