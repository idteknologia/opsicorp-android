package com.mobile.travelaja.utility

import com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance.AccomodationPreferanceModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataMultiTripOrder
import opsigo.com.domainlayer.model.DestinationAccomodationModel
import opsigo.com.domainlayer.model.HolidayModel
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.domainlayer.model.accomodation.flight.CodeSearchAirLineModel
import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.signin.CountryModel


object Constants {
    val pertaminaUrl = "https://pertamina-dtm3-qa.opsicorp.com/"
    val KEY_IS_PARTICIPANT  = "isParticipant"
    val KEY_IS_APPROVAL     = "isApproval"
    val codeCompanyTravelAja = "000002"
    val positionFlightMulticity = "position flight multicity"
    var ALREADY_SEARCH_FLIGHT = false
    var multitrip           = false
    val IS_PERSONAL_TRIP    = "isPersonalTrip"
    val ID_PERSONAL_TRIP    = "personal trip"
    val RESULT_AREA_HOTEL   = "area hotel"
    val MIN_PRICE           = "min_price"
    val MAX_PRICE           = "max_price"
    val RESULT_FACILITY     = "facility"
    val RESULT_STAR         = "start"
    val KEY_DATA_AREA       = "items"
    val BASE_PACKAGE_TRAIN  = "com.opsicorp.train_feature."
    val BASE_PACKAGE_HOTEL  = "com.opsicorp.hotel_feature."
    val BASE_PACKAGE_FLIGHT = "com.opsicorp.travelaja.feature_flight."
    val IsLogin             = "login"
    val SHOWCASE_ID         = ""
    var CODE_SEARCH_AIRLINE = ArrayList<CodeSearchAirLineModel>()
    var DATA_SEAT_AIRLINE   = ArrayList<SeatAirlineModel>()
//    var DATA_FLIGHT_MULTI_CITY = DataMultiTripOrder()
    var DATA_SSR            = ArrayList<SsrModel>()
    val DATA_SEAT                = "data_seat"
    val KEY_DATA_CODE_AIRPORT    = "key_data_airport"
    val TYPE_PASSANGGER_ADULT    = "1"
    val TYPE_PASSANGGER_CHILD    = "2"
    val TYPE_PASSANGGER_INFANT   = "3"
    val OPEN_LOCATION            = "OPEN_MAP"
    val OPEN_MAP                 = "open_map"
    val GET_LOCATION_GPS_OR_GPRS = "get_gps"
    val KEY_BUNDLE               = "data"
    var TEST_CITY                = ""
    var dataValidationHotel      = ""
    var dataConfirmationHotel    = ""
    val COUNTRY_HOTEL            = "country_hotel"
    var DATA_ORDER_TRAIN         = ""
    var DATA_ORDER_HOTEL         = ""
    var DATA_TRAIN               = ""
    var DATA_HOTEL               = ""
    var DATA_ROOM_HOTEL          = ""
    var DATA_LIST_TRAIN          = ""
    var DATA_CREATE_TRIP         = ""
    var LIST_DETAIL_PASSANGER    = ""
    val FROM_CART                = "from_cart"
    val DATA_DETAIL_TRAIN        = "data_detail_cart"
    var FROM_SUCCESS_CHECKOUT    = true
    val GET_SEAT_MAP             = 709
    var tipeTrip                 = ""
    var TRIP_CODE                = ""
    var TRIP_PLAN_ID             = ""
    var TRIP_TYPE                = ""
    var EXPIRED_TIME             = ""
    val FROM_PAYMENT             = "fromPayment"
    val PROGRESS_TRAIN_CALLBACK  = 9099
    val PROGRESS_FLIGHT_CALLBACK = 9100
    val PROGRESS_FLIGHT_SAVED    = 9101
    val PROGRESS_HOTEL_CALLBACK  = 9088
    val ONCLICK_DETAIL_FLIGHT = 9102
    val ONCLICK_CHANGE_SEAT_MAP_TRAIN   = 9087
    val ONCLICK_DETAIL_TRAIN     = 9086
    val PROGRESS_TRAIN_SAVED     = 9085
    val ONCLIK_OPTION_REMOVE_TRAIN_CART = 9084
    val SELECT_NEARBY_OFFICE     = 9083
    val SELECT_NEARBY_CITY       = 9082
    val CODE_MAP_ACTIVITY        = 9081
    var READ_REQUEST_LOCATION    = 9080
    val ONCLICK_DETAIL_HOTEL     = 9079
    val KEY_ACTIVITY_BAGAGE      = 9078
    val KEY_ACTIVITY_SSR         = 9077
    val KEY_ACTIVITY_FREQUENCE   = 9076
    val KEY_CHECK_BOX_SSR        = 9075
    val KEY_BAGGAGE_ITEM_SELECTED = 9074
    val RC_SIGN_IN               = 9073
    val REVIEW_HOTEL_SELECT      = 9072
    val REQUEST_CODE_FARE_RULES   = 9071
    val REQUEST_CODE_SELECT_SSR  = 9070
    val REQUEST_CODE_SELECT_SEAT = 9069
    val REQUEST_CODE_DELETE_SSR  = 9068
    val REQUEST_CODE_DELETE_SEAT = 9067
    val REQUEST_CODE_DELETE_BAGGAGE  = 9066
    val KEY_DETAILS_CART         = 9065
    val REQUEST_CODE_ADD_MORE_ITEM = 9065
    val REQUEST_CODE_SELECT_FROM_MULTI = 9064
    val REQUEST_CODE_SELECT_TO_MULTI = 9063
    val REQUEST_CODE_SELECT_DEPARTURE = 9062
    val REQUEST_CODE_DELETE_MULTI = 9061
    val REQUEST_CODE_SWITCH_DATA = 9060
    val KEY_POSITION_FARE_RULES  = "keyFareRules"
    val KEY_POSITION_SELECT_SSR  = "keySelectSsr"
    val KEY_POSITION_SELECT_SEAT = "keySelectSeat"
    val KEY_POSITION_CART_FLIGHT = "keyCartFlight"
    val KEY_POSITION_SELECT_PASSENGER = "keySelectPassenger"
    val SELECT_NEARBY_COUNTRY    = 9071
    val SELECT_NEARBY_AIRPORT    = 9070
    val REQUEST_CODE_NEARBY      = 9069
    val ONCLICK_AREA_HOTEL       = 9068
    val REQUEST_CODE_HOTEL_AREA  = 9067
    val REQUEST_CODE_HOTEL_FILTER = 9066
    val SELECT_FLIGHT            = 9065
    val REQUEST_CODE_SELECT_FLIGHT = 9064
    var KEY_INTENT_SELECT_FLIGHT = "key_intent_result_ok_select_flight"
    val TYPE_SELECT_NEARBY       = "type_select_nearby"
    val DATA_DETAIL_HOTEL        = ""
    var DATA_DETAIL_FLIGHT       = ""
    val TYPE_ACCOMODATION        = "type_hotel"
    val KEY_NAME_GUEST           = "name"
    val DATA_CREATE_TRIP_PLAN    = "data_create_trip"
    val SELECT_REASON_CODE       = 17
    var DATA_REASON_CODE_HOTEL   = ArrayList<ReasonCodeModel>()
    var DATA_REASON_CODE_TRAIN   = ArrayList<ReasonCodeModel>()
    var DATA_REASON_CODE_FLIGHT  = ArrayList<ReasonCodeModel>()
    var FROM_HOME                = "from_home"
    var FROM_PERSONAL_TRIP       = "from_personal_trip"
    var FROM_BISNIS_TRIP         = "from_bisnis_trip"
    var ID_TRIP_PLANE            = "id_trip"
    var FROM_SUCCESS_CREATE      = "success_create_trip_plant"
    var FROM_DRAFT               = "draft"
    var FROM_LIST_DASBOARD       = "from_list"
    var LIST_DATA_HOLIDAY        = ArrayList<HolidayModel>()
    val BTN_SIM                  = 120
    val BTN_PASSPORT             = 110
    val BTN_ID_CART              = 213
    val ADULT                    = "adult"
    val CHILD                    = "child"
    val INFANT                   = "infant"
    val GET_FILTER               = 453
    val SELECT_RESULT            = "emplaoyId"
    var DATA_TRAIN_STASION       = ArrayList<DestinationAccomodationModel>()
    var DATA_NATIONAL            = ArrayList<CountryModel>()
    var DATA_CITY                = ArrayList<SelectNationalModel>()
    val AvailableSeat            = "Available"
    val SoldSeat                 = "SOLD"
    val PickSeat                 = "PICK"
    val SelectSeat               = "SELECT"
    val IMAGE_LOGO_SPLASH        = "cache_image_logo_splash"
    val IMAGE_BACKGROUND_SPLASH  = "cache_image_splash"
    val OPEN_DETAIL_TRIP_PLANE   = 67
    val BASE_URL                 = "BASE_URL"
    var key                      = ""
    var poition: Int             = 0
    var tripDateTo               = ""
    var tripDateFrom             = ""
    val KEY_TRIP_ITEM_TYPE       = "keytripitemtype"
    val KEY_INTENT_NOTIF_ID_INT  = "keyintentnotifint"
    val OPTION_FLIGHT_APPROVE    = 67

