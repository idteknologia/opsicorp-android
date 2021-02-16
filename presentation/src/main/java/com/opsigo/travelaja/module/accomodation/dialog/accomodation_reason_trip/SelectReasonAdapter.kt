package com.opsigo.travelaja.module.accomodation.dialog.accomodation_reason_trip

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_adapter_reason_code.view.*
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

class SelectReasonAdapter (context: Context, private var items: ArrayList<ReasonCodeModel>): RecyclerView.Adapter<SelectReasonAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    var checkPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_reason_code, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.name_reason.text = data.codeBrief

        if (position==checkPosition){
            holder.itemView.ic_check.visibility = View.VISIBLE
        }
        else {
            holder.itemView.ic_check.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            checkPosition = position
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: java.util.ArrayList<ReasonCodeModel>) {
        items = data
        notifyDataSetChanged()
    }


    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}