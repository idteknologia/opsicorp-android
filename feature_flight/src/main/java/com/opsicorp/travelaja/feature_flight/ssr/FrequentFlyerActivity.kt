package com.opsicorp.travelaja.feature_flight.ssr

import android.os.Build
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.frequent_flyer.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class FrequentFlyerActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.frequent_flyer
    }

    override fun OnMain() {
        btnApply.callbackOnclickButton(this)
        initToolbar()
        setData()
    }

    private fun setData() {
        val datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataProfile = Globals.getProfile(applicationContext)
        tvPassengerFf.text = dataProfile.name
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Frequent Flyer")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onClicked() {

    }
}