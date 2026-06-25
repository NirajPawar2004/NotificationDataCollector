package com.niraj.notificationdatacollector.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val settingsIntent = Intent(
                Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
            )

            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(settingsIntent)
        }
    }
}