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

    private lateinit var repository: NotificationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_csv_export)

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        val exportButton =
            findViewById<Button>(R.id.btnExport)

        exportButton.setOnClickListener {

            lifecycleScope.launch {

                try {

                    Log.d("EXPORT", "CSV Export Started")

                    val uri: Uri =
                        ExportCsvHelper(
                            this@CsvExportActivity,
                            repository
                        ).export()

                    Log.d(
                        "EXPORT",
                        "CSV Export Success : $uri"
                    )

                    Toast.makeText(
                        this@CsvExportActivity,
                        "CSV exported successfully.\nSaved in Downloads/NotificationDataset",
                        Toast.LENGTH_LONG
                    ).show()

                } catch (e: Exception) {

                    Log.e(
                        "EXPORT",
                        "CSV Export Failed",
                        e
                    )

                    Toast.makeText(
                        this@CsvExportActivity,
                        e.localizedMessage ?: "CSV Export Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}