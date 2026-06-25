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

        val notifications = repository.getAll()

        val resolver = context.contentResolver

        val fileName = "notifications_dataset.json"

        // Delete old JSON export if it exists
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

                val id = cursor.getLong(idIndex)

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

        val values = ContentValues().apply {

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
                Environment.DIRECTORY_DOWNLOADS + "/NotificationDataset"
            )
        }

        val uri = resolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw Exception("Unable to create JSON file.")

        resolver.openOutputStream(uri)?.use { outputStream ->

            val writer = OutputStreamWriter(outputStream)

            val jsonArray = JSONArray()

            notifications.forEach { n ->

                val json = JSONObject()

                json.put("id", n.id)

                json.put("timestampMillis", n.timestampMillis)
                json.put("formattedTime", n.formattedTime)
                json.put("date", n.date)
                json.put("dayOfWeek", n.dayOfWeek)
                json.put("month", n.month)
                json.put("year", n.year)
                json.put("hour", n.hour)
                json.put("minute", n.minute)
                json.put("second", n.second)

                json.put("appName", n.appName)
                json.put("packageName", n.packageName)
                json.put("versionName", n.versionName)
                json.put("versionCode", n.versionCode)
                json.put("isSystemApp", n.isSystemApp)

                json.put("title", n.title)
                json.put("text", n.text)
                json.put("bigText", n.bigText)
                json.put("subText", n.subText)
                json.put("conversationTitle", n.conversationTitle)

                json.put("category", n.category)
                json.put("channelId", n.channelId)

                json.put("priority", n.priority)
                json.put("visibility", n.visibility)

                json.put("isOngoing", n.isOngoing)
                json.put("isClearable", n.isClearable)

                json.put("notificationNumber", n.notificationNumber)
                json.put("postTime", n.postTime)

                json.put("screenOn", n.screenOn)
                json.put("screenOrientation", n.screenOrientation)

                json.put("batteryLevel", n.batteryLevel)
                json.put("charging", n.charging)
                json.put("batterySaver", n.batterySaver)

                json.put("ringerMode", n.ringerMode)

                json.put("mediaVolume", n.mediaVolume)
                json.put("notificationVolume", n.notificationVolume)
                json.put("alarmVolume", n.alarmVolume)

                json.put("internetAvailable", n.internetAvailable)
                json.put("wifiConnected", n.wifiConnected)
                json.put("mobileDataConnected", n.mobileDataConnected)
                json.put("connectionType", n.connectionType)

                json.put("priorityLabel", n.priorityLabel)
                json.put("userAction", n.userAction)
                json.put("responseTime", n.responseTime)
                json.put("wasOpened", n.wasOpened)
                json.put("wasDismissed", n.wasDismissed)

                json.put("notes", n.notes)

                jsonArray.put(json)
            }

            writer.write(
                jsonArray.toString(4)
            )

            writer.flush()
            writer.close()
        }

        return uri
    }
}