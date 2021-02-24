package com.opsicorp.travelaja.feature_flight.ssr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ssr_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class SsrAdapter(context: Context): RecyclerView.Adapter<SsrAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<ResultListFlightModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SsrAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ssr_adapter, parent, false)

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


        if (!data.dataSSR.dataSsr.filter {
                    it.ssrFlightNumber == data.flightNumber
                }.isNullOrEmpty()){
            if (data.dataSSR.ssrSelected.isNotEmpty()){
                holder.itemView.rlSelectedSsr.visible()
                holder.itemView.ivRemoveSsr.visible()
                holder.itemView.ivRemoveSsr.setOnClickListener {
                    onclick.onClick(Constants.REQUEST_CODE_DELETE_SSR,position)
                }
                holder.itemView.tvPickSsr.text = "Change"
                var selectedSsr = ""
                data.dataSSR.ssrSelected.forEach {
                    selectedSsr = selectedSsr + "${it.ssrName}, "
                }
                holder.itemView.tvSelectedSsr.text = selectedSsr.substring(0,selectedSsr.length-1)
            } else {
                holder.itemView.rlSelectedSsr.gone()
                holder.itemView.ivRemoveSsr.gone()
                holder.itemView.tvPickSsr.text = "Select SSR"
            }
        } else {
            holder.itemView.ivRemoveSsr.gone()
            holder.itemView.tvPickSsr.text = "Unavailable"
            holder.itemView.tvPickSsr.setTextColor(ContextCompat.getColor(context,R.color.colorGray))
        }

        if (data.imgAirline.isNotEmpty()){
            Picasso.get()
                    .load(data.imgAirline)
                    .fit()
                    .into(holder.itemView.img_airline)
        }

        holder.itemView.tvPickSsr.setOnClickListener {
            if (holder.itemView.tvPickSsr.text.equals("Select SSR")||holder.itemView.tvPickSsr.text.equals("Change")){
                onclick.onClick(Constants.REQUEST_CODE_SELECT_SSR,position)
            }
        }
    }


    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}