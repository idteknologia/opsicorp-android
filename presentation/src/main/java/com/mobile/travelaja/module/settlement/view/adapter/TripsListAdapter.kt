package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTripPageBinding
import com.mobile.travelaja.databinding.ItemTripPageDraftBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import opsigo.com.domainlayer.model.trip.Trip

class TripsListAdapter(val listener : TripsListener,var name : String?,var type : Int = TYPE_SELECTED) : BaseListAdapter<Trip>() {

    override fun getLayoutItem(viewType: Int): Int = if (viewType == TYPE_SELECTED) R.layout.item_trip_page else R.layout.item_trip_page_draft

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SELECTED)
            TripsListViewHolder(ItemTripPageBinding.bind(view))
        else TripDraftViewHolder((ItemTripPageDraftBinding.bind(view)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TripsListViewHolder){
            holder.onBind(list[position])
        }else {
            (holder as TripDraftViewHolder).onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class TripsListViewHolder(val binding : ItemTripPageBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(trip : Trip){
            binding.trip = trip
            binding.selected = trip.Id == name
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    inner class TripDraftViewHolder(val binding: ItemTripPageDraftBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : Trip){
            binding.data = data
            binding.listener = listener
        }
    }

    interface TripsListener{
        fun onClickTripId(data : Trip,type : Int)
    }

    companion object{
        const val TYPE_DRAFT = 1
        const val TYPE_SELECTED = 2
    }
}