package com.mobile.travelaja.module.settlement.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTransportationExpenseBinding
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel
import opsigo.com.domainlayer.model.settlement.TransportExpenses
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel.Companion.NON_FLIGHT
import opsigo.com.domainlayer.model.settlement.ModeTransport


class TransportExpenseAdapter(
    var viewModel: TransportExpenseViewModel,
    var onClick: (v: View, pos: Int) -> Unit,
    var modes: List<ModeTransport> = listOf()
) :
    BaseListAdapter<TransportExpenses>() {

    override fun getLayoutItem(viewType: Int): Int = R.layout.item_transportation_expense

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return TransportExpenseViewHolder(ItemTransportationExpenseBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransportExpenseViewHolder) {
            val transport = viewModel.getTransportExpense(position)
            holder.onBind(transport, position)
        }
    }

    override fun getItemCount(): Int = viewModel.transportExpenses.size

    inner class TransportExpenseViewHolder(val binding: ItemTransportationExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(transport: TransportExpenses, position: Int) {
            binding.viewModel = viewModel
            binding.position = position
            binding.setVariable(BR.transport,transport)
            addChip(position)
            binding.buttonRemove.setOnClickListener {
                onClick.invoke(it, position)
            }
            binding.etOriginCity.setOnClickListener {
                onClick.invoke(it, position)
            }
            binding.chipGroup.setOnCheckedChangeListener(null)
            binding.switchNonFlight.setOnClickListener {
                val checked = binding.switchNonFlight.isChecked
                viewModel.switchNonFlight(checked, position)
            }
            binding.switchRoundtrip.setOnClickListener {
                val checked = binding.switchRoundtrip.isChecked
                viewModel.checkedSwitchRoundtrip(checked,position)
            }
        }

        private fun onClickChip(position: Int) =  View.OnClickListener {
            if (it is Chip){
                val checked = it.isChecked
                if (checked){
                    viewModel.calculateTransportByType(NON_FLIGHT, it.text.toString(), position)
                }
            }
        }

        private fun addChip(position: Int) {
            if (binding.chipGroup.childCount == 0) {
                modes.forEach {
                    val v = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.item_chip_transport, binding.chipGroup, false) as Chip
                    v.id = it.id
                    v.text = it.Text
                    v.setOnClickListener(onClickChip(position))
                    binding.chipGroup.addView(v)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("appCheckedMode")
        fun setModeChecked(chipGroup: ChipGroup, mode: Int) {
            if (mode != 0)
                chipGroup.findViewById<Chip>(mode).isChecked = true
            else
                chipGroup.clearCheck()
        }

        @JvmStatic
        @BindingAdapter("tintEditText")
        fun setTintEditText(editText: EditText, isEmpty : Boolean) {
        }
    }
}