package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*

interface AccomodationRepository {
    fun getDataStationTrain(token: String, callback:CallbackDestinationAccomodation)
    fun getSearchTrain(token: String,data:HashMap<Any,Any>,callback:CallbackResultSearchTrain)
    fun getReasonCodeTrain(token: String,typeAccomodation: String,callback: CallbackReasonCode)
    fun seatMapTrain(token:String,data:HashMap<Any,Any>,callback: CallbackSeatMap)
    fun getValidationTrain(token:String,data:HashMap<Any,Any>,callback: CallbackValidationTrain)
    fun getReservationTrain(token:String,data:HashMap<Any,Any>,callback: CallbackReservationTrain)
    fun setSeatMapTrain(token:String, data:HashMap<Any,Any>, callback: CallbackSetSeatMapTrain)
    fun getProgressTrain(token: String, idTrain:String, callback:CallbackProgressTrain)

    fun getSearchCountry(token: String,travelAgent:String,callback: CallbackArrayListCountry)
    fun getSearchCity(token: String,isoCountry:String,travelAgent:String,callback: CallbackListCityHotel)
    fun getSearchHotel(token: String,data:HashMap<Any,Any>,callback: CallbackSearchHotel)
    fun getSearchPageHotel(token: String,data:HashMap<Any,Any>,callback: CallbackUploadFile)
    fun getHotelDetail(token: String,data:HashMap<Any,Any>,callback: CallbackDetailHotel)
    fun getConfirmationHotel(token: String,data:HashMap<Any,Any>,callback: CallbackConfirmationHotel)
    fun getValidationHotel(token: String,data:HashMap<Any,Any>,callback: CallbackValidationHotel)
    fun getCancellationPolicyHotel(token: String,data:HashMap<Any,Any>,callback: CallbackRoomHotel)
    fun getBookingHotel(token: String,data:HashMap<Any,Any>,callback: CallbackBookingHotel)
    fun getSyncHotel(token: String,data:HashMap<Any,Any>,callback: CallbackArrayListString)
    fun getUpdateRemarkHotel(token: String,data:HashMap<Any,Any>,callback: CallbackUploadFile)
    fun getRemoveHotel(token: String,tripItemId:String,tripId:String,hotelId:String,pnrId:String,travelAgent:String,callback: CallbackUploadFile)
    fun getListCompanyHotel(token: String, notEmptyLatlong: Boolean, callback: CallbackListCompany)

    fun getSyncronizeFlight(token: String,data:HashMap<Any,Any>,callback: CallbackArrayListString)
    fun getValidationFlight(token:String,data:HashMap<Any,Any>,callback: CallbackValidationFlight)
    fun getReservationFlight(token:String,data:HashMap<Any,Any>,callback: CallbackReserveFlight)
    fun getProgressFlight(token: String, idFlight:String, callback:CallbackProgressFlight)
    fun getRemoveTrain(token: String, idTrain:HashMap<Any,Any>, callback:CallbackArrayListString)
    fun getSyncTrain(token: String, data:HashMap<Any,Any>, callback:CallbackArrayListString)
    fun getSearchFlight(token: String,data:HashMap<Any,Any>,callback:CallbackResultSearchFlight)
    fun getPreferedFlight(token: String,data:HashMap<Any,Any>,callback:CallbackAirlinePreference)
    fun getAllCodeFlight(token: String,callback:CallbackGetAllCodeAirline)
    fun getSeatMapFlight(token: String, data:HashMap<Any,Any>, callbackSeatMap: CallbackSeatMapFlight)
    fun getSsrFlight(token: String, data:HashMap<Any,Any>, callbackSeatMap: CallbackGetSsr)
    fun getFareRules(token: String, data: HashMap<Any, Any>, callback: CallbackGetFareRules)

}