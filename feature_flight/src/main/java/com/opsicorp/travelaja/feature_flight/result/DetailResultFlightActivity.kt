package com.opsicorp.travelaja.feature_flight.result

import java.util.HashMap
import android.view.View
import java.lang.Exception
import com.squareup.picasso.Picasso
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.StringUtils
import com.mobile.travelaja.utility.DateConverter
import opsigo.com.domainlayer.callback.CallbackGetSsr
import opsigo.com.domainlayer.model.summary.PassportModel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackGetFareRules
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import opsigo.com.domainlayer.callback.CallbackValidationFlight
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import kotlinx.android.synthetic.main.layout_card_detail_flight_new.*
import opsigo.com.domainlayer.model.accomodation.flight.FareRulesModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.detail_departing_flight_activity_new.*
import opsigo.com.datalayer.request_model.accomodation.flight.ssr.SsrRequest
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ValidationFlightModel
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import kotlinx.android.synthetic.main.detail_departing_flight_activity_new.toolbar
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.Constants.isAllreadyFilterFlight
import com.opsicorp.travelaja.feature_flight.flight_info.activity.FlightInfoActivity
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.request_model.accomodation.flight.ssr.SegmentListItemRequest
import opsigo.com.datalayer.request_model.accomodation.flight.fare_rules.FareRulesRequest
import opsigo.com.datalayer.request_model.accomodation.flight.validation.SegmentsItemRequest
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.flight.validation.ValidationFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.fare_rules.SegmentFareRulesRequest
import opsigo.com.datalayer.request_model.accomodation.flight.validation.ContactValidationFlightRequest

class DetailResultFlightActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener, ButtonDefaultOpsicorp.OnclickButtonListener, View.OnClickListener {

    override fun getLayout(): Int {
        return R.layout.detail_departing_flight_activity_new
    }

    var isNotComply = false
    lateinit var dataTripPlan: SuccessCreateTripPlaneModel
    lateinit var dataFlight: ResultListFlightModel
    lateinit var dataSsr : SsrModel
    lateinit var dataFareRules : ArrayList<FareRulesModel>
    lateinit var dataOrder: OrderAccomodationModel
    var isSsr = false
    var isFareRules = false

