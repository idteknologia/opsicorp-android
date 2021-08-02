package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.paging.PageListAdapter
import com.mobile.travelaja.databinding.ItemSettlementPageBinding
import opsigo.com.domainlayer.model.settlement.Settlement

class SettlementAdapter : PageListAdapter<Settlement>(POST_COMPARATOR) {
    companion object{
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Settlement>() {
            override fun areContentsTheSame(
                    oldItem: Settlement,
                    newItem: Settlement
            ): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(
                    oldItem: Settlement,
                    newItem: Settlement
            ): Boolean =
                    oldItem.Id == newItem.Id
        }
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_settlement_page

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = SettlementVH(ItemSettlementPageBinding.bind(view))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val settlement = getItem(position)
        if (holder is SettlementVH){
            holder.onBind(settlement)
        }
    }

    inner class SettlementVH(val view : ItemSettlementPageBinding) : RecyclerView.ViewHolder(view.root){
        fun onBind(settlement : Settlement?){
            view.tvIdReq.text = settlement?.Code
            view.tvType.text = settlement?.statusView
        }
    }
}