package com.niraj.notificationdatacollector.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.model.NotificationEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationHistoryAdapter :
    RecyclerView.Adapter<NotificationHistoryAdapter.ViewHolder>() {

    private val notifications =
        mutableListOf<NotificationEntity>()

    fun submitList(
        list: List<NotificationEntity>
    ) {

        notifications.clear()

        notifications.addAll(list)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_notification,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        holder.bind(
            notifications[position]
        )
    }

    override fun getItemCount(): Int {

        return notifications.size
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val txtApp =
            itemView.findViewById<TextView>(R.id.txtApp)

        private val txtTitle =
            itemView.findViewById<TextView>(R.id.txtTitle)

        private val txtText =
            itemView.findViewById<TextView>(R.id.txtText)

        private val txtTime =
            itemView.findViewById<TextView>(R.id.txtTime)

        fun bind(
            notification: NotificationEntity
        ) {

            txtApp.text =
                notification.appName

            txtTitle.text =
                notification.notificationTitle

            txtText.text =
                buildString {

                    append(notification.notificationText)

                    if (
                        notification.senderName.isNotBlank()
                    ) {

                        append("\n")

                        append("Sender : ")

                        append(notification.senderName)
                    }

                    if (
                        notification.priorityLabel !=
                        "UNKNOWN"
                    ) {

                        append("\n")

                        append("Priority : ")

                        append(notification.priorityLabel)
                    }
                }

            txtTime.text =
                SimpleDateFormat(
                    "dd MMM yyyy  HH:mm",
                    Locale.getDefault()
                ).format(
                    Date(
                        notification.timestamp
                    )
                )
        }
    }
}