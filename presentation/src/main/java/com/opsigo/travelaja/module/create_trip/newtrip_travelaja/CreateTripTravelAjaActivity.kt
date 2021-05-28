package com.opsigo.travelaja.module.create_trip.newtrip_travelaja

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
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.BaseActivityBinding
import com.opsigo.travelaja.databinding.ActivityNewCreatetripTravelajaBinding
import com.opsigo.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.opsigo.travelaja.module.create_trip.newtrip.view.CreateTripView
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.opsigo.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.btn_next
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.et_notes
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.ic_back
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.rv_attachment
import kotlinx.android.synthetic.main.activity_new_createtrip_travelaja.tv_notes_count
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


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
    var idCountry            = ""
    var purposeIsEmpty = true
    var notesIsEmpty = true

    override fun onMain() {
        initOnClick()
        setTextDocs()
        presenter.setDataAutomatically()
        presenter.initRecyclerViewAttachment(rv_attachment)
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