package com.opsicorp.travelaja.feature_flight.ssr

import android.os.Build
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.frequent_flyer.*
import kotlinx.android.synthetic.main.frequent_flyer.toolbar
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class FrequentFlyerActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener, ButtonDefaultOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.frequent_flyer
    }

    override fun OnMain() {
        btnApply.callbackOnclickButton(this)
        btnApply.setTextButton("Apply")
        initToolbar()
        setData()
    }

    private fun setData() {
        val dataProfile = Globals.getProfile(applicationContext)
        tvPassengerFf.text = dataProfile.name
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Frequent Flyer")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onClicked() {

    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
    }
}