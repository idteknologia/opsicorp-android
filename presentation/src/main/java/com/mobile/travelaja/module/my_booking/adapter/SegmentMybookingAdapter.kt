package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.items_purchase_detail_flight_and_train.view.*
import opsigo.com.domainlayer.model.my_booking.PurchaseDetailTripFlightAndTrainModel

class SegmentMybookingAdapter (var context: Context, private var items: ArrayList<PurchaseDetailTripFlightAndTrainModel>): androidx.recyclerview.widget.RecyclerView.Adapter<SegmentMybookingAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    var positionCheckBox = "left"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.items_purchase_detail_flight_and_train, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (data.layover.isNotEmpty()||!data.layover.equals("null")){
            holder.itemView.line_layover.visibility = View.VISIBLE
            holder.itemView.tv_name_airport_layover.text = data.nameAirportLayover
            holder.itemView.tv_layover.text = data.layover
        }
        else {
            holder.itemView.line_layover.visibility = View.GONE
        }

        holder.itemView.tv_flight_name.text             = data.nameFlight
        holder.itemView.tv_seat_number.text             = data.numberSeat
        holder.itemView.tv_calss.text                   = data.classFlight
        holder.itemView.tv_type_accomodation.text       = data.typeFlight
        holder.itemView.tv_time_departure.text          = data.timeDeparture
        holder.itemView.tv_date_departure.text          = data.dateDepartute
        holder.itemView.tv_origin.text                  = data.origin
        holder.itemView.tv_name_station_departure.text  = data.nameAirportDepature
        holder.itemView.tv_terminal.text                = data.terminalDeparture
        holder.itemView.line_total_duration.text        = data.totalHour
        holder.itemView.tv_time_arrival.text            = data.timeArrival
        holder.itemView.tv_date_arrival.text            = data.dateArrival
        holder.itemView.tv_destination.text             = data.destinantion
        holder.itemView.tv_name_station_arrival.text    = data.nameStasiunArrival
        holder.itemView.tv_bloc_station_arrival.text    = ""

        if (data.imageFlight.isNotEmpty()){
            Picasso.get()
                .load(data.imageFlight)
                .fit()
                .centerCrop()
                .into(holder.itemView.ic_image_airline)
        }

    }

    fun setData(data: ArrayList<PurchaseDetailTripFlightAndTrainModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}