package com.opsigo.travelaja.module.profile

import java.util.*
import android.os.Build
import android.view.View
import java.lang.Exception
import android.content.Intent
import com.opsigo.travelaja.R
import android.widget.TextView
import android.widget.DatePicker
import android.view.Gravity.CENTER
import android.widget.LinearLayout
import kotlin.collections.ArrayList
import android.app.DatePickerDialog
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import opsigo.com.domainlayer.model.booking_contact.SimModel
import com.opsigo.travelaja.utility.FormatingMonthIndonesian
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.*
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.toolbar
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.et_email
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_mr
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_ms
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_mrs
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_mr
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_ms
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_mrs
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.et_mobile_phone
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_day_birtdate
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_month_birtdate
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_year_birtdate

class SimFormContactActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int { return R.layout.sim_form_booking_contact_view }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()
    var titlePassenger = ""
    var month = 1

    override fun OnMain() {
        texts.add(tv_btn_mr)
        texts.add(tv_btn_mrs)
        texts.add(tv_btn_ms)
        lines.add(line_btn_mr)
        lines.add(line_btn_mrs)
        lines.add(line_btn_ms)

        tv_btn_mr.setOnClickListener(this)
        tv_btn_mrs.setOnClickListener(this)
        tv_btn_ms.setOnClickListener(this)
        line_btn_mr.setOnClickListener(this)
        line_btn_mrs.setOnClickListener(this)
        line_btn_ms.setOnClickListener(this)
        initDataIntent()
        initToolbar()
    }

    private fun initDataIntent() {
        try {
            if (intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_SIM,"").isNotEmpty()){
                val dataString = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_SIM,"")
                val dataSim    = Serializer.deserialize(dataString,SimModel::class.java)

                et_email.setText(dataSim.email)
                et_name_driver_license.setText(dataSim.name)
                et_mobile_phone.setText(dataSim.mobilePhone)
                et_number_driver_license.setText(dataSim.idSim)
                mappingtitle(dataSim.title)

                if (dataSim.birthDate.isNotEmpty()){
                     val bd = dataSim.birthDate.replace("00:00:00","").trim()
                     tv_year_birtdate.setText(bd.split("-")[0])
                     tv_month_birtdate.setText(DateConverter().getDate(dataSim.birthDate,"yyyy-MM-dd","MMM"))
                     tv_day_birtdate.setText(bd.split("-")[2])
                     month = DateConverter().getDate(dataSim.birthDate,"yyyy-MM-dd","M").toInt()
                }
            }
        }catch (e:Exception){}
        titlePassenger = "Mr"
    }

    private fun mappingtitle(string: String) {
        titlePassenger = string
        when(string){
            "Mr" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            "Mrs" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            "Ms" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
        }

    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.setDoubleTitle("Adult Passenger","Age 3 and older ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(CENTER)
        }
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
    }

    fun discardListener(view: View){
        finish()
    }

    fun saveListener(view: View){
        if (Globals.validatiEdittext(getFields())){
            val model = SimModel()
            model.idSim       = et_number_driver_license.text.toString()
            model.title       = titlePassenger
            model.name        = et_name_driver_license.text.toString()
            model.email       = et_email.text.toString()
            model.mobilePhone = et_mobile_phone.text.toString()
            val birthdate     = "${tv_year_birtdate.text}-${month}-${tv_month_birtdate.text}"
            model.birthDate   = DateConverter().getDate(birthdate,"yyyy-MM-dd","yyyy-MM-dd")

            val intent = Intent()
            intent.putExtra(Constants.RESULT_EDIT_SIM, Serializer.serialize(model))
            Globals.finishResultOk(this,intent)
        }
        else {
            showAllert(getString(R.string.sorry),getString(R.string.warning_canot_be_empty))
        }

    }

    private fun getFields(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add(et_name_driver_license.text.toString())
        data.add(et_number_driver_license.text.toString())
        data.add(et_email.text.toString())
        data.add(et_mobile_phone.text.toString())
        return data
    }

    fun getDatePickerBirtday(view: View){
        val cal   = Calendar.getInstance()
        val year  = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day   = cal.get(Calendar.DAY_OF_MONTH)

        Globals.getCalendarSpinner(this,year,month,day,object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                tv_day_birtdate.text =  p3.toString()
                tv_month_birtdate.text = FormatingMonthIndonesian().formatMonth(p2)
                tv_year_birtdate.text = p1.toString()
                this@SimFormContactActivity.month = p2
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0){
            tv_btn_mr -> {
                titlePassenger = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            line_btn_mr-> {
                titlePassenger = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            tv_btn_mrs -> {
                titlePassenger = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            line_btn_mrs -> {
                titlePassenger = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            tv_btn_ms -> {
                titlePassenger = "Ms"
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            line_btn_ms -> {
                titlePassenger = "Ms"
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
        }
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }


}