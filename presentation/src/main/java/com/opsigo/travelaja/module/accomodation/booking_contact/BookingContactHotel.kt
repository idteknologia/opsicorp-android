package com.opsigo.travelaja.module.accomodation.booking_contact

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.booking_contact_view_hotel.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.accomodation.hotel.booking.*
import opsigo.com.domainlayer.callback.CallbackBookingHotel
import opsigo.com.domainlayer.model.accomodation.hotel.*
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import opsigo.com.domainlayer.model.booking_contact.SimModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.summary.PassportModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class BookingContactHotel : BaseActivity(),OnclickListenerRecyclerView,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.booking_contact_view_hotel }

    val dataContacts = ArrayList<BookingContactAdapterModel>()
    val adapter by lazy { BookingContactAdapter(this,dataContacts) }

    lateinit var dataHotel: ResultListHotelModel
    lateinit var dataRoom: SelectRoomModel
    lateinit var dataValidation: ValidationHotelModel
    lateinit var dataConfirmation: ConfirmationHotelModel
    lateinit var dataTrip: SuccessCreateTripPlaneModel

    override fun OnMain() {
        initDataOrder()
        initToolbar()
        initRecyclerView()
        setDataContact()
        initPrize()
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

        adapter.notifyDataSetChanged()
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
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_booking_information.layoutManager = layoutManager
        rv_booking_information.itemAnimator = DefaultItemAnimator()
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
        }
    }


    override fun onClicked() {
        getReservasedHotel()
    }

    fun getReservasedHotel(){
        showLoadingOpsicorp(true)
        setLog(Serializer.serialize(dataReservationHotelRequest()))
        GetDataAccomodation(getBaseUrl()).getBookingHotel(getToken(),dataReservationHotelRequest(),object :CallbackBookingHotel{
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
        data.destinationCity = dataHotel.city
//        data.guestPassport   = Constants.Country.id
        data.guests          = dataGuest()
//        data.travelProfileId = null!!
        data.remark          = ""
        data.header          = dataHeader()


        return Globals.classToHashMap(data,ReservationHotelRequest::class.java)
    }

    private fun dataHeader(): HeaderReservationHotelRequest {
        val data            = HeaderReservationHotelRequest()
        data.origin         = dataTrip.originId
        data.returnDate     = dataTrip.endDate
        data.startDate      = dataTrip.startDate
        data.destination    = dataTrip.destinationName
        data.tripParticipants   = listParticipant()
        data.type               = 1
        data.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        data.purpose        = dataTrip.purpose
        data.code           = dataTrip.tripCode
        data.ID             = dataTrip.idTripPlant
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
        data.remark    = ""
        return data
    }

    private fun dataBookingHoteRequest(): BookingReservationHotelRequest {
        val data = BookingReservationHotelRequest()
        data.origin      = dataTrip.originName
        data.destination = dataHotel.city
        data.remarks     = ""
        data.reasonCode  = dataValidation.reasonCode
        data.members     = getDataMembers()
        data.hotel       = getDataHotel()
        return data
    }

    private fun getDataHotel(): HotelReservationHotelRequest {
        val mData = HotelReservationHotelRequest()
        mData.isHsre = false
        mData.mapUri = dataConfirmation.mapUri
        mData.area = dataHotel.city
        mData.roomSelector = dataRoom.roomKey
        mData.isTourism  = false
        mData.tourismTax = 0
        mData.isViolatedHotelRules = dataValidation.isViolatedRules
        mData.causeViolatedRules = dataValidation.causeViolatedRules
        mData.image  = dataHotel.imageHotel
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

    private fun listParticipant(): List<TripParticipantsReservationHotelRequest> {
        val data = ArrayList<TripParticipantsReservationHotelRequest>()
        val model = TripParticipantsReservationHotelRequest()
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