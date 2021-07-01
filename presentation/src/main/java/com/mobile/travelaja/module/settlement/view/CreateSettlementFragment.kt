package com.mobile.travelaja.module.settlement.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.databinding.FragmentCreateSettlementBinding
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import com.mobile.travelaja.module.settlement.TripSearchActivity

import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel

class CreateSettlementFragment : Fragment() {
    private lateinit var viewModel : SettlementViewModel
    private lateinit var binding : FragmentCreateSettlementBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateSettlementBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), DefaultViewModelFactory(false, requireContext())).get(SettlementViewModel::class.java)
        binding.viewModel = viewModel
        binding.increaseDecreaseView.setValue(1,0,object : IncreaseDecreaseListener{
            override fun onChangeValue(value: Int) {
                Toast.makeText(context,value.toString(),Toast.LENGTH_SHORT).show()
            }

        })
        binding.etTripCode.setOnClickListener {
            val intent = Intent(requireContext(), TripSearchActivity::class.java)
            intent.putExtra(TripSearchActivity.SELECTED,viewModel.selectedCode.get())
            requireActivity().startActivityForResult(intent,9)
        }
        binding.checkboxDeclare.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.buttonNextEnabled.set(isChecked)
        }
    }



}