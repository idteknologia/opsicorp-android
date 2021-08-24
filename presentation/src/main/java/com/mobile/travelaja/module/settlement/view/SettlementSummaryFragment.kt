package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.SettlementSummaryFragmentBinding
import com.mobile.travelaja.module.settlement.view.adapter.AttachmentAdapter
import com.mobile.travelaja.module.settlement.view.adapter.SummaryPersonalAdapter
import com.mobile.travelaja.module.settlement.viewmodel.DraftSettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory

class SettlementSummaryFragment : Fragment() {
    private lateinit var binding : SettlementSummaryFragmentBinding
    private lateinit var viewModel: DraftSettlementViewModel
    private val args : SettlementSummaryFragmentArgs by navArgs()
    private lateinit var adapter: AttachmentAdapter
    private lateinit var personalAdapter : SummaryPersonalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.settlement_summary_fragment,container,false)
        binding = SettlementSummaryFragmentBinding.bind(view)
        setViewModel()
        setRecyclerAttachment()
        setRecyclerPersonal()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getDetailTrip("GetSummary",args.idTrip)
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(DraftSettlementViewModel::class.java)
    }

    private fun setRecyclerAttachment() {
        adapter = AttachmentAdapter(binding.rvAttachment, null)
        binding.rvAttachment.adapter = adapter
    }

    private fun setRecyclerPersonal(){
        personalAdapter = SummaryPersonalAdapter()
        binding.rvParticipants.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        binding.rvParticipants.adapter = personalAdapter
    }
}