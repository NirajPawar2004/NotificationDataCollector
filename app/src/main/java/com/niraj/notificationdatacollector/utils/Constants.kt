package com.niraj.notificationdatacollector.utils

object Constants {

    // =========================================
    // Database
    // =========================================

    const val DATABASE_NAME = "notification_database"

    const val DATABASE_VERSION = 1

    const val TABLE_NOTIFICATION = "notifications"

    // =========================================
    // CSV Export
    // =========================================

    const val EXPORT_FOLDER = "exports"

    const val CSV_FILE_NAME = "notifications_dataset.csv"

    const val JSON_FILE_NAME = "notifications_dataset.json"

    // =========================================
    // Shared Preferences
    // =========================================

    const val PREF_NAME = "notification_collector_pref"

    // =========================================
    // AI Labels
    // =========================================

    const val LABEL_HIGH = "HIGH"

    const val LABEL_MEDIUM = "MEDIUM"

    const val LABEL_LOW = "LOW"

    // =========================================
    // Notification Categories
    // =========================================

    const val CATEGORY_MESSAGE = "message"

    const val CATEGORY_CALL = "call"

    const val CATEGORY_EMAIL = "email"

    const val CATEGORY_EVENT = "event"

    const val CATEGORY_ALARM = "alarm"

    const val CATEGORY_REMINDER = "reminder"

    const val CATEGORY_PROGRESS = "progress"

    // =========================================
    // Intent Actions
    // =========================================

    const val ACTION_EXPORT_DATASET =
        "com.niraj.notificationdatacollector.EXPORT_DATASET"

    const val ACTION_REFRESH_DATASET =
        "com.niraj.notificationdatacollector.REFRESH_DATASET"

    // =========================================
    // Request Codes
    // =========================================

    const val REQUEST_NOTIFICATION_PERMISSION = 1001

    const val REQUEST_USAGE_PERMISSION = 1002

    const val REQUEST_STORAGE_PERMISSION = 1003

    // =========================================
    // Export Formats
    // =========================================

    const val FORMAT_CSV = "CSV"

    const val FORMAT_JSON = "JSON"

    // =========================================
    // Logging
    // =========================================

    const val LOG_TAG = "NotificationCollector"

    // =========================================
    // Dataset
    // =========================================

    const val DATASET_VERSION = 1

    const val MAX_EXPORT_ROWS = 1_000_000

    // =========================================
    // Time
    // =========================================

    const val ONE_SECOND = 1000L

    const val ONE_MINUTE = 60_000L

    const val ONE_HOUR = 3_600_000L

    const val ONE_DAY = 86_400_000L

    // =========================================
    // Default Values
    // =========================================

    const val UNKNOWN = "UNKNOWN"

    const val EMPTY = ""

    const val NONE = "NONE"

    // =========================================
    // AI Priority Score
    // =========================================

    const val PRIORITY_MIN = 0

    const val PRIORITY_MAX = 100

    const val HIGH_PRIORITY_SCORE = 70

    const val MEDIUM_PRIORITY_SCORE = 40

    // =========================================
    // Dataset Columns
    // =========================================

    val CSV_HEADER = listOf(

        "id",
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
    )
}