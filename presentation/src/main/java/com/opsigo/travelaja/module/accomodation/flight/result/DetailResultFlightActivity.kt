package com.opsigo.travelaja.module.accomodation.flight.result

import opsigo.com.datalayer.request_model.accomodation.flight.validation.ContactValidationFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.validation.ValidationFlightRequest
import com.opsigo.travelaja.module.accomodation.flight.flight_info.activity.FlightInfoActivity
import opsigo.com.datalayer.request_model.accomodation.flight.validation.SegmentsItemRequest
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.detail_departing_flight_activity.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ValidationFlightModel
import kotlinx.android.synthetic.main.layout_card_detail_flight.*
import opsigo.com.domainlayer.callback.CallbackValidationFlight
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.StringUtils
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import com.squareup.picasso.Picasso
import com.opsigo.travelaja.R
import java.lang.Exception
import android.view.View
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.request_model.accomodation.flight.ssr.SsrRequest
import opsigo.com.domainlayer.callback.CallbackGetSsr
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import java.util.HashMap

class DetailResultFlightActivity : BaseActivity(),ToolbarOpsicorp.OnclickButtonListener,ButtonDefaultOpsicorp.OnclickButtonListener,View.OnClickListener {

    override fun getLayout(): Int { return R.layout.detail_departing_flight_activity }

    var isNotComply = false
    lateinit var dataTripPlan: SuccessCreateTripPlaneModel
    lateinit var dataFlight: ResultListFlightModel
    lateinit var dataSsr : SsrModel

    override fun OnMain() {
        getValidationFlight()

        initButtonNext()
        initToolbar()
    }

    private fun getValidationFlight() {
        setLog(Serializer.serialize(dataRequest()))
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getValidationFlight(getToken(),dataRequest(),object :CallbackValidationFlight{
            override fun successLoad(data: ValidationFlightModel) {
                isNotComply = data.isSecurity || data.isSecondary || data.isResticted || data.advanceBooking || !data.isLowerFare || !data.isAirlinePolicy
                getSsr()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
            }
        })
    }

    private fun dataRequest(): HashMap<Any, Any> {
        dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

        val data = ValidationFlightRequest()
        data.origin              = dataFlight.origin
        data.destination         = dataFlight.destination
        data.remarks             = getRemark()
        data.contact             = getDataContact()
        data.flightType          = 0
        data.segments            = getDataSegment()
        data.purpose             = dataTripPlan.purpose
        data.members             = getDataMembers()

        return Globals.classToHashMap(data,ValidationFlightRequest::class.java)
    }

    private fun getDataMembers(): List<String> {
        val data = ArrayList<String>()
        data.add(getProfile().employId)
        return data
    }

    private fun getDataSegment(): List<SegmentsItemRequest> {

        val data = ArrayList<SegmentsItemRequest>()
        val mData = SegmentsItemRequest()
        mData.origin = ""
        mData.destination           = dataFlight.destination
        mData.arriveDate            = dataFlight.arriveDate
        mData.airlineImageUrl       = dataFlight.imgAirline
        mData.category              = dataFlight.nameClass
        mData.airline               = dataFlight.airline
        mData.airlineView           = dataFlight.titleAirline
        mData.num                   = 0
        mData.amount                = dataFlight.price.toInt()
        mData.airlineName           = dataFlight.titleAirline
        mData.classId               = dataFlight.classId
        mData.duration              = dataFlight.duration
        mData.departDate            = dataFlight.departDate
        mData.originView            = dataFlight.originName
        mData.classCode             = dataFlight.classCode
        mData.departTime            = dataFlight.departTime
        mData.flightId              = dataFlight.flightId
        mData.destinationView       = dataFlight.destinationName
        mData.flightNumber          = dataFlight.flightNumber
        mData.arriveDate            = dataFlight.arriveDate
        mData.seq                   = 0
        data.add(mData)

        return data
    }

    private fun getDataContact(): ContactValidationFlightRequest? {
        val data = ContactValidationFlightRequest()
        data.email          = getProfile().email
        data.homePhone      = getProfile().phone
        data.firstName      = getProfile().firstName
        data.title          = getProfile().title
        data.lastName       = getProfile().lastName
        data.mobilePhone    = getProfile().phone
        return data
    }

    private fun getRemark(): List<String> {
        val data = ArrayList<String>()
        data.add(getProfile().title)
        data.add(getProfile().employId)
        data.add(getProfile().firstName)
        data.add(getProfile().email)
        data.add(getProfile().homePhone)
        data.add(getProfile().mobilePhone)
        return data
    }

    private fun initView() {
        tv_price.text = StringUtils().setCurrency("IDR",Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java).price , false)
        line_info_facility.setOnClickListener{
            goInfoFlight()
        }

