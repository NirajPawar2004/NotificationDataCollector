package com.niraj.notificationdatacollector

import android.app.Application
import com.niraj.notificationdatacollector.data.DatabaseProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Room Database
        DatabaseProvider.getDatabase(this)
    }
}