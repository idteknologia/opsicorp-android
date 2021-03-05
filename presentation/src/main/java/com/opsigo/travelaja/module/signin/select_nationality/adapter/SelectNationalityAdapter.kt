package com.opsigo.travelaja.module.signin.select_nationality.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.gone
import com.opsigo.travelaja.utility.visible
import kotlinx.android.synthetic.main.select_nationality_adapter_view.view.*

class SelectNationalityAdapter (private var items: ArrayList<SelectNationalModel>): RecyclerView.Adapter<SelectNationalityAdapter.ViewHolder>() {

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

        holder.itemView.tv_country.text = "${data.name}, ${data.country}"

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

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}