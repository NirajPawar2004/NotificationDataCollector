package com.niraj.notificationdatacollector.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.utils.PreferencesManager
import com.niraj.notificationdatacollector.worker.WorkScheduler

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: PreferencesManager

    private lateinit var switchCollection: Switch
    private lateinit var switchAutoExport: Switch

    private lateinit var btnDaily: Button
    private lateinit var btnWeekly: Button
    private lateinit var btnMonthly: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        preferences = PreferencesManager(this)

        switchCollection = findViewById(R.id.switchCollection)
        switchAutoExport = findViewById(R.id.switchAutoExport)

        btnDaily = findViewById(R.id.btnDailyExport)
        btnWeekly = findViewById(R.id.btnWeeklyExport)
        btnMonthly = findViewById(R.id.btnMonthlyExport)
        btnCancel = findViewById(R.id.btnCancelExport)

        switchCollection.isChecked =
            preferences.collectionEnabled

        switchAutoExport.isChecked =
            preferences.autoExportEnabled

        switchCollection.setOnCheckedChangeListener { _, checked ->

            preferences.collectionEnabled = checked

            Toast.makeText(
                this,
                if (checked)
                    "Collection Enabled"
                else
                    "Collection Disabled",
                Toast.LENGTH_SHORT
            ).show()
        }

        switchAutoExport.setOnCheckedChangeListener { _, checked ->

            preferences.autoExportEnabled = checked
        }

        btnDaily.setOnClickListener {

            WorkScheduler.scheduleDailyExport(this)

            Toast.makeText(
                this,
                "Daily Export Scheduled",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnWeekly.setOnClickListener {

            WorkScheduler.scheduleWeeklyExport(this)

            Toast.makeText(
                this,
                "Weekly Export Scheduled",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnMonthly.setOnClickListener {

            WorkScheduler.scheduleMonthlyExport(this)

            Toast.makeText(
                this,
                "Monthly Export Scheduled",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnCancel.setOnClickListener {

            WorkScheduler.cancelExport(this)

            Toast.makeText(
                this,
                "Export Cancelled",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}