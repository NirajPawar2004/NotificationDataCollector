package com.niraj.notificationdatacollector

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.appbar.MaterialToolbar
import com.niraj.notificationdatacollector.ui.CsvExportActivity
import com.niraj.notificationdatacollector.ui.DashboardActivity
import com.niraj.notificationdatacollector.ui.NotificationHistoryActivity
import com.niraj.notificationdatacollector.ui.SettingsActivity
import com.niraj.notificationdatacollector.ui.StatisticsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar

    private lateinit var dashboardCard: CardView
    private lateinit var historyCard: CardView
    private lateinit var analyticsCard: CardView
    private lateinit var exportCard: CardView
    private lateinit var settingsCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.app_name)

        dashboardCard = findViewById(R.id.cardDashboard)
        historyCard = findViewById(R.id.cardHistory)
        analyticsCard = findViewById(R.id.cardAnalytics)
        exportCard = findViewById(R.id.cardExport)
        settingsCard = findViewById(R.id.cardSettings)

        dashboardCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )
        }

        historyCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    NotificationHistoryActivity::class.java
                )
            )
        }

        analyticsCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    StatisticsActivity::class.java
                )
            )
        }

        exportCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CsvExportActivity::class.java
                )
            )
        }

        settingsCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }
    }
}