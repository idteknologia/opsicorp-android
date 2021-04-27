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
        val token   = "Bearer EplXdIaWFeIoL-L5M9wU4wmP4dAbNVz4XvspgxCsulcsMP3nroa5vYiIZCPwch0d5GN9j20qGXYk3h04Jzn0_eet7Lpl7G4e6s46Aws9DQ16SKmUeRJ5dki3VkrXEboy1ei42mMio0h24nBa08muXn2MtrFxRJYhKFjITtXHTvrZrvA3DEkDA7R71J5CfTFfc7hUQv0UO55BjSSWD0_zTQ1KiE7f1oAEP_IDo616T9uS7Ul3uMWYU8d5gQYdIVX67n0T7tjVhmHZZigXqfPVb3Kk5qQ8xPDhT_Q-sP9oFxBHp_Gfzx1UUX5kGDN8lfmOlgX8kaXzwqD8Td3tDTlPHcsHtvJNr7Qts4i51CCzNgL986CFJDFbfr6vzy11UqUqeJ8VMSFp8zlOB_7uVuAy-sd_v1HOARGuAPlytaeW9GqUxKiApvJiDIv6Axr0nnVcqKCuSsZWy2TVIRtffsf8y_InVYieFbw6WRT8UWODaOdZbMYbwIhzUYJx11DK5_GcbcoqYzeRB53-bzOgZ5-fxNmDhXjr_94OFrukwKDgqCVGlFfCa-S2cDUSa3XF5iaWYjNs3G1C_4FJb-8jiV7NiQ"
        GetDataTravelRequest(baseUrl).getTypeActivity(token,object :CallbackTypeActivity{
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
        })

        /*GetDataTravelRequest(baseUrl).getEstimatedCost(token,dataPurpose(),object :CallbackEstimatedCostTravelRequest{
            override fun successLoad(data: EstimatedCostTravelRequestModel) {
                println(data.estAllowance)
                println(Serializer.serialize(data))
                latch.await()
            }

            override fun failedLoad(message: String) {
                latch.await()
            }
        })*/

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