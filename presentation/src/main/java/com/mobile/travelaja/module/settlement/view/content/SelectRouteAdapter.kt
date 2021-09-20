package com.mobile.travelaja.module.settlement.view.content

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemSelectRouteBinding
import opsigo.com.domainlayer.model.settlement.RouteTransport

class SelectRouteAdapter(val listener : SelectRouteListener,val city : String) : BaseListAdapter<RouteTransport>() {
    override fun getLayoutItem(viewType: Int): Int  = R.layout.item_select_route

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return SelectRouteViewHolder(ItemSelectRouteBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SelectRouteViewHolder){
            val data = list[position]
            holder.onBind(data)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class SelectRouteViewHolder(val binding : ItemSelectRouteBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : RouteTransport){
            binding.data = data
            binding.city = city
            binding.listener = listener
        }
    }
}

interface SelectRouteListener{
    fun onClickItem(data : RouteTransport)
}