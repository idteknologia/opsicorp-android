package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.TripsListAdapter
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.trip.Trip

class TripsListFragment : BaseListFragment<Trip>(),SearchView.OnQueryTextListener {
    private lateinit var  adapter : TripsListAdapter
    private lateinit var viewModel: SettlementViewModel

    override fun baseListAdapter(): BaseListAdapter<Trip> {
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        adapter = TripsListAdapter(viewModel)
        return adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tripCodes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                adapter.list = it.toMutableList()
                adapter.notifyDataSetChanged()
            } else {
                showWarning(getString(R.string.no_have_trips))
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                showError(t) {
                    viewModel.getTripCodes()
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            isRefreshing(loading)
        }
        viewModel.getTripCodes()
        setHintSearch(getString(R.string.txt_enter_trip_code))
        setSearchListener(this)
    }

    override fun onRefresh() {
        hiddenError()
        viewModel.getTripCodes()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonClose){
            findNavController().navigateUp()
        }
    }

    override fun dividerEnabled(): Boolean = true
    override fun isSearchVisible(): Boolean  = true
    override fun onQueryTextSubmit(query: String?): Boolean {
        return if (query.isNullOrEmpty() || query.isNullOrBlank()){
            false
        }else {
            search(query)
            true
        }    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return if (newText.isNullOrEmpty() || newText.isNullOrBlank()){
            false
        }else {
            search(newText)
            true
        }
    }

    private fun search(query : String){
        val list = adapter.list.filter{ it.Code.contains(query,false)}
        adapter.list = list.toMutableList()
        adapter.notifyDataSetChanged()
    }

    override fun isButtonVisible(): Boolean = false

}