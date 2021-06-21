package com.opsicorp.travelaja.feature_flight.seat_map

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.select_seat_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class SelectSeatAdapter(context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<SelectSeatAdapter.ViewHolder>() {

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
                holder.itemView.ivRemoveSeat.visible()
                holder.itemView.ivRemoveSeat.setOnClickListener {
                    onclick.onClick(Constants.REQUEST_CODE_DELETE_SEAT,position)
                }
            } else {
                holder.itemView.ivRemoveSeat.gone()
                holder.itemView.tvPickSeat.text = "Select Seat"
            }
        } else {
            holder.itemView.ivRemoveSeat.gone()
            holder.itemView.tvPickSeat.text = "Unavailable"
            holder.itemView.tvPickSeat.setTextColor(ContextCompat.getColor(context,R.color.colorGray))
        }

        holder.itemView.tvPickSeat.setOnClickListener {
            if (holder.itemView.tvPickSeat.text.equals("Select Seat")||holder.itemView.tvPickSeat.text.equals("Change")){
                onclick.onClick(Constants.REQUEST_CODE_SELECT_SEAT,position)

            }
        }
    }

    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(row)  {

    }
}