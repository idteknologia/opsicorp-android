package com.opsicorp.hotel_feature.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.opsicorp.hotel_feature.R
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.nearby_office_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyOfficeModel

class NearbyOfficeAdapter (val context: Context, private var items: ArrayList<NearbyOfficeModel>): RecyclerView.Adapter<NearbyOfficeAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.nearby_office_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_city.text            = data.city
        holder.itemView.tv_distance.text        = data.latitude
        holder.itemView.tv_name_company.text    = data.nameCompany
        holder.itemView.tv_type_company.text    = data.nameCompany

        if (items.size>1){
            if (position>0){
                if (data.city==items[(position-1)].city){
                    holder.itemView.line_city.visibility = View.GONE
                }
                else {
                    holder.itemView.line_city.visibility = View.VISIBLE
                }
            }
            else{
                holder.itemView.line_city.visibility = View.VISIBLE
            }
        }
        else {
            holder.itemView.line_city.visibility = View.VISIBLE
        }
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

    fun setData(data: ArrayList<NearbyOfficeModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}