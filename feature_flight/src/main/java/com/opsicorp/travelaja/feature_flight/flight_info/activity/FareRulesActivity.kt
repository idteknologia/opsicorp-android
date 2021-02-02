package com.opsicorp.travelaja.feature_flight.flight_info.activity

import android.os.Build
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.rules_fare_rules_new.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FareRulesActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.rules_fare_rules_new
    }

    override fun OnMain() {
        if(Globals.DATA_FLIGHT.isNotEmpty()) {
            val data = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
            val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            toolbar.setDoubleTitle(data.titleAirline,dataOrder.idOrigin + " - " + dataOrder.idDestination + ", " + data.durationView)
            toolbar.hidenBtnCart()
            toolbar.changeImageBtnBack(com.opsigo.travelaja.R.drawable.ic_close)
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