package com.opsicorp.travelaja.feature_flight.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_train_short_by_adapter.view.*

class FlightShortByAdapter (context: Context, private var items: ArrayList<String>): RecyclerView.Adapter<FlightShortByAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context
    var checkIn = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_train_short_by_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
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

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}