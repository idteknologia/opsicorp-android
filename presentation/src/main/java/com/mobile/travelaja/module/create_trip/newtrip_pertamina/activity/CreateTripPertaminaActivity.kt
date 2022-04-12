package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

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
import com.mobile.travelaja.databinding.ActivityNewCreatetripplanBinding
import com.mobile.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.mobile.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.mobile.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_new_createtripplan.*
import kotlinx.android.synthetic.main.activity_new_createtripplan.btn_next
import kotlinx.android.synthetic.main.activity_new_createtripplan.et_end_date
import kotlinx.android.synthetic.main.activity_new_createtripplan.et_event
import kotlinx.android.synthetic.main.activity_new_createtripplan.et_notes
import kotlinx.android.synthetic.main.activity_new_createtripplan.et_purpose
import kotlinx.android.synthetic.main.activity_new_createtripplan.layEvent
import kotlinx.android.synthetic.main.activity_new_createtripplan.rv_attachment
import kotlinx.android.synthetic.main.activity_new_createtripplan.tv_from
import kotlinx.android.synthetic.main.activity_new_createtripplan.tv_notes_count
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.CashAdvanceRequest
import opsigo.com.datalayer.request_model.travel_request.CheckAvaibilityDateRequest
import opsigo.com.domainlayer.callback.CallbackCashAdvance
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.callback.CallbackTypeActivity
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.travel_request.CashAdvanceModel
import opsigo.com.domainlayer.model.travel_request.ChangeTripModel
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.io.File
import java.util.ArrayList
import java.util.HashMap


