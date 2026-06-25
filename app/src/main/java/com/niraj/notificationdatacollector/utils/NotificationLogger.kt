package com.niraj.notificationdatacollector.utils

import android.util.Log
import com.niraj.notificationdatacollector.model.NotificationEntity

object NotificationLogger {

    private const val TAG = "NotificationCollector"

    fun serviceStarted() {
        Log.i(TAG, "Notification Listener Started")
    }

    fun serviceStopped() {
        Log.i(TAG, "Notification Listener Stopped")
    }

    fun notificationReceived(entity: NotificationEntity) {

        Log.d(
            TAG,
            """
            -----------------------------
            App      : ${entity.appName}
            Package  : ${entity.packageName}
            Title    : ${entity.title}
            Text     : ${entity.text}
            Time     : ${entity.formattedTime}
            -----------------------------
            """.trimIndent()
        )
    }

    fun databaseSaved(id: Long) {
        Log.i(TAG, "Saved Notification ID = $id")
    }

    fun csvExport(path: String) {
        Log.i(TAG, "CSV Exported : $path")
    }

    fun duplicateDetected(packageName: String) {
        Log.w(TAG, "Duplicate Notification : $packageName")
    }

    fun error(
        message: String,
        throwable: Throwable? = null
    ) {
        Log.e(TAG, message, throwable)
    }

    fun statistics(
        total: Int,
        apps: Int
    ) {

        Log.i(
            TAG,
            """
            ===== Dataset Statistics =====
            Total Notifications : $total
            Unique Apps         : $apps
            ==============================
            """.trimIndent()
        )
    }
}