    val OPTION_TRAIN_APPROVE     = 56
    val OPTION_HOTEL_APPROVE     = 75
    val OPTION_FLIGHT_REJECT     = 68

    val OPTION_TRAIN_REJECT      = 59
    var DATA_FLIGHT_ARIVAL       = ArrayList<AccomodationResultModel>()
    var DATA_RESULT_FLIGHT_MULTI_CITY    = ArrayList<AccomodationResultModel>()
    val OPTION_HOTEL_REJECT      = 76
    var isBisnisTrip             = true

    //hotel
    val ONCLICK_INFO_CANCELATION_HOTEL = 8001

    var DATA_SUCCESS_CREATE_TRIP = ""

    var isParticipant            = false
    var isApproval               = false

    val KEY_TASK_DESC            = "key_task_desc"
    val NOTIFICATION_REASON      = "NotificationReason"
    val NOTIFICATION_CONFIRM_APPROVE = "ConfirmApprove"
    val KEY_INTENT_APPROVE_REJECT= "keyapprovereject"
    val KEY_INTENT_TRIPID        = "keyintenttripid"
    val KEY_INTENT_TRIP_CODE     = "keyintenttripcode"
    val KEY_INTENT_TRIP_PARTIC   = "keyintenttrippartic"
    val KEY_FROM                 = "keyfromlist"
    //val KEY_FROM_DRAFT = "keyfromdraft"
    //val KEY_FROM_LIST_DRAFT  = "keyfromlistdraft"
    val KEY_INTENT_BUILDER       = "keyintent_builder"
    val REQUEST_CODE_APPROVE     = 1000
    val REQUEST_CODE_REJECT      = 1500

