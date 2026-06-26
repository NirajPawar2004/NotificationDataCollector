package com.niraj.notificationdatacollector.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.utils.DatasetStatistics
import kotlinx.coroutines.launch

class StatisticsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "StatisticsActivity"
    }

    private lateinit var repository: NotificationRepository

    private lateinit var txtTotal: TextView
    private lateinit var txtUniqueApps: TextView
    private lateinit var txtAverage: TextView
    private lateinit var txtTopApps: TextView
    private lateinit var txtCategory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistics)

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        txtTotal = findViewById(R.id.txtTotal)
        txtUniqueApps = findViewById(R.id.txtUniqueApps)
        txtAverage = findViewById(R.id.txtAverage)
        txtTopApps = findViewById(R.id.txtTopApps)
        txtCategory = findViewById(R.id.txtCategory)

        loadStatistics()
    }

    private fun loadStatistics() {

        lifecycleScope.launch {

            try {

                val notifications =
                    repository.getAll()

                txtTotal.text =
                    DatasetStatistics
                        .totalNotifications(
                            notifications
                        )
                        .toString()

                txtUniqueApps.text =
                    DatasetStatistics
                        .uniqueApps(
                            notifications
                        )
                        .toString()

                txtAverage.text =
                    String.format(

                        "%.2f",

                        DatasetStatistics
                            .averageNotificationsPerDay(
                                notifications
                            )

                    )

                txtTopApps.text =
                    DatasetStatistics
                        .topApps(
                            notifications
                        )
                        .joinToString("\n") {

                            "${it.first} : ${it.second}"

                        }

                txtCategory.text =
                    DatasetStatistics
                        .categoryDistribution(
                            notifications
                        )
                        .entries
                        .joinToString("\n") {

                            "${it.key} : ${it.value}"

                        }

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Failed to load statistics",
                    e
                )
            }
        }
    }
}