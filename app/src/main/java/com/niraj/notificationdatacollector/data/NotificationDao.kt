package com.niraj.notificationdatacollector.data

import com.niraj.notificationdatacollector.model.NotificationEntity

interface NotificationDao {

    fun insertNotification(notification: NotificationEntity)

    fun getAllNotifications(): List<NotificationEntity>

    fun deleteAllNotifications()
}