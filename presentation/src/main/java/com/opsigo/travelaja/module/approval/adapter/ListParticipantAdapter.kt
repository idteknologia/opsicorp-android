package com.opsigo.travelaja.module.approval.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.approval.summary.ParticipantModel
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_participant_list_dialog.view.*
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain

class ListParticipantAdapter (context: Context, private var items: ArrayList<ParticipantModel>): RecyclerView.Adapter<ListParticipantAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_participant_list_dialog, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_name.text = data.name
        holder.itemView.tv_jobtitle.text = data.jobtitle

        conditionApprove(holder.itemView,position,data.approval)

        data.approval.forEach {
            Globals.setLog(it.name)
        }

    }

    private fun conditionApprove(itemView: View, position: Int, data: java.util.ArrayList<ParticipantModelDomain>) {

        val positionList = data.indexOf(data.filter { it.employId == Globals.getProfile(context).employId }.first())

        if (positionList>0){
            if (data[positionList-1].layer == data[positionList].layer){
                if (data[positionList-1].isCompletelyReviewed){
                    println("approve done")
                    doneApprove(itemView)
                }
                else {
                    println("show approve")
                    showApprove(itemView,position)
                }
            }
            else {
                if (data[positionList-1].isCompletelyReviewed){
                    println("show approve")
                    showApprove(itemView,position)
                }
                else{
                    println("disable approve")
                    disableApprove(itemView)
                }
            }
        }
        else {
            if (data.size>1){
                if (data[positionList+1].layer == data[positionList].layer){
                    if (data[positionList+1].isCompletelyReviewed){
                        doneApprove(itemView)
                        println("approve done")
                    }
                    else{
                        showApprove(itemView,position)
                        println("show approve")
                    }
                }
                else {
                    showApprove(itemView,position)
                    println("show approve")
                }
            }
            else{
                if (data[positionList].isCompletelyReviewed){
                    doneApprove(itemView)
                    println("approve done")
                }
                else{
                    showApprove(itemView,position)
                    println("show approve")
                }
            }
        }
    }

    private fun disableApprove(itemView: View) {
        itemView.tv_status_approve.visibility = View.GONE
        itemView.img_check.visibility = View.VISIBLE
        itemView.img_check.setImageResource((R.drawable.ic_time_red))
    }

    private fun doneApprove(itemView: View) {
        itemView.tv_status_approve.visibility = View.VISIBLE
        itemView.img_check.visibility = View.GONE
    }

    private fun showApprove(itemView: View,position: Int) {
        itemView.tv_status_approve.visibility = View.GONE
        itemView.img_check.visibility = View.VISIBLE
        itemView.img_check.setImageResource((R.drawable.ic_checklist_green))
        onclick.onClick(-89,position)
    }

    //    fun setData(data: java.util.ArrayList<String>) {
    fun setData(data: java.util.ArrayList<ParticipantModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}