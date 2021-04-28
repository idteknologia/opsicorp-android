package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.SelectRouteTripItemBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel

class ItineraryAdapter(val viewModel : ItineraryViewModel) : RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.select_route_trip_item, parent, false)
        val binding = SelectRouteTripItemBinding.bind(v)
        return ItineraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = 1

    inner class ItineraryViewHolder(val v: SelectRouteTripItemBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(position: Int) {
            v.viewModel = viewModel


            v.layDeaprtureDate.setOnClickListener {
                (it.context as ItineraryListener).clickItemItinerary(position,0)

            }
            v.lineDepart.setOnClickListener {
                (it.context as ItineraryListener).clickItemItinerary(position,1)
            }

            v.lineArrive.setOnClickListener {
                (it.context as ItineraryListener).clickItemItinerary(position,2)
            }

            v.tvTypeTransport.setOnClickListener {
                (it.context as ItineraryListener).clickItemItinerary(position,3)
            }
        }
    }
}