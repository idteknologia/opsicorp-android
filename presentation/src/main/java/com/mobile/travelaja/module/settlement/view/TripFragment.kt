package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mobile.travelaja.R
import com.mobile.travelaja.base.paging.PageListAdapter
import com.mobile.travelaja.base.paging.PageListFragment
import com.mobile.travelaja.module.settlement.view.adapter.TripAdapter
import com.mobile.travelaja.module.settlement.viewmodel.TripViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import opsigo.com.domainlayer.model.trip.Trip

class TripFragment : PageListFragment<Trip>(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: TripViewModel
    private var job: Job? = null
    private var query = mutableMapOf<String, Any>()
    private val navArgs: TripFragmentArgs by navArgs()
    private var selectedCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val status = navArgs.status
            selectedCode = navArgs.selectedCode
            if (status != -1) {
                query["Status"] = status
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ivBack){
            activity?.finish()
        }else {
            activity?.finish()
        }
    }

    override fun pageListAdapter(): PageListAdapter<Trip> {
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(TripViewModel::class.java)
        return TripAdapter(selectedCode, requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchQuery.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { queryStr ->
                query["Keyword"] = queryStr
                loadItems()
            }
        })
        setSearchListener(this)
        setHintSearch(getString(R.string.txt_enter_trip_code))
//        setSubmitSearchEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    private fun loadItems() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getTrips(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun isSearchVisible(): Boolean  = true
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            this.query["Keyword"] = query
            loadItems()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}