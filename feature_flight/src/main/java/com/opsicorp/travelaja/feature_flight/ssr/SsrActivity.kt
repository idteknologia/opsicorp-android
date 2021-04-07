package com.opsicorp.travelaja.feature_flight.ssr

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.ssr_flight_activity.*
import kotlinx.android.synthetic.main.ssr_flight_activity.body_price
import kotlinx.android.synthetic.main.ssr_flight_activity.btnDone
import kotlinx.android.synthetic.main.ssr_flight_activity.line_shadow
import kotlinx.android.synthetic.main.ssr_flight_activity.toolbar
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer
import java.lang.Exception

class SsrActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener, ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView {

    val adapter by lazy { SsrAdapter(this) }
    val adapter2 by lazy { SsrPriceAdapter(this) }
    lateinit var datalist: DataListOrderAccomodation

    override fun OnMain() {
        btnDone.callbackOnclickButton(this)
        btnDone.setTextButton("Done")
        initToolbar()
        initRecyclerView()
        initRecyclerViewPrice()
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
        }

    }

    private fun initRecyclerViewPrice() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rvPriceSsr.layoutManager = layoutManager
        rvPriceSsr.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvPriceSsr.adapter = adapter2

    }

    private fun totalPriceSelected(): String? {
        var totalSelected = 0.0
        datalist.dataFlight.forEach {
            it.dataSSR.ssrSelected.forEach {
                try {
                    totalSelected = totalSelected + it.price.toDouble()
                } catch (e: Exception) {
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
        icImageSsr.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_down))
        line_shadow.visible()
        body_price.expand()
    }

    private fun collapsePrice() {
        icImageSsr.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_up_orange))
        line_shadow.gone()
        body_price.collapse()
    }

    private fun setData() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataProfile = Globals.getProfile(applicationContext)
        tvPassengerSsr.text = "${dataProfile.title}.${dataProfile.name}"
        adapter.setData(datalist.dataFlight)
        adapter2.setData(datalist.dataFlight)
        initPrice()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.REQUEST_CODE_SELECT_SSR -> {
                if (resultCode == Activity.RESULT_OK) {
                    setData()
                    val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                    setLog("testSaveKembali", dataList.dataFlight[0].dataSSR.ssrSelected.size.toString())
                }
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rvSsr.layoutManager = layoutManager
        rvSsr.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvSsr.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR (Special Service Request)")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        tvTotalPriceSsr.text = "IDR 0"
    }


    override fun getLayout(): Int {
        return R.layout.ssr_flight_activity
    }

    override fun onClicked() {
        finish()
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onClick(views: Int, position: Int) {
        when (views){
            Constants.REQUEST_CODE_SELECT_SSR -> {
                val intent = Intent(this, SsrListActivity::class.java)
                intent.putExtra(Constants.KEY_POSITION_SELECT_SSR, position)
                gotoActivityResultIntent(intent,Constants.REQUEST_CODE_SELECT_SSR)
            }
            Constants.REQUEST_CODE_DELETE_SSR -> {
                datalist.dataFlight[position].dataSSR.ssrSelected.clear()
                Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)
                adapter.setData(datalist.dataFlight)
                adapter2.setData(datalist.dataFlight)
                initPrice()
            }
        }
    }
}