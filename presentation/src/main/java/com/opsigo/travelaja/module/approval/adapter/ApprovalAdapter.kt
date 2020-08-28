package com.opsigo.travelaja.module.approval.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.datalayer.model.StatusTrip
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import kotlinx.android.synthetic.main.approval_adapter.view.*


class ApprovalAdapter (val context: Context, private var items: ArrayList<ApprovalModelAdapter>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    var showByApproval = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){

            VIEW_WAITING_APPROVAL -> WaitingAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.approval_adapter, parent, false))

            else -> ListApprovalAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.approval_adapter, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
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
            itemView.tv_city.text = data.destination
            itemView.tv_expired.text = data.timeExperied
            Log.e("TAG === ",data.timeExperied)

//            val idUser = Globals.getProfile(context).emplaoyId

            /*Log.e("TAAAG","${idUser}")

            data.listApproval.forEachIndexed { index, participantModel ->
                if (idUser.equals(participantModel.id)){
                    showByApproval = true
                }
            }*/

            if(data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "Approver +${data.listApproval.size}"
            }
            else if(data.isApproval&&!data.isParticipant){
                itemView.tv_total_layer_approval.text = "Participant +${data.participant.size}"
            }
            else if(!data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "Approver +${data.listApproval.size}"
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

    inner class ListApprovalAdapter internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: ApprovalModelAdapter, position: Int) {

            itemView.tv_status.text = data.status
            itemView.tv_title.text = data.title
            itemView.tv_trip_code.text = data.tripCode
            itemView.tv_date.text = formatingDate(data.start_date) + " - " + formatingDate(data.end_date)
            itemView.tv_city.text = data.destination
            itemView.tv_expired.text = data.timeExperied

            val idUser = Globals.getProfile(context).employId

            data.listApproval.forEachIndexed { index, participantModel ->
                if (idUser.equals(participantModel.id)){
                    showByApproval = true
                }
            }

            if(data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "Approver +${data.listApproval.size}"
            }
            else if(data.isApproval&&!data.isParticipant){
                itemView.tv_total_layer_approval.text = "Participant +${data.participant.size}"
            }
            else if(!data.isApproval&&data.isParticipant){
                itemView.tv_total_layer_approval.text = "Approver +${data.listApproval.size}"
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

/*    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_status.text = data.status
        holder.itemView.tv_title.text = data.titleHeader
        holder.itemView.tv_trip_code.text = data.tripCode
        holder.itemView.tv_date.text = data.time
        holder.itemView.tv_expired.text = data.timeExperied
        holder.itemView.tv_total_layer_approval.text = "Approver +${data.participant.size}"


        holder.itemView.setOnLongClickListener {
            callback.onClick(-109,position)
            true// returning true instead of false, works for me
        }

        holder.itemView.setOnClickListener {
            callback.onClick(-1,position)
        }


        if ("Waiting".equals(data.status)){
            holder.itemView.tv_expired.visibility = View.VISIBLE
            holder.itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorOrangeApproval))
            holder.itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_orange)
        }
        else if("Approved".equals(data.status)){
            holder.itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorGreenApproval))
            holder.itemView.tv_expired.visibility = View.GONE
            holder.itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_green)
        }
        else if("Partially Rejected".equals(data.status)){
            holder.itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorRedApproval))
            holder.itemView.tv_expired.visibility = View.GONE
            holder.itemView.tv_status.background = context.resources.getDrawable(R.drawable.rounded_approval_red)
        }

        if(data.selected == true){
            holder.itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
            holder.itemView.line_start.setBackgroundColor(context.resources.getColor(R.color.colorBackgroundSelected))
        }
        else{
            holder.itemView.parent_layout.setBackgroundColor(context.resources.getColor(R.color.colorPureWhite))
        }


        if (data.participant.size>1){
            holder.itemView.line_singgel_image.visibility = View.GONE
            holder.itemView.img_participant_second.visibility = View.VISIBLE

            Picasso.get()
                    .load(data.participant.get(0).image)
                    .into(holder.itemView.img_participant_first)

            Picasso.get()
                    .load(data.participant.get(1).image)
                    .into(holder.itemView.img_participant_second)
        }
        else{
            holder.itemView.line_singgel_image.visibility = View.VISIBLE
            holder.itemView.img_participant_second.visibility = View.GONE

            Picasso.get()
                    .load(data.participant.get(0).image)
                    .into(holder.itemView.img_participant_first)
        }


        if(position==items.size-1){
            holder.itemView.line_border_bottom.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_border_bottom.visibility = View.GONE
        }

    }*/

    fun setData(data: java.util.ArrayList<ApprovalModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {

        val VIEW_WAITING_APPROVAL = 1
        val VIEW_LIST_APPROVAL = 2
    }


    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)


    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).status){
            "Waiting" -> VIEW_WAITING_APPROVAL
            else -> VIEW_LIST_APPROVAL
        }
    }
}