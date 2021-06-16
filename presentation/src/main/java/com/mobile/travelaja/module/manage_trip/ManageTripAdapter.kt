package com.mobile.travelaja.module.manage_trip

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_manage_trip.view.*
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter



class ManageTripAdapter (context: Context, private var items: ArrayList<ApprovalModelAdapter>): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){

            VIEW_WAITING_APPROVAL -> DraftHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_manage_trip, parent, false))

            else -> CompletedHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_manage_trip_completed, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_WAITING_APPROVAL -> (holder as DraftHolder).bind(items[position],position)
            VIEW_LIST_APPROVAL -> (holder as CompletedHolder).bind(items[position],position)
        }
    }

    inner class DraftHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        var viewForeground : RelativeLayout = itemView.findViewById(R.id.parent_layout)

        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_user_type.visibility    = View.GONE
            itemView.tv_header_month.text       = data.header
            itemView.tv_purpose.text            = data.title
            itemView.tv_date.text               = data.time
            itemView.tv_city.text               = data.destination

            if(position == 1) {
                itemView.tv_header_month.visibility = View.GONE
            }

            Log.d("puropse1","" + data.title + " - " + data.isApproval + " - " + data.status)

            if(data.isApproval){
                itemView.tv_user_type.visibility = View.VISIBLE
            }

            itemView.setOnLongClickListener {
                onclick.onClick(-109,position)
                true// returning true instead of false, works for me
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }


            if ("Draft".equals(data.status)){
                //itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_gray)
            }


            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }

//            if(position==items.size-1){
//                itemView.line_border_bottom.visibility = View.VISIBLE
//            }
//            else{
//                itemView.line_border_bottom.visibility = View.GONE
//            }
        }

    }

    inner class CompletedHolder internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: ApprovalModelAdapter, position: Int) {

            //itemView.tv_status.text = data.status
            itemView.tv_header_month.text       = data.header
            itemView.tv_purpose.text            = data.title
            itemView.tv_date.text               = data.time
            itemView.tv_trip_code.text          = data.tripCode
            //itemView.tv_expired.text    = data.timeExperied


            itemView.setOnLongClickListener {
                onclick.onClick(-109,position)
                true// returning true instead of false, works for me
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }

            if ("Completed".equals(data.status)){
                //itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_green)
            }

            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }

        }

    }

    fun setData(data: java.util.ArrayList<ApprovalModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {

        val VIEW_WAITING_APPROVAL = 1
        val VIEW_LIST_APPROVAL = 2
    }


    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)


    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).status){
            "Draft" -> VIEW_WAITING_APPROVAL
            else ->    VIEW_LIST_APPROVAL
        }
    }


}