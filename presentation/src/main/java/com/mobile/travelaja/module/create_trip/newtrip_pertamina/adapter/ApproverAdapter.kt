package com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.gone
import kotlinx.android.synthetic.main.item_participant_list.view.*
import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalModel

class ApproverAdapter (val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<ApproverAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items: ArrayList<TravelRequestApprovalModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_participant_list, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        val dataProfile = Globals.getProfile(context)

        holder.itemView.btn_next.gone()
        holder.itemView.tv_jobtitle.text = data.profile.positionText
        holder.itemView.tv_name.text = data.profile.name
        holder.itemView.tv_budget_name.text = data.profile.email
        if (dataProfile.approval.travelRequestApproval.isNotEmpty()){
            if (dataProfile.approval.travelRequestApproval[position].isPjs){
                holder.itemView.tv_status.text = "PJS"
            } else {
                holder.itemView.tv_status.text = "NON-PJS"
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(data: ArrayList<TravelRequestApprovalModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}