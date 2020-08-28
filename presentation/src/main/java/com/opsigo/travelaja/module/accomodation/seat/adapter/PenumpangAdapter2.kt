package com.opsigo.travelaja.module.accomodation.seat.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.seat.model.SeatModel2
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.penumpang_adater_view.view.*

class PenumpangAdapter2 (var context: Context, var items: ArrayList<SeatModel2>): RecyclerView.Adapter<PenumpangAdapter2.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.penumpang_adater_view, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = items.get(position)
        holder.itemView.tv_penumpang.text = data.namePassager

        if(data.type=="bangku"){
            holder.itemView.tv_penumpang.visibility = View.VISIBLE
            holder.itemView.line_parent.visibility = View.VISIBLE
        }else{
            holder.itemView.tv_penumpang.visibility = View.GONE
            holder.itemView.line_parent.visibility = View.GONE
        }

        if(data.actived&&data.selected&&data.namePassager.isNotEmpty()){
            holder.itemView.tv_penumpang.setTextColor(context.resources.getColor(R.color.white))
            holder.itemView.tv_penumpang.background = context.resources.getDrawable(R.drawable.rounded_button_seat_activated)
        }
        else if(!data.actived&&data.selected&&data.namePassager.isNotEmpty()) {
            holder.itemView.tv_penumpang.setTextColor(context.resources.getColor(R.color.black))
            holder.itemView.tv_penumpang.background = context.resources.getDrawable(R.drawable.rounded_button_seat_selected)
        }else if(!data.actived&&!data.selected&&data.namePassager.isEmpty()){
            holder.itemView.tv_penumpang.visibility = View.GONE
            holder.itemView.line_parent.visibility = View.GONE
        }else{
            holder.itemView.tv_penumpang.visibility = View.GONE
            holder.itemView.line_parent.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
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