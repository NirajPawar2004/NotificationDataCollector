package com.niraj.notificationdatacollector.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.utils.ExportCsvHelper
import com.niraj.notificationdatacollector.utils.JsonExportHelper
import com.niraj.notificationdatacollector.utils.PreferencesManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var repository: NotificationRepository
    private lateinit var preferences: PreferencesManager

    private lateinit var txtTotal: TextView
    private lateinit var txtApps: TextView
    private lateinit var txtToday: TextView
    private lateinit var txtLastExport: TextView

    private lateinit var btnExportCsv: Button
    private lateinit var btnExportJson: Button
    private lateinit var btnHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        preferences = PreferencesManager(this)

        txtTotal = findViewById(R.id.txtTotalNotifications)
        txtApps = findViewById(R.id.txtUniqueApps)
        txtToday = findViewById(R.id.txtTodayNotifications)
        txtLastExport = findViewById(R.id.txtLastExport)

        btnExportCsv = findViewById(R.id.btnExportCsv)
        btnExportJson = findViewById(R.id.btnExportJson)
        btnHistory = findViewById(R.id.btnHistory)

        loadStatistics()

        btnExportCsv.setOnClickListener {

            lifecycleScope.launch {

                try {

                    Log.d("EXPORT", "CSV Export Started")

                    val uri: Uri =
                        ExportCsvHelper(
                            this@DashboardActivity,
                            repository
                        ).export()

                    Log.d("EXPORT", "CSV Export Success : $uri")

                    preferences.lastExportTime =
                        System.currentTimeMillis()

                    Toast.makeText(
                        this@DashboardActivity,
                        "CSV exported successfully.\nSaved in Downloads/NotificationDataset",
                        Toast.LENGTH_LONG
                    ).show()

                    loadStatistics()

                } catch (e: Exception) {

                    Log.e("EXPORT", "CSV Export Failed", e)

                    Toast.makeText(
                        this@DashboardActivity,
                        e.localizedMessage ?: "CSV Export Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnExportJson.setOnClickListener {

            lifecycleScope.launch {

                try {

                    Log.d("EXPORT", "JSON Export Started")

                    val uri: Uri =
                        JsonExportHelper(
                            this@DashboardActivity,
                            repository
                        ).export()

                    Log.d("EXPORT", "JSON Export Success : $uri")

                    preferences.lastExportTime =
                        System.currentTimeMillis()

                    Toast.makeText(
                        this@DashboardActivity,
                        "JSON exported successfully.\nSaved in Downloads/NotificationDataset",
                        Toast.LENGTH_LONG
                    ).show()

                    loadStatistics()

                } catch (e: Exception) {

                    Log.e("EXPORT", "JSON Export Failed", e)

                    Toast.makeText(
                        this@DashboardActivity,
                        e.localizedMessage ?: "JSON Export Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    NotificationHistoryActivity::class.java
                )
            )
        }
    }

    private fun loadStatistics() {

        lifecycleScope.launch {

            txtTotal.text =
                repository.getCount().toString()

            txtApps.text =
                repository.getUniqueApps().toString()

            txtToday.text =
                repository.getTodayCount(
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(Date())
                ).toString()

            txtLastExport.text =
                if (preferences.lastExportTime == 0L) {
                    "Never"
                } else {
                    SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    ).format(
                        Date(preferences.lastExportTime)
                    )
                }
        }
    }
}