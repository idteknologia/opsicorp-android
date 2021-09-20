package com.mobile.travelaja.module.settlement.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.paging.PageListAdapter
import com.mobile.travelaja.databinding.ItemSettlementBinding
import com.mobile.travelaja.databinding.ItemTripPageBinding
import com.mobile.travelaja.module.settlement.TripSearchActivity
import com.mobile.travelaja.module.settlement.view.TripFragmentDirections
import com.mobile.travelaja.module.settlement.view.TripsListFragmentDirections
import opsigo.com.domainlayer.model.trip.Trip

class TripAdapter(private val selectedCode : String?,private val activity: Activity) : PageListAdapter<Trip>(POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Trip>() {
            override fun areContentsTheSame(
                    oldItem: Trip,
                    newItem: Trip
            ): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(
                    oldItem: Trip,
                    newItem: Trip
            ): Boolean =
                    oldItem.Id == newItem.Id
        }
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_settlement

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = TripViewHolder(ItemSettlementBinding.bind(view))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trip = getItem(position)
        if (holder is TripViewHolder){
            holder.onBind(trip)
        }
    }

    inner class TripViewHolder(val view : ItemSettlementBinding) : RecyclerView.ViewHolder(view.root){
        fun onBind(trip : Trip?){
            view.data = trip
            view.executePendingBindings()
//            view.containerTripItem.setOnClickListener {
//                val action = TripFragmentDirections.actionTripFragmentToSettlementSummaryFragment(trip!!.Id)
//                itemView.findNavController().navigate(action)
//            }
        }
    }

}