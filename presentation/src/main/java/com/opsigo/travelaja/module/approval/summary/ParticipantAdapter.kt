package com.opsigo.travelaja.module.approval.summary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso

import java.util.ArrayList

class ParticipantAdapter (val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    var items: ArrayList<ParticipantModel> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_TYPE_APROVAL -> ParticipantHolderApproval(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_participant_list, parent, false))

            else            -> ParticipantHolderParticipant(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_participant_list, parent, false))
        }
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (items[position].isApproval){
            true -> {
                (holder as ParticipantHolderApproval).bind(items[position],position)
            }
            else -> {
                (holder as ParticipantHolderParticipant).bind(items[position],position)
            }
        }
    }

    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    fun setData(data: ArrayList<ParticipantModel>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).isApproval){
            true        -> VIEW_TYPE_APROVAL
            else        -> VIEW_TYPE_PARTICIPANT
        }
    }

    inner class ParticipantHolderParticipant internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img_participant         :ImageView= itemView.findViewById(R.id.img_participant)
        var tv_job_title            :TextView = itemView.findViewById(R.id.tv_jobtitle)
        var tv_name                 :TextView = itemView.findViewById(R.id.tv_name)
        var tv_budget_name          :TextView = itemView.findViewById(R.id.tv_budget_name)
        var ic_chevron_next_orange  : ImageView = itemView.findViewById(R.id.btn_next)
        var tv_status               :TextView = itemView.findViewById(R.id.tv_status)
        var line_border             :LinearLayout = itemView.findViewById(R.id.line_border_bottom)



        fun bind(data: ParticipantModel, position: Int) {
            tv_job_title.text       = data.jobtitle
            tv_status.text          = data.status
            tv_budget_name.text     = data.budgetCode + " - " + data.budgetName
            tv_name.text            = data.name

            tv_budget_name.visibility = View.VISIBLE
            ic_chevron_next_orange.visibility = View.VISIBLE
            tv_status.visibility = View.VISIBLE

            if (data.status.toLowerCase().contains("reject")||data.status.toLowerCase().contains("expired")){
                tv_status.setTextColor(context.resources.getColor(R.color.colorRed))
            }
            if (data.status.toLowerCase().contains("approved")){
                tv_status.setTextColor(context.resources.getColor(R.color.colorGreenApproval))
            }
            else {
                tv_status.setTextColor(context.resources.getColor(R.color.colorYellowButton))
            }


            if (data.imgUrl.isNotEmpty()){
                Picasso.get()
                        .load(data.imgUrl)
                        .fit()
                        .centerCrop()
                        .into(img_participant)
            }

            if (items.size==1){
                line_border.visibility = View.VISIBLE
            }
            else{
                if (position==items.size-1){
                    line_border.visibility = View.VISIBLE
                }
                else{
                    line_border.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }
        }

    }

    inner class ParticipantHolderApproval internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img_participant         :ImageView= itemView.findViewById(R.id.img_participant)
        var tv_job_title            :TextView = itemView.findViewById(R.id.tv_jobtitle)
        var tv_name                 :TextView = itemView.findViewById(R.id.tv_name)
        var line_border             :LinearLayout = itemView.findViewById(R.id.line_border_bottom)
        var tv_budget_name          :TextView = itemView.findViewById(R.id.tv_budget_name)
        var ic_chevron_next_orange  :ImageView = itemView.findViewById(R.id.btn_next)
        var tv_status               :TextView = itemView.findViewById(R.id.tv_status)


        fun bind(data: ParticipantModel, position: Int) {
            tv_job_title.text       = data.jobtitle
            tv_status.text          = data.status
            tv_budget_name.text     = data.budgetCode + " - " + data.budgetName
            tv_name.text            = data.name

            tv_budget_name.visibility = View.GONE
            ic_chevron_next_orange.visibility = View.GONE
            tv_status.visibility = View.GONE

            if (data.imgUrl.isNotEmpty()){
                Picasso.get()
                        .load(data.imgUrl)
                        .fit()
                        .centerCrop()
                        .into(img_participant)
            }



            if (items.size==1){
                line_border.visibility = View.VISIBLE
            }
            else{
                if (position==items.size-1){
                    line_border.visibility = View.VISIBLE
                }
                else{
                    line_border.visibility = View.GONE
                }
            }
        }

    }

    companion object {

        val VIEW_TYPE_APROVAL      = 1
        val VIEW_TYPE_PARTICIPANT  = 2

    }

}