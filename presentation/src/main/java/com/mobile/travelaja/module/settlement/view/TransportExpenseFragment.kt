package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.TransportExpenseAdapter
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.TransportExpenses

class TransportExpenseFragment : BaseListFragment<TransportExpenses>(), ItemClickListener {
    private lateinit var adapter: TransportExpenseAdapter
    private lateinit var viewModel: TransportExpenseViewModel
    private lateinit var settlementViewModel: SettlementViewModel
    private val args: TransportExpenseFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showWarning(
                R.string.alert,
                R.string.warning_back_settlement_transport_expense,
                R.string.dont_save,
                R.string.save,
                WARNING_NAVIGATEUP
            )
        }
    }

    override fun baseListAdapter(): BaseListAdapter<TransportExpenses> {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(TransportExpenseViewModel::class.java)

        settlementViewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        setDataTransports()
        setModeTransports()
        adapter =
            TransportExpenseAdapter(viewModel, modes = viewModel.modeTransports, listener = this)
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
        return adapter
    }

    private fun setDataTransports() {
        val transports = settlementViewModel.submitSettlement.TransportExpenses
        if (transports.isNotEmpty() && viewModel.transportExpenses.isEmpty()) {
            viewModel.totalTransport.value = settlementViewModel.totalTransport.get()
            viewModel.transportExpenses.clear()
            viewModel.transportExpenses.addAll(transports)
            viewModel.isRemoveVisible.set(transports.size > 1)
        } else {
            viewModel.transportExpenses.add(TransportExpenses())
        }
    }

    private fun setModeTransports() {
        val modes = args.modes
        if (modes.isNotEmpty()) {
            viewModel.modeTransports.clear()
            viewModel.modeTransports.addAll(modes)
            viewModel.modeFlight =
                viewModel.modeTransports.last { it.Value == SettlementViewModel.TYPE_FLIGHT }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { error ->
                    val errorDesc =
                        if (error == Utils.EMPTY) getString(R.string.empty_mode_transports) else error
                    Toast.makeText(requireContext(), errorDesc, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.warning.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { warning ->
                if (warning == WARNING_SAME_DATA) {
                    showWarning(
                        R.string.alert, R.string.has_same_transport_expense, -1, R.string.close,
                        WARNING_SAME_DATA
                    )
                } else {
                    showWarning(
                        R.string.waiting, R.string.warning_loading, -1, R.string.ok,
                        WARNING_ISLOADING
                    )
                }
            }
        }
        viewModel.totalTransport.observe(viewLifecycleOwner) {
            settlementViewModel.totalTransport.set(it)
        }
        settlementViewModel.saveAction = {
            saveTransports()
        }
    }

    private fun saveTransports() {
        val indexEmpty = viewModel.indexFirstEmpty()
        if (indexEmpty != -1) {
            val y = binding.rvBaseList.getChildAt(indexEmpty).y - 30f
            binding.nested.scrollTo(0,y.toInt())
            val snackbar = Snackbar.make(
                binding.root,
                R.string.warning_not_complete_transport_expense,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction(R.string.ok) {
                snackbar.dismiss()
            }
            snackbar.show()
        } else {
            val transports = viewModel.transportExpenses
            val total = viewModel.totalTransport.value
            settlementViewModel.submitSettlement.TransportExpenses = transports
            settlementViewModel.submitSettlement.TotalTransportExpense = total ?: 0
            if (settlementViewModel.modeTransports.isEmpty()) {
                settlementViewModel.modeTransports.addAll(viewModel.modeTransports)
            }
            navigateBack()
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun showCityList(pos: Int) {
        val list = args.cities
        MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.select_city)
            .setItems(list) { _, which ->
                val city = list[which]
                viewModel.getModeTransports(city, pos)
            }.show()
    }


    override fun dividerEnabled(): Boolean = false

    override fun isSearchVisible(): Boolean = false

    override fun onRefresh() {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList) {
            addTransport()
        } else {
            showWarning(
                R.string.alert,
                R.string.warning_back_settlement_transport_expense,
                R.string.dont_save,
                R.string.save,
                WARNING_NAVIGATEUP
            )
        }
    }

    override fun isButtonVisible(): Boolean = true

    private fun addTransport() {
        val size = adapter.itemCount
        adapter.notifyItemInserted(size)
        viewModel.addTransport()
    }

    private fun showWarning(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes negativeName: Int,
        @StringRes positiveName: Int,
        type: Int
    ) {
        val alert = MaterialAlertDialogBuilder(requireContext())
        alert.setTitle(title).setMessage(message)
        alert.setPositiveButton(positiveName) { d, _ ->
            if (type == WARNING_NAVIGATEUP) {
                saveTransports()
            } else {
                d.dismiss()
            }
        }
        if (negativeName != -1) {
            alert.setNegativeButton(negativeName) { d, _ ->
                d.dismiss()
                navigateBack()
            }
        }
        alert.show()
    }

    private fun removeTransport(pos: Int) {
        viewModel.removeTransport(pos)
        binding.rvBaseList.removeViewAt(pos)
        adapter.notifyItemRemoved(pos)
    }


    companion object {
        const val WARNING_NAVIGATEUP = 1
        const val WARNING_SAME_DATA = 2
        const val WARNING_ISLOADING = 3
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.buttonRemove -> {
                removeTransport(position)
            }
            R.id.etOriginCity -> {
                showCityList(position)
            }
            R.id.switchNonFlight -> {
                if (view is SwitchMaterial) {
                    val checked = view.isChecked
                    viewModel.switchNonFlight(checked, position)
                }
            }
            R.id.switchRoundtrip -> {
                if (view is SwitchMaterial) {
                    val checked = view.isChecked
                    viewModel.checkedSwitchRoundtrip(checked, position)
                }
            }
            else -> {
                if (view is Chip) {
                    val checked = view.isChecked
                    if (checked) {
                        val text = view.text.toString()
                        viewModel.calculateTransportByType(
                            TransportExpenseViewModel.NON_FLIGHT,
                            text,
                            position
                        )
                    }
                }
            }
        }
    }

}