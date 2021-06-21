package com.opsicorp.travelaja.feature_flight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.filter_cabin_adapter_flight.view.*
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel

class FilterFlightCabinAdapter (val context: Context, private var items: ArrayList<FilterFlightModel>): androidx.recyclerview.widget.RecyclerView.Adapter<FilterFlightCabinAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var check = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_cabin_adapter_flight, parent, false)

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

        holder.itemView.tvCabinClass.text = data.name


        holder.itemView.setOnClickListener {
            check = position
            onclick.onClick(-1,position)
        }

        holder.itemView.checkbox.setOnClickListener {
            check = position

        }
    }

    fun setData(data: ArrayList<FilterFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}
