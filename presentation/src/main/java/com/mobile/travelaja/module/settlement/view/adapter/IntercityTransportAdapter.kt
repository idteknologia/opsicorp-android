package com.mobile.travelaja.module.settlement.view.adapter

import android.text.InputFilter
import android.text.Spanned
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemIntercityTransportBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.IntercityTransportViewModel
import opsigo.com.domainlayer.model.settlement.IntercityTransport

class IntercityTransportAdapter(
    val viewModel: IntercityTransportViewModel,
    val listener: ItemClickListener
) : BaseListAdapter<IntercityTransport>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_intercity_transport

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return IntercityTransportVH(ItemIntercityTransportBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IntercityTransportVH) {
            val model = viewModel.items[position]
            val isRemove = viewModel.isRemoveVisible
            holder.onBind(model, isRemove, position)
        }
    }

    override fun getItemCount(): Int = viewModel.items.size

    inner class IntercityTransportVH(val binding: ItemIntercityTransportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            model: IntercityTransport,
            isRemove: ObservableBoolean,
            position: Int
        ) {
            binding.listener = listener
            binding.isRemove = isRemove
            binding.position = position
            binding.setVariable(BR.data, model)
            binding.executePendingBindings()
            binding.etDistance.doOnTextChanged { text, start, count, after ->
                if (!text.isNullOrEmpty() && binding.etDistance.isFocusable){
                    viewModel.setDistance(text.toString().toDouble(),itemId.toInt())
                }
            }
            binding.etDistance.filters = arrayOf(InputFilterMax())
        }
    }
}

class InputFilterMax : InputFilter {
    /*
      @dest is value before change
      @replacement is char value input / delete is empty
      @charsStart is chars from index 0 until dStart
      @charsEnd is chars from dend until length dest
      @value is value input after change / append charsStar + replacement + charsEnd
     */
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
            val d = value.toDouble()
            if (d > 200)
                return ""
        }catch (t : Throwable){

        }
        return source!!
    }

}