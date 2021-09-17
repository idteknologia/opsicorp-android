package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import opsigo.com.domainlayer.model.my_booking.PassangerPurchaseModel
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_passanger_list_detail_purchase.view.*
import opsigo.com.domainlayer.model.my_booking.PassengersTrainModel

class PassangerPurchaseAdapter (var context: Context, private var items: ArrayList<Any>): androidx.recyclerview.widget.RecyclerView.Adapter<PassangerPurchaseAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_passanger_list_detail_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        when(data){
            is PassangerPurchaseModel -> {
                holder.itemView.line_total_baggage.visibility   = View.VISIBLE
                holder.itemView.tv_id_cart.visibility     = View.GONE
                holder.itemView.tv_seat_number.visibility = View.GONE
                holder.itemView.tv_age.text               = data.age
                holder.itemView.total_bagage.text         = data.totalBagage
                holder.itemView.tv_name.text              = data.Name
            }
            is PassengersTrainModel -> {
                holder.itemView.tv_id_cart.visibility     = View.VISIBLE
                holder.itemView.tv_seat_number.visibility = View.VISIBLE
                holder.itemView.line_total_baggage.visibility   = View.GONE
                holder.itemView.tv_id_cart.text           = "${data.idType} - ${data.idNumber}"
                holder.itemView.tv_seat_number.text       = data.getSeatView()
                holder.itemView.tv_age.text = data.typeAge
                holder.itemView.tv_name.text = data.fullName()
            }
        }
        holder.itemView.btn_see_detail.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<Any>) {
        items = data
        notifyDataSetChanged()

        Log.e("TAG","gcbsn "+this.items.size)
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}