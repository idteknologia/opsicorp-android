package com.opsicorp.hotel_feature.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsicorp.hotel_feature.R
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hotel_booking_contact.view.*
import opsigo.com.datalayer.request_model.accomodation.hotel.booking.GuestsItemReservationHotelRequest

class HotelBookingContactAdapter (context: Context, private var items: ArrayList<GuestsItemReservationHotelRequest>): androidx.recyclerview.widget.RecyclerView.Adapter<HotelBookingContactAdapter.ViewHolder>() {

    lateinit var onclick: OnclickRecyclerBookingContact
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.hotel_booking_contact, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickRecyclerBookingContact){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        if (position==0){
            holder.itemView.cb_guest.isChecked = !data.lastName.isNullOrEmpty()
            holder.itemView.line_im_the_guest.visibility = View.VISIBLE
        }
        else {
            holder.itemView.et_guest.setText("TBA")
            holder.itemView.line_im_the_guest.visibility = View.GONE
        }

        holder.itemView.line_im_the_guest.setOnClickListener {
            onclick.onClick(7,position)
        }

        if (!data.firstName.isEmpty()){
            if (data.firstName==data.lastName){
                holder.itemView.et_guest.setText("${data.firstName}")
            }
            else {
                holder.itemView.et_guest.setText("${data.firstName}${data.lastName}")
            }
        }
        else{
            holder.itemView.et_guest.setText("${data.firstName}")
        }

        holder.itemView.et_no_hp.setText(data.mobilePhone)

        holder.itemView.disable_checklist.setOnClickListener {

        }
    }

    fun setData(data: ArrayList<GuestsItemReservationHotelRequest>) {
        items = data
        notifyDataSetChanged()
    }

    fun getDataList():ArrayList<GuestsItemReservationHotelRequest>{
        return items
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}