package com.niraj.notificationdatacollector.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.niraj.notificationdatacollector.collector.NotificationCollector
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationListenerService : NotificationListenerService() {

    companion object {
        private const val TAG = "Collector"
    }

    private val serviceScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val repository by lazy {

        Log.d(TAG, "Initializing Repository")

        NotificationRepository(
            DatabaseProvider
                .getDatabase(applicationContext)
                .notificationDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service Created")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Listener Connected")
    }

    override fun onNotificationPosted(
        sbn: StatusBarNotification
    ) {

        Log.d(TAG, "===================================")
        Log.d(TAG, "Notification Received")
        Log.d(TAG, "Package : ${sbn.packageName}")
        Log.d(TAG, "Post Time : ${sbn.postTime}")

        serviceScope.launch {

            try {

                Log.d(TAG, "Collecting notification...")

                val entity = NotificationCollector.collect(
                    applicationContext,
                    sbn
                )

                Log.d(TAG, "Collection Successful")
                Log.d(TAG, "App Name : ${entity.appName}")
                Log.d(TAG, "Title : ${entity.title}")
                Log.d(TAG, "Saving into Room Database...")

                repository.insert(entity)

                Log.d(TAG, "Database Insert Successful")
                Log.d(TAG, "===================================")

            } catch (e: Exception) {

                Log.e(TAG, "Database Insert Failed", e)

            }
        }
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification
    ) {

        Log.d(TAG, "Notification Removed : ${sbn.packageName}")

        super.onNotificationRemoved(sbn)
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "Listener Disconnected")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service Destroyed")
    }
}