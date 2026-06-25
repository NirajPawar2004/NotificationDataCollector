package com.niraj.notificationdatacollector.collector

import android.app.Notification
import android.os.Bundle
import android.service.notification.StatusBarNotification

object NotificationParser {

    private fun extras(sbn: StatusBarNotification): Bundle {
        return sbn.notification.extras
    }

    fun getPackageName(sbn: StatusBarNotification): String {
        return sbn.packageName
    }

    fun getTitle(sbn: StatusBarNotification): String? {
        return extras(sbn).getString(Notification.EXTRA_TITLE)
    }

    fun getText(sbn: StatusBarNotification): String? {
        return extras(sbn)
            .getCharSequence(Notification.EXTRA_TEXT)
            ?.toString()
    }

    fun getBigText(sbn: StatusBarNotification): String? {
        return extras(sbn)
            .getCharSequence(Notification.EXTRA_BIG_TEXT)
            ?.toString()
    }

    fun getSubText(sbn: StatusBarNotification): String? {
        return extras(sbn)
            .getCharSequence(Notification.EXTRA_SUB_TEXT)
            ?.toString()
    }

    fun getConversationTitle(sbn: StatusBarNotification): String? {
        return extras(sbn)
            .getCharSequence(Notification.EXTRA_CONVERSATION_TITLE)
            ?.toString()
    }

    fun getCategory(sbn: StatusBarNotification): String? {
        return sbn.notification.category
    }

    fun getChannelId(sbn: StatusBarNotification): String? {
        return sbn.notification.channelId
    }

    fun getPriority(sbn: StatusBarNotification): Int {
        @Suppress("DEPRECATION")
        return sbn.notification.priority
    }

    fun getVisibility(sbn: StatusBarNotification): Int {
        return sbn.notification.visibility
    }

    fun isOngoing(sbn: StatusBarNotification): Boolean {
        return sbn.isOngoing
    }

    fun isClearable(sbn: StatusBarNotification): Boolean {
        return sbn.isClearable
    }

    fun getNotificationNumber(sbn: StatusBarNotification): Int {
        return sbn.notification.number
    }

    fun getPostTime(sbn: StatusBarNotification): Long {
        return sbn.postTime
    }
}