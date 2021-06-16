package com.mobile.travelaja.module.settlement.view

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.paging.PageListAdapter
import com.mobile.travelaja.databinding.ItemTripPageBinding
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

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_trip_page

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = TripViewHolder(ItemTripPageBinding.bind(view))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trip = getItem(position)
        if (holder is TripViewHolder){
            holder.onBind(trip)
            holder.view.tvPosition.text = "$position"
        }
    }

    inner class TripViewHolder(val view : ItemTripPageBinding) : RecyclerView.ViewHolder(view.root){
        fun onBind(trip : Trip?){
            view.trip = trip
            view.selected = selectedCode == trip?.Code
            view.executePendingBindings()
            /*itemView.setOnClickListener {
                val intent = Intent()
                intent.putExtra(TripSearchActivity.SELECTED,trip?.Code)
                activity.setResult(Activity.RESULT_OK,intent)
                activity.finish()
            }*/
        }
    }

}