package com.niraj.notificationdatacollector.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.niraj.notificationdatacollector.behavior.AIBehaviorEngine
import com.niraj.notificationdatacollector.behavior.NotificationSession
import com.niraj.notificationdatacollector.collector.NotificationCollector
import com.niraj.notificationdatacollector.data.ContactProfileRepository
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationListenerService : NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationCollector"
    }

    private val serviceScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO
        )

    private val database by lazy {
        DatabaseProvider.getDatabase(applicationContext)
    }

    private val notificationRepository by lazy {
        NotificationRepository(
            database.notificationDao()
        )
    }

    private val contactRepository by lazy {
        ContactProfileRepository(
            database.contactProfileDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service Created")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Listener Connected")
    }

    override fun onNotificationPosted(
        sbn: StatusBarNotification
    ) {

        serviceScope.launch {

            try {

                val entity =
                    NotificationCollector.collect(
                        context = applicationContext,
                        sbn = sbn,
                        contactRepository = contactRepository
                    )

                notificationRepository.insert(entity)

                AIBehaviorEngine.notificationPosted(

                    NotificationSession(

                        notificationKey = sbn.key,

                        packageName = sbn.packageName,

                        senderName = entity.senderName,

                        postedTime = System.currentTimeMillis()

                    )
                )

                Log.d(
                    TAG,
                    "Notification Saved : ${entity.appName}"
                )

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Insert Failed",
                    e
                )
            }
        }
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification
    ) {

        AIBehaviorEngine.notificationRemoved(
            sbn.key
        )

        Log.d(
            TAG,
            "Notification Removed : ${sbn.packageName}"
        )

        super.onNotificationRemoved(sbn)
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "Listener Disconnected")
    }

    override fun onDestroy() {
        super.onDestroy()

        AIBehaviorEngine.clearAll()

        Log.d(TAG, "Service Destroyed")
    }
}