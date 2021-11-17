package com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.TypeTransportationDialogBinding
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory

class TypeTransportationDialog : DialogFragment() {
    private lateinit var binding: TypeTransportationDialogBinding
    private var isAir = false
    private lateinit var viewModel: ItineraryViewModel
    companion object {
        const val IS_AIR = "IS_AIR"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog_MinWidth)
        arguments?.let {
            isAir = it.getBoolean(IS_AIR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.type_transportation_dialog, container, false)
        binding = TypeTransportationDialogBinding.bind(v)
        val window = dialog?.window
        window?.setGravity(Gravity.TOP)
        val params = window?.attributes
        params?.y = 200
        window?.attributes = params
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(ItineraryViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        binding.rgTransportation.check(if (isAir) R.id.radio_air else R.id.radio_non_air)
        if (activity is View.OnClickListener){
            binding.radioAir.setOnClickListener(activity as View.OnClickListener)
            binding.radioNonAir.setOnClickListener(activity as View.OnClickListener)
        }
//        binding.radioAir.setOnClickListener {
//            if (it is RadioButton) {
//                val checked = it.isChecked
//                if (checked) {
//                    viewModel.setTypeTransportation(1)
//                }
//            }
//            dismiss()
//        }
//
        binding.tvClose.setOnClickListener {
            dismiss()
        }

//        binding.radioNonAir.setOnClickListener {
//            if (it is RadioButton) {
//                val checked = it.isChecked
//                if (checked) {
//                    viewModel.setTypeTransportation(2)
//                }
//            }
//            dismiss()
//        }
    }
}