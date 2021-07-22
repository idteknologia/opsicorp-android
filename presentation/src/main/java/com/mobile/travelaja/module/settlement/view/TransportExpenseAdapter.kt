package com.mobile.travelaja.module.settlement.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemTransportationExpenseBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel
import opsigo.com.domainlayer.model.settlement.TransportExpenses

class TransportExpenseAdapter(
    var viewModel: TransportExpenseViewModel,
    var onClick: (v: View, pos: Int) -> Unit,
    var chips : MutableList<Chip> = mutableListOf()
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
            binding.tvTitleTransport.text = "Transport ${position + 1}"
            binding.viewModel = viewModel
            binding.position = position

            binding.buttonRemove.setOnClickListener {
                onClick.invoke(it,position)
            }
            binding.etOriginCity.setOnClickListener {
                onClick.invoke(it, position)
            }
            binding.captionNonFlight.setOnClickListener {
                onClick.invoke(it, position)
            }
            binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
                val c = group.findViewById<Chip>(checkedId)
                if (c != null && c.isChecked){
                    viewModel.calculateTransportByType(SettlementViewModel.NON_FLIGHT,c.text.toString(),position)
                }
            }
            binding.switchNonFlight.setOnCheckedChangeListener { compoundButton, b ->
                if (b){
                    switchNonFlight()
                }
            }
            binding.switchNonFlight.setOnClickListener {
                val checked = binding.switchNonFlight.isChecked
                if (checked) {
                    switchNonFlight()
                } else {
//                    viewModel.calculateTransportByType(1, viewModel.modeFlight., position)
                }
            }
        }

        private fun switchNonFlight(){
            if (binding.chipGroup.childCount ==  0){
                chips.forEach {
                    binding.chipGroup.addView(it)
                }
            }
            binding.chipGroup.isVisible = true
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("appCheckedMode")
        fun setModeChecked(chipGroup: ChipGroup,mode : Int){
            chipGroup.check(mode)
        }
    }
}