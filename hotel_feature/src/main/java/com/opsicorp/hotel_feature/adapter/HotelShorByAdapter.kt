package com.opsicorp.hotel_feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_hotel_short_by_adapter.view.*

class HotelShorByAdapter (context: Context, private var items: ArrayList<String>): androidx.recyclerview.widget.RecyclerView.Adapter<HotelShorByAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context
    var checkIn = 0

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hotel_short_by_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_short_name.text = data

        if (checkIn==position){
            holder.itemView.ic_checklist.visibility = View.VISIBLE
            holder.itemView.tv_short_name.setTextColor(context.resources.getColor(R.color.colorPrimary))
        }
        else {
            holder.itemView.ic_checklist.visibility = View.GONE
            holder.itemView.tv_short_name.setTextColor(context.resources.getColor(R.color.blue_info_time))
        }

        holder.itemView.setOnClickListener {
            checkIn = position
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: java.util.ArrayList<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}