package com.niraj.notificationdatacollector.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niraj.notificationdatacollector.R
import com.niraj.notificationdatacollector.model.NotificationEntity

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

        val view = LayoutInflater.from(parent.context)
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

        private val app =
            itemView.findViewById<TextView>(R.id.txtApp)

        private val title =
            itemView.findViewById<TextView>(R.id.txtTitle)

        private val text =
            itemView.findViewById<TextView>(R.id.txtText)

        private val time =
            itemView.findViewById<TextView>(R.id.txtTime)

        fun bind(
            notification: NotificationEntity
        ) {

            app.text = notification.appName
            title.text = notification.title
            text.text = notification.text
            time.text = notification.formattedTime
        }
    }
}