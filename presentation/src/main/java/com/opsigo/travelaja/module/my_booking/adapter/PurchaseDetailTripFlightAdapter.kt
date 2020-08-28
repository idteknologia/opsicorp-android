package com.opsigo.travelaja.module.my_booking.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.model.PurchaseDetailTripFlightAndTrainModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.items_purchase_detail_flight_and_train.view.*
import kotlinx.android.synthetic.main.items_purchase_detail_flight_and_train_time.view.*
import kotlinx.android.synthetic.main.items_purchase_detail_flight_and_train_time_left.view.*

class PurchaseDetailTripFlightAdapter (context: Context, private var items: ArrayList<PurchaseDetailTripFlightAndTrainModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){

            VIEW_BODY -> BodyAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_purchase_detail_flight_and_train, parent, false))

            VIEW_HEADER_LEFT -> HeaderLeft(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_purchase_detail_flight_and_train_time_left, parent, false))

            else -> HeaderCenter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_purchase_detail_flight_and_train_time, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_BODY -> (holder as BodyAdapter).bind(items[position],position)
            VIEW_HEADER_LEFT -> (holder as HeaderLeft).bind(items[position],position)
            VIEW_HEADER_CENTER -> (holder as HeaderCenter).bind(items[position],position)
        }
    }

    inner class BodyAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: PurchaseDetailTripFlightAndTrainModel, position: Int) {
            itemView.tv_airport_name.text            = data.nameAirportDepature
            itemView.tv_seat_number.text             = data.numberSeat
            itemView.tv_calss.text                   = data.classFlight
            itemView.tv_type_accomodation.text       = data.typeFlight
            itemView.tv_time_departure.text          = data.timeDeparture
            itemView.tv_date_departure.text          = data.dateDepartute
            itemView.tv_name_city_departure.text     = data.nameAirportDepature
            itemView.tv_name_station_departure.text  = data.addressAirportDeparture
            itemView.tv_bloc_station_departure.text  = data.terminalDeparture
            itemView.line_total_duration.text        = data.totalHour
            itemView.tv_time_arrival.text            = data.timeArrival
            itemView.tv_date_arrival.text            = data.dateArrival
            itemView.tv_name_city_arrival.text       = data.nameStasiunArrival
            itemView.tv_name_station_arrival.text    = data.addressStationArrival
            itemView.tv_bloc_station_arrival.text    = ""
        }

    }

    inner class HeaderLeft internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: PurchaseDetailTripFlightAndTrainModel, position: Int) {

            itemView.tv_layover_left.text = data.layover
            itemView.tv_name_airport_layover_left.text = data.nameAirportLayover

        }

    }

    inner class HeaderCenter internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: PurchaseDetailTripFlightAndTrainModel, position: Int) {

            itemView.tv_layover.text = data.layover
            itemView.tv_name_airport_layover.text = data.nameAirportLayover

        }

    }

    fun setData(data: java.util.ArrayList<PurchaseDetailTripFlightAndTrainModel>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {

        val VIEW_BODY = 1
        val VIEW_HEADER_LEFT = 2
        val VIEW_HEADER_CENTER = 3
    }

    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).status){
            "Body" -> VIEW_BODY
            "header_left" -> VIEW_HEADER_LEFT
            else -> VIEW_HEADER_CENTER
        }
    }
}