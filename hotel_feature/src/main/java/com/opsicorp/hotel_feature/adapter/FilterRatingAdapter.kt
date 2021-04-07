package com.opsicorp.hotel_feature.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_filter_rating.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.StartSelected

class FilterRatingAdapter (context: Context, private var items: ArrayList<StartSelected>): androidx.recyclerview.widget.RecyclerView.Adapter<FilterRatingAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filter_rating, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        when(data.rating){
            1 ->{
                holder.itemView.star_1.visibility = View.VISIBLE
                holder.itemView.star_2.visibility = View.GONE
                holder.itemView.star_3.visibility = View.GONE
                holder.itemView.star_4.visibility = View.GONE
                holder.itemView.star_5.visibility = View.GONE
            }
            2 ->{
                holder.itemView.star_1.visibility = View.VISIBLE
                holder.itemView.star_2.visibility = View.VISIBLE
                holder.itemView.star_3.visibility = View.GONE
                holder.itemView.star_4.visibility = View.GONE
                holder.itemView.star_5.visibility = View.GONE
            }
            3 ->{
                holder.itemView.star_1.visibility = View.VISIBLE
                holder.itemView.star_2.visibility = View.VISIBLE
                holder.itemView.star_3.visibility = View.VISIBLE
                holder.itemView.star_4.visibility = View.GONE
                holder.itemView.star_5.visibility = View.GONE
            }
            4 ->{
                holder.itemView.star_1.visibility = View.VISIBLE
                holder.itemView.star_2.visibility = View.VISIBLE
                holder.itemView.star_3.visibility = View.VISIBLE
                holder.itemView.star_4.visibility = View.VISIBLE
                holder.itemView.star_5.visibility = View.GONE
            }
            5 ->{
                holder.itemView.star_1.visibility = View.VISIBLE
                holder.itemView.star_2.visibility = View.VISIBLE
                holder.itemView.star_3.visibility = View.VISIBLE
                holder.itemView.star_4.visibility = View.VISIBLE
                holder.itemView.star_5.visibility = View.VISIBLE
            }
        }

        holder.itemView.cb_rating.isChecked = data.selected

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
        }
    }


    fun setData(data: ArrayList<StartSelected>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}