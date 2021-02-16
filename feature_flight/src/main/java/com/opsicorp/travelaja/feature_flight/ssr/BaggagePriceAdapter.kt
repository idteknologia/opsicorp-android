package com.opsicorp.travelaja.feature_flight.ssr

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.baggage_price_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.SelectedBaggageModel

class BaggagePriceAdapter (context: Context): RecyclerView.Adapter<BaggagePriceAdapter.ViewHolder>() {

    var items = ArrayList<SelectedBaggageModel>()
    val context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.baggage_price_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        if (data.price.equals("0.0")){
            holder.itemView.tvBaggagePrice.text = "Free"
        } else {
            holder.itemView.tvBaggagePrice.text = "IDR ${Globals.currencyIDRFormat(data.price.toDouble())}"
        }
        holder.itemView.tvBaggageValue.text = data.flightTitle
    }

    fun setData(data: ArrayList<SelectedBaggageModel>) {
        items = data
        notifyDataSetChanged()
    }
    class ViewHolder (row: View) : RecyclerView.ViewHolder(row) {
    }
}