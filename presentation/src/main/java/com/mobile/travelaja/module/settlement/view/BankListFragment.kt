package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.BankListAdapter
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.Bank

class BankListFragment : BaseListFragment<Bank>() {
    private lateinit var adapter : BankListAdapter
    private lateinit var viewModel: SettlementViewModel

    override fun baseListAdapter(): BaseListAdapter<Bank> {
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        adapter = BankListAdapter(viewModel)
        setTitleName(R.string.select_bank_transfer)
        return adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bank.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                adapter.list = it.toMutableList()
                adapter.notifyDataSetChanged()
            } else {
                showWarning(getString(R.string.no_have_bank_account))
            }

        }
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                showError(t) {
                    viewModel.getBank()
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            isRefreshing(loading)
        }
        viewModel.getBank()

    }

    override fun onStart() {
        super.onStart()
        binding.tvSubtitle.isVisible = false
    }

    override fun onRefresh() {
        hiddenError()
        viewModel.getBank()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ivBack){
            findNavController().navigateUp()
        }
    }

    override fun dividerEnabled(): Boolean = true
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = false
    override fun isButtonBottomVisible(): Boolean  = false
}