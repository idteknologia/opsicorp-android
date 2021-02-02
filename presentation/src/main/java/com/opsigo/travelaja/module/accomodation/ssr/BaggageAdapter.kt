package com.opsigo.travelaja.module.accomodation.ssr

import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.baggage_adapter.view.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.Constants
import android.view.LayoutInflater
import android.content.Context
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.view.View
import com.opsigo.travelaja.utility.Globals
import com.squareup.picasso.Picasso

class BaggageAdapter(context: Context): RecyclerView.Adapter<BaggageAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    var items = ArrayList<ResultListFlightModel>()
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.baggage_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        val dataProfile = Globals.getProfile(context)
        if (position==0){
            holder.itemView.tv_title_trip.text     = "Departure Flight"
        } else {
            holder.itemView.tv_title_trip.text     = "Arrival Flight"
        }
        holder.itemView.tv_city_code.text      = "${data.origin} - ${data.destination}"
        holder.itemView.tv_name_passanger.text = dataProfile.name
        Picasso.get()
                .load(data.imgAirline)
                .fit()
                .centerInside()
                .into(holder.itemView.img_airline)

        setDataRecycler(holder,data,position)
    }

    private fun setDataRecycler(holder: ViewHolder, data: ResultListFlightModel ,positionParent: Int) {
        val adapter by lazy { BaggageListAdapter(context) }
        val layoutManager = LinearLayoutManager(context)

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        holder.itemView.rv_list_item_bagage.layoutManager = layoutManager
        holder.itemView.rv_list_item_bagage.itemAnimator = DefaultItemAnimator()
        holder.itemView.rv_list_item_bagage.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    Constants.KEY_BAGGAGE_ITEM_SELECTED -> {
                        onclick.onClick(Constants.KEY_BAGGAGE_ITEM_SELECTED,positionParent,Constants.KEY_BAGGAGE_ITEM_SELECTED,position)
                        holder.itemView.tvTotalBaggage.text = data.dataSSR.dataBagage.get(position).ssrName

                    }
                }
            }
        })

        adapter.setData(data.dataSSR.dataBagage)

    }

    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}