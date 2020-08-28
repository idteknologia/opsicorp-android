package com.opsigo.travelaja.module.accomodation.tour.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.home.model.TourEventModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.item_promosi_hotel.view.*
import kotlinx.android.synthetic.main.item_tour.view.tv_country
import kotlinx.android.synthetic.main.item_tour.view.tv_date_tour
import kotlinx.android.synthetic.main.item_tour.view.tv_pricing_tour
import java.lang.Exception

class TourEventChildAdapter (context: Context, private var items: ArrayList<TourEventModel>): RecyclerView.Adapter<TourEventChildAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_promosi_hotel, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_country.text      = data.country
        holder.itemView.tv_date_tour.text    = data.dateTour
        holder.itemView.tv_pricing_tour.text = data.pricing

        if(position==0){
            holder.itemView.line0.visibility = View.VISIBLE
        }
        else {
            holder.itemView.line0.visibility = View.GONE
        }

        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                holder.itemView.img_tour.setImageBitmap(bitmap)
                holder.itemView.img_tour.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        Picasso.get()
                .load(data.imageTour)
                .into(target)
    }

    fun setData(data: java.util.ArrayList<TourEventModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}