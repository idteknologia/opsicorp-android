package com.mobile.travelaja.module.create_trip.success_create_trip

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.accomodation.view_accomodation.activity.AccomodationActivity
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.ApproverAdapter
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.utility.*
import com.mobile.travelaja.utility.Constants.TYPE_ACCOMODATION
import kotlinx.android.synthetic.main.success_create_trip_plane.*
import kotlinx.android.synthetic.main.success_create_trip_plane.icCopyClipboard
import kotlinx.android.synthetic.main.success_create_trip_plane.image_barcode
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_destination
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_end_date
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_purpose
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_start_date
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_status
import kotlinx.android.synthetic.main.success_create_trip_plane.tv_tripcode
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel


class SucessCreateTripPlaneActivity : BaseActivity(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.success_create_trip_plane
    }

    var data = SuccessCreateTripPlaneModel()
    val dataApproval = ArrayList<TravelRequestApprovalModel>()
    val adapterApproval by lazy { ApproverAdapter(this) }

    override fun OnMain() {
        setTypeTravelRequest()
        icCopyClipboard.setOnClickListener {
            copyToClip()
        }
        line_submit.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    private fun setTypeTravelRequest() {
        if (Globals.getBaseUrl(applicationContext) == "https://pertamina-dtm3-qa.opsicorp.com/") {
            button_pertamina.visible()
            button_except_pertamina.gone()
            setDataPertamina()
        } else {
            setData()
            button_pertamina.gone()
            button_except_pertamina.visible()
        }
    }

    private fun setDataPertamina() {
        data = Serializer.deserialize(Constants.DATA_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        setLog("Data Draft", Serializer.serialize(data))
        if (data.tripCode != null) {
            image_barcode.setImageBitmap(Globals.stringToBarcodeImage(data.tripCode))
        }
        tv_status.text = data.tripType
        tv_tripcode.text = data.tripCode
        tv_purpose.text = data.purpose
        tv_activity_type_text.text = data.activityType
        tv_created_date2.text = "${data.createDateView}"
        //tv_expired_date.text = "1 days left to expired"
        tv_expired_date.visibility = View.GONE //don't need expire for draft
        /*tv_destination.text = "${data.originName} - ${data.destinationName}"*/
        tv_destination.text = data.destinationName
        if (Globals.getProfile(this).approval.travelRequestApproval.isEmpty()){
            tv_list_approval.text = "List Approver (0)"
        }

        tv_start_date.text = DateConverter().setDateFormatDayEEEddMMM(data.startDate)
        tv_end_date.text = DateConverter().setDateFormatDayEEEddMMM(data.endDate)
        initRecyclerViewApproval()

        Globals.delay(1500, object : Globals.DelayCallback {
            override fun done() {
                nested_view.post(Runnable {
//                    nested_view.fullScroll(View.FOCUS_DOWN)
                    nested_view.smoothScrollBy(0, nested_view.bottom)
                })
            }
        })

    }

    private fun initRecyclerViewApproval() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_approver.layoutManager = layoutManager
        rv_approver.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_approver.adapter = adapterApproval

        if (data.tripType == "Domestic Route"){
            dataApproval.addAll(Globals.getProfile(this).approval.travelRequestApproval.filter {
                it.isDomestic
            })
        } else {
            dataApproval.addAll(Globals.getProfile(this).approval.travelRequestApproval.filter {
                !it.isDomestic
            })
        }
        val totalApprover = dataApproval.size
        tv_list_approval.text = "List Approver (${totalApprover})"

        adapterApproval.setData(dataApproval)

        if (dataApproval.isEmpty()){
            line_approver.gone()
        } else {
            line_approver.visible()
        }
    }

    private fun copyToClip() {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(android.R.attr.label.toString(), tv_tripcode.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(applicationContext, "Copied To Clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun setData() {
        data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        if (data.tripCode != null) {
            image_barcode.setImageBitmap(Globals.stringToBarcodeImage(data.tripCode))
        }
        tv_status.text = data.status
        tv_tripcode.text = data.tripCode
        tv_purpose.text = data.purpose
        tv_created_date2.text = "${data.createDateView}"
        //tv_expired_date.text = "1 days left to expired"
        tv_expired_date.visibility = View.GONE //don't need expire for draft
        tv_activity_type.gone()
        tv_activity_type_text.gone()
        if (data.destinationName.isNullOrEmpty()) {
            tv_destination.text = data.originName
        } else {
            tv_destination.text = "${data.originName} - ${data.destinationName}"
        }

        if (Globals.getProfile(this).approval.travelRequestApproval.isNotEmpty()){
            tv_list_approval.text = "List Approver (${Globals.getProfile(this).approval.travelRequestApproval.size.toString()})"
        } else {
            tv_list_approval.text = "List Approver (0)"
        }


        tv_start_date.text = DateConverter().setDateFormatDayEEEddMMM(data.startDate)
        tv_end_date.text = DateConverter().setDateFormatDayEEEddMMM(data.endDate)
        initRecyclerViewApproval()
        setLog(Constants.DATA_SUCCESS_CREATE_TRIP)

        Globals.delay(1500, object : Globals.DelayCallback {
            override fun done() {
                nested_view.post(Runnable {
//                    nested_view.fullScroll(View.FOCUS_DOWN)
                    nested_view.smoothScrollBy(0, nested_view.bottom)
                })
            }
        })
    }

    fun laterListener(view: View) {
        later()
    }

    fun later() {
        finish()

        val exit = Intent(this, HomeActivity::class.java)
        exit.addCategory(Intent.CATEGORY_HOME)
        exit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        //exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(exit)
    }

    fun addTripPlaneListener(view: View) {
        Globals.setDataPreferenceString(this, Constants.DATA_CREATE_TRIP_PLAN, Serializer.serialize(data, SuccessCreateTripPlaneModel::class.java))
        val bundle = Bundle()
        bundle.putInt(TYPE_ACCOMODATION, Constants.KEY_ACCOMODATION)
        gotoActivityWithBundle(AccomodationActivity::class.java, bundle)
    }


    override fun onBackPressed() {
        /*backListerner()*/
        later()
    }


    override fun onClick(v: View?) {
        when (v) {
            line_submit -> {
                /*submitTripPlan()*/
                later()
            }
            btn_submit -> {
                /*submitTripPlan()*/
                later()
            }
        }
    }

   /* private fun submitTripPlan() {
        showLoadingOpsicorp(true)
        GetDataTravelRequest(getBaseUrl()).submitTravelRequest(Globals.getToken(), dataRequest(), object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                later()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry), message)
            }

        })
    }


    private fun dataRequest(): HashMap<String, Any> {
        val dataRequest = SaveAsDraftRequestPertamina()
        dataRequest.origin = dataDraft.routes[0].Origin
        dataRequest.destination = dataDraft.routes[0].Destination
        dataRequest.golper = 2
        dataRequest.purpose = dataDraft.namePusrpose
        dataRequest.businessTripType = dataDraft.nameActivity
        dataRequest.startDate = dataDraft.startDate
        dataRequest.returnDate = dataDraft.endDate
        dataRequest.type = Globals.getConfigCompany(this).travelingPurposeFormType.toInt()
        dataRequest.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        dataRequest.isDomestic = !dataDraft.isInternational
        dataRequest.remark = dataDraft.notes
        dataRequest.wbsNo = dataDraft.wbsNumber

        dataRequest.routes = ArrayList()
        val mDataRoutes = ArrayList<RoutesItem>()
        dataDraft.routes.forEachIndexed { index, routesItinerary ->
            val dataRoutes = RoutesItem()
            dataRoutes.transportation = routesItinerary.Transportation
            dataRoutes.departureDate = DateConverter().getDate(routesItinerary.DepartureDateView, "dd MMM yyyy", "yyyy-MM-dd")
            dataRoutes.departureDateView = DateConverter().getDate(routesItinerary.DepartureDateView, "dd MMM yyyy", "dd-MM-yyyy")
            dataRoutes.origin = routesItinerary.Origin
            dataRoutes.destination = routesItinerary.Destination
            mDataRoutes.add(dataRoutes)
        }
        dataRequest.routes = mDataRoutes

        val attachments = ArrayList<TripAttachmentsItemRequest>()
        dataDraft.image.forEachIndexed { index, uploadModel ->
            val mDataAttachments = TripAttachmentsItemRequest()
            mDataAttachments.description = uploadModel.nameImage
            mDataAttachments.url = uploadModel.url
            attachments.add(mDataAttachments)
        }
        dataRequest.tripAttachments = attachments

        dataRequest.tripParticipants = ArrayList()
        val participants = ArrayList<TripParticipantsPertaminaItem>()
        val mDataParticipants = TripParticipantsPertaminaItem()
        mDataParticipants.employeeId = getProfile().employId
        mDataParticipants.useCostCenterOther = dataDraft.participant[0].useCostCenterOther
        mDataParticipants.useCashAdvance = dataDraft.participant[0].useCashAdvance
        mDataParticipants.cashAdvance = dataDraft.participant[0].cashAdvance
        mDataParticipants.costCenterCode = dataDraft.participant[0].costCenterCode
        mDataParticipants.estFlight = dataDraft.participant[0].estFlight
        mDataParticipants.estTransportation = dataDraft.participant[0].estTransportation
        mDataParticipants.estTotal = dataDraft.participant[0].estTotal
        mDataParticipants.estAllowance = dataDraft.participant[0].estAllowance
        mDataParticipants.estAllowanceEvent = dataDraft.participant[0].estAllowanceEvent
        mDataParticipants.estLaundry = dataDraft.participant[0].estLaundry
        mDataParticipants.estHotel = dataDraft.participant[0].estHotel
        participants.add(mDataParticipants)
        dataRequest.tripParticipants = participants


        return Globals.classToHasMap(dataRequest, SaveAsDraftRequestPertamina::class.java)
    }*/

    /*private fun backListerner() {

            Globals.showAlert("Maaf","Apakah anda yakin?",this,object : OnclikAllertDoubleSelected {
                override fun yes() {
                    later()
                }

                override fun no() {

                }
            })

    }*/
}