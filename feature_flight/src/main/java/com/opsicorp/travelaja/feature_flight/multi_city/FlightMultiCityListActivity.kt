package com.opsicorp.travelaja.feature_flight.multi_city

import android.content.Intent
import android.os.Build
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.detail_prize_bottom_new.*
import kotlinx.android.synthetic.main.multi_city_list_activity.*

class FlightMultiCityListActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView {

    override fun getLayout(): Int {
        return R.layout.multi_city_list_activity
    }

    var isFlightEmpty = true

    override fun OnMain() {
        initToolbar()
        changeButtonBookGrayColor()
    }



    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.setTitleBar("Multi City")
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        btn_next.setTextButton("Book")
        btn_next.callbackOnclickButton(this)
        tv_price.setText("IDR 0")
    }

    private fun changeButtonBookGrayColor() {
        btn_next.changeTextColorButton(R.color.colorPureBlack)
        btn_next.changeBackgroundDrawable(com.opsigo.travelaja.R.drawable.rounded_button_dark_select_budget)

    }

    private fun changeButtonBookOrangeColor() {
        btn_next.changeTextColorButton(R.color.colorPureBlack)
        btn_next.changeBackgroundDrawable(com.opsigo.travelaja.R.drawable.rounded_button_yellow)
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onClicked() {
        checkEmptyFlight()
    }

    private fun checkEmptyFlight() {
        if (!isFlightEmpty) {
            changeButtonBookOrangeColor()
        } else {
            changeButtonBookGrayColor()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(views: Int, position: Int) {
    }
}