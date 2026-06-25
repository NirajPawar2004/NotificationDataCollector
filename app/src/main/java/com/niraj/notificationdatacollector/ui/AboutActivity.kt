package com.niraj.notificationdatacollector.ui

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.niraj.notificationdatacollector.R

class AboutActivity : AppCompatActivity() {

    private lateinit var txtVersion: TextView
    private lateinit var txtProject: TextView
    private lateinit var txtDescription: TextView
    private lateinit var txtDevice: TextView
    private lateinit var txtAndroid: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)

        txtVersion = findViewById(R.id.txtVersion)
        txtProject = findViewById(R.id.txtProject)
        txtDescription = findViewById(R.id.txtDescription)
        txtDevice = findViewById(R.id.txtDevice)
        txtAndroid = findViewById(R.id.txtAndroid)

        txtProject.text = "Notification Collector V2.0"

        txtVersion.text = "Version 1.0"

        txtDevice.text =
            "${Build.MANUFACTURER} ${Build.MODEL}"

        txtAndroid.text =
            "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

        txtDescription.text = """
Intelligent Notification Prioritization AI

This application collects smartphone notification data,
device context and user interactions for building an AI
model that predicts notification priority.
        """.trimIndent()
    }
}