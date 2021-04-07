package com.opsicorp.travelaja.feature_flight.filter

import android.os.Build
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsicorp.travelaja.feature_flight.adapter.FilterFlightAdapter
import com.opsicorp.travelaja.feature_flight.adapter.FilterFlightCabinAdapter
import com.opsigo.travelaja.module.accomodation.dialog.accomodation_preferance.AccomodationPreferanceModel
import com.opsigo.travelaja.module.accomodation.dialog.accomodation_preferance.SelectAccomodationPreferance
import com.opsigo.travelaja.module.item_custom.btn_filter.FilterTransitOpsicorp
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.filter_flight_activity_new.*
import kotlinx.android.synthetic.main.filter_flight_activity_new.btn_next
import kotlinx.android.synthetic.main.flight_filter_fragment_new.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel
import org.koin.core.KoinComponent

class FilterFlightActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener, FilterTransitOpsicorp.OnclickFilterListener, KoinComponent, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.filter_flight_activity_new
    }

    var dataCabin = ArrayList<FilterFlightModel>()
    var dataDeparture  = ArrayList<FilterFlightModel>()
    var dataNameCabin = ArrayList<AccomodationPreferanceModel>()
    var dataDepartureTime = ArrayList<AccomodationPreferanceModel>()
    var dataArrivalTime = ArrayList<AccomodationPreferanceModel>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()
    var dataArrival    = ArrayList<FilterFlightModel>()
    var namesAirlines  = ArrayList<String>()
    val totalFlight = "0"
    val adapterCabinClass by lazy { FilterFlightCabinAdapter(this,dataCabin) }
    val adapterDeparture by lazy { FilterFlightAdapter(this,dataDeparture) }
    val adapterArrival by lazy { FilterFlightAdapter(this,dataArrival) }

    override fun OnMain() {
        initToolbar()
        initRangeBar()
        initButtonTransit()
        initRecyclerView()
        addDataDummyTime()
        initButtonNext()
    }

    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("See ${totalFlight} Flight(s)")
    }

    private fun addDataDummyTime() {
        dataCabin = DataDummyAccomodation().addCabinClass()
        dataDeparture = DataDummyAccomodation().addDataDepartureTime()
        dataArrival   = DataDummyAccomodation().addDataDepartureTime()

        adapterCabinClass.setData(dataCabin)
        adapterDeparture.setData(dataDeparture)
        adapterArrival.setData(dataArrival)
    }

    private fun initRecyclerView() {
        val layoutManagerCabin = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        rvCabinClass.layoutManager = layoutManagerCabin
        rvCabinClass.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvCabinClass.adapter = adapterCabinClass

        adapterCabinClass.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        dataNameCabin[position].checked = !dataNameCabin[position].checked
                        adapterCabinClass.notifyItemChanged(position)
                        Constants.dataClassFlight = dataNameCabin
                    }
                }
            }
        })

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_departure_time.layoutManager = layoutManager
        rv_departure_time.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_departure_time.adapter = adapterDeparture

        adapterDeparture.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        dataDepartureTime[position].checked = !dataDepartureTime[position].checked
                        adapterDeparture.notifyItemChanged(position)
                        Constants.dataDepartureTime = dataDepartureTime
                    }
                }
            }
        })

        val layoutManagerArrival = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManagerArrival.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_arrival_time.layoutManager = layoutManagerArrival
        rv_arrival_time.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_arrival_time.adapter = adapterArrival

        adapterArrival.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 ->{
                        dataArrivalTime[position].checked = !dataArrivalTime[position].checked
                        adapterArrival.notifyItemChanged(position)
                        Constants.dataArrivalTime = dataDepartureTime
                    }
                }
            }
        })
    }

    private fun initButtonTransit() {
        btn_transit.callbackOnclickFilter(this)
    }

    private fun initRangeBar() {
        rangebar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener{
            override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
                setLog("rangeBar p1 = ${p1}  p1 = ${p2}" )
                tvRangeBar.text = "${p1} - ${p2}"
            }

            override fun onStartTrackingTouch(p0: RangeSeekBar?) {

            }

            override fun onStopTrackingTouch(p0: RangeSeekBar?) {
                setLog("rangeBar strat = ${p0?.progressStart}  end = ${p0?.progressEnd}" )
                tvRangeBar.text = "${p0?.progressStart} - ${p0?.progressEnd}"
            }
        })
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Filter")
        toolbar.showBtnReset()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onClicked() {
        finish()
    }

    override fun onDirect() {
    }

    override fun onOneTransit() {
    }

    override fun onTwoTransit() {
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    fun airlinesPreferance(view: View){
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(supportFragmentManager,"dialog")

        selectAccomodationPreferance.setCallbackListener(object : SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String) {
                tv_airline_prreferance.text = string
            }
        })

//
//        val selectReason = SelectReasonAccomodation(true,R.style.CustomDialog,dataPrefarance)
//        selectReason.show(,"dialog")

    }

    private fun addDataAirPreferance() {
        namesAirlines.forEachIndexed { index, names ->
            val mData = AccomodationPreferanceModel()
            mData.id = "${index+1}"
            mData.checked = false
            mData.name = names
            dataPrefarance.add(mData)
        }
    }

    private fun addNamesAirline() {
        namesAirlines.clear()
        namesAirlines.add("Select All")
        namesAirlines.add("Air France")
        namesAirlines.add("British Airways")
        namesAirlines.add("Cathay Pacific")
        namesAirlines.add("China Airlines")
        namesAirlines.add("Emirates")
        namesAirlines.add("Etihad")
        namesAirlines.add("Garuda Indonesia")
        namesAirlines.add("KLM")
        namesAirlines.add("Malaysia Airlines")
        namesAirlines.add("Qatar Airways")
        namesAirlines.add("Singapore Airlines")
        namesAirlines.add("Turkey Airlines")
    }
}