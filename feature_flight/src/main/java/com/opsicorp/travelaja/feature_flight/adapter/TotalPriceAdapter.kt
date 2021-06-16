package com.opsicorp.travelaja.feature_flight.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.total_price_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class TotalPriceAdapter (context: Context): RecyclerView.Adapter<TotalPriceAdapter.ViewHolder>() {

    var items = ArrayList<ResultListFlightModel>()
    val context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.total_price_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        if (data.price.equals(0.0)){
            holder.itemView.tv_prize_departure.text = "Free"
        } else {
            holder.itemView.tv_prize_departure.text = "IDR ${Globals.currencyIDRFormat(data.price.toDouble())}"
        }
        holder.itemView.tv_station_departure.text = data.titleAirline
    }

    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }
    class ViewHolder (row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {
    }
}