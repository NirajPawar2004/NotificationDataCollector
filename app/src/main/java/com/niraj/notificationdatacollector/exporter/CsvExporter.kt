package com.niraj.notificationdatacollector.exporter

import android.content.Context
import android.util.Log
import com.niraj.notificationdatacollector.model.NotificationEntity
import java.io.File

object CsvExporter {

    private const val TAG = "NotificationCollector"
    private const val FILE_NAME = "notifications.csv"

    fun saveNotification(
        context: Context,
        entity: NotificationEntity
    ) {

        try {

            val file = File(
                context.getExternalFilesDir(null),
                FILE_NAME
            )

            if (!file.exists()) {

                file.createNewFile()

                file.appendText(getHeader())

            }

            file.appendText(getRow(entity))

            Log.d(
                TAG,
                "CSV Saved : ${file.absolutePath}"
            )

        } catch (e: Exception) {

            Log.e(
                TAG,
                "CSV Export Failed",
                e
            )

        }

    }

    private fun getHeader(): String {

        return listOf(

            "timestampMillis",
            "formattedTime",
            "date",
            "dayOfWeek",
            "month",
            "year",
            "hour",
            "minute",
            "second",

            "appName",
            "packageName",
            "versionName",
            "versionCode",
            "isSystemApp",

            "title",
            "text",
            "bigText",
            "subText",
            "conversationTitle",
            "category",
            "channelId",
            "priority",
            "visibility",
            "isOngoing",
            "isClearable",
            "notificationNumber",
            "postTime",

            "screenOn",
            "screenOrientation",
            "batteryLevel",
            "charging",
            "batterySaver",
            "ringerMode",
            "mediaVolume",
            "notificationVolume",
            "alarmVolume",

            "internetAvailable",
            "wifiConnected",
            "mobileDataConnected",
            "connectionType",

            "priorityLabel",
            "userAction",
            "responseTime",
            "wasOpened",
            "wasDismissed",
            "notes"

        ).joinToString(",") + "\n"

    }

    private fun csv(value: Any?): String {
        val text = value?.toString() ?: ""
        return "\"" + text.replace("\"", "\"\"") + "\""
    }

    private fun getRow(entity: NotificationEntity): String {

        return listOf(

            entity.timestampMillis,
            entity.formattedTime,
            entity.date,
            entity.dayOfWeek,
            entity.month,
            entity.year,
            entity.hour,
            entity.minute,
            entity.second,

            entity.appName,
            entity.packageName,
            entity.versionName,
            entity.versionCode,
            entity.isSystemApp,

            entity.title,
            entity.text,
            entity.bigText,
            entity.subText,
            entity.conversationTitle,
            entity.category,
            entity.channelId,
            entity.priority,
            entity.visibility,
            entity.isOngoing,
            entity.isClearable,
            entity.notificationNumber,
            entity.postTime,

            entity.screenOn,
            entity.screenOrientation,
            entity.batteryLevel,
            entity.charging,
            entity.batterySaver,
            entity.ringerMode,
            entity.mediaVolume,
            entity.notificationVolume,
            entity.alarmVolume,

            entity.internetAvailable,
            entity.wifiConnected,
            entity.mobileDataConnected,
            entity.connectionType,

            entity.priorityLabel,
            entity.userAction,
            entity.responseTime,
            entity.wasOpened,
            entity.wasDismissed,
            entity.notes

        ).joinToString(",") { csv(it) } + "\n"

    }

}