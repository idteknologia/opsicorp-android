package opsigo.com.datalayer.datanetwork

import okhttp3.ResponseBody
import opsigo.com.datalayer.request_model.accomodation.train.search.SearchTrainRequest
import org.junit.Test
import org.koin.test.KoinTest
import retrofit2.Call
import retrofit2.Response

import java.lang.Exception
import java.util.concurrent.CountDownLatch
import javax.security.auth.callback.Callback


class GetDataTest:KoinTest{

    val token = "Bearer yTe2KDlNtAwif7Rci5KUir0_mA36DEMImPLiiYkJRyux_OJxKAX9d3q_Wpz3mAEIAJbZbFVsQY1f0ae7DKi8qgFrugCRLZEBg1_Jgu5TH3TU0dFo3bxN_yb6euB9EM86mBh1czth3Bz-mVUYfd7n9g6A9LtPBXa2_2zWZD7rDi58L6ysp9hKhPUNG2XUt5BzQFWGprVl6GHu14CZsjc8KBx3fhdny1lx6DowtSfGehEFsqiKNxH1Pfk6jBcPfOMC2bKOXcpe3kQEW9buWweQaKSJ_0PkQapRIF5tsuSqYIWDLy9mephny8QejKXs7AwkUOCIgvuY636yUEhRuPRFG85H-TOYwGVP7y0gYkv8yYmTtRE4iIANVOV9eY4wMPvZtgjIgXRvM-B38_c_WAGfQXOXyYegCeo-tEur2g2Va0_9gPaQe-uraemUpm7p2JDobq4zBAd-C_TcTQ4gRP7tR_QkwxajpGT96-9m8lBtRCvUFWwaHtJyHILhGllz3dFasN1Gxj4esKr6b2av_5leAQCNC7CtfaxMU0LrT0TpBSA"

    val url = "https://mobile.opsicorp.com"

