package com.mobile.travelaja.module.settlement.view

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mobile.travelaja.base.paging.PageListAdapter
import com.mobile.travelaja.base.paging.PageListFragment
import com.mobile.travelaja.module.settlement.view.adapter.SettlementAdapter
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import opsigo.com.domainlayer.model.settlement.Settlement

class SettlementsFragment : PageListFragment<Settlement>(){
    private var job : Job?= null
    private lateinit var viewModel : SettlementViewModel

    override fun pageListAdapter(): PageListAdapter<Settlement> {
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(false, requireContext())).get(SettlementViewModel::class.java)
        return SettlementAdapter()
    }

    override fun onStart() {
        super.onStart()
        loadItems()
        adapter.refresh()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private fun loadItems(){
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getSettlement(mutableMapOf()).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun isSearchVisible(): Boolean {
        TODO("Not yet implemented")
    }
}