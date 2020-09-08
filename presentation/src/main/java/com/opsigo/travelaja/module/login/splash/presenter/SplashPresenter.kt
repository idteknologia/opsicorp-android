package com.opsigo.travelaja.module.login.splash.presenter

import android.content.Context
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.opsigo.travelaja.module.login.splash.view.SplashView
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.Globals.setLog
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.model.BudgetModel
import opsigo.com.domainlayer.model.DestinationAccomodationModel
import opsigo.com.domainlayer.model.PurposeModel
import opsigo.com.domainlayer.model.accomodation.flight.CodeAirLineModel
import opsigo.com.domainlayer.model.accomodation.flight.CodeSearchAirLineModel
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.signin.CheckVersionModel
import opsigo.com.domainlayer.model.signin.CountryModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import org.json.JSONArray
import java.io.IOException
import java.util.HashMap


class SplashPresenter {

    val context             : Context
    val view                : SplashView
    var thisToken = ""
    var thisFCMToken = ""
    var thisModelPhone = ""
    var thisUsername = ""
    var baseUrl = ""
    val expiredTokenMessage = "token"
    val version_url = "http://mobapi.svc.opsinfra.net/"

    constructor(context: Context, view: SplashView) {
        this.context = context
        this.view    = view
        baseUrl      = Globals.getBaseUrl(context)
    }

    fun getData(token:String, modelPhone: String, username: String) {
        thisToken       = token
        thisModelPhone  = modelPhone
        thisUsername    = username

            Thread(Runnable {
                try {
                    val fcmToken = FirebaseInstanceId.getInstance().getToken(context.getString(R.string.SENDER_ID), "FCM")
                    Log.i("MyFirebaseTokenY ok", fcmToken)
                    //Log.i("MyFirebaseTokenXX", FirebaseInstanceId.getInstance().id)
                    Globals.setDataPreferenceString(context, Constants.FCM_TOKEN, fcmToken)
                    val fcm = Globals.getDataPreferenceString(context, Constants.FCM_TOKEN)
                    thisFCMToken = fcm
                    Log.d("proxx 5 abc_dx11",": " + thisFCMToken)
                    getCheckVersion(token)
                } catch (e: IOException) {
                    Log.i("MyFirebaseTokenY no", "something wrong")
                    e.printStackTrace()
                }
            }).start()

    }

    //error, skip dulu
    fun getCheckVersion(token: String) {
        GetDataGeneral(version_url).getCheckVersion(token,Globals.getVersionCode(),"1",object :CallbackCheckVersion{
            override fun successLoad(data: CheckVersionModel) {
                getDataProfile(token)
            }

            override fun failedLoad(message: String) {
                //
                view.showDialogUpdate("Opsicorp", "New version is available,\nplease update your application")
                //view.showDialogUpdate("Sorry",message +" Or plase check connection")
            }
        })
    }

    fun getDataProfile(token:String) {
        GetDataLogin(baseUrl).getDataProfile(token,object : CallbackProfile {
            override fun successLoad(data: ProfileModel) {
                Globals.setDataPreferenceString(context,"profile",Serializer.serialize(data,ProfileModel::class.java))
                getDataPurphose(token)
            }

            override fun failedLoad(message: String) {
                if (expiredTokenMessage.equals(message)){
                    view.tokenExpired()
                }
                else {
                    view.failedGetData("profile")
                }

            }
        })
    }

    fun getDataPurphose(token: String) {
        GetDataTripPlane(baseUrl).getDataPurpose(token,object :CallbackPurpose{
            override fun successLoad(data: ArrayList<PurposeModel>) {
                DataTemporary.dataPurphose.clear()
                DataTemporary.dataPurphose.addAll(data)
                getDataBudget(token)
            }

            override fun failedLoad(message: String) {
                view.failedGetData("purphose")

            }
        })
    }

    fun getDataBudget(token: String) {
        GetDataTripPlane(baseUrl).getDataBudget(Globals.getToken(),Globals.getProfile(context).employId,Globals.getConfigCompany(context).defaultTravelAgent,object : CallbackBudget {
            override fun successLoad(data: ArrayList<BudgetModel>) {
                DataTemporary.dataSelectBudget.clear()
                data.forEachIndexed { index, budgetModel ->
                    val mData = SelectNationalModel()
                    mData.name = budgetModel.value
                    mData.id   = budgetModel.id
                    DataTemporary.dataSelectBudget.add(mData)
                }
                getDataCountry(token)
            }

            override fun failedLoad(message: String) {
                view.failedGetData("budget")

            }
        })
    }

    fun getDataStation(token: String) {
        GetDataAccomodation(baseUrl).getDataStationTrain(token,object :CallbackDestinationAccomodation{
            override fun success(data: ArrayList<DestinationAccomodationModel>) {

                Constants.DATA_TRAIN_STASION = data
                if(Globals.getDataPreferenceBolean(context,"login")){
                    checkAllairline(token)
                }
                else{
                    setDeviceId(thisToken, thisUsername, thisFCMToken, thisModelPhone)
                }

            }

            override fun failed(error: String) {
                view.failedGetData("station_train")
            }
        })

    }

    fun getDataCity(token: String) {
        GetDataTripPlane(baseUrl).getDataCity(token,object : CallbackArrayListCity {
            override fun failedLoad(message: String) {
                view.failedGetData("city")
            }

            override fun successLoad(data: ArrayList<SelectNationalModel>) {
                Constants.DATA_CITY = data
                getDataStation(token)

           }
        })
    }

    fun getDataCountry(token: String) {
        GetDataLogin(version_url).getDataCountry(token,object :CallbackCountry{
            override fun successLoad(data: ArrayList<CountryModel>) {
                Constants.DATA_NATIONAL = data
                getDataCity(token)
            }

            override fun failedLoad(message: String) {
                view.failedGetData("country")
            }
        })
    }

    fun setDeviceId(token:String, username:String, fcmToken: String, modelPhone: String) {

        GetDataGeneral(baseUrl).setDeviceId(token, username, fcmToken, modelPhone, object : CallbackSetDeviceId {

            override fun successLoad(isSuccess: Boolean) {
                checkAllairline(token)
            }

            override fun failedLoad(message: String) {
                Log.d("proxx 6",": failed " + message )
                view.failedGetData("setDeviceId")
            }
        })

    }

    private fun checkAllairline(token: String) {
        if (Globals.readJsonFromFile(context,Constants.FILE_NAME_ALL_CODE_AIRPORT).isEmpty()){
            setLog("get all airline")
            getAllAirLine(token)
        }
        else{
            setLog("not get all airline")
            view.successLoadData()
        }
    }

    fun getAllAirLine(token: String){
        GetDataAccomodation(baseUrl).getAllCodeFlight(token,object : CallbackGetAllCodeAirline {
            override fun success(data: ArrayList<CodeAirLineModel>) {
                Globals.writeJsonToFile(Serializer.serialize(data),context,Constants.FILE_NAME_ALL_CODE_AIRPORT)
                view.successLoadData()
            }

            override fun failed(string: String) {
                view.failedGetData("getAllAirline")
            }
        })
    }

}