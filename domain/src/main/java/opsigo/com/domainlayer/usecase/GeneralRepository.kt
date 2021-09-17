package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*



interface GeneralRepository {
    fun getDataUpcomingFlight(token:String,callbackUpcomingFlight: CallbackUpcomingFlight)
//    fun getDataAirlinePreference(token:String,jobTitleId:String,companyCode:String,callbackAirlinePreference: CallbackAirlinePreference)
    fun setDeviceId(token: String, userName: String, deviceId: String, modelPhone: String, callbackSetDeviceId: CallbackSetDeviceId)
    fun setProfile(token: String, mobilePhone: String, nationality: String, callbackSetProfile: CallbackSetProfile)
    fun removeDeviceId(token: String, userName: String, deviceId: String, modelPhone: String, callbackSetDeviceId: CallbackSetDeviceId)
    fun getDataSummary(token: String, id: String, callbackSummary: CallbackSummary)
    fun getListTripplan(token: String, size: String, index: String, orderBy: String, direction: String, tripDateFrom: String, tripDateTo: String, callback: CallbackListTripplan)
    fun getListCart(token: String, size: String, index: String, orderBy: String, direction: String, callback: CallbackListCart)
    fun getCheckVersion(token: String,version:String,type:String,callbackString: CallbackCheckVersion)
    fun getHolidayApi(year:String,idCountry:String,callbackHoliday:CallbackHoliday)
    fun getCheckIdDevice(year:String,idDevice:String,callback:CallbackIdDevice)
    fun getDataEticket(token:String, id: String,idIten:String,typeItem:Int, callbackEticket: CallbackEticket)
}