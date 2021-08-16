package com.mobile.travelaja.module.manage_trip

import android.view.View
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityTripHistoryBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_trip_history.*

class TripHistoryActivity : BaseActivityBinding<ActivityTripHistoryBinding>(),
        View.OnClickListener,ToolbarOpsicorp.OnclickButtonListener{


    override fun bindLayout(): ActivityTripHistoryBinding {
        return ActivityTripHistoryBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Trip History")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.singgleTitleGravity(toolbar.START)
    }

    override fun onClick(v: View?) {
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }
}