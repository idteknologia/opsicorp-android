package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTripPageBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import opsigo.com.domainlayer.model.trip.Trip

class TripsListAdapter(val viewModel : SettlementViewModel) : BaseListAdapter<Trip>() {
    override fun getLayoutItem(viewType: Int): Int = R.layout.item_trip_page

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
       return TripsListViewHolder(ItemTripPageBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TripsListViewHolder){
            holder.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class TripsListViewHolder(val binding : ItemTripPageBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(trip : Trip){
            binding.trip = trip
            itemView.setOnClickListener {
                viewModel.submitSettlement.TripId = trip.Id
                viewModel.submitSettlement.TripCode = trip.Code
                it.findNavController().navigateUp()
            }
        }
    }
}