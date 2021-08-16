package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.TripsListAdapter
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.trip.Trip
import com.mobile.travelaja.module.settlement.view.CreateSettlementFragment.Companion.KEY_REQUEST_TRIP_LIST

class TripsListFragment : BaseListFragment<Trip>(), SearchView.OnQueryTextListener,
    TripsListAdapter.TripsListener {
    private lateinit var adapter: TripsListAdapter
    private lateinit var viewModel: SettlementViewModel
    private val args: TripsListFragmentArgs by navArgs()
    private var viewType = TripsListAdapter.TYPE_SELECTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewType = args.viewType
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (viewType == TripsListAdapter.TYPE_DRAFT)
                activity?.finish()
            else findNavController().navigateUp()
        }
    }

    override fun baseListAdapter(): BaseListAdapter<Trip> {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        adapter = TripsListAdapter(this, args.idTrip, args.viewType)
        setUi()
        return adapter
    }

    private fun setUi() {
        if (args.viewType == TripsListAdapter.TYPE_DRAFT) {
            setTitleName(R.string.last_draft_reimbursement, R.color.colorTextHint)
            setSubtitle(R.string.subtitle_draft_reimbursement)
            setButtonName(R.string.create_new_reimbursement)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tripCodes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.list = it.toMutableList()
                adapter.notifyDataSetChanged()
            } else {
                showWarning(getString(R.string.no_have_trips))
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                showError(t) {
                    viewModel.getTripCodes(viewType)
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            isRefreshing(loading)
        }
        viewModel.getTripCodes(viewType)
        setHintSearch(getString(R.string.txt_enter_trip_code))
        setSearchListener(this)
    }

    override fun onRefresh() {
        hiddenError()
        viewModel.getTripCodes(viewType)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonClose) {
            findNavController().navigateUp()
        } else if (v?.id == R.id.buttonBottom) {
            navigateCreateReimbursement()
        } else {
            activity?.finish()
        }
    }

    override fun dividerEnabled(): Boolean = args.viewType == TripsListAdapter.TYPE_SELECTED
    override fun isSearchVisible(): Boolean = args.viewType == TripsListAdapter.TYPE_SELECTED
    override fun isButtonBottomVisible(): Boolean = args.viewType == TripsListAdapter.TYPE_DRAFT
    override fun onQueryTextSubmit(query: String?): Boolean {
        return if (query.isNullOrEmpty() || query.isNullOrBlank()) {
            false
        } else {
            search(query)
            true
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return if (newText.isNullOrEmpty() || newText.isNullOrBlank()) {
            false
        } else {
            search(newText)
            true
        }
    }

    private fun search(query: String) {
        val list = adapter.list.filter { it.Code.contains(query, false) }
        adapter.list = list.toMutableList()
        adapter.notifyDataSetChanged()
    }

    override fun isButtonVisible(): Boolean = false
    override fun onClickTripId(data: Trip, type: Int) {
        if (type == TripsListAdapter.TYPE_SELECTED) {
            setFragmentResult(
                KEY_REQUEST_TRIP_LIST, bundleOf(
                    KEY_TRIP_CODE to data.Code,
                    KEY_TRIP_ID to data.Id
                )
            )
            findNavController().navigateUp()
        } else if (type == TripsListAdapter.TYPE_DRAFT) {
            val action =
                TripsListFragmentDirections.actionTripCodeFragmentToSettlementDetailDraftFragment(
                    data.Id
                )
            findNavController().navigate(action)
        }
    }

    private fun navigateCreateReimbursement() {
        setFragmentResult(KEY_REQUEST_TRIP_LIST, bundleOf(KEY_CREATE_REIMBURSEMENT to true))
        findNavController().navigateUp()
//        val action = TripsListFragmentDirections.actionTripCodeFragmentToCreateSettlement()
//        findNavController().navigate(action)
    }

    companion object {
        const val KEY_TRIP_CODE = "KEY_TRIP_CODE"
        const val KEY_TRIP_ID = "KEY_TRIP_ID"
        const val KEY_CREATE_REIMBURSEMENT = "KEY_CREATE_REIMBURSEMENT"
    }

}