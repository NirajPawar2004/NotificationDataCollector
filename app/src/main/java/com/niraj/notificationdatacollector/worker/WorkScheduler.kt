package com.niraj.notificationdatacollector.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkScheduler {

    private const val EXPORT_WORK_NAME =
        "notification_dataset_export"

    fun scheduleDailyExport(
        context: Context
    ) {

        val request =
            PeriodicWorkRequestBuilder<AutoExportWorker>(
                1,
                TimeUnit.DAYS
            )
                .setConstraints(defaultConstraints())
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    15,
                    TimeUnit.MINUTES
                )
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                EXPORT_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    fun scheduleWeeklyExport(
        context: Context
    ) {

        val request =
            PeriodicWorkRequestBuilder<AutoExportWorker>(
                7,
                TimeUnit.DAYS
            )
                .setConstraints(defaultConstraints())
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                EXPORT_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    fun scheduleMonthlyExport(
        context: Context
    ) {

        val request =
            PeriodicWorkRequestBuilder<AutoExportWorker>(
                30,
                TimeUnit.DAYS
            )
                .setConstraints(defaultConstraints())
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                EXPORT_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    fun cancelExport(
        context: Context
    ) {

        WorkManager.getInstance(context)
            .cancelUniqueWork(EXPORT_WORK_NAME)
    }

    private fun defaultConstraints(): Constraints {

        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .setRequiresCharging(false)
            .build()
    }
}