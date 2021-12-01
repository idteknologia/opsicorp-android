package com.opsicorp.hotel_feature.confirmation

import com.mobile.travelaja.module.accomodation.dialog.accomodation_reason_trip.SelectReasonAccomodation
import opsigo.com.datalayer.request_model.accomodation.hotel.validation.ContactValidationHotelRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.validation.DataValidationHotelRequest
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.hotel.validation.ValidationHotelRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.confirmation.ConfirmHotelRequest
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.domainlayer.model.accomodation.hotel.ConfirmationHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.hotel.ValidationHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import com.opsicorp.hotel_feature.booking_contact.BookingContactHotelActivity
import com.opsicorp.hotel_feature.select_room.DialogCancelationPolicy
import kotlinx.android.synthetic.main.detail_prize_bottom_hotel.*
import kotlinx.android.synthetic.main.confirmation_order_hotel.*
import opsigo.com.domainlayer.callback.CallbackConfirmationHotel
import com.mobile.travelaja.module.cart.model.ItemCardHotelModel
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.domainlayer.callback.CallbackValidationHotel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.mobile.travelaja.module.cart.model.CartModel
import com.mobile.travelaja.utility.DateConverter
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.base.BaseActivity
import com.squareup.picasso.Picasso
import com.opsicorp.hotel_feature.R
import android.view.View
import java.util.HashMap

