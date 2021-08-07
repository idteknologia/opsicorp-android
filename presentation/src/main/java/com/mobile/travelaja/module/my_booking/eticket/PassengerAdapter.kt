package com.mobile.travelaja.module.my_booking.eticket

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTicketPurchasePassengerTrainBinding
import com.mobile.travelaja.module.my_booking.eticket.model.Passenger

class PassengerAdapter(val type : Int = TRAIN) : BaseListAdapter<Passenger>() {

    companion object {
        const val TRAIN = 1
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun getLayoutItem(viewType: Int): Int {
        if (viewType == TRAIN){
            return R.layout.item_ticket_purchase_passenger_train
        }
        TODO("Not yet implemented")
    }

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
          return PassengerTrainVH(ItemTicketPurchasePassengerTrainBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        if (holder is PassengerTrainVH){
            holder.onBind(data,position)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class PassengerTrainVH(val binding : ItemTicketPurchasePassengerTrainBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(passenger: Passenger,position: Int){
            binding.data = passenger
            binding.position = position
        }
    }


}