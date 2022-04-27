package opsigo.com.datalayer.datanetwork

import retrofit2.Call
import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import org.json.JSONObject
import okhttp3.ResponseBody
import opsigo.com.datalayer.mapper.*
import opsigo.com.domainlayer.callback.*
import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.model.signin.LoginEntity
import opsigo.com.domainlayer.usecase.LoginRepository
import opsigo.com.datalayer.model.general.CountryEntity
import opsigo.com.datalayer.model.profile.ConfigEntity
import opsigo.com.datalayer.model.profile.ProfileNewEntity

class GetDataLogin(baseUrl:String) : BaseGetData(), LoginRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getDataLogin(username: String, password: String, callbackLogin: CallbackLogin) {
        apiOpsicorp.getDataLogin("password",username,password).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackLogin.failedGetData(t.message!!) //error here
                //when /NetworkSecurityConfig: No Network Security Config specified, using platform default
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        callbackLogin.successGetData(LoginEntityDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), LoginEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackLogin.failedGetData(message)
                    }
                }catch (e:Exception){
                    callbackLogin.failedGetData(messageFailed)
                }
            }
        })
    }

    override fun getDataProfile(token:String,callbackProfile: CallbackProfile) {
        apiOpsicorp.getDataProfile(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackProfile.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.code()==200){
                        callbackProfile.successLoad(ProfileDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), ProfileNewEntity::class.java)))
                    }
                    else if(response.code()==401){
                        callbackProfile.failedLoad("token")
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackProfile.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackProfile.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getDataCountry(token: String, callbackCountry: CallbackCountry) {
        apiOpsicorp.getDataCountry(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackCountry.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        var xxx = response.body()?.string().toString()
                        //callbackCountry.successLoad(CountryMapper().mapping(Serializer.deserialize(response.body()?.string().toString(), CountryEntity::class.java)))
                        callbackCountry.successLoad(CountryMapper().mapping(Serializer.deserialize(xxx, CountryEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackCountry.failedLoad(message)
                        Log.d("cekcountry_def","go here 3 " )
                    }
                }catch (e:Exception){
                    Log.d("cekcountry_1abc","go here 4 " + e.toString() )
                    callbackCountry.failedLoad(messageFailed)
                }
            }
        })

    }

    override fun getDataRegister(data :HashMap<Any, Any> ,callback: CallbackString) {
        apiOpsicorp.getRegisterByEmail(data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.code()==200){
                        val resp = JSONObject(response.body()?.string())
                        val status = resp.getBoolean("status")
                        if (status){
                            callback.successLoad("success")
                        }
                        else {
                            val error = resp.getString("errorMessage")
                            callback.failedLoad(error)
                        }
                    }
                    else {
                        callback.failedLoad(messageFailed)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getDataValidationOtp(data :HashMap<Any, Any>, callback: CallbackString) {
        apiOpsicorp.getValidationOtp(data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.code()==200){
                        val resp = JSONObject(response.body()?.string())
                        val status = resp.getBoolean("status")
                        if (status){
                            val id = resp.getJSONObject("data").getString("id")
                            callback.successLoad(id)
                        }
                        else {
                            val error = resp.getString("errorMessage")
                            callback.failedLoad(error)
                        }
                    }
                    else {
                        callback.failedLoad(messageFailed)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getCompletlyRegister( data :HashMap<Any, Any> ,callback: CallbackString) {
        apiOpsicorp.getCompletlyRegister(data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    try {
                        if (response.code()==200){
                            val resp = JSONObject(response.body()?.string())
                            val status = resp.getBoolean("status")
                            if (status){
                                callback.successLoad("success")
                            }
                            else {
                                val error = resp.getString("errorMessage")
                                callback.failedLoad(error)
                            }

                        }
                        else {
                            callback.failedLoad(messageFailed)
                        }
                    }catch (e:Exception){
                        callback.failedLoad(messageFailed)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getDataConfig(token:String,callbackConfig: CallbackConfig) {
        apiOpsicorp.getDataConfig(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackConfig.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        callbackConfig.successLoad(ConfigEntityDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), ConfigEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackConfig.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackConfig.failedLoad(messageFailed)
                }
            }
        })
    }

}
