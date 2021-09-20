package com.mobile.travelaja.base.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.PageListFragmentBinding
import com.mobile.travelaja.utility.Utils
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.ext.android.bind
import kotlinx.coroutines.ExperimentalCoroutinesApi as ExperimentalCoroutinesApi1

abstract class PageListFragment<T : Any> : Fragment(),View.OnClickListener {
    private var snackbar: Snackbar? = null
    lateinit var adapter: PagingDataAdapter<T, RecyclerView.ViewHolder>
    abstract fun pageListAdapter(): PageListAdapter<T>
    lateinit var binding : PageListFragmentBinding

    abstract fun isSearchVisible(): Boolean


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page_list_fragment,container,false)
        binding = PageListFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))
        adapter = pageListAdapter()
        binding.includeSearch.root.isVisible = isSearchVisible()
        binding.ivBack.isVisible = !isSearchVisible()
        binding.ivBack.setOnClickListener(this)
        binding.includeSearch.buttonClose.setOnClickListener(this)
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi1::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swpRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(FlowPreview::class)
            adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect {
                        binding.rvList.scrollToPosition(0)
                    }
        }

        snackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.txt_try_again)) {
                    adapter.refresh()
                }

        binding.rvList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PageLoadStateAdapter {
                    adapter.retry()
                },
                footer = PageLoadStateAdapter {
                    adapter.retry()
                }
        )


        adapter.addLoadStateListener { loadState ->
            val errorState = loadState.refresh as? LoadState.Error
            errorState?.let {
                showActionError(it.error)
            }
        }
    }

    fun setHintSearch(hint: String) {
        binding.includeSearch.searchView.queryHint = hint
    }

    fun setSearchListener(listener: SearchView.OnQueryTextListener) {
        binding.includeSearch.searchView.setOnQueryTextListener(listener)
    }

    fun setSubmitSearchEnabled(isSubmitEnabled : Boolean){
        binding.includeSearch.searchView.isSubmitButtonEnabled = isSubmitEnabled
    }

    override fun onStart() {
        super.onStart()
        binding.swpRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    override fun onStop() {
        super.onStop()
        snackbar?.dismiss()
    }


    // Todo showing error
    private fun showActionError(error: Throwable) {
        Utils.handleErrorMessage(requireContext(),error){ errorString ->
            Snackbar.make(requireView(),errorString, Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.txt_try_again)){
                adapter.refresh()
            }.show()
        }
    }

}