    val DETAIL_PERTICIPANT_INTENT = 1892

    val JOB_TITLE                = "jobTitle"
    val NAME_PARTICIPANT         = "nameParticipant"
    val COSTCENTER               = "costcenter"
    val BUDGET                   = "budget"

    val CHANNNEL_ID_X            = "opsicorpx"
    val CHANNEL_NAME_X           = "opsicorpx"
    val CHANNEL_DESC             = "desc"

    val FCM_TOKEN                = "FCM_TOKEN"
    val NOTIFICATION_REPLY       = "NotificationReply"

    val KEY_INTENT_MORE          = "keyintentmore"
    val KEY_INTENT_REJECT        = "keyintenthelp"

    val REQUEST_CODE_MORE_X      = 1000
    val REQUEST_CODE_HELP_X      = 1400
    val APPROVE_IT               = 1
    val REJECT_IT                = 2

    var Type                     = "Type"

    var DEPART                   = "DEPART"
    var RETURN                   = "RETURN"

    var ACT_DETAIL_TP = "ACT_DETAIL_TP"
    var ACT_DETAIL_PART = "ACT_DETAIL_PART"
    var ACT_CART = "ACT_CART"

    var Purpose                  = "Purpose"
    var StartDate                = "StartDate"
    var EndDate                  = "EndDate"
    var ReturnDate               = "ReturnDate"
    var TravelAgentAccount       = "TravelAgentAccount"
    var Origin                   = "Origin"
    var Destination              = "Destination"
    var TripParticipants         = "TripParticipants"
    var EmployeeId               = "EmployeeId"
    var BudgetId                 = "BudgetId"
    var CostCenterId             = "CostCenterId"
    var DataBooking              = "DataBooking"
    var FlightType               = "FlightType"
    var Members                  = "Members"

