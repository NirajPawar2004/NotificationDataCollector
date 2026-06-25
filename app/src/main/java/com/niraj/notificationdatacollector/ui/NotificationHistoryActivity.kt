package com.niraj.notificationdatacollector.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.data.DatabaseProvider
import com.niraj.notificationdatacollector.data.NotificationRepository
import kotlinx.coroutines.launch

class NotificationHistoryActivity : AppCompatActivity() {

    private lateinit var repository: NotificationRepository

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: NotificationHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_history)

        recyclerView = findViewById(R.id.recyclerNotifications)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotificationHistoryAdapter()

        recyclerView.adapter = adapter

        repository = NotificationRepository(
            DatabaseProvider
                .getDatabase(this)
                .notificationDao()
        )

        loadNotifications()
    }

    private fun loadNotifications() {

        lifecycleScope.launch {

            val list = repository.getAll()

            adapter.submitList(list)
        }
    }
}