package com.niraj.notificationdatacollector

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.niraj.notificationdatacollector.ui.DashboardActivity

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        if (!isNotificationServiceEnabled()) {

            startActivity(
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            )

        } else {

            openDashboard()
        }
    }

    override fun onResume() {
        super.onResume()

        if (isNotificationServiceEnabled()) {

            openDashboard()
        }
    }

    private fun openDashboard() {

        startActivity(
            Intent(
                this,
                DashboardActivity::class.java
            )
        )

        finish()
    }

    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {

        val enabledListeners =
            Settings.Secure.getString(
                contentResolver,
                "enabled_notification_listeners"
            ) ?: return false

        return enabledListeners.contains(
            ComponentName(
                this,
                com.niraj.notificationdatacollector.service.NotificationListenerService::class.java
            ).flattenToString()
        )
    }
}