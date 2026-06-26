package com.niraj.notificationdatacollector.utils

import android.util.Log
import com.niraj.notificationdatacollector.model.NotificationEntity

object NotificationLogger {

    private const val TAG = "NotificationCollector"

    fun serviceStarted() {

        Log.i(
            TAG,
            "Notification Listener Started"
        )
    }

    fun serviceStopped() {

        Log.i(
            TAG,
            "Notification Listener Stopped"
        )
    }

    fun notificationReceived(
        entity: NotificationEntity
    ) {

        Log.d(
            TAG,
            """
================ Notification =================

App                 : ${entity.appName}
Package             : ${entity.packageName}

Title               : ${entity.notificationTitle}
Text                : ${entity.notificationText}

Sender              : ${entity.senderName}
Sender Type         : ${entity.senderType}
Favorite Contact    : ${entity.favoriteContact}

Category            : ${entity.notificationCategory}

Priority Label      : ${entity.priorityLabel}

Timestamp           : ${entity.timestamp}

===============================================
            """.trimIndent()
        )
    }

    fun databaseSaved(
        id: Long
    ) {

        Log.i(
            TAG,
            "Database Saved : $id"
        )
    }

    fun csvExport(
        path: String
    ) {

        Log.i(
            TAG,
            "CSV Exported : $path"
        )
    }

    fun jsonExport(
        path: String
    ) {

        Log.i(
            TAG,
            "JSON Exported : $path"
        )
    }

    fun duplicateDetected(
        packageName: String
    ) {

        Log.w(
            TAG,
            "Duplicate Notification : $packageName"
        )
    }

    fun statistics(

        total: Int,

        apps: Int

    ) {

        Log.i(
            TAG,
            """
============= DATASET =============

Total Notifications : $total

Unique Apps         : $apps

===================================
            """.trimIndent()
        )
    }

    fun error(

        message: String,

        throwable: Throwable? = null

    ) {

        Log.e(
            TAG,
            message,
            throwable
        )
    }
}