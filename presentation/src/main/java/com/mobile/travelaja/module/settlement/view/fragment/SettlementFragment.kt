package com.mobile.travelaja.module.settlement.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.module.settlement.view.screen.SettlementScreen
import com.mobile.travelaja.module.settlement.viewmodel.TripViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory

class SettlementFragment : Fragment() {
    private lateinit var viewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setViewModel()
        return ComposeView(requireContext()).apply{
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                SettlementScreen(){
                    activity?.finish()
                }
            }
        }
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(TripViewModel::class.java)
    }
}