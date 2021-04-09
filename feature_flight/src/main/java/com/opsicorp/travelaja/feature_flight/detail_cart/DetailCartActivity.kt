package com.opsicorp.travelaja.feature_flight.detail_cart

import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.cart.model.CartModel
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.detail_cart_activity.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.ConfirmationFlightModel

class DetailCartActivity : BaseActivity() {

    val data = ArrayList<ConfirmationFlightModel>()
    val adapter by lazy { DetailCartAdapter(this, data) }
    lateinit var dataOrder: CartModel

    override fun getLayout(): Int {
        return R.layout.detail_cart_activity
    }

    override fun OnMain() {
        dataOrder = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_FLIGHT), CartModel::class.java)
        setDataDetail()
        initToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_item_flight.layoutManager = layoutManager
        rv_item_flight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_item_flight.adapter = adapter
    }

    private fun setDataDetail() {
        data.clear()
        val dataFlight                = dataOrder.dataCardFlight

        val mData                    = ConfirmationFlightModel()
        mData.pnr_code               = dataFlight.pnrCode
        mData.status                 = dataFlight.status
        mData.title_flight            = dataFlight.titleFlight
        mData.class_type             = dataFlight.classFlight
        mData.airlineNumber          = dataFlight.flightNumber
        mData.img_airline            = dataFlight.imageFlight
        mData.number_sheet           = dataFlight.numberSheet

        mData.timeDeparture          = dataFlight.timeDeparture
        mData.dateDeparture          = dataFlight.dateDeparture
        mData.line_total_duration    = dataFlight.duration

        mData.time_arrival           = dataFlight.timeArrival
        mData.date_arrival           = dataFlight.dateArrival

        mData.depatureAirportName         = dataFlight.airportDeparture
        mData.arrivalAirportName           = dataFlight.airportArrival

        mData.notcomply              = false
        mData.name_stationDeparture = dataFlight.stationOrigin
        mData.name_stationArrival   = dataFlight.stationDestination

        mData.totalPassenger         = if ("".isEmpty()) "Adult x 1" else ""
        mData.totalPrice            = dataFlight.price

        data.add(mData)

        if (mData.notcomply){
            line_reason_code.visibility = View.VISIBLE
            tv_reason_code.text = mData.dataReasonCode.codeBrief
            tv_reason_code_desc.text = mData.dataReasonCode.description
        }
        else {
            line_reason_code.visibility = View.GONE
        }

        adapter.setData(data)
        setTotalPrice(mData)
    }

    private fun setTotalPrice(mData: ConfirmationFlightModel) {
        name_flight.text = mData.title_flight
        tv_total_price_flight1.text  = "IDR ${Globals.formatAmount(mData.totalPrice.split(".")[0])}"
        tv_total_price_service_fee.text = "IDR 0"
        tv_total_price_tax.text    = "IDR 0"
        tv_total_amount.text        = "IDR ${Globals.formatAmount(mData.totalPrice.split(".")[0])}"
    }


    private fun initToolbar() {
        if (dataOrder.dataCardFlight.typeFlight==0){
            tripType.text = "Oneway"
        } else {
            tripType.text = "Roundtrip"
        }
        ic_close.setOnClickListener {
            onBackPressed()
        }
    }

}