    @Test
    fun testApiResponse() {

        val latch = CountDownLatch(1)

//          GetDataLogin(url).apiOpsicorp.getDataLogin("password",
//                  "akzirhp","Opsicorp2019")
//                  .enqueue(object :Callback<ResponseBody>{
//                      override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                          latch.await()
//                      }
//
//                      override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                          latch.await()
//                      }
//                  })



          /*GetDataLogin().apiOpsicorp.getDataProfile(token).enqueue(object :Callback<ResponseBody>{
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  latch.await()
              }

              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful){
                      latch.await()
                  }
              }
          })*/

//        val api = GetDataLogin()
//        api.BASE_URL = "https://demo.opsicorp.com/"
//        api.apiOpsicorp.getDataConfig(token).enqueue(object :Callback<ResponseBody>{
//              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                  latch.await()
//              }
//
//              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                  if (response.isSuccessful){
//                      latch.await()
//                  }
//              }
//          })
/*
        GetDataGeneral().apiOpsicorp.getDataPurpose(token).enqueue(object :Callback<ResponseBody>{
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  latch.await()
              }

              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful){
                      latch.await()
                  }
              }
          })
        */


        /*GetDataGeneral().apiOpsicorp.getDataUpcomingFlight(token).enqueue(object :Callback<ResponseBody>{
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  latch.await()
              }

              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful){
                      latch.await()
                  }
              }
          })*/


        /*GetDataGeneral().apiOpsicorp.getDataAirlinePreference(token).enqueue(object :Callback<ResponseBody>{
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  latch.await()
              }

              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful){
                      latch.await()
                  }
              }
          })
        */


//        GetDataGeneral().apiOpsicorp.getDataBudget(token).enqueue(object :Callback<ResponseBody>{
//              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                  latch.await()
//              }
//
//              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                  if (response.isSuccessful){
//                      latch.await()
//                  }
//              }
//          })



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

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })
*/

        /*val emplaoyId = "51a5ed27-7faf-41b3-a9f2-e79a02021c1e"
        val emplaoyId = "6a4b28e9-a4f5-4419-a12b-1394597cc193"

        GetDataGeneral().getDataSummary(token, emplaoyId, object : CallbackSummary {
            //override fun successLoad(summaryModel: SummaryModel, contactModel: ContactModel) {
            override fun successLoad(SUMM: SummaryModel) {
                //Globals.setDataPreferenceString(context,"profile",Serializer.serialize(data,ProfileModel::class.java))
                //view.succesGetData()
                //Log.d("xx_vc_YE",": success " + SUMM.purpose )

                //Timber.d("Hello %s %s!", firstName);

                Log.d("xxx","123")

                //tv_user_info.text = summaryModel.purpose

                //xx = summaryModel
                //SUMM = summaryModel

                //val listTripParticipant = SUMM.tripParticipantModels

                for (tp in SUMM.tripParticipantModels!!){
                    //Log.v("xx_DEFaa", tp.emplaoyId + " - " + tp.fullName)


                    //val idx = tp.emplaoyId
                    //val participantName = tp.fullName

                    for (tit in tp.tripItemTypes!!){
                        //Log.v("xx_DEFbb", tit.emplaoyId + " - " + tit.type + " _ " + tit.name)

                        for (t_items in tit.tripItems!!){
                            //Log.v("xx_DE_cc", t_items.employeeName)

                            val tripFlights = t_items.tripFlights
                            if (tripFlights != null && tripFlights.size > 0) {

                                if(tp.isApproveForm!!){

//                                    val img_url = tripFlights.get(0).getSegments().get(0).getAirlineImageUrl()
                                    val img_url = tripFlights.get(0).segments?.get(0)?.airlineImageUrl

//                                    displayConfirmNotification(InitApplications.appContext, SystemClock.uptimeMillis().toInt(),
//                                            emplaoyId, tp.emplaoyId.toString(), SUMM.purpose.toString(), SUMM.destinationName.toString(), tp.fullName,
//                                            SUMM.code.toString(), t_items.tripFlights, Constants.TripType.Airline, null, null,
//                                            t_items.amount!!, img_url)

                                }
                            }

                            for (t_flight in t_items.tripFlights!!){
                                //Log.v("xx_DE_cc", t_flight.origin + " - " + t_flight.destination + " - " + t_flight.amount)

                            }
                        }
                    }

                    //val tripPlanItemsItem = tp
                }

                //saveDataSummary(summaryModel)

            }

            override fun failedLoad(message: String) {
                //Log.d("xx_vc_YE",": fail " + message )
            }
        })*/



//        GetDataGeneral().setDeviceId(token, "rahman","3fc110af-d157-466b-b184-2b092f5305a9","Samsung S7 Plus", object : CallbackSetDeviceId {
//            //override fun successLoad(summaryModel: SummaryModel, contactModel: ContactModel) {
//            override fun successLoad(model: DeviceIdModel) {
//                latch.await()
//                //Globals.setDataPreferenceString(context,"profile",Serializer.serialize(data,ProfileModel::class.java))
//                //view.succesGetData()
//                //Log.d("xx_vc_YE",": success " + summaryModel.purpose )
//                //tv_user_info.text = summaryModel.purpose
//
//            }
//
//            override fun failedLoad(message: String) {
//                latch.await()
//                //Log.d("xx_vc_YE",": fail " + message )
//            }
//        })

        /*GetDataGeneral().setDeviceId(token, "rahman","3fc110af-d157-466b-b184-2b092f5305a9","Samsung S7 Plus", object : CallbackSetDeviceId {
            //override fun successLoad(summaryModel: SummaryModel, contactModel: ContactModel) {
            override fun successLoad(model: DeviceIdModel) {
                latch.await()
                //Globals.setDataPreferenceString(context,"profile",Serializer.serialize(data,ProfileModel::class.java))
                //view.succesGetData()
                //Log.d("xx_vc_YE",": success " + summaryModel.purpose )
                //tv_user_info.text = summaryModel.purpose

            }

            override fun failedLoad(message: String) {
                latch.await()
                //Log.d("xx_vc_YE",": fail " + message )
            }
        })*/
