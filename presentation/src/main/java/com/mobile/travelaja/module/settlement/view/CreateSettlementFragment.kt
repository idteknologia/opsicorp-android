package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.FragmentCreateSettlementBinding
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils

class CreateSettlementFragment : Fragment(),View.OnClickListener {
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
            if (binding.switchTransportation.isChecked){
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
        viewModel.getDetailTrip(viewModel.submitSettlement.TripId)
    }

    private fun showTransportation(){
        val cities = viewModel.detailTrip.value?.trip?.cities()?.toTypedArray()
        val modeTransports = viewModel.modeTransports.toTypedArray()
        cities?.let {
            val action = CreateSettlementFragmentDirections.actionCreateSettlementToTransportExpenseFragment(cities,modeTransports)
            findNavController().navigate(action)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.etBank -> navigateBank()
            R.id.ivBack -> activity?.finish()
            R.id.viewDetailInformation -> showTransportation()
            else -> navigateTripCode()
        }
    }

    private fun navigateBank(){
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToBankListFragment()
        findNavController().navigate(action)
    }

    private fun navigateTripCode(){
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToTripCodeFragment()
        findNavController().navigate(action)
    }

}