package opsigo.com.datalayer.datanetwork

import android.util.Log
import opsigo.com.datalayer.BuildConfig
import opsigo.com.datalayer.di.*

open class BaseGetData {

    var BASE_URL = "http://opsicorp-mobile.opsinfra.com/"

    val messageFailed ="failed mapping data"

//    val BASE_URL = "https://demo.opsicorp.com/"
//    val BASE_URL = "https://dev.opsicorp.com/"

    fun apiDependency() : ApiComponent {
        val mApiComponentWeather = DaggerApiComponent.builder()
                .networkComponent(getNetworkComponent())
                .build()
        return mApiComponentWeather
    }

    fun getNetworkComponent(): NetworkComponent {
        return DaggerNetworkComponent.builder()
                .networkModule(NetworkModule(BASE_URL))
                .build()
    }

    fun setLog(message:String){
        if(BuildConfig.DEBUG){
            Log.e("Test",message)
        }
    }

}