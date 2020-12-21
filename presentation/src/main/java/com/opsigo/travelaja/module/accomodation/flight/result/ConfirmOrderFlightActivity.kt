package com.opsigo.travelaja.module.accomodation.flight.result

import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_reason_trip.SelectReasonAccomodation
import com.opsigo.travelaja.module.accomodation.flight.adapter.ConfirmationFlightAdapter
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.accomodation.booking_contact.BookingContactFlight
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.domainlayer.model.accomodation.flight.ConfirmationFlightModel
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_station_departure
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_prize_departure
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_station_arrival
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_prize_arrival
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_price_total
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_title_prize
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_including
import kotlinx.android.synthetic.main.detail_prize_bottom.line_arrival
import kotlinx.android.synthetic.main.detail_prize_bottom.body_prize
import kotlinx.android.synthetic.main.detail_prize_bottom.btn_next
import kotlinx.android.synthetic.main.detail_prize_bottom.ic_image
import kotlinx.android.synthetic.main.detail_prize_bottom.tv_price
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import kotlinx.android.synthetic.main.confirm_flight_order.*
import kotlinx.android.synthetic.main.detail_prize_bottom.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import opsigo.com.datalayer.mapper.Serializer
import android.support.v4.app.FragmentManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import com.opsigo.travelaja.R
import android.view.View
import android.util.Log

