package com.opsigo.travelaja.module.item_custom.calendar

import android.app.Activity
import android.content.Intent
import com.opsigo.library.impl.OnDaySelectedListener
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.FormatingMonthIndonesian
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.calendar_activity_view.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewActivity : BaseActivity() {

    override fun getLayout(): Int { return R.layout.calendar_activity_view }

    var startDateSelected = ""
    var endDateSelected   = ""
    var typeSelected      = "" //singel

    override fun OnMain() {
//        typeSelected = intent.getStringExtra("type")
        setOnclickListener()
        setCalendarView()
    }

    private fun setOnclickListener() {
        btn_close.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            if(Globals.ONE_TRIP){
                if("-".equals(start_date.text.toString())){
                    showAllert(resources.getString(R.string.sorry_selected_singel_select_in_date_picker2),resources.getString(R.string.warning_selected_singel_select_in_date_picker2))
                }
                else {
                    if (!"-".equals(end_date.text.toString())){
                        showAllert(resources.getString(R.string.sorry_selected_singel_select_in_date_picker2),resources.getString(R.string.warning_selected_singel_select_in_date_picker2))
                    }
                    else {
                        callbackData()
                    }
                }
            }
            else{
                if("-".equals(start_date.text.toString())||"-".equals(end_date.text.toString())){
                    showAllert(resources.getString(R.string.sorry_selected_singel_select_in_date_picker),resources.getString(R.string.warning_selected_singel_select_in_date_picker))
                }
                else {
                    callbackData()
                }
            }

        }

        start_date.text = "-"
        end_date.text   = "-"
    }

    private fun callbackData() {

        if (intent.getStringExtra("endDate")!=null){
           if (SimpleDateFormat(intent.getStringExtra("formatDate")).parse(intent.getStringExtra("endDate")).before(SimpleDateFormat("yyyy-MM-dd").parse(endDateSelected))){
               showAllert(resources.getString(R.string.sorry_selected_singel_select_in_date_picker),resources.getString(R.string.warning_selected_next_date))
           }
           else{
               finishListener()
           }
        }
        else{
            finishListener()
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

    fun setStartDateCalendar(formatInput:String,dateStringInput: String){
        calendarView.setStartDay(SimpleDateFormat(formatInput).parse(dateStringInput))
    }

    fun setEndateCalendar(formatInput:String,dateStringInput: String){
        end_date.text = DateConverter().getDate(dateStringInput,formatInput,"EEE , dd MMM yyyy")
        endDateSelected  = if (endDateSelected.split(" ").isEmpty()) endDateSelected else endDateSelected.split(" ")[0]
        calendarView.setEndDay(SimpleDateFormat(formatInput).parse(dateStringInput))
    }


    private fun setCalendarView() {
        calendarView.setDateFormat("MMM yyyy")
        calendarView.setPreventPreviousDate(true)
        calendarView.setErrToastMessage("You can not select the previous date.")
        calendarView.setOnDaySelectedListener(object :OnDaySelectedListener{
            override fun onDaySelected(startDay: String, endDay: String) {
                val formatter = SimpleDateFormat("EEE, dd MMMM yyyy")
                val formatterOpsicorp = SimpleDateFormat("yyyy-MM-dd")
                var startDate = Date()

                if (startDay.isEmpty()||"".equals(startDay)){
                    start_date.text = "-"

                }else{
                    startDate = SimpleDateFormat("yyyy.MM.dd").parse(startDay)
                    start_date.text = FormatingMonthIndonesian().format(formatter.format(startDate))
                    startDateSelected = FormatingMonthIndonesian().format(formatterOpsicorp.format(startDate))
                }

                if (endDay.isEmpty()||"".equals(endDay)){
                    end_date.text   = "-"
                }
                else {
                    var endDate   = SimpleDateFormat("yyyy.MM.dd").parse(endDay)
                    end_date.text   = FormatingMonthIndonesian().format(formatter.format(endDate))
                    endDateSelected  = FormatingMonthIndonesian().format(formatterOpsicorp.format(endDate))
                }
            }
        })

        calendarView.buildCalendar()
        calendarView.getOnTouchListener()

        if (intent.getStringExtra("formatDate")!=null){
            setStartDateCalendar(intent.getStringExtra("formatDate"),intent.getStringExtra("startDate"))
        }

        if (intent.getStringExtra("endDate")!=null){
            setEndateCalendar(intent.getStringExtra("formatDate"),intent.getStringExtra("endDate"))
        }
    }
}
