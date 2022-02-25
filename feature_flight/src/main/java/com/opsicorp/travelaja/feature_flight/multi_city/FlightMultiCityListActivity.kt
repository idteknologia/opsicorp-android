package com.opsicorp.travelaja.feature_flight.multi_city

import android.os.Build
import android.os.Bundle
import java.util.HashMap
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.mobile.travelaja.base.BaseActivity
import opsigo.com.datalayer.mapper.Serializer
import com.opsicorp.travelaja.feature_flight.R
import opsigo.com.domainlayer.callback.CallbackGetSsr
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.opsicorp.travelaja.feature_flight.adapter.TotalPriceAdapter
import com.opsicorp.travelaja.feature_flight.result.ConfirmOrderFlightActivity
import opsigo.com.domainlayer.model.summary.PassportModel
import opsigo.com.domainlayer.callback.CallbackGetFareRules
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.domainlayer.callback.CallbackValidationFlight
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import kotlinx.android.synthetic.main.multi_city_list_activity.*
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.request_model.accomodation.flight.ssr.SsrRequest
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ValidationFlightModel
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import com.opsicorp.travelaja.feature_flight.result.ResultSearchFlightActivity
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.*
import kotlinx.android.synthetic.main.detail_price_bottom_new.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.request_model.accomodation.flight.ssr.SegmentListItemRequest
import opsigo.com.datalayer.request_model.accomodation.flight.fare_rules.FareRulesRequest
import opsigo.com.datalayer.request_model.accomodation.flight.fare_rules.SegmentFareRulesRequest
import opsigo.com.datalayer.request_model.accomodation.flight.validation.SegmentsItemRequest
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.flight.validation.ValidationFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.validation.ContactValidationFlightRequest

class FlightMultiCityListActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView {

    override fun getLayout(): Int {
        return R.layout.multi_city_list_activity
    }

    val dataFligt = ArrayList<ResultListFlightModel>()
    val adapterPrice by lazy { TotalPriceAdapter(this) }
    val adapter          = FlightMultiCityListAdapter(this@FlightMultiCityListActivity)
    var dataOrder        = OrderAccomodationModel()
    val dataFLigt        = DataListOrderAccomodation()
    var currentPosition  = 0

