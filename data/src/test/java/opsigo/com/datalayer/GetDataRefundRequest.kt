package opsigo.com.datalayer.datanetwork

import opsigo.com.datalayer.request_model.reschedule.*
import opsigo.com.domainlayer.callback.CallbackString
import java.util.concurrent.CountDownLatch
import org.koin.test.KoinTest
import java.lang.Exception
import java.util.HashMap
import org.junit.Test

class GetDataRefundRequest:KoinTest{

    val token = "Bearer 0VwG7rkbRtqalg9KKhKanNasiaA-SMD-gYsXGZGZyzjqEdXPYCmrVtRX5GI3uYUtjIeCq2JRNX9bG4MS42QodSJ7SR7nVyswEbWiMP76pLIB7oiQXev9vIvmCIUa-hfD8bt5gvtCTB3QgflPTUGQQidy1t1XIqoZ6fn4dR4_VoAVHq9Ip4serPK4bnZwBuG_497oh9h9TfjOlCvdOZx12K74lsVkvVH663o-B0Ba3iUXrjCn7PeyljKBq4ceCsAylX3cJbz2mM_RjQ2Sqhe7BIyKlcjKWJycqBemOx9R7ifAPc19nY2s9glxXWFkVksZ8W2-9uD8ZQF-fTQ4ehlxnc3_qj0_6IGhbMkhU5e35mR6BuUJaRJ0_oixnhrivE8vBs8ALKaGelngNaIGv-6a8x6QLiIrm67YPpmo2rCdBlGAoUHMU5hOdH0thzrOi6gS7wuCzSO8u5DAYw0NNFn0VkYaqYntIMXfX-ONJkcQRlKU9hkkvrWCCIfrNOMPvAaO555RbF2B9LuMNCtkZH0_TB0I_UE1I0zJY0XepTpccnjF1N5V8vfVq9lB2aP5F61cZox6dy1RCBi90-kfudv4lLe9IMdJycSGIB0v-5oIqJA"
    val url = "https://dtmqa.opsinfra.net"

    @Test
    fun testApiReschedule() {

        val latch = CountDownLatch(1)
        val item = 0
        var data = dataRescheduleRequestFlight()
        when(item) {
            0 -> {
                data = dataRescheduleRequestFlight()
            }
            1 -> {
                data = dataRescheduleRequestHotel()
            }
        }

        GetDataApproval(url).refund(token,data,object : CallbackString {
            override fun successLoad(data: String) {
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

    private fun dataRescheduleRequestHotel(): HashMap<Any, Any> {
        val data = RescheduleFlightRequest()
        data.tripCode       = ""
        data.participant    = participantRequest()
        data.reschedule     = rescheduleRequest()
        data.attachment     = attatchmentRequest()
        return classToHasMap(data,RescheduleFlightRequest::class.java)
    }

    private fun attatchmentRequest(): ArrayList<AttachmentItemReschedule> {
        val data = ArrayList<AttachmentItemReschedule>()
        data.add(AttachmentItemReschedule("",""))
        return data
    }

    private fun rescheduleRequest(): Reschedule? {
        val data = Reschedule()
        data.bookingCode = ""
        data.type        = 0
        data.changeDate  = ""
        data.changeTime  = ""
        data.changeNotes = ""
        return data
    }

    private fun participantRequest(): ParticipantReschedule? {
        val data  = ParticipantReschedule()
        data.id   = ""
        return data
    }

    private fun dataRescheduleRequestFlight(): HashMap<Any, Any> {
        val data = RescheduleHotelRequest()
        data.tripCode           = ""
        data.participant        = participantRequest()
        data.rescheduleHotel    = rescheduleHotelRequest()
        data.attachment         = attatchmentRequest()
        return classToHasMap(data,RescheduleFlightRequest::class.java)
    }

    private fun rescheduleHotelRequest(): RescheduleHotel {
        val data = RescheduleHotel()
        data.bookingCode    = ""
        data.type           = 0
        data.changeCheckin  = ""
        data.changeCheckout = ""
        data.changeNotes    = ""
        return data
    }


    @Test
    fun testApiRefund() {
        val latch = CountDownLatch(1)
        GetDataApproval(url).refund(token,refundRequest(),object : CallbackString {
            override fun successLoad(data: String) {
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

    private fun refundRequest(): HashMap<Any, Any> {
        val refund = RefundRequest()
        refund.tripCode        = ""
        refund.listPnr         = dataPnrrequest()
        refund.participant     = dataParticipantRequest()
        return classToHasMap(refund,RefundRequest::class.java)
    }

    private fun dataParticipantRequest(): Participant? {
        val data = Participant()
        data.id  =""
        return data
    }

    private fun dataPnrrequest(): ListPnr? {
        val data = ListPnr()
        data.flights = listFlight()
        data.hotels  = listHotel()
        data.listAttachments = dataAttacthment()
        return data
    }

    private fun dataAttacthment(): ArrayList<ListAttachmentsItem> {
        val data = ArrayList<ListAttachmentsItem>()
        data.add(ListAttachmentsItem("","",""))
        return data
    }

    private fun listHotel(): ArrayList<HotelsItem> {
        val data = ArrayList<HotelsItem>()
        data.add(HotelsItem(""))
        return data
    }

    private fun listFlight(): ArrayList<FlightsItem> {
        val data = ArrayList<FlightsItem>()
        data.add(FlightsItem(""))
        return data
    }

    fun classToHasMap(objects: Any, nameClass: Class<*>):HashMap<Any, Any>{
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