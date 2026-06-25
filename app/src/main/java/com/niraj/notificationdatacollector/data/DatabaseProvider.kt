package com.niraj.notificationdatacollector.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: NotificationDatabase? = null

    fun getDatabase(context: Context): NotificationDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                NotificationDatabase::class.java,
                "notification_database"
            )
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance

            instance
        }
    }
}