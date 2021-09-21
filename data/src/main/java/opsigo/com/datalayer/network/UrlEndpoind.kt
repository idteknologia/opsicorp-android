package opsigo.com.data.network

import opsigo.com.datalayer.network.MyURL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import opsigo.com.domainlayer.callback.CallbackApprovAll
import retrofit2.http.POST

interface UrlEndpoind {

    @FormUrlEncoded
    @POST(MyURL.LOGIN)
    fun getDataLogin(@Field("grant_type")grant_type:String,
                     @Field("username")username:String,
                     @Field("password")password:String): Call<ResponseBody>

    @GET(MyURL.PROFILE)
    fun getDataProfile(@Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.CONFIG)
    fun getDataConfig(@Header("Authorization")token:String): Call<ResponseBody>

    @GET
    fun getDataConfig(@Url url:String, @Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.PURPOSE)
    fun getDataPurpose(@Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.UPCOMING_FLIGHT)
    fun getDataUpcomingFlight(@Header("Authorization")token:String): Call<ResponseBody>

//    last
//    @GET(MyURL.CHECK_VERSION)
//    fun checkVersion(@Header("Authorization")token:String,
//                     @Query("version")version:String,
//                     @Query("type")type:String): Call<ResponseBody>


    @GET(MyURL.CHECK_VERSION)
    fun checkVersion(@Query("code")version:String,
                     @Query("type")type:String): Call<ResponseBody>

    @GET(MyURL.BUDGET)
    fun getDataBudget(@Header("Authorization")token:String,
                      @Query("EmployeeId") EmployeeId: String,
                      @Query("TravelAgentCode") TravelAgentCode: String): Call<ResponseBody>

//    @PUT(MyURL.SET_DEVICE_ID)
//    fun setDeviceId(@Header("Authorization")token:String,
//                      @Query("Username") Username: String,
//                      @Query("DeviceId") DeviceId: String,
//                      @Query("ModelPhone") ModelPhone: String): Call<ResponseBody>

    @FormUrlEncoded
    @PUT(MyURL.SET_DEVICE_ID)
    fun setDeviceId(@Header("Authorization") token:String,
                    @Field("Username") username:String,
                    @Field("DeviceId") deviceId:String,
                    @Field("ModelPhone") modelPhone:String): Call<ResponseBody>

    @FormUrlEncoded
    @PUT(MyURL.UPDATE_PROFILE)
    fun setProfile(@Header("Authorization") token:String,
                    @Field("MobilePhone") mobilePhone:String,
                    @Field("Nationality") nationality:String): Call<ResponseBody>

    @FormUrlEncoded
    @PUT(MyURL.REMOVE_DEVICE_ID)
    fun removeDeviceId(@Header("Authorization") token:String,
                    @Field("Username") username:String,
                    @Field("DeviceId") deviceId:String,
                    @Field("ModelPhone") modelPhone:String): Call<ResponseBody>

    @GET(MyURL.SUMMARY)
    fun getDataSummary(@Header("Authorization")token:String,
                       @Query("id") id: String): Call<ResponseBody>


    @GET(MyURL.LIST_APPROVE)
    fun getListTripplan(@Header("Authorization")token:String,
                        @Query("Size")      Size: String,
                        @Query("Index")     Index: String,
                        @Query("OrderBy")   OrderBy: String,
                        @Query("Direction") Direction: String,
                        @Query("CreatedDateFrom")dateFrom:String,
                        @Query("CreatedDateTo")TripDateTo:String)
            : Call<ResponseBody>


    @GET(MyURL.LIST_CART)
    fun getListCart(@Header("Authorization")token:String,
                        @Query("Size")      Size: String,
                        @Query("Index")     Index: String,
                        @Query("OrderBy")   OrderBy: String,
                        @Query("Direction") Direction: String)
            : Call<ResponseBody>


    @GET(MyURL.AIRPORT)
    fun getDataAirport(@Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.CITY)
    fun getCity(@Header("Authorization")token:String): Call<ResponseBody>

    @Multipart
    @POST(MyURL.ATTACHMENT)
    fun posDataAttachment(@Header("Authorization")token:String
                          ,@Part file: MultipartBody.Part?): Call<ResponseBody>



