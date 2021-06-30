package opsigo.com.datalayer

import org.junit.Test
import java.util.HashMap
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackTypeActivity
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.request_model.travel_request.CheckAvaibilityDateRequest
import opsigo.com.datalayer.request_model.travel_request.EstimatedRequest
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel

class TravelRequestNetworkTest {

    @Test
    fun getActivityTypeTest(){
        val latch = CountDownLatch(1)
        val baseUrl = "https://pertamina-dtm3-qa.opsicorp.com"
        val token   = "Bearer 6KCPiP_vTRzLVR2rjTx_KYnJroOxRKjTGH19YuPYIAqOLqIud7EE02KeoxLoALLnQiAIpWLm7DqIrEKKZCW2A1wWjZh0I1LCm-SHP0qEtM9QV9hyFVJEjCsN3VZid14UeoRW8yOx38oKKc3Q84-A1MVKj3dUgOnm-8UocMI-kBOmDvj_uNLxTK4Vug8vvtMf62Y4Flc3jnOojBz2CzzU1HSRpf9YXdPGFeTi9X_AX_fmU3CLTFIWBHJk68R4HLrNPIDv5YRbbfMTaQ2QYjHUeBiFYU3zBCitg9DoNSuZo7oDs8dJsDv25QO8PENCcenNVPEDh4v_eqKvsSC8tQm7w2uqhRuBGfUDl_fZQcFCVz2maxcHTZ-OMyMiOif47YuJSox3GJNpN7FGEez_6peBYT9P_pLTfLHOHNOxUN9XjkjzeSs9UC5DS6VTbZq7BBzn5NUEMH8k7KW87VkQ3fn0cBhiEBJgjz-PJxQQxqKTow1YkOLrWFIDsPv_jDTy_37Jsk_VSEr0N6H_cIH-A94VqbN757-zXMt5rJalv3l6sNJdOBtR4nIC4jmnWpPMRHVXiAON-VLEjLdj_RYq8VKXSA"

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

        GetDataTravelRequest(baseUrl).checkDateAvaibility(token,dataDate(),object :CallbackString{
            override fun successLoad(data: String) {
                println(data)
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

    private fun dataDate(): HashMap<Any, Any> {
        val data = CheckAvaibilityDateRequest()
        data.endDate = "2021-07-05"
        data.startDate = "2021-06-30"
        return classToHashMap(data,CheckAvaibilityDateRequest::class.java)
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