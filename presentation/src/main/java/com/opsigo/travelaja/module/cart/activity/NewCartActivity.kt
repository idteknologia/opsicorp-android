package com.opsigo.travelaja.module.cart.activity

import com.opsigo.travelaja.module.accomodation.view_accomodation.activity.AccomodationActivity
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsItem
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.request_model.create_trip_plane.SubmitTripPlant
import opsigo.com.datalayer.request_model.create_trip_plane.ContactRequest
import com.opsigo.travelaja.module.item_custom.success_view.SuccessView
import com.opsigo.travelaja.module.cart.view.PageDetailPersonalTrip
import com.opsigo.travelaja.module.cart.view.PageListBisnisTrip
import com.opsigo.travelaja.module.cart.view.PageDetailListTrip
import com.opsigo.travelaja.module.cart.model.CartHeaderModel
import com.opsigo.travelaja.module.home.activity.HomeActivity
import opsigo.com.domainlayer.model.summary.ItemFlightModel
import com.opsigo.travelaja.module.payment.PaymentActivity
import opsigo.com.domainlayer.model.cart.CartModelAdapter
import kotlinx.android.synthetic.main.new_cart_activity.*
import opsigo.com.domainlayer.model.summary.SummaryModel
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import kotlinx.android.synthetic.main.empty_cart_view.*
import com.opsigo.travelaja.module.cart.model.CartModel
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.mapper.Serializer
import android.content.BroadcastReceiver
import com.opsigo.travelaja.BaseActivity
import opsigo.com.domainlayer.callback.*
import com.opsigo.travelaja.utility.*
import android.content.IntentFilter
import java.text.SimpleDateFormat
import android.widget.TextView
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.os.Build
import android.util.Log
import java.util.*

