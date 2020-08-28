package com.opsigo.travelaja.module.accomodation.flight.filter

import com.opsigo.travelaja.module.item_custom.btn_filter.FilterTransitOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.filter_flight_activity.*
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import org.koin.core.parameter.parametersOf
import com.opsigo.travelaja.BaseActivity
import org.koin.core.KoinComponent
import com.opsigo.travelaja.R
import org.koin.core.inject
import android.os.Build
import android.view.View
import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.AccomodationPreferanceModel
import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.SelectAccomodationPreferance
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel
import kotlinx.android.synthetic.main.filter_flight_activity.btn_next
import kotlinx.android.synthetic.main.flight_fragment.*

class FilterFlightActivity : BaseActivity(),ButtonDefaultOpsicorp.OnclickButtonListener,FilterTransitOpsicorp.OnclickFilterListener,KoinComponent,ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.filter_flight_activity }

    var dataDeparture  = ArrayList<FilterFlightModel>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()
    var dataArrival    = ArrayList<FilterFlightModel>()
    var namesAirlines  = ArrayList<String>()
    val adapterDeparture by inject<FilterFlightAdapter> { parametersOf(dataDeparture) }
    val adapterArrival by inject<FilterFlightAdapter> { parametersOf(dataArrival) }
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
        btn_next.setTextButton("Filter")
    }

    private fun initButtonTransit() {
        btn_transit.callbackOnclickFilter(this)
    }

    private fun addDataDummyTime() {
        dataDeparture = DataDummyAccomodation().addDataDepartureTime()
        dataArrival   = DataDummyAccomodation().addDataDepartureTime()

        adapterDeparture.setData(dataDeparture)
        adapterArrival.setData(dataArrival)
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_departure_time.layoutManager = layoutManager
        rv_departure_time.itemAnimator = DefaultItemAnimator()
        rv_departure_time.adapter = adapterDeparture

        adapterDeparture.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {

                    }
                }
            }
        })

        val layoutManagerArrival = LinearLayoutManager(this)
        layoutManagerArrival.orientation = LinearLayoutManager.VERTICAL
        rv_arrival_time.layoutManager = layoutManagerArrival
        rv_arrival_time.itemAnimator = DefaultItemAnimator()
        rv_arrival_time.adapter = adapterArrival

        adapterArrival.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 ->{

                    }
                }
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

    private fun initRangeBar() {
        rangebar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener{
            override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
                setLog("rangeBar p1 = ${p1}  p1 = ${p2}" )
            }

            override fun onStartTrackingTouch(p0: RangeSeekBar?) {

            }

            override fun onStopTrackingTouch(p0: RangeSeekBar?) {
                setLog("rangeBar strat = ${p0?.progressStart}  end = ${p0?.progressEnd}" )
            }
        })
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onDirect() {

    }

    override fun onOneTransit() {

    }

    override fun onTwoTransit() {

    }

    fun airlinesPreferance(view:View){
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(supportFragmentManager,"dialog")

        selectAccomodationPreferance.setCallbackListener(object :SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String) {
                tv_airline_prreferance.text = string
            }
        })

//
//        val selectReason = SelectReasonAccomodation(true,R.style.CustomDialog,dataPrefarance)
//        selectReason.show(,"dialog")

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

    private fun addDataAirPreferance() {

        namesAirlines.forEachIndexed { index, names ->
            val mData = AccomodationPreferanceModel()
            mData.id = "${index+1}"
            mData.checked = false
            mData.name = names
            dataPrefarance.add(mData)
        }

    }

}