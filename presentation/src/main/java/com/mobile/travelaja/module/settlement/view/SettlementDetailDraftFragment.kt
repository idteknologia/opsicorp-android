package com.mobile.travelaja.module.settlement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.SettlementDraftDetailFragmentBinding
import com.mobile.travelaja.module.settlement.view.adapter.RefundsAdapter
import com.mobile.travelaja.module.settlement.viewmodel.DraftSettlementViewModel
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.settlement.Ticket

class SettlementDetailDraftFragment : Fragment() ,View.OnClickListener{
    private lateinit var binding: SettlementDraftDetailFragmentBinding
    private lateinit var viewModel: DraftSettlementViewModel
    private lateinit var settlementViewModel : SettlementViewModel
    private val args: SettlementDetailDraftFragmentArgs by navArgs()
    private var idTrip = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTrip = args.idTrip
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.settlement_draft_detail_fragment, container, false)
        binding = SettlementDraftDetailFragmentBinding.bind(view)
        binding.ivChangeEdit.setOnClickListener(this)
        binding.buttonSubmit.setOnClickListener(this)
        setViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getDetailTrip(idTrip)
        setTicketRefund()

        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { errorString ->
                    Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, requireContext())
        ).get(DraftSettlementViewModel::class.java)

        settlementViewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
    }

    private fun setTicketRefund() {
        val adapter = RefundsAdapter()
        binding.rvRefundTicket.adapter = adapter
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setDataTicketRefund")
        fun setDataTicketRefunds(recyclerView: RecyclerView, list: MutableList<Ticket>) {
            val adapter = recyclerView.adapter
            if (adapter is RefundsAdapter) {
                adapter.list = list
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ivChangeEdit){
            val detail = viewModel.submitSettlement.value
            if (detail != null){
                settlementViewModel.changeEditDraft(detail)
                val action = SettlementDetailDraftFragmentDirections.actionSettlementDetailDraftFragmentToCreateSettlement()
                findNavController().navigate(action)
            }
        }else{
            //Todo submit
        }
    }
}