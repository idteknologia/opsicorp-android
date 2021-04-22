package com.opsicorp.hotel_feature.adapter

interface OnclickRecyclerBookingContact {
    fun onClick(view:Int,position:Int)
    fun editText(view: Int,position: Int,string: String)
}