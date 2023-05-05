package com.mobile.travelaja.module.approval.activity

import android.R.attr.label
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.accomodation.view_accomodation.activity.AccomodationActivity
import com.mobile.travelaja.module.approval.dialog.ListParticipantDialog
import com.mobile.travelaja.module.approval.summary.ParticipantAdapter
import com.mobile.travelaja.module.approval.summary.ParticipantModel
import com.mobile.travelaja.module.approval.summary.SummaryAdapter
import com.mobile.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity.CreateTripPertaminaActivity
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.ApproverAdapter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.ApproverCustomAdapter
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.module.item_custom.barcode.popup.QRPopUp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.my_booking.purchase_list_detail.PurchaseDetailListActivity
import com.mobile.travelaja.module.payment.PaymentActivity
import com.mobile.travelaja.utility.*
import com.mobile.travelaja.utility.Constants.TYPE_ACCOMODATION
import kotlinx.android.synthetic.main.detail_trip_activity_view.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.ApprovalAllRequest
import opsigo.com.datalayer.request_model.ApprovePerPaxRequest
import opsigo.com.datalayer.request_model.ApproverPerItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.*
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel
import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalModel
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import opsigo.com.domainlayer.model.summary.SummaryModel
import opsigo.com.domainlayer.model.summary.SummaryModelItems
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel
import opsigo.com.domainlayer.model.summary.TripParticipantsItemModel
import opsigo.com.domainlayer.model.travel_request.ChangeTripModel
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.airbnb.lottie.network.NetworkFetcher.fetch
import com.tonyodev.fetch2.*

