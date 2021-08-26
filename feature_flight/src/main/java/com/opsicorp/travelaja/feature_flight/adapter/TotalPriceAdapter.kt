package com.opsicorp.travelaja.feature_flight.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.Globals
import kotlinx.android.synthetic.main.total_price_adapter.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
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
        val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        val data = items[position]
        val totalPrice = data.price * dataOrder.totalPassengerInteger
        if (data.price.equals(0.0)){
            holder.itemView.tv_prize_departure.text = "Free"
        } else {
            holder.itemView.tv_prize_departure.text = "IDR ${Globals.currencyIDRFormat(totalPrice)}"
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