class NewCartActivity : BaseActivity(), View.OnClickListener,
        PageListBisnisTrip.Callback,
        PageDetailListTrip.Callback,
        PageDetailPersonalTrip.Callback,
        ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.new_cart_activity
    }

    var idTripPlant = ""
    var pagePosition = 0
    var LIST_BISNIS_TRIP = 0
    var DETAIL_BISNIS_TRIP = 1
    var DETAIL_PERSONAL_TRIP = 2
    var tripSummary = SummaryModel()
    val btnList = ArrayList<TextView>()
    var mData = ArrayList<CartModelAdapter>()
    val itemsTrip = ArrayList<ModelItemTrip>()
    var addMore = Constants.REQUEST_CODE_ADD_MORE_ITEM

    override fun OnMain() {
        initView()
        checkData()
    }

    private fun initView() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        toolbar.setTitleBar("Cart List")

        btn_bisnis_trip.setOnClickListener(this)
        btn_personal_trip.setOnClickListener(this)
        btnList.add(btn_bisnis_trip)
        btnList.add(btn_personal_trip)

        page_list_detail_trip.visibility = View.GONE
        page_detail_personal_trip.visibility = View.GONE
        line_prize.visibility = View.GONE
    }

    private fun checkData() {
        when(intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.FROM_CART)){
            Constants.FROM_HOME -> {
                Globals.changeViewButton(btnList, 0, this)
                pagePosition = LIST_BISNIS_TRIP
                getDataCart()
            }
            Constants.FROM_BISNIS_TRIP -> {
                idTripPlant = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.ID_TRIP_PLANE,"")
                Globals.changeViewButton(btnList, 0, this)
                pagePosition = DETAIL_BISNIS_TRIP
                showLoadingOpsicorp(false)
                showDetailBisnisTrip()
                getDataSummary(idTripPlant)
            }
            Constants.FROM_PERSONAL_TRIP -> {
                idTripPlant = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.ID_TRIP_PLANE,"")
                Globals.changeViewButton(btnList, 1, this)
                pagePosition = DETAIL_PERSONAL_TRIP
                showLoadingOpsicorp(false)
                getDataSummary(idTripPlant)
            }
        }

        btn_home_page.callbackOnclickButton(object : ButtonDefaultOpsicorp.OnclickButtonListener {
            override fun onClicked() {

            }
        })
    }

    fun nestedScrollUp() {
        Globals.delay(100, object : Globals.DelayCallback {
            override fun done() {
                nested_view.fullScroll(View.FOCUS_UP)
            }
        })
    }

    private fun showListCartBisnisTrip() {
        nestedScrollUp()
        pagePosition = LIST_BISNIS_TRIP
        page_list_detail_trip.visibility = View.GONE
        page_list_bisnis_trip.visibility = View.VISIBLE
        page_detail_personal_trip.visibility = View.GONE
        line_prize.visibility = View.GONE
        page_list_bisnis_trip.callbackOnclickButton(this)
    }

    private fun showDetailBisnisTrip() {
        nestedScrollUp()
        pagePosition = DETAIL_BISNIS_TRIP
        page_list_detail_trip.visibility = View.VISIBLE
        page_list_bisnis_trip.visibility = View.GONE
        page_detail_personal_trip.visibility  = View.GONE
        line_prize.visibility = View.GONE
        page_list_bisnis_trip.callbackOnclickButton(this)
    }



    private fun getDataCart() {
        showListCartBisnisTrip()
        page_list_bisnis_trip.setLoadingView()
        GetDataGeneral(getBaseUrl()).getListCart(Globals.getToken(), "40", "1", "Code", "1", object : CallbackListCart {
            override fun successLoad(approvalModel: ArrayList<CartModelAdapter>) {
                if (approvalModel.isNotEmpty()) {
                    toolbar.hideAddMoreItem()
                    mData.clear()
                    mData.addAll(approvalModel)
                    mData.reversed()
                    page_list_bisnis_trip.hideLoadingView()
                    setDataListCart()
                }
            }

            override fun failedLoad(message: String) {
                Globals.showAlert("Sorry", message, this@NewCartActivity)
            }
        })
    }

    fun setDataListCart() {
        toolbar.hideAddMoreItem()
        page_list_bisnis_trip.setDataOrder(mData)
    }

    private fun removeTrip(id: String) {

        GetDataTripPlane(getBaseUrl()).cancelTripplan(Globals.getToken(), id, object : CallbackCancelTripplan {
            override fun successLoad(boolean: Boolean) {

                if (!boolean) {
                    Globals.showAlert("Sorry", "something wrong!", applicationContext)
                }

            }

            override fun failedLoad(message: String) {
                Globals.showAlert("Sorry", message, applicationContext)
            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_bisnis_trip -> {
                getDataCart()
                Globals.changeViewButton(btnList, 0, this)
            }
            btn_personal_trip -> {
                showPageListDataPersonalTrip()
                Globals.changeViewButton(btnList, 1, this)
            }
        }

    }

    override fun onClicked(position: Int) {
        val data = mData.get(position)
        showLoadingOpsicorp(false)
        pagePosition = DETAIL_BISNIS_TRIP
        getDataSummary(data.id)
    }

    fun failedWarning(message: String) {
        if (message.isNotEmpty()) {
            showAlert("Sorry", message)
        } else {
            showAlert("Sorry", "failed to retrieve data")
        }
    }

    fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, which -> finish() }
        builder.create().show()
    }

    private fun updateViewSummary() {

        val totalExpenditure = java.lang.Double.parseDouble(tripSummary.totalExpenditure)
        val totalAllowance = java.lang.Double.parseDouble(tripSummary.totalAllowance)

        tv_total_purchase.text = StringUtils().setCurrency("IDR", (totalExpenditure - totalAllowance), false)
        if (tv_total_purchase.text.equals("IDR 0")) {
            tv_total_purchase.gone()
        } else {
            tv_total_purchase.visible()
        }

        when(pagePosition){
            DETAIL_BISNIS_TRIP -> {
                showDetailBisnisTrip()
                page_list_detail_trip.setPurpose(tripSummary.purpose)
                page_list_detail_trip.setTimeLimit(tripSummary.expiredRemaining)
                page_list_detail_trip.setTripCode(tripSummary.tripCode)
                page_list_detail_trip.setData(getDataTrip())
                page_list_detail_trip.callbackOnclickButton(this)
            }
            DETAIL_PERSONAL_TRIP -> {
                showPageListDataPersonalTrip()
                line_prize.visibility = View.VISIBLE
                page_detail_personal_trip.setData(getDataTrip())
                page_detail_personal_trip.callbackOnclickButton(this)
            }
        }

        line_prize.visibility = View.VISIBLE

        toolbar.showAddMoreItem()
        showValidationButtonSubmit()
    }

    private fun getDataTrip(): ArrayList<CartModel> {
        val dataAccomodation = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first()

        val list = ArrayList<CartModel>()
        list.clear()
        itemsTrip.clear()

        if (dataAccomodation.itemTrainModel.isNotEmpty()) {
            val data = CartModel()
            data.typeCard = Constants.TYPE_HEADER
            data.dataHeader = headerTrain()
            list.add(data)
            dataAccomodation.itemTrainModel.forEachIndexed { index, itemTrainModel ->
                val model = CartModel()
                model.typeCard = Constants.TYPE_TRAIN

                model.dataCardTrain.idTrain = itemTrainModel.idTrain
                model.dataCardTrain.refrenceCode = itemTrainModel.referenceCode
                model.dataCardTrain.tripId = itemTrainModel.tripID
                model.dataCardTrain.tripItemId = itemTrainModel.tripItemID
                model.dataCardTrain.titleTrain = itemTrainModel.titleTrain
                model.dataCardTrain.imageTrain = itemTrainModel.imageTrain

                model.dataCardTrain.status = itemTrainModel.status
                model.dataCardTrain.pnrCode = itemTrainModel.pnrCode
                model.dataCardTrain.pnrID = itemTrainModel.pnrID
                model.dataCardTrain.classTrain = itemTrainModel.classTrain

                model.dataCardTrain.carrierNumber = itemTrainModel.carrierNumber
                model.dataCardTrain.fareBasisCode = itemTrainModel.fareBasisCode
                model.dataCardTrain.seatNumber = itemTrainModel.seatNumber
                model.dataCardTrain.seatName = itemTrainModel.seatName
                model.dataCardTrain.seatText = itemTrainModel.seatText
                model.dataCardTrain.classCode = itemTrainModel.classCode

                //depart
                model.dataCardTrain.origin = itemTrainModel.originName
                model.dataCardTrain.stationDeparture = itemTrainModel.stationDeparture
                model.dataCardTrain.dateDeparture = itemTrainModel.dateDeparture
                model.dataCardTrain.timeDeparture = itemTrainModel.timeDeparture

                //arrival
                model.dataCardTrain.destination = itemTrainModel.destinationName
                model.dataCardTrain.stationArrival = itemTrainModel.stationArrival
                model.dataCardTrain.dateArrival = itemTrainModel.dateArrival
                model.dataCardTrain.timeArrival = itemTrainModel.timeArrival
                model.dataCardTrain.progressTrain = itemTrainModel.progressTrain

                val sPrice = StringUtils().setCurrency("IDR", java.lang.Double.parseDouble(itemTrainModel.price), false)
                model.dataCardTrain.price = sPrice

                val modelItem = ModelItemTrip()
                modelItem.id = itemTrainModel.idTrain
                modelItem.name = itemTrainModel.titleTrain
                modelItem.progress = itemTrainModel.progressTrain
                modelItem.status = itemTrainModel.status

                itemsTrip.add(modelItem)
                list.add(model)
            }
        }

        if (dataAccomodation.itemFlightModel.isNotEmpty()) {

            Log.d("xixxx", "gohere 22")
            val data = CartModel()
            data.typeCard = Constants.TYPE_HEADER
            data.dataHeader = headerFlight(dataAccomodation.itemFlightModel)

            list.add(data)

            dataAccomodation.itemFlightModel.forEach {
                val model = CartModel()


                model.typeCard = Constants.TYPE_FLIGHT

                model.dataCardFlight.idFlight = it.idFlight
                model.dataCardFlight.progressFlight = it.progressFLight
                model.dataCardFlight.imageFlight = it.imageFlight
                model.dataCardFlight.status = it.status
                model.dataCardFlight.pnrCode = it.pnrCode
                model.dataCardFlight.pnrId = it.pnrId
                model.dataCardFlight.tripId = tripSummary.tripId
                model.dataCardFlight.titleFlight = it.airlineName
                model.dataCardFlight.numberSheet = it.seatNumber
                model.dataCardFlight.flightNumber = it.flightNumber
                model.dataCardFlight.typeFlight = it.type
                model.dataCardFlight.duration = it.duration

                model.dataCardFlight.stationOrigin = it.originName
                model.dataCardFlight.stationDestination = it.destinationName
                model.dataCardFlight.dateArrival = it.dateArrival
                model.dataCardFlight.dateDeparture = it.dateDeparture
                model.dataCardFlight.timeArrival = it.timeArrival
                model.dataCardFlight.isComply = it.isComply
                model.dataCardFlight.timeDeparture = it.timeDeparture
                model.dataCardFlight.price = it.price

                model.dataCardFlight.priceItem = it.priceItem
                model.dataCardFlight.flightSegmentItem = it.flightSegmentItem

                model.dataCardFlight.classFlight = it.classFlight
                model.dataCardFlight.codeFlight = it.pnrCode
                model.dataCardFlight.departureFlight = it.originDestination
                model.dataCardFlight.arrivalFlight = it.nextDestination

                model.dataCardFlight.departure = it.origin
                model.dataCardFlight.arrival = it.destination
                model.dataCardFlight.airportDeparture = it.airportDeparture
                model.dataCardFlight.airportArrival = it.airportArrival

                val modelItem = ModelItemTrip()
                modelItem.id = it.idFlight
                modelItem.name = it.airlineName
                modelItem.progress = it.progressFLight
                modelItem.status = it.status

                itemsTrip.add(modelItem)
                list.add(model)
            }
        }
        if (dataAccomodation.itemHotelModel.isNotEmpty()) {

            val data = CartModel()
            data.typeCard = Constants.TYPE_HEADER
            data.dataHeader = headerHotel()


            list.add(data)

            dataAccomodation.itemHotelModel.forEach {
                val model = CartModel()
                model.typeCard = Constants.TYPE_HOTEL

                model.dataCardHotel.image = it.image
                model.dataCardHotel.status = it.status
                model.dataCardHotel.nameHotel = it.nameHotel
                model.dataCardHotel.tripIdHotel = it.tripItemId
                model.dataCardHotel.checkIn = it.checkIn
                model.dataCardHotel.checkOut = it.checkOut
                model.dataCardHotel.address = it.address
                model.dataCardHotel.dateBooking = it.dateBooking
                model.dataCardHotel.starRating = it.starRating
                model.dataCardHotel.price = it.price
                model.dataCardHotel.reasonCode = it.reasonCode
                model.dataCardHotel.pnrHotel = it.pnrHotel
                model.dataCardHotel.typeHotel = it.typeHotel
                model.dataCardHotel.descreption = it.description

                val modelItem = ModelItemTrip()
                modelItem.id = it.pnrHotel
                modelItem.name = it.nameHotel
                if ("pending".equals(it.status.toLowerCase())) {
                    modelItem.progress = "100.00"
                } else if ("saved".equals(it.status.toLowerCase())) {
                    modelItem.progress = "50.00"
                } else {
                    modelItem.progress = "100.00"
                }
                modelItem.status = it.status

                itemsTrip.add(modelItem)

                list.add(model)
            }
        }

        return list
    }

    private fun headerHotel(): CartHeaderModel {
        val header = CartHeaderModel()
        header.titleHeader = "Hotel Summary"
        header.typeTrip = "OneWay"
        header.typeHeader = Constants.TYPE_HOTEL
        return header
    }

    private fun headerTrain(): CartHeaderModel {
        val header = CartHeaderModel()
        header.titleHeader = "Train Summary"
        header.typeTrip = "OneWay"
        header.typeHeader = Constants.TYPE_TRAIN
        return header
    }

    private fun headerFlight(data: List<ItemFlightModel>): CartHeaderModel {
        val header = CartHeaderModel()
        header.titleHeader = "Flight Summary"
        if (data[0].type == 0) {
            header.typeTrip = "One Way"
        } else {
            header.typeTrip = "Round Trip"
        }
        header.typeHeader = Constants.TYPE_FLIGHT
        return header
    }

    private fun getDataSummary(tripId: String) {
        nestedScrollUp()
        GetDataGeneral(getBaseUrl()).getDataSummary(getToken(), tripId, object : CallbackSummary {
            override fun successLoad(summaryModel: SummaryModel) {
                setLog(Serializer.serialize(summaryModel))
                tripSummary = summaryModel
                hideLoadingOpsicorp()
                updateViewSummary()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                failedWarning(message)
            }
        })
    }

    fun showPageListDataPersonalTrip() {
        nestedScrollUp()
        pagePosition = DETAIL_PERSONAL_TRIP
        page_list_bisnis_trip.visibility = View.GONE
        page_list_detail_trip.visibility = View.GONE
        page_detail_personal_trip.visibility = View.VISIBLE
        page_detail_personal_trip.callbackOnclickButton(this)
    }

    override fun updateViewReserved() {
        getDataSummary(idTripPlant)
    }

    override fun updateViewSaved() {
        viewSavedListener()
    }

    override fun updateViewReservedPersonalTrip() {
        getDataSummary(idTripPlant)
    }

    override fun updateViewSavedPersonalTrip() {
        viewSavedListener()
    }

    private fun viewSavedListener() {
        btn_submit_trip_plant.background = resources.getDrawable(R.drawable.rounded_button_gray)
        tv_warning_cart.text = getString(R.string.warning_status_booking_saved)
        line_warning.visibility = View.VISIBLE
        line_warning.setBackgroundColor(resources.getColor(R.color.colorYellowButton))
    }

    override fun onBackPressed() {
        backListener()
    }

    private fun backListener() {
        when (pagePosition) {
            LIST_BISNIS_TRIP -> {
                gotoActivity(HomeActivity::class.java)
            }
            DETAIL_BISNIS_TRIP -> {
                getDataCart()
            }
            DETAIL_PERSONAL_TRIP -> {
                gotoActivity(HomeActivity::class.java)
            }
        }
    }


    class ModelItemTrip {
        var id = ""
        var name = ""
        var progress = ""
        var status = ""
    }

    fun showValidationButtonSubmit() {

        if (itemsTrip.filter { it.progress != "100.00" }.isNotEmpty()) {
            btn_submit_trip_plant.background = resources.getDrawable(R.drawable.rounded_button_gray)
            line_warning.visibility = View.VISIBLE
            tv_warning_cart.text = "Please wait.. We try to connecting ${itemsTrip.filter { it.progress != "100.00" }.first().name} server"
        } else {
            if (itemsTrip.filter { it.status == "Expired" }.isNotEmpty()) {
                btn_submit_trip_plant.background = resources.getDrawable(R.drawable.rounded_button_gray)
                line_warning.visibility = View.GONE
            } else {
                if (itemsTrip.filter { it.status.toLowerCase().contains("saved") }.isNotEmpty()) {
                    btn_submit_trip_plant.background = resources.getDrawable(R.drawable.rounded_button_gray)
                    tv_warning_cart.text = "Please wait until your trip item status Reserved"
                    line_warning.visibility = View.VISIBLE
                    line_warning.setBackgroundColor(resources.getColor(R.color.colorYellowButton))
                }
                /*else if (itemsTrip.filter { it.status.toLowerCase().contains("pending") }.isNotEmpty()){

                }*/
                else {
                    btn_submit_trip_plant.background = resources.getDrawable(R.drawable.rounded_button_yellow)
                    line_warning.visibility = View.GONE
                }
            }

        }
    }

    fun submitTripPlant(view: View) {
        if (btn_submit_trip_plant.background.constantState == resources.getDrawable(R.drawable.rounded_button_gray).constantState) {
            setLog("notReady")
        } else {
            var tripPlanId = tripSummary.tripId
            val bundle = Bundle()
            bundle.putString(Constants.TRIP_PLAN_ID, tripPlanId)

            gotoActivityWithBundle(PaymentActivity::class.java, bundle)
            /*if (tripSummary.tripCode.contains("PT")) {
                Constants.isBisnisTrip = false
                if (Constants.isBisnisTrip.equals(false)) {
                    var tripPlanId = tripSummary.tripId
                    val bundle = Bundle()
                    bundle.putString(Constants.TRIP_PLAN_ID, tripPlanId)

                    gotoActivityWithBundle(PaymentActivity::class.java, bundle)
                } else {
                    getSubmitTripPlant()
                }
            } else {
                getSubmitTripPlant()
            }*/
        }
    }

    fun getSubmitTripPlant() {
        showLoadingOpsicorp(true)
        GetDataTripPlane(getBaseUrl()).submitTripPlant(getToken(), getDataTripItem(), object : CallbackSubmitTripPlant {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                val bundle = Bundle()
                bundle.putString(Constants.TRIP_CODE,data.tripCode)
                bundle.putString(Constants.ID_TRIP_PLANE,data.idTripPlane)
                gotoActivityWithBundle(SuccessView::class.java,bundle)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAlert("Sorry", message)
            }
        })
    }

    private fun getDataTripItem(): HashMap<String, Any> {
        val model = SubmitTripPlant()
        model.startDate = tripSummary.startDate
        model.returnDate = tripSummary.returnDate
        model.origin = tripSummary.origin
        model.destination = tripSummary.destination
        model.type = tripSummary.type
        model.tripParticipants = getDataParticipant()
        model.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        model.purpose = tripSummary.purpose
        model.id = tripSummary.tripId
        model.code = tripSummary.tripCode
        model.contact = getContactRequest()
        return Globals.classToHasMap(model, SubmitTripPlant::class.java)
    }

    private fun getContactRequest(): ContactRequest {
        val contactPerson = ContactRequest()
        contactPerson.lastName = getProfile().lastName
        contactPerson.firstName = getProfile().firstName
        return contactPerson
    }

    private fun getDataParticipant(): List<TripParticipantsItem> {
        val participants = ArrayList<TripParticipantsItem>()

        if (tripSummary.tripParticipantModels.isNotEmpty()) {
            tripSummary.tripParticipantModels.forEach {
                val data = TripParticipantsItem()
                data.budgetId = it.budgetId
                data.costCenterId = it.costId
                data.employeeId = it.employId
                participants.add(data)
            }
        }

        return participants
    }

    override fun btnBack() {
        backListener()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        gotoAddItem()
    }

    fun gotoAddItem() {
        if (tripSummary.contact.employeeId.equals(getProfile().employId)) {
            val model = SuccessCreateTripPlaneModel()
            model.purpose = tripSummary.purpose
            model.idTripPlane = tripSummary.tripId
            model.status = tripSummary.status
            model.tripCode = tripSummary.tripCode
            model.createDate = tripSummary.creationDate
            model.timeExpired = tripSummary.expiredRemaining
            model.destinationName = tripSummary.destinationName
            model.destinationId = tripSummary.destination
            model.originId = tripSummary.origin
            model.originName = tripSummary.originName
            model.startDate = tripSummary.startDate
            model.endDate = tripSummary.returnDate
            model.buggetId = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().budgetId
            model.costCenter = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().costId
            Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(model)

            setLog(Constants.DATA_SUCCESS_CREATE_TRIP)

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm")
            if (Date().before(dateFormatter.parse(tripSummary.returnDate))) {
                val bundle = Bundle()
                bundle.putInt(Constants.TYPE_ACCOMODATION, Constants.ADD_ITEM_PERSONAL_TRIP)
                gotoActivityWithBundle(AccomodationActivity::class.java, bundle)
            } else {
                showAlert(getString(R.string.message_sorry), getString(R.string.info_expired_trip))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.GET_SEAT_MAP -> {
                if (resultCode == Activity.RESULT_OK) {
                    idTripPlant = intent.getStringExtra(Constants.ID_TRIP_PLANE)
                    showLoadingOpsicorp(false)
                    getDataSummary(idTripPlant)
                }
            }
        }
    }

    private lateinit var mReceiver: BroadcastReceiver

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter(Constants.ACT_CART)

        /*

         */

        mReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                //Get message from intent
                //val tp_status = intent.getStringExtra("tp_status")

                //tripId = intent.getStringExtra(Constants.KEY_INTENT_TRIPID)
                //tripCode = intent.getStringExtra(Constants.KEY_INTENT_TRIP_CODE)
                //employIdUser = getProfile().employId

                //showLoadingOpsicorp(true)

                val vProgress = intent.getStringExtra("vProgress")
                val vText = intent.getStringExtra("vText")
                val vPnrId = intent.getStringExtra("vPnrId")
                val PnrCode = intent.getStringExtra("PnrCode")

                tvProgress.text = vProgress
                tvTestStatus.text = vText
                tvpnrid.text = vPnrId
                tvpnrcode.text = PnrCode

                val prog = (vProgress!!.toDouble()).toInt()
                progress_flight.progress = prog

//                tv_status_test.text = vText
//                tv_pnr_test.text = PnrCode

            }
        }

        if (intentFilter != null || mReceiver != null) {
            try {
                this.registerReceiver(mReceiver, intentFilter)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            this.unregisterReceiver(mReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}
