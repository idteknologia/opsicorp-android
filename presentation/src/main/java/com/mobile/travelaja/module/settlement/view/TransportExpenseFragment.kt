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
    private var hasUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backAction()
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

        setModeTransports()
        setDataTransports()
        adapter =
            TransportExpenseAdapter(viewModel, modes = viewModel.modeTransports, listener = this)
        setUi()
        return adapter
    }

    private fun setUi() {
        setEnableButtonBottom(hasUpdate)
        showingTotal(R.string.grand_total, R.string.other_expense_idr)
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
    }

    private fun setDataTransports() {
        val data = settlementViewModel.getDetailSubmit()
        val transports = data!!.TransportExpenses
        if (transports.isNotEmpty() && viewModel.transportExpenses.isEmpty()) {
            viewModel.addTotal(data.TotalTransportExpense.toDouble())
            viewModel.addingTransportExpense(transports)
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
            viewModel.modeFlight = viewModel.modeTransports.last { it.Value == SettlementViewModel.TYPE_FLIGHT }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { error ->
                    val errorDesc = when (error) {
                        Utils.EMPTY -> getString(R.string.empty_mode_transports)
                        MODE_NOT_SUPPORT -> getString(R.string.mode_transport_not_support)
                        else -> error
                    }
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
        viewModel.total.observe(viewLifecycleOwner) {
            if (it != null) {
                val value = Utils.formatCurrency(it)
                textBottomValue.set(value)
            }
        }

        viewModel.hasUpdate.observe(viewLifecycleOwner) { hasUpdate ->
            if (!this.hasUpdate)
                setEnableButtonBottom(true)
            this.hasUpdate = hasUpdate
        }

        viewModel.loading.observe(viewLifecycleOwner){ loading ->
            isLoading.set(loading)
        }

    }

    private fun saveTransports() {
        val indexEmpty = viewModel.indexFirstEmpty()
        if (indexEmpty != -1) {
            val y = binding.rvBaseList.getChildAt(indexEmpty).y - 30f
            binding.nested.scrollTo(0, y.toInt())
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
            val transportsExpense = viewModel.transportExpenses
            val modeTransport = viewModel.modeTransports
            settlementViewModel.addingTransportExpense(transportsExpense, modeTransport)
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
    override fun isButtonBottomVisible(): Boolean = true

    override fun onRefresh() {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList) {
            addTransport()
        } else if (v?.id == R.id.buttonBottom) {
            saveTransports()
        } else {
            backAction()
        }
    }

    private fun backAction(){
        if (hasUpdate){
            showWarning(
                R.string.alert,
                R.string.warning_back_settlement_transport_expense,
                R.string.dont_save,
                R.string.save,
                WARNING_NAVIGATEUP
            )
        }else {
            navigateBack()
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
        if (pos == viewModel.indexEmpty.get()) {
            viewModel.indexEmpty.set(-1)
        }
        viewModel.removeTransport(pos)
        binding.rvBaseList.removeViewAt(pos)
        adapter.notifyItemRemoved(pos)
    }

    override fun onClickItem(view: View, position: Int) {
        when (view.id) {
            R.id.buttonRemove -> {
                if (viewModel.loading.value == true)
                    return
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

    companion object {
        const val WARNING_NAVIGATEUP = 1
        const val WARNING_SAME_DATA = 2
        const val WARNING_ISLOADING = 3
        const val MODE_NOT_SUPPORT = "mode not support"
    }


}