package com.mobile.travelaja.module.settlement.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.IntercityTransportAdapter
import com.mobile.travelaja.module.settlement.viewmodel.IntercityTransportViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.IntercityTransport
import opsigo.com.domainlayer.model.settlement.RouteTransport
import com.mobile.travelaja.module.settlement.view.content.SelectRouteFragment.Companion.DATA
import com.mobile.travelaja.module.settlement.view.content.SelectRouteFragment.Companion.TYPE
import com.mobile.travelaja.module.settlement.view.content.SelectRouteFragment.Companion.POSITION
import com.mobile.travelaja.utility.Utils

class IntercityTransportFragment : BaseListFragment<IntercityTransport>(), ItemClickListener {
    private lateinit var adapter: IntercityTransportAdapter
    private lateinit var viewModel: IntercityTransportViewModel
    private lateinit var dialog: MaterialAlertDialogBuilder
    private val args: IntercityTransportFragmentArgs by navArgs()
    private var routes = arrayOf<RouteTransport>()
    private var position = -1
    private var golper = 0
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showingWarning(
                R.string.alert,
                R.string.warning_back_settlement_transport_expense,
                R.string.dont_save,
                R.string.save,
                TransportExpenseFragment.WARNING_NAVIGATEUP
            )
        }
        arguments?.let {
            if (args.total.isNotEmpty()) {
                total = args.total.toDouble()
            }
        }
    }

    override fun baseListAdapter(): BaseListAdapter<IntercityTransport> {
        setViewModel()
        addTotal()
        addItems()
        arguments?.let {
            routes = args.route
            golper = args.golper
        }
        setUi()
        adapter = IntercityTransportAdapter(viewModel, this)
        return adapter
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = true
    override fun isButtonBottomVisible(): Boolean = true

    override fun onRefresh() {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList) {
            addItem()
        } else if (v?.id == R.id.buttonBottom) {
            saveItems()
        } else {
            showingWarning(
                R.string.alert,
                R.string.warning_back_settlement_transport_expense,
                R.string.dont_save,
                R.string.save,
                TransportExpenseFragment.WARNING_NAVIGATEUP
            )
        }
        binding.rvBaseList.clearFocus()
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.buttonRemove -> {
                removeItem(position)
            }
            R.id.switchRoundtrip -> {
                if (view is SwitchMaterial) {
                    val checked = view.isChecked
                    viewModel.switchTransport(position, checked)
                }
            }
            else -> {
                this.position = position
                navigateRoute(position)
            }
        }
        binding.rvBaseList.clearFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.total.observe(viewLifecycleOwner) {
            if (it != null) {
                val value = Utils.formatCurrency(it)
                textBottomValue.set(value)
            }
        }

        setFragmentResultListener(TYPE) { _, bundle ->
            val data = bundle.get(DATA)
            val pos = bundle.getInt(POSITION)
            if (data is RouteTransport && pos != -1) {
                viewModel.setRoute(pos, data, golper)
            }
        }
    }

    //Todo no override
    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(IntercityTransportViewModel::class.java)
    }

    private fun addTotal() {
        if (viewModel.total.value == 0 && total != 0.0) {
            viewModel.addTotal(total)
        }
    }

    private fun addItems() {
        val items = args.items
        if (items.isNotEmpty() && viewModel.items.isEmpty()) {
            viewModel.addItems(items)
        } else if (viewModel.items.isEmpty()) {
            val item = IntercityTransport()
            viewModel.addItems(arrayOf(item))
        }
    }

    private fun addRoutes() {
        val items = routes.map { it.City }.toTypedArray()
        dialog = MaterialAlertDialogBuilder(requireContext()).setTitle("Ticket Number")
        dialog.setItems(items) { d, pos ->
            val route = routes[pos]
            viewModel.setRoute(this.position, route, golper)
            d.dismiss()
        }
    }

    private fun setUi() {
        setTitleName(R.string.title_intercity_transport, R.color.colorTextHint)
        setSubtitle(R.string.transportation_form)
        setButtonText(R.string.add_intercity_transport)
        isEnabledRefresh(false)
        showingTotal(R.string.grand_total, R.string.other_expense_idr)
    }

    private fun navigateRoute(position: Int) {
        if (viewModel.loading) {
            showingWarning(
                R.string.waiting, R.string.warning_loading, -1, R.string.ok,
                TransportExpenseFragment.WARNING_ISLOADING
            )
            return
        }

        val city = viewModel.items[position].City
        val action =
            IntercityTransportFragmentDirections.actionIntercityTransportFragmentToSelectRouteFragment(
                routes,
                position,
                city
            )
        findNavController().navigate(action)
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

    private fun saveItems() {
        val indexEmpty = viewModel.indexFirstEmpty()
        if (indexEmpty != -1) {
            scrollNestedByIndexItem(indexEmpty)
            showWarning(R.string.warning_back_settlement_intercity_transport, R.string.ok)
        } else {
            val list = ArrayList<IntercityTransport>(viewModel.items)
            val bundle = bundleOf()
            bundle.putParcelableArrayList(KEY_INTERCITY_TRANSPORTS, list)
            setFragmentResult(CreateSettlementFragment.KEY_REQUEST_INTERCITY_TRANSPORT, bundle)
            navigateUp()
        }
    }

    override fun onWarningClick(dialogInterface: DialogInterface, type: Int, isPositive: Boolean) {
        super.onWarningClick(dialogInterface, type, isPositive)
        if (isPositive) {
            if (type == TransportExpenseFragment.WARNING_NAVIGATEUP) {
                saveItems()
            } else {
                dialogInterface.dismiss()
            }
        } else {
            dialogInterface.dismiss()
            navigateUp()
        }
    }

    companion object {
        const val KEY_INTERCITY_TRANSPORTS = "KEY_INTERCITY_TRANSPORTS"
    }

}