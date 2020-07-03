package com.example.emergencybutton.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencybutton.R
import com.example.emergencybutton.model.NotificationItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.notification_item.*

class NotificationAdapter(
    private val context: Context,
    private val items: List<NotificationItem?>?,
    private val listener: (NotificationItem) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.notification_item,
                    parent,
                    false
                )
        )
    override fun getItemCount(): Int = items?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.get(position)?.let { holder.bindItem(it, listener) }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(items: NotificationItem, listener: (NotificationItem) -> Unit) {
            tv_nama_barang.text = items.name
            tv_nama_pemosting.text = items.postersName
            tv_date_barang.text = items.date
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}