package com.mobile.travelaja.module.settlement.view.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTransportationExpenseBinding
import com.mobile.travelaja.module.settlement.view.ItemClickListener
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel
import opsigo.com.domainlayer.model.settlement.TransportExpenses
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel.Companion.TYPE_FLIGHT
import opsigo.com.domainlayer.model.settlement.ModeTransport

class TransportExpenseAdapter(
    var viewModel: TransportExpenseViewModel,
    var modes: MutableList<ModeTransport> = mutableListOf(),
    var listener: ItemClickListener
) :
    BaseListAdapter<TransportExpenses>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_transportation_expense

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return TransportExpenseViewHolder(ItemTransportationExpenseBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransportExpenseViewHolder) {
            val transport = viewModel.getTransportExpense(position)
            val isRemove = viewModel.isRemoveVisible
            val indexEmpty = viewModel.indexEmpty
            holder.onBind(transport, position, isRemove,indexEmpty)
        }
    }

    override fun getItemCount(): Int = viewModel.transportExpenses.size

    inner class TransportExpenseViewHolder(val binding: ItemTransportationExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(transport: TransportExpenses, position: Int, isRemove: ObservableBoolean,indexEmpty : ObservableInt) {
            binding.position = position
            binding.listener = listener
            binding.isRemove = isRemove
            binding.indexEmpty = indexEmpty
            binding.setVariable(BR.transport, transport)
            if (modes.isNotEmpty()) {
                addChip()
            }
            binding.switchNonFlight.setOnClickListener {
                val checked = binding.switchNonFlight.isChecked
                if (checked) {
                    addChip()
                }
                viewModel.switchNonFlight(checked, position)
            }
            binding.executePendingBindings()
            binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
                val mode = group.findViewById<Chip>(checkedId)?.text
                if (!binding.hasPendingBindings() && mode != null && transport.TransportationMode != mode.toString()) {
                    viewModel.calculateTransportByType(
                        TransportExpenseViewModel.NON_FLIGHT,
                        mode.toString(),
                        position
                    )
                }
            }
        }

        private fun addChip() {
            if (binding.chipGroup.childCount == 0) {
                modes.forEach {
                    if (it.Value != TYPE_FLIGHT) {
                        val v = LayoutInflater.from(itemView.context)
                            .inflate(R.layout.item_chip_transport, binding.chipGroup, false)
                        if (v is Chip) {
                            v.text = it.Text
                        }
                        v.id = it.id
                        binding.chipGroup.addView(v)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("appCheckedMode")
        fun setModeChecked(chipGroup: ChipGroup, mode: Int) {
            if (mode != 0)
                chipGroup.findViewById<Chip>(mode)?.isChecked = true
            else
                chipGroup.clearCheck()
        }
        @JvmStatic
        @BindingAdapter("visibleEmpty")
        fun setEmptyData(card : MaterialCardView,isEmpty : Boolean){
            card.strokeWidth = if (isEmpty) 1 else 0
        }

    }
}