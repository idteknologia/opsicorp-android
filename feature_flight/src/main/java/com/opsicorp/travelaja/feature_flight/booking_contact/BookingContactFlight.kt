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
import com.opsicorp.travelaja.feature_flight.R
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import android.content.Intent
import android.app.Activity
import android.view.View
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsicorp.travelaja.feature_flight.seat_map.SelectSeatActivity
import com.opsicorp.travelaja.feature_flight.ssr.FrequentFlyerActivity
import com.opsigo.travelaja.module.profile.KtpCardFormActivity
import com.opsigo.travelaja.module.profile.PassportFormActivity
import com.opsigo.travelaja.module.profile.SimFormContactActivity
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat.SeatFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr.BagageFlightRequest
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import java.lang.Exception

class BookingContactFlight : BaseActivity(), OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.booking_contact_view_flight
    }

    /*val dataContacts = ArrayList<BookingContactAdapterModel>()*/
    val resultSeat = ResultSeat()
    lateinit var dataListFlight: DataListOrderAccomodation
    val adapter by lazy { BookingContactFlightAdapter(this) }
    lateinit var dataOrder: OrderAccomodationModel
    var currentPosition = 0
    lateinit var dataFlight: ResultListFlightModel

    override fun OnMain() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

        initRecyclerView()
        setDataContact()
        initToolbar()

    }

    private fun initToolbar() {

        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        btn_next.setTextButton("Continue")

        val datedepar = DateConverter().setDateFormat3(dataOrder.dateDeparture)
        val datereturn = DateConverter().setDateFormat3(dataOrder.dateArrival)

        /*toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}"," ${datedepar} , ${datereturn}")*/ //- 1 pax
        toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}", " ${datedepar}") //- 1 pax

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
        mDataBooker.checktype = Constants.TYPE_KTP
        mDataBooker.sim = getSimDataBooker()
        mDataBooker.pasport = getPassportDataBooker()
        mDataBooker.idcard = getDataIdCartBooker()

        /*dataContacts.add(mDataBooker)*/
        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
          resultListFlightModel.passenger.add(mDataBooker)
        }


        for (i in 0 until dataOrder.infant) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.INFANT
            dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                resultListFlightModel.passenger.add(mData)
            }
        }
        if (dataOrder.adult > 1){
            for (i in 0 until dataOrder.adult -1) {
                val mData = BookingContactAdapterModel()
                mData.typeContact = Constants.ADULT
                dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                    resultListFlightModel.passenger.add(mData)
                }
            }
        }
        for (i in 0 until dataOrder.child) {
            val mData = BookingContactAdapterModel()
            mData.typeContact = Constants.CHILD
            dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                resultListFlightModel.passenger.add(mData)
            }
        }

        val dataProfile = getProfile()
        tv_name_contact.text = dataProfile.name
        tv_number_contact.text = dataProfile.homePhone
        tv_email_contact.text = dataProfile.email

        adapter.setData(dataListFlight.dataFlight[0].passenger)
        initPrice()
    }

    private fun getDataIdCartBooker(): IdCartModel {
        val model = IdCartModel()
        model.fullname = getProfile().name
        model.id = getProfile().employId
        model.idCart = getProfile().ktp
        return model
    }

    private fun getSimDataBooker(): SimModel {
        val model = SimModel()
        model.name = getProfile().name
        model.id = getProfile().employId
        model.idSim = getProfile().sim
        return model
    }

    private fun getPassportDataBooker(): PassportModel {
        val model = PassportModel()
        model.firstName = getProfile().firstName
        model.id = getProfile().employId
        model.passporNumber = getProfile().passport
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

    private fun updateDataListFlight(){
        dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
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

        if (Globals.typeAccomodation == Constants.FLIGHT) {


            tv_prize_departure.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
            tv_station_departure.text = "${dataListFlight.dataFlight[0].origin} - ${dataListFlight.dataFlight[0].destination}"
            tvTotalPassenger1.text = "${dataOrder.totalPassengerString} /Pax"


            if (dataListFlight.dataFlight.size > 1) {
                line_arrival.visibility = View.VISIBLE
                tv_prize_arrival.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[1].price * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
                tv_station_arrival.text = "${dataListFlight.dataFlight[1].origin} - ${dataListFlight.dataFlight[1].destination}"
                tvTotalPassenger2.text = "${dataOrder.totalPassengerString} /Pax"
                tv_price_total.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
                tv_price.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
            } else {
                line_arrival.visibility = View.GONE
                tv_price_total.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
                tv_price.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant), false)
            }
            dataListFlight.dataFlight.forEachIndexed { index, resultListFlightModel ->
                if (resultListFlightModel.passenger[0].ssr.bagaggeItemSelected.isNotEmpty() || resultListFlightModel.passenger[0].ssr.ssrSelected.isNotEmpty()) {
                    rlBaggagePrice.visible()
                    rlSsrPrice.visible()
                    tv_total_price_baggage.text = StringUtils().setCurrency("IDR", totalPriceBaggage()!!.toDouble(), false)
                    tv_total_price_ssr.text = StringUtils().setCurrency("IDR", totalPriceSsr()!!.toDouble(), false)
                    if (dataListFlight.dataFlight.size > 1) {
                        tv_price_total.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble(), false)
                        tv_price.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight[0].price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble(), false)
                    } else {
                        tv_price_total.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble(), false)
                        tv_price.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight[0].price * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage()!!.toDouble() + totalPriceSsr()!!.toDouble(), false)
                    }
                } else {
                    rlBaggagePrice.gone()
                    rlSsrPrice.gone()
                }
            }
        }
    }

    private fun totalPriceSsr(): String? {
        var totalPriceSsrItem = 0.0
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataList.dataFlight.forEach {
            it.passenger[currentPosition].ssr.ssrSelected.forEach {
                try {
                    totalPriceSsrItem = totalPriceSsrItem + it.price.toDouble()
                } catch (e: Exception) {
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
            it.passenger[currentPosition].ssr.bagaggeItemSelected.forEach {
                try {
                    totalPriceBagaggeItem = totalPriceBagaggeItem + it.price.toDouble()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return totalPriceBagaggeItem.toString()
    }

    fun showOrHideDetailPrize() {
        if (body_prize.isExpanded) {
            collapsePrize()
        } else {
            expandPrize()
        }
    }

    private fun expandPrize() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_down))
        tv_including.visibility = View.GONE
        body_prize.expand()
        Globals.delay(210, object : Globals.DelayCallback {
            override fun done() {
                nested_view.fullScroll(View.FOCUS_DOWN)
            }
        })
    }

    private fun collapsePrize() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_up_orange))
        tv_including.visibility = View.VISIBLE
        body_prize.collapse()
    }

    override fun onClick(views: Int, position: Int) {
        currentPosition = position
        when(views){
            Constants.BTN_SIM       -> {
                dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                    val bundle = Bundle()
                    bundle.putString(Constants.INPUT_EDIT_SIM,Serializer.serialize(resultListFlightModel.passenger[position].sim))
                    gotoActivityResultWithBundle(SimFormContactActivity::class.java,bundle,Constants.BTN_SIM)
                }
            }
            Constants.BTN_PASSPORT  -> {
                dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                    val bundle = Bundle()
                    bundle.putString(Constants.INPUT_EDIT_PASPORT,Serializer.serialize(resultListFlightModel.passenger[position].pasport))
                    gotoActivityResultWithBundle(PassportFormActivity::class.java,bundle,Constants.BTN_PASSPORT)
                }

            }
            Constants.BTN_ID_CART   -> {
                dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                    val bundle = Bundle()
                    bundle.putString(Constants.INPUT_EDIT_KTP,Serializer.serialize(resultListFlightModel.passenger[position].idcard))
                    gotoActivityResultWithBundle(KtpCardFormActivity::class.java,bundle,Constants.BTN_ID_CART)
                }

            }
            Constants.KEY_ACTIVITY_BAGAGE -> {
                val bundle = Bundle()
                bundle.putInt(Constants.KEY_POSITION_SELECT_PASSENGER,position)
                gotoActivityResultWithBundle(BagageActivity::class.java, bundle, Constants.KEY_ACTIVITY_BAGAGE)
            }
            Constants.KEY_ACTIVITY_SSR -> {
                val bundle = Bundle()
                bundle.putInt(Constants.KEY_POSITION_SELECT_PASSENGER,position)
                gotoActivityResultWithBundle(SsrActivity::class.java, bundle, Constants.KEY_ACTIVITY_SSR)
            }
            Constants.KEY_ACTIVITY_FREQUENCE -> {
                val bundle = Bundle()
                bundle.putInt(Constants.KEY_POSITION_SELECT_PASSENGER,position)
                gotoActivityResultWithBundle(FrequentFlyerActivity::class.java, bundle, Constants.KEY_ACTIVITY_FREQUENCE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.BTN_SIM       -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_SIM
                        }
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_SIM)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].sim = Serializer.deserialize(dataString,SimModel::class.java)
                        }
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
            Constants.BTN_PASSPORT  -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_PASSPORT
                        }
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_PASPORT)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].pasport = Serializer.deserialize(dataString,PassportModel::class.java)
                        }
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){}
                }
            }
            Constants.BTN_ID_CART   -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_KTP
                        }
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_KTP)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].idcard = Serializer.deserialize(dataString,IdCartModel::class.java)
                        }
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){}
                }
            }
            Constants.KEY_ACTIVITY_BAGAGE -> {
                val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                setLog("testSaveBaruBaggage", Serializer.serialize(dataList.dataFlight[0].passenger[currentPosition].ssr.bagaggeItemSelected))
                totalPriceBaggage()
                updateDataListFlight()
                initPrice()
                adapter.setData(dataListFlight.dataFlight[0].passenger)

            }
            Constants.KEY_ACTIVITY_SSR -> {
                val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                setLog("testSaveBaruSsr", Serializer.serialize(dataList.dataFlight[0].passenger[currentPosition].ssr.ssrSelected))
                totalPriceSsr()
                updateDataListFlight()
                initPrice()
                adapter.setData(dataListFlight.dataFlight[0].passenger)
            }
        }
    }

    fun seatMapListener(view: View) {
        gotoActivityResult(SelectSeatActivity::class.java, Constants.GET_SEAT_MAP)

    }

    override fun onClicked() {
        getReservased()
    }

    fun getReservased() {
        setLog("Test Reservasi", Serializer.serialize(getDataFlight()))
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getReservationFlight(Globals.getToken(), getDataFlight(), object : CallbackReserveFlight {
            override fun successLoad(data: ReserveFlightModel) {
                if (data.errorMessage.equals("")) {
                    goToNewCart(data.idTrip)
                } else {
                    showAllert("error", data.errorMessage)
                }
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert("error", message)
            }
        })
    }

    private fun goToNewCart(idTrip: String) {
        val bundle = Bundle()
        if (Constants.isBisnisTrip){
            bundle.putString(Constants.ID_TRIP_PLANE,idTrip)
            bundle.putString(Constants.FROM_CART,Constants.FROM_BISNIS_TRIP)
            gotoActivityWithBundle(NewCartActivity::class.java,bundle)
        }
        else {
            bundle.putString(Constants.ID_TRIP_PLANE,idTrip)
            bundle.putString(Constants.FROM_CART,Constants.FROM_PERSONAL_TRIP)
            gotoActivityWithBundle(NewCartActivity::class.java,bundle)
        }
        hideLoadingOpsicorp()
    }

    private fun getDataFlight(): HashMap<Any, Any> {
        val model = ReserveFlightRequest()
        model.dataBooking = getDataBooking()
        model.header = getHeader()
        return Globals.classToHashMap(model, ReserveFlightRequest::class.java)
    }

    private fun getDataBooking(): DataBookingFlightRequest {
        val dataBooking = DataBookingFlightRequest()
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        dataFlight = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

        dataBooking.origin = dataTrip.originId
        dataBooking.destination = dataTrip.destinationId
        dataBooking.segments = getSegment()
        dataBooking.opsigoPassengers = getPassanger()

        dataBooking.flightType = dataFlight.flightType
        if (dataListFlight.dataFlight.size == 1) {
            dataBooking.flightTripType = 0
        } else if (dataListFlight.dataFlight.size == 2) {
            dataBooking.flightTripType = 1
        } else {
            dataBooking.flightTripType = 2
        }

        dataBooking.contact = getContactValidationFlightRequest()
        dataBooking.remarks = ArrayList()
        dataBooking.members = getMembers()

        return dataBooking
    }

    private fun getMembers(): List<String> {
        val members = ArrayList<String>()
        members.add(getProfile().employId)
        return members
    }

    private fun getContactValidationFlightRequest(): ContactFlightRequest {
        val contact = ContactFlightRequest()
        contact.email = getProfile().email
        contact.homePhone = getProfile().phone
        contact.firstName = getProfile().firstName
        contact.title = getProfile().title
        contact.lastName = getProfile().lastName
        contact.mobilePhone = getProfile().phone
        return contact
    }

    private fun getPassanger(): List<PassangersFlightRequest> {
        val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        val model = ArrayList<PassangersFlightRequest>()
        model.clear()
        dataListFlight.dataFlight[0].passenger.forEachIndexed { index, bookingContactAdapterModel ->
            val data = PassangersFlightRequest()

            when(bookingContactAdapterModel.checktype){
                Constants.TYPE_SIM -> {
                    if (bookingContactAdapterModel.sim.name.contains(" ")){
                        data.firstName    = bookingContactAdapterModel.sim.name.split(" ")[0]
                        data.lastName     = bookingContactAdapterModel.sim.name.split(" ")[1]
                    }
                    else {
                        data.firstName    = bookingContactAdapterModel.sim.name
                        data.lastName     = bookingContactAdapterModel.sim.name
                    }
                    data.email        = getProfile().email
                    data.mobilePhone = getProfile().mobilePhone
                    data.homePhone   = getProfile().phone
                    data.birthDate   = getProfile().birthDate
                    data.title       = bookingContactAdapterModel.sim.title
                    data.idNumber    = bookingContactAdapterModel.sim.idSim
                    data.identityNumber = bookingContactAdapterModel.sim.idSim
                }
                Constants.TYPE_PASSPORT -> {
                    data.firstName    = bookingContactAdapterModel.pasport.firstName
                    data.lastName     = bookingContactAdapterModel.pasport.lastName
                    data.email        = getProfile().email
                    data.mobilePhone  = getProfile().mobilePhone
                    data.homePhone    = getProfile().phone
                    data.birthDate    = bookingContactAdapterModel.pasport.birtDate
                    data.title        = bookingContactAdapterModel.pasport.title
                    data.idNumber     =  bookingContactAdapterModel.pasport.passporNumber
                    data.identityNumber = bookingContactAdapterModel.pasport.passporNumber
                }
                Constants.TYPE_KTP -> {
                    if (bookingContactAdapterModel.sim.name.contains(" ")){
                        data.firstName    = bookingContactAdapterModel.idcard.fullname.split(" ")[0]
                        data.lastName     = bookingContactAdapterModel.idcard.fullname.split(" ")[1]
                    }
                    else {
                        data.firstName    = bookingContactAdapterModel.idcard.fullname
                        data.lastName     = bookingContactAdapterModel.idcard.fullname
                    }
                    data.email        = bookingContactAdapterModel.idcard.email
                    data.mobilePhone  = bookingContactAdapterModel.idcard.mobileNum
                    data.birthDate    = getProfile().birthDate
                    data.homePhone    = bookingContactAdapterModel.idcard.mobileNum
                    data.title        = bookingContactAdapterModel.idcard.title
                    data.idNumber     = bookingContactAdapterModel.idcard.idCart
                    data.identityNumber = bookingContactAdapterModel.idcard.idCart
                }
            }

            when(bookingContactAdapterModel.typeContact){
                Constants.ADULT -> {
                    data.type = "1"
                }
                Constants.INFANT -> {
                    data.type = "3"
                }
            }

            data.index        = dataOrder.totalPassenger
            data.identityType = bookingContactAdapterModel.checktype
            data.otherPhone   = getProfile().homePhone
            data.employeeNik  = getProfile().employeeNik
            data.remarksPax   = ArrayList()
            data.jobTitleId   = getProfile().jobTitleId
            data.employeeId   = getProfile().employId
            data.paxType      = "ADT"
            data.companyCode  = getProfile().companyCode

            data.seats = getSeat()
            data.depSsr = getDepSsr()
            data.retSsr = getRetSsr()


            model.add(data)
        }
        return model
    }

    private fun getSeat(): ArrayList<SeatFlightRequest> {
        val listSeat = ArrayList<SeatFlightRequest>()
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        if (dataList.dataFlight.isNotEmpty()) {
            dataList.dataFlight[0].dataSeat.dataSeat.forEach {
                val mData = SeatFlightRequest()
                mData.availability = it.status
                mData.ccy = it.ccy
                mData.flightNumber = it.flightNumber
                mData.seatFare = it.price.toDouble().toInt()
                mData.seatNumber = it.numberSeat
                mData.seatType = it.type
                mData.seatCode = it.seatName
                mData.seatClass = it.seatClass
                mData.seatClassCode = it.seatClassCode
                mData.seatRowSet = it.seatRow
                mData.posX = it.x
                mData.posX = it.y

                listSeat.add(mData)
            }
            if (dataList.dataFlight.size > 1) {
                dataList.dataFlight[1].dataSeat.dataSeat.forEach {
                    val mData = SeatFlightRequest()
                    mData.availability = it.status
                    mData.ccy = it.ccy
                    mData.flightNumber = it.flightNumber
                    mData.seatFare = it.price.toDouble().toInt()
                    mData.seatNumber = it.numberSeat
                    mData.seatType = it.type
                    mData.seatCode = it.seatName
                    mData.seatClass = it.seatClass
                    mData.seatClassCode = it.seatClassCode
                    mData.seatRowSet = it.seatRow
                    mData.posX = it.x
                    mData.posX = it.y

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
        if (dataList.dataFlight.size > 1) {
            dataList.dataFlight[1].passenger[1].ssr.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight[1].origin
                mData.destinationCode = dataList.dataFlight[1].destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight[1].flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight[1].passenger[1].ssr.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight[1].origin
                mData.destinationCode = dataList.dataFlight[1].destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight[1].flightNumber

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
        if (dataList.dataFlight.isNotEmpty()) {
            dataList.dataFlight[0].passenger[0].ssr.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight[0].origin
                mData.destinationCode = dataList.dataFlight[0].destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight[0].flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight[0].passenger[0].ssr.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight[0].origin
                mData.destinationCode = dataList.dataFlight[0].destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight[0].flightNumber

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

            segment.airline = it.airline
            segment.airlineImageUrl = it.imgAirline
            segment.airlineName = it.titleAirline

            segment.arrivalDate = it.arrivalDate
            segment.arriveDate = it.arriveDate
            segment.arriveDateTimeView = it.arriveDateTimeView
            segment.arriveTime = it.arriveTime

            segment.category = it.nameClass
            segment.classCode = it.classCode
            segment.classId = it.classId
            segment.fareBasisCode = it.code

            segment.departDate = it.departDate
            segment.departTime = it.departTime
            segment.departureDate = it.departureDate

            segment.duration = it.duration
            segment.durationIncludeTransit = it.durationIncludeTransit
            segment.durationIncludeTransitView = it.durationIncludeTransitView
            segment.fare = it.price
            segment.fareBasisCode = it.fareBasisCode
            segment.flightId = it.flightId
            segment.flightNumber = it.flightNumber
            segment.flightType = it.flightType
            segment.flightTypeView = it.flightTypeView

            segment.isAvailable = it.isAvailable
            segment.isComply = it.isComply
            segment.isConnecting = it.isConnecting
            segment.isMultiClass = it.isMultiClass
            segment.isHolderFlight = it.isHolderFlight

            //ulik sendiri ma kita
            segment.num = index.toString()
            segment.seq = index.toString()
            segment.sequence = it.sequence
            segment.number = it.number
            segment.origin = it.origin
            segment.destination = it.destination
            segment.seat = it.numberSeat.toInt()
            segment.totalTransit = it.totalTransit
            segment.num = index.toString()

            listSegment.add(segment)
        }

        return listSegment
    }

    private fun getHeader(): HeaderReserveFlightRequest {

        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val header = HeaderReserveFlightRequest()
        header.startDate = dataTrip.startDate
        header.returnDate = dataTrip.endDate
        header.origin = dataTrip.originId
        header.destination = dataTrip.destinationId
        header.type = 2
        header.tripParticipants = tripParticipant()
        header.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        header.idTripPlan = dataTrip.idTripPlane
        header.codeTripPlan = dataTrip.tripCode
        header.purpose = dataTrip.purpose
        if (dataTrip.purpose.equals("-")) {
            header.purpose = "Personal Trip"
        }

        return header
    }


    private fun tripParticipant(): List<TripParticipantsItem> {
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        val data = ArrayList<TripParticipantsItem>()
        val model = TripParticipantsItem()
        model.budgetId = dataTrip.buggetId
        model.costCenterId = dataTrip.costCenter
        model.employeeId = getProfile().employId
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