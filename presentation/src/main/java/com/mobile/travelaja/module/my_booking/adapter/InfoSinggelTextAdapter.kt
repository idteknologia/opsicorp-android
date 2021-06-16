package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_info_singgel_adapter.view.*

class InfoSinggelTextAdapter (var context: Context, private var items: ArrayList<String>): androidx.recyclerview.widget.RecyclerView.Adapter<InfoSinggelTextAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_info_singgel_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_description.text = data

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}