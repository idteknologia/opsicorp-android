package com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.gone
import kotlinx.android.synthetic.main.item_participant_list.view.*
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain

class ApproverCustomAdapter (val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<ApproverCustomAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items: ArrayList<ParticipantModelDomain> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_participant_list, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.btn_next.gone()
        holder.itemView.tv_jobtitle.text = data.positionName
        holder.itemView.tv_name.text = data.name
        holder.itemView.tv_budget_name.text = data.positionId
        if (data.isPjs.equals(true)){
            holder.itemView.tv_status.text = "PJS"
            holder.itemView.tv_status.setTextColor(Color.parseColor("#939396"))
        } else {
            holder.itemView.tv_status.text = "NON-PJS"
            holder.itemView.tv_status.setTextColor(Color.parseColor("#939396"))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(data: ArrayList<ParticipantModelDomain>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}