package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.OtherExpenseAdapter
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.ExpenseType
import opsigo.com.domainlayer.model.settlement.OtherExpense

class OtherExpenseFragment : BaseListFragment<OtherExpense>(), ItemClickListener {
    private lateinit var viewModel: OtherExpenseViewModel
    private lateinit var adapter: OtherExpenseAdapter
    private var expenseTypes = arrayOf<String>()

    override fun baseListAdapter(): BaseListAdapter<OtherExpense> {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(OtherExpenseViewModel::class.java)
        setOtherExpense()
        adapter = OtherExpenseAdapter(viewModel, this)
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        return adapter
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {

            } else if (!loading && viewModel.expenseTypes.isNotEmpty()) {
                showExpenseType()
            } else {
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { error ->
                Utils.handleErrorMessage(requireContext(), error) { errorString ->
                    Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setOtherExpense() {
        viewModel.otherExpenses.add(OtherExpense())
    }

    override fun onRefresh() {
    }

    override fun onClick(v: View?) {
    }

    override fun onClickItem(view: View, position: Int) {
        if (viewModel.expenseTypes.isEmpty()) {
            viewModel.getExpenseType()
        } else {
            showExpenseType()
        }
    }

    private fun showExpenseType() {
        if (expenseTypes.isEmpty()) {
            expenseTypes = viewModel.expenseTypes.map { it.Text }.toTypedArray()
        }
        MaterialAlertDialogBuilder(requireContext()).setTitle("Expense Type")
            .setItems(expenseTypes) { d, pos ->
                val value = viewModel.expenseTypes[pos]
                setExpenseType(value)
                d.dismiss()
            }.show()
    }

    private fun setExpenseType(expenseType : ExpenseType) {
        viewModel.otherExpenses[0].ExpenseType = expenseType.Value
        viewModel.otherExpenses[0].expenseName = expenseType.Text
    }
}