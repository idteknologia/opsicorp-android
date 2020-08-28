package com.opsigo.travelaja.module.accomodation.ssr

import android.view.View
import com.opsigo.travelaja.R
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.list_ssr_item_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.DataSsr

class SsrListAdapter (context: Context): RecyclerView.Adapter<SsrListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<DataSsr>()
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_ssr_item_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_value_ssr.text  = data.pricing
        holder.itemView.tv_title_ssr.text  = data.ssrName

        holder.itemView.checkbox.isChecked = data.selected

        holder.itemView.setOnClickListener {
            onclick.onClick(Constants.KEY_CHECK_BOX_SSR,position)
        }
        holder.itemView.checkbox.setOnClickListener {
            onclick.onClick(Constants.KEY_CHECK_BOX_SSR,position)
        }
    }


    fun setData(data: ArrayList<DataSsr>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}