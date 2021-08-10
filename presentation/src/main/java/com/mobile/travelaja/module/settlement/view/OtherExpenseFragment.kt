package com.mobile.travelaja.module.settlement.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.OtherExpenseAdapter
import com.mobile.travelaja.module.settlement.view.content.ExpenseTypeFragment
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.ExpenseType
import opsigo.com.domainlayer.model.settlement.OtherExpense
import com.mobile.travelaja.module.settlement.view.CreateSettlementFragment.Companion.KEY_REQUEST

class OtherExpenseFragment : BaseListFragment<OtherExpense>(), ItemClickListener,DialogInterface.OnClickListener {
    private lateinit var viewModel: OtherExpenseViewModel
    private lateinit var adapter: OtherExpenseAdapter
    private var position = -1
    private val args : OtherExpenseFragmentArgs by navArgs()
    private var expenseTypes = arrayOf<ExpenseType>()
    private var isPcu = false

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
            isPcu = args.isPcu
        }
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
                viewModel.setExpenseType(data,pos,getString(R.string.other_expense_idr))
            }
        }
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(OtherExpenseViewModel::class.java)
    }

    // Todo add expense type
    private fun addTypeExpense() {
        val items = args.expenseType
        if (items.isNotEmpty()){
            expenseTypes = items
        }
    }

    //Todo add items
    private fun addItems(){
        val items = args.otherExpenses
        if (items.isNotEmpty() && viewModel.items.isEmpty()){
            viewModel.items.clear()
            viewModel.items.addAll(items)
            viewModel.isRemoveVisible.set(items.size > 1)
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
            saveItems()
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
                }
            }
            R.id.buttonRemove -> {
                removeItem(position)
            }
            else->{
                if (viewModel.expenseTypes.isEmpty()) {
                    getExpenseType()
                } else {
                    navigateExpenseType(position)
                }
            }
        }
        binding.rvBaseList.clearFocus()
    }

    private fun getExpenseType(){
        if (!viewModel.isLoading){
            viewModel.getExpenseType(isPcu)
        }else {
            showingWarning(
                R.string.waiting, R.string.warning_loading, -1, R.string.ok,
                TransportExpenseFragment.WARNING_ISLOADING
            )
        }
    }

    private fun navigateExpenseType(position : Int){
        val exType = viewModel.getItem(position)?.expenseName ?: ""
        val action = OtherExpenseFragmentDirections.actionOtherExpenseFragmentToExpenseTypeFragment(exType,expenseTypes,position)
        findNavController().navigate(action)
    }

    private fun setExpenseType() {
        expenseTypes = viewModel.expenseTypes.toTypedArray()
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

    //Todo save item to detail
    private fun saveItems(){
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
            val list : ArrayList<OtherExpense> = viewModel.items
            val bundle = bundleOf()
            bundle.putParcelableArrayList(KEY_OTHER_EXPENSE_LIST,list)
            bundle.putParcelableArray(KEY_EXPENSE_TYPE_LIST,expenseTypes)
            setFragmentResult(KEY_REQUEST, bundle)
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
                saveItems()
            } else {
                dialogInterface.dismiss()
            }
        }else {
            dialogInterface.dismiss()
            navigateUp()
        }
    }

    companion object{
        const val KEY_OTHER_EXPENSE_LIST = "OTHER_EXPENSE_LIST"
        const val KEY_EXPENSE_TYPE_LIST = "EXPENSE_TYPE_LIST"
    }
}