package com.mobile.travelaja.module.my_booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mobile.travelaja.R
import opsigo.com.domainlayer.model.my_booking.ImportanPreProductInfoModel
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_product_info_purchase.view.*

class ImportanPreProductInfoAdapter (var context: Context, private var items: ArrayList<ImportanPreProductInfoModel>): androidx.recyclerview.widget.RecyclerView.Adapter<ImportanPreProductInfoAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_info_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.ic_info.setImageDrawable(ContextCompat.getDrawable(context,data.icon))
        holder.itemView.tv_message_info.text = data.description

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<ImportanPreProductInfoModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}