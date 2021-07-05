package com.mobile.travelaja.module.item_custom.calendar

import android.app.Activity
import android.content.Intent

class CalendarViewOpsicorp {
    var REQUEST_CODE_CALENDAR = 76

    fun showCalendarView(activity: Activity, formatDate:String, startDate: String,endDate:String){
        val intent  = Intent(activity,CalendarViewActivity::class.java)
        intent.putExtra("formatDate",formatDate)
        intent.putExtra("startDate",startDate)
        intent.putExtra("endDate",endDate)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun showCalendarView(activity: Activity, formatDate:String, startDate: String){
        val intent  = Intent(activity,CalendarViewActivity::class.java)
        intent.putExtra("formatDate",formatDate)
        intent.putExtra("startDate",startDate)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun showCalendarView(activity: Activity){
        val intent  = Intent(activity,CalendarViewActivity::class.java)
        activity.startActivityForResult(intent,REQUEST_CODE_CALENDAR)
    }

    fun resultCalendarView(requestCode: Int, resultCode: Int, data: Intent?,callbackResult :CallbackResult){
        when(requestCode){
            REQUEST_CODE_CALENDAR -> {
                if (resultCode== Activity.RESULT_OK){
                    if (data!!.getStringExtra("startDate") != null) {
                        callbackResult.startDate(data?.getStringExtra("displayStartDate").toString(),data?.getStringExtra("startDate").toString())
                    }
                    if (data?.getStringExtra("endDate") != null) {
                        callbackResult.endDate(data?.getStringExtra("displayEndDate").toString(),data?.getStringExtra("endDate").toString())
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