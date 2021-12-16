package com.opsicorp.travelaja.feature_flight.detail_cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.StringUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_cart_item_adapter.view.*
import opsigo.com.domainlayer.model.summary.FlightSegmentItem

class DetailCartFlightAdapter (val context: Context, private var items: ArrayList<FlightSegmentItem>): androidx.recyclerview.widget.RecyclerView.Adapter<DetailCartFlightAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_cart_item_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_pnr_flight_cart.text = data.pnrCode
        holder.itemView.tv_status_flight_cart.text = data.status
        holder.itemView.tv_date_departure_flight_cart_top.text = Globals.getDateNowNewFormat()

        holder.itemView.tv_title_airline.text = data.nameAirline
        holder.itemView.tv_airline_number.text   = data.airlineNumber

        if(!data.imageAirline.isEmpty()) {
            Picasso.get()
                    .load(data.imageAirline)
                    .fit()
                    .centerInside()
                    .into(holder.itemView.img_flight)
        }

        holder.itemView.tv_class_type.text = data.typeFlight
        holder.itemView.tv_number_sheet.text = data.seatFlight
        holder.itemView.tv_time_origin.text = data.timeDeparture
        holder.itemView.tv_date_origin.text = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd","dd MMM")
        holder.itemView.tv_origin.text = data.cityCodeDeparture
        holder.itemView.tv_station_origin.text = data.airportDeparture

        if (data.terminal.isNullOrEmpty()||"null".equals(data.terminal)){
            holder.itemView.tv_terminal.text = context.getString(R.string.text_terminal)
        }else{
            holder.itemView.tv_terminal.text = context.getString(R.string.text_terminal)+data.terminal
        }

        holder.itemView.line_total_duration.text = data.estimatiTime

        holder.itemView.tv_time_arrival.text = data.timeArrival
        holder.itemView.tv_date_arrival.text = DateConverter().getDate(data.dateArrival,"yyyy-MM-dd","dd MMM")
        holder.itemView.tv_depart.text = data.cityCodeArrival
        holder.itemView.tv_station_destination.text = data.airportArrival
        holder.itemView.tv_total_passager.text = "${context.getString(R.string.text_adult_times)} ${data.totalPassenger}"
        holder.itemView.tv_total_prize.text = StringUtils().setCurrency("IDR", data.price.toDouble(), false)

    }

    fun setData(data: ArrayList<FlightSegmentItem>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}