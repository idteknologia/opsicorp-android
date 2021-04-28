package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.ActivitySelectRoutePertaminaBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryAdapter
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryListener
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.dialog.TypeTransportationDialog
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModelFactory
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp

class SelectTripRoutePertaminaActivity : AppCompatActivity(), ItineraryListener {
    private lateinit var viewModel: ItineraryViewModel
    private lateinit var binding: ActivitySelectRoutePertaminaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_route_pertamina)
        viewModel = ViewModelProvider(this, ItineraryViewModelFactory(this)).get(ItineraryViewModel::class.java)
        binding.viewModel = viewModel
        val isInternational = intent.getBooleanExtra(IS_INTERNATIONAL, false)
        viewModel.checkedInternational(isInternational)
        setRecycler()
        binding.btnNext.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if (viewModel.getItinerary().isComplete()) {

        } else {
            val warning = if (viewModel.getItinerary().isEmptyField()) "Harap isi" else "Tidak boleh sama"
            Toast.makeText(this, warning, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog() {
        TypeTransportationDialog().show(supportFragmentManager, "typeTransportation")
    }

    private fun setRecycler() {
        binding.rvItinerary.adapter = ItineraryAdapter(viewModel)
    }

    companion object {
        const val IS_INTERNATIONAL = "is_international"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                val name = data?.getStringExtra(CityActivity.RESULT)
                if (!name.isNullOrEmpty())
                viewModel.setOriginFrom(name ?: "")
            }
            2 -> {
                val name = data?.getStringExtra(CityActivity.RESULT)
                if (!name.isNullOrEmpty())
                viewModel.setDestination(name)
            }
            else -> {
                val date = data?.getStringExtra("displayStartDate")
                if (!date.isNullOrEmpty())
                viewModel.setStartDate(date ?: "")
            }
        }

    }

    override fun clickItemItinerary(pos: Int, type: Int) {
        if (type == 0) {
            NewCalendarViewOpsicorp().showCalendarView(this, Constant.SINGGLE_SELECTED)
        } else if (type == 3) {
            showDialog()
        } else {
            val intent = Intent(this, CityActivity::class.java)
            intent.putExtra(IS_INTERNATIONAL, viewModel.isInternational.get())
            startActivityForResult(intent, type)
        }
    }
}