    override fun OnMain() {
        getValidationFlight()

    }

    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
        if (Constants.multitrip){
            btn_next.setTextButton("Select Flight")
        }
        else {
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

    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        if (!Constants.multitrip){
            if(Globals.ALL_READY_SELECT_DEPARTING){
                toolbar.setTitleBar("Returning Flight")
            }else{
                toolbar.setTitleBar("Departing Flight")
            }
        }
        else {
            val position = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.positionFlightMulticity)!!
            toolbar.setDoubleTitle("${dataOrder.routes[position].originName}(${dataOrder.routes[position].idOrigin}) - ${dataOrder.routes[position].destinationName}(${dataOrder.routes[position].idDestination})","${DateConverter().getDate(dataOrder.routes[position].dateDeparture,"yyyy-MM-dd","EEE, dd MMM yyyy")} - 1 pax")
        }
        toolbar.callbackOnclickToolbar(this)
    }

    private fun getValidationFlight() {
        setLog(Serializer.serialize(dataRequest()))
        showLoadingOpsicorp(true)
        if (Globals.isPertamina(this)){
            isNotComply = true
            getFareRules()
            getSsr()
        }
        else {
            GetDataAccomodation(getBaseUrl()).getValidationFlight(getToken(),dataRequest(),object : CallbackValidationFlight {
                override fun successLoad(data: ValidationFlightModel) {
                    isNotComply = data.isSecurity || data.isSecondary || data.isResticted || data.advanceBooking || !data.isLowerFare || !data.isAirlinePolicy
                    getFareRules()
                    getSsr()
                }

                override fun failedLoad(message: String) {
                    hideLoadingOpsicorp()
                }
            })
        }

    }

    private fun getFareRules() {
        GetDataAccomodation(getBaseUrl()).getFareRules(getToken(),dataFareRulesRequest(),object : CallbackGetFareRules {
            override fun success(data: ResultListFlightModel) {
                isFareRules = true
                dataFareRules = data.dataFareRules
            }

            override fun failed(string: String) {
            }
        })
    }

    private fun dataFareRulesRequest(): HashMap<Any, Any> {
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
        val dataProfile = Globals.getProfile(applicationContext)
        val data = FareRulesRequest()
        data.adult = 1
        data.child = 0
        data.infant = 0
        data.companyCode = dataProfile.companyCode
        data.travelAgent = "apidev"
        data.provider = dataFlight.airline
        data.segments = getDataSegmentFareRules()
        return Globals.classToHashMap(data, FareRulesRequest::class.java)
    }

    private fun getDataSegmentFareRules(): List<SegmentFareRulesRequest?>? {
        val data = ArrayList<SegmentFareRulesRequest>()
        val mData = SegmentFareRulesRequest()
        mData.classId               = dataFlight.classId
        mData.flightId              = dataFlight.flightId
        mData.fareRuleKeys          = dataFlight.fareRuleKeys
        mData.num                   = 0
        mData.seq                   = 0
        data.add(mData)

        return data
    }

    private fun getSsr() {
        GetDataAccomodation(getBaseUrl()).getSsrFlight(getToken(),dataSrrRequest(),object : CallbackGetSsr {
            override fun success(data: SsrModel) {
                hideLoadingOpsicorp()
                isSsr = true
                dataSsr = data
                initView()
            }

            override fun failed(string: String) {
                hideLoadingOpsicorp()
                initView()
            }
        })
    }

    private fun dataSrrRequest(): HashMap<Any, Any> {
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

        val data = SsrRequest()
        data.adult = 1
        data.child = 0
        data.infant = 0
        data.travelAgent = "apidev"
        data.segmentList = getDataSegmentSsr()
        /*val data = Serializer.deserialize(temporary, SsrRequest::class.java)*/
        return Globals.classToHashMap(data, SsrRequest::class.java)
    }

    private fun getDataSegmentSsr(): List<SegmentListItemRequest?>? {
        val data = ArrayList<SegmentListItemRequest>()
        val mData = SegmentListItemRequest()
        mData.origin                = dataFlight.origin
        mData.destination           = dataFlight.destination
        mData.arriveTime            = dataFlight.arriveTime
        mData.airline               = dataFlight.airline
        mData.num                   = dataFlight.num
        mData.classId               = dataFlight.classId
        mData.departDate            = dataFlight.departDate
        mData.classCode             = dataFlight.classCode
        mData.departTime            = dataFlight.departTime
        mData.flightId              = dataFlight.flightId
        mData.flightNumber          = dataFlight.flightNumber
        mData.arriveDate            = dataFlight.arriveDate
        mData.seq                   = dataFlight.seq
        data.add(mData)

        return data
    }

    private fun initView() {
        tv_price.text = StringUtils().setCurrency("IDR",Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java).price , false)
        line_info_facility.setOnClickListener{
            goInfoFlight()
        }

        if(Globals.DATA_FLIGHT.isNotEmpty()){

            dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)
            dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

            initButtonNext()
            initToolbar()

            tv_airport_name.text = dataFlight.originAirport
            title_airline.text   = dataFlight.titleAirline
            tv_number.text       = dataFlight.flightNumber
            tv_type_class.text   = dataFlight.nameClass + " (" + dataFlight.code + ")"

            tv_duration.text     = dataFlight.durationView
            if(dataFlight.isConnecting){
                tv_transit.visibility   = View.GONE
            }else{
                tv_transit.visibility   = View.GONE
            }

            tv_time_departure.text   = dataFlight.departTime
            tv_time_arrival.text     = dataFlight.arriveTime

            tv_date_departure.text  = DateConverter().setDateFormat4(dataFlight.departDate)
            tv_date_arrival.text    = DateConverter().setDateFormat4(dataFlight.arrivalDate)

            if (Globals.ALL_READY_SELECT_DEPARTING){
                tv_departure.text        = dataOrder.destinationName + " (" + dataOrder.idDestination + ")"
                tv_arrival.text          = dataOrder.originName + " (" + dataOrder.idOrigin + ")"
            }
            else{
                tv_departure.text        = dataOrder.originName + " (" + dataOrder.idOrigin + ")"
                tv_arrival.text          = dataOrder.destinationName + " (" + dataOrder.idDestination + ")"
            }

            Picasso.get()
                    .load(dataFlight.imgAirline)
                    .fit()
                    .centerInside()
                    .into(img_flight_icon)

        }
    }

    private fun goInfoFlight() {
        gotoActivity(FlightInfoActivity::class.java)
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

        return Globals.classToHashMap(data, ValidationFlightRequest::class.java)
    }

    private fun getRemark(): List<String?> {
        val data = ArrayList<String>()

        data.add(getProfile().title)
        data.add(getProfile().employId)
        data.add(getProfile().firstName)
        data.add(getProfile().email)
        data.add(getProfile().homePhone)
        data.add(getProfile().mobilePhone)
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

    private fun getDataSegment(): List<SegmentsItemRequest?>? {
        val data = ArrayList<SegmentsItemRequest>()
        val mData = SegmentsItemRequest()
        mData.origin = dataFlight.origin
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

    private fun getDataMembers(): List<String?>? {
        val data = ArrayList<String>()
        data.add(getProfile().employId)
        return data
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onClicked() {
        checkTotalTrip()
    }

    private fun checkTotalTrip() {
        if (Globals.ONE_TRIP){
            if (Constants.isBisnisTrip){
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderFlightActivity::class.java)
            }
            else{
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderFlightActivity::class.java)
            }
        }
        else{
            isAllreadyFilterFlight = false
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
        var datalist   = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

        //save flight
        try {
            datalist.dataFlight.add(dataFlight)
        }catch (e: Exception){
            datalist = DataListOrderAccomodation()
            datalist.dataFlight.add(dataFlight)
        }

        val mDataBooker = BookingContactAdapterModel()
        mDataBooker.typeContact = Constants.ADULT
        mDataBooker.checktype   = Constants.TYPE_KTP
        mDataBooker.sim         = getSimDataBooker()
        mDataBooker.pasport     = getPassportDataBooker()
        mDataBooker.idcard      = getDataIdCartBooker()

        datalist.dataFlight.last().passenger.clear()
        datalist.dataFlight.last().passenger.add(mDataBooker)

        for (i in 0 until dataOrder.infant) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.INFANT
            datalist.dataFlight.last().passenger.add(mData)
        }
        if (dataOrder.adult > 1){
            for (i in 0 until dataOrder.adult -1) {
                if (dataTripPlan.isTripPartner.equals(true)){
                    if (i == 0){
                        val mData = BookingContactAdapterModel()
                        mData.idcard.fullname = dataTripPlan.tripPartnerName
                        mData.pasport.fullname = dataTripPlan.tripPartnerName
                        mData.sim.name = dataTripPlan.tripPartnerName
                        mData.typeContact = Constants.ADULT
                        datalist.dataFlight.last().passenger.add(mData)
                    }
                } else {
                    val mData = BookingContactAdapterModel()
                    mData.typeContact = Constants.ADULT
                    datalist.dataFlight.last().passenger.add(mData)
                }
            }
        }
        for (i in 0 until dataOrder.child) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.CHILD
            datalist.dataFlight.last().passenger.add(mData)
        }
        if (isSsr){
            datalist.dataFlight.last().passenger.mapIndexed { index, bookingContactAdapterModel ->
                bookingContactAdapterModel.ssr = dataSsr
            }
        }


        datalist.dataFlight.last().notComply = isNotComply

        if (datalist.dataFlight.last().flightType.equals("GdsBfm")){
            if (isFareRules) {
                datalist.dataFlight.last().dataFareRules = dataFareRules
            }
        }

        /*dataContacts.add(mDataBooker)*/
        /*datalist.dataFlight.mapIndexed { index, resultListFlightModel ->
            resultListFlightModel.passenger.clear()
            resultListFlightModel.passenger.add(mDataBooker)
        }

        for (i in 0 until dataOrder.infant) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.INFANT
            datalist.dataFlight.mapIndexed { index, resultListFlightModel ->
                resultListFlightModel.passenger.add(mData)
            }
        }
        if (dataOrder.adult > 1){
            for (i in 0 until dataOrder.adult -1) {
                val mData = BookingContactAdapterModel()
                mData.typeContact = Constants.ADULT
                datalist.dataFlight.mapIndexed { index, resultListFlightModel ->
                    resultListFlightModel.passenger.add(mData)
                }
            }
        }
        for (i in 0 until dataOrder.child) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.CHILD
            datalist.dataFlight.mapIndexed { index, resultListFlightModel ->
                resultListFlightModel.passenger.add(mData)
            }
        }

        datalist.dataFlight.forEach {
            it.passenger.mapIndexed { index, bookingContactAdapterModel ->
                bookingContactAdapterModel.ssr = dataSsr
            }
        }

        datalist.dataFlight.forEach {
            it.notComply = isNotComply
        }

        datalist.dataFlight.forEach {
            if (it.flightType.equals("GdsBfm")){
                it.dataFareRules = dataFareRules
            }
        }*/

        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist, DataListOrderAccomodation::class.java)
    }


    private fun getDataIdCartBooker(): IdCartModel {
        val model           = IdCartModel()
        model.id            = getProfile().employId
        model.title         = getProfile().title
        model.fullname      = getProfile().fullName
        model.idCart        = getProfile().idNumber
        model.mobilePhone   = getProfile().mobilePhone
        model.email         = getProfile().email
        model.birthDate     = getProfile().birthDate

        return model
    }

    private fun getSimDataBooker(): SimModel {
        val model         = SimModel()
        model.id          = getProfile().employId
        model.idSim       = getProfile().sim
        model.title       = getProfile().title
        model.name        = getProfile().fullName
        model.email       = getProfile().email
        model.birthDate   = getProfile().birthDate
        model.mobilePhone = getProfile().mobilePhone
        return model
    }

    private fun getPassportDataBooker(): PassportModel {
        val model = PassportModel()
        model.passporNumber = getProfile().passport
        model.fullname      = getProfile().fullName
        model.id            = getProfile().employId
        model.title         = getProfile().title
        model.birtDate      = getProfile().birthDate
        model.nasionality   = getProfile().nationality
        model.mobilePhone   = getProfile().mobilePhone

        return model
    }

    override fun onClick(v: View?) {
    }
}