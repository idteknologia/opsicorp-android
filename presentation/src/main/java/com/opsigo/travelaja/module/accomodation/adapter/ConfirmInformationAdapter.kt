package com.opsigo.travelaja.module.accomodation.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_flight_list.view.*
import kotlinx.android.synthetic.main.layout_card_detail_flight.view.*

class ConfirmInformationAdapter (context: Context, private var items: ArrayList<ConfirmationFlightModelAdapter>): RecyclerView.Adapter<ConfirmInformationAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_flight_list, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, positionParent: Int) {

        val data = items.get(positionParent)

        Picasso.get()
                .load(data.imageFlight)
                .fit()
                .centerInside()
                .into(holder.itemView.img_flight_icon)

        holder.itemView.title_airline.text = data.titleFlight
        holder.itemView.tv_number.text = data.numberSheet
        holder.itemView.tv_type_class.text = data.className
        holder.itemView.tv_time_departure.text = data.timeDeparture
        holder.itemView.tv_date_departure.text = DateConverter().setDateFrormattingDetailFlight(data.dateDeparture)
        holder.itemView.tv_departure.text = data.stationDeparture
        holder.itemView.tv_duration.text = data.duration
        holder.itemView.tv_transit.text = data.transit
        holder.itemView.tv_time_arrival.text = data.timeArrival
        holder.itemView.tv_date_arrival.text = DateConverter().setDateFrormattingDetailFlight(data.dateArrival)
        holder.itemView.tv_arrival.text = data.stationArrival

        if (positionParent==0){
            holder.itemView.line_doted.visibility = View.GONE
        }
        else{
            holder.itemView.line_doted.visibility = View.VISIBLE
        }

    }


    fun setData(data: ArrayList<ConfirmationFlightModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}