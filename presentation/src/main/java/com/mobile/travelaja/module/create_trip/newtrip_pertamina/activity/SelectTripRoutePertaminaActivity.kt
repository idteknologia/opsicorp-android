package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.opsicorp.sliderdatepicker.utils.Constant
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.ActivitySelectRoutePertaminaBinding
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryAdapter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.ItineraryListener
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.TypeTransportationDialog
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.Itinerary
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.travel_request.ChangeTripModel

class SelectTripRoutePertaminaActivity : AppCompatActivity(), ItineraryListener,
    View.OnClickListener {
    private lateinit var viewModel: ItineraryViewModel
    private lateinit var binding: ActivitySelectRoutePertaminaBinding
    private var starDate = ""
    private var endDate = ""
    private var nonCbt = false
    private var position = -1
    var dataChangeTrip = ChangeTripModel()

    // format 15-07-2021
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_route_pertamina)
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, this)
        ).get(ItineraryViewModel::class.java)
        binding.viewModel = viewModel
        val bundle = intent.getBundleExtra("data")
        val isInternational = bundle?.getBoolean(IS_INTERNATIONAL) ?: false
        nonCbt = bundle?.getBoolean(NON_CBT) ?: false
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
        binding.btAddOtherFlight.setOnClickListener {
           addItem()
        }

        getDataChangeTrip()
    }

    private fun getDataChangeTrip() {
        dataChangeTrip = Serializer.deserialize(intent?.getBundleExtra("data")?.getString("data_change_trip"), ChangeTripModel::class.java)
        if (dataChangeTrip.isChangeTrip.equals(true)){
            viewModel.itineraries.clear()
            starDate = dataChangeTrip.startDate
            endDate = dataChangeTrip.returnDate
            dataChangeTrip.routes.forEach {
                val itinerary = Itinerary(
                        Transportation = it.transportation,
                        Origin = it.origin,
                        Destination = it.destination
                )
                viewModel.itineraries.add(itinerary)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                val name = data?.getStringExtra(CityActivity.RESULT)
                if (!name.isNullOrEmpty())
                    viewModel.setOriginFrom(name, position)
            }
            2 -> {
                val name = data?.getStringExtra(CityActivity.RESULT)
                if (!name.isNullOrEmpty())
                    viewModel.setDestination(name, position)
            }
            else -> {
                val date = data?.getStringExtra("displayStartDate")
                if (!date.isNullOrEmpty())
                    viewModel.setStartDate(date, position)
            }
        }
        position = -1

    }

    //Todo sending itineraries
    private fun validation() {
        if (viewModel.isCompleteItems()) {
            val list = viewModel.itineraries
            val bundle = intent.getBundleExtra("data")
            val intent = Intent(this, RevieBudgetPertaminaActivity::class.java)
            intent.putExtra("itineraries", list.toTypedArray())
            intent.putExtra("data", bundle)
            startActivity(intent)
        } else {
            val warning = if (viewModel.isEmptyItems()
            ) getString(R.string.txt_warning_itinerary_emptyt_field) else getString(R.string.txt_itinerary_warning_city_all_same)
            Snackbar.make(binding.root, warning, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showDialog() {
        val isAir = viewModel.getItinerary(position).Transportation == 1
        val fragment = TypeTransportationDialog()
        fragment.arguments = bundleOf(TypeTransportationDialog.IS_AIR to isAir)
        fragment.show(supportFragmentManager, "typeTransportation")
    }

    private fun closeDialog() {
        val fragment =
            supportFragmentManager.findFragmentByTag("typeTransportation") as DialogFragment
        fragment.dismiss()
    }

    private fun setRecycler() {
        binding.rvItinerary.adapter = ItineraryAdapter(viewModel)
    }


    override fun clickItemItinerary(pos: Int, type: Int) {
        position = pos
        when (type) {
            0 -> {
                if (!nonCbt){
                    NewCalendarViewOpsicorp().showCalendarViewMinMax(
                        this,
                        "yyyy-MM-dd",
                        starDate,
                        endDate,
                        Constant.SINGGLE_SELECTED
                    )
                } else {
                    NewCalendarViewOpsicorp().showCalendarBackDateMinMax(
                        this,
                        "yyyy-MM-dd",
                        starDate,
                        endDate,
                        Constant.SINGGLE_SELECTED,
                        true
                    )
                }
            }
            3 -> {
                showDialog()
            }
            4 -> {
                removeItem(pos)
            }
            else -> {
                selectCity(type)
            }
        }
    }

    private fun selectCity(type: Int) {
        val intent = Intent(this, CityActivity::class.java)
        intent.putExtra(IS_INTERNATIONAL, viewModel.isInternational.get())
        startActivityForResult(intent, type)
    }

    private fun removeItem(position: Int) {
        viewModel.itineraries.removeAt(position)
        binding.rvItinerary.adapter?.notifyDataSetChanged()
        viewModel.isRemoveVisible.set(viewModel.itineraries.size > 1)
    }

    private fun addItem(){
        val size = viewModel.itineraries.size
        viewModel.itineraries.add(Itinerary())
        binding.rvItinerary.adapter?.notifyItemInserted(size)
        viewModel.isRemoveVisible.set(true)

    }

    override fun onClick(v: View?) {
        if (v is RadioButton) {
            val id = v.id
            val checked = v.isChecked
            setTransportation(id, checked)
            closeDialog()

        }
    }

    private fun setTransportation(id: Int, checked: Boolean) {
        if (checked) {
            val type = if (id == R.id.radio_air) 1 else 2
            viewModel.setTypeTransportation(type, position)
            position = -1
        }
    }

    companion object {
        const val IS_INTERNATIONAL = "is_international"
        const val NON_CBT = "is_noncbt"
        const val START_DATE = "SDATE"
        const val END_DATE = "EDATE"
    }


}