package com.opsicorp.travelaja.feature_flight.booking_contact

import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr.BagageFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat.SeatFlightRequest
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ContactFlightRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.*
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel
import opsigo.com.datalayer.request_model.reservation.TripParticipantsItem
import opsigo.com.domainlayer.model.accomodation.flight.ReserveFlightModel
import com.opsicorp.travelaja.feature_flight.seat_map.SelectSeatActivity
import com.opsicorp.travelaja.feature_flight.ssr.FrequentFlyerActivity
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel
import kotlinx.android.synthetic.main.booking_contact_view_flight.*
import com.mobile.travelaja.module.profile.SimFormContactActivity
import com.mobile.travelaja.module.cart.activity.NewCartActivity
import com.opsicorp.travelaja.feature_flight.ssr.BagageActivity
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import com.mobile.travelaja.module.profile.PassportFormActivity
import com.mobile.travelaja.module.profile.KtpCardFormActivity
import com.opsicorp.travelaja.feature_flight.ssr.SsrActivity
import opsigo.com.domainlayer.callback.CallbackReserveFlight
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.summary.PassportModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.opsicorp.travelaja.feature_flight.R
import opsigo.com.datalayer.mapper.Serializer
import androidx.core.content.ContextCompat
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.*
import android.content.Intent
import android.app.Activity
import java.lang.Exception
import android.view.View
import android.os.Bundle
import com.opsicorp.travelaja.feature_flight.adapter.TotalPriceAdapter
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel


