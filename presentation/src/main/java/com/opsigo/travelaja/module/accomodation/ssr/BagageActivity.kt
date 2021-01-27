package com.opsigo.travelaja.module.accomodation.ssr

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_bagage.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.DataSsr
import java.lang.Exception
import java.util.ArrayList

class BagageActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerViewParent {

    override fun getLayout(): Int {
        return R.layout.activity_bagage
    }

    val adapter by lazy { BaggageAdapter(this) }
    lateinit var datalist: DataListOrderAccomodation


    override fun OnMain() {
        btnDone.callbackOnclickButton(this)
        initToolbar()
        initRecyclerView()
        initPrice()
        setData()
    }

    private fun initPrice() {
        tvTitleBagagge.setOnClickListener {
            showOrHideDetailPrice()
        }
        tvTotalPriceBaggage.setOnClickListener {
            showOrHideDetailPrice()
        }
        icImageBagagge.setOnClickListener {
            showOrHideDetailPrice()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrice()
        }
        if (Globals.typeAccomodation== Constants.FLIGHT) {
            datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
            tv_station_departure.text = "${datalist.dataFlight[0].origin} - ${datalist.dataFlight[0].destination}"
            tv_price_departure.text = "IDR ${datalist.dataFlight[0].dataSSR.bagaggeSelected.pricing.replace(".0","").trim()}"
            if (datalist.dataFlight.size>1){
                line_arrival.visible()
                tv_station_arrival.text = "${datalist.dataFlight[1].origin} - ${datalist.dataFlight[1].destination}"
                tv_price_arrival.text = "IDR ${datalist.dataFlight[1].dataSSR.bagaggeSelected.pricing.replace(".0","").trim()}"
            } else {
                line_arrival.gone()
            }
        }
    }

    private fun showOrHideDetailPrice() {
        if (body_price.isExpanded){
            collapsePrice()
        } else {
            expandPrice()
        }
    }

    private fun expandPrice() {
        icImageBagagge.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down))
        line_shadow.visible()
        body_price.expand()
    }

    private fun collapsePrice() {
        icImageBagagge.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_orange))
        line_shadow.gone()
        body_price.collapse()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Baggage")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_baggage.layoutManager = layoutManager
        rv_baggage.itemAnimator = DefaultItemAnimator()
        rv_baggage.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun setData() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        /*datalist.dataFlight[0].dataSSR.dataBagage = DataDummyAccomodation().addBaggae()*/
        /*datalist.dataFlight[1].dataSSR.dataBagage = DataDummyAccomodation().addBaggae()*/
        adapter.setData(datalist.dataFlight)
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {
        setLog(datalist.dataFlight[positionParent].dataSSR.dataBagage[positionChild].pricing)
        datalist.dataFlight[positionParent].dataSSR.bagaggeSelected = datalist.dataFlight[positionParent].dataSSR.dataBagage[positionChild]
        tvTotalPriceBaggage.text = "IDR ${totalPriceSelected()!!.replace(".0","").trim()}"
    }

    private fun totalPriceSelected(): String? {
        var totalSelected = 0.0
        datalist.dataFlight.forEach {
            try {
                totalSelected = totalSelected + it.dataSSR.bagaggeSelected.pricing.toDouble()
            }
            catch (e:Exception){
               e.printStackTrace()
            }
        }
        return totalSelected.toString()
    }

    override fun onClicked() {
        finish()
    }
}
