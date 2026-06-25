package com.niraj.notificationdatacollector.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.niraj.notificationdatacollector.collector.NotificationContextFactory
import com.niraj.notificationdatacollector.collector.NotificationEntityFactory
import com.niraj.notificationdatacollector.exporter.CsvExporter

class NotificationCaptureService : NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationCollector"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service Created")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Listener Connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        try {

            Log.d(TAG, "Notification Received")

            val notificationContext =
                NotificationContextFactory.create(
                    this,
                    sbn
                )

            val notificationEntity =
                NotificationEntityFactory.create(
                    notificationContext
                )

            CsvExporter.saveNotification(
                this,
                notificationEntity
            )

            Log.d(TAG, "==========================================")
            Log.d(TAG, "Time : ${notificationEntity.formattedTime}")
            Log.d(TAG, "App : ${notificationEntity.appName}")
            Log.d(TAG, "Package : ${notificationEntity.packageName}")
            Log.d(TAG, "Title : ${notificationEntity.title}")
            Log.d(TAG, "Text : ${notificationEntity.text}")
            Log.d(TAG, "Battery : ${notificationEntity.batteryLevel}")
            Log.d(TAG, "Charging : ${notificationEntity.charging}")
            Log.d(TAG, "Screen : ${notificationEntity.screenOn}")
            Log.d(TAG, "WiFi : ${notificationEntity.wifiConnected}")
            Log.d(TAG, "Connection : ${notificationEntity.connectionType}")
            Log.d(TAG, "CSV Export : Success")
            Log.d(TAG, "==========================================")

        } catch (e: Exception) {

            Log.e(
                TAG,
                "Notification Processing Failed",
                e
            )

        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)

        Log.d(TAG, "Notification Removed")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()

        Log.d(TAG, "Listener Disconnected")
    }
}