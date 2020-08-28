package opsigo.com.datalayer.network

object MyURL {
    val URL_TRAVELAJA         = "https://travelaja.opsicorp.com/"
    const val LOGIN           = "token"
    const val PROFILE         = "api/User/Profile"
    const val UPDATE_PROFILE  = "api/User/UpdateProfile"
    const val CONFIG          = "api/Config"
    const val UPCOMING_FLIGHT = "api/User/FlightBooking?showAll=true"
    const val AIRLINE_PREF    = "api/Lookup/GetAirlineListBasedLogin"
    const val BUDGET          = "api/Lookup/Budgets"
    const val SUMMARY         = "api/TripPlan/Summary"
    const val LIST_APPROVE    = "api/TripPlan/List"
    const val LIST_CART       = "api/TripPlan/Cart"

    const val SET_DEVICE_ID    = "api/User/SetPlayerId"
    const val REMOVE_DEVICE_ID = "api/User/RemovePlayerId"

    //create tripplan
    const val PURPOSE          = "api/Lookup/TravelingPurpose"
    const val DESTINATION      = "api/Lookup/Airports"
    const val ATTACHMENT       = "api/UploadAttachment"
    const val SELECT_BUDGET    = "api/Lookup/Budgets"
    const val COST             = "api/Lookup/CostCenters"
    const val SAVE_DRAFT       = "api/TripPlan/SaveAsDraft"
    const val CREATE_TRIP_PLAN = "api/TripPlan/Submit"
    const val CITY             = "api/Lookup/Cities"
    const val CANCEL_TRIP_PLAN = "api/TripPlan/CancelTrip/{Id}"
    const val REMARKS          = "api/TripPlan/Remarks"
    //    const val CREATE_TRIP_PLAN = "api/TripPlan/CancelTrip/2ef3ae65-1797-46ca-b4c9-4e3e322c4f9d"

    //search flight
    const val SEARCH_FLIGHT       = "api/SearchFlight"
    const val AIRLINE_PREFERED    = "api/Lookup/GetCarriersBasedJobTitle"
    const val FLIGHT_VALIDATION   = "api/TripPlan/ValidationTrip"
    const val RESERVATION_AIRLINE = "api/TripPlan/ReservationFlight"
    const val SYNCRONIZE_FLIGHT   = "api/TripPlan/SynchronizeBooking"
    const val ALL_CODE_AIRLINE    = "api/Lookup/GetAllCarriers"
    const val GET_SEATMAP_FLIGHT  = "api/TripPlan/GetFlightSeat"
    const val GET_SSR_FLIGHT      = "api/TripPlan/GetSsrFlight"

    // search train
    const val STATION_TRAIN     = "api/Lookup/GetStationList"
    const val SEARCH_TRAIN      = "api/SearchTrain"
    const val TRAIN_VALIDATION  = "api/TripPlan/ValidationTrain"
    const val RESERVATION_TRAIN = "api/TripPlan/ReservationTrain"
    const val GET_SEATMAP_TRAIN = "api/TripPlan/GetTrainSeatMap"
    const val SET_SEATMAP_TRAIN = "api/TripPlan/SetTrainSeat"

    // search hotel
    const val SEARCH_CITI_HOTEL    = "apiv1/GetCities"
    const val SEARCH_COUNTRI_HOTEL = "apiv1/GetIsoCountries"
    const val SEARCH_HOTEL         = "apiv1/HotelAvailabilityFirst"
    const val PAGE_HOTEL           = "apiv1/PageHotelApi"
    const val VALIDATION_HOTEL     = "apiv1/ValidationHotel"
    const val CONFIRMATION_HOTEL   = "apiv1/ConfirmationHotel"
    const val CANCELLATION_POLICY  = "apiv1/GetAllCancellationPolicy"
    const val DETAIL_HOTEL         = "apiv1/HotelDetail"
    const val BOOKING_HOTEL        = "apiv1/BookHotel"
    const val SINCRONIZE_HOTEL     = "apiv1/SynchronizeHotel"
    const val REMOVE_HOTEL         = "apiv1/RemoveHotel"
    const val REMARK_HOTEL         = "apiv1/UpdateHotelRemarks"



//    const val COUNTRY              = "api/Lookup/Country"
    const val COUNTRY              = "api/MobileVersion/getcallcountry"
    const val TOTAL_APROVAL        = "api/TripPlan/GetTripPanStatus"

    const val APPROVE_ALL          = "api/TripPlan/ApprovalAll"
    const val APPROV_PER_PAX       = "api/TripPlan/ApprovalPerPax"
    const val APPROVE_PER_ITEM     = "api/TripPlan/ApprovalAct"

    const val CHECK_VERSION        = "api/MobileVersion/getversion"

    const val CALENDAR_HOLIDAY     = "api/MobileVersion/getHoliday"
    const val REASON_CODE          = "api/GetReasonCode"
    const val PROGRESS_TRAIN       = "api/TripPlan/ProgressBarTrain"
    const val PROGRESS_FLIGHT      = "api/TripPlan/ProgressBar"
    const val REMOVE_TRAIN         = "api/TripPlan/RemoveTrain"
    const val SYNC_TRAIN           = "api/TripPlan/SynchronizeTrainBooking"
    const val LIST_COMPANY         = "api/Lookup/CompanyLocationList"

    const val CHECK_VALID_ID_DEVICE = "api/User/IsDeviceValid"

    const val REGISTER_BY_EMAIL    = "api/Account/RegisterCorporate"
    const val CONFIRMATION_OTP     = "api/Account/ValidateToken"
    const val COMPLITELY_REGISTER  = "api/Account/ConfirmCorporate"
}
