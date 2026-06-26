package com.niraj.notificationdatacollector.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationDetailsActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "NotificationDetails"

        const val EXTRA_NOTIFICATION_ID =
            "notification_id"
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

        setContentView(
            R.layout.activity_notification_details
        )

        repository =
            NotificationRepository(

                DatabaseProvider
                    .getDatabase(this)
                    .notificationDao()

            )

        txtApp =
            findViewById(R.id.txtApp)

        txtTitle =
            findViewById(R.id.txtTitle)

        txtText =
            findViewById(R.id.txtText)

        txtCategory =
            findViewById(R.id.txtCategory)

        txtPriority =
            findViewById(R.id.txtPriority)

        txtTime =
            findViewById(R.id.txtTime)

        txtPackage =
            findViewById(R.id.txtPackage)

        txtDetails =
            findViewById(R.id.txtDetails)

        val notificationId =
            intent.getLongExtra(
                EXTRA_NOTIFICATION_ID,
                -1L
            )

        if (notificationId != -1L) {

            loadNotification(
                notificationId
            )
        }
    }

    private fun loadNotification(
        notificationId: Long
    ) {

        lifecycleScope.launch {

            try {

                val notification =
                    repository.getById(
                        notificationId
                    ) ?: return@launch

                txtApp.text =
                    notification.appName

                txtTitle.text =
                    notification.notificationTitle

                txtText.text =
                    notification.notificationText

                txtCategory.text =
                    notification.notificationCategory

                txtPriority.text =
                    notification.priorityLabel

                txtPackage.text =
                    notification.packageName

                txtTime.text =
                    SimpleDateFormat(
                        "dd MMM yyyy  HH:mm:ss",
                        Locale.getDefault()
                    ).format(
                        Date(
                            notification.timestamp
                        )
                    )

                txtDetails.text =
                    buildString {

                        appendLine("Notification ID : ${notification.notificationId}")

                        appendLine("Notification Key : ${notification.notificationKey}")

                        appendLine("Sender : ${notification.senderName}")

                        appendLine("Sender Type : ${notification.senderType}")

                        appendLine("Favorite Contact : ${notification.favoriteContact}")

                        appendLine("Notification Frequency : ${notification.notificationFrequency}")

                        appendLine("Screen On : ${notification.screenOn}")

                        appendLine("Phone Locked : ${notification.phoneLocked}")

                        appendLine("Battery Level : ${notification.batteryLevel}%")

                        appendLine("Charging : ${notification.charging}")

                        appendLine("Internet : ${notification.internetStatus}")

                        appendLine("Do Not Disturb : ${notification.doNotDisturb}")

                        appendLine("Foreground App : ${notification.foregroundApp}")

                        appendLine("User Activity : ${notification.userActivity}")

                        appendLine("Opened : ${notification.opened}")

                        appendLine("Dismissed : ${notification.dismissed}")

                        appendLine("Time To Open : ${notification.timeToOpen} ms")

                        appendLine("Response Time : ${notification.responseTime} ms")

                        appendLine("Priority Label : ${notification.priorityLabel}")

                        appendLine("Priority Class : ${notification.priorityClass}")

                        appendLine("Prediction Confidence : ${notification.predictionConfidence}")
                    }

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Failed to load notification",
                    e
                )
            }
        }
    }
}