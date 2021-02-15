package com.opsigo.travelaja.module.accomodation.seat.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.seat.model.SeatModel2
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView


class SeatAdapter2 (var context: Context, var items: ArrayList<SeatModel2>): RecyclerView.Adapter<SeatAdapter2.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_cabin_adapter_flight, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = items.get(position)

        /*if (data.selected&&data.actived) {
            holder.itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_button_seat_activated)
        }
        else if (data.selected) {
            holder.itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_button_seat_selected)
        }
        else {
            holder.itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_button_seat_default)
        }

        if (data.type=="bangku"){
            holder.itemView.line_seat.visibility = View.VISIBLE
            holder.itemView.titel_seat.visibility = View.VISIBLE
            holder.itemView.titel_seat.setText(data.namePassager)
        }else{
            holder.itemView.titel_seat.setText(data.seatNumberRow)
            holder.itemView.line_seat.background = context.resources.getDrawable(R.drawable.rounded_button_seat_transparant)
        }*/

        holder.itemView.setOnClickListener { v ->
            if(data.type!="bangku"){
                return@setOnClickListener
            }
            onclick.onClick(-1,position)
        }


    }

    fun setData(data: ArrayList<SeatModel2>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}