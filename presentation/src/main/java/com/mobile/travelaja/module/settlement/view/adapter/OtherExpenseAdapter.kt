package com.mobile.travelaja.module.settlement.view.adapter

import android.text.InputFilter
import android.text.Spanned
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemOtherExpenseBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import opsigo.com.domainlayer.model.settlement.OtherExpense

class OtherExpenseAdapter(
    val viewModel: OtherExpenseViewModel,
    val recyclerView: RecyclerView,
    var listener: ItemClickListener
) :
    BaseListAdapter<OtherExpense>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_other_expense

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return OtherExpenseViewHolder(ItemOtherExpenseBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OtherExpenseViewHolder) {
            val otherExpense = viewModel.items[position]
            val isRemove = viewModel.isRemoveVisible
            val indexEmpty = viewModel.indexEmpty
            holder.onBind(otherExpense, position, isRemove, indexEmpty)
        }
    }

    override fun getItemCount(): Int = viewModel.items.size

    inner class OtherExpenseViewHolder(val binding: ItemOtherExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: OtherExpense,
            position: Int,
            isRemove: ObservableBoolean,
            indexEmpty: ObservableInt
        ) {
            binding.position = position
            binding.isRemove = isRemove
            binding.indexEmpty = indexEmpty
            binding.isUsd = data.Currency.contains("usd", true)
            binding.setVariable(BR.otherExpense, data)
            binding.listener = listener
            binding.executePendingBindings()
            binding.etAmount.filters = arrayOf(ZeroLeadingFilter())
            binding.etAmount.addTextChangedListener {
                val value = it.toString()
                if (binding.etAmount.isFocusable) {
                    if (value.isNotEmpty()) {
                        val amount = value.toDouble()
                        viewModel.setAmount(amount, position)
                    } else {
                        viewModel.setAmount(0, position)
                    }
                }
            }

            binding.toggleButton.setOnClickListener {
                val checked = binding.toggleButton.isChecked
                println(checked)
                val text = binding.toggleButton.text.toString()
                viewModel.setCurrency(text, position)
                binding.etAmount.text.clear()
            }

            binding.editTextNotes.addTextChangedListener {
                val value = it.toString()
                if (binding.etAmount.isFocusable) {
                    if (value.isNotEmpty()) {
                        viewModel.addDescription(value, position)
                    }
                }
            }
        }
    }
}


class ZeroLeadingFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        try {
            val replacement = source?.subSequence(start, end)
            val charsStart = dest?.substring(0, dstart)
            val charsEnd = dest?.substring(dend, dest.length)
            val value = charsStart + replacement + charsEnd
            val isLeadingZero = value.isNotEmpty() && value.matches(Regex("^(?!0\\d)\\d+(?:\\.\\d{1,10})?\$"))
            if (!isLeadingZero){
                return ""
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            return ""
        }
        return source!!
    }

}