package com.opsicorp.travelaja.feature_flight.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.filter_adapter_flight.view.*
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel

class FilterFlightAdapter (val context: Context, private var items: ArrayList<FilterFlightModel>): RecyclerView.Adapter<FilterFlightAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var check = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_adapter_flight, parent, false)

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

        holder.itemView.checkbox.isChecked = position == check

        holder.itemView.tv_name_time.text = data.name
        holder.itemView.tv_time.text      = data.time

        holder.itemView.setOnClickListener {
            check = position
            notifyDataSetChanged()
            onclick.onClick(-1,position)
        }

        holder.itemView.checkbox.setOnClickListener {
            check = position
            notifyDataSetChanged()

        }
    }

    fun setData(data: ArrayList<FilterFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}