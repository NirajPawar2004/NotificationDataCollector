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

        val notifications = repository.getAll()

        val resolver = context.contentResolver

        val fileName = "notifications_dataset.csv"

        // Delete previous export if it exists
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
                "text/csv"
            )

            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOWNLOADS + "/NotificationDataset"
            )
        }

        val uri = resolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw Exception("Unable to create CSV file.")

        resolver.openOutputStream(uri)?.use { outputStream ->

            val writer = OutputStreamWriter(outputStream)

            writer.appendLine(
                "id,timestampMillis,formattedTime,date,dayOfWeek,month,year,hour,minute,second," +
                        "appName,packageName,versionName,versionCode,isSystemApp," +
                        "title,text,bigText,subText,conversationTitle," +
                        "category,channelId,priority,visibility,isOngoing,isClearable," +
                        "notificationNumber,postTime," +
                        "screenOn,screenOrientation,batteryLevel,charging,batterySaver," +
                        "ringerMode,mediaVolume,notificationVolume,alarmVolume," +
                        "internetAvailable,wifiConnected,mobileDataConnected,connectionType," +
                        "priorityLabel,userAction,responseTime,wasOpened,wasDismissed,notes"
            )

            notifications.forEach { n ->

                writer.appendLine(

                    listOf(

                        n.id,
                        n.timestampMillis,

                        csv(n.formattedTime),
                        csv(n.date),
                        csv(n.dayOfWeek),
                        csv(n.month),

                        n.year,
                        n.hour,
                        n.minute,
                        n.second,

                        csv(n.appName),
                        csv(n.packageName),
                        csv(n.versionName),

                        n.versionCode,
                        n.isSystemApp,

                        csv(n.title),
                        csv(n.text),
                        csv(n.bigText),
                        csv(n.subText),
                        csv(n.conversationTitle),

                        csv(n.category),
                        csv(n.channelId),

                        n.priority,
                        n.visibility,

                        n.isOngoing,
                        n.isClearable,

                        n.notificationNumber,
                        n.postTime,

                        n.screenOn,
                        csv(n.screenOrientation),

                        n.batteryLevel,
                        n.charging,
                        n.batterySaver,

                        csv(n.ringerMode),

                        n.mediaVolume,
                        n.notificationVolume,
                        n.alarmVolume,

                        n.internetAvailable,
                        n.wifiConnected,
                        n.mobileDataConnected,

                        csv(n.connectionType),

                        csv(n.priorityLabel),
                        csv(n.userAction),

                        n.responseTime,
                        n.wasOpened,
                        n.wasDismissed,

                        csv(n.notes)

                    ).joinToString(",")

                )
            }

            writer.flush()
            writer.close()
        }

        return uri
    }

    private fun csv(value: Any?): String {

        return "\"" +
                (value?.toString()?.replace("\"", "\"\"") ?: "") +
                "\""
    }
}