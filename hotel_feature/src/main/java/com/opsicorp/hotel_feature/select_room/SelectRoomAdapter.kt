package com.opsicorp.hotel_feature.select_room

import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.content.Context
import android.view.ViewGroup
import android.view.View
import com.opsicorp.hotel_feature.R
import com.opsicorp.hotel_feature.detail_hotel.DialogDetailReview
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.item_select_room.view.*

class SelectRoomAdapter (var items: ArrayList<SelectRoomModel>): RecyclerView.Adapter<SelectRoomAdapter.ViewHolder>() {

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
        holder.itemView.tv_policy_description.text  = data.policyDescription
        holder.itemView.tv_prize_room.text          = "${Globals.formatAmount(data.prize.toDouble().toInt().toString())}"
        holder.itemView.tv_policy_description.text  = "This Reservation is non-refundable"

        holder.itemView.btn_information.setOnClickListener {
            onclick.onClick(Constants.ONCLICK_INFO_CANCELATION_HOTEL,position)
        }
        holder.itemView.tv_policy_description.setOnClickListener {
            onclick.onClick(Constants.ONCLICK_INFO_CANCELATION_HOTEL,position)
        }
        holder.itemView.tv_title_policy.setOnClickListener {
            onclick.onClick(Constants.ONCLICK_INFO_CANCELATION_HOTEL,position)
        }

        if (data.isBreakfast){
            holder.itemView.tv_include_breakfast.text  = "Included Breakfast"
        }
        else {
            holder.itemView.tv_include_breakfast.text  = "Room Only"
        }

        if (data.isGuaranteedBooking){
            holder.itemView.type_booking.text          = "Hold Booking"
        }
        else{
            holder.itemView.type_booking.text          = "Direct Booking"
        }

        if (data.isFullCharge){
//            holder.itemView.line_guaranted.visibility   = View.VISIBLE //"Refundable"\
        }else {
//            holder.itemView.line_guaranted.visibility   = View.GONE //"Refundable"\
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