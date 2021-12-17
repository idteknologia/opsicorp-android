package com.mobile.travelaja.module.approval.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.activity_review_budget.*
import opsigo.com.datalayer.model.listtripplan.StatusTrip
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import kotlinx.android.synthetic.main.approval_adapter.view.*
import opsigo.com.datalayer.mapper.Serializer


class ApprovalAdapter (val context: Context, private var items: ArrayList<ApprovalModelAdapter>): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    var showByApproval = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_WAITING_APPROVAL_AVAILABLE_REJECT -> WaitingAdapterAvailbaleReject(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.approval_adapter, parent, false))

            VIEW_WAITING_APPROVAL -> WaitingAdapter(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approval_adapter, parent, false))

            else -> ListApprovalAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.approval_adapter, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_WAITING_APPROVAL_AVAILABLE_REJECT -> (holder as WaitingAdapterAvailbaleReject).bind(items[position],position)
            VIEW_WAITING_APPROVAL -> (holder as WaitingAdapter).bind(items[position],position)
            VIEW_LIST_APPROVAL -> (holder as ListApprovalAdapter).bind(items[position],position)
        }
    }

    fun formatingDate(string: String):String{
        return DateConverter().setDateFormat4(string)
    }

    inner class WaitingAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        var viewForeground : RelativeLayout = itemView.findViewById(R.id.parent_layout)

        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text = data.status
            itemView.tv_title.text = data.title
            itemView.tv_trip_code.text = data.tripCode
            itemView.tv_date.text = formatingDate(data.start_date) + " - " + formatingDate(data.end_date)
            if (data.routes.isNotEmpty()) {
                if (data.routes.size == 1) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination}"
                } else if (data.routes.size == 2) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination}"
                } else if (data.routes.size == 3) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination}"
                } else if (data.routes.size == 4) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination}"
                } else if (data.routes.size == 5) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination} - ${data.routes[4].destination}"
                } else {
                    itemView.tv_city.text = data.routes.last().destination
                }
            }
            itemView.tv_expired.text = data.timeExperied

            if(data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }
            else if(data.isApproval&&!data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.participant)} +${data.participant.size}"
            }
            else if(!data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }

