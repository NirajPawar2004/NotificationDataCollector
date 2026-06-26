package com.niraj.notificationdatacollector.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(

    // ==========================================
    // Primary Key
    // ==========================================

    @PrimaryKey(autoGenerate = true)
    val notificationId: Long = 0,

    // Android Notification Key
    val notificationKey: String,

    // ==========================================
    // User Information
    // ==========================================

    val userId: String = "USER_001",

    // ==========================================
    // Application Information
    // ==========================================

    val appName: String,

    val packageName: String,

    // ==========================================
    // Notification Information
    // ==========================================

    val notificationCategory: String,

    val notificationTitle: String,

    val notificationText: String,

    val senderName: String,

    val senderId: String,

    val senderType: String,

    val favoriteContact: Boolean,

    val notificationFrequency: Int,

    // ==========================================
    // Time Information
    // ==========================================

    val timestamp: Long,

    val dayOfWeek: String,

    val hourOfDay: Int,

    // ==========================================
    // Device Context
    // ==========================================

    val screenOn: Boolean,

    val phoneLocked: Boolean,

    val batteryLevel: Int,

    val charging: Boolean,

    val internetStatus: Boolean,

    val doNotDisturb: Boolean,

    // ==========================================
    // User Context
    // ==========================================

    val foregroundApp: String,

    val userActivity: String,

    // ==========================================
    // User Behaviour
    // ==========================================

    val opened: Boolean = false,

    val dismissed: Boolean = false,

    val timeToOpen: Long = -1L,

    val responseTime: Long = -1L,

    // ==========================================
    // AI Output
    // ==========================================

    val priorityLabel: String = "UNKNOWN",

    val priorityClass: Int = -1,

    val predictionConfidence: Float = 0f
)