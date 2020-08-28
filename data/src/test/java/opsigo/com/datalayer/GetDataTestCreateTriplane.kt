package opsigo.com.datalayer.datanetwork


import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.Test
import org.koin.test.KoinTest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import java.util.concurrent.CountDownLatch


class GetDataTestCreateTriplane:KoinTest{

    @Test
    fun testApiCreateTripPlane() {

        val url = "https://mobile.opsicorp.com/"
        val latch = CountDownLatch(1)

        //val token = "Bearer ZY59D_AD_78bbAYmP7LCYsGpx8bDhqJB4dkhukBawLUrV8VgfmhMBG-als0jGn5LG7SvnCvNjDc8IjMVmVnMgBMcFSsgI5QfsSSvryP3wcGlrMZruvD4CJB5Wp2-CedUNraaRjPOnMyMWCEDYM5mXEw5yJL92puaB-_4dx3tXCluZN5_0qdSlvCcVapRiQKWWAwcpjAY41EB6y70pSo9ao-MyxmRJtltm2wCaegnl-8-iJy0vUDl-055GzqSilv-7C5g_U0CNYDuUxg5XpMLHgxNniFt9gtDnQJYA0xHyHw4uXT3RVtZmLaCjwzs_G_T7mVk29ZMC4XLPu4NeVB45XT_tojzEj4vyB_0gcRcYQHJ0MGmdJmvB1OlK95zzyAEBdxDT724VwTiv3IZ2XsaO1EoDfA-sDfDBZW0xkA9UNQRFvhFst47oasra3IrSTeDKQt1AsVnAet08F4jsImfTrOT-hQ_VLpqBt_tRVLg8yELjeSdd9q2IQkrBfk91riEEqrw50PJLP5BnYSmAkDjAw"
        val token = "Bearer D_BdKaNRKBvaGKWLiJPtsHX1fkeKXN38gH8nLfC5w4eyU1XXWjnNjO5VDarnCYKGc_IQRzI50ChiXiV_jVSF6qwRKCKB8dLJEa2uhuwBBDpWMN8BqZQAV7pyMBDqu2EPqVpONHaEWKkyI4JVzVj9IqPLVicziJSh0EYlxws6Wki23ZMGx27WH-oOGvzPuiYCGoXst1G6UFZYlo5bxRmToTcMFL9Np-5u-5HeNF71sLuiUqFlC9WaC4n2W8NU3yKFdgshZ2AVo8AJy-4AcoX0_oc642k_jkfQigMLdCRqZHFZEs944DAOGaWCrXEQVvjZFOnWuadcOrb9qmHovVZrjg-ac9VJYP4gFOCHnjjN1jLrs1-PQAfHbSusBhVAI1xlZHqHuJ0yW2qBhJ_EJhg58VJi2KNTPE_tmIzQBH1X39LjpIjjkb0ofuVyXD773ilgbNk4QblvyC-8MPdo1yZQMWTyzKbuakZHjU8YYjC0TXwxw2lIcJXqB3K4WyW3bFkhKDiWOeIkTh4SZ5Y4AvcwhA"

        // done
//       GetDataGeneral(url).apiOpsicorp.getDataPurpose(token).enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                latch.await()
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                latch.await()
//            }
//        })

        GetDataTripPlane(url).apiOpsicorp.postCancelTrip(token,"zzaa").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                latch.await()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                latch.await()
            }
        })


        //done
        /*GetDataGeneral().apiOpsicorp.getDataDestinationAirline(token).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/

        /*GetDataGeneral().apiOpsicorp.getDataDestinationAirline(token).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/


        //test
        /*GetDataGeneral().apiOpsicorp.posDataAttachment(token,pathImageToMultipart("file","")).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/

        //done
       /* GetDataGeneral().apiOpsicorp.posDataSelectBudget(token,"a52fbb50-aeac-4cf1-9deb-312ddd059ba1","apidev").enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/

        //done
        /*GetDataGeneral().apiOpsicorp.posDataSelectCost(token,"BDGT01","a52fbb50-aeac-4cf1-9deb-312ddd059ba1").enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/

       /* var modelParamCreateTripPlane= "{\n" +
                "    \"Type\":2,\n" +
                "    \"Purpose\" : \"Breafing\",\n" +
                "    \"StartDate\" : \"2018-08-06\",\n" +
                "    \"ReturnDate\" : \"2018-08-08\",\n" +
                "    \"TravelAgentAccount\" : \"apidev\",\n" +
                "    \"Origin\" : \"CGK\",\n" +
                "    \"Destination\" : \"DPS\",\n" +
                "    \"Contact\": {\n" +
                "      \"FirstName\" :\"SIDIQ\",\n" +
                "      \"LastName\" : \"AKBAR\"\n" +
                "    },\n" +
                "    \"TripAttachments\" : [\n" +
                "     {\n" +
                "       \"Description\":\"holder_slide.png\",\n" +
                "       \"Url\":\"https://opsicorp.blob.core.windows.net/opsicorpdemo-trip-attachment/20191031.dde39dca-5ad7-400b-a2c6-c6c4fe59c1c7.png\"\n" +
                "     },\n" +
                "     {\n" +
                "       \"Description\":\"holder_slide.png\",\n" +
                "       \"Url\":\"https://opsicorp.blob.core.windows.net/opsicorpdemo-trip-attachment/20191031.dde39dca-5ad7-400b-a2c6-c6c4fe59c1c7.png\"\n" +
                "     }\n" +
                "     ],\n" +
                "    \"TripParticipants\" : [\n" +
                "      {\n" +
                "        \"EmployeeId\" : \"a52fbb50-aeac-4cf1-9deb-312ddd059ba1\",\n" +
                "        \"BudgetId\":\"cb2fa51e-efd1-4f66-86f2-d9171663b70d\",\n" +
                "        \"CostCenterId\":\"b4df3880-b1af-41af-89ce-a0d1b7eb5c3c\"\n" +
                "      }\n" +
                "    ]\n" +
                "}"

        val modelData = Serializer.deserialize(modelParamCreateTripPlane,SaveAsDraftRequest::class.java)


        //test
        GetDataGeneral().apiOpsicorp.posDataSaveDraft(token,classToHasMap(modelData,SaveAsDraftRequest::class.java)).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })*/

        //test
        /*GetDataGeneral().apiOpsicorp.posDataCreateTriplPlane(token,classToHasMap(modelData,SaveAsDraftRequest::class.java)).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

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


    fun pathImageToMultipart(nameParameter:String,realPathImage:String): MultipartBody.Part? {
        val file = File(realPathImage)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), realPathImage)
        return MultipartBody.Part.createFormData(nameParameter, file.getName(), requestFile)
    }

}