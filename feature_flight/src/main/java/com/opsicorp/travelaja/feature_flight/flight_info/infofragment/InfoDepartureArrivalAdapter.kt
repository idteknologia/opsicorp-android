package com.opsicorp.travelaja.feature_flight.flight_info.infofragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_adapter_time_arrival_departure_new.view.*

class InfoDepartureArrivalAdapter (val context: Context, private var items: ArrayList<ModelListDepartureAndArivalAdapter>): androidx.recyclerview.widget.RecyclerView.Adapter<InfoDepartureArrivalAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_time_arrival_departure_new, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
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
        holder.itemView.tv_airport_terminal.text = "Terminal: ${data.originTerminal}"

        if (data.imageFlight.isNotEmpty()){
            Picasso.get()
                    .load(data.imageFlight)
                    .fit()
                    .into(holder.itemView.icon_train)
        }
    }

    fun setData(data :ArrayList<ModelListDepartureAndArivalAdapter>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}