package opsigo.com.datalayer

import org.junit.Test
import java.util.HashMap
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackTypeActivity
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.request_model.travel_request.EstimatedRequest
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel

class TravelRequestNetworkTest {

    @Test
    fun getActivityTypeTest(){
        val latch = CountDownLatch(1)
        val baseUrl = "https://opsicorp-per-dtm.azurewebsites.net"
        val token   = "Bearer tpygpvGKmd5_POTQSwy4DdBsEr_zeied42-d0nzgdbyClHFw6nq3FS-7B4yNpqJiufyhqVOYuKFGId8P6A_QPIGGYxvxlzIwV4cek1oWO4Z-zYwj7xa5HGzO8AbCdOPiY5jxiIk3MXB8adRlsU9lv_jgLBZx-rPqFdcQREGesGQAZA7LYzEpfYNBHrEEcMqmYgauO-Hg7irgT3zShwCkBla97U-kB3Um89qYw8s2g2IGie1w9EKd3wGfqvuw-4sw0djlZkWNkeJo4ermlb5ydJeXTcZhvaeT3EhX0135lmqvyk9bqDa9miAPG9euKdksNmr2c8r_1Ae2G6IdMqo5OiNgINHSXITTQuz63h8B_Ea1epq5iYPCr6hREWkWheldQsG5Jv7g0tLFBuKV3gasOn-6XOFj0pDLq4Xb8e3Q5j0eeHSyrwHVUi4wHd0V0UePXePxJO-bGXWkG7NitTxIk82hdEV06exnbw2tbBxAqZczMYvDVfrp_e5cGpqDxdXc-sw_ai8FOr3NGaN91OJaLLKxNEC6D5WJUZmndaIwq-VFySAhD0vCgYjVEj9NMtoSJPqy-T2AI9S8dtvO-7aKtQ"
        /*GetDataTravelRequest(baseUrl).getTypeActivity(token,object :CallbackTypeActivity{
            override fun success(data: ArrayList<TypeActivityTravelRequestModel>) {
                println(data.size)
                data.forEach {
                    println(it.purpose)
                }
                latch.await()
            }

            override fun failed(message: String) {
                latch.await()
            }
        })*/

        GetDataTravelRequest(baseUrl).getEstimatedCost(token,dataPurpose(),object :CallbackEstimatedCostTravelRequest{
            override fun successLoad(data: EstimatedCostTravelRequestModel) {
                println(data.estAllowance)
                println(Serializer.serialize(data))
                latch.await()
            }

            override fun failedLoad(message: String) {
                latch.await()
            }
        })

        try {
            latch.await()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun dataPurpose(): HashMap<Any, Any> {
        val dataRequest = "{\n" +
                "\t\"TripType\":\"Dinas non-residensial di luar tempat kedudukan\",\n" +
                "\t\"Purpose\":\"Koordinasi Eksternal\",\n" +
                "\t\"StartDate\":\"2021-05-19\",\n" +
                "\t\"EndDate\":\"2021-05-28\",\n" +
                "\t\"Golper\":2,\n" +
                "\t\"Routes\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"DepartureDateView\":\"19-05-2021\",\n" +
                "\t\t\t\"Origin\":\"Jakarta\",\n" +
                "\t\t\t\"Destination\":\"Surabaya\",\n" +
                "\t\t\t\"Transportation\":\"1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"DepartureDateView\":\"24-05-2021\",\n" +
                "\t\t\t\"Origin\":\"Surabaya\",\"\n" +
                "\t\t\tDestination\":\"Medan\",\n" +
                "\t\t\t\"Transportation\":\"2\"\n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"IsDomestic\":true,\n" +
                "\t\"WithPartner\":true\n" +
                "\t\n" +
                "}"
        val request = Serializer.deserialize(dataRequest,EstimatedRequest::class.java)
        return classToHashMap(request,EstimatedRequest::class.java)
    }

    fun classToHashMap(objects: Any, nameClass: Class<*>):HashMap<Any, Any>{
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