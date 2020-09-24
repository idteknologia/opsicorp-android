package com.opsigo.travelaja.module.accomodation.hotel.parent

import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.content.Context
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.nearby_hotel_adapter.view.*
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import opsigo.com.domainlayer.model.accomodation.hotel.ListCompanyModel

class NearbyHotelAdapter (context: Context, private var items: ArrayList<ListCompanyModel>): RecyclerView.Adapter<NearbyHotelAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.nearby_hotel_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_city.text            = data.city
        holder.itemView.tv_distance.text        = data.latitude
        holder.itemView.tv_name_company.text    = data.nameCompany
        holder.itemView.tv_type_company.text    = data.nameCompany

        if (items.size>1){
            if (data.city==items[(position-1)].city){
                holder.itemView.line_city.visibility = View.GONE
            }
            else {
                holder.itemView.line_city.visibility = View.VISIBLE
            }
        }
        else {
            holder.itemView.line_city.visibility = View.VISIBLE
        }
    }


    fun setData(data: ArrayList<ListCompanyModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}