    @GET(MyURL.SELECT_BUDGET)
    fun posDataSelectBudget(@Header("Authorization")token:String,
                            @Query("EmployeeId")EmployeeId:String,
                            @Query("TravelAgentCode")TravelAgentCode:String): Call<ResponseBody>


    @GET(MyURL.COST)
    fun posDataSelectCost(@Header("Authorization")token:String,
                            @Query("budgetCode")budgetCode:String,
                            @Query("employeeId")employeeId:String): Call<ResponseBody>

    @POST(MyURL.SAVE_DRAFT)
    fun posDataSaveDraft(@Header("Authorization")token:String,
                         @Body body: HashMap<String,Any>): Call<ResponseBody>

    @POST(MyURL.SAVE_DRAFT_PERSONAL)
    fun posDataSaveDraftPersonal(@Header("Authorization")token:String,
                         @Body body: HashMap<String,Any>): Call<ResponseBody>

    @GET(MyURL.CHECK_EXIST_TRIP_PERSONAL)
    fun checkExistTripPersonal(@Header("Authorization")token:String): Call<ResponseBody>

    @POST(MyURL.CREATE_TRIP_PLAN)
    fun submitTrip(@Header("Authorization")token:String,
                                @Body body: HashMap<String,Any>): Call<ResponseBody>

    @POST(MyURL.CANCEL_TRIP_PLAN)
    fun postCancelTrip(@Header("Authorization")token:String, @Path("Id")id:String): Call<ResponseBody>

    @GET(MyURL.GET_PAYMENT_LINK)
    fun getPaymentLink(@Header("Authorization")token: String,
                       @Query("id")id: String) : Call<ResponseBody>


    // flight
//    @POST(MyURL.SEARCH_FLIGHT)
//    fun posDataSearchFlight(@Header("Authorization")token:String,
//                          @Body body: HashMap<String, Any>): Call<ResponseBody>

    @POST(MyURL.SEARCH_FLIGHT)
    fun getDataSearchFlight(@Header("Authorization")token:String,
                           @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.GET_SEATMAP_FLIGHT)
    fun getSeatMapFlight(@Header("Authorization")token:String,
                        @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.GET_SSR_FLIGHT)
    fun getSsrFlight(@Header("Authorization")token:String,
                         @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.GET_FARE_RULES)
    fun getFareRules(@Header("Authorization")token:String,
                     @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.RESERVATION_AIRLINE)
    fun reserveFlight(@Header("Authorization")token:String,
                         @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.SYNCRONIZE_FLIGHT)
    fun syncronizeFlight(@Header("Authorization")token:String,
                      @Body body: HashMap<Any, Any>): Call<ResponseBody>


