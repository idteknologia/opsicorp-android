package com.opsicorp.sliderdatepicker.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.opsicorp.sliderdatepicker.R
import com.opsicorp.sliderdatepicker.utils.Constant
import androidx.core.content.res.ResourcesCompat
import com.opsicorp.sliderdatepicker.model.DayDataModel
import kotlinx.android.synthetic.main.item_date_view.view.*

class DateAdapter (var context: Context,var items: ArrayList<DayDataModel>): androidx.recyclerview.widget.RecyclerView.Adapter<DateAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_item_date.setText(data.day.toString())

        if (Constant.fontDay!=-1){
            try {
                val typefaceTitle = ResourcesCompat.getFont(context, Constant.fontDay)
                holder.itemView.tv_item_date.setTypeface(typefaceTitle)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        if (!Constant.selectBeforeDay){
            if (data.date.before(Constant.minDate)&&(SimpleDateFormat(Constant.formatDate).format(Constant.minDate)!=data.fullDay)){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
            }
            else if (data.date.after(Constant.maxDate)){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
            }
            else{
                ChangeColorDate(holder,position,data)
            }
        }
        else {
            ChangeColorDate(holder,position,data)
        }

    }

    private fun ChangeColorDate(holder: ViewHolder, position: Int, data: DayDataModel) {
        if (data.typeDay==Constant.DAY_NEXT_MONTH||data.typeDay==Constant.DAY_PREVIOUS_MONTH){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
        }
        else if(data.typeDay==Constant.DAY_SUNDAY||data.typeDay==Constant.DAY_HOLIDAY){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHoliday))
        }
        else {
            if (Constant.startSelectDate.isNotEmpty()){
                if (data.fullDay==Constant.startSelectDate){
                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                }
                else {
                    if (Constant.TYPE_SELECTED==Constant.SINGGLE_SELECTED){
                        holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                    }
                    else{
                        if (Constant.endSelectDate.isNotEmpty()){
                            if (data.date.after(SimpleDateFormat(Constant.formatDate).parse(Constant.startSelectDate))&&data.date.before(SimpleDateFormat("dd-MM-yyyy").parse(Constant.endSelectDate))){
                                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                            }
                            else if(data.fullDay==Constant.endSelectDate){
                                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                            }
                            else {
                                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                            }
                        }
                        else {
                            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                        }
                    }
                }
            }
            else {
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
            }
        }

        holder.itemView.setOnClickListener {
            onclick.callbackRecyclerView(-1,position)
        }
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}