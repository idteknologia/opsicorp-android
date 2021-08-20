package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.SettlementItemPersonalSummaryBinding
import opsigo.com.domainlayer.model.settlement.Passenger

class SummaryPersonalAdapter : BaseListAdapter<Passenger>() {
    override fun getLayoutItem(viewType: Int): Int= R.layout.settlement_item_personal_summary

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return SummaryPersonalViewHolder(SettlementItemPersonalSummaryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SummaryPersonalViewHolder){
            holder.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class SummaryPersonalViewHolder(val binding : SettlementItemPersonalSummaryBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : Passenger){
            binding.data = data
        }
    }
}