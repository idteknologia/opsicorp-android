package com.mobile.travelaja.module.manage_trip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter

class TripHistoryAdapter (val context: Context, private var items: ArrayList<ApprovalModelAdapter>): RecyclerView.Adapter<TripHistoryAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        when(data.status){
            "Trip Completed" -> {
                holder.itemView.ivStatusHistory.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_trip_complete))
            } else -> {
            holder.itemView.ivStatusHistory.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_trip_canceled))
        }
        }

        holder.itemView.tv_date.text = DateConverter().setDateFormat4(data.start_date) + " - " + DateConverter().setDateFormat4(data.end_date)
        holder.itemView.tv_purpose.text = data.title

        holder.itemView.setOnClickListener {
            onclick.onClick(-1, position)
        }

    }

    fun setData(data: java.util.ArrayList<ApprovalModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }
}