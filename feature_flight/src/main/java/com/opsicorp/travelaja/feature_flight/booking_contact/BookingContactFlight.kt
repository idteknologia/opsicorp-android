package com.opsicorp.travelaja.feature_flight.booking_contact

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ContactFlightRequest
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.*
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.request_model.reservation.TripParticipantsItem
import opsigo.com.domainlayer.model.accomodation.flight.ReserveFlightModel
import opsigo.com.datalayer.model.accomodation.flight.seat.ResultSeat
import kotlinx.android.synthetic.main.booking_contact_view_flight.*
import com.opsicorp.travelaja.feature_flight.ssr.BagageActivity
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import com.opsicorp.travelaja.feature_flight.ssr.SsrActivity
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.domainlayer.callback.CallbackReserveFlight
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.summary.PassportModel
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsicorp.travelaja.feature_flight.R
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import android.content.Intent
import android.app.Activity
import android.view.View
import android.os.Build
import android.support.v4.content.ContextCompat
import com.opsicorp.travelaja.feature_flight.seat_map.SelectSeatActivity
import com.opsicorp.travelaja.feature_flight.ssr.FrequentFlyerActivity
import com.opsigo.travelaja.module.profile.KtpCardFormActivity
import com.opsigo.travelaja.module.profile.PassportFormActivity
import com.opsigo.travelaja.module.profile.SimFormContactActivity
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat.SeatFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr.BagageFlightRequest
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import java.lang.Exception

