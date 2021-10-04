package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import java.util.HashMap
import com.mobile.travelaja.R
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.base.BaseActivityBinding
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.datalayer.request_model.reschedule.*
import opsigo.com.datalayer.datanetwork.GetDataApproval
import com.mobile.travelaja.module.home.activity.HomeActivity
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import com.mobile.travelaja.databinding.DetailPurchaseListActivityBinding
import com.mobile.travelaja.databinding.MenuPopupPurchaseDetailListBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.my_booking.PurchaseDetailTripFlightAndTrainModel
import com.mobile.travelaja.module.my_booking.adapter.SegmentMybookingAdapter
import com.mobile.travelaja.module.my_booking.refund.RescheduleDialog
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

class PurchaseDetailListActivity : BaseActivityBinding<DetailPurchaseListActivityBinding>(),ToolbarOpsicorp.OnclickButtonListener{

    val dataFlight by lazy { ArrayList<PurchaseDetailTripFlightAndTrainModel>() }
    val adapterSegemntFligh by lazy { SegmentMybookingAdapter(this,dataFlight) }
    var positionSelectedItem = 0
    var READ_REQUEST_CODE    = 67
    lateinit var dataPurchaseDetail: DetailMyBookingModel

    var dataAttacmennth: ArrayList<UploadModel> = ArrayList()
    var startDate                               = ""
    var endDate                                 = ""
    var notes                                   = ""
    var DIALOG_RESCHEDULE                       = 0
    var DIALOG_REFUND                           = 1
    var typeDialogView                          = DIALOG_RESCHEDULE
    var isFlightAndTrain                        = false

    override fun bindLayout(): DetailPurchaseListActivityBinding {
        return DetailPurchaseListActivityBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        setDataIntent()
        initToolbar()
        validationLayout()
        Globals.scrollToUp(viewBinding.nestedView)
    }

