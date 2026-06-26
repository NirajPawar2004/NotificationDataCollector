package com.niraj.notificationdatacollector.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.utils.ExportCsvHelper
import com.niraj.notificationdatacollector.utils.JsonExportHelper
import com.niraj.notificationdatacollector.utils.NotificationLogger
import com.niraj.notificationdatacollector.utils.PreferencesManager

class AutoExportWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(
    appContext,
    workerParams
) {

    override suspend fun doWork(): Result {

        return try {

            val repository =
                NotificationRepository(

                    DatabaseProvider
                        .getDatabase(applicationContext)
                        .notificationDao()

                )

            val csvUri: Uri =
                ExportCsvHelper(

                    applicationContext,

                    repository

                ).export()

            val jsonUri: Uri =
                JsonExportHelper(

                    applicationContext,

                    repository

                ).export()

            NotificationLogger.csvExport(
                csvUri.toString()
            )

            NotificationLogger.jsonExport(
                jsonUri.toString()
            )

            val preferences =
                PreferencesManager(
                    applicationContext
                )

            preferences.lastExportTime =
                System.currentTimeMillis()

            preferences.totalExported =
                preferences.totalExported + 1

            Result.success()

        } catch (e: Exception) {

            NotificationLogger.error(
                "Automatic export failed",
                e
            )

            Result.failure()
        }
    }
}