package com.khoiron.module_tour.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import com.khoiron.module_tour.R
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.khoiron.module_tour.model.TourEventParentModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.item_tour_parent_adapter.view.*

class TourEventParentAdapter (context: Context, private var items: ArrayList<TourEventParentModel>): androidx.recyclerview.widget.RecyclerView.Adapter<TourEventParentAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tour_parent_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, positionParent: Int) {

        val data = items.get(positionParent)

        holder.itemView.btn_show_more.setOnClickListener {

        }

        holder.itemView.tv_name.text = data.name

        val adapterTourEventAdapter = TourEventChildAdapter(context,data.dataList)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        holder.itemView.rv_tour_child.layoutManager = layoutManager
        holder.itemView.rv_tour_child.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        holder.itemView.rv_tour_child.adapter = adapterTourEventAdapter

        adapterTourEventAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    1 ->{
                        onclick.onClick(-10,positionParent,views,position)
                    }
                }
            }
        })
    }



    fun setData(data: java.util.ArrayList<TourEventParentModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}