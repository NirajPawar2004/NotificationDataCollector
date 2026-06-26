package com.niraj.notificationdatacollector.behavior

data class NotificationSession(

    val notificationKey: String,

    val packageName: String,

    val senderName: String,

    val postedTime: Long,

    var removedTime: Long = 0L,

    var opened: Boolean = false,

    var dismissed: Boolean = false,

    var responseTime: Long = -1L

)