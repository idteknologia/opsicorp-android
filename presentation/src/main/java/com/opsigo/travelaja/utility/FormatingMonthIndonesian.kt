package com.opsigo.travelaja.utility

class FormatingMonthIndonesian {

    fun format(string: String):String{

        var mData = ""

        if("Monday".equals(string.split(",")[0])){
            mData = "Senin, ${string.split(",")[1]}"
        }
        else if("Tuesday".equals(string.split(",")[0])){
            mData = "Selasa, ${string.split(",")[1]}"
        }
        else if("Wednesday".equals(string.split(",")[0])){
            mData = "Rabu, ${string.split(",")[1]}"
        }
        else if("Thursday".equals(string.split(",")[0])){
            mData = "Kamis, ${string.split(",")[1]}"
        }
        else if("Friday".equals(string.split(",")[0])){
            mData = "Jumat, ${string.split(",")[1]}"
        }
        else if("Saturday".equals(string.split(",")[0])){
            mData = "Sabtu, ${string.split(",")[1]}"
        }
        else if("Sunday".equals(string.split(",")[0])){
            mData = "Minggu, ${string.split(",")[1]}"
        }
        else{
            mData = string
        }

        return mData

    }

    fun formatMonth(month:Int):String{
        var string = ""
        when(month){
            0 -> {
                string = "January"
            }
            1 -> {
                string = "February"
            }
            2 -> {
                string = "Maret"
            }
            3 -> {
                string = "April"
            }
            4 -> {
                string = "Mei"
            }
            5 -> {
                string = "Juni"
            }
            6 -> {
                string = "Juli"
            }
            7 -> {
                string = "Agustus"
            }
            8 -> {
                string = "September"
            }
            9 -> {
                string = "Oktober"
            }
            10 -> {
                string = "November"
            }
            11 -> {
                string = "Desember"
            }
        }
        return string
    }

}