    var OutgoingTrain            = "OutgoingTrain"
    var IncomingTrain            = "IncomingTrain"


    var Segments                 = "Segments"
    var ClassId                  = "ClassId"
    var DepartDate               = "DepartDate"
    var ArriveDate               = "ArriveDate"
    var DepartTime               = "DepartTime"
    var ArriveTime               = "ArriveTime"
    var ClassCode                = "ClassCode"
    var FlightId                 = "FlightId"
    var Airline                  = "Airline"
    var AirlineView              = "AirlineView"
    var FlightNumber             = "FlightNumber"
    var AirlineImageUrl          = "AirlineImageUrl"
    var AirlineName              = "AirlineName"
    var Category                 = "Category"
    var Num                      = "Num"
    var Seq                      = "Seq" // utk connecting
    var OriginView               = "OriginView"
    var DestinationView          = "DestinationView"
    var Duration                 = "Duration"
    var Amount                   = "Amount"
    var AmountS                  = "AmountS"

    var IS_RETURN                = "IS_RETURN"
    //public static String IS_FROM_SELECT = "IS_FROM_SELECT";

    val IS_APPROVAL              = "isApproval"
    var LastStep                 = "LastStep"
    var Reserve                  = "Reserve"
    var TripSummary              = "TripSummary"
    var Summary                  = "Summary"
    var Number                   = "Number"
    var ID_PARTICIPANT           = "iDParticipant"
    var EMPLOY_ID                = "employId"
    var STATUS_MEMBER            = "statusMember"
    var ParticipantID            = "ParticipantID"

    var TripplanCode             = "TripplanCode"
    var TripplanIDX              = "TripplanID"
    var JsonTripplan             = "JsonTripplan"
    var CalStart                 = "CalStart"
    var CalEnd                   = "CalEnd"

    var CorrelationId            = "CorrelationId"
    var HotelKey                 = "HotelKey"
    var HotelObj                 = "HotelObj"
    var SelectMode               = "SelectMode"
    var costCenterListStr        = "costCenterListStr"
    var reasonCodeListStr        = "reasonCodeListStr"
    var PurposeList              = "PurposeList"
    var DestinationList          = "DestinationList"
    var CountryList              = "CountryList"
    var BudgetList               = "BudgetList"
    var CostCenterList           = "CostCenterList"
    var ReasonCodeList           = "ReasonCodeList"
    var IS_APPROVER              = "IsApprover"
    var IS_PARTICIPANT           = "IsParticipant"
    var IS_DRAFT                 = "IsDraft"
    var IS_FROM_MANAGE_TRIP      = "IsFromManageTrip"

    var FILE_NAME_ALL_CODE_AIRPORT = "ai.op"
    var FILE_NAME_DATA_HOLIDAY     = "hl.op"
    var FILE_NAME_DATA_COUNTRY_HOTEL  = "ct_ht.op"
    var FILE_NAME_CITY_TRIP        = "ct_trip.op"
    var FILE_NAME_COUNTRY_TRIP     = "cntr_trip.op"
    var FILE_NAME_STATION_TRAIN    = "st_train.op"

    object SendTo {
        val Participant = 0
        val Bookers     = 1
        val Approvers   = 2
    }

    object TripType {
        val Airline = 0
        val Hotel   = 1
        val KAI     = 2
        val All     = 3
    }

    object typePassanger{
        val ADULT  = 1
        val CHILD  = 2
        val INFANT = 3
    }

