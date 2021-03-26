package com.opsicorp.hotel_feature.booking_contact

import java.util.*
import android.os.Build
import android.view.View
import android.os.Bundle
import java.lang.Exception
import android.app.Activity
import android.content.Intent
import kotlin.collections.List
import android.widget.CheckBox
import kotlin.collections.ArrayList
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.hotel.*
import opsigo.com.domainlayer.model.summary.PassportModel
import opsigo.com.domainlayer.callback.CallbackBookingHotel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import kotlinx.android.synthetic.main.booking_contact_view_hotel.*
import opsigo.com.datalayer.request_model.accomodation.hotel.booking.*
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.Constants.KEY_NAME_GUEST
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

class BookingContactHotel : BaseActivity(),OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.booking_contact_view_hotel }

    val dataContacts = ArrayList<BookingContactAdapterModel>()
//    val adapter by lazy { BookingContactAdapter(this,dataContacts) }

    lateinit var dataHotel: ResultListHotelModel
    lateinit var dataRoom: SelectRoomModel
    lateinit var dataValidation: ValidationHotelModel
    lateinit var dataConfirmation: ConfirmationHotelModel
    lateinit var dataTrip: SuccessCreateTripPlaneModel
    val BED_TYPE_REQUEST    = 1089
    val OTHER_TYPE_REQUEST  = 1088
    val KEY_REQUEST         = "type"
    val KEY_VALUE           = "value"
    var remark              = ArrayList<RemarkBookingModel>()

    override fun OnMain() {
        initDataOrder()
        initToolbar()
//        initRecyclerView()
        initOnClickListener()
        setDataContact()
        initPrize()
    }

    private fun initOnClickListener() {
        line_cb_1.setOnClickListener { cbCheckedListener(cb1,1) }
        line_cb_3.setOnClickListener { cbCheckedListener(cb3,3) }
        line_cb_4.setOnClickListener { cbCheckedListener(cb4,4) }

        line_cb_2.setOnClickListener { bedTypeListener() }
        line_cb_5.setOnClickListener { otherTypeListener() }
        btn_cb5.setOnClickListener { otherTypeListener() }
        btn_cb2.setOnClickListener { bedTypeListener() }
        cb_guest.setOnClickListener {
            if (cb_guest.isChecked){
                setGuestName()
            }
            else {
                emptyGuestName()
            }
        }
    }

    private fun emptyGuestName() {
        et_guest.setText("")
        et_guest.isEnabled = true
    }

    private fun setGuestName() {
        et_guest.isEnabled = false
        et_guest.setText(getProfile().name)
    }

    private fun otherTypeListener() {
        if (cb5.isChecked==false){
            val bundle = Bundle()
            bundle.putInt(KEY_REQUEST,OTHER_TYPE_REQUEST)
            if (et_guest.text.toString().isNotEmpty()) bundle.putString(KEY_NAME_GUEST,et_guest.text.toString())
            else bundle.putString(KEY_NAME_GUEST,getProfile().name)
            gotoActivityResultWithBundle(SpecialRequestActivity::class.java,bundle,OTHER_TYPE_REQUEST)
        }else {
            cb5.isChecked = false
            deletRemark(5)
        }
    }

    private fun bedTypeListener() {
        if (cb2.isChecked==false){
            val bundle =  Bundle()
            bundle.putInt(KEY_REQUEST,BED_TYPE_REQUEST)
            if (et_guest.text.toString().isNotEmpty()) bundle.putString(KEY_NAME_GUEST,et_guest.text.toString())
            else bundle.putString(KEY_NAME_GUEST,getProfile().name)
            gotoActivityResultWithBundle(SpecialRequestActivity::class.java,bundle,BED_TYPE_REQUEST)
        }else {
            cb2.isChecked = false
            deletRemark(2)
        }
    }

    private fun cbCheckedListener(cb: CheckBox,type: Int) {
        cb.isChecked = !cb.isChecked
        when(type){
            1->  {
                if (cb1.isChecked){
                    addRemark("Hight Floor",type)
                }
                else {
                    deletRemark(type)
                }
            }
            3-> {
                if (cb3.isChecked){
                    addRemark("Non Smoking Room",type)
                }
                else {
                    deletRemark(type)
                }
            }
            4-> {
                if (cb4.isChecked){
                    addRemark("Conecting Room",type)
                }
                else {
                    deletRemark(type)
                }
            }
        }
    }

    private fun deletRemark(type: Int) {
        try {
            remark.removeAt(remark.indexOf(remark.filter { it.type ==type }.first()))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun addRemark(data:String,type: Int) {
        if (!remark.filter { it.type==type }.isNullOrEmpty()){
            remark.add(remark.indexOf(remark.filter { it.type==type }.first()),RemarkBookingModel("Hight Floor",type))
        }
        else {
            remark.add(RemarkBookingModel(data,type))
        }
    }

    private fun initDataOrder() {
        dataHotel = Serializer.deserialize(Constants.DATA_HOTEL, ResultListHotelModel::class.java)
        dataRoom = Serializer.deserialize(Constants.DATA_ROOM_HOTEL, SelectRoomModel::class.java)
        dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        dataValidation   = Serializer.deserialize(Constants.dataValidationHotel,ValidationHotelModel::class.java)
        dataConfirmation = Serializer.deserialize(Constants.dataConfirmationHotel,ConfirmationHotelModel::class.java)
    }

    private fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
        toolbar.setDoubleTitle(dataHotel.nameHotel,"${dataRoom.titleRoom}")
        btn_next.setTextButton("Continue")
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

//        adapter.notifyDataSetChanged()
    }

    private fun getDataIdCartBooker(): IdCartModel {
        val model = IdCartModel()
        model.fullname = getProfile().name
        model.id       = getProfile().employId
        model.idCart   = getProfile().ktp
        return model
    }

    private fun getSimDataBooker(): SimModel {
        val model   = SimModel()
        model.name  = getProfile().name
        model.id    = getProfile().employId
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

//    private fun initRecyclerView() {
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_booking_information.layoutManager = layoutManager
//        rv_booking_information.itemAnimator = DefaultItemAnimator()
//        rv_booking_information.adapter = adapter
//
//        adapter.setOnclickListener(this)
//    }

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

        tv_prize_departure.text   = "IDR "+Globals.formatAmount(dataRoom.prize.toDouble())
        tv_station_departure.text = dataRoom.titleRoom
        line_arrival.visibility   = View.GONE
        tv_price_total.text       = "IDR "+Globals.formatAmount(dataRoom.prize.toDouble())
        tv_price.text             = "IDR "+Globals.formatAmount(dataRoom.prize.toDouble())
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
        when(views){
            Constants.BTN_SIM       -> {
                dataContacts[position].checktype = "SIM"
            }
            Constants.BTN_PASSPORT  -> {
                dataContacts[position].checktype = "PASSPORT"
            }
            Constants.BTN_ID_CART   -> {
                dataContacts[position].checktype = "KTP"
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
            BED_TYPE_REQUEST -> {
                if (resultCode==Activity.RESULT_OK){
                    cb2.isChecked = true
                    addRemark(data?.getStringExtra(KEY_VALUE).toString(),2)
                }
            }
            OTHER_TYPE_REQUEST -> {
                if (resultCode==Activity.RESULT_OK){
                    cb5.isChecked = true
                    addRemark(data?.getStringExtra(KEY_VALUE).toString(),5)
                }
            }
        }
    }


    override fun onClicked() {
        getReservasedHotel()
    }

    fun getReservasedHotel(){
        showLoadingOpsicorp(true)
        setLog(Serializer.serialize(dataReservationHotelRequest()))
        GetDataAccomodation(getBaseUrl()).getBookingHotel(getToken(),dataReservationHotelRequest(),object : CallbackBookingHotel {
            override fun success(data: BookingHotelModel) {
                hideLoadingOpsicorp()
                Constants.ID_BOOKING_TEMPORARY = dataTrip.idTripPlant
                gotoActivity(NewCartActivity::class.java)
            }

            override fun failed(message: String) {
                hideLoadingOpsicorp()
                showAllert("Sorry",message)
            }
        })
    }

    private fun dataReservationHotelRequest(): HashMap<Any, Any> {
        val data             = ReservationHotelRequest()
        data.beds            = dataBeds()
        data.booking         = dataBookingHoteRequest()
        data.confirmationId  = dataConfirmation.idConfirmation
        data.contact         = getContactHotelRequest()
        data.correlationId   = dataHotel.correlationId
        data.destinationCity = "vOoQdQqvHE6Req8ZI8ulZA"//dataHotel.city
        data.guestPassport   = dataHotel.idCountry
        data.guests          = dataGuest()
//        data.travelProfileId = null!!
        data.remark          = parsingDataRemark()
        data.header          = dataHeader()
        return Globals.classToHashMap(data,ReservationHotelRequest::class.java)
    }

    private fun dataHeader(): HeaderReservationHotelRequest {
        val data            = HeaderReservationHotelRequest()
        data.origin         = dataTrip.originId
        data.returnDate     = dataHotel.checkOut
        data.startDate      = dataHotel.checkIn
        data.destination    = dataTrip.destinationName
        data.tripParticipants   = listParticipant()
        data.type               = 1
        data.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        data.purpose        = dataTrip.purpose
        data.idTripPlan     = dataTrip.idTripPlant
        data.code           = dataTrip.tripCode
//        data.ID             = dataTrip.idTripPlant
        return data
    }

    private fun dataGuest(): List<GuestsItemReservationHotelRequest> {
        val data = ArrayList<GuestsItemReservationHotelRequest>()

        val model = GuestsItemReservationHotelRequest()
        model.type          = 1
        model.homePhone     = getProfile().homePhone
        model.remarks       = remarkGuest()
        model.firstName     = getProfile().firstName
        model.idNumber      = getProfile().idNumber
        model.assignedRoom  = 1
        model.orderInRoom   = 1
        model.title         = getProfile().title
        model.index         = 0
        model.lastName      = getProfile().lastName
        model.mobilePhone   = getProfile().mobilePhone
        model.nationality   = getProfile().nationality
        model.isUseLocal    = true

        data.add(model)
        return data
    }

    private fun remarkGuest(): List<String> {
        val data = ArrayList<String>()
        data.add("NikEmployee: ${getProfile().employeeNik}")
        data.add("Email: ${getProfile().email}")
        data.add("JobTitleName: ${getProfile().jobTitleId}")
        return data
    }

    private fun getContactHotelRequest(): ContactReservationHotelRequest {
        val data = ContactReservationHotelRequest()
        data.email     = getProfile().email
        data.firstName = getProfile().firstName
        data.title     = getProfile().title
        data.lastName  = getProfile().lastName
        data.mobilePhone = getProfile().mobilePhone
        data.remark    = parsingDataRemark()
        return data
    }

    private fun dataBookingHoteRequest(): BookingReservationHotelRequest {
        val data = BookingReservationHotelRequest()
        data.origin      = dataTrip.originName
        data.destination = dataHotel.city
        data.remarks     = null
        data.reasonCode  = dataValidation.reasonCode
        data.members     = getDataMembers()
        data.hotel       = getDataHotel()
        return data
    }

    private fun parsingDataRemark(): String {
        var dataRemark = ""
        if (remark.isNotEmpty()){
            remark.forEach {
                dataRemark = dataRemark+it.remark+","
            }
        }
        return if (dataRemark.contains(",")) dataRemark.substring(0,dataRemark.length-1) else dataRemark
    }

    private fun getDataHotel(): HotelReservationHotelRequest {
        val mData           = HotelReservationHotelRequest()
        mData.isHsre        = false
        mData.mapUri        = dataConfirmation.mapUri
        mData.area          = dataHotel.city
        mData.roomSelector  = dataRoom.roomKey
        mData.isTourism     = false
        mData.tourismTax    = 0
        mData.isViolatedHotelRules = dataValidation.isViolatedRules
        mData.causeViolatedRules   = dataValidation.causeViolatedRules
        mData.image                = dataHotel.imageHotel
        mData.cancellationPoliciesView = dataConfirmation.cancellPolicyHotel
        return mData
    }

    private fun getDataMembers(): List<String> {
        val mData : ArrayList<String> = ArrayList()
        mData.add(getProfile().employId)
        return mData
    }

    private fun dataBeds(): BedsReservationHotelRequest{
        val data = BedsReservationHotelRequest()
        data.countAdult = 1
        data.index = 0
        data.type  = "single"
        return data
    }

    private fun listParticipant(): List<TripParticipantHotelRequest> {
        val data = ArrayList<TripParticipantHotelRequest>()
        val model = TripParticipantHotelRequest()
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