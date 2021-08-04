package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.request_model.accomodation.flight.cancel.CancelFlightRequest
import opsigo.com.datalayer.request_model.reservation.ReservationFlightRequest
import opsigo.com.domainlayer.callback.CallbackArrayListString
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.accomodation.hotel.cancel.CancelHotelRequest
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
    val token = "Bearer cjkgvciCRrOl0cWK7A26IAmY0q_Yg1AKl5pmcY4yDg9fRK3E9CaVjVUBXcjGPgogBHeV323cDGJ92kgve0COiFyEpMFPAhuFP7h4LYkeQGdt7NEj9rebl7-ov6kf3tgELMT_51b-Duo1f8XF2QUpccPXJ0s4OfmaQeX_cndJFo94J1THtE1rMuHMcCtt7Seknvn6DD7gtM6TcZrLc0j12iGPj1YQwL-yYNZI90KWO-eN8SGQ87CkUlrjahrRQ4Uvr5-M9Azib3RFXEeemLxquj5n1F_jvHjOkS8RGKb9qADObQcVaqwVCcHm3HxKvswiUTLaPTCPEhNN0aFF0vNa-hrxFR1TV6QpazS9_8P8kt--GQDdHPO2eVgFqYfy8snk6Bh8TaHbKnfJyXj5ZuIDcPzl9zfSLYtwI2r_3LnThy8EImKthrup9WSUjr88Y4kTOfZ0niHkU3y8jXa1xphCfS6CzNZURiKkLS8ak8fPDUcqEkcOQz7VcAzftLiODPGigqCtii8PHBPSpCrpMBZTu3aYNi2FwlCnP0ycc4r2mk6RFZ-W4Gzu5-H4eCGpQNq5"


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