    object ApprovalAction {
        val Approve = 1
        val Reject  = 0
    }

    object NotifType {
        val ProgressPnr          = 1
        val FinishFollowUpPerPax = 2
        val SubmitTripPan        = 3
        val Approval             = 4
        val Info = 5
        val PaymentCompleted = 6
    }

    object StatusTrip {
        val Draft              = 0
        val WaitingForApproval = 1
        val PartiallyApproved  = 2
        val PartiallyRejected  = 3
        val CompletelyApproved = 4
        val CompletelyRejected = 5
        val Canceled           = 6
        val Expired            = 7
        val PartiallyApprovedAndReject = 8
        val TripCompleted      = 9
    }

    val TYPE_TRAIN   = 1
    val TYPE_FLIGHT  = 2
    val TYPE_BUS     = 3
    val TYPE_HOTEL   = 4
    val ADD_ITEM_PERSONAL_TRIP = 9
    val FLIGHT       = "Flight"
    val TRAIN        = "Train"
    val TYPE_LOADING = 5
    val TYPE_TOUR    = 6
    val KEY_ACCOMODATION       = 0
    val TYPE_HEADER_NOT_COMPLY = 6
    val TYPE_HEADER_SOLD_OUT   = 7
    val TYPE_LOADING_HOTEL     = 8
    var TYPE_FLIGHT_BUTTON = false


    var dataClassFlight      = ArrayList<AccomodationPreferanceModel>()

    var dataDepartureTime = ArrayList<AccomodationPreferanceModel>()
    var dataArrivalTime   = ArrayList<AccomodationPreferanceModel>()
    var dataNameTrainSelected     = ArrayList<AccomodationPreferanceModel>()
    var dataNameTrainAll  = ArrayList<AccomodationPreferanceModel>()
    var dataFilterMaxPriceAccomodation = 0
    var dataFIlterMinPriceAccomodation = 0

    val TYPE_HEADER = 6

    var TotalNight               = ""
    var Country                  = SelectNationalModel()
    var City                     = SelectNationalModel()
    var Company                  = SelectNationalModel()
    var typeDestination          = ""
    var CheckInDate              = ""
    var CheckOutDate             = ""
    var Latitude:Double?         = null
    var Longitude:Double?        = null

    object TypeSsr{
        var Unspecified = 0
        var Baggage = 1
        var SportEquipment = 2
        var Meal = 3
        var Product = 4
        var Beverage = 4
        var Seat = 5
        var Infant = 6
        var Drink = 7
        var WhellChair = 7
        var InFlightEntertainment = 8
        var ComfortKit = 9
        var Other = 99
    }

    object KeyBundle {
        var KEY_ID_COUNTRY       = "1"
        var KEY_DURATION         = "2"
        var KEY_ID_CITY          = "3"
        var KEY_DESTINATION      = "4"
        var KEY_LATITUDE         = "5"
        var KEY_LONGITUDE        = "6"
        var KEY_CHECKIN          = "7"
        var KEY_TOTAL_GUEST      = "13"
        var KEY_CHECKOUT         = "8"
        var KEY_NAME_CITY        = "9"
        var KEY_NAME_AIRPORT     = "10"
        var KEY_NAME_OFFICE      = "11"
        var KEY_NAME_COUNTRY     = "12"
    }

    val RESULT_EDIT_KTP     = "edit_ktp"
    val RESULT_EDIT_SIM     = "edit_sim"
    val RESULT_EDIT_PASPORT = "edit_pasport"
    val INPUT_EDIT_KTP     = "input_ktp"
    val INPUT_EDIT_SIM     = "input_sim"
    val INPUT_EDIT_PASPORT = "input_pasport"

    val TYPE_SIM      = "SIM"
    val TYPE_PASSPORT = "PASSPORT"
    val TYPE_KTP      = "KTP"


    val BISNIS_TRIP      = 1
    var PERSONAL_TRIP    = 2

    object TypeHeader{
        var bookers     = 1
        var personal    = 2
        var walkIn      = 3
    }

}
