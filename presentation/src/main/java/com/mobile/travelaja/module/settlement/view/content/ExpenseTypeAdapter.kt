package com.mobile.travelaja.module.settlement.view.content

import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemExpenseTypeBinding
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import opsigo.com.domainlayer.model.settlement.ExpenseType

class ExpenseTypeAdapter(val listener : ExpenseTypeListener) : BaseListAdapter<ExpenseType>() {
    override fun getLayoutItem(viewType: Int): Int  = R.layout.item_expense_type

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ExpenseTypeViewHolder(ItemExpenseTypeBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpenseTypeViewHolder){
            val data = list[position]
            holder.onBind(data)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ExpenseTypeViewHolder(val binding : ItemExpenseTypeBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(data : ExpenseType){
            binding.data = data
            binding.listener = listener
        }
    }
}

interface ExpenseTypeListener{
    fun onClickItem(data : ExpenseType)
}