class CreateTripPertaminaActivity : BaseActivityBinding<ActivityNewCreatetripplanBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, CreateTripView,
        KoinComponent, NewCalendarViewOpsicorp.CallbackResult, View.OnClickListener {

    override fun bindLayout(): ActivityNewCreatetripplanBinding {
        return ActivityNewCreatetripplanBinding.inflate(layoutInflater)
    }

    val presenter by inject<CreateTripPresenter> { parametersOf(this) }
    var dialogCamera = DialogCamera()
    var dataChangeTrip = ChangeTripModel()
    var SELECT_CODE_PURPOSE = 98
    var SELECT_CODE_ACTIVITY = 78
    var READ_REQUEST_CODE = 67
    var m_startdate = ""
    var m_endate = ""
    var isWbs = false
    var isPartner = false
    var isRoundTrip = false
    var idPurphose = ""
    var idActivity = ""
    var nonCbt = false
    var tripPartner = false
    var typeTrip = false
    var isDomestic = true
    var purposeIsEmpty = true
    var activityIsEmpty = true
    var wbsIsEmpty = true
    var partnerIsEmpty = true
    var notesIsEmpty = true
    var offDutty = false

    override fun onMain() {
        initOnClick()
        Globals.typeAccomodation = ""
        /*setTextDocs()*/
        presenter.setDataAutomatically()
        presenter.initRecyclerViewAttachment(rv_attachment)

        changeButtonNextGrayColor()
        if (intent.getBooleanExtra(Constants.CHANGE_TRIP, false)) {
            getDataForChangeTrip()
        }
        getDataActivity()
    }

    private fun getDataActivity() {
        GetDataTravelRequest(getBaseUrl()).getTypeActivity(Globals.getToken(),object :
            CallbackTypeActivity {
            override fun success(data: ArrayList<TypeActivityTravelRequestModel>) {
                DataTemporary.dataActivity.clear()
                DataTemporary.dataActivity.addAll(data)
            }

            override fun failed(message: String) {

            }

        })
    }

    private fun setTextDocs() {
        val textDocs = getString(R.string.txt_document_description)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewBinding.tvDescDoc1.text = Html.fromHtml(textDocs, Html.FROM_HTML_MODE_COMPACT)
        } else {
            viewBinding.tvDescDoc1.text = Html.fromHtml(textDocs)
        }
    }

    private fun getDataForChangeTrip() {
        dataChangeTrip = Serializer.deserialize(Constants.DATA_CHANGE_TRIP, ChangeTripModel::class.java)

        btn_switch2.isChecked = dataChangeTrip.isNonCbt == true
        nonCbt = btn_switch2.isChecked

        btn_switch.isChecked = dataChangeTrip.isDomestic == false
        isDomestic = !btn_switch.isChecked
        typeTrip = btn_switch.isChecked

        if (dataChangeTrip.purpose == "Perjalanan Dinas Investasi (WBS)") {
            isWbs = true
            layEvent.visible()
        } else {
            isWbs = false
            layEvent.gone()
        }

        if (dataChangeTrip.purpose == "Pelatihan Purna Karya") {
            isPartner = true
            line_partner.visible()
            btn_switch3.isChecked = dataChangeTrip.isTripPartner == true
        } else {
            isPartner = false
            line_partner.gone()
            layPartnerName.gone()
        }

        if (dataChangeTrip.purpose == "Off Duty Kasim") {
            nonCbt = true
            btn_switch2.isChecked = true
            btn_switch2.isEnabled = false
            btn_switch.isEnabled = false
            offDutty = true
        } else {
            nonCbt = false
            btn_switch2.isChecked = false
            btn_switch2.isEnabled = true
            btn_switch.isEnabled = true
            offDutty = false
        }


        et_purpose.text = dataChangeTrip.purpose
        et_activity_type.text = dataChangeTrip.businessTripType
        tv_from.text = DateConverter().getDate(dataChangeTrip.routes.first().departureDateView, "dd-MM-yyyy", "dd MMM yyyy")
        et_end_date.text = DateConverter().getDate(dataChangeTrip.routes.last().departureDateView, "dd-MM-yyyy", "dd MMM yyyy")

        m_startdate = dataChangeTrip.startDate
        m_endate = dataChangeTrip.returnDate
        et_notes.setText(dataChangeTrip.remark)

        purposeIsEmpty = false
        activityIsEmpty = false
        notesIsEmpty = false

        checkEmptyField()

        setDataAttachment(dataChangeTrip.attactment)
    }

    private fun setDataAttachment(mData: ArrayList<UploadModel>) {
        if (mData.isNotEmpty()) {
            rv_attachment.visible()
            presenter.dataAttachment.clear()
            presenter.dataAttachment.addAll(mData)
            presenter.adapter.setData(presenter.dataAttachment)
        } else {
            rv_attachment.gone()
        }
    }

    private fun initOnClick() {
        btn_next.setTextButton(getString(R.string.continue_))
        btn_next.callbackOnclickButton(this)

        ic_back.setOnClickListener(this)
        et_notes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val countText: Int = s.toString().length
                tv_notes_count.text = "${countText}/100"
                if (s.toString().isEmpty()) {
                    notesIsEmpty = true
                    checkEmptyField()
                } else {
                    notesIsEmpty = false
                    checkEmptyField()
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        btn_switch.setOnClickListener(this)
        btn_switch2.setOnClickListener(this)
        btn_switch3.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data, this)

        when (requestCode) {

            SELECT_CODE_PURPOSE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.getStringExtra("nameCountry").equals("Perjalanan Dinas Investasi (WBS)")) {
                        isWbs = true
                        layEvent.visibility = View.VISIBLE
                    } else {
                        isWbs = false
                        layEvent.visibility = View.GONE
                    }
                    if (data?.getStringExtra("nameCountry").equals("Pelatihan Purna Karya")) {
                        isPartner = true
                        line_partner.visible()
                    } else {
                        isPartner = false
                        line_partner.gone()
                        layPartnerName.gone()
                    }
                    if (data?.getStringExtra("nameCountry").equals("Off Duty Kasim")) {
                        nonCbt = true
                        btn_switch2.isChecked = true
                        btn_switch2.isEnabled = false
                        btn_switch.isEnabled = false
                        offDutty = true
                    } else {
                        nonCbt = false
                        btn_switch2.isEnabled = true
                        btn_switch.isEnabled = true
                        offDutty = false
                    }

                    purposeIsEmpty = false
                    checkEmptyField()
                    et_purpose.text = data?.getStringExtra("nameCountry")
                    idPurphose = data?.getStringExtra("idCountry").toString()
                }
            }

            SELECT_CODE_ACTIVITY -> {
                if (resultCode == Activity.RESULT_OK) {
                    activityIsEmpty = false
                    checkEmptyField()
                    et_activity_type.text = data?.getStringExtra("nameCountry")
                    idActivity = data?.getStringExtra("idCountry").toString()
                }
            }
        }
    }

    fun getDateWithCalendar(view: View) {
        Globals.ONE_TRIP = false
        if (nonCbt){
            NewCalendarViewOpsicorp().showCalendarView(this, Constant.DOUBLE_SELECTED,true)
        } else {
            NewCalendarViewOpsicorp().showCalendarView(this,Constant.DOUBLE_SELECTED,false)
        }
    }

    fun selectTravelPurpose(view: View) {
        btn_switch3.isChecked = false
        val bundle = Bundle()
        bundle.putString("emplaoyId", "purpose")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("titleHeader", getString(R.string.what_is_your_travel_purpose))
        gotoActivityResultWithBundle(DialogPurpose::class.java, bundle, SELECT_CODE_PURPOSE)
    }

    fun selectActivityType(view: View) {
        val bundle = Bundle()
        bundle.putString("emplaoyId", "activity")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("titleHeader", getString(R.string.select_trip_activity_type))
        gotoActivityResultWithBundle(DialogPurpose::class.java, bundle, SELECT_CODE_ACTIVITY)
    }

    fun openCameraView(view: View) {
        checkPermissionCameraAndFile()
    }

    private fun checkPermissionCameraAndFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                        READ_REQUEST_CODE)
            } else {
                showDialogCamera()
            }
        } else {
            showDialogCamera()
        }
    }

    private fun showDialogCamera() {
        showDialogFragment(dialogCamera)
        dialogCamera.setCallbak(object : DialogCameraCallback {
            override fun data(imagePath: String, file: File, type: String?) {
                presenter.addDataAttactment(imagePath, file,type )
            }
        })
    }

    fun checkEmptyField() {
        if (!purposeIsEmpty && !activityIsEmpty && !notesIsEmpty) {
            changeButtonNextOrangeColor()
        } else {
            changeButtonNextGrayColor()
        }
    }

    private fun changeButtonNextGrayColor() {
        btn_next.changeTextColorButton(R.color.gray_total)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_dark_select_budget)
        btn_next.isClickable = false
    }

    private fun changeButtonNextOrangeColor() {
        btn_next.changeTextColorButton(R.color.textButtonColor)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_yellow)
        btn_next.isClickable = true
    }

