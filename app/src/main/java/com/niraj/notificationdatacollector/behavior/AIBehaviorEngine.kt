package com.niraj.notificationdatacollector.behavior

object AIBehaviorEngine {

    private val activeNotifications =
        mutableMapOf<String, NotificationSession>()

    fun notificationPosted(
        session: NotificationSession
    ) {

        activeNotifications[
            session.notificationKey
        ] = session
    }

    fun notificationOpened(
        notificationKey: String
    ): NotificationSession? {

        val session =
            activeNotifications[
                notificationKey
            ] ?: return null

        session.opened = true

        session.dismissed = false

        session.responseTime =
            System.currentTimeMillis() -
                    session.postedTime

        return session
    }

    fun notificationRemoved(
        notificationKey: String
    ): NotificationSession? {

        val session =
            activeNotifications.remove(
                notificationKey
            ) ?: return null

        session.removedTime =
            System.currentTimeMillis()

        if (!session.opened) {

            session.dismissed = true

            session.responseTime =
                session.removedTime -
                        session.postedTime
        }

        return session
    }

    fun getActiveNotification(
        notificationKey: String
    ): NotificationSession? {

        return activeNotifications[
            notificationKey
        ]
    }

    fun getAllActiveNotifications():
            List<NotificationSession> {

        return activeNotifications
            .values
            .toList()
    }

    fun clearNotification(
        notificationKey: String
    ) {

        activeNotifications.remove(
            notificationKey
        )
    }

    fun clearAll() {

        activeNotifications.clear()
    }
}