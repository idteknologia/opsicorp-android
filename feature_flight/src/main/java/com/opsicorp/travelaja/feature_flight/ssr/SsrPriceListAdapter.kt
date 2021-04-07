package com.opsicorp.travelaja.feature_flight.ssr

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.ssr_price_list_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.SelectedSsrModel

class SsrPriceListAdapter (context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<SsrPriceListAdapter.ViewHolder>() {

    var items = ArrayList<SelectedSsrModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ssr_price_list_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        if (data.price.equals("0.0")){
            holder.itemView.tvSsrPrice.text = "Free"
        } else {
            holder.itemView.tvSsrPrice.text = "IDR ${Globals.currencyIDRFormat(data.price.toDouble())}"
        }
        holder.itemView.tvSsrValue.text = data.ssrName
    }

    fun setData(data: ArrayList<SelectedSsrModel>) {
        items = data
        notifyDataSetChanged()
    }
    class ViewHolder (row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {
    }

}