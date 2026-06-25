package com.niraj.notificationdatacollector.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.niraj.notificationdatacollector.model.NotificationEntity

@Database(
    entities = [
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NotificationDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

}