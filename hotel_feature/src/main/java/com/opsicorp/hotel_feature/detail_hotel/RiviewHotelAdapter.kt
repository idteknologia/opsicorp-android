package com.opsicorp.hotel_feature.detail_hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_review_hotel.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.RiviewHotelModel

class RiviewHotelAdapter (context: Context, var items: ArrayList<RiviewHotelModel>): androidx.recyclerview.widget.RecyclerView.Adapter<RiviewHotelAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_review_hotel, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_message_riview.text = data.massage
        holder.itemView.tv_name.text           = data.name

        holder.itemView.setOnClickListener {
            onclick.onClick(Constants.REVIEW_HOTEL_SELECT,position)
        }
    }


    fun setData(data: ArrayList<RiviewHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}