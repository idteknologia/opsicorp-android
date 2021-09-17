package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_facility_hotel_purchase.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

class FacilityHotelPurchaseAdapter (context: Context, var items: ArrayList<FacilityHotelModel>): androidx.recyclerview.widget.RecyclerView.Adapter<FacilityHotelPurchaseAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_facility_hotel_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_name_facility.text = data.name
        holder.itemView.ic_facility.setImageDrawable(ContextCompat.getDrawable(context,data.image))//data.image

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<FacilityHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}