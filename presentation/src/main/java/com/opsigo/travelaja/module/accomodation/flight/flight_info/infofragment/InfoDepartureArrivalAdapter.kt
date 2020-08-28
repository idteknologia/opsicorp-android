package com.opsigo.travelaja.module.accomodation.flight.flight_info.infofragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_adapter_time_arrival_departure.view.*

class InfoDepartureArrivalAdapter (val context: Context, private var items: ArrayList<ModelListDepartureAndArrivalAdapter>): RecyclerView.Adapter<InfoDepartureArrivalAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_time_arrival_departure, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    fun setData(data :ArrayList<ModelListDepartureAndArrivalAdapter>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_title_flight.text = data.titleFlight
        holder.itemView.tv_class_type.text = data.classNameFlight
        holder.itemView.tv_number_sheet.text = data.numberFlight
        holder.itemView.tv_time_departure.text = data.timeDeparture
        holder.itemView.tv_name_departure.text = data.departureDisplay
        holder.itemView.tv_date_departure.text = data.dateDeparture
        holder.itemView.tv_airport_departure.text = data.departureAirport
        holder.itemView.line_total_duration.text  = data.duration
        holder.itemView.tv_time_arrival.text = data.timeArrival
        holder.itemView.tv_name_arrival.text = data.arrivalDisplay
        holder.itemView.tv_date_arrival.text = data.dateArrival
        holder.itemView.tv_airport_arrival.text = data.arrivalAirport

        if (data.imageFlight.isNotEmpty()){
            Picasso.get()
                    .load(data.imageFlight)
                    .fit()
                    .into(holder.itemView.icon_train)
        }

    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}