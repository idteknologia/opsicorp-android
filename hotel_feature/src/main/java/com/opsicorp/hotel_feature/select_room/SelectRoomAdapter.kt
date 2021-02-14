package com.khoiron.hotel_feature.select_room

import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_select_room.view.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.content.Context
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.view.View
import com.opsigo.travelaja.utility.Globals

class SelectRoomAdapter (context: Context, var items: ArrayList<SelectRoomModel>): RecyclerView.Adapter<SelectRoomAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_room, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_title_room.text          = data.titleRoom
        holder.itemView.tv_bed_facility.text        = data.BedFacility
        holder.itemView.tv_policy_description.text  = data.policyDescription
        holder.itemView.tv_prize_room.text          = "IDR ${Globals.formatAmount(data.prize.toDouble().toInt().toString())}"

        if (data.isGuaranteedBooking){
            holder.itemView.tv_type_refund.text         = "Guaranted"
        }
        else{
            holder.itemView.tv_type_refund.text         = "Refundable"
        }

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<SelectRoomModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}