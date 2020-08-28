package com.opsigo.travelaja.module.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.home.model.UpcommingFlightModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_upcoming_event.view.*

class UpcommingFlightAdapter (var context: Context, var items: ArrayList<UpcommingFlightModel>): RecyclerView.Adapter<UpcommingFlightAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_upcoming_event, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (position==0){
            holder.itemView.line_first.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_first.visibility = View.GONE
        }

        holder.itemView.tv_day_number.text  = data.daysNumber
        holder.itemView.tv_mount.text       = data.mount
        holder.itemView.tv_day_text1.text   = "S"
        holder.itemView.tv_day_text2.text   = "UN"
        holder.itemView.tv_today.text       = data.today
        holder.itemView.tv_time.text        = " - "+data.time
        holder.itemView.tv_destination.text = data.airportName
        holder.itemView.tv_tipe_tour.text   = data.typeTour

        if(position==0){
            holder.itemView.tv_today.setTextColor(context.resources.getColor(R.color.colorRed))
        }

    }

    fun setData(data: ArrayList<UpcommingFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        init {

        }
    }
}