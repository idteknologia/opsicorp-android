package com.opsigo.travelaja.module.manage_trip.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.DateConverter

import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_manage_trip.view.*

import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter

class ManageTripAdapter (val context: Context, private var items: ArrayList<ApprovalModelAdapter>): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var month_temp_draft = ""
    var month_temp_completed = ""

    override fun getItemCount(): Int {
        return items.size
    }

    var showByApproval = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){

            VIEW_DRAFT -> DraftHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_manage_trip, parent, false))

            else -> CompletedHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_manage_trip_completed, parent, false))
        }

    }

    inner class CompletedHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text         = data.status
            itemView.tv_header_month.text   = DateConverter().setDateFormatMonthYear(data.header)
            itemView.tv_purpose.text        = data.title
            itemView.tv_date.text           = DateConverter().setDateFormat4(data.start_date) + " - " + DateConverter().setDateFormat4(data.end_date)
            itemView.tv_trip_code.text      = data.tripCode

            val month = DateConverter().setDateFormatMonthYear(data.header)

            if(month_temp_completed.equals(month)){
                itemView.tv_header_month.visibility = View.GONE
            }else{
                itemView.tv_header_month.visibility = View.VISIBLE
            }
            month_temp_completed = month

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }

            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }
        }
    }

    inner class DraftHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        var viewForeground : RelativeLayout = itemView.findViewById(R.id.parent_layout)

        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text             = data.status
            itemView.tv_status.visibility       = View.GONE
            itemView.tv_user_type.visibility    = View.GONE
            itemView.tv_header_month.text       = DateConverter().setDateFormatMonthYear(data.header)
            itemView.tv_purpose.text            = data.title
            itemView.tv_date.text               = DateConverter().setDateFormat4(data.start_date) + " - " + DateConverter().setDateFormat4(data.end_date)
            itemView.tv_city.text               = data.destination
            itemView.tv_trip_code.text          = data.tripCode


            val month = DateConverter().setDateFormatMonthYear(data.header)

            if(month_temp_draft.equals(month)){
                itemView.tv_header_month.visibility = View.GONE
            }else{
                itemView.tv_header_month.visibility = View.VISIBLE
            }
            month_temp_draft = month

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_DRAFT -> (holder as DraftHolder).bind(items[position],position)
            VIEW_COMPLETED -> (holder as CompletedHolder).bind(items[position],position)
        }
    }


    fun formatingDate(string: String):String{
        return DateConverter().setDateFormat4(string)
    }

    fun setData(data: java.util.ArrayList<ApprovalModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {

        val VIEW_DRAFT = 1
        val VIEW_COMPLETED = 2
    }


    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)


    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).status){
            "Draft" -> VIEW_DRAFT
            else -> VIEW_COMPLETED
        }
    }
}