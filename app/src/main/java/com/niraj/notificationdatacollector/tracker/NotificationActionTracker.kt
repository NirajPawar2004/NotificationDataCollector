package com.niraj.notificationdatacollector.tracker

import android.service.notification.StatusBarNotification
import java.util.concurrent.ConcurrentHashMap

object NotificationActionTracker {

    data class NotificationSession(
        val key: String,
        val packageName: String,
        val postedTime: Long,
        var removedTime: Long = 0L,
        var responseTime: Long = 0L,
        var wasOpened: Boolean = false,
        var wasDismissed: Boolean = false
    )

    private val activeNotifications =
        ConcurrentHashMap<String, NotificationSession>()

    fun notificationPosted(
        sbn: StatusBarNotification
    ) {

        activeNotifications[sbn.key] = NotificationSession(
            key = sbn.key,
            packageName = sbn.packageName,
            postedTime = System.currentTimeMillis()
        )
    }

    fun notificationRemoved(
        sbn: StatusBarNotification
    ): NotificationSession? {

        val session =
            activeNotifications.remove(sbn.key)
                ?: return null

        session.removedTime = System.currentTimeMillis()

        session.responseTime =
            session.removedTime - session.postedTime

        session.wasDismissed = true

        return session
    }

    fun notificationOpened(
        key: String
    ) {

        activeNotifications[key]?.apply {

            wasOpened = true

            responseTime =
                System.currentTimeMillis() - postedTime
        }
    }

    fun isTracked(
        key: String
    ): Boolean {

        return activeNotifications.containsKey(key)
    }

    fun activeCount(): Int {

        return activeNotifications.size
    }

    fun clear() {

        activeNotifications.clear()
    }

    fun getSession(
        key: String
    ): NotificationSession? {

        return activeNotifications[key]
    }

    fun getAllSessions(): List<NotificationSession> {

        return activeNotifications.values.toList()
    }
}