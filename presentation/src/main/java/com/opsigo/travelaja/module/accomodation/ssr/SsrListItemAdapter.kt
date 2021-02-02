package com.opsigo.travelaja.module.accomodation.ssr

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.list_ssr_item_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.DataSsrModel

class SsrListItemAdapter (context: Context): RecyclerView.Adapter<SsrListItemAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<DataSsrModel>()
    val context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_ssr_item_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        if (data.pricing.equals("0.0")){
            holder.itemView.tv_value_ssr.text = "Free"
        } else {
            holder.itemView.tv_value_ssr.text = "IDR ${Globals.currencyIDRFormat(data.pricing.toDouble())}"
        }
        holder.itemView.tv_title_ssr.text  = data.ssrName

        holder.itemView.checkbox.isChecked = data.selected

        holder.itemView.setOnClickListener {
            onclick.onClick(Constants.KEY_CHECK_BOX_SSR,position)
        }
        holder.itemView.checkbox.setOnClickListener {
            onclick.onClick(Constants.KEY_CHECK_BOX_SSR,position)
        }
    }

    fun setData(data: ArrayList<DataSsrModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}