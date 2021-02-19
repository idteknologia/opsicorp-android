package com.opsicorp.travelaja.feature_flight.ssr

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_bagage.*
import kotlinx.android.synthetic.main.activity_bagage.btnDone
import kotlinx.android.synthetic.main.activity_bagage.toolbar
import kotlinx.android.synthetic.main.toolbar_view_new.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.SelectedBaggageModel
import java.lang.Exception

class BagageActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener, ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerViewParent {

    override fun getLayout(): Int {
        return R.layout.activity_bagage
    }

    val adapter by lazy { BaggageAdapter(this) }
    val adapter2 by lazy { BaggagePriceAdapter(this) }
    val baggageSelected = ArrayList<SelectedBaggageModel>()
    lateinit var datalist: DataListOrderAccomodation


    override fun OnMain() {
        btnDone.callbackOnclickButton(this)
        btnDone.setTextButton("Done")
        initToolbar()
        initRecyclerView()
        initPrice()
        initPriceRv()
        setData()
    }

    private fun initPriceRv() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvPriceBagagge.layoutManager = layoutManager
        rvPriceBagagge.itemAnimator = DefaultItemAnimator()
        rvPriceBagagge.adapter = adapter2
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
        if (Globals.typeAccomodation == Constants.FLIGHT) {
            datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

        }
    }

    private fun showOrHideDetailPrice() {
        if (body_price.isExpanded) {
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
        toolbar.callbackOnclickToolbar(this)
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
        adapter.setData(datalist.dataFlight)
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {
        /*setLog(datalist.dataFlight[positionParent].dataSSR.dataBagage[positionChild].pricing)*/
        datalist.dataFlight[positionParent].dataSSR.bagaggeSelected = datalist.dataFlight[positionParent].dataSSR.dataBagage[positionChild]
        tvTotalPriceBaggage.text = "IDR ${Globals.currencyIDRFormat(totalPriceSelected()!!.replace(".0", "").toDouble())}"

        val selectedItem = SelectedBaggageModel()
        selectedItem.price = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.pricing
        selectedItem.ssrCode = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.ssrCode
        selectedItem.curency = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.curency
        selectedItem.ssrName = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.ssrName
        selectedItem.ssrType = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.ssrType
        selectedItem.ssrTypeName = datalist.dataFlight[positionParent].dataSSR.bagaggeSelected.ssrTypeName
        selectedItem.flightTitle = "${datalist.dataFlight[positionParent].origin}-${datalist.dataFlight[positionParent].destination}"
        if (!baggageSelected.filter { it.ssrType  == selectedItem.ssrType}.isNullOrEmpty()){
            baggageSelected.removeAt(baggageSelected.indexOf(baggageSelected.filter { it.ssrType  == selectedItem.ssrType }.last()))
            baggageSelected.add(selectedItem)
        } else {
            baggageSelected.add(selectedItem)
        }

        datalist.dataFlight[positionParent].dataSSR.bagaggeItemSelected.clear()
        datalist.dataFlight[positionParent].dataSSR.bagaggeItemSelected.addAll(baggageSelected)
        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)

        adapter2.setData(datalist.dataFlight[positionParent].dataSSR.bagaggeItemSelected)

        Log.e("testSaveBaggage", baggageSelected[0].price)
    }



    private fun totalPriceSelected(): String? {
        var totalSelected = 0.0
        datalist.dataFlight.forEach {
            try {
                totalSelected = totalSelected + it.dataSSR.bagaggeSelected.pricing.toDouble()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return totalSelected.toString()
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
}
