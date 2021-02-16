package com.opsicorp.travelaja.feature_flight.ssr

import android.view.View
import com.opsigo.travelaja.R
import android.view.ViewGroup
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.baggage_list_item_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.DataSsrModel

class BaggageListAdapter (context: Context): RecyclerView.Adapter<BaggageListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<DataSsrModel>()
    val context = context
    var row_index = -1

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.baggage_list_item_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.itemView.tv_value_baggage.text = data.ssrName
        holder.itemView.tv_price_baggage.text = "IDR ${Globals.currencyIDRFormat(data.pricing.toDouble())}"

        holder.itemView.setOnClickListener {
            onclick.onClick(Constants.KEY_BAGGAGE_ITEM_SELECTED,position)
            row_index = position
            notifyDataSetChanged()
        }
        if (row_index == position ){
            holder.itemView.cvBaggage.setBackgroundResource(R.drawable.card_background_corner_green)
            holder.itemView.tv_price_baggage.setTextColor(ContextCompat.getColor(context, R.color.green_price))
        } else {
            holder.itemView.cvBaggage.setBackgroundResource(R.drawable.card_background_corner_grey)
            holder.itemView.tv_price_baggage.setTextColor(ContextCompat.getColor(context, R.color.colorMaterialGrey))
        }

    }

    fun setData(data: ArrayList<DataSsrModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}