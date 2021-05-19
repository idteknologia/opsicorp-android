package com.opsigo.travelaja.module.item_custom.dialog_cabin_class

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.cabin_class_item.view.*
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel

class CabinClassAdapter(private var items: ArrayList<FilterFlightModel>): androidx.recyclerview.widget.RecyclerView.Adapter<CabinClassAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var check = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cabin_class_item, parent, false)

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

        holder.itemView.radioButton.isChecked = position == check

        holder.itemView.tvCabinClass.text = data.name


        holder.itemView.setOnClickListener {
            check = position
            notifyDataSetChanged()
            onclick.onClick(-1,position)
        }

        holder.itemView.radioButton.setOnClickListener {
            check = position
            notifyDataSetChanged()
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<FilterFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}