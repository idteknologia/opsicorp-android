package com.opsicorp.travelaja.feature_flight.multi_city

import android.os.Build
import android.content.Intent
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.gone
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import com.opsicorp.travelaja.feature_flight.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.detail_price_bottom_new.*
import kotlinx.android.synthetic.main.multi_city_list_activity.*
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataMultiTripOrder
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import com.opsicorp.travelaja.feature_flight.result.ResultSearchFlightActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation

class FlightMultiCityListActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView {

    override fun getLayout(): Int {
        return R.layout.multi_city_list_activity
    }

    val adapter          = FlightMultiCityListAdapter(this@FlightMultiCityListActivity)
    var dataOrder        = DataMultiTripOrder()
    val dataFLigt        = DataListOrderAccomodation()
    var isFlightEmpty    = true

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        initData()
    }

    private fun initData() {
        dataOrder = Constants.DATA_FLIGHT_MULTI_CITY
        for (i in 0 until dataOrder.dataListOrderAccomodation.size){
            setLog(Serializer.serialize(dataOrder.dataListOrderAccomodation[i]))
            val data = ResultListFlightModel()
            data.departDate      = dataOrder.dataListOrderAccomodation[i].dateDeparture
            data.origin          = dataOrder.dataListOrderAccomodation[i].idOrigin
            data.destination     = dataOrder.dataListOrderAccomodation[i].idDestination
            data.originName      = dataOrder.dataListOrderAccomodation[i].originName
            data.destinationName = dataOrder.dataListOrderAccomodation[i].destinationName
            dataFLigt.dataFlight.add(data)
        }
        adapter.setData(dataFLigt.dataFlight)
        changeButtonBookGrayColor()
    }

    private fun initRecyclerView() {
        rv_confirmation_order_flight.apply {
            val lm          = LinearLayoutManager(this@FlightMultiCityListActivity)
            lm.orientation  = LinearLayoutManager.VERTICAL
            layoutManager   = lm
            itemAnimator    = DefaultItemAnimator()
            adapter         = this@FlightMultiCityListActivity.adapter
        }
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.setTitleBar("Multi City")
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        btn_next.callbackOnclickButton(this)
        tv_price.setText("IDR 0")
        line_shadow.gone()
    }

    private fun changeButtonBookGrayColor() {
        btn_next.changeTextColorButton(R.color.colorPureBlack)
        btn_next.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_dark_select_budget)
    }

    private fun changeButtonBookOrangeColor() {
        btn_next.changeTextColorButton(R.color.colorPureBlack)
        btn_next.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_yellow)
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onClicked() {

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
        when(views){
            Constants.SELECT_FLIGHT -> {
                Constants.multitrip = true
                gotoActivityResult(ResultSearchFlightActivity::class.java,Constants.REQUEST_CODE_SELECT_FLIGHT)
            }
        }
    }
}