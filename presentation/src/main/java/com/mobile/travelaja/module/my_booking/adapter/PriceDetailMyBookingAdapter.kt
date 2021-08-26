package com.mobile.travelaja.module.my_booking.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.item_price_detail_my_booking.view.*
import opsigo.com.domainlayer.model.my_booking.PriceListModel

class PriceDetailMyBookingAdapter (val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<PriceDetailMyBookingAdapter.ViewHolder>() {

    var items: ArrayList<PriceListModel> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_price_detail_my_booking, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_name_price.text = data.title.toString()
        holder.itemView.tv_price.text = "IDR ${Globals.formatAmount(data.amount)}"
    }

    fun setData(data:ArrayList<PriceListModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}