    override fun OnMain() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        initToolbar()
        initRecyclerView()
        mappingDataListFlight()
    }

    private fun mappingDataListFlight() {
        dataFLigt.dataFlight.clear()
        dataOrder.routes.forEachIndexed { index, routeMultiCityModel ->
            if (routeMultiCityModel.flightResult.titleAirline.isEmpty()){
                val data = ResultListFlightModel()
                data.departDate      = routeMultiCityModel.dateDeparture
                data.origin          = routeMultiCityModel.idOrigin
                data.destination     = routeMultiCityModel.idDestination
                data.originName      = routeMultiCityModel.originName
                data.destinationName = routeMultiCityModel.destinationName
                dataFLigt.dataFlight.add(data)
            }
            else {
                dataFLigt.dataFlight.add(routeMultiCityModel.flightResult)
            }
        }
        adapter.setData(dataFLigt.dataFlight)
        checkEmptyFlight()
    }

    private fun initRecyclerView() {
        rv_confirmation_order_flight.apply {
            val lm          = LinearLayoutManager(this@FlightMultiCityListActivity)
            lm.orientation  = LinearLayoutManager.VERTICAL
            layoutManager   = lm
            itemAnimator    = DefaultItemAnimator()
            adapter         = this@FlightMultiCityListActivity.adapter
        }

        adapter.setOnclickListener(this)

        rv_total_price.apply {
            val lm          = LinearLayoutManager(this@FlightMultiCityListActivity)
            lm.orientation  = LinearLayoutManager.VERTICAL
            layoutManager   = lm
            itemAnimator    = DefaultItemAnimator()
            adapter    = this@FlightMultiCityListActivity.adapterPrice
        }
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.setTitleBar("Multi City")
        toolbar.callbackOnclickToolbar(this)
        toolbar.singgleTitleGravity(toolbar.START)
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("Book")
        tv_price.gone()
        line_shadow.gone()
    }

    private fun changeButtonBookGrayColor() {
        btn_next.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_dark_select_budget)
    }

    private fun changeButtonBookOrangeColor() {
        btn_next.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_yellow)
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onClicked() {
        if (dataFLigt.dataFlight.filter { it.titleAirline.isEmpty() }.isEmpty()){
            dataOrder.routes.forEachIndexed { index, it ->
                it.flightResult = dataFLigt.dataFlight[index]
            }
            Globals.DATA_ORDER_FLIGHT = Serializer.serialize(dataOrder, OrderAccomodationModel::class.java)
            gotoActivity(ConfirmOrderFlightActivity::class.java)
        }
    }

    private fun checkEmptyFlight() {
        if (dataFLigt.dataFlight.filter { it.titleAirline.isEmpty() }.isEmpty()){
            changeButtonBookOrangeColor()
        } else {
            changeButtonBookGrayColor()
        }
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.SELECT_FLIGHT -> {
                currentPosition = position
                Constants.multitrip = true
                val bundle = Bundle()
                bundle.putInt(Constants.positionFlightMulticity,position)
                gotoActivityResultWithBundle(ResultSearchFlightActivity::class.java,bundle,Constants.REQUEST_CODE_SELECT_FLIGHT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constants.REQUEST_CODE_SELECT_FLIGHT -> {
                if (resultCode==Activity.RESULT_OK){
                    val data = Serializer.deserialize(data?.getStringExtra(Constants.KEY_INTENT_SELECT_FLIGHT),ResultListFlightModel::class.java)
                    if (getProfile().companyCode=="000002"){
                        dataOrder.routes[currentPosition].flightResult = data
                        dataOrder.routes[currentPosition].flightResult.notComply = false
                        setTotalprice()
                        getFareRules(dataOrder.routes[currentPosition].flightResult)
                        getSsr(dataOrder.routes[currentPosition].flightResult)
                    }else {
                        getValidationFlight(data)
                    }
                }
            }
        }
    }

    private fun getValidationFlight(dataFlight: ResultListFlightModel) {
        setLog(Serializer.serialize(dataRequest(dataFlight)))
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getValidationFlight(getToken(),dataRequest(dataFlight),object : CallbackValidationFlight {
            override fun successLoad(data: ValidationFlightModel) {
                val isNotComply = data.isSecurity || data.isSecondary || data.isResticted || data.advanceBooking || !data.isLowerFare || !data.isAirlinePolicy
                dataOrder.routes[currentPosition].flightResult = dataFlight
                dataOrder.routes[currentPosition].flightResult.notComply = isNotComply
                setTotalprice()
//                getFareRules(dataOrder.routes[currentPosition].flightResult)
                getSsr(dataOrder.routes[currentPosition].flightResult)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
            }
        })
    }

    private fun getSsr(dataFlight: ResultListFlightModel) {
        GetDataAccomodation(getBaseUrl()).getSsrFlight(getToken(),dataSrrRequest(dataFlight),object : CallbackGetSsr {
            override fun success(data: SsrModel) {
                hideLoadingOpsicorp()
                saveDataSsr(dataFlight,data,true)
            }

            override fun failed(string: String) {
                saveDataSsr(dataFlight,SsrModel(),false)
            }
        })
    }

    private fun saveDataSsr(dataFlight: ResultListFlightModel,dataSsr: SsrModel,ssrNotEmpty:Boolean) {
        val dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val mDataBooker = BookingContactAdapterModel()
        mDataBooker.typeContact = Constants.ADULT
        mDataBooker.checktype   = Constants.TYPE_KTP
        mDataBooker.sim         = getSimDataBooker()
        mDataBooker.pasport     = getPassportDataBooker()
        mDataBooker.idcard      = getDataIdCartBooker()

        dataFlight.passenger.clear()
        dataFlight.passenger.add(mDataBooker)

        for (i in 0 until dataOrder.infant) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.INFANT
            dataFlight.passenger.add(mData)
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
                        dataFlight.passenger.add(mData)
                    }
                } else {
                    val mData = BookingContactAdapterModel()
                    mData.typeContact = Constants.ADULT
                    dataFlight.passenger.add(mData)
                }
            }
        }
        for (i in 0 until dataOrder.child) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.CHILD
            dataFlight.passenger.add(mData)
        }

        if (ssrNotEmpty){
            dataFlight.passenger.mapIndexed { index, bookingContactAdapterModel ->
                bookingContactAdapterModel.ssr = dataSsr
            }
        }


        mappingDataListFlight()
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

    private fun getFareRules(dataFlight: ResultListFlightModel) {
        GetDataAccomodation(getBaseUrl()).getFareRules(getToken(),dataFareRulesRequest(dataFlight),object : CallbackGetFareRules {
            override fun success(data: ResultListFlightModel) {
                if (dataFlight.flightType.equals("GdsBfm")){
                    dataFlight.dataFareRules = data.dataFareRules
                }
            }

            override fun failed(string: String) {

            }
        })
    }


    private fun dataFareRulesRequest(dataFlight: ResultListFlightModel): HashMap<Any, Any> {
        val dataProfile = Globals.getProfile(applicationContext)
        val data = FareRulesRequest()
        data.adult = 1
        data.child = 0
        data.infant = 0
        data.companyCode = dataProfile.companyCode
        data.travelAgent = "apidev"
        data.provider = dataFlight.airline
        data.segments = getDataSegmentFareRules(dataFlight)
        return Globals.classToHashMap(data, FareRulesRequest::class.java)
    }

    private fun getDataSegmentFareRules(dataFlight: ResultListFlightModel): List<SegmentFareRulesRequest?>? {
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


    private fun dataSrrRequest(dataFlight: ResultListFlightModel): HashMap<Any, Any> {
        val data = SsrRequest()
        data.adult = 1
        data.child = 0
        data.infant = 0
        data.travelAgent = "apidev"
        data.segmentList = getDataSegmentSsr(dataFlight)
        /*val data = Serializer.deserialize(temporary, SsrRequest::class.java)*/
        return Globals.classToHashMap(data, SsrRequest::class.java)
    }

    private fun getDataSegmentSsr(dataFlight: ResultListFlightModel): List<SegmentListItemRequest?>? {
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

    private fun dataRequest(dataFlight: ResultListFlightModel): HashMap<Any, Any> {
        val dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val data = ValidationFlightRequest()
        data.origin              = dataFlight.origin
        data.destination         = dataFlight.destination
        data.remarks             = getRemark()
        data.contact             = getDataContact()
        data.flightType          = 0
        data.segments            = getDataSegment(dataFlight)
        data.purpose             = dataTripPlan.purpose
        data.members             = getDataMembers()

        return Globals.classToHashMap(data, ValidationFlightRequest::class.java)
    }

    private fun getDataSegment(dataFlight: ResultListFlightModel): List<SegmentsItemRequest?> {
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

    private fun getDataMembers(): List<String> {
        val data = ArrayList<String>()
        data.add(getProfile().employId)
        return data
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

    fun setTotalprice(){

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

        dataFligt.clear()
        dataOrder.routes.forEach {
            dataFligt.add(it.flightResult)
        }
        adapterPrice.setData(dataFligt)
        rl_total_price.visible()

        var totalPrice = 0.0
        dataOrder.routes.forEach {
            totalPrice += it.flightResult.price
        }
        val totalPricing = totalPrice * dataOrder.totalPassengerInteger
        tv_title_prize.text = "${getString(R.string.total_price_for)} ${dataOrder.totalPassengerInteger} pax and ${dataFLigt.dataFlight.size} item(s)"
        tv_total_price.text = "IDR ${Globals.formatAmount(totalPricing)}"
        tv_price_total.text         = "IDR ${Globals.formatAmount(totalPricing)}"
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


}