package com.opsigo.travelaja.utility

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateConverter {

    val errorFormating = "Error Formating"

    fun getDay():String{
        try {
            val calendar = Calendar.getInstance()
            val mdformat = SimpleDateFormat("dd/MM/yyyy")
            val strDate = "Current Date : " + mdformat.format(calendar.getTime())
            return strDate
        }catch (e:Exception){
            return errorFormating
        }

    }
    fun getDay(int: Int,formatDate:String):String{
        try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, int)
            val mdformat = SimpleDateFormat(formatDate)
            val strDate = "Current Date : " + mdformat.format(calendar.getTime())
            return strDate
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getDay(formatDate:String):String{
        try {
            val calendar = Calendar.getInstance()
            val mdformat = SimpleDateFormat(formatDate)
            val strDate = "Current Date : " + mdformat.format(calendar.getTime())
            return strDate
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getAfterDay(formatDate:String,numberAfter:Int):String{
        try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, numberAfter)
            val mdformat = SimpleDateFormat(formatDate)
            val strDate = mdformat.format(calendar.getTime())
            return strDate
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getNameDayFromDate(stringDate :String):String{
        try {
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            cal.setTime(sdf.parse(stringDate))
            val format = SimpleDateFormat("EEEE, yyyy-MM-dd",Locale.ENGLISH)
            return format.format(cal.getTime()).split(",")[0]
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getNameDayFromDate(stringDate :String,formatInput:String,formatOutput:String):String{
        try {
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat(formatInput)//"yyyy-MM-dd"
            cal.setTime(sdf.parse(stringDate))
            val format = SimpleDateFormat(formatOutput,Locale.ENGLISH)//"EEEE, yyyy-MM-dd"
            return format.format(cal.getTime()).split(",")[0]
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getDate(stringDate :String,formatInput:String,formatOutput:String):String{
        try {
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat(formatInput)//"yyyy-MM-dd"
            cal.setTime(sdf.parse(stringDate))
            val format = SimpleDateFormat(formatOutput,Locale.ENGLISH)//"EEEE, yyyy-MM-dd"
            return format.format(cal.getTime())
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun formatingDateDefault(format:String,stringDate: String):String{
        try {
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat(format) //"yyyy-MM-dd"
            cal.setTime(sdf.parse(stringDate))
            val format = SimpleDateFormat("EEEE, yyyy-MMMM-dd",Locale.ENGLISH)
            return format.format(cal.getTime()).split(",")[1].trim()
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun formatingDay(format:String,stringDate: String):String{
        try {

            var cal = Calendar.getInstance();
            var sdf = SimpleDateFormat(format) //"yyyy-MM-dd"
            cal.setTime(sdf.parse(stringDate))
            var format = SimpleDateFormat("EEE",Locale.ENGLISH)
            return format.format(cal.getTime())
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getDayFormatOpsicorp():String{
        try {
            val formatter = SimpleDateFormat("EEE, dd MMMM yyyy")
            val obDate = Date()

            return FormatingMonthIndonesian().format(formatter.format(obDate.getTime()))
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun getDayFormatOpsicorp2():String{
        try {

            val obDate = Date()
            val obDateFormat =  SimpleDateFormat("yyyy-MM-dd")
            return obDateFormat.format(obDate.getTime())
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun setDateFrormattingDetailFlight(string: String):String{
        try {
            if (string.split(" ").size==3){
                return string.split(" ")[1]+" "+string.split(" ")[2].substring(0,3)
            }
            else {
                return string.split(" ")[2]+" "+string.split(" ")[3].substring(0,3)
            }
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun setDateFormatNotif(date_resp: String): String {
//
//        val sDate = date_resp.substring(0, 10)
//        val output = SimpleDateFormat("EEE, dd MMM")
//        val formatter = SimpleDateFormat("yyyy-MM-dd")
//
//        var newFormat = ""
//
//        try {
//            val d = formatter.parse(sDate)
//            newFormat = output.format(d)
//
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        return newFormat
        return "test1"

    }

    fun setDateFormat3(date_resp: String): String {
        try {

            val sDate = date_resp.substring(0, 10)
            val output = SimpleDateFormat("EEE, dd MMM yyyy")
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            var newFormat = ""

            try {
                val d = formatter.parse(sDate)
                newFormat = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newFormat
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun setDateFormatDayEEEddMMM(date_resp: String): String {
        try {

            val sDate = date_resp.substring(0, 10)
            val output = SimpleDateFormat("EEE, dd MMM")
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            var newFormat = ""

            try {
                val d = formatter.parse(sDate)
                newFormat = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newFormat
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun setDateFormat4(date_resp: String): String {
        try {

            val sDate = date_resp.substring(0, 10)
            val output = SimpleDateFormat("dd MMM")
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            var newFormat = ""

            try {
                val d = formatter.parse(sDate)
                newFormat = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newFormat
        }catch (e:Exception){
            return errorFormating
        }
    }

    fun setDateFormatMonthYear(date_resp: String): String {
        try {

            val sDate = date_resp.substring(0, 10)
            val output = SimpleDateFormat("MMMM yyyy")
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            var newFormat = ""

            try {
                val d = formatter.parse(sDate)
                newFormat = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newFormat
        }catch (e:Exception){
            return errorFormating
        }
    }

}