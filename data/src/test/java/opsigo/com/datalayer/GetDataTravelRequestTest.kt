package opsigo.com.datalayer.datanetwork

import com.google.gson.Gson
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.RoutesItem
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftRequestPertamina
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsPertaminaItem
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import org.junit.Test
import org.koin.test.KoinTest
import java.lang.Exception
import java.util.HashMap
import java.util.concurrent.CountDownLatch

class GetDataTravelRequestTest:KoinTest{

    val token = "Bearer YvbDdyPky92Zi2NDyeI_U9V3D6Pm1IHxL0wgAjXxrhdVkQvx6kO28QWeONiOGH6p-Se9yHyEgdjnEtBWsXhw6fYN0K_KK8ZDOmbRRILoJxCT5BBdk_-rTcWcesMSNSCCrJ_5SArmsan-o5usmQ3EVxHU6k41MCPMV1cCx2rkfUiHf8s8-UPgI0bIors83SqNLY75GN0kroIYrKTxYQ3drM5hWX6XB3peK2MrW2jOjquvFP6Na1UwY6r42TwRiq25y0XkrvIYQuuuw1LtaQzjrjiOIWrnc6PFvSCCFIG3MzWEssiO2hU3zyOcCycv0BIo5WZJCSWWQCQjzWo6F-bRna-ONUonItNDynXPuE-F7cQr7TLbWQWKsYC5hPvuDthm_ERl8yqnDo1N4gT86x7MbMX3eukKdde_rMvQv0pegHfUHulL0Sz44lmOd-o7blJhNZGzx_DRoY36iEyyjpbdWvOBFcRrwRmAGDWLe6_2IzPpJ4rBLpzjVp2Dl8Vn2xTs5R7yNEeUCJ5ePvigyub15nKP9v_sVQqtlPtvL37DFhWGYEbWujUllxg4qQzt13A1"

    val url = "https://pertamina-dtm3-qa.opsicorp.com"

    @Test
    fun testApiResponse() {

        val latch = CountDownLatch(1)

          GetDataTravelRequest(url).submitTravelRequest(token,tripRequest(),object : CallbackSaveAsDraft {
              override fun successLoad(data: SuccessCreateTripPlaneModel) {
                  println("success")
                  print(data)
                  latch.await()
              }

              override fun failedLoad(message: String) {
                  println(message)
                  println("failed")
                  latch.await()
              }
          })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun tripRequest(): HashMap<String, Any> {
        val request  = SaveAsDraftRequestPertamina()
        request.travelAgentAccount = "apidev"
        request.purpose = "Koordinasi Internal"
        request.tripParticipants = tripParticipant()
        request.origin = "Jakarta"
        request.destination = "Surabaya"
        request.tripAttachments = tripAttachment()
        request.remark = "vadvar"
        request.golper = 2
        request.type = 1
        request.businessTripType = "Dinas Res Luar Kota"
        request.isDomestic = true
        request.routes = routes()
        request.returnDate = "2021-07-06"
        request.startDate = "2021-07-01"
        request.wbsNo = ""
        return classToHasMap(request,SaveAsDraftRequestPertamina::class.java)
    }

    private fun routes(): List<RoutesItem> {
        val data = "{\n" +
                "                \"DepartureDate\":\"2021-07-01\",\n" +
                "                \"DepartureDateView\":\"01-07-2021\",\n" +
                "                \"Destination\":\"Surabaya\",\n" +
                "                \"Origin\":\"Jakarta\",\n" +
                "                \"Transportation\":1\n" +
                "            }"
        val list = ArrayList<RoutesItem>()
        list.add(Serializer.deserialize(data,RoutesItem::class.java))
        return list
    }

    private fun tripAttachment(): List<TripAttachmentsItemRequest> {
        val data = "{\n" +
                "                \"Description\":\"1624404384.jpeg\",\n" +
                "                \"Url\":\"https:\\/\\/opsicorp.blob.core.windows.net\\/opsicorpperdtm-trip-attachment\\/20210623.503cb998-b6fd-490d-95b3-eae875cfe5fb.jpeg\"\n" +
                "            }"
        val list = ArrayList<TripAttachmentsItemRequest>()
        list.add(Serializer.deserialize(data,TripAttachmentsItemRequest::class.java))
        return list
    }

    private fun tripParticipant(): List<TripParticipantsPertaminaItem> {
        val string =  "{\n" +
                "                \"CashAdvance\":0,\n" +
                "                \"CostCenterCode\":\"A0000001\",\n" +
                "                \"EmployeeId\":\"d08ed87f-ffe7-4abc-9d39-1a5c7b0966b2\",\n" +
                "                \"EstAllowance\":800000,\n" +
                "                \"EstAllowanceEvent\":400000,\n" +
                "                \"EstFlight\":9415200,\n" +
                "                \"EstHotel\":12000000,\n" +
                "                \"EstLaundry\":1500000,\n" +
                "                \"EstTotal\":25265200,\n" +
                "                \"EstTransportation\":1150000,\n" +
                "                \"UseCashAdvance\":false,\n" +
                "                \"UseCostCenterOther\":false\n" +
                "            }"
        val list = ArrayList<TripParticipantsPertaminaItem>()
        list.add(Serializer.deserialize(string,TripParticipantsPertaminaItem::class.java))
        return list
    }

    fun classToHasMap(objects: Any, nameClass: Class<*>):HashMap<String, Any>{
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