    private fun setDataIntent() {
        positionSelectedItem = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.KEY_POSITION_SELECTED_ITEM,0)!!
        dataPurchaseDetail   = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getParcelable<DetailMyBookingModel>(Constants.KEY_DATA_PARCELABLE)!!
    }

    private fun validationLayout() {
        when(dataPurchaseDetail.itemType){
            Constants.TripType.Airline -> {
                isFlightAndTrain = true
                initPageFlightDetail()
            }
            Constants.TripType.KAI -> {
                isFlightAndTrain = true
                initPageTrainDetail()
            }
            Constants.TripType.Hotel -> {
                isFlightAndTrain = false
                initPageHotelDetail()
            }
        }
    }

    private fun initPageHotelDetail() {
        viewBinding.toolbar.showtitlePurchaseHotel()
        viewBinding.flightHeader.lineFlightHeader.visibility  = View.GONE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.GONE
        viewBinding.layHeaderHotel.lineDetailHotel.visibility = View.VISIBLE
        viewBinding.lineTicket.visibility                     = View.VISIBLE

        viewBinding.tvTitleTicket.text                      = getString(R.string.title_ticket_hotel)
        viewBinding.tvPnrCode.text                          = dataPurchaseDetail.dataHotel.voucerCode
        viewBinding.layHeaderHotel.tvNameHotel.text         = dataPurchaseDetail.dataHotel.hotelName
        viewBinding.layHeaderHotel.tvCity.text              = dataPurchaseDetail.dataHotel.area
        viewBinding.layHeaderHotel.tvChekinDate.text        = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkInDate.toString(),"yyyy-MM-dd HH:mm:ss","E, dd MMM")
        viewBinding.layHeaderHotel.tvCheckinTime.text       = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkInDate.toString(),"yyyy-MM-dd HH:mm:ss","HH:mm")
        viewBinding.layHeaderHotel.tvCheckoutDate.text      = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkOutDate.toString(),"yyyy-MM-dd HH:mm:ss","E, dd MMM")
        viewBinding.layHeaderHotel.tvCheckoutTime.text      = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkOutDate.toString(),"yyyy-MM-dd HH:mm:ss","HH:mm")
        viewBinding.layHeaderHotel.tvNight.text             = "${dataPurchaseDetail.dataHotel.totalNight} night(s)"
        viewBinding.layHeaderHotel.tvNameBookingContact.text = dataPurchaseDetail.bookingContact?.fullName.toString()

        viewBinding.pageHotel.showLayout()
        viewBinding.pageHotel.setDataHotel(dataPurchaseDetail)
        Globals.viewRatingStarHotel(arrayListOf(viewBinding.layHeaderHotel.icStart1,viewBinding.layHeaderHotel.icStart2,viewBinding.layHeaderHotel.icStart3,viewBinding.layHeaderHotel.icStart4,viewBinding.layHeaderHotel.icStart5),dataPurchaseDetail.dataHotel.ratingStar.toDouble().toString())
        viewBinding.pageFlightAndTrain.hidenLayout()
    }

    private fun initPageFlightDetail() {
        viewBinding.flightHeader.lineFlightHeader.visibility = View.VISIBLE
        viewBinding.flightHeader.pnrCodeFlight.text          = dataPurchaseDetail.dataFlight[positionSelectedItem].pnrCode.toString()
        viewBinding.flightHeader.icCopyCode.setOnClickListener { Globals.copyText(dataPurchaseDetail.dataFlight[positionSelectedItem].pnrCode.toString(),this) }
        viewBinding.lineTicket.visibility = View.GONE
        viewBinding.toolbar.showtitlePurchaseTrain(dataPurchaseDetail.dataFlight[positionSelectedItem].originCity,dataPurchaseDetail.dataFlight[positionSelectedItem].destinationCity)

        initRecyclerView()
        adapterSegemntFligh.setData(dataPurchaseDetail.dataFlight[positionSelectedItem].Segment)

        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataFlight(dataPurchaseDetail.dataFlight[positionSelectedItem],dataPurchaseDetail.totalPaid)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initRecyclerView() {
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        viewBinding.flightHeader.rvDetailFlight.layoutManager = layoutManager
        viewBinding.flightHeader.rvDetailFlight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        viewBinding.flightHeader.rvDetailFlight.adapter = adapterSegemntFligh
    }

    private fun initPageTrainDetail() {
        viewBinding.lineTicket.visibility                     = View.VISIBLE
        viewBinding.flightHeader.lineFlightHeader.visibility  = View.GONE
        viewBinding.layHeaderHotel.lineDetailHotel.visibility = View.GONE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.VISIBLE

        val data = dataPurchaseDetail.dataTrain[positionSelectedItem]
        viewBinding.layDetailTrain.tvNameTrain.text           = data.nameTrain
        viewBinding.layDetailTrain.tvTrainCode.text           = data.trainCode
        viewBinding.layDetailTrain.tvClassNameTrain.text      = data.className
        viewBinding.layDetailTrain.tvTimeDeparture.text       = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd HH:mm","HH:mm")
        viewBinding.layDetailTrain.tvTimeArrival.text         = DateConverter().getDate(data.dateArrival,"yyyy-MM-dd HH:mm","HH:mm")
        viewBinding.layDetailTrain.tvDateDeparture.text       = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd HH:mm","dd MMM")
        viewBinding.layDetailTrain.tvDateArrival.text         = DateConverter().getDate(data.dateArrival,"yyyy-MM-dd HH:mm","dd MMM")

        viewBinding.layDetailTrain.tvDepartureTrain.text             = data.originCity
        viewBinding.layDetailTrain.tvStationDepartureTrain.text      = data.originStation
        viewBinding.layDetailTrain.tvArrivalTrain.text               = data.destinationCity
        viewBinding.layDetailTrain.tvArrivalStationTrain.text        = data.destinationStation
        viewBinding.layDetailTrain.tvTimeDurationTrain.text          = data.durationTime

        viewBinding.tvPnrCode.text = dataPurchaseDetail.dataTrain[positionSelectedItem].pnrCode
        viewBinding.toolbar.showtitlePurchaseTrain(dataPurchaseDetail.dataTrain[positionSelectedItem].originCity.toString(),dataPurchaseDetail.dataTrain[positionSelectedItem].destinationCity.toString())
//        val data : DetailTrainPurchaseModel = getDataPageTrain()
        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataTrain(dataPurchaseDetail.dataTrain[positionSelectedItem],dataPurchaseDetail.totalPaid)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initToolbar() {
        viewBinding.toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        viewBinding.toolbar.callbackOnclickToolbar(this)
    }

    private fun initPopUpMenu(view:View) {
        val binding  = MenuPopupPurchaseDetailListBinding.inflate(LayoutInflater.from(this))
        val dialog = Globals.showPopup(view,binding.root)

        if (getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM){
            val isRefund     = getDataIsRefund().first
            val isReschedule = getDataIsRefund().second
            if (!isRefund){
                binding.btnReimbursement.visibility = View.VISIBLE
                binding.lineRefund.visibility       = View.VISIBLE

                if (!isReschedule){
                    binding.lineReschedule.visibility = View.VISIBLE
                    binding.btnReschedule.visibility  = View.VISIBLE
                }
                else {
                    binding.lineReschedule.visibility = View.GONE
                    binding.btnReschedule.visibility  = View.GONE
                }
            }
            else {
                binding.btnReimbursement.visibility = View.GONE
                binding.lineRefund.visibility       = View.GONE
            }

        }

        binding.btnReimbursement.setOnClickListener {
            dialog.dismiss()
            typeDialogView = DIALOG_REFUND
            checkPermissionCameraAndFile()
        }
        binding.btnReschedule.setOnClickListener {
            dialog.dismiss()
            typeDialogView = DIALOG_RESCHEDULE
            checkPermissionCameraAndFile()
        }
        binding.btnBackToHomepage.setOnClickListener {
            gotoActivity(HomeActivity::class.java)
        }
        binding.btnBackToPurchase.setOnClickListener{
            dialog.dismiss()
            finish()
        }
    }

    private fun getDataIsRefund(): Pair<Boolean,Boolean> {
        when(dataPurchaseDetail.itemType){
            Constants.TripType.Airline -> {
                return Pair(dataPurchaseDetail.dataFlight[positionSelectedItem].Segment.first().isRefund,dataPurchaseDetail.dataFlight[positionSelectedItem].Segment.first().isReschedule)
            }
            Constants.TripType.KAI -> {
                return Pair(dataPurchaseDetail.dataTrain[positionSelectedItem].isRefund,dataPurchaseDetail.dataTrain[positionSelectedItem].isReschedule)
            }
            else -> {
                return Pair(dataPurchaseDetail.dataHotel.isRefund,dataPurchaseDetail.dataHotel.isReschedule)
            }
        }
    }

    private fun rescheduleListener() {
        val dialog = RescheduleDialog(isFlightAndTrain,object : RescheduleDialog.CallbackRescheduleDialog{
            override fun dataReturn(
                mDataAttachment: ArrayList<UploadModel>,
                mStartDate: String,
                mEndDate: String,
                mNotes: String
            ) {
                dataAttacmennth = mDataAttachment
                startDate = mStartDate
                endDate = mEndDate
                notes = mNotes
                getReschedule()
            }
        },true)
        showDialogFragment(dialog)
    }

    private fun getReschedule() {
        var data = HashMap<Any, Any>()
        when(dataPurchaseDetail.itemType){
            Constants.TripType.Airline -> {
                data = dataRescheduleRequestFlight()
            }
            Constants.TripType.Hotel -> {
                data = dataRescheduleRequestHotel()
            }
        }
        if (getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM){
            showDialog("")
            GetDataApproval(getBaseUrl()).reschedule(getToken(),data,object :CallbackString{
                override fun successLoad(data: String) {
                    hideDialog()
                    setToast("Success")
                    finish()
                }

                override fun failedLoad(message: String) {
                    hideDialog()
                    setToast("failed")
                }
            })
        }
    }

    private fun dataRescheduleRequestFlight(): HashMap<Any, Any> {
        val data = RescheduleFlightRequest()
        data.tripCode       = dataPurchaseDetail.code
        data.participant    = participantRequest()
        data.reschedule     = rescheduleRequest()
        data.attachment     = attatchmentRequest()
        return Globals.classToHashMap(data, RescheduleFlightRequest::class.java)
    }

    private fun attatchmentRequest(): ArrayList<AttachmentItemReschedule> {
        val data = ArrayList<AttachmentItemReschedule>()
        dataAttacmennth.forEach {
            data.add(AttachmentItemReschedule(it.nameImage,it.url))
        }
        return data
    }

    private fun rescheduleRequest(): Reschedule {
        val data = Reschedule()
        data.bookingCode = dataPurchaseDetail.dataFlight[positionSelectedItem].pnrCode
        data.type        = 0
        data.changeDate  = startDate
        data.changeTime  = endDate
        data.changeNotes = notes
        return data
    }

    private fun participantRequest(): ParticipantReschedule {
        val data  = ParticipantReschedule()
        data.id   = dataPurchaseDetail.tripMemberId
        return data
    }

    private fun dataRescheduleRequestHotel(): HashMap<Any, Any> {
        val data = RescheduleHotelRequest()
        data.tripCode           = dataPurchaseDetail.code
        data.participant        = participantRequest()
        data.reschedule    = rescheduleHotelRequest()
        data.attachment         = attatchmentRequest()
        return Globals.classToHashMap(data, RescheduleHotelRequest::class.java)
    }

    private fun rescheduleHotelRequest(): RescheduleHotel {
        val data = RescheduleHotel()
        data.bookingCode    = dataPurchaseDetail.dataHotel.voucerCode
        data.type           = 1
        data.changeCheckin  = startDate
        data.changeCheckout = endDate
        data.changeNotes    = notes
        return data
    }


    private fun refundListener() {
        val dialog = RescheduleDialog(true,object : RescheduleDialog.CallbackRescheduleDialog{
            override fun dataReturn(
                mDataAttachment: ArrayList<UploadModel>,
                mStartDate: String,
                mEndDate: String,
                mNotes: String
            ) {
                dataAttacmennth = mDataAttachment
                startDate = mStartDate
                endDate = mEndDate
                notes = mNotes
                getRefund()
            }
        },false)
        showDialogFragment(dialog)
    }

    private fun getRefund() {
        showDialog("")
        if (getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM){
            GetDataApproval(getBaseUrl()).refund(getToken(),refundRequest(),object :CallbackString{
                override fun successLoad(data: String) {
                    hideDialog()
                    setToast("Success")
                    finish()
                }

                override fun failedLoad(message: String) {
                    hideDialog()
                    setToast("Failed")
                }
            })
        }
    }

    private fun refundRequest(): HashMap<Any, Any> {
        val refund = RefundRequest()
        refund.tripCode        = dataPurchaseDetail.code
        refund.listPnr         = dataPnrrequest()
        refund.participant     = dataParticipantRequest()
        return Globals.classToHashMap(refund,RefundRequest::class.java)
    }

    private fun dataParticipantRequest(): Participant {
        val data = Participant()
        data.id  = dataPurchaseDetail.tripMemberId
        return data
    }

    private fun dataPnrrequest(): ListPnr {
        val data = ListPnr()
        data.flights = listFlight()
        data.hotels  = listHotel()
        data.listAttachments = dataAttacthment()
        return data
    }

    private fun dataAttacthment(): ArrayList<ListAttachmentsItem> {
        val data = ArrayList<ListAttachmentsItem>()
        dataAttacmennth.forEach {
            data.add(ListAttachmentsItem(it.nameImage,it.url))
        }
        return data
    }

    private fun listHotel(): ArrayList<HotelsItem> {
        val data = ArrayList<HotelsItem>()
        if (dataPurchaseDetail.dataHotel.idHotel.isNotEmpty()){
            data.add(HotelsItem(dataPurchaseDetail.dataHotel.idHotel))
        }
        return data
    }

    private fun listFlight(): ArrayList<FlightsItem> {
        val data = ArrayList<FlightsItem>()
        if (dataPurchaseDetail.dataFlight.isNotEmpty()){
            data.add(FlightsItem(dataPurchaseDetail.dataFlight[positionSelectedItem].idFlight))
        }
        return data
    }

    private fun checkPermissionCameraAndFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission()
            }
            else {
                showDialogPicker()
            }
        }
        else {
            showDialogPicker()
        }
    }

    private fun showDialogPicker() {
        when (typeDialogView){
            DIALOG_RESCHEDULE ->{
                rescheduleListener()
            }
            DIALOG_REFUND ->{
                refundListener()
            }
        }
    }

    protected fun checkPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        return permission
    }

    protected fun requestPermission() {
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)&&
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            Toast.makeText(
                this,
                "Read External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ), READ_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            READ_REQUEST_CODE ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showDialogPicker()
                }
                else{
                    checkPermissionCameraAndFile()
                }
            }
        }
    }




    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        initPopUpMenu(viewBinding.toolbar.getImageCart())
    }
}