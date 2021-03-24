package com.opsicorp.travelaja.feature_flight.detail_cart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_cart_item_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ConfirmationFlightModel

class DetailCartAdapter (val context: Context, private var items: ArrayList<ConfirmationFlightModel>): RecyclerView.Adapter<DetailCartAdapter.ViewHolder>() {

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

        holder.itemView.tv_pnr_flight_cart.text = data.pnr_code
        holder.itemView.tv_status_flight_cart.text = data.status
        holder.itemView.tv_date_departure_flight_cart_top.text = Globals.getDateNowNewFormat()

        holder.itemView.tv_title_airline.text = data.title_flight
        holder.itemView.tv_airline_number.text   = data.airlineNumber

        if(!data.img_airline.isEmpty() && data.img_airline != null) {
            Picasso.get()
                    .load(data.img_airline)
                    .fit()
                    .centerInside()
                    .into(holder.itemView.img_flight)
        }

        holder.itemView.tv_class_type.text = data.class_type
        holder.itemView.tv_number_sheet.text = data.number_sheet
        holder.itemView.tv_time_origin.text = data.timeDeparture
        holder.itemView.tv_date_origin.text = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd","dd MMM")
        holder.itemView.tv_origin.text = data.name_stationDeparture
        holder.itemView.tv_station_origin.text = data.depatureAirportName

        if (data.terminal.isNullOrEmpty()||"null".equals(data.terminal)){
            holder.itemView.tv_terminal.text = "Terminal - "
        }else{
            holder.itemView.tv_terminal.text = "Terminal "+data.terminal
        }

        holder.itemView.line_total_duration.text = data.line_total_duration

        holder.itemView.tv_time_arrival.text = data.time_arrival
        holder.itemView.tv_date_arrival.text = DateConverter().getDate(data.date_arrival,"yyyy-MM-dd","dd MMM")
        holder.itemView.tv_depart.text = data.name_stationArrival
        holder.itemView.tv_station_destination.text = data.arrivalAirportName
        holder.itemView.tv_total_passager.text = data.total_passager
        holder.itemView.tv_total_prize.text = "IDR ${Globals.formatAmount(data.total_prize.split(".")[0])}"

    }

    fun setData(data: ArrayList<ConfirmationFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}