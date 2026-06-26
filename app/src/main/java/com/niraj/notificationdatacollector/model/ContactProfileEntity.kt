package com.niraj.notificationdatacollector.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_profiles")
data class ContactProfileEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val senderName: String,

    val senderPhone: String = "",

    val senderType: String = "UNKNOWN",

    val isFavoriteContact: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)