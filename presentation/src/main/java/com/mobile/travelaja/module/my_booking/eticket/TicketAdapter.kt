package com.mobile.travelaja.module.my_booking.eticket

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTicketPurchaseTrainBinding
import com.mobile.travelaja.module.my_booking.eticket.model.Segment

class TicketAdapter(val type : Int = TRAIN) : BaseListAdapter<Segment>(){

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun getLayoutItem(viewType: Int): Int {
        if (type == TRAIN){
            return R.layout.item_ticket_purchase_train
        }
        return R.layout.item_ticket_purchase_train
    }

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TRAIN){
            return TicketTrainVH(ItemTicketPurchaseTrainBinding.bind(view))
        }
        return TicketTrainVH(ItemTicketPurchaseTrainBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        if (holder is TicketTrainVH){
            holder.onBind(data)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class TicketTrainVH(val binding : ItemTicketPurchaseTrainBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : Segment){
            binding.data = data
        }
    }

    companion object {
        const val TRAIN = 1
        const val FLIGHT = 2
        const val HOTEL = 3
    }
}