package com.opsicorp.hotel_feature.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.nearby_city_airport_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel

class NearbyCityAdapter(val context: Context, private var items: ArrayList<CityHotelModel>): androidx.recyclerview.widget.RecyclerView.Adapter<NearbyCityAdapter.ViewHolder>() {

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
        holder.itemView.tv_country.visibility = View.GONE
        holder.itemView.line_city.visibility  = View.VISIBLE
        holder.itemView.tv_name.text  = items[position].cityName
        holder.itemView.tv_description.text = items[position].descrip
        holder.itemView.tv_description.visibility = View.GONE
        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }

        if (position==0){
            holder.itemView.tv_title.text = context.getString(R.string.title_popular_city)
            holder.itemView.layToolbar.visibility = View.VISIBLE
        }
        else {
            holder.itemView.layToolbar.visibility = View.GONE
        }

        if (position==items.size-1){
            holder.itemView.line_bottom.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_bottom.visibility = View.GONE
        }
    }
    
    fun setData(data: ArrayList<CityHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}