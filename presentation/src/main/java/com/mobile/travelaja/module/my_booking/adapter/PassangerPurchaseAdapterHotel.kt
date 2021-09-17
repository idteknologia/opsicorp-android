package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_passanger_list_detail_purchase_hotel.view.*
import opsigo.com.domainlayer.model.my_booking.GuestsItems

class PassangerPurchaseAdapterHotel (var context: Context, private var items: ArrayList<GuestsItems>): androidx.recyclerview.widget.RecyclerView.Adapter<PassangerPurchaseAdapterHotel.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_passanger_list_detail_purchase_hotel, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_name.text = data.firstName
        holder.itemView.tv_total_passager.text = "Max ${data.maxBedType} Guest/room"
        holder.itemView.tv_number.text = (position+1).toString()
        holder.itemView.tv_type_bed_type.text = data.bedType
    }

    fun setData(data: ArrayList<GuestsItems>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}