package com.opsicorp.train_feature.seat

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.train_feature.R
import opsigo.com.domainlayer.model.accomodation.train.CabinModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import com.opsigo.travelaja.utility.WrappableGridLayoutManager
import kotlinx.android.synthetic.main.cabin_adapter_view.view.*

class AdapterSeatMapCabin (var context: Context, var items: ArrayList<CabinModel>): RecyclerView.Adapter<AdapterSeatMapCabin.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cabin_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        initRecyclerView(holder.itemView,position,data)
    }

    private fun initRecyclerView(itemView: View, positionParent: Int,dataSeat : CabinModel) {
        val adapterSeat by lazy { AdapterSeatMapTable(context,dataSeat.seatTables) }
        val mLayoutManager = WrappableGridLayoutManager(context, dataSeat.totalSeatPerRows)
        itemView.rv_seat.setLayoutManager(mLayoutManager)
        itemView.rv_seat.setItemAnimator(DefaultItemAnimator())
        itemView.rv_seat.setHasFixedSize(true)
        itemView.rv_seat.hasFixedSize()
        itemView.rv_seat.setAdapter(adapterSeat)

        adapterSeat.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -3 -> {
                        onclick.onClick(-3,positionParent,-3,position)
                    }
                }
            }
        })

    }

    fun setData(data: ArrayList<CabinModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}