//
//
//        GetDataGeneral().getDataSummary(token, emplaoyId, object : CallbackSummary {
//            //override fun successLoad(summaryModel: SummaryModel, contactModel: ContactModel) {
//            override fun successLoad(summaryModel: SummaryModel) {
//                latch.await()
//                //Globals.setDataPreferenceString(context,"profile",Serializer.serialize(data,ProfileModel::class.java))
//                //view.succesGetData()
//                //Log.d("xx_vc_YE",": success " + summaryModel.purpose )
//                //tv_user_info.text = summaryModel.purpose
//
////                //xx = summaryModel
////                SUMM = summaryModel
////
////
////                //val listTripParticipant = SUMM.tripParticipantModels
////
////                for (tp in SUMM.tripParticipantModels!!){
////                    Log.v("xx_DEFaa", tp.emplaoyId + " - " + tp.fullName)
////
////                    if(tp.isApproveForm!!){
////                        //ntar merge dulu
//////                        displayConfirmNotification(InitApplications.appContext, SystemClock.uptimeMillis().toInt(),
//////                                tripId, tp.getEmplaoyId(), tripSummary.getPurpose(), tripSummary.getDestinationName(), tp.fullName,
//////                                SUMM.code, tripFlights, Constants.TripType.Airline, null, null, tripPlanItem.getAmount(), img_url)
////                    }
////
////
////                    //val idx = tp.emplaoyId
////                    //val participantName = tp.fullName
////
////                    for (tit in tp.tripItemTypes!!){
////                        Log.v("xx_DEFbb", tit.emplaoyId + " - " + tit.type + " _ " + tit.name)
////
////                        for (t_items in tit.tripItems!!){
////                            Log.v("xx_DE_cc", t_items.employeeName)
////
////                            for (t_flight in t_items.tripFlights!!){
////                                Log.v("xx_DE_cc", t_flight.origin + " - " + t_flight.destination + " - " + t_flight.amount)
////                            }
////                        }
////                    }
////
////                    //val tripPlanItemsItem = tp
////                }
//
//                //saveDataSummary(summaryModel)
//
//            }
//
//            override fun failedLoad(message: String) {
//                latch.await()
//                //Log.d("xx_vc_YE",": fail " + message )
//            }
//        })

        /*GetDataLogin().getDataLogin("akzirhp","Opsicorp2019")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DataLoginModel> {
                    override fun onComplete() {
                        Log.e("TAG","completed")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.e("TAG","disposs")
                    }

                    override fun onNext(t: DataLoginModel) {
                        Log.e("TAG","success ${t.userName}")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("TAG","${e.printStackTrace()}")
                    }
                })*/

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }



    }

    @Test
    fun testLogin(){
        val latch = CountDownLatch(1)

        GetDataLogin(url).apiOpsicorp.getDataLogin("password",
                "klarisha","Opsicorp2020!").enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testApiTrain(){
        val latch = CountDownLatch(1)
//            GetDataAccomodation(url).apiOpsicorp.getDataDestinationTrain(token)
//                    .enqueue(object : Callback<ResponseBody> {
//                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                            latch.await()
//                        }
//
//                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                            latch.await()
//                        }
//                    })

            val data = SearchTrainRequest()
            data.airline     = "21"
            data.departDate  = "2020-01-16"
            data.destination = "BD"
            data.origin      = "GMR"
            data.returnDate  = ""
//            data.travelAgentCode = "apidev"

//            val callRequest = GetDataAccomodation(url).apiOpsicorp.getDataSearchTrain(token,classToHasMaps(data,SearchTrainRequest::class.java))
//            callRequest.enqueue(object :Callback<ResponseBody>{
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//
//                }
//
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//                }
//            })
//            callRequest.cancel()



        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    fun classToHasMaps(objects: Any, nameClass:Class<*>):HashMap<String, Any>{
//        val maps = HashMap<String, Any>()
//        for (field in nameClass.getDeclaredFields()) {
//            if (!field.isAccessible()) {
//                field.setAccessible(true)
//            }
//            maps.put(field.getName(), field.get(objects))
//        }
//
//        return maps
//    }


}