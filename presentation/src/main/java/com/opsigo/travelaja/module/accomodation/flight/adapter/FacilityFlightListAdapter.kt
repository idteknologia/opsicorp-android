package com.opsigo.travelaja.module.accomodation.flight.adapter


import android.view.View
import com.opsigo.travelaja.R
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.facility_list_adapter_view.view.*
import opsigo.com.domainlayer.model.accomodation.flight.FacilityFlightModel

class FacilityFlightListAdapter (val context: Context, private var items: ArrayList<FacilityFlightModel>): RecyclerView.Adapter<FacilityFlightListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.facility_list_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_name_facility.text = items[position].nameFacility
        holder.itemView.tv_value_facility.text = items[position].valueFacility
    }

    fun setData(data: ArrayList<FacilityFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}