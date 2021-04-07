package com.opsicorp.travelaja.feature_flight.ssr

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.travelaja.feature_flight.R
import kotlinx.android.synthetic.main.ssr_price_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class SsrPriceAdapter(context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<SsrPriceAdapter.ViewHolder>() {

    var items = ArrayList<ResultListFlightModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ssr_price_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.itemView.tv_title_trip.text = "${data.origin} - ${data.destination}"

        setDataRecycler(holder,data,position)
    }

    private fun setDataRecycler(holder: ViewHolder, data: ResultListFlightModel, position: Int) {
        val adapter by lazy {SsrPriceListAdapter(context)}
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        holder.itemView.rvPriceListSsr.layoutManager = layoutManager
        holder.itemView.rvPriceListSsr.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        holder.itemView.rvPriceListSsr.adapter = adapter

        adapter.setData(data.dataSSR.ssrSelected)
    }


    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {
    }
}