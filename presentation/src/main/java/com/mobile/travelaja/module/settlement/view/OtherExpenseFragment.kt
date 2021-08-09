package com.mobile.travelaja.module.settlement.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.OtherExpenseAdapter
import com.mobile.travelaja.module.settlement.view.content.ExpenseTypeFragment
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.ExpenseType
import opsigo.com.domainlayer.model.settlement.OtherExpense

class OtherExpenseFragment : BaseListFragment<OtherExpense>(), ItemClickListener,DialogInterface.OnClickListener {
    private lateinit var viewModel: OtherExpenseViewModel
    private lateinit var settlementViewModel: SettlementViewModel
    private lateinit var adapter: OtherExpenseAdapter
    private var expenseTypes = arrayOf<String>()
    private var position = -1
    private lateinit var typeExpenseDialog : MaterialAlertDialogBuilder
    private val args : OtherExpenseFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            showingWarning(
//                R.string.alert,
//                R.string.warning_back_settlement_transport_expense,
//                R.string.dont_save,
//                R.string.save,
//                TransportExpenseFragment.WARNING_NAVIGATEUP
//            )
//        }
    }

    override fun baseListAdapter(): BaseListAdapter<OtherExpense> {
        setViewModel()
        addTypeExpense()
        addItems()
        adapter = OtherExpenseAdapter(viewModel, this)
        setTitleName(R.string.other_expense, R.color.colorTextHint)
        setSubtitle(R.string.transportation_form)
        setButtonText(R.string.add_other_expense)
        isEnabledRefresh(false)
        return adapter
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = true
    override fun isButtonBottomVisible(): Boolean  = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let { loading ->
                binding.rvBaseList.isEnabled = !loading
                if (!loading && viewModel.expenseTypes.isNotEmpty()) {
                    setExpenseType()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { error ->
                Utils.handleErrorMessage(requireContext(), error) { errorString ->
                    Toast.makeText(context, if (errorString != Utils.EMPTY)errorString else getString(R.string.alert_empty_expense_type),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        setFragmentResultListener(ExpenseTypeFragment.TYPE){ key, bundle ->
            val data = bundle.get(ExpenseTypeFragment.DATA)
            val pos = bundle.getInt(ExpenseTypeFragment.POSITION)
            if (data is ExpenseType && pos != -1){
                val item = OtherExpense()
                item.ExpenseType = data.ExpenseType
                item.expenseName = data.Description
                viewModel.setItem(item,position)
                val list = settlementViewModel._otherExpenses
                println(list)
//                viewModel.setExpenseType(data,position,getString(R.string.other_expense_idr))
            }
        }
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(OtherExpenseViewModel::class.java)

        settlementViewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
    }

    private fun addTypeExpense() {
        typeExpenseDialog = MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.expense_type)
        val typeExpense = settlementViewModel.typeExpense
        if (typeExpense.isNotEmpty() && viewModel.expenseTypes.isEmpty()){
            viewModel.expenseTypes.clear()
            viewModel.expenseTypes.addAll(typeExpense)
            expenseTypes = typeExpense.map { it.Description }.toTypedArray()
        }
    }

    private fun addItems(){
        val items2 = args.otherExpenses
        if (items2.isNotEmpty() && viewModel.items.isEmpty()){
            viewModel.items .clear()
            viewModel.items.addAll(items2)
            viewModel.isRemoveVisible.set(items2.size > 1)
        }else if (viewModel.items.isEmpty()) {
            val item = OtherExpense()
            item.Currency = getString(R.string.other_expense_idr)
            viewModel.items.add(item)
        }
    }

    override fun onRefresh() {
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList){
            addItem()
        }else if (v?.id == R.id.buttonBottom) {
            saveItem()
        }else {
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
        this.position = position
        when(view.id){
            R.id.toggleButton -> {
                if (view is ToggleButton){
                    val text = view.text.toString()
                    val d = viewModel.items[position]
                    d.Currency = text
                    viewModel.setItem(d,position)
                }
            }
            R.id.buttonRemove -> {
                removeItem(position)
            }
            else->{
                if (viewModel.expenseTypes.isEmpty()) {
                    viewModel.getExpenseType()
                } else {
                    navigateExpenseType(position)
                }
            }
        }
        binding.rvBaseList.clearFocus()
    }

    private fun navigateExpenseType(position : Int){
        val expenseTypes = viewModel.expenseTypes.toTypedArray()
        val action = OtherExpenseFragmentDirections.actionOtherExpenseFragmentToExpenseTypeFragment(expenseTypes,position)
        findNavController().navigate(action)
    }

    private fun setExpenseType() {
//        if (expenseTypes.isEmpty()) {
//            val expenseType = viewModel.expenseTypes
//                .filter { !it.Description.isNullOrEmpty() || !it.ExpenseType.isNullOrEmpty() }
//                .map {
//                    it.Description
//                }
//            expenseTypes = expenseType.toTypedArray()
//            typeExpenseDialog.setItems(expenseTypes,this).show()
//        }

        navigateExpenseType(position)
    }


    private fun setExpenseType(expenseType : ExpenseType,position: Int) {
        val item = viewModel.items[position]
        item.ExpenseType = expenseType.ExpenseType
        item.expenseName = expenseType.Description
    }

    private fun addItem(){
        val size = adapter.itemCount
        adapter.notifyItemInserted(size)
        viewModel.addItem(getString(R.string.other_expense_idr))
    }

    private fun removeItem(pos: Int) {
        if (pos == viewModel.indexEmpty.get()){
            viewModel.indexEmpty.set(-1)
        }
        viewModel.removeItem(pos)
        binding.rvBaseList.removeViewAt(pos)
        adapter.notifyItemRemoved(pos)
    }

    private fun saveItem(){
        val indexEmpty = viewModel.indexFirstEmpty()
        if (indexEmpty != -1){
            val y = binding.rvBaseList.getChildAt(indexEmpty).y - 30f
            binding.nested.scrollTo(0, y.toInt())
            val snackbar = Snackbar.make(
                binding.root,
                R.string.warning_not_complete_other_expense,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction(R.string.ok) {
                snackbar.dismiss()
            }
            snackbar.show()
        } else {
            val items = arrayListOf<OtherExpense>()
            val list = viewModel.items.toList()
            items.addAll(list)
//            settlementViewModel.addingOtherExpense(items)
            setFragmentResult("Expense", bundleOf("expenses" to items))
            if (settlementViewModel.typeExpense.isEmpty()){
                settlementViewModel.typeExpense.addAll(viewModel.expenseTypes)
            }
            navigateUp()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which != -1){
            val value = viewModel.expenseTypes[which]
            setExpenseType(value, position)
            dialog?.dismiss()
        }
    }

    override fun onWarningClick(dialogInterface: DialogInterface, type: Int, isPositive: Boolean) {
        super.onWarningClick(dialogInterface, type, isPositive)
        if (isPositive){
            if (type == TransportExpenseFragment.WARNING_NAVIGATEUP) {
                saveItem()
            } else {
                dialogInterface.dismiss()
            }
        }else {
            dialogInterface.dismiss()
            navigateUp()
        }
    }
}