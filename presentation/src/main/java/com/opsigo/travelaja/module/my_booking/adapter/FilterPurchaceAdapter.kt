package com.opsigo.travelaja.module.my_booking.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.model.FilterPurchaseModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.filter_adapter_purchase.view.*

class FilterPurchaceAdapter (var context: Context, private var items: ArrayList<FilterPurchaseModel>): androidx.recyclerview.widget.RecyclerView.Adapter<FilterPurchaceAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    var positionCheckBox = "left"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_adapter_purchase, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (positionCheckBox=="left"){
            holder.itemView.checkbox_right.visibility = View.GONE
            holder.itemView.tv_name_filter_right.visibility =  View.GONE
            holder.itemView.checkbox.visibility = View.VISIBLE
            holder.itemView.tv_name_filter.visibility =  View.VISIBLE
        }
        else{
            holder.itemView.checkbox_right.visibility = View.VISIBLE
            holder.itemView.tv_name_filter_right.visibility =  View.VISIBLE
            holder.itemView.checkbox.visibility = View.GONE
            holder.itemView.tv_name_filter.visibility =  View.GONE
        }

        holder.itemView.tv_name_filter_right.text = data.name
        holder.itemView.tv_name_filter.text = data.name

        if (data.checlist){
            holder.itemView.checkbox.isChecked = true
            holder.itemView.checkbox_right.isChecked = true
        }
        else {
            holder.itemView.checkbox.isChecked = false
            holder.itemView.checkbox_right.isChecked = false
        }

        holder.itemView.setOnClickListener {
            items[position].checlist = !data.checlist
            notifyItemChanged(position)
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<FilterPurchaseModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}