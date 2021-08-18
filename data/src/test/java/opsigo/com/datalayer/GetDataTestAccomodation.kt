package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.request_model.accomodation.flight.cancel.CancelFlightRequest
import opsigo.com.datalayer.request_model.reservation.ReservationFlightRequest
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.accomodation.flight.search.ValidationRouteAvailable
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.AirlinePrefByCompanyRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.cancel.CancelHotelRequest
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem
import opsigo.com.domainlayer.model.accomodation.hotel.CountryHotel
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList
import org.koin.test.KoinTest
import org.junit.Test
import java.text.SimpleDateFormat
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
    val token = "Bearer zfwbC5ZI0jwFNUgUq8sAEXD4t_CFxqcbjXsTa-_YWw7qpEvzxYbnYFm6Y0zKHkoVfGkOXXqlTI3Lvw-wk2B0tRIk5ZoVM1SuHa0v8ePTNxSGY4M7yXokj9N41uSMDWiKv0TybUlhvIr8XhNmykL8Qm2SpX9w07r0hfkCTGrGQ-VQyInScGgfLTwyqu9KhVz77pXcf_H1NqX23Y4THJPl8UyoWB0MBZZA2aTtJ_edKFb7fBpegYIEPNzsK16ZSbPDemsxlFC6u7_0685CXEQX7xfaceqKflYcUF2ACS3RKdYn-_MsXvORTIu30pxXubDXqzYd208lyJpWZ7JJ3UUUR-ED7NlPHNcll023QOmGln0QZgHHGajJrR5dLLcyJCQCh08vxP7rqo7eMzZM-1CXkjKSORBacWNVhjBuKDRf0ooUfzbwvUz40zI1mWJyHBVVwAhr6If5Rl6yRSuH-IV5vu2GSzDHoGaENh3QpN9shVjgeTcTd5z7_MHVIzLV-2MOAEAFIhhnALzdxvpOHaM9TbRn1pAp_dIzhqEGSfLeXWh7nnv1SE38L74GiU9bCdINfBkuLFqOlhyAsrGHtFnC6Q"

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

    @Test
    fun testGetListMyBookingTest(){
        val latch       = CountDownLatch(1)
            GetDataMyBooking(baseUrl).getListMyBooking(token,10,1,"0,1,2",firstDateQuery().first,firstDateQuery().second,object :CallbackListMyBooking{
                override fun success(data: ArrayList<MyBookingModel>) {
                    println(Serializer.serialize(data))
                    latch.await()
                }

                override fun failed(message: String) {
                    print(message)
                    latch.await()
                }
            })
        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun firstDateQuery():Pair<String,String>{
        val referenceDate = Date()
        val c: Calendar = Calendar.getInstance()
        c.setTime(referenceDate)
        c.add(Calendar.MONTH, -3)

        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy-MM-dd")
        val toDay  = mdformat.format(calendar.getTime())

        return Pair(SimpleDateFormat("yyyy-MM-dd").format(c.getTime()),toDay)
    }

    @Test
    fun testGetDetailMyBookingTest(){
        val idTrain = "2b589018-c123-4b9a-8d29-43f7cd3931f3"
        val idHotel = "892048b8-224e-416f-86ce-1e74c9d5902f"
        val idFlight = "1e696e4e-9a18-448f-88ef-afcea84a6c3f"
        val latch       = CountDownLatch(1)
        GetDataMyBooking(baseUrl).getDetailMyBooking(token,idTrain,object :CallbackDetailMyBooking{
            override fun success(data: DetailMyBookingModel) {
                println(Serializer.serialize(data))
                latch.await()
            }

            override fun failed(message: String) {
                println(message)
                latch.await()
            }
        })
        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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