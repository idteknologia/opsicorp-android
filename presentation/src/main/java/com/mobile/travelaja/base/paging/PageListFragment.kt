package com.mobile.travelaja.base.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Utils
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.ExperimentalCoroutinesApi as ExperimentalCoroutinesApi1

abstract class PageListFragment<T : Any> : Fragment() {
    private var snackbar: Snackbar? = null
    lateinit var adapter: PagingDataAdapter<T, RecyclerView.ViewHolder>
    abstract fun pageListAdapter(): PageListAdapter<T>
    private lateinit var swpRefresh : SwipeRefreshLayout
    private lateinit var rvList : RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swpRefresh = view.findViewById(R.id.swpRefresh)
        rvList = view.findViewById(R.id.rvList)
        rvList.addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))
        adapter = pageListAdapter()
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi1::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                swpRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
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
                        rvList.scrollToPosition(0)
                    }
        }

        snackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.txt_try_again)) {
                    adapter.refresh()
                }

        rvList.adapter = adapter.withLoadStateHeaderAndFooter(
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

    override fun onStart() {
        super.onStart()
        swpRefresh.setOnRefreshListener {
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