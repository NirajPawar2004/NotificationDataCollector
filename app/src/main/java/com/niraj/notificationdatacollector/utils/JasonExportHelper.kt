package com.niraj.notificationdatacollector.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.niraj.notificationdatacollector.data.NotificationRepository
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter

class JsonExportHelper(

    private val context: Context,

    private val repository: NotificationRepository

) {

    suspend fun export(): Uri {

        val notifications =
            repository.getAll()

        val resolver =
            context.contentResolver

        val fileName =
            "notification_dataset_v3.json"

        // Delete previous export

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
                    "application/json"
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
                "Unable to create JSON file."
            )

        resolver.openOutputStream(uri)?.use { stream ->

            val writer =
                OutputStreamWriter(stream)

            val jsonArray =
                JSONArray()

            notifications.forEach { n ->

                val json =
                    JSONObject()

                json.put(
                    "notificationId",
                    n.notificationId
                )

                json.put(
                    "notificationKey",
                    n.notificationKey
                )

                json.put(
                    "userId",
                    n.userId
                )

                json.put(
                    "appName",
                    n.appName
                )

                json.put(
                    "packageName",
                    n.packageName
                )

                json.put(
                    "notificationCategory",
                    n.notificationCategory
                )

                json.put(
                    "notificationTitle",
                    n.notificationTitle
                )

                json.put(
                    "notificationText",
                    n.notificationText
                )

                json.put(
                    "senderName",
                    n.senderName
                )

                json.put(
                    "senderId",
                    n.senderId
                )

                json.put(
                    "senderType",
                    n.senderType
                )

                json.put(
                    "favoriteContact",
                    n.favoriteContact
                )

                json.put(
                    "notificationFrequency",
                    n.notificationFrequency
                )

                json.put(
                    "timestamp",
                    n.timestamp
                )

                json.put(
                    "dayOfWeek",
                    n.dayOfWeek
                )

                json.put(
                    "hourOfDay",
                    n.hourOfDay
                )

                json.put(
                    "screenOn",
                    n.screenOn
                )

                json.put(
                    "phoneLocked",
                    n.phoneLocked
                )

                json.put(
                    "batteryLevel",
                    n.batteryLevel
                )

                json.put(
                    "charging",
                    n.charging
                )

                json.put(
                    "internetStatus",
                    n.internetStatus
                )

                json.put(
                    "doNotDisturb",
                    n.doNotDisturb
                )

                json.put(
                    "foregroundApp",
                    n.foregroundApp
                )

                json.put(
                    "userActivity",
                    n.userActivity
                )

                json.put(
                    "opened",
                    n.opened
                )

                json.put(
                    "dismissed",
                    n.dismissed
                )

                json.put(
                    "timeToOpen",
                    n.timeToOpen
                )

                json.put(
                    "responseTime",
                    n.responseTime
                )

                json.put(
                    "priorityLabel",
                    n.priorityLabel
                )

                json.put(
                    "priorityClass",
                    n.priorityClass
                )

                json.put(
                    "predictionConfidence",
                    n.predictionConfidence
                )

                jsonArray.put(json)
            }

            writer.write(
                jsonArray.toString(4)
            )

            writer.flush()
        }

        return uri
    }
}