package com.niraj.notificationdatacollector.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    companion object {

        private const val PREF_NAME = "notification_collector_pref"

        private const val KEY_FIRST_LAUNCH = "first_launch"

        private const val KEY_COLLECTION_ENABLED = "collection_enabled"

        private const val KEY_NOTIFICATION_PERMISSION = "notification_permission"

        private const val KEY_USAGE_PERMISSION = "usage_permission"

        private const val KEY_AUTO_EXPORT = "auto_export"

        private const val KEY_EXPORT_PATH = "export_path"

        private const val KEY_DATASET_VERSION = "dataset_version"

        private const val KEY_LAST_EXPORT_TIME = "last_export_time"

        private const val KEY_TOTAL_EXPORTED = "total_exported"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

    var firstLaunch: Boolean
        get() = preferences.getBoolean(KEY_FIRST_LAUNCH, true)
        set(value) {
            preferences.edit().putBoolean(
                KEY_FIRST_LAUNCH,
                value
            ).apply()
        }

    var collectionEnabled: Boolean
        get() = preferences.getBoolean(
            KEY_COLLECTION_ENABLED,
            true
        )
        set(value) {
            preferences.edit().putBoolean(
                KEY_COLLECTION_ENABLED,
                value
            ).apply()
        }

    var notificationPermissionGranted: Boolean
        get() = preferences.getBoolean(
            KEY_NOTIFICATION_PERMISSION,
            false
        )
        set(value) {
            preferences.edit().putBoolean(
                KEY_NOTIFICATION_PERMISSION,
                value
            ).apply()
        }

    var usagePermissionGranted: Boolean
        get() = preferences.getBoolean(
            KEY_USAGE_PERMISSION,
            false
        )
        set(value) {
            preferences.edit().putBoolean(
                KEY_USAGE_PERMISSION,
                value
            ).apply()
        }

    var autoExportEnabled: Boolean
        get() = preferences.getBoolean(
            KEY_AUTO_EXPORT,
            false
        )
        set(value) {
            preferences.edit().putBoolean(
                KEY_AUTO_EXPORT,
                value
            ).apply()
        }

    var exportPath: String
        get() = preferences.getString(
            KEY_EXPORT_PATH,
            ""
        ) ?: ""
        set(value) {
            preferences.edit().putString(
                KEY_EXPORT_PATH,
                value
            ).apply()
        }

    var datasetVersion: Int
        get() = preferences.getInt(
            KEY_DATASET_VERSION,
            1
        )
        set(value) {
            preferences.edit().putInt(
                KEY_DATASET_VERSION,
                value
            ).apply()
        }

    var lastExportTime: Long
        get() = preferences.getLong(
            KEY_LAST_EXPORT_TIME,
            0L
        )
        set(value) {
            preferences.edit().putLong(
                KEY_LAST_EXPORT_TIME,
                value
            ).apply()
        }

    var totalExported: Int
        get() = preferences.getInt(
            KEY_TOTAL_EXPORTED,
            0
        )
        set(value) {
            preferences.edit().putInt(
                KEY_TOTAL_EXPORTED,
                value
            ).apply()
        }

    fun clearAll() {
        preferences.edit().clear().apply()
    }
}