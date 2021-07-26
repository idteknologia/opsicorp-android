package com.mobile.travelaja.module.settlement.view

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.IntercityTransportAdapter
import com.mobile.travelaja.module.settlement.viewmodel.IntercityTransportViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.IntercityTransport
import opsigo.com.domainlayer.model.settlement.RouteTransport

class IntercityTransportFragment : BaseListFragment<IntercityTransport>(), ItemClickListener {
    private lateinit var adapter: IntercityTransportAdapter
    private lateinit var viewModel: IntercityTransportViewModel
    private lateinit var dialog: MaterialAlertDialogBuilder
    private val args: IntercityTransportFragmentArgs by navArgs()
    private var routes = arrayOf<RouteTransport>()
    private var position = -1


    override fun baseListAdapter(): BaseListAdapter<IntercityTransport> {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(IntercityTransportViewModel::class.java)
        viewModel.items.add(IntercityTransport())
        arguments?.let {
            routes = args.route
        }
        setRoute()
        adapter = IntercityTransportAdapter(viewModel, this)
        setUi()
        return adapter
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = true

    override fun onRefresh() {
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList) {
            addItem()
        }
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.buttonRemove -> {
                removeItem(position)
            }
            else -> {
                this.position = position
                dialog.show()
            }
        }
    }

    private fun setRoute() {
        val items = routes.map { it.City }.toTypedArray()
        dialog = MaterialAlertDialogBuilder(requireContext()).setTitle("Ticket Number")
        dialog.setItems(items) { d, pos ->
            val route = routes[pos]
            viewModel.setRoute(this.position, route)
            d.dismiss()
        }
    }

    private fun setUi() {
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
    }

    private fun addItem() {
        val size = adapter.itemCount
        binding.rvBaseList.clearFocus()
        adapter.notifyItemInserted(size)
        viewModel.addItem()
    }

    private fun removeItem(pos: Int) {
        binding.rvBaseList.clearFocus()
        viewModel.removeItem(pos)
        binding.rvBaseList.removeViewAt(pos)
        adapter.notifyItemRemoved(pos)
    }

}