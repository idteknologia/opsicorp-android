package com.mobile.travelaja.module.settlement.view

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemBankLayoutBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import opsigo.com.domainlayer.model.settlement.Bank

class BankListAdapter(val viewModel: SettlementViewModel) : BaseListAdapter<Bank>() {
    override fun getLayoutItem(viewType: Int): Int = R.layout.item_bank_layout

    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
       return BankListViewHolder(ItemBankLayoutBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BankListViewHolder){
            holder.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class BankListViewHolder(val binding : ItemBankLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(bank : Bank){
            binding.bank = bank
            itemView.setOnClickListener {
                if (itemView.context is BankListener){
                    (itemView.context as BankListener).onClickItemBank(bank.Id)
                } else {
                    itemClickByNav(bank)
                }
            }
        }
        fun itemClickByNav(bank : Bank){
            viewModel.submitSettlement.BankAccount = bank.Account
            viewModel.submitSettlement.BankTransfer = bank.BankName
            itemView.findNavController().navigateUp()
        }
    }
}