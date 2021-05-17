package com.opsigo.travelaja.module.settlement.view

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.opsigo.travelaja.base.paging.PageListAdapter
import com.opsigo.travelaja.base.paging.PageListFragment
import com.opsigo.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.opsigo.travelaja.viewmodel.DefaultViewModelFactory
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

    private fun loadItems(){
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getSettlement(mutableMapOf()).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}