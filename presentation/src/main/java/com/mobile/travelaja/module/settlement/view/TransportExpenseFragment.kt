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
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.module.settlement.viewmodel.TransportExpenseViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.ModeTransport
import opsigo.com.domainlayer.model.settlement.TransportExpenses

class TransportExpenseFragment : BaseListFragment<TransportExpenses>() {
    private lateinit var adapter: TransportExpenseAdapter
    private lateinit var viewModel: TransportExpenseViewModel
    private lateinit var settlementViewModel: SettlementViewModel
    private val args: TransportExpenseFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            showWarning(R.string.alert,R.string.warning_back_settlement_transport_expense,R.string.dont_save,R.string.save,
                WARNING_NAVIGATEUP)
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
        val transports = settlementViewModel.submitSettlement.TransportExpenses
        if (transports.isNotEmpty()) {
            viewModel.totalTransport.value = settlementViewModel.totalTransport.get()
            viewModel.transportExpenses.clear()
            viewModel.transportExpenses.addAll(transports)
        }
        adapter = TransportExpenseAdapter(viewModel, { v, pos ->
            when (v.id) {
                R.id.etOriginCity -> {
                    showCityList(pos)
                }
                R.id.buttonRemove -> {
                    removeTransport(pos)
                }
            }
        })
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
        return adapter
    }

    private fun setChips(list: List<ModeTransport>) {
        val modes = list.filter { it.Value == 2 }
        adapter.modes = modes
        adapter.notifyDataSetChanged()
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

        viewModel.modeTransport.observe(viewLifecycleOwner) {
            setChips(it)
        }
        viewModel.warning.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { warning ->
                if (warning) {
                    MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.alert)
                        .setMessage(R.string.has_same_transport_expense)
                        .setPositiveButton(R.string.close) { t, l ->
                            t.dismiss()
                        }.show()
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

    private fun saveTransports(){
        if (viewModel.isEmptyCityTransport()) {
            showWarning(R.string.alert, R.string.warning_not_complete_transport_expense,R.string.dont_save,R.string.close,
                WARNING_SAVE)
        } else {
            val transports = viewModel.transportExpenses
            val total = viewModel.totalTransport.value
            settlementViewModel.submitSettlement.TransportExpenses.addAll(transports)
            settlementViewModel.submitSettlement.TotalTransportExpense = total ?: 0
            navigateBack()
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun showCityList(pos: Int) {
        val list = args.cities
        MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.select_city)
            .setItems(list) { dialog, which ->
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
            showWarning(R.string.alert,R.string.warning_back_settlement_transport_expense,R.string.dont_save,R.string.save,
                WARNING_NAVIGATEUP)
        }
    }

    override fun isButtonVisible(): Boolean = true

    private fun addTransport() {
        if (!viewModel.isEmptyCityTransport()) {
            val size = adapter.itemCount
            adapter.notifyItemInserted(size)
            viewModel.addTransport()
        } else {
            Toast.makeText(
                context,
                getString(R.string.warning_not_complete_transport_expense),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun showWarning(@StringRes title: Int,
                            @StringRes message: Int,
                            @StringRes negativeName : Int,
                            @StringRes positiveName : Int,
                            type : Int
    ) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(title)
            .setMessage(message).setPositiveButton(positiveName) { d, l ->
                if (type == WARNING_NAVIGATEUP){
                    saveTransports()
                }else {
                    d.dismiss()
                }
            }.setNegativeButton(negativeName) { d, l ->
                d.dismiss()
                navigateBack()
            }.show()
    }

    private fun removeTransport(pos: Int) {
        if (!viewModel.isEmptyCityTransport()) {
            viewModel.removeTransport(pos)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                context,
                getString(R.string.warning_not_complete_transport_expense),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }


    companion object {
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
        const val WARNING_SAVE = 0
        const val WARNING_NAVIGATEUP = 1
    }

}