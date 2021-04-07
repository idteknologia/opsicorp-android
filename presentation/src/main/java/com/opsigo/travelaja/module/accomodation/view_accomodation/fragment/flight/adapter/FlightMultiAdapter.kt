package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.gone
import com.opsigo.travelaja.utility.visible
import kotlinx.android.synthetic.main.flight_multi_city_item.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel

class FlightMultiAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<FlightMultiAdapter.ViewHolder>() {

    lateinit var onClick : OnclickListenerRecyclerView
    var items = ArrayList<OrderAccomodationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.flight_multi_city_item, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onClick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        if (items.size > 2){
            holder.itemView.tvRemoveCard.visible()
        } else {
            holder.itemView.tvRemoveCard.gone()
        }
        holder.itemView.tv_departur_date.text = data.dateDeparture
        if (data.idOrigin.isNotEmpty()){
            holder.itemView.tv_from.text = "${data.originName} (${data.idOrigin})"
        } else {
            holder.itemView.tv_from.text = data.originName
        }
        if (data.idDestination.isNotEmpty()){
            holder.itemView.tv_to.text = "${data.destinationName} (${data.idDestination})"
        } else {
            holder.itemView.tv_to.text = data.destinationName
        }
        holder.itemView.tvFlightNumberMulti.text = "Flight ${position+1}"
        holder.itemView.tv_from.setOnClickListener {
            onClick.onClick(Constants.REQUEST_CODE_SELECT_FROM_MULTI,position)
        }
        holder.itemView.tv_to.setOnClickListener {
            onClick.onClick(Constants.REQUEST_CODE_SELECT_TO_MULTI,position)
        }
        holder.itemView.tv_departur_date.setOnClickListener {
            onClick.onClick(Constants.REQUEST_CODE_SELECT_DEPARTURE,position)
        }
        holder.itemView.btn_switch.setOnClickListener {
            onClick.onClick(Constants.REQUEST_CODE_SWITCH_DATA,position)
        }
        holder.itemView.tvRemoveCard.setOnClickListener {
            onClick.onClick(Constants.REQUEST_CODE_DELETE_MULTI,position)
        }
    }

    fun setData(data: ArrayList<OrderAccomodationModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)

}