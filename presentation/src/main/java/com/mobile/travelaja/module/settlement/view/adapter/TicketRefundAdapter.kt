package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTicketRefundBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.TicketRefundViewModel
import opsigo.com.domainlayer.model.settlement.Ticket

class TicketRefundAdapter(val viewModel: TicketRefundViewModel, val listener: ItemClickListener) :
    BaseListAdapter<Ticket>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_ticket_refund

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return TicketRefundViewHolder(ItemTicketRefundBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TicketRefundViewHolder) {
            val model = viewModel.getItem(position)
            val isRemove = viewModel.isRemoveVisible
            holder.onBind(model,isRemove,position)
        }
    }

    override fun getItemCount(): Int = viewModel.ticketRefunds.size

    inner class TicketRefundViewHolder(val binding: ItemTicketRefundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(ticket: Ticket,
                   isRemove: ObservableBoolean,
                   position: Int) {
            binding.listener = listener
            binding.isRemove = isRemove
            binding.position = position
            binding.setVariable(BR.ticketRefund, ticket)
            binding.executePendingBindings()
            binding.etRefundAmount.doOnTextChanged { text, start, count, after ->
                if (!text.isNullOrEmpty() && binding.etRefundAmount.isFocusable) {
                    viewModel.setRefundAmount(text.toString().toDouble(), itemId.toInt())
                }
            }
        }

    }

}