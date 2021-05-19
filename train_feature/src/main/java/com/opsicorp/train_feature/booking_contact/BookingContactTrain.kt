package com.opsicorp.train_feature.booking_contact


import opsigo.com.datalayer.request_model.accomodation.train.validation.ContactValidationTrainRequest
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.domainlayer.model.accomodation.train.ReservationTrainModel
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.request_model.reservation.TripParticipantsItem
import opsigo.com.datalayer.request_model.accomodation.train.reservation.*
import kotlinx.android.synthetic.main.booking_contact_train_view.*
import com.opsigo.travelaja.module.profile.SimFormContactActivity
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import com.opsigo.travelaja.module.profile.PassportFormActivity
import opsigo.com.domainlayer.callback.CallbackReservationTrain
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import com.opsigo.travelaja.module.profile.KtpCardFormActivity
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.summary.PassportModel
import com.opsigo.travelaja.utility.DateConverter
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import com.opsicorp.train_feature.R
import android.content.Intent
import android.app.Activity
import java.lang.Exception
import android.os.Bundle
import android.view.View
import android.os.Build

class BookingContactTrain : BaseActivity(),OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.booking_contact_train_view }

    val dataContacts = ArrayList<BookingContactAdapterModel>()
    lateinit var dataOrder: OrderAccomodationModel
    val adapter by lazy { BookingContactTrainAdapter(this,dataContacts) }
    var currentPosition = 0

    override fun OnMain() {
        initRecyclerView()
        initPrize()
        initToolbar()
        setDataContact()
    }

    private fun initToolbar() {
        dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        val dateDeparture = if (dataOrder.dateDeparture.contains(" ")) dataOrder.dateDeparture.split(" ")[0] else dataOrder.dateDeparture
        toolbar.setDoubleTitle("${dataOrder.originStationName} - ${dataOrder.destinationStationName}","${DateConverter().getDate(dateDeparture,"yyyy-MM-dd","EEE, dd MMM |")} ${dataOrder.totalPassengerString} adult") //- 1 pax , ${dataOrder.dateArrival}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
    }

    private fun setDataContact() {

        val mDataBooker = BookingContactAdapterModel()
        mDataBooker.typeContact = Constants.ADULT
        mDataBooker.sim         = getSimDataBooker()
        mDataBooker.pasport     = getPassportDataBooker()
        mDataBooker.idcard      = getDataIdCartBooker()
        mDataBooker.checktype   = Constants.TYPE_KTP
        dataContacts.add(mDataBooker)

        if(dataOrder.adult>1){
            for (i in 0 until dataOrder.adult-1){
                val mDataAdult = BookingContactAdapterModel()
                mDataAdult.typeContact = Constants.ADULT
                dataContacts.add(mDataAdult)
            }
        }
        if(dataOrder.infant>0){
            for (i in 0 until dataOrder.infant){
                val mDataInfant = BookingContactAdapterModel()
                mDataInfant.typeContact = Constants.INFANT
                dataContacts.add(mDataInfant)
            }
        }
        if(dataOrder.child>0){
            for (i in 0 until dataOrder.child){
                val mDataChild = BookingContactAdapterModel()
                mDataChild.typeContact = Constants.CHILD
                dataContacts.add(mDataChild)
            }
        }

        adapter.notifyDataSetChanged()
        contactInformationView()
    }

    private fun contactInformationView() {
        val dataProfile             = getProfile()
        tv_number_contact.text      = formatNumberListener(dataProfile.homePhone)
        tv_email_contact.text       = dataProfile.email
        tv_name_contact.text        = dataProfile.name
    }

    private fun formatNumberListener(string: String): String {
        if (string.substring(0,1)=="0"){
            return string.substring(1)
        }
        else {
            return string
        }
    }

    private fun getDataIdCartBooker(): IdCartModel {
        val model           = IdCartModel()
        model.id            = getProfile().employId
        model.title         = getProfile().title
        model.fullname      = getProfile().name
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
        model.name        = getProfile().name
        model.email       = getProfile().email
        model.birthDate   = getProfile().birthDate
        model.mobilePhone = getProfile().mobilePhone
        return model
    }

    private fun getPassportDataBooker(): PassportModel {
        val model = PassportModel()
        model.passporNumber = getProfile().passport
        model.id            = getProfile().employId
        model.fullname      = getProfile().name
        model.title         = getProfile().title
        model.birtDate      = getProfile().birthDate
        model.nasionality   = getProfile().nationality
        model.mobilePhone   = getProfile().mobilePhone

        return model
    }

    private fun initRecyclerView() {
        line_select_seat_map.visibility = View.GONE
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_booking_information.layoutManager = layoutManager
        rv_booking_information.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_booking_information.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initPrize() {
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

        if (Globals.typeAccomodation=="Train"){
            val dataListTrain = Serializer.deserialize(Constants.DATA_LIST_TRAIN, DataListOrderAccomodation::class.java)

            tv_prize_departure.text   = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)
            tv_station_departure.text = dataListTrain.dataTrain[0].titleTrain

            if (dataListTrain.dataTrain.size>1){
                line_arrival.visibility = View.VISIBLE
                tv_prize_arrival.text   = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[1].price)
                tv_station_arrival.text = dataListTrain.dataTrain[1].titleTrain
                tv_price_total.text     = "IDR "+Globals.formatAmount((dataListTrain.dataTrain[0].price.toInt()+dataListTrain.dataTrain[1].price.toInt()).toString())
                tv_price.text           = "IDR "+Globals.formatAmount((dataListTrain.dataTrain[0].price.toInt()+dataListTrain.dataTrain[1].price.toInt()).toString())
            }
            else{
                line_arrival.visibility = View.GONE
                tv_price_total.text         = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)
                tv_price.text         = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)
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
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down_green))
        tv_including.visibility = View.GONE
        body_prize.expand()
        Globals.delay(210,object :Globals.DelayCallback{
            override fun done() {
                nested_view.fullScroll(View.FOCUS_DOWN)
            }
        })
    }

    private fun collapsePrize() {
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_green))
        tv_including.visibility = View.VISIBLE
        body_prize.collapse()
    }

    override fun onClick(views: Int, position: Int) {
        currentPosition = position
        when(views){
            Constants.BTN_SIM       -> {
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_SIM,Serializer.serialize(dataContacts[position].sim))
                gotoActivityResultWithBundle(SimFormContactActivity::class.java,bundle,Constants.BTN_SIM)
            }
            Constants.BTN_PASSPORT  -> {
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_PASPORT,Serializer.serialize(dataContacts[position].pasport))
                gotoActivityResultWithBundle(PassportFormActivity::class.java,bundle,Constants.BTN_PASSPORT)
            }
            Constants.BTN_ID_CART   -> {
                val bundle = Bundle()
                bundle.putString(Constants.INPUT_EDIT_KTP,Serializer.serialize(dataContacts[position].idcard))
                gotoActivityResultWithBundle(KtpCardFormActivity::class.java,bundle,Constants.BTN_ID_CART)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constants.BTN_SIM       -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataContacts[currentPosition].checktype = Constants.TYPE_SIM
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_SIM)
                        dataContacts[currentPosition].sim = Serializer.deserialize(dataString,SimModel::class.java)
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
            Constants.BTN_PASSPORT  -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataContacts[currentPosition].checktype = Constants.TYPE_PASSPORT
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_PASPORT)
                        dataContacts[currentPosition].pasport = Serializer.deserialize(dataString,PassportModel::class.java)
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){}
                }
            }
            Constants.BTN_ID_CART   -> {
                if (resultCode==Activity.RESULT_OK){
                    try {
                        dataContacts[currentPosition].checktype = Constants.TYPE_KTP
                        val dataString = data?.getStringExtra(Constants.RESULT_EDIT_KTP)
                        dataContacts[currentPosition].idcard = Serializer.deserialize(dataString,IdCartModel::class.java)
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){}
                }
            }
        }
    }

    fun seatMapListener(view: View){
//        getReservased()
    }

    override fun onClicked() {
        getReservased()
    }

    fun getReservased(){
        setLog(Serializer.serialize(getPassanger()))
        if (bookingContactIsEmpty()){
            showAllert("Sorry",getString(R.string.booking_contact_not_empty))
        }
        else {
            showLoadingOpsicorp(true)
            GetDataAccomodation(getBaseUrl()).getReservationTrain(Globals.getToken(),getDataTrain(),object : CallbackReservationTrain {
                override fun successLoad(data: ReservationTrainModel) {
                    gotoNewCart(data.idTrip)
                }

                override fun failedLoad(message: String) {
                    showAllert("Sorry",message)
                    hideLoadingOpsicorp()
                }
            })
        }

    }

    private fun bookingContactIsEmpty(): Boolean {
        var isEmpty = false
        dataContacts.forEach {
            if (it.checktype.isEmpty()){
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun gotoNewCart(idTrip: String) {
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

    private fun getDataTrain(): HashMap<Any, Any> {

        val model = ReservationTrainRequest()
        model.dataBooking = getDataBooking()
        model.header      = getHeader()

        return Globals.classToHashMap(model,ReservationTrainRequest::class.java)
    }

    private fun getDataBooking(): DataBookingReservationTrainRequest {
        val dataBooking   = DataBookingReservationTrainRequest()
        val dataTrip      = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        val dataListTrain = Serializer.deserialize(Constants.DATA_LIST_TRAIN, DataListOrderAccomodation::class.java)
        val dataOrder     = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)

        dataBooking.origin                  = dataTrip.originId
        dataBooking.destination             = dataTrip.destinationId
        dataBooking.segments                = getSegment(dataListTrain)
        dataBooking.opsigoPassengers        = getPassanger()
        if (dataListTrain.dataTrain.filter { it.notComply }.isNotEmpty()){
            dataBooking.reasonCode          = dataOrder.reasonCode
        }
        else {
            dataBooking.reasonCode = ""
        }

        if (Constants.tipeTrip == "round_trip"){
            dataBooking.trainType               = "1"
            dataBooking.flightType              = "1"
        }
        else{

            dataBooking.trainType               = "0"
            dataBooking.flightType              = "0"
        }
        dataBooking.contact                 = getContactValidationTrainRequest()
        dataBooking.remarks                 = ArrayList()

        dataBooking.members                 = getMembers()

        return dataBooking
    }

    private fun getMembers(): List<String> {
        val members = ArrayList<String>()
        members.add(getProfile().employId)
        return members
    }

    private fun getContactValidationTrainRequest(): ContactValidationTrainRequest {
        val contact = ContactValidationTrainRequest()
        contact.email     = getProfile().email
        contact.homePhone = getProfile().phone
        contact.firstName = getProfile().firstName
        contact.title     = getProfile().title
        contact.lastName  = getProfile().lastName
        contact.mobilePhone = getProfile().phone
        return contact
    }

    private fun getPassanger(): List<OpsigoPassengersItem> {

        val model = ArrayList<OpsigoPassengersItem>()
        model.clear()
        dataContacts.forEachIndexed { index, bookingContactAdapterModel ->
            val data = OpsigoPassengersItem()

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
                }
            }

            when(bookingContactAdapterModel.typeContact){
                Constants.ADULT -> {
                    data.type = 1
                }
                Constants.INFANT -> {
                    data.type = 3
                }
            }

            data.identityType = bookingContactAdapterModel.checktype
            data.otherPhone   = getProfile().homePhone
            data.index        = index+1
            data.employeeNik  = getProfile().employeeNik
            data.remarksPax   = ArrayList()
            data.jobTitleId   = getProfile().jobTitleId
            data.employeeId   = getProfile().employId
            data.paxType      = "ADT"
            data.companyCode  = getProfile().companyCode

            model.add(data)
        }
        return model
    }

    private fun getSegment(dataListTrain: DataListOrderAccomodation): List<SegmentsItemReservationTrainRequest> {

        val listSegment = ArrayList<SegmentsItemReservationTrainRequest>()
        listSegment.clear()

        dataListTrain.dataTrain.forEachIndexed { index, it ->
            val segment = SegmentsItemReservationTrainRequest ()
            segment.origin      =  it.origin
            segment.subClass    =  it.subClass
            segment.destination =  it.destination
            segment.arriveTime  =  it.timeArrifal
            segment.arriveDate  =  it.dateArrivalString
            segment.departDate  =  it.dateDepartureString
            segment.departTime  =  it.timeDeparture
            segment.trainName   =  it.trainName
            segment.num         =  index.toString()
            segment.classKey    =  it.classKey
            segment.fareBaseCode = it.fareBaseCode
            segment.clas        =  it.className
            segment.journeyCode =  it.journeyCode
            segment.carrierNumber           = it.carrierNumber
            segment.causeViolatedTrainRules = it.causeViolatedTrainRules
            segment.isSecuritySensitivity   = it.isSecuritySensitivity
            segment.descSecuritySensitivity = it.descSecuritySensitivity
            segment.isViolatedTrainRules    = it.isViolatedTrainRules.toString()

            listSegment.add(segment)
        }

        return listSegment
    }

    private fun getHeader(): HeaderReservationTrainRequest {

        val dataTrip      = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        val header = HeaderReservationTrainRequest()
        header.startDate = dataTrip.startDate
        header.returnDate = dataTrip.endDate
        header.origin = dataTrip.originId
        header.destination = dataTrip.destinationId
        header.type = 2
        header.tripParticipants= tripParticipant()
        header.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        header.idTripPlan = dataTrip.idTripPlane
        header.codeTripPlan  = dataTrip.tripCode
        header.purpose = dataTrip.purpose

        return header
    }

    private fun dataDummyCreateTrip() {
        val data = SuccessCreateTripPlaneModel()

        data.purpose          = "Meeting"
        data.idTripPlane      = "91a7f32c-53b9-4ffb-9e0d-39768d95a586"
        data.status           = "Draft"
        data.tripCode         = "TP202003040001"
        data.createDate       = "2020-03-04 09:44:57"
        data.timeExpired      = "00:00:00"
        data.destinationName  = "BD"
        data.destinationId    = "BD"
        data.originId         = "GMR"
        data.originName       = "GMR"
        data.startDate        = "2020-03-12 00:00:00"
        data.endDate          = "2020-03-21 00:00:00"
        data.buggetId         = "cb2fa51e-efd1-4f66-86f2-d9171663b70d"
        data.costCenter       = "b4df3880-b1af-41af-89ce-a0d1b7eb5c3c"

        Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data,SuccessCreateTripPlaneModel::class.java)
    }

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