//    override fun setDataAutomatically2(tvFrom: String, etEnd: String, mStartDate: String, mEndDate: String) {
//        tv_from.text = tvFrom
//        et_end_date.text = etEnd
//        m_startdate = mStartDate
//        m_endate = mEndDate
//    }

    override fun onClicked() {
        presenter.createTripNow()
    }


    override fun loadDataView() {
        /*showLoadingOpsicorp(true)*/
    }

    override fun failedLoadDataView() {
        /*hideLoadingOpsicorp()*/
    }

    override fun successLoadDataView() {
        /*hideLoadingOpsicorp()*/
    }

    override fun setDataAutomatically(dataNow: String, dataNow1: String, city: String, idCity: String, mStartDate: String, mEndDate: String) {

    }

    override fun SuccessCreateTrip() {
        val sorry: String = getString(R.string.sorry)
        val input_event: String = getString(R.string.please_fill_event_name)

        if (isWbs) {
            if (et_event.text.isNotEmpty()) {
                gotoSelectRoutes()
            } else {
                Globals.showAlert(sorry, input_event, this)
            }
        } else {
            gotoSelectRoutes()
        }
    }

    private fun gotoSelectRoutes() {
        if (btn_next.isClickable) {
            val dataOrderCreatTrip = DataBisnisTripModel()
            dataOrderCreatTrip.namePusrpose = et_purpose.text.toString()
            dataOrderCreatTrip.nameActivity = et_activity_type.text.toString()
            if (isWbs) {
                dataOrderCreatTrip.isWbs = isWbs
                dataOrderCreatTrip.wbsNumber = et_event.text.toString()
            }
            if (isPartner) {
                dataOrderCreatTrip.isTripPartner = isPartner
                dataOrderCreatTrip.tripPartnerName = et_partner.text.toString()
            }
            dataOrderCreatTrip.startDate = m_startdate//tv_from.text.toString()

            dataOrderCreatTrip.notes = et_notes.text.toString()
            dataOrderCreatTrip.image.addAll(presenter.dataDokumentUploaded())

            dataOrderCreatTrip.idPurphose = idPurphose
            dataOrderCreatTrip.dateCreated = DateConverter().getDay("dd MMMM yyyy HH:mm")

            dataOrderCreatTrip.isCbt = nonCbt
            dataOrderCreatTrip.isInternational = typeTrip
            if (typeTrip) {
                dataOrderCreatTrip.statusCreateTrip = "International Route"
            } else {
                dataOrderCreatTrip.statusCreateTrip = "Domestic Route"
            }

            val tripRange = "${Globals.countDaysBettwenTwoDate(m_startdate,m_endate,"yyyy-MM-dd")+1}".toInt()
            dataOrderCreatTrip.tripRange = tripRange

            val bundle = Bundle()
            if (dataChangeTrip.isChangeTrip) {
                dataChangeTrip.startDate = m_startdate
                dataChangeTrip.returnDate = m_endate
                dataOrderCreatTrip.trnNumber = dataChangeTrip.trnNumber
                dataOrderCreatTrip.tripCodeOld = dataChangeTrip.tripCodeOld
                dataOrderCreatTrip.tripIdOld = dataChangeTrip.tripIdOld
                dataOrderCreatTrip.isChangeTrip = dataChangeTrip.isChangeTrip
            }
            if (m_startdate == m_endate){
                isRoundTrip = true
                dataOrderCreatTrip.endDate = m_startdate
            } else {
                isRoundTrip = false
                dataOrderCreatTrip.endDate = m_endate
            }

            bundle.putString("data_order", Serializer.serialize(dataOrderCreatTrip, DataBisnisTripModel::class.java))
            bundle.putString("data_change_trip", Serializer.serialize(dataChangeTrip, ChangeTripModel::class.java))

            if (et_purpose.text == resources.getString(R.string.select_your_purpose)) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.select_your_purpose), this)
            } else if (presenter.dataAttachment.size <= 0) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.attach_your_document), this)
            } else if (attactmentIsEmpty()) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.waiting_upload_file), this)
            } else {
                checkAvailableDate(bundle)
                showLoadingOpsicorp(true)
            }
        }
    }

    private fun attactmentIsEmpty(): Boolean {
        return presenter.dataAttachment.filter { it.url.isEmpty() }.size > 0
    }

    override fun failedCreareTrip() {
        val sorry: String = getString(R.string.sorry)
        val upload_doc: String = getString(R.string.please_upload_your_doc)
        Globals.showAlert(sorry, upload_doc, this)
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_from.text = displayStartDate
        m_startdate = startDate
        setLog("start date " + m_startdate)
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        et_end_date.text = displayEndDate
        m_endate = endDate
        setLog("end date " + m_endate)
    }

    override fun canceledCalendar() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialogCamera()
                } else {
                    checkPermissionCameraAndFile()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            ic_back -> {
                onBackPressed()
            }
            btn_switch -> {
                typeTrip = btn_switch.isChecked
                isDomestic = !btn_switch.isChecked

            }
            btn_switch2 -> {
                nonCbt = btn_switch2.isChecked
            }
            btn_switch3 -> {
                tripPartner = btn_switch3.isChecked
                if (tripPartner.equals(true)) {
                    layPartnerName.visible()
                } else {
                    layPartnerName.gone()
                }

            }
        }
    }

    private fun checkAvailableDate(bundle: Bundle) {

        GetDataTravelRequest(getBaseUrl()).checkDateAvaibility(getToken(), dataDate(), object : CallbackString {
            override fun successLoad(data: String) {
                hideLoadingOpsicorp()
                if (!data.contains("true")) {
                    showAllert(getString(R.string.sorry), data)
                } else {
                    bundle.putString(SelectTripRoutePertaminaActivity.START_DATE, m_startdate)
                    bundle.putString(SelectTripRoutePertaminaActivity.END_DATE, m_endate)
                    bundle.putBoolean(SelectTripRoutePertaminaActivity.IS_INTERNATIONAL, typeTrip)
                    bundle.putBoolean(SelectTripRoutePertaminaActivity.NON_CBT, nonCbt)
                    bundle.putBoolean(SelectTripRoutePertaminaActivity.OFF_DUTTY, offDutty)
                    gotoActivityWithBundle(SelectTripRoutePertaminaActivity::class.java, bundle)
                    /*checkCashAdvance(bundle)*/
                }
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    /*private fun checkCashAdvance(bundle: Bundle) {
        GetDataTravelRequest(getBaseUrl()).checkCashAdvance(getToken(), dataRequest(), object : CallbackCashAdvance {
            override fun successLoad(data: CashAdvanceModel) {
                hideLoadingOpsicorp()
                Constants.DATA_CASH_ADVANCE = Serializer.serialize(data)
                setLog("Test Cash", Serializer.serialize(Constants.DATA_CASH_ADVANCE))
                gotoActivityWithBundle(SelectTripRoutePertaminaActivity::class.java, bundle)
            }

            override fun failedLoad(message: String) {

            }

        })
    }

    private fun dataRequest(): HashMap<Any, Any> {
        val data = CashAdvanceRequest()
        data.isDomestic = isDomestic
        data.activityType = et_activity_type.text.toString()
        data.startDate = tv_from.text.toString()
        data.endDate = et_end_date.text.toString()
        return Globals.classToHashMap(data, CashAdvanceRequest::class.java)
    }*/

    private fun dataDate(): HashMap<Any, Any> {
        val data = CheckAvaibilityDateRequest()
        data.endDate = et_end_date.text.toString()//"2021-07-05"
        data.startDate = tv_from.text.toString() //"2021-06-30"
        data.tripCodeOld = dataChangeTrip.tripCodeOld
        return Globals.classToHashMap(data, CheckAvaibilityDateRequest::class.java)
    }

}