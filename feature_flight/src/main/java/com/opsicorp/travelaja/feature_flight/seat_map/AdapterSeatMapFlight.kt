package com.opsicorp.travelaja.feature_flight.seat_map

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.seat_adapter_view_flight.view.*
import kotlinx.android.synthetic.main.seatmap_number_adapter_view.view.*
import kotlinx.android.synthetic.main.seatmap_spacing_center_adapter_view.view.*
import kotlinx.android.synthetic.main.seatmap_spacing_left_adapter_view.view.*
import opsigo.com.domainlayer.model.accomodation.flight.SeatFlightModel

class AdapterSeatMapFlight (val context: Context, private var items: ArrayList<SeatFlightModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){

            VIEW_SEAT -> SeatAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seat_adapter_view_flight, parent, false))

            VIEW_NUMBER -> NumberAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seatmap_number_adapter_view, parent, false))

            VIEW_SPACING_CENTER -> SpacingCenterAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seatmap_spacing_center_adapter_view, parent, false))

            else -> SpacingAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seatmap_spacing_left_adapter_view, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_SEAT -> (holder as SeatAdapter).bind(items[position],position)
            VIEW_NUMBER -> (holder as NumberAdapter).bind(items[position],position)
            VIEW_SPACING_CENTER -> (holder as SpacingCenterAdapter).bind(items[position],position)
            VIEW_SPACING -> (holder as SpacingAdapter).bind(items[position],position)
        }
    }

    inner class SeatAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: SeatFlightModel, position: Int) {

            itemView.titel_seat.text = data.numberSeat


            if (data.status.equals( Constants.SelectSeat)){
                itemView.titel_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_selected)
                itemView.titel_seat.setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            else if(data.status.equals(Constants.PickSeat)){
                itemView.titel_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_pick)
                itemView.titel_seat.setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            else if(data.status.equals(Constants.SoldSeat)){
                itemView.titel_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_occupied)
                itemView.titel_seat.setTextColor(context.resources.getColor(R.color.colorGrayRound))
            }
            else if(data.status.equals(Constants.AvailableSeat)){
                itemView.titel_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_available)
                itemView.titel_seat.setTextColor(context.resources.getColor(R.color.colorGray))
            }

            when (data.status){
                Constants.SelectSeat -> {
                    itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_selected)
                }
                Constants.PickSeat -> {
                    itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_pick)
                }
                Constants.SoldSeat -> {
                    itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_occupied)
                }
                Constants.AvailableSeat -> {
                    itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_seatmap_available)
                }
            }

            itemView.titel_seat.setOnClickListener {
                onclick.onClick(-3,position)
            }

            itemView.line_seat.setOnClickListener {
                onclick.onClick(-3,position)
            }
        }

    }

    inner class NumberAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: SeatFlightModel, position: Int) {
            itemView.tv_number.text = data.number
        }

    }

    inner class SpacingAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: SeatFlightModel, position: Int) {
            itemView.textSpaceLeft.text = "2"
        }

    }

    inner class SpacingCenterAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: SeatFlightModel, position: Int) {
            itemView.textSpaceCenter.text = "1"
        }

    }

    fun setData(data: java.util.ArrayList<SeatFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {
        val VIEW_SEAT = 1
        val VIEW_NUMBER = 2
        val VIEW_SPACING = 3
        val VIEW_SPACING_CENTER =4
    }

    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).type){
            "SEAT" -> VIEW_SEAT
            "NUMBER"-> VIEW_NUMBER
            "SPACING_CENTER" -> VIEW_SPACING_CENTER
            else -> VIEW_SPACING
        }
    }
}