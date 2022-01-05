package com.mobile.travelaja.module.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_cart_bisnis_trip.view.*
import opsigo.com.domainlayer.model.cart.CartModelAdapter

//class CartListAdapter (var context:Context,private var items: ArrayList<CartTripModel>): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
class CartListAdapter (var context:Context,private var items: ArrayList<CartModelAdapter>): androidx.recyclerview.widget.RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_cart_bisnis_trip, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.checkbox.isChecked = data.cheked

        holder.itemView.tv_title_bisnis_trip.text = data.title
        //date
        holder.itemView.tv_date.text              = DateConverter().setDateFormat3(data.start_date)
        holder.itemView.tv_trip_code.text         = data.tripCode

        holder.itemView.line_item.background = context.resources.getDrawable(R.drawable.rounded_button_cart_list_bottom_left_green)
        holder.itemView.tv_title_bisnis_trip.setTextColor(context.resources.getColor(R.color.white))
        holder.itemView.tv_date.setTextColor(context.resources.getColor(R.color.white))

//        if(data.statusOrder=="Completed"){
//        if(data.status=="Completed"){
//            holder.itemView.line_item.background = context.resources.getDrawable(R.drawable.rounded_button_cart_list_bottom_left_green)
//            holder.itemView.tv_title_bisnis_trip.setTextColor(context.resources.getColor(R.color.white))
//            holder.itemView.tv_date.setTextColor(context.resources.getColor(R.color.white))
//        }
//        else{
//            holder.itemView.line_item.background = context.resources.getDrawable(R.drawable.rounded_button_cart_list_bottom_left_gray)
//            holder.itemView.tv_title_bisnis_trip.setTextColor(context.resources.getColor(R.color.blue_info_time))
//            holder.itemView.tv_date.setTextColor(context.resources.getColor(R.color.blue_info_time))
//        }

//        holder.itemView.tv_country.text = data

        holder.itemView.checkbox.setOnClickListener {
            onclick.onClick(-2,position)

        }

        holder.itemView.line_item.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

//    fun setData(data: ArrayList<CartTripModccel>) {
    fun setData(data: ArrayList<CartModelAdapter>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}