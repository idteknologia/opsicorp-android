package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.ActivitySelectRoutePertaminaBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryAdapter
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryListener
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.dialog.TypeTransportationDialog
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.Itinerary
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.create_trip_plane.RoutesItinerary
import org.koin.android.ext.android.bind

class SelectTripRoutePertaminaActivity : AppCompatActivity(), ItineraryListener {
    private lateinit var viewModel: ItineraryViewModel
    private lateinit var binding: ActivitySelectRoutePertaminaBinding
    private var starDate = ""
    private var endDate = ""

    // format 15-07-2021
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_route_pertamina)
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(false,this)).get(ItineraryViewModel::class.java)
        binding.viewModel = viewModel
        val bundle = intent.getBundleExtra("data")
        val isInternational = bundle?.getBoolean(IS_INTERNATIONAL) ?: false
        starDate = bundle?.getString(START_DATE) ?: ""
        endDate = bundle?.getString(END_DATE) ?: ""
        viewModel.checkedInternational(isInternational)
        setRecycler()
        binding.btnNext.setOnClickListener {
            validation()
        }

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun validation() {
        if (viewModel.getItinerary().isComplete()) {
            val list = viewModel.itineraries
            val bundle = intent.getBundleExtra("data")
            val intent = Intent(this, RevieBudgetPertaminaActivity::class.java)
            intent.putExtra("itineraries",list.toTypedArray())
            intent.putExtra("data",bundle)
            startActivity(intent)
        } else {
            val warning = if (viewModel.getItinerary().isEmptyField()) getString(R.string.txt_warning_itinerary_emptyt_field) else getString(R.string.txt_itinerary_warning_city_all_same)
            Snackbar.make(binding.root,warning,Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showDialog() {
        TypeTransportationDialog().show(supportFragmentManager, "typeTransportation")
    }

    private fun setRecycler() {
        binding.rvItinerary.adapter = ItineraryAdapter(viewModel)
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
            NewCalendarViewOpsicorp().showCalendarViewMinMax(this,"yyyy-MM-dd",starDate,endDate, Constant.SINGGLE_SELECTED)
        } else if (type == 3) {
            showDialog()
        } else {
            val intent = Intent(this, CityActivity::class.java)
            intent.putExtra(IS_INTERNATIONAL, viewModel.isInternational.get())
            startActivityForResult(intent, type)
        }
    }

    companion object {
        const val IS_INTERNATIONAL = "is_international"
        const val START_DATE = "SDATE"
        const val END_DATE = "EDATE"
    }
}