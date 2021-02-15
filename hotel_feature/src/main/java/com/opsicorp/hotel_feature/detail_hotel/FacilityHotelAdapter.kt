package com.opsicorp.hotel_feature.detail_hotel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import kotlinx.android.synthetic.main.item_facility_hotel.view.*

class FacilityHotelAdapter (context: Context, var items: ArrayList<FacilityHotelModel>): RecyclerView.Adapter<FacilityHotelAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_facility_hotel, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        if(position==0){
            holder.itemView.line_start.visibility = View.VISIBLE
        }
        else{
            if (position == items.size-1){
                holder.itemView.line_end.visibility   = View.VISIBLE
            }else{
                holder.itemView.line_start.visibility = View.GONE
            }
        }

        holder.itemView.tv_name_facility.text = data.name

        if (data.image.isEmpty()){
            holder.itemView.image_facility.setImageDrawable(context.resources.getDrawable(R.drawable.ac))
        }
        else {
            Picasso.get()
                    .load(data.image)
                    .centerInside()
                    .fit()
                    .into(holder.itemView.image_facility)
        }

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }


    fun setData(data: ArrayList<FacilityHotelModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row)
}