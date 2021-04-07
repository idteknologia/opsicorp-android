package com.opsicorp.travelaja.feature_flight.flight_info.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import kotlinx.android.synthetic.main.fare_rules_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.FareRulesModel

class FareRulesAdapter(context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<FareRulesAdapter.ViewHolder>() {

    var items = ArrayList<FareRulesModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.fare_rules_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.itemView.tvFareRulesName.text = data.fareRulesName
        holder.itemView.tvFareRulesValue.text = data.fareRulesValues.toString()
    }

    fun setData(data: ArrayList<FareRulesModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}