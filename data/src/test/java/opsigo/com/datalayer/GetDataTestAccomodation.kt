package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.reservation.ReservationFlightRequest
import org.junit.Test
import org.koin.test.KoinTest
import java.util.*
import java.util.concurrent.CountDownLatch


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

    @Test
    fun testApiSearchFlight() {

       val latch = CountDownLatch(1)

        val token = "Bearer ZY59D_AD_78bbAYmP7LCYsGpx8bDhqJB4dkhukBawLUrV8VgfmhMBG-als0jGn5LG7SvnCvNjDc8IjMVmVnMgBMcFSsgI5QfsSSvryP3wcGlrMZruvD4CJB5Wp2-CedUNraaRjPOnMyMWCEDYM5mXEw5yJL92puaB-_4dx3tXCluZN5_0qdSlvCcVapRiQKWWAwcpjAY41EB6y70pSo9ao-MyxmRJtltm2wCaegnl-8-iJy0vUDl-055GzqSilv-7C5g_U0CNYDuUxg5XpMLHgxNniFt9gtDnQJYA0xHyHw4uXT3RVtZmLaCjwzs_G_T7mVk29ZMC4XLPu4NeVB45XT_tojzEj4vyB_0gcRcYQHJ0MGmdJmvB1OlK95zzyAEBdxDT724VwTiv3IZ2XsaO1EoDfA-sDfDBZW0xkA9UNQRFvhFst47oasra3IrSTeDKQt1AsVnAet08F4jsImfTrOT-hQ_VLpqBt_tRVLg8yELjeSdd9q2IQkrBfk91riEEqrw50PJLP5BnYSmAkDjAw"

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

    fun classToHasMap(objects: Any, nameClass:Class<*>):HashMap<String, Any>{
        val maps = HashMap<String, Any>()
        for (field in nameClass.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }
            maps.put(field.getName(), field.get(objects))
        }

        return maps
    }

}