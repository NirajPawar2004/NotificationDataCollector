package com.niraj.notificationdatacollector.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {

    fun getExportDirectory(
        context: Context
    ): File {

        val directory = File(
            context.getExternalFilesDir(null),
            Constants.EXPORT_FOLDER
        )

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return directory
    }

    fun createCsvFile(
        context: Context
    ): File {

        val timestamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())

        return File(
            getExportDirectory(context),
            "notifications_$timestamp.csv"
        )
    }

    fun createJsonFile(
        context: Context
    ): File {

        val timestamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())

        return File(
            getExportDirectory(context),
            "notifications_$timestamp.json"
        )
    }

    fun exportFolderExists(
        context: Context
    ): Boolean {

        return getExportDirectory(context).exists()
    }

    fun listExportFiles(
        context: Context
    ): List<File> {

        return getExportDirectory(context)
            .listFiles()
            ?.sortedByDescending { it.lastModified() }
            ?: emptyList()
    }

    fun deleteFile(
        file: File
    ): Boolean {

        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    fun clearExportFolder(
        context: Context
    ): Int {

        val files = listExportFiles(context)

        var deleted = 0

        files.forEach {

            if (it.delete()) {
                deleted++
            }
        }

        return deleted
    }

    fun fileSizeMB(
        file: File
    ): Double {

        return file.length() / 1024.0 / 1024.0
    }

    fun storageAvailable(
        context: Context
    ): Boolean {

        return context.getExternalFilesDir(null) != null &&
                Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
    }

    fun totalExportSizeMB(
        context: Context
    ): Double {

        return listExportFiles(context)
            .sumOf { it.length() }
            .toDouble() / 1024.0 / 1024.0
    }

    fun latestExport(
        context: Context
    ): File? {

        return listExportFiles(context)
            .firstOrNull()
    }

    fun cleanupOldFiles(
        context: Context,
        keepLatest: Int = 10
    ) {

        val files = listExportFiles(context)

        if (files.size <= keepLatest)
            return

        files.drop(keepLatest).forEach {

            it.delete()
        }
    }

    fun readableSize(
        file: File
    ): String {

        val size = file.length()

        return when {

            size >= 1024L * 1024L * 1024L ->
                String.format(
                    Locale.getDefault(),
                    "%.2f GB",
                    size / 1024.0 / 1024.0 / 1024.0
                )

            size >= 1024L * 1024L ->
                String.format(
                    Locale.getDefault(),
                    "%.2f MB",
                    size / 1024.0 / 1024.0
                )

            size >= 1024L ->
                String.format(
                    Locale.getDefault(),
                    "%.2f KB",
                    size / 1024.0
                )

            else ->
                "$size B"
        }
    }
}