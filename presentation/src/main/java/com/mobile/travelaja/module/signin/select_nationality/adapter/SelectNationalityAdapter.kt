package com.mobile.travelaja.module.signin.select_nationality.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
import kotlinx.android.synthetic.main.select_nationality_adapter_view.view.*

class SelectNationalityAdapter (private var items: ArrayList<SelectNationalModel>): androidx.recyclerview.widget.RecyclerView.Adapter<SelectNationalityAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_nationality_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (data.country.isNotEmpty()){
            holder.itemView.tv_country.text = "${data.name}, ${data.country}"
        } else {
            holder.itemView.tv_country.text = data.name
        }

        if (Globals.typeAccomodation.equals("Flight")){
            holder.itemView.rlIdCity.visible()
            holder.itemView.tv_country2.text = data.id
        } else {
            holder.itemView.rlIdCity.gone()
        }

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: ArrayList<SelectNationalModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}