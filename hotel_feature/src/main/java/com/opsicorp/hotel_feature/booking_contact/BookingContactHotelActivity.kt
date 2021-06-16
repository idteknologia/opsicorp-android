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
import android.widget.EditText
import kotlin.collections.ArrayList
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import opsigo.com.domainlayer.model.accomodation.hotel.*
import opsigo.com.domainlayer.callback.CallbackBookingHotel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.mobile.travelaja.utility.Constants.KEY_NAME_GUEST
import com.mobile.travelaja.module.cart.activity.NewCartActivity
import kotlinx.android.synthetic.main.booking_contact_view_hotel.*
import com.opsicorp.hotel_feature.adapter.HotelBookingContactAdapter
import opsigo.com.datalayer.request_model.accomodation.hotel.booking.*
import com.opsicorp.hotel_feature.adapter.OnclickRecyclerBookingContact
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

class BookingContactHotelActivity : BaseActivity(),
        OnclickRecyclerBookingContact,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.booking_contact_view_hotel }

    val dataContacts = ArrayList<GuestsItemReservationHotelRequest>()
    val adapter by lazy { HotelBookingContactAdapter(this,dataContacts) }

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
        initRecyclerView()
        initOnClickListener()
        initPrize()
        setDataContact()
    }

    private fun initOnClickListener() {
        line_cb_1.setOnClickListener { cbCheckedListener(cb1,1) }
        line_cb_3.setOnClickListener { cbCheckedListener(cb3,3) }
        line_cb_4.setOnClickListener { cbCheckedListener(cb4,4) }

        line_cb_2.setOnClickListener { bedTypeListener() }
        line_cb_5.setOnClickListener { otherTypeListener() }
        btn_cb5.setOnClickListener { otherTypeListener() }
        btn_cb2.setOnClickListener { bedTypeListener() }
    }

    private fun emptyGuestName() {
        dataContacts[0].firstName = ""
        dataContacts[0].lastName  = ""
        adapter.notifyItemChanged(0)
    }

    private fun setGuestName() {
        dataContacts[0].firstName = getProfile().firstName
        dataContacts[0].lastName  = getProfile().lastName
        adapter.notifyItemChanged(0)
    }

    private fun otherTypeListener() {
        if (cb5.isChecked==false){
            val bundle = Bundle()
            bundle.putInt(KEY_REQUEST,OTHER_TYPE_REQUEST)
            if (dataContacts[0].firstName.toString().isNotEmpty()) bundle.putString(KEY_NAME_GUEST,dataContacts[0].firstName.toString())
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
            if (dataContacts[0].firstName.toString().isNotEmpty()) bundle.putString(KEY_NAME_GUEST,dataContacts[0].firstName.toString())
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

        val model = GuestsItemReservationHotelRequest()
        model.type          = 1
        model.homePhone     = getProfile().homePhone
        model.remarks       = remarkGuest()
        model.firstName     = ""
        model.idNumber      = getProfile().idNumber
        model.assignedRoom  = 1
        model.orderInRoom   = 1
        model.title         = getProfile().title
        model.index         = 0
        model.lastName      = ""
        model.mobilePhone   = getProfile().mobilePhone
        model.nationality   = getProfile().nationality
        model.isUseLocal    = true
        dataContacts.add(model)

        for (i in 1 until dataHotel.totalGuest){
            val mData = GuestsItemReservationHotelRequest()
            mData.type          = 1
            mData.homePhone     = getProfile().homePhone
            mData.remarks       = ArrayList()
            mData.firstName     = ""
            mData.idNumber      = getProfile().idNumber
            mData.assignedRoom  = 1
            mData.orderInRoom   = 1
            mData.title         = "Mr"
            mData.index         = 0
            mData.lastName      = ""
            mData.mobilePhone   = getProfile().mobilePhone
            mData.nationality   = getProfile().nationality
            mData.isUseLocal    = true
            dataContacts.add(mData)
        }

        adapter.setData(dataContacts)

        val dataProfile = getProfile()
        tv_name_contact.text        = dataProfile.name
        tv_number_contact.text      = dataProfile.homePhone
        tv_email_contact.text       = dataProfile.email
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_booking_contact.layoutManager = layoutManager
        rv_booking_contact.itemAnimator  = DefaultItemAnimator()
        rv_booking_contact.adapter = adapter

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

    override fun onClick(view: Int, position: Int) {
        when (view){
            7-> {
                if (dataContacts[0].firstName.isEmpty()){
                    setGuestName()
                }
                else {
                    emptyGuestName()
                }
            }
        }
    }

    override fun editText(view: Int, position: Int, string: String) {
        try {
            when(view){
                6->{
                    dataContacts[position].firstName = string
                    dataContacts[position].lastName  = string
                    adapter.notifyItemChanged(position)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
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
        if (checkEmptyListContactName()){
            showAllert("Sorry","Please complete input guest name")
        }
        else {
            showLoadingOpsicorp(true)
            setLog(Serializer.serialize(dataReservationHotelRequest()))
            GetDataAccomodation(getBaseUrl()).getBookingHotel(getToken(),dataReservationHotelRequest(),object : CallbackBookingHotel {
                override fun success(data: BookingHotelModel) {
                    hideLoadingOpsicorp()
                    val bundle = Bundle()
                    bundle.putString(Constants.ID_TRIP_PLANE,dataTrip.idTripPlane)
                    if (Constants.isBisnisTrip){
                        bundle.putString(Constants.FROM_CART,Constants.FROM_BISNIS_TRIP)
                    }
                    else {
                        bundle.putString(Constants.FROM_CART,Constants.FROM_PERSONAL_TRIP)
                    }
                    gotoActivityWithBundle(NewCartActivity::class.java,bundle)
                }

                override fun failed(message: String) {
                    hideLoadingOpsicorp()
                    showAllert("Sorry",message)
                }
            })
        }

    }

    private fun dataReservationHotelRequest(): HashMap<Any, Any> {
        val data             = ReservationHotelRequest()
        data.beds            = dataBeds()
        data.booking         = dataBookingHoteRequest()
        data.confirmationId  = dataConfirmation.idConfirmation
        data.contact         = getContactHotelRequest()
        data.correlationId   = dataHotel.correlationId
        data.destinationCity = dataHotel.idCity //"vOoQdQqvHE6Req8ZI8ulZA"
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
        data.idTripPlan     = dataTrip.idTripPlane
        data.code           = dataTrip.tripCode
        return data
    }

    private fun dataGuest(): ArrayList<GuestsItemReservationHotelRequest> {
        dataContacts.forEachIndexed { index, contatc ->
            val view = rv_booking_contact.getChildAt(index)
            val nameEditText = view.findViewById(R.id.et_guest) as EditText
            val noHp = view.findViewById(R.id.et_no_hp) as EditText
            val name = nameEditText.getText().toString()
            val hp   = noHp.text.toString()
            if (name.isNotEmpty()){
                if (name.contains(" ")){
                    contatc.firstName = name.split(" ")[0]
                    contatc.lastName  = name.split(" ")[1]
                }
                else {
                    contatc.firstName = name
                    contatc.lastName  = name
                }
            }
            if (hp.isNotEmpty()){
                contatc.mobilePhone = hp
            }
        }
        return dataContacts
    }

    private fun checkEmptyListContactName():Boolean{
        var empty = false
        dataContacts.forEachIndexed { index, contatc ->
            val view = rv_booking_contact.getChildAt(index)
            val nameEditText = view.findViewById(R.id.et_guest) as EditText
            val noHp = view.findViewById(R.id.et_no_hp) as EditText
            val name = nameEditText.getText().toString().trim()
            val hp   = noHp.text.toString().trim()
            if (name.isEmpty()){
                empty = true
            }
            if (hp.isEmpty()){
                empty = true
            }
        }
        return empty
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