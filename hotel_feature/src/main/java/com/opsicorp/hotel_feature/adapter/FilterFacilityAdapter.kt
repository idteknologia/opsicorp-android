package com.opsicorp.hotel_feature.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_filter_facility.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

class FilterFacilityAdapter (context: Context, private var items: ArrayList<FacilityHotelModel>): androidx.recyclerview.widget.RecyclerView.Adapter<FilterFacilityAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context
    var checkedPosition = 0

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filter_facility, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.itemView.tv_name_facility.text = data.name
        holder.itemView.cb_facility.isChecked = data.active
        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<FacilityHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}