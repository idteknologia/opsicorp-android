package com.opsigo.travelaja.module.settlement.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.FragmentCreateSettlementBinding
import com.opsigo.travelaja.viewmodel.DefaultViewModelFactory
import com.opsigo.travelaja.module.home.presenter.HomeViewModel
import com.opsigo.travelaja.module.settlement.TripSearchActivity
import com.opsigo.travelaja.module.settlement.viewmodel.SettlementViewModel

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
        binding.etTripCode.setOnClickListener {
            val intent = Intent(requireContext(),TripSearchActivity::class.java)
            intent.putExtra(TripSearchActivity.SELECTED,viewModel.selectedCode.get())
            requireActivity().startActivityForResult(intent,9)
        }
    }



}