package com.opsigo.travelaja.module.item_custom.calendar

import android.app.Activity
import android.content.Intent

class NewCalendarViewOpsicorp {
    var REQUEST_CODE_CALENDAR = 76

    fun showCalendarView(activity: Activity, formatDate:String, startDate: String,endDate:String,minDate:String,maxDate:String,typeSelected:Int){
        val intent  = Intent(activity,NewCalendarViewActivity::class.java)
        intent.putExtra("formatDate",formatDate)
        intent.putExtra("startDate",startDate)
        intent.putExtra("endDate",endDate)
        intent.putExtra("minDate",minDate)
        intent.putExtra("maxDate",maxDate)
        intent.putExtra("typeSelected",typeSelected)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun showCalendarView(activity: Activity, formatDate:String, startDate: String,endDate:String,typeSelected: Int){
        val intent  = Intent(activity,NewCalendarViewActivity::class.java)
        intent.putExtra("formatDate",formatDate)
        intent.putExtra("startDate",startDate)
        intent.putExtra("endDate",endDate)
        intent.putExtra("typeSelected",typeSelected)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun showCalendarViewMinMax(activity: Activity,formatDate: String, minDate:String, maxDate: String,typeSelected: Int){
        val intent  = Intent(activity,NewCalendarViewActivity::class.java)
        intent.putExtra("formatDate",formatDate)
        intent.putExtra("minDate",minDate)
        intent.putExtra("maxDate",maxDate)
        intent.putExtra("typeSelected",typeSelected)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun showCalendarView(activity: Activity,typeSelected: Int){
        val intent  = Intent(activity,NewCalendarViewActivity::class.java)
        intent.putExtra("typeSelected",typeSelected)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun resultCalendarView(requestCode: Int, resultCode: Int, data: Intent?,callbackResult :CallbackResult){
        when(requestCode){
            REQUEST_CODE_CALENDAR -> {
                if (resultCode== Activity.RESULT_OK){
                    if (data!!.getStringExtra("startDate") != null) {
                        callbackResult.startDate(data.getStringExtra("displayStartDate"),data.getStringExtra("startDate"))
                    }
                    if (data.getStringExtra("endDate") != null) {
                        callbackResult.endDate(data.getStringExtra("displayEndDate"),data.getStringExtra("endDate"))
                    }
                }
                else {
                    callbackResult.canceledCalendar()
                }
            }
        }

    }

    interface CallbackResult{
        fun startDate(displayStartDate: String,startDate:String)
        fun endDate(displayEndDate: String,endDate:String)
        fun canceledCalendar()
    }

}