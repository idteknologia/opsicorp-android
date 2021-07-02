package com.mobile.travelaja.module.profile

import java.util.*
import android.os.Build
import android.view.View
import android.content.Intent
import com.mobile.travelaja.R
import android.widget.TextView
import android.widget.DatePicker
import android.widget.LinearLayout
import kotlin.collections.ArrayList
import android.app.DatePickerDialog
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.summary.PassportModel
import com.mobile.travelaja.utility.FormatingMonthIndonesian
import kotlinx.android.synthetic.main.passport_form_activity_view.*
import kotlinx.android.synthetic.main.passport_form_activity_view.toolbar
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_mr
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_ms
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_mrs
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_mr
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_ms
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_mrs

class PassportFormActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.passport_form_activity_view
    }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()
    var title = "Mr"
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
            if (intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.INPUT_EDIT_PASPORT,"").toString().isNotEmpty()){
                val dataString = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.INPUT_EDIT_PASPORT,"")
                val dataPasspor = Serializer.deserialize(dataString,PassportModel::class.java)

                et_passpor_number.setText(dataPasspor.passporNumber)
                et_mobile_phone.setText(dataPasspor.mobilePhone)
                tv_fullname_pasport.setText(dataPasspor.fullname)
                et_email.setText(dataPasspor.email)
                mappingtitle(dataPasspor.title)

                if (dataPasspor.birtDate.isNotEmpty()){
                     val bd = dataPasspor.birtDate.replace("00:00:00","").trim()
                     tv_year_birtdate.setText(bd.split("-")[0])
                     tv_month_birtdate.setText(DateConverter().getDate(dataPasspor.birtDate,"yyyy-MM-dd","MMM"))
                     tv_day_birtdate.setText(bd.split("-")[2])
                     month = DateConverter().getDate(dataPasspor.birtDate,"yyyy-MM-dd","M").toInt()
                }

                if (dataPasspor.expiredDate.isNotEmpty()){
                    tv_year_expired.setText(dataPasspor.expiredDate.split("-")[0])
                    tv_month_expired.setText(dataPasspor.expiredDate.split("-")[1])
                    tv_day_expired.setText(dataPasspor.expiredDate.split("-")[2])
                }
            }
        }catch (e:Exception){}
    }

    private fun mappingtitle(string: String) {
        title = string
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
            toolbar.doubleTitleGravity(toolbar.CENTER)
        }

        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
    }

    fun getDatePickerBirtday(view: View){
        val cal   = Calendar.getInstance()
        val year  = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day   = cal.get(Calendar.DAY_OF_MONTH)

        Globals.getCalendarSpinner(this,year,month,day,object :DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                tv_day_birtdate.text =  p3.toString()
                tv_month_birtdate.text = FormatingMonthIndonesian().formatMonth(p2)
                this@PassportFormActivity.month = p2
                tv_year_birtdate.text = p1.toString()
            }
        })
    }

    fun getDateExpiretDate(view: View){
        val cal   = Calendar.getInstance()
        val year  = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day   = cal.get(Calendar.DAY_OF_MONTH)

        Globals.getCalendarSpinner(this,year,month,day,object :DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                tv_day_expired.text =  p3.toString()
                tv_month_expired.text = FormatingMonthIndonesian().formatMonth(p2)
                tv_year_expired.text = p1.toString()
            }
        })
    }

    fun discardListener(view: View){
        finish()
    }

    fun saveListener(view: View){
        if (Globals.validatiEdittext(getDataField())){
            val pasport = PassportModel()
            pasport.passporNumber = et_passpor_number.text.toString()
            pasport.email         = et_email.text.toString()
            pasport.mobilePhone   = et_mobile_phone.text.toString()
            pasport.id            = ""
            pasport.title         = title
            pasport.fullname      = tv_fullname_pasport.text.toString()
            pasport.expiredDate   = "${tv_year_expired.text}-${tv_month_birtdate.text}-${tv_day_expired.text}"
            val birthdate         = "${tv_year_birtdate.text}-${month}-${tv_day_birtdate.text}"
            pasport.birtDate      = DateConverter().getDate(birthdate,"yyyy-MM-dd","yyyy-MM-dd")
            val intent = Intent()
            intent?.putExtra(Constants.RESULT_EDIT_PASPORT, Serializer.serialize(pasport))
            Globals.finishResultOk(this,intent)
        }
        else{
            showAllert(getString(R.string.sorry),getString(R.string.warning_canot_be_empty))
        }
    }

    private fun getDataField(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add(tv_fullname_pasport.text.toString())
        data.add(et_passpor_number.text.toString())
        data.add(et_email.text.toString())
        data.add(et_mobile_phone.text.toString())
        return data
    }

    override fun onClick(p0: View?) {
        when (p0){
            tv_btn_mr -> {
                title = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            line_btn_mr-> {
                title = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            tv_btn_mrs -> {
                title = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            line_btn_mrs -> {
                title = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            tv_btn_ms -> {
                title = "Ms"
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            line_btn_ms -> {
                title = "Ms"
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