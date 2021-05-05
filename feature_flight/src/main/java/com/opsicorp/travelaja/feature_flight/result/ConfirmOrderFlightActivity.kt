package com.opsicorp.travelaja.feature_flight.result

import android.util.Log
import android.view.View
import com.opsigo.travelaja.utility.*
import com.opsigo.travelaja.BaseActivity
import androidx.core.content.ContextCompat
import opsigo.com.datalayer.mapper.Serializer
import com.opsicorp.travelaja.feature_flight.R
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackSeatMapFlight
import kotlinx.android.synthetic.main.detail_price_bottom_new.*
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import kotlinx.android.synthetic.main.confirm_flight_order_new.*
import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import com.opsicorp.travelaja.feature_flight.adapter.ConfirmationFlightAdapter
import opsigo.com.domainlayer.model.accomodation.flight.ConfirmationFlightModel
import com.opsicorp.travelaja.feature_flight.booking_contact.BookingContactFlight
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.request_model.accomodation.flight.seat.SeatMapFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.SegmentFlightsRequest
import com.opsigo.travelaja.module.accomodation.dialog.accomodation_reason_trip.SelectReasonAccomodation

class ConfirmOrderFlightActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener,
        View.OnClickListener, OnclickListenerRecyclerView {
    override fun getLayout(): Int {
        return R.layout.confirm_flight_order_new
    }

    val data = ArrayList<ConfirmationFlightModel>()
    lateinit var supportFragment: androidx.fragment.app.FragmentManager
    lateinit var dataFlight: ResultListFlightModel
    lateinit var dataListFlight: DataListOrderAccomodation
    var allreadySelectReasonCode = false
    val adapter by lazy { ConfirmationFlightAdapter(this, data) }

    override fun OnMain() {
        btn_next.setTextButton("Book")
        btn_next.callbackOnclickButton(this)

        if(Globals.ONE_TRIP){
            tvoneway.text = "Oneway"
        }else{
            tvoneway.text = "Roundtrip"
        }
        getSeatMapFlight()
        initDataView()
        initPrice()
        initToolbar()

        ic_back.setOnClickListener {
            onBackPressed()
        }

        Globals.delay(100,object : Globals.DelayCallback{
            override fun done() {
                collapsePrice()
            }
        })
    }

    private fun getSeatMapFlight() {
        showDialog("Checking Available Seat")
        GetDataAccomodation(getBaseUrl()).getSeatMapFlight(getToken(),dataRequestSeatMap(),object : CallbackSeatMapFlight {
            override fun success(data: ArrayList<SeatAirlineModel>) {
                Constants.DATA_SEAT_AIRLINE.clear()
                Constants.DATA_SEAT_AIRLINE.addAll(data)
                /*Constants.DATA_SEAT_AIRLINE.forEachIndexed { index, seatAirlineModel ->
                    setLog(seatAirlineModel.nameFlight)
                    setLog(seatAirlineModel.nameAirCraft)
                    setLog(seatAirlineModel.totalRows.toString())
                    setLog(Serializer.serialize(seatAirlineModel.dataSeat))
                }*/
                hideDialog()
            }

            override fun failed(errorMessage: String) {
                hideDialog()
            }

        })
    }

    private fun dataRequestSeatMap(): HashMap<Any, Any> {
        dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
        val data = SeatMapFlightRequest()
        data.adult  = 1
        data.child = 0
        data.infant = 0
        data.travelAgent = "apidev"
        data.provider = dataFlight.airline
        data.segments = getSegmentSeat()

        return Globals.classToHashMap(data, SeatMapFlightRequest::class.java)
    }

    private fun getSegmentSeat(): List<SegmentFlightsRequest?>? {
        val listSegment = ArrayList<SegmentFlightsRequest>()

        dataListFlight.dataFlight.forEachIndexed { index, it ->
            val segment = SegmentFlightsRequest()

            segment.airline             =  it.airline
            segment.arriveDate          =  it.arriveDate
            segment.arriveTime          =  it.arriveTime

            segment.classCode           =  it.classCode
            segment.classId             =  it.classId

            segment.departDate          =  it.departDate
            segment.departTime          =  it.departTime

            segment.flightId        =  it.flightId
            segment.flightNumber    =  it.flightNumber

            segment.num         =  index.toString()
            segment.seq         =  index.toString()

            segment.origin      =  it.origin
            segment.destination =  it.destination

            listSegment.add(segment)
        }

        return listSegment
    }


    private fun initDataView() {
        tv_price.text = "IDR 0"
        initRecyclerView()
        setData()
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
            mData.class_type    = resultListFlightModel.nameClass
            mData.flight_type    = resultListFlightModel.flightType
            mData.number_sheet  = resultListFlightModel.numberSeat
            mData.airlineNumber = resultListFlightModel.flightNumber
            mData.terminal      = resultListFlightModel.terminal

            mData.date_arrival_departure = DateConverter().getDate(resultListFlightModel.departDate,"yyyy-MM-dd","EEE, dd MMM yyyy")
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
            mData.line_total_duration   = resultListFlightModel.durationView

            mData.depatureAirportName = resultListFlightModel.originAirport
            mData.arrivalAirportName = resultListFlightModel.destinationAirport


            mData.time_arrival  = resultListFlightModel.arriveTime
            mData.date_arrival  = DateConverter().getDate(resultListFlightModel.arrivalDate,"yyyy-MM-dd","dd MMM")

            mData.totalPassenger = "${dataOrder.totalPassengerString} /Pax"
            mData.totalPassengerInt = (dataOrder.adult + dataOrder.child + dataOrder.infant)
            mData.totalPrice    = StringUtils().setCurrency("IDR",  resultListFlightModel.price * mData.totalPassengerInt , false)

            dataList.add(mData)
            setLog(Serializer.serialize(mData))
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

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

        rv_confirmation_order_flight.layoutManager = layoutManager
        rv_confirmation_order_flight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_confirmation_order_flight.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    private fun initPrice() {
        Log.d("dtxxx","confirmorder xx : 01 " + Globals.typeAccomodation)

        tv_price.setOnClickListener {
            showOrHideDetailPrice()
        }
        ic_image.setOnClickListener {
            showOrHideDetailPrice()
        }
        tv_title_prize.setOnClickListener {
            showOrHideDetailPrice()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrice()
        }

        if (Globals.typeAccomodation== Constants.FLIGHT){
            val dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
            val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            tv_prize_departure.text   = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant) , false)
            tvPassengerTotal1.text = "${dataOrder.totalPassengerString} /Pax"
            tv_station_departure.text = "${dataListFlight.dataFlight[0].origin} - ${dataListFlight.dataFlight[0].destination}"

            if (dataListFlight.dataFlight.size>1){
                line_arrival.visibility     = View.VISIBLE
                tv_prize_arrival.text       =  StringUtils().setCurrency("IDR", dataListFlight.dataFlight[1].price * (dataOrder.adult + dataOrder.child + dataOrder.infant) , false)
                tv_station_arrival.text = "${dataListFlight.dataFlight[1].origin} - ${dataListFlight.dataFlight[1].destination}"
                tvPassengerTotal2.text = "${dataOrder.totalPassengerString} /Pax"
                tv_price_total.text         = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) , false)
                tv_price.text               = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) , false)
            }
            else{
                line_arrival.visibility     = View.GONE
                tv_price_total.text         = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
                tv_price.text               = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant) , false)
            }
        }
    }

    private fun showOrHideDetailPrice() {
        if (body_prize.isExpanded){
            collapsePrice()
        }
        else{
            expandPrice()
        }
    }

    private fun expandPrice() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_down))
        line_shadow.visibility  = View.VISIBLE
        tv_including.visibility = View.GONE
        body_prize.expand()
    }

    private fun collapsePrice() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_up_orange))
        tv_including.visibility = View.VISIBLE
        line_shadow.visibility  = View.GONE
        body_prize.collapse()
    }

    private fun initToolbar() {

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

    override fun onClick(v: View?) {
        when(v){
            line_reason_code ->{
                selectReasonCode()
            }
        }
    }

    private fun selectReasonCode() {
        val selectAccomodation = SelectReasonAccomodation(true,R.style.CustomDialog, Constants.DATA_REASON_CODE_FLIGHT)
        selectAccomodation.show(supportFragment,"dialog")

        selectAccomodation.setCallbackListener(object : SelectReasonAccomodation.CallbackSelectPreferance{
            override fun callback(model: ReasonCodeModel) {
                allreadySelectReasonCode = true
                val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
                dataOrder.reasonCode = model.codeBrief+"-"+model.description
                Globals.DATA_ORDER_FLIGHT = Serializer.serialize(dataOrder)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(views: Int, position: Int) {
    }
}