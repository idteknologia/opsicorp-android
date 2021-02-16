package com.opsicorp.travelaja.feature_flight.flight_info.activity

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsicorp.travelaja.feature_flight.R
import com.opsicorp.travelaja.feature_flight.flight_info.adapter.FareRulesAdapter
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.rules_fare_rules_new.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FareRulesActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener {

   val adapter by lazy { FareRulesAdapter(this) }
    lateinit var datalist: DataListOrderAccomodation
    var positionFlight: Int = 0

    override fun getLayout(): Int {
        return R.layout.rules_fare_rules_new
    }

    override fun OnMain() {
        initToolbar()
        initRecycleView()
        setData()
    }

    private fun setData() {
        adapter.setData(datalist.dataFlight[positionFlight].dataFareRules)
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvFareRules.layoutManager = layoutManager
        rvFareRules.itemAnimator = DefaultItemAnimator()
        rvFareRules.adapter = adapter
    }

    private fun initToolbar() {
        if(Globals.DATA_FLIGHT.isNotEmpty()) {
            datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
            positionFlight = intent.getIntExtra(Constants.KEY_POSITION_FARE_RULES, 0)

            toolbar.setDoubleTitle(datalist.dataFlight[positionFlight].titleAirline,datalist.dataFlight[positionFlight].origin
                    + " - " + datalist.dataFlight[positionFlight].destination + ", " +
                    datalist.dataFlight[positionFlight].departureDate)
            /*val data = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
            val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            toolbar.setDoubleTitle(data.titleAirline,dataOrder.idOrigin + " - " + dataOrder.idDestination + ", " + data.durationView)*/
            toolbar.hidenBtnCart()
            toolbar.changeImageBtnBack(R.drawable.ic_close_white)
            toolbar.callbackOnclickToolbar(this)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toolbar.doubleTitleGravity(toolbar.START)
            }
        }
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
    }

    override fun onBackPressed() {
        onBackPressed()
    }
}