import com.tonyodev.fetch2.Fetch.Impl.getInstance
import opsigo.com.datalayer.datanetwork.*
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.signin.CountryModel
import java.io.File
import java.io.FileWriter


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
    val dataItems = ArrayList<SummaryModelItems>()
    var isUpdateSummary = false

    val dataParticipant = ArrayList<ParticipantModel>()
    val dataApproval = ArrayList<ParticipantModelDomain>()
    val adapterParticpant by lazy { ParticipantAdapter(this) }
    val adapterApproval by lazy { ApproverCustomAdapter(this) }
    val adapterItemOrder by lazy { SummaryAdapter(this) }
    var dataAttachment = ArrayList<UploadModel>()
    val adapter by inject<AttachmentAdapter> { parametersOf(dataAttachment) }

    override fun OnMain() {

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
        swipeRefreshDetail.setOnRefreshListener {
            getSummary()
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

        getDataCity(getToken())

    }

    fun getDataCity(token: String) {
        GetDataTripPlane(getBaseUrl()).getDataCity(token,object : CallbackListCityTrip {
            override fun failedLoad(message: String) {

            }

            override fun successLoad(data: ArrayList<SelectNationalModel>) {
                Constants.DATA_CITY = data

            }
        })
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
                swipeRefreshDetail.isRefreshing = false
                tripSummary = summaryModel
                mapperlistParticipantAndApproval()
                hideLoadingOpsicorp()
                initRecyclerViewApproval()

            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                failedWarning(message)
            }
        })

    }

    private fun dataPurpose(): HashMap<Any, Any> {
        val dataRequest = RequestEstimatedCost()
        dataRequest.tripType = tripSummary.businessTripType
        dataRequest.purpose = tripSummary.purpose
        dataRequest.startDate = tripSummary.startDate
        dataRequest.endDate = tripSummary.returnDate

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
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_approval.layoutManager = layoutManager
        rv_approval.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_approval.adapter = adapterApproval

        dataApproval.clear()
        dataApproval.addAll(tripSummary.tripParticipantModels.first().dataApproval)
        val totalApprover = dataApproval.size
        tv_list_approval.text = "${getString(R.string.list_approver)} (${totalApprover})"
        adapterApproval.setData(dataApproval)

        if (dataApproval.isEmpty()) {
            tv_list_approval.gone()
            rv_approval.gone()
        } else {
            tv_list_approval.visible()
            rv_approval.visible()
        }
    }

    private fun initRecyclerViewParticipant() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_participant.layoutManager = layoutManager
        rv_participant.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_participant.adapter = adapterParticpant

        adapterParticpant.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views) {
                    -1 -> {
                        if (intent.getBooleanExtra(Constants.KEY_IS_APPROVAL, false)) {
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
                                bundle.putString(Constants.DetailDestination, tv_destination.text.toString())
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
        val layoutManager = LinearLayoutManager(this)
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
            showAlert(getString(R.string.sorry), message)
        } else {
            showAlert(getString(R.string.sorry), getString(R.string.failed_to_retrieve_data))
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
            mData.first().isDetailTrip = true
            dataAttachment.addAll(mData)
            adapter.setData(dataAttachment)
            title_attachment.visibility = View.VISIBLE
            line_dot_attacthment.visibility = View.VISIBLE
            title_attachment.text = getString(R.string.attachment_files)
        } else {
            rv_attachment.visibility = View.GONE
//            title_attachment.text = "No attachment Files"
            line_dot_attacthment.visibility = View.GONE
            title_attachment.visibility = View.GONE
        }

    }

    private fun setDataApproval() {
        /*dataApproval.clear()*/
        dataParticipant.clear()

        /*if (tripSummary.tripParticipantModels.filter {
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
        }*/

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
                    model.jobtitle = tripParticipantsItemModel.positionName
                    model.email = Globals.getProfile(this).approval.reqEmail
                    model.costCenterCode = tripParticipantsItemModel.costCenterCode
                    model.costCenterName = tripParticipantsItemModel.costCenterName
                    model.budgetCode = tripParticipantsItemModel.budgetCode
                    model.budgetName = tripParticipantsItemModel.budgetName
                    model.approval = tripParticipantsItemModel.dataApproval

                    model.imgUrl = ""

                    tv_budget.text = model.budgetCode + " - " + model.budgetName
                    if (model.costCenterName.isNotEmpty()) {
                        tv_cost_center.text = model.costCenterCode + " - " + model.costCenterName
                    } else {
                        tv_cost_center.text = Globals.getProfile(this).costCenter + " - " + Globals.getProfile(this).costCenterDefaultText
                    }


                    dataParticipant.add(model)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /*if (dataApproval.isNotEmpty()) {
            tv_list_approval.visibility = View.VISIBLE
            tv_notice_title.visibility = View.VISIBLE
            rv_approval.visibility = View.VISIBLE
            tv_list_approval.text = "${getString(R.string.list_approver)} (${dataApproval.size})"
            adapterApproval.setData(dataApproval)
        } else {
            rv_approval.visibility = View.GONE
            tv_list_approval.visibility = View.GONE
            tv_notice_title.visibility = View.GONE
        }*/

        if (dataParticipant.isNotEmpty()) {
            tv_list_participant_num.visibility = View.VISIBLE
            tv_notice_participant.visibility = View.VISIBLE
            rv_participant.visibility = View.VISIBLE
            tv_list_participant_num.text = "${getString(R.string.list_participant)} (${dataParticipant.size})"
            adapterParticpant.setData(dataParticipant)
        } else {
            rv_participant.visibility = View.GONE
            tv_list_participant_num.visibility = View.GONE
            tv_notice_participant.visibility = View.GONE
        }

        if (!tripSummary.tripParticipantModels.isNotEmpty()) {
            tv_cost_center.text = "${tripSummary.tripParticipantModels[0].costCenterCode} - ${tripSummary.tripParticipantModels[0].costCenterName}"
            tv_mount.text = tripSummary.totalAllowance
        } else {
            tv_cost_center.text = Globals.getProfile(this).costCenter + " - " + Globals.getProfile(this).costCenterDefaultText
            tv_mount.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estTotal.toString())}"
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
        } else if (tripSummary.status.toInt() ?: -1 == Constants.StatusTrip.Canceled) {
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_orange)
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
            tv_status.text = getString(R.string.waiting)
        }

        id_tv_create_date.text = "${getString(R.string.created_date)} : ${tripSummary.creationDate}"
        tv_expired.text = "${tripSummary.expiredRemaining} ${getString(R.string.left_to_expired)}"
        tv_purpose.text = tripSummary.purpose

        if (Globals.isPertamina(this)){
            if (tripSummary.nonCbt){
                tv_cbt.text = "Non CBT"
            } else {
                tv_cbt.text = "CBT"
            }
        } else {
            tv_cbt.gone()
        }


        if (tripSummary.routes.isNotEmpty()) {
            if (tripSummary.routes.size == 1) {
                tv_destination.text = "${tripSummary.routes[0].origin} - ${tripSummary.routes[0].destination}"
            } else if (tripSummary.routes.size == 2) {
                tv_destination.text = "${tripSummary.routes[0].origin} - ${tripSummary.routes[0].destination} - ${tripSummary.routes[1].destination}"
            } else if (tripSummary.routes.size == 3) {
                tv_destination.text = "${tripSummary.routes[0].origin} - ${tripSummary.routes[0].destination} - ${tripSummary.routes[1].destination} - ${tripSummary.routes[2].destination}"
            } else if (tripSummary.routes.size == 4) {
                tv_destination.text = "${tripSummary.routes[0].origin} - ${tripSummary.routes[0].destination} - ${tripSummary.routes[1].destination} - ${tripSummary.routes[2].destination} - ${tripSummary.routes[3].destination}"
            } else if (tripSummary.routes.size == 5) {
                tv_destination.text = "${tripSummary.routes[0].origin} - ${tripSummary.routes[0].destination} - ${tripSummary.routes[1].destination} - ${tripSummary.routes[2].destination} - ${tripSummary.routes[3].destination} - ${tripSummary.routes[4].destination}"
            } else {
                tv_destination.text = tripSummary.routes.last().destination
            }
        } else {
            tv_destination.text = tripSummary.destinationName
        }
        tv_start_date.text = DateConverter().setDateFormatDayEEEddMMM(tripSummary.startDate)
        tv_end_date.text = DateConverter().setDateFormatDayEEEddMMM(tripSummary.returnDate)

        tv_notes.text = tripSummary.remark

        val debug = intent.getBooleanExtra(Constants.KEY_IS_PARTICIPANT, false)

        if (tripSummary.statusView == "Completely Rejected" || tripSummary.statusView == "Trip Completed" || tripSummary.statusView == "Canceled") {
            line_add_trip_item.gone()
            line_btn_change.gone()
        } else if (!debug) {
            line_add_trip_item.gone()
            line_btn_change.gone()
        }  else if (tripSummary.statusView == "Completely Approved" && tripSummary.nonCbt) {
            line_add_trip_item.gone()
        } else if (!Globals.isPertamina(this)){
            line_btn_change.gone()
        }
        else {
            line_add_trip_item.visible()
            line_btn_change.visible()
        }

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

    lateinit var dataAccomodation: TripParticipantsItemModel

    private fun addDataAccomodation() {
        rv_item_order.visibility = View.VISIBLE
        line_items.visibility = View.VISIBLE
        title_trip_total.visibility = View.VISIBLE

        dataItems.clear()
        if (!intent.extras?.getBoolean(Constants.KEY_IS_PARTICIPANT)!! && intent.extras?.getBoolean(Constants.KEY_IS_APPROVAL)!!) {
            dataAccomodation = tripSummary.tripParticipantModels.first()
        } else {
            dataAccomodation = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first()
        }

        val totalAccomdation = dataAccomodation.itemFlightModel.size + dataAccomodation.itemTrainModel.size + dataAccomodation.itemHotelModel.size
        title_trip_total.text = "${getString(R.string.your_trip_items_detail)} (${totalAccomdation})"

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

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
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
                    Constants.DETAIL_TICKET_FLIGHT ->{
                        gotoEticket(position,0)
                    }
                    Constants.DETAIL_TICKET_HOTEL -> {
                        gotoEticket(position,1)
                    }
                }
            }
        })
    }

    private fun gotoEticket(position: Int,typeItem:Int) {

        var status = ""
        var idItem = ""
        when(typeItem){
            0 -> {
                idItem = dataItems[position].dataItemFlight.pnrCode
                status = dataItems[position].dataItemFlight.status
            }
            1 -> {
                idItem = dataItems[position].dataItemHotel.hotelId
                status = dataItems[position].dataItemHotel.status
            }
            2 -> {
                idItem = dataItems[position].dataItemTrain.pnrCode
                status = dataItems[position].dataItemTrain.status
            }
        }
        if (status.toLowerCase()=="ticketed"){
            showLoadingOpsicorp(true)
            GetDataGeneral(getBaseUrl()).getDataEticket(getToken(), tripId,idItem,typeItem, object : CallbackEticket {
                override fun successLoad(summaryModel: DetailMyBookingModel) {
                    hideLoadingOpsicorp()
                    val bundle = Bundle()
                    bundle.putInt(Constants.KEY_POSITION_SELECTED_ITEM,0)
                    bundle.putParcelable(Constants.KEY_DATA_PARCELABLE,summaryModel)
                    gotoActivityWithBundle(PurchaseDetailListActivity::class.java,bundle)
                }

                override fun failedLoad(message: String) {
                    hideLoadingOpsicorp()
                }
            })
        }
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
        if (!intent.extras?.getBoolean(Constants.KEY_IS_PARTICIPANT)!! && intent.extras?.getBoolean(Constants.KEY_IS_APPROVAL)!!) {
            data.tripParticipantId = tripSummary.tripParticipantModels.first().id
        } else {
            data.tripParticipantId = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().id
        }
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
        val lineDownload = layout.findViewById(R.id.line_download) as LinearLayout
        val btnDownload = layout.findViewById(R.id.tv_download_itenary) as TextView
        val lineCanceltrip = layout.findViewById(R.id.line_cancel_trip) as LinearLayout
        val btnCancelTrip = layout.findViewById(R.id.tv_cancel_trip) as TextView
        val lineDownloadCoverLetter = layout.findViewById(R.id.line_download_cover_latter) as LinearLayout
        val btnDownloadCoverLetter = layout.findViewById(R.id.tv_download_cover_latter) as TextView

        btnDetail.text      = "Back"
        btnRemove.text      = "Help and guide"
        btnDownload.text    = "Download itinerary"
        btnDownloadCoverLetter.text  = "Download SKPD Letter"

        val isParticipant = intent.getBooleanExtra(Constants.KEY_IS_PARTICIPANT,false)

        if (tripSummary.status=="4"&&dataItems.isEmpty()&&!isParticipant){
            lineCanceltrip.visibility = View.VISIBLE
            btnCancelTrip.visibility  = View.VISIBLE
        }

        if (isParticipant&&tripSummary.tripParticipantModels.find { it.employId==getProfile().employId }?.itinerary!="null") {
            lineDownload.visibility = View.VISIBLE
            btnDownload.visibility  = View.VISIBLE
        }

        if (isParticipant&&tripSummary.coverLatter!="null") {
            lineDownloadCoverLetter.visibility = View.VISIBLE
            btnDownloadCoverLetter.visibility  = View.VISIBLE
        }

        val dialog = Globals.showPopup(toolbar.getImageCart(), layout)

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
            dialog.dismiss()
        }

        btnDownload.setOnClickListener {
            dialog.dismiss()
            val idItinerary = tripSummary.tripParticipantModels.find { it.employId==getProfile().employId }?.itinerary.toString()
            getUrlFile(idItinerary)
        }

        btnDownloadCoverLetter.setOnClickListener {
            dialog.dismiss()
            getUrlFile(tripSummary.coverLatter)
        }

        btnCancelTrip.setOnClickListener {
            cancelTripListener()
            dialog.dismiss()
        }
    }

    private fun cancelTripListener() {
        showDialog("")
        GetDataTripPlane(getBaseUrl()).cancelTripplan(Globals.getToken(), getDataTripId(), object : CallbackCancelTripplan {
            override fun successLoad(boolean: Boolean) {
                hideDialog()
                if (!boolean) {
                    Globals.showAlert(getString(R.string.sorry), "something wrong!", this@DetailTripActivity)
                }
                else {
                    getSummary()
                }
            }
            override fun failedLoad(message: String) {
                hideDialog()
                Globals.showAlert(getString(R.string.sorry), message, this@DetailTripActivity)
            }
        })
    }

    private fun getDataTripId(): HashMap<String, Any> {
        val model = CancelTripPlan()
        model.id = tripSummary.tripId
        return Globals.classToHasMap(model, CancelTripPlan::class.java)
    }

    private fun getUrlFile(idFile:String) {
        showDialog("Please Wait")
        GetDataTripPlane(getBaseUrl()).getUrlFile(getToken(),idFile,object : CallbackGetUrlFile {
            override fun success(url:String) {
                hideDialog()
                dowloadByLib(url)
            }

            override fun failed(string: String) {
                setToast(string)
            }
        })
    }

    lateinit var fetch: Fetch

    private fun dowloadByLib(url: String) {
        try {
            val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(3)
                .build()

            fetch = getInstance(fetchConfiguration)

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val request = Request(url,dir.absolutePath+"/Travelaja/Itin${System.currentTimeMillis()}.${url.split(".").last()}")
            request.priority = Priority.HIGH
            request.networkType = NetworkType.ALL

            fetch.enqueue(request, { updatedRequest ->
                setToast("Download Completed : ${updatedRequest.file}")
            }) { error ->
                setToast(error.throwable?.message.toString())
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
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

    fun changeTripListener(view: View) {
        gotoChangeTrip()
    }

    fun showApproveAllDialog(view: View) {
        if (getConfigCompany().codeCompany == Constants.CodeCompany.PertaminaDTM) {
            approveOrRejectItemRequest("1", "1")
            /*saveToDraft()*/
        } else {
            ListParticipantDialog(this).create(this, dataParticipant)

        }
    }

    fun rejectListener(view: View) {
        rejectOrApproveSelected("0")
    }

    fun rejectOrApproveSelected(action: String) {
        showDialog("")
        GetDataApproval(getBaseUrl()).approveItem(Globals.getToken(), dataBodyApproved(action), object : CallbackApprovAll {
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
        mData.tripType = "0"

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
            /*val tripPlanId = tripSummary.tripId
            val bundle = Bundle()
            bundle.putString(Constants.TRIP_PLAN_ID, tripPlanId)
            gotoActivityWithBundle(PaymentActivity::class.java, bundle)*/
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

    fun gotoChangeTrip() {
        showLoadingOpsicorp(true)
        GetDataTravelRequest(getBaseUrl()).changeTrip(getToken(), tripSummary.tripId, object : CallbackChangeTrip{
            override fun successLoad(data: ChangeTripModel) {
                hideLoadingOpsicorp()
                Constants.DATA_CHANGE_TRIP = Serializer.serialize(data)
                setLog("Test Change Trip", Serializer.serialize(Constants.DATA_CHANGE_TRIP))
                val intent = Intent(applicationContext,CreateTripPertaminaActivity::class.java)
                intent.putExtra(Constants.CHANGE_TRIP, true)
                startActivity(intent)
            }

            override fun failedLoad(message: String) {
                showAllert(getString(R.string.sorry), message)
            }

        })
    }

    fun gotoAddItem() {
        if (intent.getBooleanExtra(Constants.KEY_IS_PARTICIPANT, false)) {
            val model = SuccessCreateTripPlaneModel()
            model.purpose = tripSummary.purpose
            model.idTripPlane = tripSummary.tripId
            model.status = tripSummary.statusView
            model.statusNumber = tripSummary.status.toInt()
            model.tripCode = tripSummary.tripCode
            model.createDate = tripSummary.creationDate
            model.timeExpired = tripSummary.expiredRemaining
            model.trnNumber = tripSummary.trnNumber
            model.isTripPartner = tripSummary.isTripPartner
            model.tripPartnerName = tripSummary.parterName
            if (tripSummary.routes.isNotEmpty()) {
                model.originId = tripSummary.origin
                model.originName = tripSummary.routes.first().origin
                model.destinationName = tripSummary.routes.first().destination
                model.destinationId = tripSummary.destination
            } else {
                model.originId = tripSummary.origin
                model.originName = tripSummary.originName
                model.destinationName = tripSummary.destinationName
                model.destinationId = tripSummary.destination
            }
            model.route = mappingRoutes(tripSummary.routes)
            model.startDate = tripSummary.startDate
            model.endDate = tripSummary.returnDate
            model.attachment.addAll(addAttacthment())
            if (tripSummary.budgetId.isNullOrEmpty()) {
                model.buggetId = getProfile().costCenter
            } else {
                model.buggetId = tripSummary.budgetId
            }
            model.costCenter = tripSummary.tripParticipantModels.filter { it.employId == getProfile().employId }.first().costId

            model.businessTripType = tripSummary.businessTripType
            model.remark = tripSummary.remark.toString()
            model.wbsNo = tripSummary.wbsNo
            model.isDomestik = tripSummary.isDomestic
            model.isBookAfterApprove = tripSummary.isBookAfterApprove
            model.isPrivateTrip = tripSummary.isPrivateTrip
            model.golper = tripSummary.golper

            Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(model)

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm")
            if (Date().before(dateFormatter.parse(tripSummary.returnDate))) {
                val bundle = Bundle()
                bundle.putInt(TYPE_ACCOMODATION, Constants.KEY_ACCOMODATION)
                gotoActivityWithBundle(AccomodationActivity::class.java, bundle)
            } else {
                showAlert("Sorry", "This Trip Plan return date is expired")
            }
        }
    }

    private fun addAttacthment(): ArrayList<TripAttachmentItemModel> {
        val data = ArrayList<TripAttachmentItemModel>()
        tripSummary.attactment.forEach {
            val model = TripAttachmentItemModel()
            model.description = it.nameImage
            model.url = it.url
            model.id = it.id
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
            mData.originName = it.origin
            mData.dateDeparture = it.departureDate
            mData.transportationType = it.transportation
            data.add(mData)
        }
        return data
    }
}