//            itemView.setOnLongClickListener {
//                onclick.onClick(-109,position)
//                true// returning true instead of false, works for me
//            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }

            if ("Waiting".equals(data.status)){
                itemView.tv_expired.visibility = View.VISIBLE
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorOrangeApproval))
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_orange)
            }

            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }


            if (data.participant.size>1){
                itemView.line_singgel_image.visibility = View.GONE
                itemView.img_participant_second.visibility = View.VISIBLE

            }
            else{
                itemView.line_singgel_image.visibility = View.VISIBLE
                itemView.img_participant_second.visibility = View.GONE

            }


            if(position==items.size-1){
                itemView.line_border_bottom.visibility = View.VISIBLE
            }
            else{
                itemView.line_border_bottom.visibility = View.GONE
            }
        }

    }

    inner class WaitingAdapterAvailbaleReject internal constructor(itemView: View) : ViewHolder(itemView) {

        var viewForeground : RelativeLayout = itemView.findViewById(R.id.parent_layout)

        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text = data.status
            itemView.tv_title.text = data.title
            itemView.tv_trip_code.text = data.tripCode
            itemView.tv_date.text = formatingDate(data.start_date) + " - " + formatingDate(data.end_date)
            if (data.routes.isNotEmpty()) {
                if (data.routes.size == 1) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination}"
                } else if (data.routes.size == 2) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination}"
                } else if (data.routes.size == 3) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination}"
                } else if (data.routes.size == 4) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination}"
                } else if (data.routes.size == 5) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination} - ${data.routes[4].destination}"
                } else {
                    itemView.tv_city.text = data.routes.last().destination
                }
            }
            itemView.tv_expired.text = data.timeExperied

            if(data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }
            else if(data.isApproval&&!data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.participant)} +${data.participant.size}"
            }
            else if(!data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }

            itemView.setOnLongClickListener {
                onclick.onClick(-109,position)
                true// returning true instead of false, works for me
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }

            if ("Waiting".equals(data.status)){
                itemView.tv_expired.visibility = View.VISIBLE
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorOrangeApproval))
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_orange)
            }

            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }


            if (data.participant.size>1){
                itemView.line_singgel_image.visibility = View.GONE
                itemView.img_participant_second.visibility = View.VISIBLE

            }
            else{
                itemView.line_singgel_image.visibility = View.VISIBLE
                itemView.img_participant_second.visibility = View.GONE

            }
            if(position==items.size-1){
                itemView.line_border_bottom.visibility = View.VISIBLE
            }
            else{
                itemView.line_border_bottom.visibility = View.GONE
            }
        }

    }

    inner class ListApprovalAdapter internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text = data.status
            itemView.tv_title.text = data.title
            itemView.tv_trip_code.text = data.tripCode
            itemView.tv_date.text = formatingDate(data.start_date) + " - " + formatingDate(data.end_date)
            if (data.routes.isNotEmpty()){
                if (data.routes.size == 1) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination}"
                } else if (data.routes.size == 2) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination}"
                } else if (data.routes.size == 3) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination}"
                } else if (data.routes.size == 4) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination}"
                } else if (data.routes.size == 5) {
                    itemView.tv_city.text = "${data.routes[0].origin} - ${data.routes[0].destination} - ${data.routes[1].destination} - ${data.routes[2].destination} - ${data.routes[3].destination} - ${data.routes[4].destination}"
                } else {
                    itemView.tv_city.text = data.routes.last().destination
                }
            } else {
                itemView.tv_city.text = data.destination
            }
            itemView.tv_expired.text = data.timeExperied

            val idUser = Globals.getProfile(context).employId

            data.listApproval.forEachIndexed { index, participantModel ->
                if (idUser.equals(participantModel.id)){
                    showByApproval = true
                }
            }

            if(data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }
            else if(data.isApproval&&!data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.participant)} +${data.participant.size}"
            }
            else if(!data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "${itemView.context.getString(R.string.approver)} +${data.listApproval.size}"
            }


            itemView.setOnLongClickListener {
                onclick.onClick(-109,position)
                true// returning true instead of false, works for me
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }

            if( "Completely Approved".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorGreenApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_green)
            }
            else if("Completely Rejected".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorRedApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_red)
            }
            else if("Partially Approved".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorGreenApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_green)
            }
            else if("Partially Rejected".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorRedApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_red)
            }
            else if (StatusTrip.Draft.equals(data.status)){
                itemView.tv_expired.visibility = View.VISIBLE
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorGrayRound))
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_gray)
            }
            else if ("Canceled".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorRedApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_red)
            }
            else if ("Expired".equals(data.status)){
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorRedApproval))
                itemView.tv_expired.visibility = View.GONE
                itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_red)
            }

            if(data.selected == true){
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
                itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            }
            else{
                itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
            }


            if (data.participant.size>1){
                itemView.line_singgel_image.visibility = View.GONE
                itemView.img_participant_second.visibility = View.VISIBLE

//                Picasso.get()
//                        .load(data.participant.get(0).image)
//                        .into(itemView.img_participant_first)
//
//                Picasso.get()
//                        .load(data.participant.get(1).image)
//                        .into(itemView.img_participant_second)
            }
            else{
                itemView.line_singgel_image.visibility = View.VISIBLE
                itemView.img_participant_second.visibility = View.GONE

//                Picasso.get()
//                        .load(data.participant.get(0).image)
//                        .into(itemView.img_participant_first)
            }


            if(position==items.size-1){
                itemView.line_border_bottom.visibility = View.VISIBLE
            }
            else{
                itemView.line_border_bottom.visibility = View.GONE
            }
        }

    }

    fun setData(data: ArrayList<ApprovalModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {
        val VIEW_WAITING_APPROVAL_AVAILABLE_REJECT = 0
        val VIEW_WAITING_APPROVAL = 1
        val VIEW_LIST_APPROVAL = 2
    }

    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        if (items.get(position).status=="Waiting"){
            if (items.get(position).isApproval){
                return VIEW_WAITING_APPROVAL_AVAILABLE_REJECT
            }
            else {
                return VIEW_WAITING_APPROVAL
            }
        }
        else {
            return VIEW_LIST_APPROVAL
        }
    }
}