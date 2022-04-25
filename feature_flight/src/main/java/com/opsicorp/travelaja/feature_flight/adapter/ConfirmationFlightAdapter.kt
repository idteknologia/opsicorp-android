package com.opsicorp.travelaja.feature_flight.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsicorp.travelaja.feature_flight.flight_info.activity.FareRulesActivity
import com.mobile.travelaja.utility.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_confirmation_order_flight_new.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ConfirmationFlightModel


class ConfirmationFlightAdapter (val context: Context, private var items: ArrayList<ConfirmationFlightModel>): androidx.recyclerview.widget.RecyclerView.Adapter<ConfirmationFlightAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_confirmation_order_flight_new, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_title_airline.text = data.title_flight
        Globals.setLog(data.img_airline)

        if(!data.img_airline.isEmpty()) {
            Picasso.get()
                    .load(data.img_airline)
                    .fit()
                    .centerInside()
                    .into(holder.itemView.img_flight)
        }

        holder.itemView.tv_origin.text = data.originDisplay
        holder.itemView.tv_depart.text = data.departureDisplay
        holder.itemView.tv_class_type.text = data.class_type
        holder.itemView.tv_total_prize.text = data.totalPrice
        holder.itemView.tvFlightDate.text = data.date_arrival_departure
        holder.itemView.tv_airline_number.text  = data.airlineNumber

        holder.itemView.tv_time_origin.text = data.timeDeparture
        holder.itemView.tv_date_origin.text = data.dateDeparture

        holder.itemView.tv_time_arrival.text = data.time_arrival
        holder.itemView.tv_date_arrival.text = data.date_arrival

        holder.itemView.tv_station_origin.text  = data.depatureAirportName
        holder.itemView.tv_station_destination.text = data.arrivalAirportName


        holder.itemView.tv_total_passager.text = data.totalPassenger

        holder.itemView.line_total_duration.text = "${context.getString(R.string.total_duration)} : ${data.line_total_duration}"

        if (data.flight_type.equals("NonGds")){
            holder.itemView.rlFareRules.gone()
        } else {
            holder.itemView.rlFareRules.visible()
        }

        holder.itemView.rlFareRules.setOnClickListener {
            val intent = Intent(context,FareRulesActivity::class.java)
            intent.putExtra(Constants.KEY_POSITION_FARE_RULES,position)
            (context as Activity).startActivityForResult(intent,Constants.REQUEST_CODE_FARE_RULES)
        }

        if (data.terminal.isEmpty()||"null".equals(data.terminal)){
            holder.itemView.tv_terminal.text = context.getString(R.string.text_terminal)
        }else{
            holder.itemView.tv_terminal.text = "${context.getString(R.string.text_terminal)} "+data.terminal
        }

        if (data.isMultiCity.equals(true)){
            holder.itemView.title_trip.text = context.getString(R.string.departure_date)
        } else {
            if (position==0){
                holder.itemView.title_trip.text = context.getString(R.string.departure_date)
            }
            else{
                holder.itemView.title_trip.text = context.getString(R.string.arrival_date)
            }
        }

        if (data.isComply){
            holder.itemView.tv_not_comply.visibility = View.GONE
        }
        else{
            holder.itemView.tv_not_comply.visibility = View.VISIBLE
        }
    }

    fun setData(data: ArrayList<ConfirmationFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}