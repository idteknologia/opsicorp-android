package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.FragmentCreateSettlementBinding
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils
import opsigo.com.domainlayer.model.settlement.OtherExpense
import com.mobile.travelaja.module.settlement.view.OtherExpenseFragment.Companion.KEY_OTHER_EXPENSE_LIST
import com.mobile.travelaja.module.settlement.view.OtherExpenseFragment.Companion.KEY_EXPENSE_TYPE_LIST
import com.mobile.travelaja.module.settlement.view.IntercityTransportFragment.Companion.KEY_INTERCITY_TRANSPORTS
import opsigo.com.domainlayer.model.settlement.ExpenseType
import opsigo.com.domainlayer.model.settlement.IntercityTransport

class CreateSettlementFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: SettlementViewModel
    private lateinit var binding: FragmentCreateSettlementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSettlementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.clickListener = this
        binding.increaseDecreaseView.setValue(1, 0, object : IncreaseDecreaseListener {
            override fun onChangeValue(value: Int) {
                viewModel.calculateOvernight(value)
            }
        })

        binding.checkboxDeclare.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.buttonNextEnabled.set(isChecked)
        }

        binding.switchTransportation.setOnClickListener { v ->
            if (binding.switchTransportation.isChecked) {
                showTransportation()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { errorString ->
                    Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.dayValueEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { value ->
                binding.increaseDecreaseView.setValue(1, value)
            }
        }
        viewModel.getDetailTrip()

        setFragmentResultListener(KEY_REQUEST) { k, b ->
            if (k == KEY_REQUEST){
                val items = b.getParcelableArrayList<OtherExpense>(KEY_OTHER_EXPENSE_LIST)
                viewModel.addingOtherExpense(items?.toList()!!)
                b.getParcelableArray(KEY_EXPENSE_TYPE_LIST)?.also {
                    if (viewModel.typeExpense.isEmpty() && it.isNotEmpty()) {
                        viewModel.typeExpense = it as Array<ExpenseType>
                    }
                }
            }

        }
        fragmentResultIntercityTransport()
    }

    private fun fragmentResultIntercityTransport(){
        setFragmentResultListener(KEY_REQUEST_INTERCITY_TRANSPORT){k,b ->
            if (k == KEY_REQUEST_INTERCITY_TRANSPORT){
                val items = b.getParcelableArrayList<IntercityTransport>(KEY_INTERCITY_TRANSPORTS)
                viewModel.addingIntercityTransport(items?.toList()!!)
            }
        }
    }

    private fun showTransportation() {
        val cities = viewModel.submitSettlement.value?.cities()?.toTypedArray()
        val modeTransports = viewModel.modeTransports.toTypedArray()
        cities?.let {
            val action =
                CreateSettlementFragmentDirections.actionCreateSettlementToTransportExpenseFragment(
                    cities,
                    modeTransports
                )
            findNavController().navigate(action)
        } ?: run {
            showWarning()
        }
    }

    private fun showWarning() {
        Toast.makeText(context, "Citi is Empty", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.etBank -> navigateBank()
            R.id.ivBack -> activity?.finish()
            R.id.viewDetailInformation -> showTransportation()
            R.id.viewDetailOtherExpense -> navigateOtherExpense()
            R.id.viewIntercity -> navigateIntercity()
            R.id.switchExpense -> {
                if (v is SwitchMaterial && v.isChecked) {
                    navigateOtherExpense()
                }
            }
            R.id.switchRefund -> {
                if (v is SwitchMaterial && v.isChecked) {
                    navigateTicketRefund()
                }
            }
            R.id.switchIntercity -> {
                if (v is SwitchMaterial) {
                    viewModel.isEnabledIntercity.set(v.isChecked)
                    if (v.isChecked)
                    navigateIntercity()
                }
            }
            R.id.buttonSubmit -> viewModel.submit()
            else -> navigateTripCode()
        }
    }

    // Todo Navigate Other Expense
    private fun navigateOtherExpense() {
        val expenseTypes = viewModel.typeExpense
        val others = viewModel.otherExpenses
        val isPcu = viewModel.submitSettlement.value?.TripType?.contains("pcu",true) ?: false
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToOtherExpenseFragment(
                expenseTypes,
                others.toTypedArray(),
                isPcu
            )
        findNavController().navigate(action)
    }

    private fun navigateBank() {
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToBankListFragment()
        findNavController().navigate(action)
    }

    private fun navigateTripCode() {
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToTripCodeFragment()
        findNavController().navigate(action)
    }

    //Todo navigate intercity transport
    private fun navigateIntercity() {
        val golper = viewModel.submitSettlement.value?.Golper ?: 0
        val items  = viewModel.intercityTransport.toTypedArray()
        val total  = viewModel.totalIntercity.toString()
        val action = CreateSettlementFragmentDirections.actionCreateSettlementToIntercityTransportFragment(
                     viewModel.routes.toTypedArray(),
                     golper, items, total)
        findNavController().navigate(action)
    }

    private fun navigateTicketRefund() {
        val list = viewModel.tickets
        if (!list.isNullOrEmpty()) {
            val action =
                CreateSettlementFragmentDirections.actionCreateSettlementToTicketRefundFragment(list.toTypedArray())
            findNavController().navigate(action)
        } else {
            showWarning()
        }

    }

    companion object {
        const val KEY_REQUEST = "CREATE_SETTLEMENT"
        const val KEY_REQUEST_INTERCITY_TRANSPORT = "KEY_REQUEST_INTERCITY_TRANSPORT"
    }

}