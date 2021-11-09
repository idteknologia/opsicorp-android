package com.mobile.travelaja.module.approval.activity

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.approval.summary.*
import com.mobile.travelaja.module.item_custom.barcode.popup.QRPopUp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.*
import opsigo.com.domainlayer.model.summary.*
import kotlinx.android.synthetic.main.detail_participant_activity_view.*
import kotlinx.android.synthetic.main.detail_participant_activity_view.image_barcode
import kotlinx.android.synthetic.main.detail_participant_activity_view.rv_summary
import kotlinx.android.synthetic.main.detail_participant_activity_view.toolbar
import kotlinx.android.synthetic.main.detail_participant_activity_view.tv_purpose
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.ApprovePerPaxRequest
import opsigo.com.datalayer.request_model.ApproverPerItemRequest
import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import java.util.HashMap

class DetailParticipantActivity : BaseActivity()
        , View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.detail_participant_activity_view }

    lateinit var dateConverter : DateConverter

    val data = ArrayList<SummaryModelItems>()
    val adapter by lazy { SummaryAdapter(this) }

    var id  = ""
    var str = ""
    var jobTitle = ""
    var status = ""
    var nameParticipant = ""
    var destination = ""
    var getActionApprove = false

    var idTripCode    = ""
    var tripSummary   = SummaryModel()
    var estCost = ""
    var idParticipant = ""
    var employId      = ""
    lateinit var dataAccomodation: TripParticipantsItemModel

    override fun OnMain() {
        toolbar.callbackOnclickToolbar(this)
        toolbar.setTitleBar(getString(R.string.detail_participant))
        toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        image_barcode.setOnClickListener(this)

        val bundle = intent.getBundleExtra("data")
        id  = bundle?.getString(Constants.KEY_INTENT_TRIPID).toString()
        str = bundle?.getString(Constants.Summary).toString()
        estCost = bundle?.getString(Constants.EstCost).toString()
        status = bundle?.getString(Constants.STATUS_MEMBER).toString()
        idParticipant = bundle?.getString(Constants.ID_PARTICIPANT).toString()
        employId      = bundle?.getString(Constants.EMPLOY_ID).toString()
        destination = bundle?.getString(Constants.DetailDestination).toString()
        tripSummary = Serializer.deserialize(str,SummaryModel::class.java)
        idTripCode = tripSummary.tripCode
        updateView()

        initRecyclerView()
        addDataDummy()
    }

    private fun failedWarning(message:String) {
        Globals.showAlert("Failed",message,this)
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_summary.layoutManager  = layoutManager
        rv_summary.itemAnimator   = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_summary.adapter        = adapter

        adapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    Constants.OPTION_FLIGHT_APPROVE -> {
                        approveOrRejectItemRequest("1","0")
                    }
                    Constants.OPTION_TRAIN_APPROVE -> {
                        approveOrRejectItemRequest("1","2")
                    }
                    Constants.OPTION_HOTEL_APPROVE -> {
                        approveOrRejectItemRequest("1","1")
                    }

                    Constants.OPTION_FLIGHT_REJECT -> {
                        approveOrRejectItemRequest("0","0")
                    }
                    Constants.OPTION_TRAIN_REJECT -> {
                        approveOrRejectItemRequest("0","2")
                    }
                    Constants.OPTION_HOTEL_REJECT -> {
                        approveOrRejectItemRequest("0","1")
                    }
                }
            }
        })

    }
    private fun addDataDummy() {
        data.clear()
        dataAccomodation = tripSummary.tripParticipantModels.filter { it.id==idParticipant }.first()
        checkEnableOption(dataAccomodation)

        if (dataAccomodation.itemFlightModel.isNotEmpty()){
            val mDataHeaderFlight = SummaryModelItems()
            mDataHeaderFlight.typeCard = "HEADER"
            mDataHeaderFlight.title = "${dataAccomodation.itemFlightModel.size}. Flight Summary"
            mDataHeaderFlight.reason = reasonItemFlight(dataAccomodation,"Flight")
            data.add(mDataHeaderFlight)
        }

        dataAccomodation.itemFlightModel.forEachIndexed { index, itemFlightModel ->
            val mDataFlighModel = SummaryModelItems()
            mDataFlighModel.typeCard = "FLIGHT"
            mDataFlighModel.title    = "flight"
            mDataFlighModel.dataItemFlight = itemFlightModel
            data.add(mDataFlighModel)
        }

        if (dataAccomodation.itemTrainModel.isNotEmpty()){
            val mDataHeaderHotel  = SummaryModelItems()
            mDataHeaderHotel.typeCard  = "HEADER"
            mDataHeaderHotel.title  = "${dataAccomodation.itemTrainModel.size}. Train Summary"
            mDataHeaderHotel.reason = reasonItemFlight(dataAccomodation,"Train")
            data.add(mDataHeaderHotel)
        }

        dataAccomodation.itemTrainModel.forEachIndexed { index, itemTrainModel ->
            val mDataTrainModel = SummaryModelItems()
            mDataTrainModel.typeCard = "TRAIN"
            mDataTrainModel.title = "train"
            mDataTrainModel.dataItemTrain = itemTrainModel
            data.add(mDataTrainModel)
        }

        if (dataAccomodation.itemHotelModel.isNotEmpty()){
            val mDataHeaderHotel  = SummaryModelItems()
            mDataHeaderHotel.typeCard  = "HEADER"
            mDataHeaderHotel.title     = "${dataAccomodation.itemHotelModel.size}. Hotel"
            mDataHeaderHotel.reason    = reasonItemFlight(dataAccomodation,"Hotel")
            data.add(mDataHeaderHotel)
        }

        dataAccomodation.itemHotelModel.forEachIndexed { index, itemTrainModel ->
            val mData = SummaryModelItems()
            mData.typeCard = "HOTEL"
            mData.dataItemHotel = itemTrainModel
            data.add(mData)
        }

        adapter.setData(data)

        val totalAccomdation = data.filter { it.typeCard != "HEADER" }.size
        tv_trip_items_detail.text = "${getString(R.string.your_trip_items_detail)} (${totalAccomdation})"

/*        nested_view.smoothScrollTo(0,0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_total_amount.focusable = View.FOCUSABLE
        }*/

        checkNotComply()
    }

    private fun reasonItemFlight(dataAccomodation: TripParticipantsItemModel,type: String): String {
        var dataTotal = 0

        dataAccomodation.dataApproval.forEachIndexed { index, participantModel ->
            when (type){
                "Flight" -> {
                    if (participantModel.followUpFlight.isNotEmpty()){
                        dataTotal++
                    }
                }
                "Train"-> {
                    if (participantModel.followUpTrain.isNotEmpty()){
                        dataTotal++
                    }
                }
                "Hotel" -> {
                    if (participantModel.followUpHotel.isNotEmpty()){
                        dataTotal++
                    }
                }
            }

            setLog("<------------>")
            setLog(participantModel.name)
            setLog(participantModel.layer.toString())
            setLog(participantModel.isCompletelyReviewed.toString())
        }

        if (dataTotal == dataAccomodation.dataApproval.size){
            return "Approved"
        } else if (dataTotal == 0){
            return ""
        }
        else{
            return "Approved by ${dataTotal}/${dataAccomodation.dataApproval.size}"
        }
    }

    private fun checkEnableOption(tripParticipant: TripParticipantsItemModel) {

        if (Constants.isApproval){
            var positionApproval = -1
            tripParticipant.dataApproval.forEachIndexed { index, participantModel ->
                if (participantModel.employId == getProfile().employId){
                    positionApproval = index
                }
            }

            if (positionApproval>0){
                if (tripParticipant.dataApproval[positionApproval].layer == tripParticipant.dataApproval[(positionApproval-1)].layer){
                    setAdapterButtonOption(tripParticipant.dataApproval.filter { it.employId == getProfile().employId }.first())
                }
                else{
//                    setLog(positionApproval.toString()+" "+tripParticipant.dataApproval[(positionApproval)].isCompletelyReviewed)
//                    setLog((positionApproval-1).toString()+" "+tripParticipant.dataApproval[(positionApproval-1)].isCompletelyReviewed)
                    setButtonRejectOrApprove(tripParticipant.dataApproval[(positionApproval-1)].isCompletelyReviewed)
                }
            }
            else{
                setAdapterButtonOption(tripParticipant.dataApproval.filter { it.employId == getProfile().employId }.first())
            }
        }
        else{
            setButtonRejectOrApprove(false)
        }
    }


    private fun setAdapterButtonOption(approval : ParticipantModelDomain) {
        if (dataAccomodation.itemFlightModel.isNotEmpty()) adapter.optionMenuFlight = approval.followUpFlight.isEmpty()
        if (dataAccomodation.itemTrainModel.isNotEmpty()) adapter.optionMenuTrain  = approval.followUpTrain.isEmpty()
        if (dataAccomodation.itemHotelModel.isNotEmpty()) adapter.optionMenuHotel  = approval.followUpHotel.isEmpty()

        setLog(approval.followUpFlight)
        setLog(approval.followUpTrain)
        setLog(approval.followUpHotel)

        if (approval.isCompletelyReviewed){
            layout_bottom.visibility = View.GONE
        }else {
            layout_bottom.visibility = View.VISIBLE
        }
    }

    private fun setButtonRejectOrApprove(enable:Boolean) {
        adapter.optionMenuFlight = enable
        adapter.optionMenuTrain  = enable
        adapter.optionMenuHotel  = enable

        if (enable){
            layout_bottom.visibility = View.VISIBLE
        }
        else{
            layout_bottom.visibility = View.GONE
        }
    }


    private fun checkNotComply() {
        lay_reason_code.visibility = View.GONE
    }

    fun updateView() {

        image_barcode.setImageBitmap(Globals.stringToBarcodeImage(idTripCode))

        val amount = java.lang.Double.parseDouble(tripSummary.totalExpenditure)
        val sAmount = StringUtils().setCurrency("IDR", amount, false)

        tv_total_amount.text = sAmount

        tv_created_date.text    = tripSummary.creationDateView
        tv_days_left.text       = ""//tripSummary.timeLimitRemaining

        tv_purpose.text         = tripSummary.purpose
        tv_destination.text     = StringUtils().setUppercaseFirstLetter(destination)

        dateConverter = DateConverter()
        var string = tripSummary.startDate
        string = string.substring(0, 10)
        val startdate = dateConverter.setDateFormatDayEEEddMMM(string)
        tv_start_date.text = startdate

        var string2 = tripSummary.returnDate
        string2 = string2?.substring(0, 10)
        if (string2 != null) {
            val returndate = dateConverter.setDateFormatDayEEEddMMM(string2)
            tv_end_date.text = returndate
        }

        //view participant
        tv_status.text = status
        if( "Completely Approved".equals(status)){
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_green)
        }else if( "Completely Rejected".equals(status)){
            tv_status.background = resources.getDrawable(R.drawable.rounded_approval_red)
        }

        tv_jobtitle.text = tripSummary.tripParticipantItem.first().positionName
        tv_name.text     = "${tripSummary.contact.firstName} ${tripSummary.contact.lastName}"
        tv_cost_center.text   = "${tripSummary.tripParticipantItem.first().costCenterCode} - ${tripSummary.tripParticipantItem.first().costCenterName}"
        tv_budget_name.text   = tripSummary.tripParticipantItem.first().email
        tv_cost_center_price.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estTotal.toString())}"
        tv_est_flight.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estFlight.toString())}"
        tv_est_hotel.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estHotel.toString())}"
        tv_est_transportation.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estTransportation.toString())}"
        tv_est_allowance.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estAllowance.toString())}"
        tv_est_allowance_event.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estAllowanceEvent.toString())}"
        tv_est_laundry.text = "IDR ${Globals.formatAmount(tripSummary.tripParticipantItem.first().estLaundry.toString())}"

    }

    override fun btnBack() {
        /*backListen()*/
        onBackPressed()
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
        btnRemove.text = "Back to list"

        btnDetail.setOnClickListener {
            if (getActionApprove){
                if (!adapter.optionMenuHotel&&!adapter.optionMenuTrain&&!adapter.optionMenuFlight){
                    val intent = Intent()
                    intent.putExtra("action","to_detail")
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            }
            else{
                finish()
            }
        }

        btnRemove.setOnClickListener {
            if (getActionApprove){
                if (!adapter.optionMenuHotel&&!adapter.optionMenuTrain&&!adapter.optionMenuFlight){
                    val intent = Intent()
                    intent.putExtra("action","to_list_refresh")
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            }
            else{
                val intent = Intent()
                intent.putExtra("action","to_list")
                setResult(Activity.RESULT_OK,intent)
                finish()
            }

        }

        Globals.showPopup(toolbar.getImageCart(),layout)
    }

    override fun onClick(view: View?) {
        when (view){
            image_barcode -> {
                QRPopUp.show(this,idTripCode)
            }
        }
    }

    fun rejectAllitemListener(view: View?){
        approveOrRejectAllpactRequest("0")
    }

    fun approveAllitemListener(view: View?){
        approveOrRejectAllpactRequest("1")
    }

    private fun approveOrRejectAllpactRequest(action: String) {
        showDialog("")
        GetDataApproval(getBaseUrl()).approvePerPax(getToken(),getDataPerpact(action),object :CallbackApprovAll{
            override fun successLoad(data: String) {
                val intent = Intent()
                intent.putExtra("action","to_list_refresh")
                setResult(Activity.RESULT_OK,intent)
                finish()
            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert(getString(R.string.sorry),message)
            }
        })
    }

    private fun getDataPerpact(action: String): HashMap<Any, Any> {
        val mData = ApprovePerPaxRequest()
        mData.approvalAction = action
        mData.employeeId     = getProfile().employId
        mData.tripId         = tripSummary.tripId
        mData.tripParticipantId = idParticipant

        return Globals.classToHashMap(mData,ApprovePerPaxRequest::class.java)
    }

    fun approveOrRejectItemRequest(action: String, type:String){
        showDialog("")
        GetDataApproval(getBaseUrl()).approveItem(getToken() ,
                getData(action,type),object : CallbackApprovAll {
            override fun successLoad(data: String) {
                hideDialog()
                getActionApprove = true
                updateViewItem(action,type)
               /* if (type=="3"){
                    val intent = Intent()
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
                else {
                    updateViewItem(action,type)
                }*/
            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert(getString(R.string.sorry),message)
            }
        })
    }

    private fun updateViewItem(action: String, type: String) {
        when(type){
            "0" -> {
                adapter.optionMenuFlight = false
                adapter.notifyDataSetChanged()
                checkOptionAfterGetApi()
            }
            "1" -> {
                adapter.optionMenuHotel = false
                adapter.notifyDataSetChanged()
                checkOptionAfterGetApi()
            }
            "2" -> {
                adapter.optionMenuTrain = false
                adapter.notifyDataSetChanged()
                checkOptionAfterGetApi()
            }
        }

    }

    private fun checkOptionAfterGetApi() {
        if (!adapter.optionMenuHotel&&!adapter.optionMenuTrain&&!adapter.optionMenuFlight){
//            layout_bottom.visibility = View.GONE
            val intent = Intent()
            intent.putExtra("action","to_list_refresh")
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        backListen()
    }

    fun backListen(){
        if (getActionApprove){
            if (!adapter.optionMenuHotel&&!adapter.optionMenuTrain&&!adapter.optionMenuFlight){
                val intent = Intent()
                intent.putExtra("action","to_list_refresh")
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
    }

    private fun getData(action: String, type: String): HashMap<Any, Any> {
        val data = ApproverPerItemRequest()
        data.approvalAction  = action
        data.tripType   = type
        data.employeeId = getProfile().employId
        data.tripId     = tripSummary.tripId
        data.tripParticipantId = idParticipant
        setLog("-----> "+action+" "+type+" "+data.tripId)
        return Globals.classToHashMap(data,ApproverPerItemRequest::class.java)
    }
}