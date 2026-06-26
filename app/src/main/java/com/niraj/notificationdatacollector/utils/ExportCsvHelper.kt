package com.niraj.notificationdatacollector.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.niraj.notificationdatacollector.data.NotificationRepository
import java.io.OutputStreamWriter

class ExportCsvHelper(

    private val context: Context,

    private val repository: NotificationRepository

) {

    suspend fun export(): Uri {

        val notifications =
            repository.getAll()

        val resolver =
            context.contentResolver

        val fileName =
            "notification_dataset_v3.csv"

        // Delete old export

        resolver.query(

            MediaStore.Downloads.EXTERNAL_CONTENT_URI,

            arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME
            ),

            "${MediaStore.MediaColumns.DISPLAY_NAME}=?",

            arrayOf(fileName),

            null

        )?.use { cursor ->

            val idIndex =
                cursor.getColumnIndexOrThrow(
                    MediaStore.MediaColumns._ID
                )

            while (cursor.moveToNext()) {

                val id =
                    cursor.getLong(idIndex)

                resolver.delete(

                    Uri.withAppendedPath(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        id.toString()
                    ),

                    null,

                    null

                )
            }
        }

        val values =
            ContentValues().apply {

                put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    fileName
                )

                put(
                    MediaStore.MediaColumns.MIME_TYPE,
                    "text/csv"
                )

                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS +
                            "/NotificationDataset"
                )
            }

        val uri =
            resolver.insert(

                MediaStore.Downloads.EXTERNAL_CONTENT_URI,

                values

            ) ?: throw Exception(
                "Unable to create CSV."
            )

        resolver.openOutputStream(uri)?.use { stream ->

            val writer =
                OutputStreamWriter(stream)

            writer.appendLine(

                listOf(

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

                ).joinToString(",")

            )

            notifications.forEach { n ->

                writer.appendLine(

                    listOf(

                        n.notificationId,

                        csv(n.notificationKey),

                        csv(n.userId),

                        csv(n.appName),

                        csv(n.packageName),

                        csv(n.notificationCategory),

                        csv(n.notificationTitle),

                        csv(n.notificationText),

                        csv(n.senderName),

                        csv(n.senderId),

                        csv(n.senderType),

                        n.favoriteContact,

                        n.notificationFrequency,

                        n.timestamp,

                        csv(n.dayOfWeek),

                        n.hourOfDay,

                        n.screenOn,

                        n.phoneLocked,

                        n.batteryLevel,

                        n.charging,

                        n.internetStatus,

                        n.doNotDisturb,

                        csv(n.foregroundApp),

                        csv(n.userActivity),

                        n.opened,

                        n.dismissed,

                        n.timeToOpen,

                        n.responseTime,

                        csv(n.priorityLabel),

                        n.priorityClass,

                        n.predictionConfidence

                    ).joinToString(",")

                )
            }

            writer.flush()
        }

        return uri
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
}