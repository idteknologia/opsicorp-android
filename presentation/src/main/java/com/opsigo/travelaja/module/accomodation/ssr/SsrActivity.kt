package com.opsigo.travelaja.module.accomodation.ssr

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.ssr_flight_activity.*
import kotlinx.android.synthetic.main.ssr_flight_activity.body_price
import kotlinx.android.synthetic.main.ssr_flight_activity.btnDone
import kotlinx.android.synthetic.main.ssr_flight_activity.line_arrival
import kotlinx.android.synthetic.main.ssr_flight_activity.line_shadow
import kotlinx.android.synthetic.main.ssr_flight_activity.toolbar
import kotlinx.android.synthetic.main.ssr_flight_activity.tv_price_departure
import kotlinx.android.synthetic.main.ssr_flight_activity.tv_station_arrival
import kotlinx.android.synthetic.main.ssr_flight_activity.tv_station_departure
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer
import java.lang.Exception

class SsrActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerViewParent {

    val adapter by lazy { SsrAdapter(this) }
    lateinit var datalist: DataListOrderAccomodation

    override fun OnMain() {
        btnDone.callbackOnclickButton(this)
        initToolbar()
        initRecyclerView()
        setData()
    }

    private fun initPrice() {
        tvTitleSsr.setOnClickListener {
            showOrHideDetailPrice()
        }
        tvTotalPriceSsr.setOnClickListener {
            showOrHideDetailPrice()
        }
        icImageSsr.setOnClickListener {
            showOrHideDetailPrice()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrice()
        }
        if (Globals.typeAccomodation == Constants.FLIGHT) {
            tvTotalPriceSsr.text = "IDR ${Globals.currencyIDRFormat(totalPriceSelected()!!.replace(".0", "").toDouble())}"
            tv_station_departure.text = "${datalist.dataFlight[0].origin} - ${datalist.dataFlight[0].destination}"
            if (datalist.dataFlight[0].dataSSR.ssrSelected.isNotEmpty()){
                tvSsrValue.text = "${datalist.dataFlight[0].dataSSR.ssrSelected[0].ssrName}"
                tv_price_departure.text = "${datalist.dataFlight[0].dataSSR.ssrSelected[0].price}"
            } else {
                tvSsrValue.text = "-"
                tv_price_departure.text = "IDR 0"
            }
            if (datalist.dataFlight.size >= 2) {
                line_transit.visible()
                tv_station_transit.text = "${datalist.dataFlight[1].origin} - ${datalist.dataFlight[1].destination}"
                tv_price_departure2.text = "IDR 0"
                if (datalist.dataFlight.size >= 3) {
                    line_arrival.visible()
                    tv_station_arrival.text = "${datalist.dataFlight[2].origin} - ${datalist.dataFlight[2].destination}"
                    tv_price_departure3.text = "IDR 0"
                } else {
                    line_arrival.gone()
                }
            } else {
                line_transit.gone()
                line_arrival.gone()
            }
        }

    }

    private fun totalPriceSelected(): String? {
        var totalSelected = 0.0
        datalist.dataFlight.forEach {
            it.dataSSR.ssrSelected.forEach {
                try {
                    totalSelected = totalSelected + it.price.toDouble()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return totalSelected.toString()
    }

    private fun showOrHideDetailPrice() {
        if (body_price.isExpanded) {
            collapsePrice()
        } else {
            expandPrice()
        }
    }

    private fun expandPrice() {
        icImageSsr.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down))
        line_shadow.visible()
        body_price.expand()
    }

    private fun collapsePrice() {
        icImageSsr.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_orange))
        line_shadow.gone()
        body_price.collapse()
    }

    private fun setData() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataProfile = Globals.getProfile(applicationContext)
        tvPassengerSsr.text = dataProfile.name
        adapter.setData(datalist.dataFlight)
        initPrice()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            Constants.REQUEST_CODE_SELECT_SSR -> {
                if (resultCode == Activity.RESULT_OK){
                    setData()
                    val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                    setLog("testSaveKembali",dataList.dataFlight[0].dataSSR.ssrSelected.size.toString())
                }
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvSsr.layoutManager = layoutManager
        rvSsr.itemAnimator = DefaultItemAnimator()
        rvSsr.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR(Special Service Request)")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        tvTotalPriceSsr.text = "IDR 0"
    }


    override fun getLayout(): Int {
        return R.layout.ssr_flight_activity
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

    }

    override fun onClicked() {
        finish()
    }
}