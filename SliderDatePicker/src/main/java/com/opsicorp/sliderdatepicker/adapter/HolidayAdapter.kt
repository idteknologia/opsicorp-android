package com.khoiron.sliderdatepicker.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.khoiron.sliderdatepicker.R
import android.support.v7.widget.RecyclerView
import com.khoiron.sliderdatepicker.model.HolidayModel
import kotlinx.android.synthetic.main.item_holiday_view.view.*

class HolidayAdapter (var context: Context, var items: ArrayList<HolidayModel>): RecyclerView.Adapter<HolidayAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_day_holiday.text   = data.day
        holder.itemView.tv_title_holiday.text = data.name
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}