//flight summary
class ConfirmOrderFlightActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener,
        View.OnClickListener{
    override fun getLayout(): Int { return R.layout.confirm_flight_order }

    val data = ArrayList<ConfirmationFlightModel>()
    lateinit var supportFragment: FragmentManager
    var allreadySelectReasonCode = false
    val adapter by lazy { ConfirmationFlightAdapter(this, data) }

    override fun OnMain() {
        btn_next.setTextButton("Book Now")
        btn_next.callbackOnclickButton(this)

        if(Globals.ONE_TRIP){
            tvoneway.text = "Oneway"
        }else{
            tvoneway.text = "Roundtrip"
        }
        initDataView()
        initPrize()
        initToolbar()

        ic_back.setOnClickListener {
            onBackPressed()
        }

        Globals.delay(100,object :Globals.DelayCallback{
            override fun done() {
                collapsePrize()
            }
        })
    }

    private fun initToolbar() {

    }

    private fun initDataView() {
        tv_price.text = "IDR 0"
        initRecyclerView()
        setData()
    }

    private fun initPrize() {
        Log.d("dtxxx","confirmorder xx : 01 " + Globals.typeAccomodation)

        tv_price.setOnClickListener {
            showOrHideDetailPrize()
        }
        ic_image.setOnClickListener {
            showOrHideDetailPrize()
        }
        tv_title_prize.setOnClickListener {
            showOrHideDetailPrize()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrize()
        }

        if (Globals.typeAccomodation==Constants.FLIGHT){
            val dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

            tv_prize_departure.text   = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
            tv_station_departure.visibility = View.INVISIBLE

            if (dataListFlight.dataFlight.size>1){
                line_arrival.visibility     = View.VISIBLE
                tv_prize_arrival.text       =  StringUtils().setCurrency("IDR", dataListFlight.dataFlight[1].price , false)
                tv_station_arrival.visibility = View.INVISIBLE
                tv_price_total.text         = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) , false)
                tv_price.text               = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) , false)
            }
            else{
                line_arrival.visibility     = View.GONE
                tv_price_total.text         = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
                tv_price.text               = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
            }
        }

    }

    fun showOrHideDetailPrize(){
        if (body_prize.isExpanded){
            collapsePrize()
        }
        else{
            expandPrize()
        }
    }
    private fun expandPrize() {
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down))
        line_shadow.visibility  = View.VISIBLE
        tv_including.visibility = View.GONE
        body_prize.expand()
    }
    private fun collapsePrize() {
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_orange))
        tv_including.visibility = View.VISIBLE
        line_shadow.visibility  = View.GONE
        body_prize.collapse()
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_confirmation_order_flight.layoutManager = layoutManager
        rv_confirmation_order_flight.itemAnimator = DefaultItemAnimator()
        rv_confirmation_order_flight.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    private fun setData() {
        val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

        val dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataList = ArrayList<ConfirmationFlightModel>()
        dataList.clear()

        dataListFlight.dataFlight.forEachIndexed { index, resultListFlightModel ->
            val mData = ConfirmationFlightModel()
            mData.title_flight  = resultListFlightModel.titleAirline
            mData.img_airline   = resultListFlightModel.imgAirline
            mData.class_type    = resultListFlightModel.flightNumber
            mData.number_sheet  = resultListFlightModel.numberSeat
            mData.terminal      = resultListFlightModel.terminal

            mData.date_arrival_deaprture = DateConverter().getDate(resultListFlightModel.departDate,"yyyy-MM-dd","EEEE, yyyy MMM dd")
            mData.timeDeparture = resultListFlightModel.departTime
            mData.dateDeparture = DateConverter().getDate(resultListFlightModel.departDate,"yyyy-MM-dd","dd MMM")

            mData.originDisplay    = resultListFlightModel.originName+" ("+resultListFlightModel.origin+")"
            mData.departureDisplay = resultListFlightModel.destinationName+" ("+resultListFlightModel.destination+")"

            /*when (index){
                0 ->{
                    mData.originDisplay    = resultListFlightModel.originName+" ("+resultListFlightModel.origin+")"
                    mData.departureDisplay = resultListFlightModel.destinationName+" ("+resultListFlightModel.destination+")"
                }
                1->{
                    mData.originDisplay    = resultListFlightModel.destinationName+" ("+resultListFlightModel.destination+")"
                    mData.departureDisplay = resultListFlightModel.originName+" ("+resultListFlightModel.origin+")"
                }
            }*/

            mData.name_stationDeparture = resultListFlightModel.titleAirline
            mData.line_total_duration   = resultListFlightModel.duration

            mData.time_arrival  = resultListFlightModel.arriveTime
            mData.date_arrival  = DateConverter().getDate(resultListFlightModel.arrivalDate,"yyyy-MM-dd","dd MMM")

            mData.total_passager = dataOrder.totalPassagerString
            mData.total_prize    = StringUtils().setCurrency("IDR",  resultListFlightModel.price, false)

            dataList.add(mData)
        }

        adapter.setData(dataList)
        showOrHideNotComply()
    }

    private fun showOrHideNotComply() {
        if (data.filter { it.notcomply }.isNotEmpty()){
            line_reason_code.visibility = View.VISIBLE
            line_reason_code.setOnClickListener(this)
        }
        else {
            line_reason_code.visibility = View.GONE
        }
    }

    override fun onClicked() {
        if (data.filter { it.notcomply }.isNotEmpty()){
            if (allreadySelectReasonCode){
                gotoActivity(BookingContactFlight::class.java)
            }
            else {
                showAllert("Sorry","Please Select ReasonCode")
            }
        }
        else{
            gotoActivity(BookingContactFlight::class.java)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Globals.DATA_LIST_FLIGHT = ""
    }

    private fun selectReasonCode() {
        val selectAccomodation = SelectReasonAccomodation(true,R.style.CustomDialog, Constants.DATA_REASON_CODE_FLIGHT)
        selectAccomodation.show(supportFragment,"dialog")

        selectAccomodation.setCallbackListener(object :SelectReasonAccomodation.CallbackSelectPreferance{
            override fun callback(model: ReasonCodeModel) {
                allreadySelectReasonCode = true
                val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
                dataOrder.reaseonCode = model.codeBrief+"-"+model.description
                Globals.DATA_ORDER_FLIGHT = Serializer.serialize(dataOrder)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0){
            line_reason_code ->{
                selectReasonCode()
            }
        }
    }
}