    @POST(MyURL.AIRLINE_PREFERED)
    fun getAirlineprefered(@Header("Authorization")token:String,
                           @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @GET(MyURL.ALL_CODE_AIRLINE)
    fun getAllCodeAirline(@Header("Authorization")token:String): Call<ResponseBody>

    @POST(MyURL.FLIGHT_VALIDATION)
    fun getValidationFlight(@Header("Authorization")token:String,
                           @Body body: HashMap<Any, Any>): Call<ResponseBody>


    // train

    @GET(MyURL.STATION_TRAIN)
    fun getDataStationTrain(@Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.COUNTRY)
    fun getDataCountry(): Call<ResponseBody>
//    fun getDataCountry(@Header("Authorization")token:String): Call<ResponseBody>

    @GET(MyURL.TOTAL_APROVAL)
    fun getTotalApproval(@Header("Authorization")token:String,
                        @Query("Size")      Size: String,
                        @Query("Index")     Index: String,
                        @Query("OrderBy")   OrderBy: String,
                        @Query("Direction") Direction: String,
                         @Query("CreatedDateFrom")dateFrom:String,
                         @Query("CreatedDateTo")TripDateTo:String)
            : Call<ResponseBody>


    @POST(MyURL.APPROVE_ALL)
    fun approvAll(@Header("Authorization")token:String,
                         @Body body: HashMap<Any,Any>): Call<ResponseBody>

    @POST(MyURL.APPROV_PER_PAX)
    fun approvPerPax(@Header("Authorization")token:String,
                     @Body body: HashMap<Any,Any>): Call<ResponseBody>

    @POST(MyURL.APPROVE_PER_ITEM)
    fun approvePerItem(@Header("Authorization")token:String,
                       @Body body: HashMap<Any,Any>): Call<ResponseBody>

    @GET(MyURL.CALENDAR_HOLIDAY)
    fun getCalendarHoliday(@Query("year") year:String,
                           @Query("id") id:String):Call<ResponseBody>

    @GET(MyURL.REASON_CODE)
    fun getReasonCode(@Header("Authorization")token:String,
                      @Query("type")type: String):Call<ResponseBody>


    @POST(MyURL.SEARCH_TRAIN)
    fun getDataSearchTrain(@Header("Authorization")token:String,
                           @Body body: HashMap<Any, Any>): Call<ResponseBody>


    @POST(MyURL.TRAIN_VALIDATION)
    fun getValidationTrain(@Header("Authorization")token:String,
                           @Body body: HashMap<Any, Any>): Call<ResponseBody>


    @POST(MyURL.RESERVATION_TRAIN)
    fun reservationTrain(@Header("Authorization")token:String,
                         @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.GET_SEATMAP_TRAIN)
    fun getSeatMapTrain(@Header("Authorization")token:String,
                        @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @POST(MyURL.SET_SEATMAP_TRAIN)
    fun setSeatMapTrain(@Header("Authorization")token:String,
                        @Body body: HashMap<Any, Any>): Call<ResponseBody>

    @GET(MyURL.PROGRESS_TRAIN)
    fun getProgressTrain(@Header("Authorization") token:String,
                           @Query("trainId") trainId:String):Call<ResponseBody>

    @GET(MyURL.PROGRESS_FLIGHT)
    fun getProgressFlight(@Header("Authorization") token:String,
                         @Query("flightId") trainId:String):Call<ResponseBody>

    @POST(MyURL.REMOVE_TRAIN)
    fun getRemoveTrain(@Header("Authorization") token:String,
                       @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.REMOVE_FLIGHT)
    fun getRemoveFlight(@Header("Authorization") token:String,
                       @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.VALIDATION_CONFLICT)
    fun getRouteFlightAvailable(@Header("Authorization") token:String,
                                @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.SYNC_TRAIN)
    fun getSyncTrain(@Header("Authorization") token:String,
                     @Body body: HashMap<Any, Any>):Call<ResponseBody>



    @GET(MyURL.SEARCH_CITI_HOTEL)
    fun getSearchCityHotel(@Header("Authorization") token:String,
                           @Query("isoCountry")isoCountry:String,
                           @Query("travelAgent")travelAgent:String):Call<ResponseBody>

    @GET(MyURL.SEARCH_COUNTRI_HOTEL)
    fun getSearchCountry(@Header("Authorization") token:String,
                         @Query("travelAgent")travelAgent:String):Call<ResponseBody>

    @GET(MyURL.GET_COUNTRY_BY_ROUTE_PERTAMINA)
    fun getCountryByRoutePertamina(@Header("Authorization") token:String,
                                   @Query("tripId")tripId:String,
                                   @Query("travelAgent")travelAgent:String):Call<ResponseBody>

    @POST(MyURL.SEARCH_HOTEL)
    fun getSearchHotel(@Header("Authorization") token:String,
                       @Body data: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.PAGE_HOTEL)
    fun getPageHotel(@Header("Authorization") token:String,
                     @Body data: HashMap<Any, Any>):Call<ResponseBody>


    @POST(MyURL.VALIDATION_HOTEL)
    fun getValidationHotel(@Header("Authorization") token:String,
                           @Body body: HashMap<Any, Any>):Call<ResponseBody>


    @POST(MyURL.CONFIRMATION_HOTEL)
    fun getConfirmationHotel(@Header("Authorization") token:String,
                             @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.CANCELLATION_POLICY)
    fun getCacellationPolicyHotel(@Header("Authorization") token:String,
                                  @Body body: HashMap<Any, Any>):Call<ResponseBody>
    @POST(MyURL.DETAIL_HOTEL)
    fun getDetailHotel(@Header("Authorization") token:String,
                       @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.BOOKING_HOTEL)
    fun getBookingHotel(@Header("Authorization") token:String,
                        @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.SINCRONIZE_HOTEL)
    fun getSyncHotel(@Header("Authorization") token:String,
                     @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.REMOVE_HOTEL)
    fun getRemoveHotel(@Header("Authorization") token:String,
                       @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.REMARK_HOTEL)
    fun getUpdateRemarkHotel(@Header("Authorization") token:String,
                             @Body body: HashMap<Any, Any>):Call<ResponseBody>

    @GET(MyURL.LIST_COMPANY)
    fun getListCompany(@Header("Authorization") token:String,
                           @Query("onlyHaveLocation")isoCountry:Boolean):Call<ResponseBody>

    @GET(MyURL.CHECK_VALID_ID_DEVICE)
    fun getCheckDeviceId(@Header("Authorization") token:String,
                         @Query("deviceId")deviceId:String):Call<ResponseBody>

    @POST(MyURL.REGISTER_BY_EMAIL)
    fun getRegisterByEmail(@Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.CONFIRMATION_OTP)
    fun getValidationOtp(@Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.COMPLITELY_REGISTER)
    fun getCompletlyRegister(@Body body: HashMap<Any, Any>):Call<ResponseBody>

    @GET(MyURL.ACTIVITY_TYPE)
    fun getTypeActivity(@Header("Authorization") token:String,
                         @Query("deviceId")deviceId:String):Call<ResponseBody>

    @POST(MyURL.GET_ESTIMATED_COST)
    fun getEstimatedCost(@Header("Authorization")token:String,
                         @Body body: HashMap<Any,Any>): Call<ResponseBody>

    @POST(MyURL.SUBMIT_TRAVEL_REQUEST)
    fun submitTravelRequest(@Header("Authorization")token:String,
                            @Body body: HashMap<String, Any>):Call<ResponseBody>

    @POST(MyURL.APPROVE_ALL_TRIP)
    fun approveAllTravelRequest(@Body body: HashMap<Any, Any>):Call<ResponseBody>

    @POST(MyURL.ISSUED_ALL)
    fun issuedAllTravelRequest(@Header("Authorization") token: String,
                               @Body body: HashMap<Any, Any>) : Call<ResponseBody>

    @POST(MyURL.CHECK_AVAIBILITY_DATE)
    fun checkDateAvaibility(@Header("Authorization") token: String,
                               @Body body: HashMap<Any, Any>) : Call<ResponseBody>

    @POST(MyURL.CHECK_CASH_ADVANCE)
    fun checkCashAdvance(@Header("Authorization") token: String,
                            @Body body: HashMap<Any, Any>) : Call<ResponseBody>

    @GET(MyURL.GET_LIST_MY_BOOKING)
    fun getListMyBooking(@Header("Authorization") token:String,
                         @Query("Size")isoCountry:Int,
                         @Query("Index")index:Int,
                         @Query("ItemTypes")itemTypes:String,
                         @Query("dateFrom")dateFrom:String,
                         @Query("dateTo")dateTo:String):Call<ResponseBody>

    @GET(MyURL.DETAIL_MY_BOOKING)
    fun getDetailMyBooking(@Header("Authorization") token:String,
                         @Query("id")isoCountry:String):Call<ResponseBody>

    @GET(MyURL.CHANGE_TRIP)
    fun changeTrip(@Header("Authorization")token:String,
                          @Query("tripId")tripId:String): Call<ResponseBody>
    @GET(MyURL.GET_URL_FILE)
    fun getUrlFile(@Header("Authorization") token:String,
                           @Query("id")id:String):Call<ResponseBody>


    @POST(MyURL.API_REFUND)
    fun getRefund(@Header("Authorization") token: String,
                         @Body body: HashMap<Any, Any>) : Call<ResponseBody>

    @POST(MyURL.API_RESCHEDULE)
    fun getReschedule(@Header("Authorization") token: String,
                         @Body body: HashMap<Any, Any>) : Call<ResponseBody>
}