class ConfirmationOrderHotel : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener ,
        View.OnClickListener{

    override fun getLayout(): Int {
        return R.layout.confirmation_order_hotel
    }

    lateinit var dataRoom : SelectRoomModel
    lateinit var dataHotel: ResultListHotelModel
    lateinit var dataValidation: ValidationHotelModel
    lateinit var dataConfirmation: ConfirmationHotelModel
    lateinit var dataTrip: SuccessCreateTripPlaneModel
    lateinit var dataOrderDetail : ItemCardHotelModel
    override fun OnMain() {
        initDataOrder()
        initToolbar()
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("Book")
        btn_info_cancelation.setOnClickListener {
            val dialog = DialogCancelationPolicy(
                    "2020-02-18")
            showDialogFragment(dialog)
        }
        setDataConfirmationOrder()
    }

    private fun setDataConfirmationOrder() {
        if (intent.getStringExtra(Constants.FROM_CART)!=null){
            try {
                setDataDetail()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        else {
            getDataConfirmation()
        }
    }

    private fun setDataDetail() {
        dataOrderDetail = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_HOTEL), CartModel::class.java).dataCardHotel

//        Constants.CheckInDate  = dataTrip.startDate
//        Constants.CheckOutDate = dataTrip.endDate

        dataHotel                = getDataDetailHotel()
        dataValidation           = getDataValidationHotel()
        dataRoom                 = getDataRoomHotel()
        line_prize.visibility    = View.GONE
        initDataView()
    }

    private fun getDataRoomHotel(): SelectRoomModel {
        val dataRoom = SelectRoomModel()

        dataRoom.prize = dataOrderDetail.price

        return dataRoom
    }

    private fun getDataValidationHotel(): ValidationHotelModel {
        val dataValidation = ValidationHotelModel()

        dataValidation.breakfast = "Not include Breakfast"
        dataValidation.typeBed   = dataOrderDetail.typeHotel
        dataValidation.notcomply = false

        return dataValidation
    }

    fun getDataDetailHotel(): ResultListHotelModel {

        val dataHotel = ResultListHotelModel()

        dataHotel.nameHotel = dataOrderDetail.nameHotel
        dataHotel.typeHotel = dataOrderDetail.typeHotel
        dataHotel.addressHotel = dataOrderDetail.address
        dataHotel.imageHotel   = dataOrderDetail.image

        return dataHotel
    }

    private fun getDataConfirmation() {
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getConfirmationHotel(getToken(),dataConfirmationRequest(),object :CallbackConfirmationHotel{
            override fun success(confirmationHotelModel: ConfirmationHotelModel) {
                dataConfirmation = confirmationHotelModel
                dataConfirmation.roomSelector = dataRoom.roomKey
                getValidationHotel()
            }

            override fun failed(string: String) {
                showAllert("Sorry",string)
            }
        })
    }

    private fun getValidationHotel() {
//        setLog(Serializer.serialize(dataValidationHotel()))
        GetDataAccomodation(getBaseUrl()).getValidationHotel(getToken(),dataValidationHotel(),object :CallbackValidationHotel{
            override fun success(data: ValidationHotelModel) {
                dataValidation = data
                initDataView()
                hideLoadingOpsicorp()
            }

            override fun failed(message: String) {
                showAllert("Sorry",message)
            }
        })
    }

    private fun initDataView() {
        tv_check_in.text                = DateConverter().getDate(dataHotel.checkIn,"yyyy-MM-dd","EEE, dd MMM yyyy")//"Sun 27 Apr 2019"
        tv_check_out.text               = DateConverter().getDate(dataHotel.checkOut,"yyyy-MM-dd","EEE, dd MMM yyyy")
        tv_name_hotel_cart.text         = dataHotel.nameHotel
        tv_type_hotel_cart.text         = dataHotel.typeHotel
        tv_description_hotel_cart.text  = dataHotel.addressHotel
        tv_detail_facility.text         = "24sqm Kingsize or Twin Bed 2m10 Bath Shower Free Wifi Minibar in Room Nespresso Art Nouveau Military Rate Id Must Be Shown on Arrival Incl Vat Wifi 24pm"
        tv_double_bed.text              = dataValidation.typeBed
        try {
            tv_cancelation_policy2.text     = "Charge 100% - Start from ${DateConverter().getDate(dataTrip.startDate.split(" ")[0],"yyyy-mm-dd","dd MMM yyyy")}"//22 May 2020
            tv_cancelation_policy1.text     = "No charge for cancellation before ${DateConverter().getDate(dataTrip.endDate.split(" ")[0],"yyyy-mm-dd","dd MMM yyyy")}"
        }catch (e:Exception){
            tv_cancelation_policy2.text     = dataTrip.endDate
            tv_cancelation_policy1.text     = dataTrip.startDate
            e.printStackTrace()
        }
        tv_prize_hotel_cart.text        = "IDR ${Globals.formatAmount(dataRoom.prize.toDouble().toInt().toString())}"

        if(dataValidation.notcomply){
            line_reason_code.visibility = View.VISIBLE
            tv_notcomply.visibility     = View.VISIBLE
        }
        else {
            line_reason_code.visibility = View.GONE
            tv_notcomply.visibility     = View.GONE
        }

        if (dataRoom.isBreakfast){
            id_include_breakfast.text = "Include Breakfast"
        }
        else {
            id_include_breakfast.text = dataValidation.breakfast
        }

        line_reason_code.setOnClickListener { selectReasonCode() }
        Picasso.get()
                .load(dataHotel.imageHotel)
                .fit()
                .into(tv_image_hotel_cart)

        initPrize()
    }

    private fun dataValidationHotel(): HashMap<Any, Any> {
        val data = ValidationHotelRequest()
        data.contact = dataContactPerson()
        data.hotel   = dataHotelRequest()
        data.destination = dataTrip.destinationName
        data.members = getMembers()
        data.origin  = dataTrip.originName
        data.purpose     = dataTrip.purpose
        data.reasonCode  = ""
        data.remarks     = ""
        return Globals.classToHashMap(data,ValidationHotelRequest::class.java)
    }

    private fun getMembers(): List<String> {
        var data : ArrayList<String> = ArrayList()
        data.add(getProfile().employId)
        return data
    }


    private fun dataHotelRequest(): DataValidationHotelRequest {
        val data = DataValidationHotelRequest()
        data.isHsre         = false
        data.hotelKey       = dataHotel.hotelKey
        data.mapUri         = dataConfirmation.mapUri
        data.area           = dataHotel.city
        data.confirmationId = dataConfirmation.idConfirmation
        data.roomSelector   = dataConfirmation.roomSelector
        data.isTourism      = false
        data.tourismTax     = 0
        data.correlationId  = dataHotel.correlationId
        data.image          = dataHotel.imageHotel
        return data
    }

    private fun dataContactPerson(): ContactValidationHotelRequest {
        val data = ContactValidationHotelRequest()
        data.email             = getProfile().email
        data.firstName         = getProfile().firstName
        data.title             = getProfile().title
        data.lastName          = getProfile().lastName
        data.mobilePhone       = getProfile().mobilePhone
        data.remark            = ""
        return data
    }

    private fun dataConfirmationRequest(): HashMap<Any, Any> {
        val data = ConfirmHotelRequest()
        data.correlationId = dataHotel.correlationId
        data.hotelKey      = dataHotel.hotelKey
        data.roomKey       = dataRoom.roomKey
        data.travelAgent   = Globals.getConfigCompany(this).defaultTravelAgent
        return Globals.classToHashMap(data,ConfirmHotelRequest::class.java)
    }

    private fun initDataOrder() {
        dataHotel        = Serializer.deserialize(Constants.DATA_HOTEL,ResultListHotelModel::class.java)
        dataRoom         = Serializer.deserialize(Constants.DATA_ROOM_HOTEL,SelectRoomModel::class.java)
    }

    private fun initToolbar() {
        dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        ic_back.setOnClickListener(this)
        tv_total_night.text = "${dataHotel.duration} Night (s)"
    }

    override fun onClicked() {
        Constants.dataValidationHotel   = Serializer.serialize(dataValidation,ValidationHotelModel::class.java)
        Constants.dataConfirmationHotel = Serializer.serialize(dataConfirmation,ConfirmationHotelModel::class.java)
        if (dataValidation.isDoubleBiooking){
            showAllert(getString(R.string.sorry),dataValidation.messageDoubleBooking)
        }
        else {
            gotoActivity(BookingContactHotelActivity::class.java)
        }
    }

    private fun selectReasonCode() {
        val selectAccomodation = SelectReasonAccomodation(true,R.style.CustomDialog, Constants.DATA_REASON_CODE_HOTEL)
        selectAccomodation.show(supportFragmentManager,"dialog")

        selectAccomodation.setCallbackListener(object : SelectReasonAccomodation.CallbackSelectPreferance{
            override fun callback(model: ReasonCodeModel) {
                dataValidation.reasonCode = model.codeBrief+"-"+model.description
                tv_reason_code.text = model.description
            }
        })
    }

    override fun btnBack() {

    }

    override fun logoCenter() {

    }

    override fun btnCard() {

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
        line_shadow.setOnClickListener {
            showOrHideDetailPrize()
        }
        line_arrival.visibility   = View.GONE
        tv_title_prize.text       = "Total to be paid"
        tv_prize_departure.text   = "IDR "+Globals.formatAmount(dataRoom.prize.toDouble().toInt().toString())
        tv_price_total.text       = "IDR "+Globals.formatAmount(dataRoom.prize.toDouble().toInt().toString())
        tv_price.text             = "IDR "+Globals.formatAmount(dataConfirmation.totalPrice.toDouble().toInt().toString())
        tv_including.text         = "Including of all fares & vat"
        tv_station_departure.text = dataHotel.room.first().titleRoom
        tv_item_pax.text          = dataHotel.duration

        Globals.delay(100,object :Globals.DelayCallback{
            override fun done() {
                collapsePrize()
            }
        })
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
//        line_body.cardElevation = Globals.toDp(this,0).toFloat()
//        line_reason_code.cardElevation = Globals.toDp(this,0).toFloat()

        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down_green))
        line_shadow.visibility  = View.VISIBLE
        tv_including.visibility = View.GONE
        body_prize.expand()
    }
    private fun collapsePrize() {
//        line_body.cardElevation = Globals.toDp(this,3).toFloat()
//        line_reason_code.cardElevation = Globals.toDp(this,3).toFloat()

        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_green))
        tv_including.visibility = View.VISIBLE
        line_shadow.visibility  = View.GONE
        body_prize.collapse()
    }


    override fun onClick(p0: View?) {
        when(p0){
            ic_back -> {
                finish()
            }
        }
    }
}