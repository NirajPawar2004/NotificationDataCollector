package com.niraj.notificationdatacollector.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.utils.ExportCsvHelper
import kotlinx.coroutines.launch

class CsvExportActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CsvExportActivity"
    }

    private lateinit var repository: NotificationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_csv_export)

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        val btnExport =
            findViewById<Button>(R.id.btnExport)

        btnExport.setOnClickListener {

            exportDataset()
        }
    }

    private fun exportDataset() {

        lifecycleScope.launch {

            try {

                Log.d(
                    TAG,
                    "Starting CSV Export..."
                )

                val uri: Uri =
                    ExportCsvHelper(
                        this@CsvExportActivity,
                        repository
                    ).export()

                Log.d(
                    TAG,
                    "CSV Export Success : $uri"
                )

                Toast.makeText(
                    this@CsvExportActivity,
                    "Dataset exported successfully.",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "CSV Export Failed",
                    e
                )

                Toast.makeText(
                    this@CsvExportActivity,
                    e.localizedMessage
                        ?: "CSV Export Failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}