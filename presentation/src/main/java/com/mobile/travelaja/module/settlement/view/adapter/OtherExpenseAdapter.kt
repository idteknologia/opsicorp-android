package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemOtherExpenseBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import opsigo.com.domainlayer.model.settlement.OtherExpense

class OtherExpenseAdapter(val viewModel: OtherExpenseViewModel, var listener: ItemClickListener) :
    BaseListAdapter<OtherExpense>() {
    override fun getLayoutItem(viewType: Int): Int = R.layout.item_other_expense

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return OtherExpenseViewHolder(ItemOtherExpenseBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OtherExpenseViewHolder) {
            val otherExpense = viewModel.otherExpenses[position]
            holder.onBind(otherExpense,position)
        }
    }

    override fun getItemCount(): Int = viewModel.otherExpenses.size

    inner class OtherExpenseViewHolder(val binding: ItemOtherExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(otherExpense: OtherExpense,position: Int) {
            binding.position = position
            binding.setVariable(BR.otherExpense,otherExpense)
            binding.listener = listener
            binding.executePendingBindings()
        }
    }
}