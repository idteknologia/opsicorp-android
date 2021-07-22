package com.mobile.travelaja.module.create_trip.newtrip.actvity

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.app.Activity
import org.koin.core.inject
import android.content.Intent
import com.mobile.travelaja.R
import org.koin.core.KoinComponent
import android.content.pm.PackageManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.opsicorp.sliderdatepicker.utils.Constant
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import org.koin.core.parameter.parametersOf
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.activity_create_tripplan.*
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.mobile.travelaja.module.create_trip.select_budget.activity.SelectBudget
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.mobile.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity

class CreateTripActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        CreateTripView,KoinComponent,
        NewCalendarViewOpsicorp.CallbackResult {
    override fun getLayout(): Int { return R.layout.activity_create_tripplan }

    val presenter by inject<CreateTripPresenter> { parametersOf(this) }
    var dialogCamera         = DialogCamera()
    var SELECT_CODE_PURPOSE  = 98
    var SELECT_CODE_COUNTRY  = 78
    var READ_REQUEST_CODE    = 67
    var m_startdate          = ""
    var m_endate             = ""
    var isEvent              = false
    var idPurphose           = ""
    var idCountry            = ""

    override fun OnMain() {
        setInitOnClickView()
        Globals.typeAccomodation = ""

        presenter.setDataAutomatically()
        presenter.initRecyclerViewAttachment(rv_attachment)
    }


    private fun setInitOnClickView() {
        val string: String = getString(R.string.create_now)
        btn_next.setTextButton(string)
        btn_next.callbackOnclickButton(this)

        toolbar.setToolbarColorView(R.color.colorPureWhite)
        toolbar.changeImageBtnBack(R.drawable.ic_back)
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()

        et_notes.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var countText: Int
                countText = s.toString().length
                tv_notes_count.text = "${countText}/100"
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

        when(requestCode){

            SELECT_CODE_PURPOSE -> {
                if (resultCode==Activity.RESULT_OK){
                    if (data?.getStringExtra("nameCountry").equals("Event")){
                        isEvent = true
                        layEvent.visibility = View.VISIBLE
                    }
                    else{
                        isEvent = false
                        layEvent.visibility = View.GONE
                    }

                    et_purpose.text = data?.getStringExtra("nameCountry")
                    idPurphose      = data?.getStringExtra("idCountry").toString()
                }
            }

            SELECT_CODE_COUNTRY -> {
                if (resultCode==Activity.RESULT_OK){
                    et_city_to.text = data?.getStringExtra("nameCountry")
                    idCountry       = data?.getStringExtra("idCountry").toString()
                }
            }

        }
    }

    fun getDateWithCalendar(view: View){
        Globals.ONE_TRIP = false
        NewCalendarViewOpsicorp().showCalendarView(this,Constant.DOUBLE_SELECTED)
    }

    fun selectPurpose(view: View){
        val bundle = Bundle()
        bundle.putString("emplaoyId","purpose")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("titleHeader","What is your purpose")
        gotoActivityResultWithBundle(DialogPurpose::class.java,bundle,SELECT_CODE_PURPOSE)
    }

    fun selectCountry(view: View){
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("titleHeader","Select Destination")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY)
    }

    fun openCameraView(view: View){
        checkPermissionCameraAndFile()
    }

    private fun checkPermissionCameraAndFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission()
            }
            else {
                showDialogCamera()
            }
        }
        else {
            showDialogCamera()
        }
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CAMERA)!== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        ),
                        READ_REQUEST_CODE
                )
            }
            else {
                showDialogCamera()
            }
        }
        else {
            showDialogCamera()
        }*/
    }

    protected fun checkPermission(): Boolean {
        val permission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
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


    private fun showDialogCamera() {
        showDialogFragment(dialogCamera)
        dialogCamera.setCallbak(object :DialogCameraCallback{
            override fun data(imagePath: String) {
                presenter.addDataAttactment(imagePath)
            }
        })
    }

    override fun loadDataView() {
        showLoadingOpsicorp(true)
    }

    override fun failedLoadDataView() {

    }

    override fun successLoadDataView() {
        hideLoadingOpsicorp()
    }

    override fun setDataAutomatically(tvFrom: String,etEnd:String,etCity:String,idCity:String,mStartDate:String,mEndDate:String) {
        tv_from.text        = tvFrom
        et_end_date.text    = etEnd
        et_city_to.text     = etCity
        idCountry           = idCity
        m_startdate         = mStartDate
        m_endate            = mEndDate
    }

    override fun setDataAutomatically2(dataNow: String, dataNow1: String, mStartDate: String, mEndDate: String) {
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_from.setText(displayStartDate)
        m_startdate = startDate
        setLog("start date "+m_startdate)
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        et_end_date.setText(displayEndDate)
        m_endate = endDate
        setLog("end date "+m_endate)
    }

    override fun canceledCalendar() {

    }

    override fun onClicked() {
        presenter.createTripNow()
    }

    override fun failedCreareTrip() {
        val sorry: String = getString(R.string.sorry)
        val upload_doc : String = getString(R.string.please_upload_your_doc)
        Globals.showAlert(sorry, upload_doc,this)
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

    override fun SuccessCreateTrip() {
        val sorry: String = getString(R.string.sorry)
        val input_event : String = getString(R.string.please_fill_event_name)

        if (isEvent){
            if (et_event.text.isNotEmpty()){
                gotoSelectBudget()
            }
            else{
                Globals.showAlert(sorry,input_event,this)
            }
        }else{
            gotoSelectBudget()
        }


    }

    private fun gotoSelectBudget() {
        val dataOrderCreatTrip = DataBisnisTripModel()
        dataOrderCreatTrip.namePusrpose = et_purpose.text.toString()
        dataOrderCreatTrip.nameDestination = et_city_to.text.toString()
        dataOrderCreatTrip.startDate = m_startdate//tv_from.text.toString()
        dataOrderCreatTrip.endDate = m_endate//et_end_date.text.toString()
        dataOrderCreatTrip.notes   = et_notes.text.toString()
        dataOrderCreatTrip.image.addAll(presenter.dataDokumentUploaded())
        dataOrderCreatTrip.event   =  et_event.text.toString()
        dataOrderCreatTrip.tripcode = System.currentTimeMillis().toString()
        dataOrderCreatTrip.idCountry = idCountry
        dataOrderCreatTrip.idPurphose = idPurphose
        dataOrderCreatTrip.dateCreated  = DateConverter().getDay("dd MMMM yyyy HH:mm")

        val bundle = Bundle()
        bundle.putString("data_order",Serializer.serialize(dataOrderCreatTrip,DataBisnisTripModel::class.java))

        if (et_purpose.text==resources.getString(R.string.select_your_purpose)){
            Globals.showAlert(getString(R.string.txt_please),getString(R.string.select_your_purpose),this)
        }
        else if(attactmentIsEmpty()){
            Globals.showAlert(getString(R.string.txt_please),getString(R.string.waiting_upload_file),this)
        }
        else {
            gotoActivityWithBundle(SelectBudget::class.java,bundle)
        }
    }

    private fun attactmentIsEmpty(): Boolean {
        return presenter.dataAttachment.filter { it.url.isEmpty() }.size > 0
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

}