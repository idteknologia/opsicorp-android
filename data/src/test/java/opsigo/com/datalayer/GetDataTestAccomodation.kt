package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.request_model.accomodation.flight.cancel.CancelFlightRequest
import opsigo.com.datalayer.request_model.reservation.ReservationFlightRequest
import opsigo.com.domainlayer.callback.CallbackArrayListString
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.accomodation.flight.search.ValidationRouteAvailable
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.AirlinePrefByCompanyRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.cancel.CancelHotelRequest
import opsigo.com.domainlayer.callback.CallbackCountryByRoutePertamina
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem
import opsigo.com.domainlayer.model.accomodation.hotel.CountryHotel
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList
import org.koin.test.KoinTest
import org.junit.Test
import java.util.*


class GetDataTestAccomodation:KoinTest{

    var string = "{\n" +
            "  \"DataBooking\": {\n" +
            "    \"Destination\": \"SUB\",\n" +
            "    \"FlightTripType\": 0,\n" +
            "    \"FlightType\": \"NonGds\",\n" +
            "    \"isDownloadPnr\": false,\n" +
            "    \"isManual\": false,\n" +
            "    \"Members\": [\n" +
            "      \"4dc58ce3-6dd3-452e-a6e9-83ccbdd47ef6\"\n" +
            "    ],\n" +
            "    \"Origin\": \"CGK\",\n" +
            "    \"Segments\": [\n" +
            "      {\n" +
            "        \"Airline\": 11,\n" +
            "        \"AirlineImageUrl\": \"http://portalvhds11000v9mfhk0k.blob.core.windows.net/airline/KD-mail.png\",\n" +
            "        \"AirlineName\": \"Kalstar\",\n" +
            "        \"AirlineView\": \"Kalstar\",\n" +
            "        \"Amount\": 165000,\n" +
            "        \"ArriveDate\": \"2019-09-01\",\n" +
            "        \"ArriveTime\": \"13:00\",\n" +
            "        \"Category\": \"Economy\",\n" +
            "        \"ClassCode\": \"N\",\n" +
            "        \"ClassId\": \"N\",\n" +
            "        \"DepartDate\": \"2019-09-01\",\n" +
            "        \"DepartTime\": \"11:30\",\n" +
            "        \"Destination\": \"SUB\",\n" +
            "        \"DestinationView\": \"SURABAYA (SUB)\",\n" +
            "        \"Duration\": \"1h and 30m\",\n" +
            "        \"FlightId\": \"KD~ 830~ ~~CGK~01/09/2019 11:30~SUB~01/09/2019 13:00~\",\n" +
            "        \"FlightNumber\": \"KD 830\",\n" +
            "        \"Num\": 0,\n" +
            "        \"Origin\": \"CGK\",\n" +
            "        \"OriginView\": \"JAKARTA (CGK)\",\n" +
            "        \"Seq\": 0,\n" +
            "        \"Type\": \"DEPART\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"Header\": {\n" +
            "    \"Destination\": \"SUB\",\n" +
            "    \"Origin\": \"CGK\",\n" +
            "    \"Purpose\": \"Training\",\n" +
            "    \"ReturnDate\": \"2019-09-01\",\n" +
            "    \"StartDate\": \"2019-09-01\",\n" +
            "    \"TravelAgentAccount\": \"apidev\",\n" +
            "    \"TripParticipants\": [\n" +
            "      {\n" +
            "        \"BudgetId\": \"61399d52-d06f-4ff9-8407-7e574e3b62e9\",\n" +
            "        \"CostCenterId\": \"89d4c3bf-f99a-464f-93c4-3201c4b6d5f0\",\n" +
            "        \"EmployeeId\": \"4dc58ce3-6dd3-452e-a6e9-83ccbdd47ef6\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Type\": 2\n" +
            "  }\n" +
            "}"

    val baseUrl = "https://basicqa.opsicorp.com"
    val baseUrlPertamina = "https://pertamina-dtm3-qa.opsicorp.com"
    val token = "Bearer d5c7ElQIh3bmJjQQzEq0ZY4xTeZhKRZ3JezvR9IufwWmzSb4l6OrTPBdAXvS2JPoJNWChPARZFtdttQKyeJlHv_rNV2wJ2A_-HjYDPm4rJ1w3hVjP7V-CzKt1NyRJa0xH9Gp0am-zfVF-997ceri5SoSwKBvMmw8Immyk4YB2u8KHrOl_zfZ_uHgDQoFtitIrcTECYIoOtvVov_Xajo6kD_ioJtlp5lYvVoByB2wTK5VoDRt75LWvjeHuMxOF-LlY034vzwF0q2On1yQUKHyTOISRkIUWl_EiQzd00GWWDlzhv_QLExMSquB390wxMfCea6TTDyq1QCibmpap94vVGSzawEjZSmLXEG1ETcBmFBieTJCr-xukrexN951tn3ff0WzM7idoc0Akydz2GZl01m1okcoBoCN1g8DGHqWVuXeROun2mhc9I_nxHlDdejgIRLhbQq-bb9zLBnb3cuHouivmU4P_lNTRSchTWPlinSmFayVrZjt6MIuRc4ELVSNaPoOI5o-SZSkSkbhKOKY5AHOiEQJ-Jenf7VW3qQXvDCuRrUxfzxQpOdmG2zm1e3y"


