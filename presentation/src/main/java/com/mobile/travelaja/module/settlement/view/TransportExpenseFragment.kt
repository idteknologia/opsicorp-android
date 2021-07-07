package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.base.list.BaseListFragment
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.BR
import opsigo.com.domainlayer.model.settlement.TransportExpenses

class TransportExpenseFragment : BaseListFragment<TransportExpenses>() {
    private lateinit var adapter: TransportExpenseAdapter
    private lateinit var viewModel : SettlementViewModel
    private val args : TransportExpenseFragmentArgs by navArgs()

    override fun baseListAdapter(): BaseListAdapter<TransportExpenses> {
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        adapter = TransportExpenseAdapter(viewModel){ v,pos ->
            when (v.id) {
                R.id.etOriginCity -> {
                    showCityList(pos)
                }
                R.id.buttonRemove -> {
                    removeTransport(pos)
                }
                else -> {
                    showModeTransports(pos)
                }
            }
        }
        setTitleName(R.string.transportation_form,R.color.colorTextHint)
        setSubtitle(R.string.transportation_to_airport_or_non_airport)
        viewModel.getModeTransports()
        setButtonText(R.string.add_transportation)
        isEnabledRefresh(false)
        return adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { t->
                Utils.handleErrorMessage(requireContext(),t){ error ->
                    Toast.makeText(requireContext(),error,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showCityList(pos : Int){
        val list = args.cities
        MaterialAlertDialogBuilder(requireContext()).setTitle("City List").setItems(list){ dialog , which ->
            val city = list[which]
            viewModel.calculateTransportByCity(city, pos)
        }.show()
    }

    private fun showModeTransports(pos : Int){
        val list = viewModel.modeTransports.filter { it.Value == 2 }.map { it.Text }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext()).setTitle("Select Mode Non-Flight").setItems(list){ dialog , which ->
            viewModel.calculateTransportByType(2,list[which],pos)
        }.show()
    }

    override fun dividerEnabled(): Boolean = false

    override fun isSearchVisible(): Boolean = false

    override fun onRefresh() {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonBaseList){
            addTransport()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun isButtonVisible(): Boolean = true

    private fun addTransport(){
        if (!viewModel.isEmptyCityTransport()){
            val size = adapter.itemCount
            adapter.notifyItemInserted(size)
            viewModel.addTransport()
        }else {
            Toast.makeText(context,"Pastikan tidak ada field yang kosong",Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeTransport(pos : Int){
        if (!viewModel.isEmptyCityTransport()){
            viewModel.removeTransport(pos)
            adapter.notifyDataSetChanged()
        }else {
            Toast.makeText(context,"Pastikan tidak ada field yang kosong",Toast.LENGTH_SHORT).show()
        }

    }

}