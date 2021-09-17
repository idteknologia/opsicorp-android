package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.RoutesItem
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftRequestPertamina
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsPertaminaItem
import opsigo.com.domainlayer.callback.CallbackGetUrlFile
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import org.junit.Test
import org.koin.test.KoinTest
import java.lang.Exception
import java.util.HashMap
import java.util.concurrent.CountDownLatch

class GetDataTravelRequestTest:KoinTest{

    val token = "Bearer 0VwG7rkbRtqalg9KKhKanNasiaA-SMD-gYsXGZGZyzjqEdXPYCmrVtRX5GI3uYUtjIeCq2JRNX9bG4MS42QodSJ7SR7nVyswEbWiMP76pLIB7oiQXev9vIvmCIUa-hfD8bt5gvtCTB3QgflPTUGQQidy1t1XIqoZ6fn4dR4_VoAVHq9Ip4serPK4bnZwBuG_497oh9h9TfjOlCvdOZx12K74lsVkvVH663o-B0Ba3iUXrjCn7PeyljKBq4ceCsAylX3cJbz2mM_RjQ2Sqhe7BIyKlcjKWJycqBemOx9R7ifAPc19nY2s9glxXWFkVksZ8W2-9uD8ZQF-fTQ4ehlxnc3_qj0_6IGhbMkhU5e35mR6BuUJaRJ0_oixnhrivE8vBs8ALKaGelngNaIGv-6a8x6QLiIrm67YPpmo2rCdBlGAoUHMU5hOdH0thzrOi6gS7wuCzSO8u5DAYw0NNFn0VkYaqYntIMXfX-ONJkcQRlKU9hkkvrWCCIfrNOMPvAaO555RbF2B9LuMNCtkZH0_TB0I_UE1I0zJY0XepTpccnjF1N5V8vfVq9lB2aP5F61cZox6dy1RCBi90-kfudv4lLe9IMdJycSGIB0v-5oIqJA"

    val url = "https://dtmqa.opsinfra.net"

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

    @Test
    fun testApiDownloadFile() {

        val latch = CountDownLatch(1)
        GetDataTripPlane(url).getUrlFile(token,"819dc8eb-7b57-4a66-9c8d-b79c5a01e38c",object : CallbackGetUrlFile {
            override fun success(url: String) {
                println(url)
                println("success")
                latch.await()
            }

            override fun failed(string: String) {
                println(string)
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