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
import com.mobile.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
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
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class CreateTripPertaminaActivity : BaseActivityBinding<ActivityNewCreatetripplanBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, CreateTripView,
        KoinComponent, NewCalendarViewOpsicorp.CallbackResult, View.OnClickListener {

    override fun bindLayout(): ActivityNewCreatetripplanBinding {
        return ActivityNewCreatetripplanBinding.inflate(layoutInflater)
    }

    val presenter by inject<CreateTripPresenter> { parametersOf(this) }
    var dialogCamera = DialogCamera()
    var SELECT_CODE_PURPOSE = 98
    var SELECT_CODE_ACTIVITY = 78
    var READ_REQUEST_CODE = 67
    var m_startdate = ""
    var m_endate = ""
    var isWbs = false
    var isPartner = false
    var idPurphose = ""
    var idActivity = ""
    var nonCbt = false
    var tripPartner = false
    var typeTrip = false
    var purposeIsEmpty = true
    var activityIsEmpty = true
    var wbsIsEmpty = true
    var partnerIsEmpty = true
    var notesIsEmpty = true
    var attachmentIsEmpty = true

    override fun onMain() {
        initOnClick()
        Globals.typeAccomodation = ""
        setTextDocs()
        presenter.setDataAutomatically()
        presenter.initRecyclerViewAttachment(rv_attachment)

        changeButtonNextGrayColor()
    }

    private fun setTextDocs(){
        val textDocs = getString(R.string.txt_document_description)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewBinding.tvDescDoc.text = Html.fromHtml(textDocs,Html.FROM_HTML_MODE_COMPACT)
        }else {
            viewBinding.tvDescDoc.text = Html.fromHtml(textDocs)
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
        NewCalendarViewOpsicorp().showCalendarView(this, Constant.DOUBLE_SELECTED)
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
            override fun data(imagePath: String) {
                presenter.addDataAttactment(imagePath)
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

    override fun setDataAutomatically2(tvFrom: String, etEnd: String, mStartDate: String, mEndDate: String) {
        tv_from.text = tvFrom
        et_end_date.text = etEnd
        m_startdate = mStartDate
        m_endate = mEndDate
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
        if (btn_next.isClickable.equals(true)) {
            val dataOrderCreatTrip = DataBisnisTripModel()
            dataOrderCreatTrip.namePusrpose = et_purpose.text.toString()
            dataOrderCreatTrip.nameActivity = et_activity_type.text.toString()
            if (isWbs.equals(true)) {
                dataOrderCreatTrip.isWbs = isWbs
                dataOrderCreatTrip.wbsNumber = et_event.text.toString()
            }
            if (isPartner.equals(true)) {
                dataOrderCreatTrip.isTripPartner = isPartner
                dataOrderCreatTrip.tripPartnerName = et_partner.text.toString()
            }
            dataOrderCreatTrip.startDate = m_startdate//tv_from.text.toString()
            dataOrderCreatTrip.endDate = m_endate//et_end_date.text.toString()
            dataOrderCreatTrip.notes = et_notes.text.toString()
            dataOrderCreatTrip.tripcode = System.currentTimeMillis().toString()
            dataOrderCreatTrip.image.addAll(presenter.dataDokumentUploaded())

            dataOrderCreatTrip.idPurphose = idPurphose
            dataOrderCreatTrip.dateCreated = DateConverter().getDay("dd MMMM yyyy HH:mm")

            dataOrderCreatTrip.isCbt = nonCbt
            dataOrderCreatTrip.isInternational = typeTrip
            if (typeTrip.equals(true)){
                dataOrderCreatTrip.statusCreateTrip = "International Route"
            } else {
                dataOrderCreatTrip.statusCreateTrip = "Domestic Route"
            }


            val bundle = Bundle()
            bundle.putString("data_order", Serializer.serialize(dataOrderCreatTrip, DataBisnisTripModel::class.java))

            if (et_purpose.text == resources.getString(R.string.select_your_purpose)) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.select_your_purpose), this)
            } else if (presenter.dataAttachment.size <= 0) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.attach_your_document), this)
            } else if (attactmentIsEmpty()) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.waiting_upload_file), this)
            } else {
                bundle.putString(SelectTripRoutePertaminaActivity.START_DATE,m_startdate)
                bundle.putString(SelectTripRoutePertaminaActivity.END_DATE,m_endate)
                bundle.putBoolean(SelectTripRoutePertaminaActivity.IS_INTERNATIONAL,typeTrip)
                gotoActivityWithBundle(SelectTripRoutePertaminaActivity::class.java, bundle)
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
        tv_from.setText(displayStartDate)
        m_startdate = startDate
        setLog("start date " + m_startdate)
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        et_end_date.setText(displayEndDate)
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

}