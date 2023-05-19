package com.mobile.travelaja.module.create_trip.newtrip.view

interface CreateTripView {
    fun loadDataView()
    fun failedLoadDataView()
    fun successLoadDataView()
    fun setDataAutomatically(dataNow: String, dataNow1: String, city: String,idCity:String,mStartDate:String,mEndDate:String,origin: String)
//    fun setDataAutomatically2(dataNow: String, dataNow1: String,mStartDate:String,mEndDate:String)
    fun SuccessCreateTrip()
    fun failedCreareTrip()
}