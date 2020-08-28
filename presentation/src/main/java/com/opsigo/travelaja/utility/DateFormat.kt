package com.opsigo.travelaja.utility

class DateFormat {

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

}