    @Test
    fun testApiSearchFlight() {

       val latch = CountDownLatch(1)

        /*val data = FlightModelRequest()
        data.Origin = "CGK"
        data.Destination = "SUB"
        data.DepartDate = "2019-11-29"
        data.ReturnDate = ""
        data.Airline = "2"
        data.TravelAgentCode = "apidev"

        GetDataGeneral().apiOpsicorp.posDataSearchFlight(token,classToHasMap(data,FlightModelRequest::class.java)).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: PaymentResponse<ResponseBody>) {

            }
        })*/

        //gone
        /*GetDataGeneral().apiOpsicorp.posDataAirlineprefered(token,"7e03bd9f-0ef6-4a35-851e-5c15654f565e","000006").enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: PaymentResponse<ResponseBody>) {

            }
        })*/

        val model = Serializer.deserialize(string,ReservationFlightRequest::class.java)

        // gone
        /*GetDataGeneral().apiOpsicorp.posDataReservationFlight(token,classToHasMap(model,ReservationFlightRequest::class.java)).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: PaymentResponse<ResponseBody>) {

            }
        })*/

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    @Test
    fun testCancelFlight(){
        val latch = CountDownLatch(1)
        GetDataAccomodation(baseUrl).getRemoveFlight(token,dataRequestCancelFligt(),object :CallbackArrayListString{
            override fun successLoad(data: ArrayList<String>) {
                latch.await()
            }

            override fun failedLoad(message: String) {
                latch.await()
            }
        })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testCancelHotel(){
        val latch = CountDownLatch(1)
        GetDataAccomodation(baseUrl).getRemoveHotel(token,dataRequestCancelHotel(),object :CallbackArrayListString{
            override fun successLoad(data: ArrayList<String>) {
                latch.await()
            }

            override fun failedLoad(message: String) {
                latch.await()
            }
        })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dataRequestCancelHotel(): HashMap<Any, Any> {
        val data = CancelHotelRequest()
        data.hotelId = ""
        data.pnrId   = ""
        data.travelAgent = ""
        data.tripId      = ""
        data.tripItemId  = ""
        return classToHasMap(data,CancelHotelRequest::class.java)
    }

    private fun dataRequestCancelFligt(): HashMap<Any, Any> {
        val data = CancelFlightRequest()
        data.flightId = ""
        data.pnrId    = ""
        data.travelAgent = ""
        data.tripPlanId  = ""
        return classToHasMap(data,CancelFlightRequest::class.java)
    }

    @Test
    fun testGetDestinationHotelFromRoutes(){
        val travelAgent = "apidev"
        val idTrip      = "f1b1ab79-08dd-462d-a468-476c5bb27c4e"
        val latch       = CountDownLatch(1)
        GetDataAccomodation(baseUrlPertamina).getCountryByRoutePertamina(token,idTrip,travelAgent,object :CallbackCountryByRoutePertamina{
            override fun success(country: ArrayList<CountryHotel>) {
                println(country.first().countryName)
                latch.await()
            }

            override fun failed(message: String) {
                latch.await()
            }
        })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testGetRouteFlightAvailable(){
        val latch       = CountDownLatch(1)
        GetDataAccomodation(baseUrl).getRouteFlightAvailable(token,dataRoute(),object :CallbackString{
            override fun successLoad(string: String) {
                latch.await()
            }

            override fun failedLoad(message: String) {
                latch.await()
            }
        })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dataRoute(): HashMap<Any, Any> {
        val data = ValidationRouteAvailable()
        data.schedule?.addAll(mapperRoute())
        data.tripId = ""
        return classToHasMap(data,ValidationRouteAvailable::class.java)
    }

    private fun mapperRoute(): ArrayList<ListScheduleItem> {
        return ArrayList()
    }

    fun classToHasMap(objects: Any, nameClass:Class<*>):HashMap<Any, Any>{
        val maps = HashMap<Any, Any>()
        for (field in nameClass.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }
            maps.put(field.getName(), field.get(objects))
        }

        return maps
    }

}