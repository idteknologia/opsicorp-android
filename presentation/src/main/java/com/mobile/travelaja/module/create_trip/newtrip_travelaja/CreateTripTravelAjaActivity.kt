package com.mobile.travelaja.module.create_trip.newtrip_travelaja

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import com.opsicorp.sliderdatepicker.utils.Constant
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityNewCreatetripTravelajaBinding
import com.mobile.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.mobile.travelaja.module.create_trip.success_create_trip.SucessCreateTripPlaneActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.mobile.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.*
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.ContactRequest
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsItem
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.HashMap


class CreateTripTravelAjaActivity : BaseActivityBinding<ActivityNewCreatetripTravelajaBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, CreateTripView,
        KoinComponent, NewCalendarViewOpsicorp.CallbackResult, View.OnClickListener{
    override fun bindLayout(): ActivityNewCreatetripTravelajaBinding {
        return ActivityNewCreatetripTravelajaBinding.inflate(layoutInflater)
    }

    val presenter by inject<CreateTripPresenter> { parametersOf(this) }
    var dialogCamera = DialogCamera()
    var SELECT_CODE_PURPOSE = 98
    var SELECT_CODE_COUNTRY_FROM  = 78
    var SELECT_CODE_COUNTRY_TO = 79
    var READ_REQUEST_CODE = 67
    var m_startdate = ""
    var m_endate = ""
    var idPurphose = ""
    var idCountry  = ""
    var idBudget = ""
    var idCost = ""
    var purposeIsEmpty = true
    var notesIsEmpty = true

    override fun onMain() {
        initOnClick()
        setTextDocs()
        presenter.setDataAutomatically()
        presenter.initRecyclerViewAttachment(rv_attachment)
        idBudget = "388888e0-2f5d-48da-9f13-9e93599f5ae5"
        idCost = "000002"
    }

    private fun setTextDocs() {
        val textDocs = getString(R.string.txt_document_description)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewBinding.tvDescDoc.text = Html.fromHtml(textDocs, Html.FROM_HTML_MODE_COMPACT)
        }else {
            viewBinding.tvDescDoc.text = Html.fromHtml(textDocs)
        }
    }

    private fun initOnClick() {
        btn_next.setTextButton("Continue")
        btn_next.callbackOnclickButton(this)

        ic_back.setOnClickListener(this)
        et_notes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var countText: Int
                countText = s.toString().length
                tv_notes_count.text = "${countText}/100"
                if (s.toString().isEmpty()){
                    notesIsEmpty = true
                    checkEmptyField()
                }else {
                    notesIsEmpty = false
                    checkEmptyField()
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun checkEmptyField() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

        when(requestCode){

            SELECT_CODE_PURPOSE -> {
                if (resultCode== Activity.RESULT_OK){
                    viewBinding.etPurpose.text = data?.getStringExtra("nameCountry")
                    idPurphose      = data?.getStringExtra("idCountry").toString()
                }
            }

            SELECT_CODE_COUNTRY_FROM -> {
                if (resultCode== Activity.RESULT_OK){
                    viewBinding.tvOriginCity.text = data?.getStringExtra("nameCountry")
                    idCountry       = data?.getStringExtra("idCountry").toString()
                }
            }

            SELECT_CODE_COUNTRY_TO -> {
                if (resultCode== Activity.RESULT_OK){
                    viewBinding.tvDestinationCity.text = data?.getStringExtra("nameCountry")
                    idCountry       = data?.getStringExtra("idCountry").toString()
                }
            }

        }
    }

    fun getDateWithCalendar(view: View){
        Globals.ONE_TRIP = false
        NewCalendarViewOpsicorp().showCalendarView(this, Constant.DOUBLE_SELECTED)
    }

    fun selectTravelPurpose(view: View) {
        val bundle = Bundle()
        bundle.putString("emplaoyId", "purpose")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("titleHeader", "What is your Travel purpose?")
        gotoActivityResultWithBundle(DialogPurpose::class.java, bundle, SELECT_CODE_PURPOSE)
    }

    fun selectCountryFrom(view: View){
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("titleHeader","Select Destination")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY_FROM)
    }

    fun selectCountryTo(view: View){
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("titleHeader","Select Destination")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY_TO)
    }

    fun openCameraView(view: View){
        checkPermissionCameraAndFile()
    }

    private fun checkPermissionCameraAndFile() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED&&checkSelfPermission(Manifest.permission.CAMERA)!== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                        READ_REQUEST_CODE)
            } else {
                showDialogCamera()
            }
        }
        else{
            showDialogCamera()
        }
    }


    private fun showDialogCamera() {
        showDialogFragment(dialogCamera)
        dialogCamera.setCallbak(object : DialogCameraCallback {
            override fun data(imagePath: String) {
                presenter.addDataAttactment(imagePath)
            }
        })
    }


    override fun onClicked() {
        presenter.createTripNow()
    }

    override fun loadDataView() {
        showLoadingOpsicorp(true)
    }

    override fun failedLoadDataView() {
    }

    override fun successLoadDataView() {
        hideLoadingOpsicorp()
    }

    override fun setDataAutomatically(tvFrom: String, etEnd: String, city: String, idCity: String, mStartDate: String, mEndDate: String) {
        viewBinding.tvFrom.text = tvFrom
        viewBinding.etEndDate.text = etEnd
        idCountry           = idCity
        m_startdate         = mStartDate
        m_endate            = mEndDate
    }

    override fun setDataAutomatically2(dataNow: String, dataNow1: String, mStartDate: String, mEndDate: String) {

    }

    override fun SuccessCreateTrip() {
        gotoSuccessTrip()
    }

    private fun gotoSuccessTrip() {
        showLoadingOpsicorp(true)
        val dataOrderCreatTrip = DataBisnisTripModel()
        dataOrderCreatTrip.namePusrpose = et_purpose.text.toString()
        dataOrderCreatTrip.startDate = tv_from.text.toString()
        dataOrderCreatTrip.endDate = et_end_date.text.toString()
        dataOrderCreatTrip.origin = tv_origin_city.text.toString()
        dataOrderCreatTrip.destination = tv_destination_city.text.toString()
        dataOrderCreatTrip.notes   = et_notes.text.toString()
        dataOrderCreatTrip.image.addAll(presenter.dataDokumentUploaded())
        dataOrderCreatTrip.tripcode = System.currentTimeMillis().toString()
        dataOrderCreatTrip.dateCreated  = DateConverter().getDay("dd MMMM yyyy HH:mm")


        GetDataTripPlane(getBaseUrl()).saveAsDraftTripPlant(Globals.getToken(),dataRequest(dataOrderCreatTrip),object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                val bundle = Bundle()
                data.costCenter = idCost
                data.buggetId   = idBudget
                data.destinationName = tv_destination_city.text.toString()
                data.originName      = tv_origin_city.text.toString()
                Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data)
                bundle.putString("data_order", Serializer.serialize(dataOrderCreatTrip,DataBisnisTripModel::class.java))
                gotoActivityWithBundle(SucessCreateTripPlaneActivity::class.java,bundle)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert("Sorry",message)
            }

        })

    }

    private fun dataRequest(dataOrderCreatTrip: DataBisnisTripModel): HashMap<String, Any> {
        val dataRequest             = SaveAsDraftRequest()
        dataRequest.startDate       = tv_from.text.toString()
        dataRequest.returnDate      = et_end_date.text.toString()
        dataRequest.remark          = et_notes.text.toString()
        dataRequest.origin          = tv_origin_city.text.toString()
        dataRequest.type            = Globals.getConfigCompany(this).travelingPurposeFormType
        dataRequest.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        dataRequest.destination     = tv_destination_city.text.toString()
        dataRequest.purpose         = et_purpose.text.toString()

        dataRequest.tripAttachments = ArrayList()

        val contactPerson = ContactRequest()
        contactPerson.lastName  = getProfile().lastName
        contactPerson.firstName = getProfile().firstName
        dataRequest.contact         = contactPerson

        val attachment = ArrayList<TripAttachmentsItemRequest>()

        dataOrderCreatTrip.image.forEachIndexed { index, uploadModel ->
            val mDataAttachment = TripAttachmentsItemRequest()
            mDataAttachment.description = uploadModel.nameImage
            mDataAttachment.url         = uploadModel.url
            attachment.add(mDataAttachment)
        }
        dataRequest.tripAttachments = attachment

        val tripParticipant = ArrayList<TripParticipantsItem>()
        val mDataParticipant = TripParticipantsItem()
        mDataParticipant.budgetId     = idBudget
        mDataParticipant.costCenterId = idCost
        mDataParticipant.employeeId   = getProfile().employId
        tripParticipant.add(mDataParticipant)
        dataRequest.tripParticipants   = tripParticipant


        return Globals.classToHasMap(dataRequest,SaveAsDraftRequest::class.java)
    }

    override fun failedCreareTrip() {
        val sorry: String = getString(R.string.sorry)
        val upload_doc : String = getString(R.string.please_upload_your_doc)
        Globals.showAlert(sorry, upload_doc,this)
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        viewBinding.tvFrom.setText(displayStartDate)
        m_startdate = startDate
        setLog("start date "+m_startdate)
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        viewBinding.etEndDate.setText(displayEndDate)
        m_startdate = endDate
        setLog("end date "+m_endate)
    }

    override fun canceledCalendar() {

    }

    override fun onClick(v: View?) {
        when(v){
            ic_back ->{
                onBackPressed()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            READ_REQUEST_CODE ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showDialogCamera()
                }
                else{
                    checkPermissionCameraAndFile()
                }
            }
        }
    }
}