class BookingContactFlight : BaseActivity(),OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.booking_contact_view_flight }

    val dataContacts = ArrayList<BookingContactAdapterModel>()
    val resultSeat = ResultSeat()
    val adapter by lazy { BookingContactFlightAdapter(this,dataContacts) }
    lateinit var dataOrder: OrderAccomodationModel
    lateinit var dataListFlight: DataListOrderAccomodation
    lateinit var dataFlight: ResultListFlightModel

    override fun OnMain() {
        initRecyclerView()
        setDataContact()
        initToolbar()

    }

    private fun initToolbar() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        btn_next.setTextButton("Continue")

        val datedepar = DateConverter().setDateFormat3(dataOrder.dateDeparture)
        val datereturn = DateConverter().setDateFormat3(dataOrder.dateArrival)

        /*toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}"," ${datedepar} , ${datereturn}")*/ //- 1 pax
        toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}"," ${datedepar}") //- 1 pax

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }

        if (Constants.DATA_SEAT_AIRLINE.isNotEmpty()) {
            line_select_seat_map.visible()
        } else {
            line_select_seat_map.gone()
        }
    }

    private fun setDataContact() {
        val mDataBooker = BookingContactAdapterModel()
        mDataBooker.typeContact = Constants.ADULT
        mDataBooker.sim         = getSimDataBooker()
        mDataBooker.pasport     = getPassportDataBooker()
        mDataBooker.idcart      = getDataIdCartBooker()
        dataContacts.add(mDataBooker)

        val dataProfile = getProfile()
        tv_name_contact.text        = dataProfile.name
        tv_number_contact.text      = dataProfile.homePhone
        tv_email_contact.text       = dataProfile.email

        adapter.setData(dataContacts)
        initPrice()
    }

    private fun getDataIdCartBooker(): IdCartModel {
        val model = IdCartModel()
        model.fullname = getProfile().name
        model.id       = getProfile().employId
        model.idCart   = getProfile().ktp
        return model
    }

    private fun getSimDataBooker(): SimModel {
        val model = SimModel()
        model.name = getProfile().name
        model.id   = getProfile().employId
        model.idSim = getProfile().sim
        return model
    }

    private fun getPassportDataBooker(): PassportModel {
        val model = PassportModel()
        model.firstName = getProfile().firstName
        model.id        = getProfile().employId
        model.number    = getProfile().passport
        return model
    }

    private fun initRecyclerView() {
        line_select_seat_map.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_booking_information.layoutManager = layoutManager
        rv_booking_information.itemAnimator = DefaultItemAnimator()
        rv_booking_information.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initPrice() {
        tv_price.setOnClickListener {
            showOrHideDetailPrize()
        }
        ic_image.setOnClickListener {
            showOrHideDetailPrize()
        }
        tv_title_prize.setOnClickListener {
            showOrHideDetailPrize()
        }

        btn_next.callbackOnclickButton(this)

        if (Globals.typeAccomodation==Constants.FLIGHT){
            val dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

            tv_prize_departure.text   = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
            tv_station_departure.text = "${dataListFlight.dataFlight[0].origin} - ${dataListFlight.dataFlight[0].destination}"


            if (dataListFlight.dataFlight.size>1){
                line_arrival.visibility     = View.VISIBLE
                tv_prize_arrival.text       =  StringUtils().setCurrency("IDR", dataListFlight.dataFlight[1].price , false)
                tv_station_arrival.text     = "${dataListFlight.dataFlight[1].origin} - ${dataListFlight.dataFlight[1].destination}"
                tv_price_total.text         = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price ) , false)
                tv_price.text               = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price ) , false)
            }
            else{
                line_arrival.visibility     = View.GONE
                tv_price_total.text         = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
                tv_price.text               = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price , false)
            }
            dataListFlight.dataFlight.forEachIndexed { index, resultListFlightModel ->
                if (resultListFlightModel.dataSSR.bagaggeItemSelected.isNotEmpty()||resultListFlightModel.dataSSR.ssrSelected.isNotEmpty()){
                    rlBaggagePrice.visible()
                    rlSsrPrice.visible()
                    tv_total_price_baggage.text = StringUtils().setCurrency("IDR",totalPriceBaggage()!!.toDouble(),false)
                    tv_total_price_ssr.text = StringUtils().setCurrency("IDR",totalPriceSsr()!!.toDouble(),false)
                    if (dataListFlight.dataFlight.size>1){
                        tv_price_total.text         = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble() ) , false)
                        tv_price.text               = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble() ) , false)
                    }
                    else{
                        tv_price_total.text         = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble() , false)
                        tv_price.text               = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble() , false)
                    }
                } else {
                    rlBaggagePrice.gone()
                    rlSsrPrice.gone()
                }
            }
        }
    }

    private fun totalPriceSsr(): String? {
        var totalPriceSsrItem  = 0.0
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataList.dataFlight.forEach {
            it.dataSSR.ssrSelected.forEach {
                try {
                    totalPriceSsrItem = totalPriceSsrItem + it.price.toDouble()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return totalPriceSsrItem.toString()
    }

    private fun totalPriceBaggage(): String? {
        var totalPriceBagaggeItem = 0.0
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataList.dataFlight.forEach {
            it.dataSSR.bagaggeItemSelected.forEach {
                try {
                    totalPriceBagaggeItem = totalPriceBagaggeItem + it.price.toDouble()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return totalPriceBagaggeItem.toString()
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
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_down))
        tv_including.visibility = View.GONE
        body_prize.expand()
        Globals.delay(210,object :Globals.DelayCallback{
            override fun done() {
                nested_view.fullScroll(View.FOCUS_DOWN)
            }
        })
    }

    private fun collapsePrize() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_chevron_up_orange))
        tv_including.visibility = View.VISIBLE
        body_prize.collapse()
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.BTN_SIM       -> {
                dataContacts[position].checktype = "SIM"
                gotoActivityResult(SimFormContactActivity::class.java,Constants.BTN_SIM)
            }
            Constants.BTN_PASSPORT  -> {
                dataContacts[position].checktype = "PASSPORT"
                gotoActivityResult(PassportFormActivity::class.java,Constants.BTN_PASSPORT)
            }
            Constants.BTN_ID_CART   -> {
                dataContacts[position].checktype = "KTP"
                gotoActivityResult(KtpCardFormActivity::class.java,Constants.BTN_ID_CART)
            }

            Constants.KEY_ACTIVITY_BAGAGE -> {
                gotoActivityResult(BagageActivity::class.java,Constants.KEY_ACTIVITY_BAGAGE)
            }
            Constants.KEY_ACTIVITY_SSR -> {
                gotoActivityResult(SsrActivity::class.java,Constants.KEY_ACTIVITY_SSR)
            }
            Constants.KEY_ACTIVITY_FREQUENCE -> {
                gotoActivityResult(FrequentFlyerActivity::class.java,Constants.KEY_ACTIVITY_FREQUENCE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constants.BTN_SIM       -> {
                if (requestCode==Activity.RESULT_OK){

                }
            }
            Constants.BTN_PASSPORT  -> {
                if (requestCode==Activity.RESULT_OK){

                }
            }
            Constants.BTN_ID_CART   -> {
                if (requestCode==Activity.RESULT_OK){

                }
            }
            Constants.KEY_ACTIVITY_BAGAGE ->{
                val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                setLog("testSaveBaruBaggage",Serializer.serialize(dataList.dataFlight[0].dataSSR.bagaggeItemSelected))
                totalPriceBaggage()
                initPrice()
                adapter.setData(dataContacts)

            }
            Constants.KEY_ACTIVITY_SSR ->{
                val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                setLog("testSaveBaruSsr",Serializer.serialize(dataList.dataFlight[0].dataSSR.ssrSelected))
                totalPriceSsr()
                initPrice()
                adapter.setData(dataContacts)
            }
        }
    }

    fun seatMapListener(view: View){
        /*getDataSeatMap()*/
        /*gotoActivityResult(SeatActivityFlight::class.java,Constants.GET_SEAT_MAP)*/
        gotoActivityResult(SelectSeatActivity::class.java,Constants.GET_SEAT_MAP)

    }

    override fun onClicked() {
        getReservased()
    }

    fun getReservased(){
        setLog("Test Reservasi",Serializer.serialize(getDataFlight()))
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getReservationFlight(Globals.getToken(),getDataFlight(),object : CallbackReserveFlight {
            override fun successLoad(data: ReserveFlightModel) {
                hideLoadingOpsicorp()
                Constants.ID_BOOKING_TEMPORARY = data.idTrip
                setLog("-----------##################---------------")
                setLog(Serializer.serialize(data))
                if(data.errorMessage.equals("")){
                    gotoActivity(NewCartActivity::class.java)
                } else {
                    showAllert("error",data.errorMessage)
                }
            }
            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert("error",message)
            }
        })
    }

    private fun getDataFlight(): HashMap<Any, Any> {
        val model = ReserveFlightRequest()
        model.dataBooking = getDataBooking()
        model.header      = getHeader()
        return Globals.classToHashMap(model,ReserveFlightRequest::class.java)
    }

    private fun getDataBooking(): DataBookingFlightRequest {
        val dataBooking   = DataBookingFlightRequest()
        val dataTrip      = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

        dataBooking.origin                  = dataTrip.originId
        dataBooking.destination             = dataTrip.destinationId
        dataBooking.segments                = getSegment()
        dataBooking.opsigoPassengers        = getPassanger()

        dataBooking.flightType              = dataFlight.flightType
        if (dataListFlight.dataFlight.size==1){
            dataBooking.flightTripType          = 0
        }
        else if (dataListFlight.dataFlight.size==2){
            dataBooking.flightTripType          = 1
        }
        else{
            dataBooking.flightTripType          = 2
        }

        dataBooking.contact                 = getContactValidationFlightRequest()
        dataBooking.remarks                 = ArrayList()
        dataBooking.members                 = getMembers()

        return dataBooking
    }

    private fun getMembers(): List<String> {
        val members = ArrayList<String>()
        members.add(getProfile().employId)
        return members
    }

    private fun getContactValidationFlightRequest(): ContactFlightRequest {
        val contact = ContactFlightRequest()
        contact.email     = getProfile().email
        contact.homePhone = getProfile().phone
        contact.firstName = getProfile().firstName
        contact.title     = getProfile().title
        contact.lastName  = getProfile().lastName
        contact.mobilePhone = getProfile().phone
        return contact
    }

    private fun getPassanger(): List<PassangersFlightRequest> {

        val model = ArrayList<PassangersFlightRequest>()
        model.clear()
        dataContacts.forEachIndexed { index, bookingContactAdapterModel ->
            val data = PassangersFlightRequest()

            data.identityType = bookingContactAdapterModel.checktype
            data.otherPhone   = getProfile().phone
            data.email        = getProfile().email
            data.firstName    = getProfile().firstName
            data.idNumber     = getProfile().idNumber
            data.employeeNik  = getProfile().employeeNik
            data.title        = getProfile().title
            data.index        = index+1
            data.mobilePhone  = getProfile().mobilePhone
            data.remarksPax   = ArrayList()
            data.jobTitleId   = getProfile().jobTitleId
            data.type         = Constants.TYPE_PASSANGGER_ADULT
            data.homePhone    = getProfile().homePhone
            data.paxType      = "ADT"
            data.lastName     = getProfile().lastName
            data.employeeId   = getProfile().employId
            data.companyCode  = getProfile().companyCode

            data.seats        = getSeat()
            data.depSsr       = getDepSsr()
            data.retSsr       = getRetSsr()

            var sDate =  getProfile().birthDate
            if(sDate.length > 10) {
                sDate = getProfile().birthDate.substring(0, 10)
            }
            data.birthDate      = sDate

            data.identityNumber = getProfile().identityType

            model.add(data)
        }
        return model
    }

    private fun getSeat(): ArrayList<SeatFlightRequest> {
        val listSeat = ArrayList<SeatFlightRequest>()
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        if (dataList.dataFlight.isNotEmpty()){
            dataList.dataFlight[0].dataSeat.dataSeat.forEach {
                val mData = SeatFlightRequest()
                mData.availability      = it.status
                mData.ccy               = it.ccy
                mData.flightNumber      = it.flightNumber
                mData.seatFare          = it.price.toDouble().toInt()
                mData.seatNumber        = it.numberSeat
                mData.seatType          = it.type
                mData.seatCode          = it.seatName
                mData.seatClass         = it.seatClass
                mData.seatClassCode     = it.seatClassCode
                mData.seatRowSet        = it.seatRow
                mData.posX              = it.x
                mData.posX              = it.y

                listSeat.add(mData)
            }
            if (dataList.dataFlight.size > 1){
                dataList.dataFlight[1].dataSeat.dataSeat.forEach {
                    val mData = SeatFlightRequest()
                    mData.availability      = it.status
                    mData.ccy               = it.ccy
                    mData.flightNumber      = it.flightNumber
                    mData.seatFare          = it.price.toDouble().toInt()
                    mData.seatNumber        = it.numberSeat
                    mData.seatType          = it.type
                    mData.seatCode          = it.seatName
                    mData.seatClass         = it.seatClass
                    mData.seatClassCode     = it.seatClassCode
                    mData.seatRowSet        = it.seatRow
                    mData.posX              = it.x
                    mData.posX              = it.y

                    listSeat.add(mData)
                }
            }
            return listSeat
        } else {
            return ArrayList()
        }
    }

    private fun getRetSsr(): ArrayList<BagageFlightRequest> {
        val listSsr = ArrayList<BagageFlightRequest>()
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        if (dataList.dataFlight.size > 1){
            dataList.dataFlight[1].dataSSR.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode           = it.ssrCode
                mData.originCode        = dataList.dataFlight[1].origin
                mData.destinationCode   = dataList.dataFlight[1].destination
                mData.ssrFare           = it.price.toDouble().toInt()
                mData.ccy               = it.curency
                mData.ssrName           = it.ssrName
                mData.ssrType           = it.ssrType.toInt()
                mData.flightNumber      = dataList.dataFlight[1].flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight[1].dataSSR.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode           = it.ssrCode
                mData.originCode        = dataList.dataFlight[1].origin
                mData.destinationCode   = dataList.dataFlight[1].destination
                mData.ssrFare           = it.price.toDouble().toInt()
                mData.ccy               = it.curency
                mData.ssrName           = it.ssrName
                mData.ssrType           = it.ssrType.toInt()
                mData.flightNumber      = dataList.dataFlight[1].flightNumber

                listSsr.add(mData)
            }
            return listSsr
        } else {
            return ArrayList()
        }

    }

    private fun getDepSsr(): ArrayList<BagageFlightRequest> {
        val listSsr = ArrayList<BagageFlightRequest>()
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        if (dataList.dataFlight.isNotEmpty()){
            dataList.dataFlight[0].dataSSR.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode           = it.ssrCode
                mData.originCode        = dataList.dataFlight[0].origin
                mData.destinationCode   = dataList.dataFlight[0].destination
                mData.ssrFare           = it.price.toDouble().toInt()
                mData.ccy               = it.curency
                mData.ssrName           = it.ssrName
                mData.ssrType           = it.ssrType.toInt()
                mData.flightNumber      = dataList.dataFlight[0].flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight[0].dataSSR.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode           = it.ssrCode
                mData.originCode        = dataList.dataFlight[0].origin
                mData.destinationCode   = dataList.dataFlight[0].destination
                mData.ssrFare           = it.price.toDouble().toInt()
                mData.ccy               = it.curency
                mData.ssrName           = it.ssrName
                mData.ssrType           = it.ssrType.toInt()
                mData.flightNumber      = dataList.dataFlight[0].flightNumber

                listSsr.add(mData)
            }

            return listSsr
        } else {
            return ArrayList()
        }

    }

    private fun getSegment(): List<SegmentFlightsRequest> {

        val listSegment = ArrayList<SegmentFlightsRequest>()

        dataListFlight.dataFlight.forEachIndexed { index, it ->
            val segment = SegmentFlightsRequest()

            segment.airline             =  it.airline
            segment.airlineImageUrl     =  it.imgAirline
            segment.airlineName         =  it.titleAirline

            segment.arrivalDate         =  it.arrivalDate
            segment.arriveDate          =  it.arriveDate
            segment.arriveDateTimeView  =  it.arriveDateTimeView
            segment.arriveTime          =  it.arriveTime

            segment.category            =  it.nameClass
            segment.classCode           =  it.classCode
            segment.classId             =  it.classId
            segment.fareBasisCode       =  it.code

            segment.departDate          =  it.departDate
            segment.departTime          =  it.departTime
            segment.departureDate       =  it.departureDate

            segment.duration            =  it.duration
            segment.durationIncludeTransit      =  it.durationIncludeTransit
            segment.durationIncludeTransitView  =  it.durationIncludeTransitView
            segment.fare            =  it.price
            segment.fareBasisCode   =  it.fareBasisCode
            segment.flightId        =  it.flightId
            segment.flightNumber    =  it.flightNumber
            segment.flightType      =  it.flightType
            segment.flightTypeView  =  it.flightTypeView

            segment.isAvailable     =  it.isAvailable
            segment.isComply        =  it.isComply
            segment.isConnecting    =  it.isConnecting
            segment.isMultiClass    =  it.isMultiClass
            segment.isHolderFlight  =  it.isHolderFlight

            //ulik sendiri ma kita
            segment.num         =  index.toString()
            segment.seq         =  index.toString()
            segment.sequence    =  it.sequence
            segment.number      =  it.number
            segment.origin      =  it.origin
            segment.destination =  it.destination
            segment.seat            =  it.numberSeat.toInt()
            segment.totalTransit    =  it.totalTransit
            segment.num         =  index.toString()

            listSegment.add(segment)
        }

        return listSegment
    }

    private fun getHeader(): HeaderReserveFlightRequest {

        val dataTrip      = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val header = HeaderReserveFlightRequest()
        header.startDate    = dataTrip.startDate
        header.returnDate   = dataTrip.endDate
        header.origin       = dataTrip.originId
        header.destination  = dataTrip.destinationId
        header.type         = 2
        header.tripParticipants     = tripParticipant()
        header.travelAgentAccount   = Globals.getConfigCompany(this).defaultTravelAgent
        header.idTripPlan   = dataTrip.idTripPlant
        header.codeTripPlan = dataTrip.tripCode
        header.purpose      = dataTrip.purpose

        return header
    }

    /*private fun dataDummyCreateTrip() {
        val data = SuccessCreateTripPlaneModel()
        data.purpose          = "Meeting"
        data.idTripPlant      = "91a7f32c-53b9-4ffb-9e0d-39768d95a586"
        data.status           = "Draft"
        data.tripCode         = "TP202003040001"
        data.createDate       = "2020-03-04 09:44:57"
        data.timeExpired      = "00:00:00"
        data.destinationName  = "BD"
        data.destinationId      = "BD"
        data.originId           = "GMR"
        data.originName       = "GMR"
        data.startDate        = "2020-03-12 00:00:00"
        data.endDate          = "2020-03-21 00:00:00"
        data.buggetId         = "cb2fa51e-efd1-4f66-86f2-d9171663b70d"
        data.costCenter       = "b4df3880-b1af-41af-89ce-a0d1b7eb5c3c"
        Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data,SuccessCreateTripPlaneModel::class.java)
    }*/

    private fun tripParticipant(): List<TripParticipantsItem> {
        val dataTrip      = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        val data = ArrayList<TripParticipantsItem>()
        val model = TripParticipantsItem()
        model.budgetId     = dataTrip.buggetId
        model.costCenterId = dataTrip.costCenter
        model.employeeId   = getProfile().employId
        data.add(model)
        return data
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }


}