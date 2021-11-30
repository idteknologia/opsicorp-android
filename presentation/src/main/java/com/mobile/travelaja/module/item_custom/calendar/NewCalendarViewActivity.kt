package com.mobile.travelaja.module.item_custom.calendar

import android.app.Activity
import android.content.Intent
import com.opsicorp.sliderdatepicker.model.HolidayModel
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.new_calendar_view.*
import com.opsicorp.sliderdatepicker.utils.CallbackCalendar
import com.opsicorp.sliderdatepicker.utils.Constant
import com.mobile.travelaja.utility.Constants.LIST_DATA_HOLIDAY
import java.text.SimpleDateFormat

class NewCalendarViewActivity : BaseActivity(),CallbackCalendar {

    var startDateSelected = ""
    var endDateSelected   = ""
    val formatDate        = "dd-MM-yyyy"
    var typeSelected  = Constant.DOUBLE_SELECTED

    override fun getLayout(): Int {
        return R.layout.new_calendar_view
    }

    override fun OnMain() {
        initCalendar()

        btn_close.setOnClickListener {
            finish()
        }
    }

    private fun initCalendar() {
        calendar_view.callbackCalendarListener(this)

        if (intent.getBooleanExtra("beforday",false)){
            calendar_view.selectBeforeDay()
        } else {
            calendar_view.selectAfterDay()
        }

        if (intent.getStringExtra("startDate")!=null){
            calendar_view.setStartDateSelected(SimpleDateFormat(intent.getStringExtra("formatDate")).parse(intent.getStringExtra("startDate")))
        }
        if (!intent.getStringExtra("endDate").isNullOrEmpty()){
            calendar_view.setEndDateSelected(SimpleDateFormat(intent.getStringExtra("formatDate")).parse(intent.getStringExtra("endDate")))
        }

        println("-_-")

        if (intent.getStringExtra("minDate")!=null){
            calendar_view.setMinDate(SimpleDateFormat(intent.getStringExtra("formatDate")).parse(intent.getStringExtra("minDate")))
            println(intent.getStringExtra("minDate"))
        }

        if (intent.getStringExtra("maxDate")!=null){
            calendar_view.setMaxDate(SimpleDateFormat(intent.getStringExtra("formatDate")).parse(intent.getStringExtra("maxDate")))
            println(intent.getStringExtra("maxDate"))
        }

        typeSelected = intent.getIntExtra("typeSelected",Constant.DOUBLE_SELECTED)
        calendar_view.typeSelected(typeSelected)

        if (LIST_DATA_HOLIDAY.isNotEmpty()){
            val dataHoliday = ArrayList<HolidayModel>()
            LIST_DATA_HOLIDAY.forEach {
                val model = HolidayModel(SimpleDateFormat("dd").format(it.dateholiday)
                        ,it.dateholiday
                        ,it.nameholiday)
                dataHoliday.add(model)
            }
            calendar_view.setDataHoliday(dataHoliday)
        }

    }


    private fun finishListener() {
        val output = Intent()
        output.putExtra("startDate", startDateSelected)
        output.putExtra("endDate", endDateSelected)
        output.putExtra("displayStartDate",start_date.text.toString())
        output.putExtra("displayEndDate",end_date.text.toString())
        setResult(Activity.RESULT_OK, output)
        finish()
    }


    override fun startDate(string: String) {
        startDateSelected = DateConverter().getDate(string,formatDate,"yyyy-MM-dd")
        start_date.text   = DateConverter().getDate(string,formatDate,"dd MMM yyyy")

        if (typeSelected==Constant.SINGGLE_SELECTED){
            Globals.delay(1000,object :Globals.DelayCallback{
                override fun done() {
                    finishListener()
                }
            })
        }
    }

    override fun endDate(string: String) {
        endDateSelected   = DateConverter().getDate(string,formatDate,"yyyy-MM-dd")
        end_date.text     = DateConverter().getDate(string,formatDate,"dd MMM yyyy")

        Globals.delay(1000,object :Globals.DelayCallback{
            override fun done() {
                finishListener()
            }
        })

    }



}