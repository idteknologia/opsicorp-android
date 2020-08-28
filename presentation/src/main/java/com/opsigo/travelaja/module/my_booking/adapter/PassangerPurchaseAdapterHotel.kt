package com.opsigo.travelaja.module.my_booking.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.model.PassangerPurchaseModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_passanger_list_detail_purchase_hotel.view.*

class PassangerPurchaseAdapterHotel (var context: Context, private var items: ArrayList<PassangerPurchaseModel>): RecyclerView.Adapter<PassangerPurchaseAdapterHotel.ViewHolder>() {

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

        holder.itemView.tv_name.text = data.Name
        holder.itemView.tv_total_passager.text = "Max ${data.totalPassager} Guest/room"
        holder.itemView.tv_number.text = (position+1).toString()

    }

    fun setData(data: ArrayList<PassangerPurchaseModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}