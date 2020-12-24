package com.opsicorp.travelaja.feature_flight.flight_info.activity

import android.os.Build
import com.opsicorp.travelaja.feature_flight.flight_info.infofragment.FareRulesFragment
import com.opsicorp.travelaja.feature_flight.flight_info.infofragment.FlightInfoFacility
import com.opsicorp.travelaja.feature_flight.flight_info.infofragment.FlightInfoFragment
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.tablayout.TabLayoutOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.flight_info_activity.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FlightInfoActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener
        , TabLayoutOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.flight_info_activity
    }

    var data = ArrayList<String>()

    override fun OnMain() {
        if(Globals.DATA_FLIGHT.isNotEmpty()) {
            val data = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
            val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            toolbar.setDoubleTitle(data.titleAirline,dataOrder.idOrigin + " - " + dataOrder.idDestination + ", " + data.durationView)
            toolbar.hidenBtnCart()
            toolbar.changeImageBtnBack(R.drawable.ic_close)
            toolbar.callbackOnclickToolbar(this)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toolbar.doubleTitleGravity(toolbar.START)
            }
        }

        addDataTabLayout()
        tablayout.buildTabLayout(data)
        tablayout.callbackOnclickButton(this)
        loadInfoFragment()
    }

    private fun loadInfoFragment() {
        val flightInfo = FlightInfoFragment()
        loadFragment(flightInfo,R.id.place_fragment)
    }

    private fun addDataTabLayout() {
        data.add("Flight Info")
        data.add("Facilities")
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun onBackPressed() {
        finish()
    }

    override fun btnCard() {
    }

    override fun onClicked(positionTab: Int, name: String) {
        when(positionTab){
            0 -> {
                loadInfoFragment()
            }
            1 -> {
                loadFacilityFragment()
            }
        }
    }

    private fun loadFacilityFragment() {
        val flightFacility = FlightInfoFacility()
        loadFragment(flightFacility,R.id.place_fragment)
    }

    private fun loadFareRules() {
        val flightFareRules = FareRulesFragment()
        loadFragment(flightFareRules,R.id.place_fragment)
    }
}