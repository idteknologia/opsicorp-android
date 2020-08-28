package com.opsigo.travelaja.module.item_custom.calendar

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.calendar_dialog_adapter.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarDialogAdapter (context: Context, private var items: ArrayList<Date>): RecyclerView.Adapter<CalendarDialogAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    var selected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.calendar_dialog_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        val cal = Calendar.getInstance()
        try {
            cal.time = data
        } catch (ex: Exception) {
            System.err.print(ex)
        }

        var holiday =  false
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dataHoliday = Constants.LIST_DATA_HOLIDAY.filter { it.dateholiday.after(items[0])&&it.dateholiday.before(items[items.size-1]) }
        if (Constants.LIST_DATA_HOLIDAY.isNotEmpty()){
            if (dataHoliday.isNotEmpty()){
                dataHoliday.forEach {
                    if (format.format(it.dateholiday).equals(format.format(data))){
                        holiday = true
                    }
                }
            }
        }

        if (cal[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY || holiday){
            holder.itemView.tv_date.setTextColor(context.resources.getColor(R.color.colorRedDateCalendar))
        }
        else {
            holder.itemView.tv_date.setTextColor(context.resources.getColor(R.color.gray_50_subtitle))
        }

        val formatter = SimpleDateFormat("dd")
        val formatterMount = SimpleDateFormat("MMM")
        val formatterNameDay = SimpleDateFormat("E")



        holder.itemView.tv_price.text = "70.120.000"
        holder.itemView.tv_mount.text = formatterMount.format(data.time)
        holder.itemView.tv_day_name.text = formatterNameDay.format(data.time)
        if (formatter.format(data.time).substring(0,1).equals("0")){
            holder.itemView.tv_date.text  = formatter.format(data.time).substring(1,2)
        }
        else{
            holder.itemView.tv_date.text  = formatter.format(data.time)
        }



        if (position==selected){
            holder.itemView.line_border_calendar.background = context.resources.getDrawable(R.drawable.rounded_calendar_selected)
        }
        else{
            holder.itemView.line_border_calendar.background = context.resources.getDrawable(R.drawable.rounded_calendar_deafult)
        }

        holder.itemView.setOnClickListener {
            selected = position
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<Date>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}