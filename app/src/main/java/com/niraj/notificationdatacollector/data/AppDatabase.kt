package com.niraj.notificationdatacollector.data

abstract class AppDatabase {

    abstract fun notificationDao(): NotificationDao

    companion object {
        const val DATABASE_NAME = "notification_database"
    }
}