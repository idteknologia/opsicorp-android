package com.mobile.travelaja.module.my_booking.purchase_detail

import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.content.Context
import android.view.LayoutInflater
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_product_detail_purchase.view.*
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import opsigo.com.domainlayer.model.my_booking.ItemFlightModel
import opsigo.com.domainlayer.model.my_booking.ItemHotelPurchase
import opsigo.com.domainlayer.model.my_booking.ItemPurchaseTrainModel

class PurchaseDetailProductAdapter (var context: Context, private var items: ArrayList<Any>): androidx.recyclerview.widget.RecyclerView.Adapter<PurchaseDetailProductAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_product_detail_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if(position==items.size-1){
            holder.itemView.line_bottom.visibility = View.GONE
        }
        else{
            holder.itemView.line_bottom.visibility = View.VISIBLE
        }

        when(data){
            is ItemFlightModel -> {
                holder.itemView.type_product.text = data.status
                holder.itemView.tv_departure.text = data.originCity
                holder.itemView.tv_arrival.text   = data.destinationCity
                holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_flight_up_white))
            }
            is ItemPurchaseTrainModel -> {
                holder.itemView.type_product.text = data.status
                holder.itemView.tv_departure.text = data.originCity
                holder.itemView.tv_arrival.text   = data.destinationCity
                holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_train_white))
            }
            is ItemHotelPurchase -> {
                holder.itemView.type_product.text = data.status
                holder.itemView.tv_departure.text = data.hotelName
                holder.itemView.ic_panah.visibility = View.GONE
                holder.itemView.tv_arrival.visibility = View.GONE
                holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_hotel_white))
            }
        }

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }


    }

    fun setData(data: ArrayList<Any>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}