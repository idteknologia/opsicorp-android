package com.opsigo.travelaja.module.profile

import android.app.DatePickerDialog
import android.os.Build
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.FormatingMonthIndonesian
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.id_cart_form_layout.*
import kotlinx.android.synthetic.main.passport_form_activity_view.*
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_mr
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_mrs
import kotlinx.android.synthetic.main.passport_form_activity_view.line_btn_ms
import kotlinx.android.synthetic.main.passport_form_activity_view.toolbar
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_mr
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_mrs
import kotlinx.android.synthetic.main.passport_form_activity_view.tv_btn_ms
import java.util.*
import kotlin.collections.ArrayList

class PassportFormActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.passport_form_activity_view
    }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()


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

        initToolbar()
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
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0){
            tv_btn_mr -> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            line_btn_mr-> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            tv_btn_mrs -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            line_btn_mrs -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            tv_btn_ms -> {
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            line_btn_ms -> {
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