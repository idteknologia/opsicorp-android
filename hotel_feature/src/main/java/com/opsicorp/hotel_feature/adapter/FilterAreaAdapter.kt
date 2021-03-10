package com.opsicorp.hotel_feature.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_filter_area.view.*

class FilterAreaAdapter (context: Context, private var items: ArrayList<String>): RecyclerView.Adapter<FilterAreaAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context
    var checkedPosition = 0

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filter_area, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_name.text = data
        holder.itemView.rb_area.isChecked = position == checkedPosition

        /*holder.itemView.setOnClickListener {

        }*/
        holder.itemView.btn_submit.setOnClickListener {
            checkedPosition = position
            onclick.onClick(Constants.ONCLICK_AREA_HOTEL,position)
        }
    }

    fun setData(data: ArrayList<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}