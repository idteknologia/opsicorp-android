package com.opsigo.travelaja.module.item_custom.galery

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.content.Context
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.view.View
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_galery_adapter_view.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.GaleryModel

class MyGaleryAdapter (context: Context, var items: ArrayList<GaleryModel>): androidx.recyclerview.widget.RecyclerView.Adapter<MyGaleryAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_galery_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        Picasso.get()
                .load(data.imageUri)
                .placeholder(R.drawable.background_slider)
                .into(holder.itemView.ic_image)

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }

    fun setData(data: ArrayList<GaleryModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}