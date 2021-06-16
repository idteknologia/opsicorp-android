package com.opsigo.travelaja.module.approval.activity

import android.R.attr.label
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.view_accomodation.activity.AccomodationActivity
import com.opsigo.travelaja.module.approval.dialog.ListParticipantDialog
import com.opsigo.travelaja.module.approval.summary.ParticipantAdapter
import com.opsigo.travelaja.module.approval.summary.ParticipantModel
import com.opsigo.travelaja.module.approval.summary.SummaryAdapter
import com.opsigo.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.opsigo.travelaja.module.home.activity.HomeActivity
import com.opsigo.travelaja.module.item_custom.barcode.popup.QRPopUp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Constants.TYPE_ACCOMODATION
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.detail_trip_activity_view.*
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.ApprovalAllRequest
import opsigo.com.datalayer.request_model.ApprovePerPaxRequest
import opsigo.com.datalayer.request_model.ApproverPerItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.*
import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.callback.CallbackSummary
import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel
import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.summary.SummaryModel
import opsigo.com.domainlayer.model.summary.SummaryModelItems
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel
import opsigo.com.domainlayer.model.summary.TripParticipantsItemModel
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailTripActivity : BaseActivity(), View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener,
        ListParticipantDialog.CallbackDialog {

    override fun getLayout(): Int {
        return R.layout.detail_trip_activity_view
    }

    var isShareQR = false
    var tripCode = ""
    var employIdUser = ""
    var tripId = ""
    var tripSummary = SummaryModel()
    var tripCost = EstimatedCostTravelRequestModel()
    val dataItems = ArrayList<SummaryModelItems>()
    var isUpdateSummary = false

    val dataParticipant = ArrayList<ParticipantModel>()
    val dataApproval = ArrayList<ParticipantModel>()
    val adapterParticpant by lazy { ParticipantAdapter(this) }
    val adapterApproval by lazy { ParticipantAdapter(this) }
    val adapterItemOrder by lazy { SummaryAdapter(this) }

    var dataAttachment = ArrayList<UploadModel>()
    val adapter by inject<AttachmentAdapter> { parametersOf(dataAttachment) }


    override fun OnMain() {
        initRecyclerViewApproval()
        initRecyclerViewParticipant()
        initRecyclerViewAttachment()
        initRecyclerViewItem()

        toolbar.callbackOnclickToolbar(this)
        toolbar.setTitleBar(getString(R.string.detail_tripplan))
        toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        image_barcode.setOnClickListener(this)

        tv_share_qrcode.visibility = View.GONE

        icCopyClipboard.setOnClickListener {
            copyToClip()
        }
        tv_tripcode.setOnClickListener {
            if (isShareQR) {
                tv_share_qrcode.visibility = View.GONE
                isShareQR = false
            } else {
                tv_share_qrcode.visibility = View.VISIBLE
                isShareQR = true
            }
        }

        if (intent.extras != null && intent.extras!!.containsKey(Constants.KEY_INTENT_NOTIF_ID_INT)) {

            val intent = intent
            val NOTIF_ID_INT = intent.getIntExtra(Constants.KEY_INTENT_NOTIF_ID_INT, -1)

            //clear notification
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NOTIF_ID_INT)

        }

        if (intent.extras != null && intent.extras!!.containsKey(Constants.KEY_INTENT_TRIP_CODE)) {
            getSummary()
        }
    }

    private fun copyToClip() {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(label.toString(), tv_tripcode.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(applicationContext, "Copied To Clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun getSummary() {
        tripId = intent.extras?.getString(Constants.KEY_INTENT_TRIPID).toString()
        tripCode = intent.extras?.getString(Constants.KEY_INTENT_TRIP_CODE).toString()
        employIdUser = getProfile().employId

        showLoadingOpsicorp(true)
        GetDataGeneral(getBaseUrl()).getDataSummary(getToken(), tripId, object : CallbackSummary {
            override fun successLoad(summaryModel: SummaryModel) {
                setLog("cobak")
                setLog(Serializer.serialize(tripSummary.tripParticipantModels))

                tripSummary = summaryModel
                mapperlistParticipantAndApproval()
                postEstimateCost()
                hideLoadingOpsicorp()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                failedWarning(message)
            }
        })

    }

    private fun postEstimateCost() {
        GetDataTravelRequest(getBaseUrl()).getEstimatedCost(Globals.getToken(), dataPurpose(), object : CallbackEstimatedCostTravelRequest {
            override fun successLoad(data: EstimatedCostTravelRequestModel) {
                tripCost = data

            }

            override fun failedLoad(message: String) {

            }

        })
    }

    private fun dataPurpose(): HashMap<Any, Any> {
        val dataRequest = RequestEstimatedCost()
        dataRequest.tripType = tripSummary.businessTripType
        dataRequest.purpose = tripSummary.purpose
        dataRequest.startDate = tripSummary.startDate
        dataRequest.endDate = tripSummary.returnDate
        dataRequest.golper = 2
        dataRequest.routes = ArrayList()
        val mDataRoutes = ArrayList<RoutesItem>()
        tripSummary.routes.forEachIndexed { index, routesItinerary ->
            val dataRoutes = RoutesItem()
            dataRoutes.transportation = routesItinerary.transportation
            dataRoutes.departureDate = DateConverter().getDate(routesItinerary.departureDateView, "dd-MM-yyyy", "dd MMM yyyy")
            dataRoutes.departureDateView = DateConverter().getDate(routesItinerary.departureDateView, "dd-MM-yyyy", "dd MMM yyyy")
            dataRoutes.origin = routesItinerary.origin
            dataRoutes.destination = routesItinerary.destination
            mDataRoutes.add(dataRoutes)
        }
        dataRequest.routes = mDataRoutes

        dataRequest.isDomestic = tripSummary.isDomestic
        dataRequest.withPartner = false

        return Globals.classToHashMap(dataRequest, RequestEstimatedCost::class.java)
    }

    private fun mapperlistParticipantAndApproval() {
        setDataApproval()
        setDataSummary()
        setDataAttacMent(tripSummary.attactment)
    }


    fun initRecyclerViewApproval() {
        tv_list_approval.visibility = View.VISIBLE
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_approval.layoutManager = layoutManager
        rv_approval.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_approval.adapter = adapterApproval
    }

    private fun initRecyclerViewParticipant() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_participant.layoutManager = layoutManager
        rv_participant.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_participant.adapter = adapterParticpant

        adapterParticpant.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views) {
                    -1 -> {
                        if (Constants.isApproval) {
                            if (dataParticipant[position].employId != getProfile().employId) {
                                val bundle = Bundle()
                                bundle.putString(Constants.KEY_INTENT_TRIPID, tripId)
                                bundle.putString(Constants.Summary, Serializer.serialize(tripSummary, SummaryModel::class.java))
                                bundle.putString(Constants.ID_PARTICIPANT, dataParticipant[position].idParticipant)
                                bundle.putString(Constants.JOB_TITLE, dataParticipant[position].jobtitle)
                                bundle.putString(Constants.NAME_PARTICIPANT, dataParticipant[position].name)
                                bundle.putString(Constants.COSTCENTER, dataParticipant[position].costCenterCode + " - " + dataParticipant[position].costCenterName)
                                bundle.putString(Constants.BUDGET, dataParticipant[position].budgetCode + " - " + dataParticipant[position].budgetName)
                                bundle.putString(Constants.EMPLOY_ID, dataParticipant[position].employId)
                                bundle.putString(Constants.STATUS_MEMBER, dataParticipant[position].status)
                                val status = dataParticipant[position].status
                                gotoActivityResultWithBundle(DetailParticipantActivity::class.java, bundle, Constants.DETAIL_PERTICIPANT_INTENT)

                            }
                        }
                    }
                }
            }
        })
    }

    fun initRecyclerViewAttachment() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_attachment.layoutManager = layoutManager
        rv_attachment.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_attachment.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views) {
                    R.id.image_delet -> {
                        dataAttachment.removeAt(position)
                        adapter.notifyItemChanged(position)
                        removeAttactment(position)
                    }
                }
            }
        })

        adapter.setData(dataAttachment)
    }

    private fun removeAttactment(position: Int) {

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
        builder.setPositiveButton("Ok") { dialog, which -> backtoDashboard() }
        builder.create().show()
    }

    private fun backtoDashboard() {
        if (Constants.FROM_SUCCESS_CHECKOUT) {
            gotoActivity(HomeActivity::class.java)
        } else {
            finish()
        }
    }


    private fun setDataAttacMent(mData: ArrayList<UploadModel>) {
        if (mData.isNotEmpty()) {
            rv_attachment.visibility = View.VISIBLE
            dataAttachment.clear()
            dataAttachment.addAll(mData)
            adapter.setData(dataAttachment)
            title_attachment.visibility = View.VISIBLE
            line_dot_attacthment.visibility = View.VISIBLE
            title_attachment.text = "Attachment Files"
        } else {
            rv_attachment.visibility = View.GONE
//            title_attachment.text = "No attachment Files"
            line_dot_attacthment.visibility = View.GONE
            title_attachment.visibility = View.GONE
        }

    }

    private fun setDataApproval() {
        dataApproval.clear()
        dataParticipant.clear()

        if (tripSummary.tripParticipantModels.filter {
                    it.employId == getProfile().employId
                }.isNullOrEmpty()) {
            // get approval by id participant
            tripSummary.tripParticipantModels.filter {
                it.employId == getProfile().employId
            }.forEachIndexed { index, tripParticipantsItemModel ->
                tripParticipantsItemModel.dataApproval.forEachIndexed { index, participantModel ->
                    val mDataParticipant = ParticipantModel()

                    mDataParticipant.status = tripParticipantsItemModel.status
                    mDataParticipant.isApproval = true
                    mDataParticipant.jobtitle = participantModel.jobTitle
                    mDataParticipant.budgetCode = "B00021"
                    mDataParticipant.jobtitle = "Approval (${index + 1})"
                    mDataParticipant.budgetName = "Budget Mobile Developer"
                    mDataParticipant.name = participantModel.name
                    mDataParticipant.imgUrl = participantModel.image

                    dataApproval.add(mDataParticipant)
                }
            }
        }

        //get participant by approval
        try {
            tripSummary.tripParticipantModels.forEachIndexed { index, tripParticipantsItemModel ->
                tripParticipantsItemModel.dataApproval.filter {
                    it.employId == getProfile().employId
                }.forEachIndexed { index, participantModel ->
                    val model = ParticipantModel()
                    model.name = tripParticipantsItemModel.fullName
                    model.employId = tripParticipantsItemModel.employId
                    model.idParticipant = tripParticipantsItemModel.id
                    model.status = tripSummary.statusView
                    model.isApproval = false
                    model.jobtitle = tripParticipantsItemModel.jobtitle
                    model.costCenterCode = tripParticipantsItemModel.costCenterCode
                    model.costCenterName = tripParticipantsItemModel.costCenterName
                    model.budgetCode = tripParticipantsItemModel.budgetCode
                    model.budgetName = tripParticipantsItemModel.budgetName
                    model.approval = tripParticipantsItemModel.dataApproval

                    model.imgUrl = ""

                    tv_budget.text = model.budgetCode + " - " + model.budgetName
                    tv_cost_center.text = model.costCenterCode + " - " + model.costCenterName

                    dataParticipant.add(model)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (dataApproval.isNotEmpty()) {
            tv_list_approval.visibility = View.VISIBLE
            tv_notice_title.visibility = View.VISIBLE
            rv_approval.visibility = View.VISIBLE
            tv_list_approval.text = "List Approver (${dataApproval.size})"
            adapterApproval.setData(dataApproval)
        } else {
            rv_approval.visibility = View.GONE
            tv_list_approval.visibility = View.GONE
            tv_notice_title.visibility = View.GONE
        }

        if (dataParticipant.isNotEmpty()) {
            tv_list_participant_num.visibility = View.VISIBLE
            tv_notice_participant.visibility = View.VISIBLE
            rv_participant.visibility = View.VISIBLE
            tv_list_participant_num.text = "List Participant (${dataParticipant.size})"
            adapterParticpant.setData(dataParticipant)
        } else {
            rv_participant.visibility = View.GONE
            tv_list_participant_num.visibility = View.GONE
            tv_notice_participant.visibility = View.GONE
        }

        if (!tripSummary.tripParticipantModels.isNullOrEmpty()) {
            tv_cost_center.text = "${tripSummary.tripParticipantModels[0].costCenterCode} - ${tripSummary.tripParticipantModels[0].costCenterName}"
            tv_mount.text = tripSummary.totalAllowance
        }
    }

    fun setDataSummary() {
        image_barcode.setImageBitmap(Globals.stringToBarcodeImage(tripCode))
        tv_total_amount.text = "IDR ${Globals.formatAmount(tripSummary.totalExpenditure.split(".")[0])}"
        tv_tripcode.text = tripCode
        tv_status.text = tripSummary.statusView

        if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.CompletelyApproved) {
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_green)
            tv_expired.visibility = View.GONE
            line_button_approve_reject.visibility = View.GONE
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.CompletelyRejected) {
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_red)
            tv_expired.visibility = View.GONE
            line_button_approve_reject.visibility = View.GONE
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.TripCompleted) {
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_green)
            tv_expired.visibility = View.GONE
            line_button_approve_reject.visibility = View.GONE
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.PartiallyApprovedAndReject) {
            validationButtonApproval()
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_red)
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.PartiallyApproved) {
            validationButtonApproval()
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_green)
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.PartiallyRejected) {
            validationButtonApproval()
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_red)
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.Draft) {
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_gray)
            tv_expired.visibility = View.GONE
            tv_total_amount.visibility = View.GONE
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.WaitingForApproval) {
            validationButtonApproval()
            tv_status.text = "Waiting"
        }

        id_tv_create_date.text = "Created Date : ${tripSummary.creationDate}"
        tv_expired.text = "${tripSummary.expiredRemaining} left to expired"
        tv_purpose.text = tripSummary.purpose
        tv_destination.text = tripSummary.destinationName
        tv_start_date.text = DateConverter().setDateFormatDayEEEddMMM(tripSummary.startDate)
        tv_end_date.text = DateConverter().setDateFormatDayEEEddMMM(tripSummary.returnDate)

        if (tripSummary.statusView == "Completely Approved" || tripSummary.statusView == "Completely Rejected") {
            line_add_trip_item.visibility = View.GONE
        } else {
            line_add_trip_item.visibility = View.VISIBLE
        }

        tv_notes.text = tripSummary.remark

        try {
            if (tripSummary.remark?.isEmpty()!!) {
                tv_notes.visibility = View.GONE
                titleNotes.visibility = View.GONE
            } else {
                tv_notes.visibility = View.VISIBLE
                titleNotes.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            tv_notes.visibility = View.GONE
            titleNotes.visibility = View.GONE
        }

        initDataItem()
        initViewDraft()

        if (isUpdateSummary) {
            line_button_approve_reject.visibility = View.GONE
        }
    }

    private fun initViewDraft() {
        val from = intent.extras?.getString(Constants.KEY_FROM)
        if (from.equals(Constants.FROM_DRAFT)) {
            line_button_approve_reject.visibility = View.GONE
            line_button_submit_trip.visibility = View.VISIBLE
            addDataAccomodation()
        } else {
            line_button_submit_trip.visibility = View.GONE
        }
    }

    private fun initDataItem() {

        if (dataParticipant.isNotEmpty()) {
            addDataAccomodation()
        } else {
            line_items.visibility = View.GONE
            rv_item_order.visibility = View.GONE
            title_trip_total.visibility = View.GONE
            lineBottomItemOrder.visibility = View.GONE
        }
    }

    private fun validationButtonApproval() {
        if (dataParticipant.isEmpty()) {
            line_button_approve_reject.visibility = View.GONE
        } else {
            line_button_approve_reject.visibility = View.VISIBLE
        }
    }


    private fun addDataAccomodation() {
        rv_item_order.visibility = View.VISIBLE
        line_items.visibility = View.VISIBLE
        title_trip_total.visibility = View.VISIBLE

        dataItems.clear()

        val dataAccomodation = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first()
        val totalAccomdation = dataAccomodation.itemFlightModel.size + dataAccomodation.itemTrainModel.size + dataAccomodation.itemHotelModel.size
        title_trip_total.text = "Your Trip Items Detail (${totalAccomdation})"



        var number = 1

        if (dataAccomodation.itemFlightModel.isNotEmpty()) {
            val mDataHeaderFlight = SummaryModelItems()
            mDataHeaderFlight.typeCard = "HEADER"
            mDataHeaderFlight.title = number.toString() + ". Flight Summary"
            mDataHeaderFlight.reason = reasonItemFlight(dataAccomodation, "Flight")
            if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.Draft) {
                mDataHeaderFlight.reason = ""
            }
            dataItems.add(mDataHeaderFlight)
            number += 1
        }

        dataAccomodation.itemFlightModel.forEachIndexed { index, itemFlightModel ->
            val mDataFlighModel = SummaryModelItems()
            mDataFlighModel.typeCard = "FLIGHT"
            mDataFlighModel.title = "flight"
            mDataFlighModel.dataItemFlight = itemFlightModel
            dataItems.add(mDataFlighModel)
        }

        if (dataAccomodation.itemTrainModel.isNotEmpty()) {
            val mDataHeaderTrain = SummaryModelItems()
            mDataHeaderTrain.typeCard = "HEADER"
            mDataHeaderTrain.title = number.toString() + ". Train Summary"
            if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.Draft) {
                mDataHeaderTrain.reason = ""
            }
            dataItems.add(mDataHeaderTrain)
            number += 1
        }

        dataAccomodation.itemTrainModel.forEachIndexed { index, itemTrainModel ->
            val mDataTrainModel = SummaryModelItems()
            mDataTrainModel.typeCard = "TRAIN"
            mDataTrainModel.title = "train"
            mDataTrainModel.dataItemTrain = itemTrainModel
            dataItems.add(mDataTrainModel)
        }

        if (dataAccomodation.itemHotelModel.isNotEmpty()) {
            val mDataHeaderHotel = SummaryModelItems()
            mDataHeaderHotel.typeCard = "HEADER"
            mDataHeaderHotel.title = number.toString() + ". Hotel"
            mDataHeaderHotel.reason = reasonItemFlight(dataAccomodation, "Hotel")
            if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.Draft) {
                mDataHeaderHotel.reason = ""
            }
            dataItems.add(mDataHeaderHotel)
            number += 1
        }

        dataAccomodation.itemHotelModel.forEachIndexed { index, itemTrainModel ->
            val mData = SummaryModelItems()
            mData.typeCard = "HOTEL"
            mData.title = "hotel"
            mData.dataItemHotel = itemTrainModel
            dataItems.add(mData)
        }

        adapterItemOrder.setData(dataItems)
    }

    private fun reasonItemFlight(dataAccomodation: TripParticipantsItemModel, type: String): String {
        var dataTotal = 0

        dataAccomodation.dataApproval.forEachIndexed { index, participantModel ->
            when (type) {
                "Flight" -> {
                    if (participantModel.followUpFlight.isNotEmpty()) {
                        dataTotal++
                    }
                }
                "Train" -> {
                    if (participantModel.followUpTrain.isNotEmpty()) {
                        dataTotal++
                    }
                }
                "Hotel" -> {
                    if (participantModel.followUpHotel.isNotEmpty()) {
                        dataTotal++
                    }
                }
            }

        }

        if (dataTotal == dataAccomodation.dataApproval.size) {
            return "Approved"
        } else if (dataTotal == 0) {
            return ""
        } else {
            return "Approved by ${dataTotal} (${dataAccomodation.dataApproval.size})"
        }
    }

    private fun initRecyclerViewItem() {

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_item_order.layoutManager = layoutManager
        rv_item_order.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_item_order.adapter = adapterItemOrder


        adapterItemOrder.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views) {
                    Constants.OPTION_FLIGHT_APPROVE -> {
                        approveOrRejectItemRequest("1", "0")
                    }
                    Constants.OPTION_TRAIN_APPROVE -> {
                        approveOrRejectItemRequest("1", "2")
                    }
                    Constants.OPTION_HOTEL_APPROVE -> {
                        approveOrRejectItemRequest("1", "1")
                    }

                    Constants.OPTION_FLIGHT_REJECT -> {
                        approveOrRejectItemRequest("0", "0")
                    }
                    Constants.OPTION_TRAIN_REJECT -> {
                        approveOrRejectItemRequest("0", "2")
                    }
                    Constants.OPTION_HOTEL_REJECT -> {
                        approveOrRejectItemRequest("0", "1")
                    }
                }
            }
        })
    }

    fun approveOrRejectItemRequest(action: String, type: String) {
        showDialog("")
        GetDataApproval(getBaseUrl()).approveItem(getToken(),
                getData(action, type), object : CallbackApprovAll {
            override fun successLoad(data: String) {
                isUpdateSummary = true;
                updateSummary()
            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert("Sorry", message)
            }
        })
    }

    private fun updateSummary() {
        GetDataGeneral(getBaseUrl()).getDataSummary(getToken(), tripId, object : CallbackSummary {
            override fun successLoad(summaryModel: SummaryModel) {
               tripSummary = summaryModel
                mapperlistParticipantAndApproval()
                hideDialog()
            }

            override fun failedLoad(message: String) {
                hideDialog()
                failedWarning(message)
            }
        })
    }

    private fun getData(action: String, type: String): HashMap<Any, Any> {
        val data = ApproverPerItemRequest()
        data.approvalAction = action
        data.tripType = type
        data.employeeId = getProfile().employId
        data.tripId = tripSummary.tripId
        data.tripParticipantId = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().id
        return Globals.classToHashMap(data, ApproverPerItemRequest::class.java)
    }


    override fun btnBack() {
        backtoDashboard()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        backListener()
    }

    private fun backListener() {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_list_my_booking, null)
        val btnDetail = layout.findViewById(R.id.tv_view_detail) as TextView
        val btnRemove = layout.findViewById(R.id.tv_remove_list_data) as TextView

        btnDetail.text = "Back"
        btnRemove.text = "Help and guide"

        btnDetail.setOnClickListener {
            if (isUpdateSummary) {
                val intent = Intent()
                intent.putExtra("action", "load list")
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                backtoDashboard()
            }
        }

        btnRemove.setOnClickListener {

        }

        Globals.showPopup(toolbar.getImageCart(), layout)
    }


    override fun onClick(view: View?) {
        when (view) {
            image_barcode -> {
                QRPopUp.show(this, tripCode)
            }
        }
    }

    fun submitTripListener(view: View) {
        Log.d("managetrx", "submit")
    }

    fun addTripItemListener(view: View) {
        gotoAddItem()
    }

    fun showApproveAllDialog(view: View) {
        if (Globals.getBaseUrl(applicationContext) == "https://pertamina-dtm3-qa.opsicorp.com/") {
            approveOrRejectItemRequest("1", "1")
            saveToDraft()
        } else {
            ListParticipantDialog(this).create(this, dataParticipant)
        }
    }

    fun rejectListener(view: View) {
        rejectOrApproveSelected("0")
    }

    fun rejectOrApproveSelected(action: String) {
        showDialog("")
        GetDataApproval(getBaseUrl()).approveAll(Globals.getToken(), dataBodyApproved(action), object : CallbackApprovAll {
            override fun successLoad(data: String) {
                hideDialog()
                when (action) {
                    "0" -> {
                        allreadyApprovOrRejected("Success Rejected")
                    }
                    "1" -> {
                        allreadyApprovOrRejected("Success Approved")
                    }
                }

            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert("Sorry", message)
            }
        })
    }

    private fun allreadyApprovOrRejected(string: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(string)
        builder.setPositiveButton("Ok") { dialog, which ->
            val intent = Intent()
            intent.putExtra("action", "load list")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        builder.create().show()
    }

    private fun saveToDraft() {
        GetDataTripPlane(getBaseUrl()).saveAsDraftTripPlant(Globals.getToken(), dataRequest(), object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {

            }

            override fun failedLoad(message: String) {
                showAllert("Sorry", message)
            }

        })
    }

    private fun dataRequest(): HashMap<String, Any> {
        val dataDraft = SaveAsDraftRequestPertamina()
        dataDraft.origin = tripSummary.routes[0].origin
        dataDraft.destination = tripSummary.routes[0].destination
        dataDraft.golper = 2
        dataDraft.purpose = tripSummary.purpose
        dataDraft.businessTripType = tripSummary.businessTripType
        dataDraft.startDate = tripSummary.startDate
        dataDraft.returnDate = tripSummary.returnDate
        dataDraft.type = Globals.getConfigCompany(this).travelingPurposeFormType.toInt()
        dataDraft.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        dataDraft.isDomestic = tripSummary.isDomestic
        dataDraft.remark = tripSummary.remark.toString()
        dataDraft.wbsNo = ""

        dataDraft.routes = ArrayList()
        val mDataRoutes = ArrayList<RoutesItem>()
        tripSummary.routes.forEachIndexed { index, routesItinerary ->
            val dataRoutes = RoutesItem()
            dataRoutes.transportation = routesItinerary.transportation
            dataRoutes.departureDate = DateConverter().getDate(routesItinerary.departureDateView, "dd-MM-yyyy", "yyyy-MM-dd")
            dataRoutes.departureDateView = routesItinerary.departureDateView
            dataRoutes.origin = routesItinerary.origin
            dataRoutes.destination = routesItinerary.destination
            mDataRoutes.add(dataRoutes)
        }
        dataDraft.routes = mDataRoutes

        val attachments = ArrayList<TripAttachmentsItemRequest>()
        dataAttachment.forEachIndexed { index, uploadModel ->
            val mDataAttachments = TripAttachmentsItemRequest()
            mDataAttachments.description = uploadModel.nameImage
            mDataAttachments.url = uploadModel.url
            attachments.add(mDataAttachments)
        }
        dataDraft.tripAttachments = attachments

        dataDraft.tripParticipants = ArrayList()
        val participants = ArrayList<TripParticipantsPertaminaItem>()
        val mDataParticipants = TripParticipantsPertaminaItem()
        mDataParticipants.employeeId = getProfile().employId
        mDataParticipants.useCostCenterOther = false
        mDataParticipants.useCashAdvance = false
        mDataParticipants.cashAdvance = 0
        mDataParticipants.costCenterCode = tripSummary.tripParticipantModels[0].costCenterCode
        mDataParticipants.estFlight = tripCost.estFlight.toInt()
        mDataParticipants.estTransportation = tripCost.estTransportation.toInt()
        mDataParticipants.estTotal = tripCost.total.toInt()
        mDataParticipants.estAllowance = tripCost.estAllowance.toInt()
        mDataParticipants.estAllowanceEvent = tripCost.estAllowanceEvent.toInt()
        mDataParticipants.estLaundry = tripCost.estLaundry.toInt()
        mDataParticipants.estHotel = tripCost.estHotel.toInt()
        participants.add(mDataParticipants)
        dataDraft.tripParticipants = participants

        return Globals.classToHasMap(dataDraft, SaveAsDraftRequestPertamina::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isUpdateSummary) {
            val intent = Intent()
            intent.putExtra("action", "load list")
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            backtoDashboard()
        }
    }

    private fun dataBodyApproved(action: String): HashMap<Any, Any> {
        val mData = ApprovalAllRequest()
        mData.approvalAction = action
        mData.employeeId = getProfile().employId
        mData.tripId = tripId

        return Globals.classToHashMap(mData, ApprovalAllRequest::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.DETAIL_PERTICIPANT_INTENT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val intent = data?.getStringExtra("action")
                    when (intent) {
                        "to_list_refresh" -> {
                            val mData = Intent()
                            mData.putExtra("action", "load list")
                            setResult(Activity.RESULT_OK, mData)
                            finish()
                        }
                        "to_list" -> {
                            backtoDashboard()
                        }
                        "to_detail" -> {
                            updateSummary()
                            isUpdateSummary = true
                        }
                    }
                }
            }
        }
    }

    var dataParticipantSelected = ArrayList<String>()
    var lastParticipantApprove = 0

    override fun selected(data: ArrayList<String>) {
        dataParticipantSelected.clear()
        dataParticipantSelected = data
        if (dataParticipantSelected.isNotEmpty()) {
            approveOrRejectAllpactRequest()
        }
    }

    private fun approveOrRejectAllpactRequest() {
        showDialog("")
        GetDataApproval(getBaseUrl()).approvePerPax(getToken(), getDataPerpact(dataParticipantSelected[lastParticipantApprove]), object : CallbackApprovAll {
            override fun successLoad(data: String) {
                setLog(dataParticipant.filter { it.idParticipant == dataParticipantSelected[lastParticipantApprove] }.first().name)
                setLog(dataParticipant.filter { it.idParticipant == dataParticipantSelected[lastParticipantApprove] }.first().idParticipant)

                lastParticipantApprove++
                if (lastParticipantApprove < dataParticipantSelected.size) {
                    approveOrRejectAllpactRequest()
                } else {
                    hideDialog()
                    successApproveParticipant("Success approve participant ")
                }

            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert("Sorry", "there was a failure to approve ${dataParticipantSelected[lastParticipantApprove]} travel plans ")
            }
        })
    }

    private fun successApproveParticipant(message: String) {
        Log.d("isUpdateSummary", ":2" + isUpdateSummary)
        isUpdateSummary = true

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, which ->
            getSummary()
        }
        builder.create().show()
    }


    private fun getDataPerpact(idParticipant: String): HashMap<Any, Any> {
        val mData = ApprovePerPaxRequest()
        mData.approvalAction = "1"
        mData.employeeId = getProfile().employId
        mData.tripId = tripSummary.tripId
        mData.tripParticipantId = idParticipant
        return Globals.classToHashMap(mData, ApprovePerPaxRequest::class.java)
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
            model.route   = mappingRoutes(tripSummary.routes)
            model.attachment.addAll(addAttacthment())
            model.buggetId = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().budgetId
            model.costCenter = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().costId

            model.businessTripType = tripSummary.businessTripType
            model.remark      = tripSummary.remark.toString()
            model.wbsNo       = tripSummary.wbsNo
            model.isDomestik  = tripSummary.isDomestic
            model.golper      = tripSummary.golper

            Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(model)

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm")
            if (Date().before(dateFormatter.parse(tripSummary.returnDate))) {
                val bundle = Bundle()
                bundle.putInt(TYPE_ACCOMODATION, Constants.KEY_ACCOMODATION)
                gotoActivityWithBundle(AccomodationActivity::class.java, bundle)
            } else {
                showAlert("Sorry", "This Trip Plant return date is expired")
            }
        }
    }

    private fun addAttacthment(): ArrayList<TripAttachmentItemModel> {
        val data = ArrayList<TripAttachmentItemModel>()
        tripSummary.attactment.forEach {
            val model = TripAttachmentItemModel()
            model.description = it.nameImage
            model.url         = it.url
            model.id          = it.id
            setLog(" Test attachment => ${Serializer.serialize(model)}")
            data.add(model)
        }
        return data
    }

    private fun mappingRoutes(routes: ArrayList<RoutesItemPertamina>): ArrayList<RouteMultiCityModel> {
        val data = ArrayList<RouteMultiCityModel>()
        routes.forEach {
            val mData = RouteMultiCityModel()
            mData.destinationName = it.destination
            mData.originName    = it. origin
            mData.dateDeparture = it.departureDate
            data.add(mData)
        }
        return data
    }
}