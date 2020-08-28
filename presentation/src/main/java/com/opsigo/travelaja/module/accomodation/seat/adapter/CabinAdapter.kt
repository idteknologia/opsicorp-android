package com.opsigo.travelaja.module.accomodation.seat.adapter

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.seat.model.CabinModel2
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.cabin_adapter_view.view.*

class CabinAdapter (context: Context, var items: ArrayList<CabinModel2>): RecyclerView.Adapter<CabinAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    var context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.cabin_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = items.get(position)

        setInitRecyclerView(data,holder,position)
        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position,0,0)
        }
    }

    private fun setInitRecyclerView(data: CabinModel2, holder: ViewHolder, positionParent: Int) {
        var adapter = SeatAdapter2(context,data.Seat)
        val mLayoutManager = GridLayoutManager(context, 5)
        holder.itemView.rv_seat.setLayoutManager(mLayoutManager)
        holder.itemView.rv_seat.setItemAnimator(DefaultItemAnimator())
        holder.itemView.rv_seat.hasFixedSize()
        holder.itemView.rv_seat.setAdapter(adapter)

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                onclick.onClick(-11,positionParent,views,position)
            }
        })
    }

    fun setData(data: ArrayList<CabinModel2>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}