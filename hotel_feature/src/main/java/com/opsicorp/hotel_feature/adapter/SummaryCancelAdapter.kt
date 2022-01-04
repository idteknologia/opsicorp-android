package com.opsicorp.hotel_feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import kotlinx.android.synthetic.main.item_cancel_summary.view.*

class SummaryCancelAdapter (private var items: List<String>): androidx.recyclerview.widget.RecyclerView.Adapter<SummaryCancelAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cancel_summary, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_cancelation_start_from.text = data

    }

    fun setData(data: List<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}