package com.opsicorp.travelaja.feature_flight.ssr

import androidx.core.content.ContextCompat
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_bagage.*
import kotlinx.android.synthetic.main.activity_bagage.btnDone
import kotlinx.android.synthetic.main.activity_bagage.toolbar
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
    var currentPositionPassenger = 0


    override fun OnMain() {
        try {
            currentPositionPassenger = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.KEY_POSITION_SELECT_PASSENGER,0)!!
        }
        catch (e:Exception){ }
        btnDone.callbackOnclickButton(this)
        btnDone.setTextButton("Done")
        initToolbar()
        initRecyclerView()
        initPrice()
        initPriceRv()
        setData()
    }

    private fun initPriceRv() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rvPriceBagagge.layoutManager = layoutManager
        rvPriceBagagge.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
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
        icImageBagagge.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_down))
        line_shadow.visible()
        body_price.expand()
    }

    private fun collapsePrice() {
        icImageBagagge.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_up_orange))
        line_shadow.gone()
        body_price.collapse()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Baggage")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.singgleTitleGravity(toolbar.START)
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_baggage.layoutManager = layoutManager
        rv_baggage.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_baggage.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun setData() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        adapter.setData(datalist.dataFlight,currentPositionPassenger)
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {
        datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.dataBagage[positionChild]
        tvTotalPriceBaggage.text = "IDR ${Globals.currencyIDRFormat(totalPriceSelected()!!.replace(".0", "").toDouble())}"

        val selectedItem = SelectedBaggageModel()
        selectedItem.price = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.pricing
        selectedItem.ssrCode = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.ssrCode
        selectedItem.curency = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.curency
        selectedItem.ssrName = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.ssrName
        selectedItem.ssrType = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.ssrType
        selectedItem.ssrTypeName = datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeSelected.ssrTypeName
        selectedItem.flightTitle = "${datalist.dataFlight[positionParent].origin}-${datalist.dataFlight[positionParent].destination}"
        if (!baggageSelected.filter { it.ssrType  == selectedItem.ssrType}.isNullOrEmpty()){
            baggageSelected.removeAt(baggageSelected.indexOf(baggageSelected.filter { it.ssrType  == selectedItem.ssrType }.last()))
            baggageSelected.add(selectedItem)
        } else {
            baggageSelected.add(selectedItem)
        }

        datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeItemSelected.clear()
        datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeItemSelected.addAll(baggageSelected)
        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)

        adapter2.setData(datalist.dataFlight[positionParent].passenger[currentPositionPassenger].ssr.bagaggeItemSelected)

    }



    private fun totalPriceSelected(): String? {
        var totalSelected = 0.0
        datalist.dataFlight.forEach {
            try {
                totalSelected = totalSelected + it.passenger[currentPositionPassenger].ssr.bagaggeSelected.pricing.toDouble()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return totalSelected.toString()
    }

    override fun onClicked() {
        Globals.finishResultOk(this)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }
}
