package com.niraj.notificationdatacollector.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import kotlinx.coroutines.launch

class NotificationDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }

    private lateinit var repository: NotificationRepository

    private lateinit var txtApp: TextView
    private lateinit var txtTitle: TextView
    private lateinit var txtText: TextView
    private lateinit var txtCategory: TextView
    private lateinit var txtPriority: TextView
    private lateinit var txtTime: TextView
    private lateinit var txtPackage: TextView
    private lateinit var txtDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_details)

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        txtApp = findViewById(R.id.txtApp)
        txtTitle = findViewById(R.id.txtTitle)
        txtText = findViewById(R.id.txtText)
        txtCategory = findViewById(R.id.txtCategory)
        txtPriority = findViewById(R.id.txtPriority)
        txtTime = findViewById(R.id.txtTime)
        txtPackage = findViewById(R.id.txtPackage)
        txtDetails = findViewById(R.id.txtDetails)

        val id = intent.getLongExtra(EXTRA_NOTIFICATION_ID, -1L)

        if (id != -1L) {
            loadNotification(id)
        }
    }

    private fun loadNotification(id: Long) {

        lifecycleScope.launch {

            val notification = repository.getById(id) ?: return@launch

            txtApp.text = notification.appName
            txtTitle.text = notification.title
            txtText.text = notification.text
            txtCategory.text = notification.category
            txtPriority.text = notification.priorityLabel
            txtTime.text = notification.formattedTime
            txtPackage.text = notification.packageName

            txtDetails.text = buildString {

                appendLine("Version : ${notification.versionName}")
                appendLine("Channel : ${notification.channelId}")
                appendLine("Battery : ${notification.batteryLevel}%")
                appendLine("Charging : ${notification.charging}")
                appendLine("WiFi : ${notification.wifiConnected}")
                appendLine("Mobile : ${notification.mobileDataConnected}")
                appendLine("Connection : ${notification.connectionType}")
                appendLine("Opened : ${notification.wasOpened}")
                appendLine("Dismissed : ${notification.wasDismissed}")
                appendLine("Response Time : ${notification.responseTime} ms")
            }
        }
    }
}