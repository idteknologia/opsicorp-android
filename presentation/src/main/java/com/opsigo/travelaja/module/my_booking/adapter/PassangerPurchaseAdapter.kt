package com.opsigo.travelaja.module.my_booking.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.model.PassangerPurchaseModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_passanger_list_detail_purchase.view.*

class PassangerPurchaseAdapter (var context: Context, private var items: ArrayList<PassangerPurchaseModel>): androidx.recyclerview.widget.RecyclerView.Adapter<PassangerPurchaseAdapter.ViewHolder>() {

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

        holder.itemView.tv_age.text = data.age
        holder.itemView.total_bagage.text = data.totalBagage
        holder.itemView.tv_name.text = data.Name
        holder.itemView.btn_see_detail.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<PassangerPurchaseModel>) {
        items = data
        notifyDataSetChanged()

        Log.e("TAG","gcbsn "+this.items.size)
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}