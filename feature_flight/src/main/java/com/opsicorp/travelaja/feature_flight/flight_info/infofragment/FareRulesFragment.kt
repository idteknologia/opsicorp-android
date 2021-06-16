package com.opsicorp.travelaja.feature_flight.flight_info.infofragment

import android.os.Bundle
import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.base.BaseFragment

class FareRulesFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.rules_fare_rules_new
    }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {

    }
    /*override fun getLayout(): Int {
        return R.layout.rules_fare_rules_new
    }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        toolbar.setTitleBar("Fare Rules")
        toolbar.hidenBtnCart()
        toolbar.changeImageBtnBack(com.opsigo.travelaja.R.drawable.ic_close_dark_gray)
        toolbar.btn_back.setOnClickListener { dismiss() }
    }*/


}