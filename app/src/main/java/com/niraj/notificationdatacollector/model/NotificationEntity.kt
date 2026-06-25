package com.niraj.notificationdatacollector.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(

    // =========================
    // Primary Key
    // =========================

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // =========================
    // Time Information
    // =========================

    val timestampMillis: Long,

    val formattedTime: String,

    val date: String,

    val dayOfWeek: String,

    val month: String,

    val year: Int,

    val hour: Int,

    val minute: Int,

    val second: Int,

    // =========================
    // Application Information
    // =========================

    val appName: String,

    val packageName: String,

    val versionName: String,

    val versionCode: Long,

    val isSystemApp: Boolean,

    // =========================
    // Notification Information
    // =========================

    val title: String?,

    val text: String?,

    val bigText: String?,

    val subText: String?,

    val conversationTitle: String?,

    val category: String?,

    val channelId: String?,

    val priority: Int,

    val visibility: Int,

    val isOngoing: Boolean,

    val isClearable: Boolean,

    val notificationNumber: Int,

    val postTime: Long,

    // =========================
    // Device Information
    // =========================

    val screenOn: Boolean,

    val screenOrientation: String,

    val batteryLevel: Int,

    val charging: Boolean,

    val batterySaver: Boolean,

    val ringerMode: String,

    val mediaVolume: Int,

    val notificationVolume: Int,

    val alarmVolume: Int,

    // =========================
    // Network Information
    // =========================

    val internetAvailable: Boolean,

    val wifiConnected: Boolean,

    val mobileDataConnected: Boolean,

    val connectionType: String,

    // =========================
    // AI Training Fields
    // =========================

    val priorityLabel: String = "",

    val userAction: String = "",

    val responseTime: Long = 0L,

    val wasOpened: Boolean = false,

    val wasDismissed: Boolean = false,

    val notes: String = ""
)