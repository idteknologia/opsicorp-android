package com.opsigo.travelaja.module.my_booking.purchase_detail

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_product_detail_purchase.view.*

class PurchaseDetailProductAdapter (var context: Context, private var items: ArrayList<PurchaseDetailProductModel>): androidx.recyclerview.widget.RecyclerView.Adapter<PurchaseDetailProductAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_product_detail_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if(position==items.size-1){
            holder.itemView.line_bottom.visibility = View.GONE
        }
        else{
            holder.itemView.line_bottom.visibility = View.VISIBLE
        }

        if(data.typeAccomodation=="Flight"){
            holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_flight_up_white))
        }
        else if(data.typeAccomodation=="Train"){
            holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_train_white))
        }
        else if(data.typeAccomodation=="Hotel"){
            holder.itemView.ic_panah.visibility = View.GONE
            holder.itemView.tv_arrival.visibility = View.GONE
            holder.itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_hotel_white))
        }

        holder.itemView.type_product.text = data.type
        holder.itemView.tv_departure.text = data.departure
        holder.itemView.tv_arrival.text   = data.arrival

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }


    }

    fun setData(data: ArrayList<PurchaseDetailProductModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}