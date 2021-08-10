package com.mobile.travelaja.module.settlement.view.adapter

import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemIntercityTransportBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.IntercityTransportViewModel
import com.mobile.travelaja.utility.Utils
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
            holder.onBind(model, isRemove, position,viewModel.items)
        }
    }

    override fun getItemCount(): Int = viewModel.items.size

    inner class IntercityTransportVH(val binding: ItemIntercityTransportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val filter = arrayOf(InputFilterMax {
            Toast.makeText(itemView.context,R.string.warning_maximum_200_km,Toast.LENGTH_SHORT).show()
        })

        fun onBind(
            model: IntercityTransport,
            isRemove: ObservableBoolean,
            position: Int,
            list : ObservableArrayList<IntercityTransport>
        ) {
            binding.listener = listener
            binding.isRemove = isRemove
            binding.position = position
            binding.list = list
            binding.setVariable(BR.data, model)
            binding.executePendingBindings()
            binding.etDistance.filters = filter
            binding.etDistance.doOnTextChanged { text, start, count, after ->
                val isNotLeadingZero = !text.isNullOrEmpty() && text.matches(Regex("^(?!0\\d)\\d+(?:\\.\\d{1,10})?\$")) ?:false
                val isDotEnd = !text.isNullOrEmpty() && text.matches(Regex("^(?!0\\d)\\d+(?:\\.)?\$")) ?:false
                if (!isNotLeadingZero && !isDotEnd){
                    val t = Utils.doubleParse(text.toString().toDouble())
                    if (t != null && t.toDouble() <= 200){
                        binding.etDistance.setText("$t")
                        binding.etDistance.setSelection(t.toString().length)
                    }else {
                        return@doOnTextChanged
                    }
                }
                if (!text.isNullOrEmpty() && binding.etDistance.isFocusable) {
                    viewModel.setDistance(text.toString().toDouble(), itemId.toInt())
                }
            }
        }
    }
}

class InputFilterMax(val listenMax : () -> Unit?) : InputFilter {
    var isDouble = false
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
            if (value.isEmpty()){
                return "0"
            }
             val d = value.toDouble()
            if (d > 200){ 
                if (isDouble){
                    return "."
                }
                listenMax.invoke()
                return ""
            }
            isDouble = value.contains(Regex("[.,]"))
        } catch (t: Throwable) {
            t.printStackTrace()
            return ""
        }
        return source!!
    }



}