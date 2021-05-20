package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.TypeTransportationDialogBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.opsigo.travelaja.viewmodel.DefaultViewModelFactory

class TypeTransportationDialog : DialogFragment(){
    private lateinit var binding : TypeTransportationDialogBinding
    private var isAir = false
    private lateinit var viewModel: ItineraryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,R.style.Theme_AppCompat_Light_Dialog_MinWidth)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.type_transportation_dialog,container,false)
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
        viewModel = ViewModelProvider(requireActivity(), DefaultViewModelFactory(false,requireContext())).get(ItineraryViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        isAir = viewModel.getItinerary().Transportation == 1
        binding.rgTransportation.check(if (isAir) R.id.radio_air else R.id.radio_non_air)
        binding.radioAir.setOnClickListener{
            if (it is RadioButton){
                val checked = it.isChecked
                if (checked){
                    viewModel.setTypeTransportation(1)
                }
            }
            dismiss()
        }

        binding.tvClose.setOnClickListener {
            dismiss()
        }

        binding.radioNonAir.setOnClickListener{
            if (it is RadioButton){
                val checked = it.isChecked
                if (checked){
                    viewModel.setTypeTransportation(2)
                }
            }
            dismiss()
        }
    }
}