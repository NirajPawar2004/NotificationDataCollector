package com.niraj.notificationdatacollector.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.niraj.notificationdatacollector.model.ContactProfileEntity
import com.niraj.notificationdatacollector.model.NotificationEntity

@Database(
    entities = [
        NotificationEntity::class,
        ContactProfileEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

    abstract fun contactProfileDao(): ContactProfileDao

}