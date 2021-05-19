package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.gone
import com.opsigo.travelaja.utility.visible
import kotlinx.android.synthetic.main.dialog_purpose_item.view.*
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

class DialogPurposeAdapter (private var items: ArrayList<SelectNationalModel>): RecyclerView.Adapter<DialogPurposeAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.dialog_purpose_item, parent, false)

        return ViewHolder(itemView)
    }

    var checkPosition = -1

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_purpose.text = data.name

        if (position==checkPosition){
            holder.itemView.iv_checked.visible()
        }
        else {
            holder.itemView.iv_checked.gone()
        }

        holder.itemView.setOnClickListener {
            checkPosition = position
            onclick.onClick(-1,position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(data: ArrayList<SelectNationalModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}