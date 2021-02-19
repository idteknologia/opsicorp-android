package com.opsicorp.travelaja.feature_flight.seat_map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.select_seat_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class SelectSeatAdapter(context: Context): RecyclerView.Adapter<SelectSeatAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<ResultListFlightModel>()
    val context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_seat_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.itemView.tv_airline_name.text     = "${data.origin} - ${data.destination}"
        if (position==0){
            holder.itemView.tv_title_trip.text    = "Departure Flight"
        } else {
            holder.itemView.tv_title_trip.text     = "Arrival Flight"
        }
        /*if (data.dataSeat.isNotEmpty()){
            holder.itemView.tvPickSeat.text = "Change"
        } else {
            holder.itemView.tvPickSeat.text = "Select Seat"
        }*/
        if (data.imgAirline.isNotEmpty()){
            Picasso.get()
                    .load(data.imgAirline)
                    .fit()
                    .into(holder.itemView.img_airline)
        }

        if (!Constants.DATA_SEAT_AIRLINE.filter {
                    it.flightNumber == data.flightNumber
                }.isNullOrEmpty()){
            if (data.dataSeat.dataSeat.isNotEmpty()){
                holder.itemView.tvPickSeat.text = "Change"
            } else {
                holder.itemView.tvPickSeat.text = "Select Seat"
            }
        } else {
            holder.itemView.tvPickSeat.text = "Unavailable"
            holder.itemView.tvPickSeat.setTextColor(context.resources.getColor(R.color.colorGray))
        }

        holder.itemView.tvPickSeat.setOnClickListener {
            if (holder.itemView.tvPickSeat.text.equals("Select Seat")){
                onclick.onClick(Constants.REQUEST_CODE_SELECT_SEAT,position)

            }
        }
    }

    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View): RecyclerView.ViewHolder(row)  {

    }
}