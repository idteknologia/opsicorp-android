package com.opsicorp.hotel_feature.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.nearby_city_airport_adapter.view.*
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

class NearbyCountryAdapter(val context: Context, private var items: ArrayList<SelectNationalModel>): androidx.recyclerview.widget.RecyclerView.Adapter<NearbyCountryAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.nearby_city_airport_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_country.visibility = View.VISIBLE
        holder.itemView.line_city.visibility  = View.GONE
        holder.itemView.tv_country.text = items[position].name

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }

        if (position==items.size-1){
            holder.itemView.line_bottom.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_bottom.visibility = View.GONE
        }
    }
    
    fun setData(data: ArrayList<SelectNationalModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}