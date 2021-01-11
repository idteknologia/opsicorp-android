package com.opsigo.travelaja.module.accomodation.ssr

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ssr_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class SsrAdapter(context: Context): RecyclerView.Adapter<SsrAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    var items = ArrayList<ResultListFlightModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SsrAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ssr_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.itemView.tv_airline_name.text     = "${data.origin} - ${data.destination}"
        if (data.dataSSR.dataDrink.isNullOrEmpty()||data.dataSSR.dataMeal.isNullOrEmpty()||data.dataSSR.dataOther.isNullOrEmpty()||data.dataSSR.dataSport.isNullOrEmpty()){
            holder.itemView.tvPickSsr.text = "Select SSR"
        } else {
            holder.itemView.tvPickSsr.text = "Change"
        }
        holder.itemView.tvSelectedSsr.text = Resources.getSystem().getString(R.string.selectedSsr)

        if (data.imgAirline.isNotEmpty()){
            Picasso.get()
                    .load(data.imgAirline)
                    .fit()
                    .into(holder.itemView.img_airline)
        }

        holder.itemView.tvPickSsr.setOnClickListener {

        }
    }


    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}