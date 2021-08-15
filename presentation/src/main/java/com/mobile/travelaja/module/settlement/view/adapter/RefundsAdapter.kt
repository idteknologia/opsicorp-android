package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemDraftRefundTicketSettlementBinding
import opsigo.com.domainlayer.model.settlement.Ticket

class RefundsAdapter : BaseListAdapter<Ticket>() {
    override fun getLayoutItem(viewType: Int): Int  = R.layout.item_draft_refund_ticket_settlement

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDraftRefundTicketSettlementBinding.bind(view)
        return RefundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RefundViewHolder){
            holder.onBind(list[position])
        }
    }

    override fun getItemCount(): Int  = list.size

    inner class RefundViewHolder(val binding : ItemDraftRefundTicketSettlementBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : Ticket){
            binding.data = data
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("iconTicket")
        fun setIconTicket(imageView : ImageView,category : String?){
            category?.let {
                if (it.contains("flight",true)){
                    imageView.setImageResource(R.drawable.ic_ticket_refund_flight)
                }else if (it.contains("hotel",true)){
                    imageView.setImageResource(R.drawable.ic_ticket_refund_hotel)
                }else {
                    imageView.setImageResource(R.drawable.ic_ticket_refund_train)

                }
            }

        }
    }
}