class BookingContactFlight : BaseActivity(),
        OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.booking_contact_view_flight }

    lateinit var dataListFlight: DataListOrderAccomodation
    val adapter by lazy { BookingContactFlightAdapter(this) }
    lateinit var dataOrder: OrderAccomodationModel
    var currentPosition = 0
    val dataFligt = ArrayList<ResultListFlightModel>()
    val adapterPrice by lazy { TotalPriceAdapter(this) }
    var isInputKtp = false

    override fun OnMain() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        if (Constants.multitrip){
            dataListFlight = DataListOrderAccomodation()
            dataOrder.routes.forEach {
                dataListFlight.dataFlight.add(it.flightResult)
            }
        }
        else {
            dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        }

        initRecyclerView()
        setDataContact()
        initToolbar()
    }

    private fun initToolbar() {

        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        btn_next.setTextButton("Continue")

        val datedepar = DateConverter().setDateFormat3(dataOrder.dateDeparture)
        if (dataOrder.routes.isNotEmpty()){
            toolbar.setDoubleTitle("${dataOrder.routes.first().originName} - ${dataOrder.routes.last().destinationName}", " ${DateConverter().setDateFormat3(dataOrder.routes.first().dateDeparture)}") //- 1 pax
        } else {
            toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}", " ${datedepar}") //- 1 pax
        }

        toolbar.doubleTitleGravity(toolbar.START)

        if (Constants.DATA_SEAT_AIRLINE.isNotEmpty()) {
            line_select_seat_map.visible()
        } else {
            line_select_seat_map.gone()
        }
    }

    private fun setDataContact() {
        val dataProfile = getProfile()
        et_number_contact.setText(dataProfile.mobilePhone)
        tv_email_contact.text = dataProfile.email
        et_name_contact.setText(dataProfile.fullName)

        if (Constants.multitrip){
            adapter.setData(dataOrder.routes.last().flightResult.passenger)
        }
        else {
            adapter.setData(dataListFlight.dataFlight.last().passenger)
        }
        initPrice()
    }

    private fun initRecyclerView() {
        line_select_seat_map.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_booking_information.layoutManager = layoutManager
        rv_booking_information.itemAnimator = DefaultItemAnimator()
        rv_booking_information.adapter = adapter

        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        rv_detail_prize.layoutManager = lm
        rv_detail_prize.itemAnimator = DefaultItemAnimator()
        rv_detail_prize.adapter = adapterPrice

        adapter.setOnclickListener(this)
    }

    private fun updateDataListFlight(){
        if (Constants.multitrip){
            dataListFlight = DataListOrderAccomodation()
            dataOrder.routes.forEach {
                dataListFlight.dataFlight.add(it.flightResult)
            }
        }
        else {
            dataListFlight = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        }
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

        //total price
        if (Constants.multitrip){
            dataFligt.clear()
            dataOrder.routes.forEach {
                dataFligt.add(it.flightResult)
            }
            adapterPrice.setData(dataFligt)
            var totalPrice              = 0.0
            dataOrder.routes.forEach {
                totalPrice =  totalPrice+it.flightResult.price
            }
            val totalPricing = totalPrice * dataOrder.totalPassengerInteger
            tv_title_prize.text         = "${getString(R.string.total_price_for)} ${dataOrder.totalPassengerInteger} pax and ${dataFligt.size} item{s}"
            tv_price_total.text         = "IDR ${Globals.formatAmount(totalPricing)}"
            tv_price.text               = "IDR ${Globals.formatAmount(totalPricing)}"
        }
        else {
            dataFligt.clear()
            dataListFlight.dataFlight.forEach {
                dataFligt.add(it)
            }
            adapterPrice.setData(dataFligt)
            var totalPrice              = 0.0
            dataListFlight.dataFlight.forEach {
                totalPrice =  totalPrice+it.price
            }
            val totalPricing = totalPrice * dataOrder.totalPassengerInteger
            tv_title_prize.text         = "${getString(R.string.total_price_for)} ${dataOrder.totalPassengerInteger} pax"
            tv_price_total.text         = "IDR ${Globals.formatAmount((totalPricing+totalPriceBaggage().toDouble()))}"
            tv_price.text               = "IDR ${Globals.formatAmount((totalPricing+totalPriceBaggage().toDouble()))}"
        }

        //total baggage
        dataListFlight.dataFlight.forEachIndexed { index, resultListFlightModel ->
            if (resultListFlightModel.passenger.first().ssr.bagaggeItemSelected.isNotEmpty() || resultListFlightModel.passenger.first().ssr.ssrSelected.isNotEmpty()) {
                rlBaggagePrice.visible()
                rlSsrPrice.visible()
                tv_total_price_baggage.text = StringUtils().setCurrency("IDR", totalPriceBaggage().toDouble(), false)
                tv_total_price_ssr.text = StringUtils().setCurrency("IDR", totalPriceSsr().toDouble(), false)
                if (dataListFlight.dataFlight.size > 1) {
                    tv_price_total.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight.first().price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage().toDouble() + totalPriceSsr()!!.toDouble(), false)
                    tv_price.text = StringUtils().setCurrency("IDR", (dataListFlight.dataFlight.first().price + dataListFlight.dataFlight[1].price) * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage().toDouble() + totalPriceSsr()!!.toDouble(), false)
                } else {
                    tv_price_total.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight.first().price * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage().toDouble() + totalPriceSsr().toDouble(), false)
                    tv_price.text = StringUtils().setCurrency("IDR", dataListFlight.dataFlight.first().price * (dataOrder.adult + dataOrder.child + dataOrder.infant) + totalPriceBaggage().toDouble() + totalPriceSsr().toDouble(), false)
                }
            } else {
                rlBaggagePrice.gone()
                rlSsrPrice.gone()
            }
        }
    }

    private fun totalPriceSsr(): String {
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

    private fun totalPriceBaggage(): String {
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
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_SIM,Serializer.serialize(dataListFlight.dataFlight.first().passenger[position].sim))
                gotoActivityResultWithBundle(SimFormContactActivity::class.java,bundle,Constants.BTN_SIM)
            }
            Constants.BTN_PASSPORT  -> {
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_PASPORT,Serializer.serialize(dataListFlight.dataFlight.first().passenger[position].pasport))
                gotoActivityResultWithBundle(PassportFormActivity::class.java,bundle,Constants.BTN_PASSPORT)
            }
            Constants.BTN_ID_CART   -> {
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_KTP,Serializer.serialize(dataListFlight.dataFlight.first().passenger[position].idcard))
                gotoActivityResultWithBundle(KtpCardFormActivity::class.java,bundle,Constants.BTN_ID_CART)
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
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_SIM)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_SIM
                            resultListFlightModel.passenger[currentPosition].sim = Serializer.deserialize(dataString,SimModel::class.java)
                        }
                        isInputKtp = true
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            }
            Constants.BTN_PASSPORT  -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_PASPORT)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_PASSPORT
                            resultListFlightModel.passenger[currentPosition].pasport = Serializer.deserialize(dataString,PassportModel::class.java)
                        }
                        isInputKtp = true
                    }catch (e:Exception){}
                }
                adapter.notifyDataSetChanged()
            }
            Constants.BTN_ID_CART   -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_KTP)
                        dataListFlight.dataFlight.mapIndexed { index, resultListFlightModel ->
                            resultListFlightModel.passenger[currentPosition].checktype = Constants.TYPE_KTP
                            resultListFlightModel.passenger[currentPosition].idcard = Serializer.deserialize(dataString,IdCartModel::class.java)
                        }
                        isInputKtp = true
                    }catch (e:Exception){}
                }
                adapter.notifyDataSetChanged()
            }
            Constants.KEY_ACTIVITY_BAGAGE -> {
                totalPriceBaggage()
                updateDataListFlight()
                initPrice()
                adapter.setData(dataListFlight.dataFlight.first().passenger)
            }
            Constants.KEY_ACTIVITY_SSR -> {
                totalPriceSsr()
                updateDataListFlight()
                initPrice()
                adapter.setData(dataListFlight.dataFlight.first().passenger)
            }
        }
    }

    fun seatMapListener(view: View) {
        gotoActivityResult(SelectSeatActivity::class.java, Constants.GET_SEAT_MAP)
    }

    override fun onClicked() {
        if (bookingContactIsEmpty()||!isInputKtp){
            showAllert("Sorry",getString(R.string.booking_contact_not_empty))
        }
        else {
            if (getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM){
                when {
                    phoneContactIsEmpty() -> {
                        showAllert("Sorry",getString(R.string.phone_contact_not_empty))
                    }
                    bookingContactIsEmpty() -> {
                        showAllert("Sorry",getString(R.string.booking_contact_not_empty))
                    }
                    !isInputKtp -> {
                        showAllert("Sorry",getString(R.string.booking_contact_not_empty))
                    }
                    else -> {
                        getReservased()
                    }
                }
            }
            else {
                getReservased()
            }
        }
    }

    fun getReservased() {
        val firstName = et_name_contact.text.toString()
        if (firstName.isNotBlank() && firstName.isNotEmpty()){
            setLog("Test Reservasi",Serializer.serialize(getDataFlight()))
            showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getReservationFlight(Globals.getToken(), getDataFlight(), object : CallbackReserveFlight {
            override fun successLoad(data: ReserveFlightModel) {
                if (data.errorMessage.equals("")) {
                    goToNewCart(data.idTrip)
                } else {
                    showAllert("error", data.errorMessage)
                    hideLoadingOpsicorp()
                }
            }

            override fun failedLoad(message: String) {
                showAllert("error", message)
                hideLoadingOpsicorp()
            }
        })
        } else {
            Globals.showAlert(getString(com.mobile.travelaja.R.string.please), getString(com.mobile.travelaja.R.string.name_must_not_empty), this)
        }
    }

    private fun phoneContactIsEmpty(): Boolean {
        var isEmpty = false
        val bookingContact = dataListFlight.dataFlight.first().passenger
        bookingContact.forEach {
            when (it.checktype){
                Constants.TYPE_SIM -> {
                    if (it.sim.mobilePhone.isEmpty()||it.sim.mobilePhone=="-"){
                        isEmpty = true
                    }
                }
                Constants.TYPE_PASSPORT -> {
                    if (it.pasport.mobilePhone.isEmpty()||it.pasport.mobilePhone=="-"){
                        isEmpty = true
                    }
                }
                Constants.TYPE_KTP -> {
                    if (it.idcard.mobilePhone.isEmpty()||it.idcard.mobilePhone=="-"){
                        isEmpty = true
                    }
                }
            }
        }
        return isEmpty
    }

    private fun bookingContactIsEmpty(): Boolean {
        var isEmpty = false
        val bookingContact = dataListFlight.dataFlight.first().passenger
        bookingContact.forEach {
            if (it.checktype.isEmpty()){
                isEmpty = true
            }
        }
        return isEmpty
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
        if(getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM){
            val model = ReserveFlightMulticityRequest()
            model.dataBooking = getDataBooking()
            model.header = getHeaderMulticity()
            return Globals.classToHashMap(model, ReserveFlightMulticityRequest::class.java)
        }
        else {
            val model = ReserveFlightRequest()
            model.dataBooking = getDataBooking()
            model.header = getHeader()
            return Globals.classToHashMap(model, ReserveFlightRequest::class.java)
        }
    }

    private fun getHeaderMulticity(): HeaderReserveFlightMulticityRequest {
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val header = HeaderReserveFlightMulticityRequest()
        header.startDate = dataTrip.startDate
        header.returnDate = dataTrip.endDate
        header.origin = dataTrip.originId
        header.destination = dataTrip.destinationId
        header.trnNumber = dataTrip.trnNumber
        header.tripParticipants = tripParticipant()
        header.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        header.idTripPlan = dataTrip.idTripPlane
        header.codeTripPlan = dataTrip.tripCode
        header.purpose = dataTrip.purpose
        header.status = dataTrip.statusNumber
        header.isBookAfterApprove = dataTrip.isBookAfterApprove
        header.isPrivateTrip = dataTrip.isPrivateTrip
        header.budgetId = dataTrip.buggetId
        header.withPartner = dataTrip.isTripPartner
        header.partnerName = dataTrip.tripPartnerName

        header.businessTripType = dataTrip.businessTripType
        header.remark           = dataTrip.remark
        header.wbsNo            = dataTrip.wbsNo
        header.isDomestic       = dataTrip.isDomestik
        header.golper           = dataTrip.golper
        header.type = Constants.TypeHeader.personal


        if (dataTrip.purpose.equals("-")) {
            header.purpose = "Personal Trip"
        }
        if (Constants.multitrip){
            dataTrip.route.forEach {
                header.routes.add(addRoute(it))
            }
        }
        dataTrip.attachment.forEach {
            header.attachments.add((addAttactment(it)))
        }
        return header
    }

    private fun getDataBooking(): DataBookingFlightRequest {
        val dataBooking = DataBookingFlightRequest()
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        dataBooking.origin = dataTrip.originId
        dataBooking.destination = dataTrip.destinationId
        dataBooking.segments = getSegment()
        dataBooking.opsigoPassengers = getPassanger()

        dataBooking.flightType = dataListFlight.dataFlight.first().flightType
        if (dataListFlight.dataFlight.size == 1) {
            dataBooking.flightTripType = 0
        } else if (dataListFlight.dataFlight.size > 1) {
            if(Constants.multitrip){
                dataBooking.flightTripType = 3
            }
            else {
                dataBooking.flightTripType = 1
            }
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
        val fullname = et_name_contact.text.toString()
        val names = fullname.split(" ")
        var lastName = ""
        if (names.size > 1){
            for (i in 1 until names.size){
                val n = names[i]
                if (n.isNotBlank()){
                    lastName = lastName.plus("$n ")
                }
            }
        }

        val contact = ContactFlightRequest()
        contact.email = getProfile().email
        contact.homePhone = "${etKodeArea.text.toString().replace("+","")}${et_number_contact.text}"
        contact.firstName = names.first()
        contact.title = getProfile().title
        contact.lastName = lastName
        contact.mobilePhone = "${etKodeArea.text.toString().replace("+","")}${et_number_contact.text}"
        return contact
    }

    private fun getPassanger(): List<PassangersFlightRequest> {
        val model = ArrayList<PassangersFlightRequest>()
        model.clear()
        dataListFlight.dataFlight.first().passenger.forEachIndexed { index, bookingContactAdapterModel ->
            val data = PassangersFlightRequest()

            when(bookingContactAdapterModel.checktype){
                Constants.TYPE_SIM -> {
                    if (bookingContactAdapterModel.sim.name.contains(" ")){
                        data.firstName    = bookingContactAdapterModel.sim.name.split(" ").first()
                        data.lastName     = bookingContactAdapterModel.sim.name.split(" ").last()
                    }
                    else {
                        data.firstName    = bookingContactAdapterModel.sim.name
                        data.lastName     = bookingContactAdapterModel.sim.name
                    }
                    data.email          = bookingContactAdapterModel.sim.email
                    data.mobilePhone    = bookingContactAdapterModel.sim.mobilePhone
                    data.homePhone      = bookingContactAdapterModel.sim.mobilePhone
                    data.birthDate      = bookingContactAdapterModel.sim.birthDate
                    data.title          = bookingContactAdapterModel.sim.title
                    data.idNumber       = bookingContactAdapterModel.sim.idSim
                    data.identityNumber = bookingContactAdapterModel.sim.idSim
                    data.otherPhone     = if (getProfile().homePhone=="-"||getProfile().homePhone.isEmpty()) bookingContactAdapterModel.sim.mobilePhone else getProfile().homePhone
                }
                Constants.TYPE_PASSPORT -> {
                    if (bookingContactAdapterModel.pasport.fullname.contains(" ")){
                        data.firstName    = bookingContactAdapterModel.pasport.fullname.split(" ").first()
                        data.lastName     = bookingContactAdapterModel.pasport.fullname.split(" ").last()
                    }
                    else {
                        data.firstName    = bookingContactAdapterModel.pasport.fullname
                        data.lastName     = bookingContactAdapterModel.pasport.fullname
                    }
                    data.email          = bookingContactAdapterModel.pasport.email
                    data.mobilePhone    = bookingContactAdapterModel.pasport.mobilePhone
                    data.homePhone      = bookingContactAdapterModel.pasport.mobilePhone
                    data.birthDate      = bookingContactAdapterModel.pasport.birtDate
                    data.title          = bookingContactAdapterModel.pasport.title
                    data.idNumber       = bookingContactAdapterModel.pasport.passporNumber
                    data.identityNumber = bookingContactAdapterModel.pasport.passporNumber
                    data.otherPhone   = if (getProfile().homePhone=="-"||getProfile().homePhone.isEmpty()) bookingContactAdapterModel.pasport.mobilePhone else getProfile().homePhone
                }
                Constants.TYPE_KTP -> {
                    if (bookingContactAdapterModel.idcard.fullname.contains(" ")){
                        data.firstName    = bookingContactAdapterModel.idcard.fullname.split(" ").first()
                        data.lastName     = bookingContactAdapterModel.idcard.fullname.split(" ").last()
                    }
                    else {
                        data.firstName    = bookingContactAdapterModel.idcard.fullname
                        data.lastName     = bookingContactAdapterModel.idcard.fullname
                    }
                    data.email          = bookingContactAdapterModel.idcard.email
                    data.mobilePhone    = bookingContactAdapterModel.idcard.mobilePhone
                    data.homePhone      = bookingContactAdapterModel.idcard.mobilePhone
                    data.birthDate      = bookingContactAdapterModel.idcard.birthDate
                    data.title          = bookingContactAdapterModel.idcard.title
                    data.idNumber       = bookingContactAdapterModel.idcard.idCart
                    data.identityNumber = bookingContactAdapterModel.idcard.idCart
                    data.otherPhone     = if (getProfile().homePhone=="-"||getProfile().homePhone.isEmpty()) bookingContactAdapterModel.idcard.mobilePhone else getProfile().homePhone
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

            data.index        = index+1
            data.identityType = bookingContactAdapterModel.checktype
            data.employeeNik  = getProfile().employeeNik
            data.remarksPax   = ArrayList()
            data.jobTitleId   = getProfile().jobTitleId
            data.employeeId   = getProfile().employId
            data.paxType      = "ADT"
            data.companyCode  = getProfile().companyCode
            data.seats  = getSeat()
            data.depSsr = getDepSsr()
            data.retSsr = getRetSsr()

            model.add(data)
        }
        return model
    }

    private fun getSeat(): ArrayList<SeatFlightRequest> {
        val listSeat = ArrayList<SeatFlightRequest>()
        val dataList = dataListFlight
        if (dataList.dataFlight.isNotEmpty()) {
            dataList.dataFlight.first().dataSeat.dataSeat.forEach {
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
                dataList.dataFlight.last().dataSeat.dataSeat.forEach {
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
        val dataList = dataListFlight
//        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        if (dataList.dataFlight.size > 1) {
            dataList.dataFlight.last().passenger.last().ssr.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight.last().origin
                mData.destinationCode = dataList.dataFlight.last().destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight.last().flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight.last().passenger.last().ssr.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight.last().origin
                mData.destinationCode = dataList.dataFlight.last().destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight.last().flightNumber

                listSsr.add(mData)
            }
            return listSsr
        } else {
            return ArrayList()
        }

    }

    private fun getDepSsr(): ArrayList<BagageFlightRequest> {
        val listSsr = ArrayList<BagageFlightRequest>()
        val dataList = dataListFlight

        if (dataList.dataFlight.isNotEmpty()) {
            dataList.dataFlight.first().passenger.first().ssr.ssrSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight.first().origin
                mData.destinationCode = dataList.dataFlight.first().destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight.first().flightNumber

                listSsr.add(mData)
            }
            dataList.dataFlight.first().passenger.first().ssr.bagaggeItemSelected.forEach {
                val mData = BagageFlightRequest()
                mData.ssrCode = it.ssrCode
                mData.originCode = dataList.dataFlight.first().origin
                mData.destinationCode = dataList.dataFlight.first().destination
                mData.ssrFare = it.price.toDouble().toInt()
                mData.ccy = it.curency
                mData.ssrName = it.ssrName
                mData.ssrType = it.ssrType.toInt()
                mData.flightNumber = dataList.dataFlight.first().flightNumber

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
        header.status = dataTrip.statusNumber
        header.isBookAfterApprove = dataTrip.isBookAfterApprove
        header.isPrivateTrip = dataTrip.isPrivateTrip
        header.trnNumber = dataTrip.trnNumber
        header.origin = dataTrip.originId
        header.destination = dataTrip.destinationId
        header.tripParticipants = tripParticipant()
        header.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        header.idTripPlan = dataTrip.idTripPlane
        header.codeTripPlan = dataTrip.tripCode
        header.purpose = dataTrip.purpose
        header.type = Constants.TypeHeader.personal
        header.remark = dataTrip.remark
        header.businessTripType = dataTrip.businessTripType
        header.wbsNo = dataTrip.wbsNo
        header.isDomestic = dataTrip.isDomestik
        header.golper = dataTrip.golper
        header.budgetId = dataTrip.buggetId

        if (dataTrip.purpose.equals("-")) {
            header.purpose = "Personal Trip"
        }
        if (Constants.multitrip){
            dataTrip.route.forEach {
                header.routes.add(addRoute(it))
            }
        }
        return header
    }

    private fun addAttactment(tripAttachmentItemModel: TripAttachmentItemModel): TripAttachmentsItemRequest {
        val dataAttactment = TripAttachmentsItemRequest()
        dataAttactment.description = tripAttachmentItemModel.description
        dataAttactment.url         = tripAttachmentItemModel.url
        return dataAttactment
    }

    private fun addRoute(routeMultiCityModel: RouteMultiCityModel): RoutestRequest {
        val data = RoutestRequest()
        data.DepartureDateView = DateConverter().getDate(routeMultiCityModel.dateDeparture,"yyyy-MM-dd","dd-MM-yyyy")
        data.departureDate     = routeMultiCityModel.dateDeparture
        data.destination       = routeMultiCityModel.destinationName
        data.origin            = routeMultiCityModel.originName
        data.transportation    = 1
        return data
    }


    private fun tripParticipant(): List<TripParticipantsItem> {
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        val data = ArrayList<TripParticipantsItem>()
        val model = TripParticipantsItem()
        model.budgetId = if (dataTrip.buggetId.isEmpty()||dataTrip.buggetId=="null") null else dataTrip.buggetId
        model.costCenterId = if (dataTrip.costCenter.isEmpty()||dataTrip.costCenter=="null") null else dataTrip.costCenter
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