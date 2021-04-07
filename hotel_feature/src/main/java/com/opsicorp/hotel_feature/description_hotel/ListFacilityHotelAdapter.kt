package com.opsicorp.hotel_feature.description_hotel

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsicorp.hotel_feature.R
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_facility_list_hotel.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

class ListFacilityHotelAdapter (var context: Context, var items: ArrayList<FacilityHotelModel>): androidx.recyclerview.widget.RecyclerView.Adapter<ListFacilityHotelAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_facility_list_hotel, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_name_facility.text = data.name
    }


    fun setData(data: ArrayList<FacilityHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}