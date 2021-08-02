package com.mobile.travelaja.module.settlement.view

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.view.adapter.TicketRefundAdapter
import com.mobile.travelaja.module.settlement.viewmodel.TicketRefundViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.Ticket

class TicketRefundFragment : BaseListFragment<Ticket>(),ItemClickListener {
    private lateinit var adapter: TicketRefundAdapter
    private lateinit var viewModel : TicketRefundViewModel
    private val args: TicketRefundFragmentArgs by navArgs()
    private lateinit var dialog : MaterialAlertDialogBuilder
    private var position = -1
    private var tickets= arrayOf<Ticket>()

    override fun baseListAdapter(): BaseListAdapter<Ticket> {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(TicketRefundViewModel::class.java)
        viewModel.ticketRefunds.add(Ticket())
        arguments?.let {
            tickets = args.ticketRefunds
        }
        setTicket()
        adapter = TicketRefundAdapter(viewModel,this)
        setTitleName(R.string.transportation_form, R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
        return adapter
    }

    private fun setTicket(){
        val items = tickets.map { it.TicketNumber }.toTypedArray()
        dialog = MaterialAlertDialogBuilder(requireContext()).setTitle("Ticket Number")
        dialog.setItems(items){d,pos ->
            val ticket = tickets[pos]
            viewModel.setTicket(this.position,ticket)
            d.dismiss()
        }
    }

    override fun dividerEnabled(): Boolean = false
    override fun isSearchVisible(): Boolean = false
    override fun isButtonVisible(): Boolean = true

    override fun onRefresh() {
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList){
            addTicketRefund()
        }
    }

    override fun onClickItem(view: View, position: Int) {
        when(view.id){
            R.id.buttonRemove -> {
                removeItem(position)
            }
            else -> {
                this.position = position
                dialog.show()
            }
        }
    }

    private fun addTicketRefund(){
        val size = adapter.itemCount
        adapter.notifyItemInserted(size)
        viewModel.addItem()
    }

    private fun removeItem(pos : Int){
        viewModel.removeItem(pos)
        binding.rvBaseList.removeViewAt(pos)
        adapter.notifyItemRemoved(pos)
    }

}