        if(Globals.DATA_FLIGHT.isNotEmpty()){
            val data = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
            val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            tv_airport_name.text = data.originAirport
            title_airline.text   = data.titleAirline
            tv_number.text       = data.flightNumber
            tv_type_class.text   = data.nameClass + " (" + data.code + ")"


            tv_duration.text     = data.durationView
            if(data.isConnecting){
                tv_transit.visibility   = View.GONE
            }else{
                tv_transit.visibility   = View.GONE
            }


            tv_time_departure.text   = data.departTime
            tv_time_arrival.text     = data.arriveTime

            tv_date_departure.text  = DateConverter().setDateFormat4(data.departDate)
            tv_date_arrival.text    = DateConverter().setDateFormat4(data.arrivalDate)
//            tv_departure.text       = "${data.originName} (${data.origin})"
//            tv_arrival.text         = "${data.destinationName} (${data.destination})"


            if (Globals.ALL_READY_SELECT_DEPARTING){
                tv_departure.text        = dataOrder.destinationName + " (" + dataOrder.idDestination + ")"
                tv_arrival.text          = dataOrder.originName + " (" + dataOrder.idOrigin + ")"
            }
            else{
                tv_departure.text        = dataOrder.originName + " (" + dataOrder.idOrigin + ")"
                tv_arrival.text          = dataOrder.destinationName + " (" + dataOrder.idDestination + ")"
            }

            Picasso.get()
                    .load(data.imgAirline)
                    .fit()
                    .centerInside()
                    .into(img_flight_icon)

        }
    }


    fun goInfoFlight() {
        gotoActivity(FlightInfoActivity::class.java)
    }


    private fun initToolbar() {
        if(Globals.ALL_READY_SELECT_DEPARTING){
            toolbar.setTitleBar("Detail Returning Flight")
        }else{
            toolbar.setTitleBar("Detail Departing Flight")
        }
        toolbar.callbackOnclickToolbar(this)
    }

    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
        if (Globals.ONE_TRIP){
            btn_next.setTextButton("Select Departing")
        }
        else{
            if (Globals.ALL_READY_SELECT_DEPARTING){
                btn_next.setTextButton("Select Returning")
            }
            else {
                btn_next.setTextButton("Select Departing ")
            }
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onClicked() {
        checkTotalTrip()
    }

    private fun checkTotalTrip() {
        if (Globals.ONE_TRIP){
            if (Globals.BisnisTrip){
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderFlightActivity::class.java)
            }
            else{
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderFlightActivity::class.java)
            }
        }
        else{
            if (Globals.ALL_READY_SELECT_DEPARTING){
                saveDataListAccomodation()
                Globals.ALL_READY_SELECT_DEPARTING = false
                gotoActivity(ConfirmOrderFlightActivity::class.java)
                finish()
            }
            else{
                saveDataListAccomodation()
                Globals.ALL_READY_SELECT_DEPARTING = true
                finish()
            }
        }
    }

    private fun saveDataListAccomodation() {
        var datalist   = Serializer.deserialize(Globals.DATA_LIST_FLIGHT,DataListOrderAccomodation::class.java)
        val dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
        dataFlight.notComply = isNotComply
        dataFlight.dataSSR   = dataSsr
        try {
            datalist.dataFlight.add(dataFlight)
        }catch (e:Exception){
            datalist = DataListOrderAccomodation()
            datalist.dataFlight.add(dataFlight)
        }
        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist,DataListOrderAccomodation::class.java)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    fun getSsr(){
        GetDataAccomodation(getBaseUrl()).getSsrFlight(getToken(),dataSrrRequest(),object : CallbackGetSsr {
            override fun success(data: SsrModel) {
                hideLoadingOpsicorp()
                initView()
                dataSsr = data
            }

            override fun failed(string: String) {

            }
        })
    }

    private fun dataSrrRequest(): HashMap<Any, Any> {
        val temporary = "{\n" +
                "    \"Adult\":1,\n" +
                "    \"Child\":0,\n" +
                "    \"Infant\":0,\n" +
                "    \"TravelAgent\":\"apidev\",\n" +
                "    \"SegmentList\":[\n" +
                "        {\n" +
                "            \"Airline\":2,\n" +
                "            \"ArriveDate\":\"2020-11-01\",\n" +
                "            \"ArriveTime\":\"07:10\",\n" +
                "            \"ClassCode\":\"Q\",\n" +
                "            \"ClassId\":\"20f47466-83e1-48a6-982c-8386aae9d4f7\",\n" +
                "            \"DepartDate\":\"2020-11-01\",\n" +
                "            \"DepartTime\":\"05:40\",\n" +
                "            \"Destination\":\"CGK\",\n" +
                "            \"FlightId\":\"780faf48-7e58-4e86-9070-3d761a8ac259\",\n" +
                "            \"FlightNumber\":\"JT 595\",\n" +
                "            \"Num\":\"1\",\n" +
                "            \"Origin\":\"SUB\",\n" +
                "            \"Seq\":\"0\"\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        val data = Serializer.deserialize(temporary, SsrRequest::class.java)
        return Globals.classToHashMap(data, SsrRequest::class.java)
    }

}