package com.niraj.notificationdatacollector.exporter

import android.content.Context
import android.util.Log
import com.niraj.notificationdatacollector.model.NotificationEntity
import java.io.File

object CsvExporter {

    private const val TAG = "CsvExporter"

    private const val FILE_NAME =
        "notification_dataset_v3.csv"

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

                file.appendText(
                    getHeader()
                )
            }

            file.appendText(
                getRow(entity)
            )

            Log.d(
                TAG,
                "Saved : ${file.absolutePath}"
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

            "notificationId",

            "notificationKey",

            "userId",

            "appName",

            "packageName",

            "notificationCategory",

            "notificationTitle",

            "notificationText",

            "senderName",

            "senderId",

            "senderType",

            "favoriteContact",

            "notificationFrequency",

            "timestamp",

            "dayOfWeek",

            "hourOfDay",

            "screenOn",

            "phoneLocked",

            "batteryLevel",

            "charging",

            "internetStatus",

            "doNotDisturb",

            "foregroundApp",

            "userActivity",

            "opened",

            "dismissed",

            "timeToOpen",

            "responseTime",

            "priorityLabel",

            "priorityClass",

            "predictionConfidence"

        ).joinToString(",") + "\n"
    }

    private fun csv(
        value: Any?
    ): String {

        return "\"" +
                (value?.toString()
                    ?.replace("\"", "\"\"")
                    ?: "") +
                "\""
    }

    private fun getRow(
        entity: NotificationEntity
    ): String {

        return listOf(

            entity.notificationId,

            entity.notificationKey,

            entity.userId,

            entity.appName,

            entity.packageName,

            entity.notificationCategory,

            entity.notificationTitle,

            entity.notificationText,

            entity.senderName,

            entity.senderId,

            entity.senderType,

            entity.favoriteContact,

            entity.notificationFrequency,

            entity.timestamp,

            entity.dayOfWeek,

            entity.hourOfDay,

            entity.screenOn,

            entity.phoneLocked,

            entity.batteryLevel,

            entity.charging,

            entity.internetStatus,

            entity.doNotDisturb,

            entity.foregroundApp,

            entity.userActivity,

            entity.opened,

            entity.dismissed,

            entity.timeToOpen,

            entity.responseTime,

            entity.priorityLabel,

            entity.priorityClass,

            entity.predictionConfidence

        ).joinToString(",") {

            csv(it)

        } + "\n"
    }
}