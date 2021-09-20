package com.mobile.travelaja.module.settlement.view.content

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.viewmodel.OtherExpenseViewModel
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.ExpenseType

class ExpenseTypeFragment : BaseListFragment<ExpenseType>(),ExpenseTypeListener {
    private lateinit var adapter : ExpenseTypeAdapter
    private val args : ExpenseTypeFragmentArgs by navArgs()
    private var position = -1

    override fun baseListAdapter(): BaseListAdapter<ExpenseType> {
        arguments?.let {
            position = args.position
        }
        adapter = ExpenseTypeAdapter(this,args.nameCompare)
        val items = args.expenseType
        adapter.list = items.toMutableList()
        adapter.notifyDataSetChanged()
        isEnabledRefresh(false)
        setTitleName(R.string.expense_type)
        return adapter
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = false
    override fun isButtonBottomVisible(): Boolean = false

    override fun onRefresh() {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ivBack){
            findNavController().navigateUp()
        }
    }

    override fun onClickItem(data: ExpenseType) {
        if (position != -1){
            setFragmentResult(TYPE, bundleOf(DATA to data, POSITION to position))
            findNavController().navigateUp()
        }
    }

    companion object {
        const val TYPE = "type"
        const val DATA = "data"
        const val POSITION = "position"
    }

}