package com.mobile.travelaja.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.CityDefaultRepository
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.mobile.travelaja.module.home.presenter.HomeViewModel
import com.mobile.travelaja.module.home.repository.HomeDefaultRepository
import com.mobile.travelaja.module.settlement.repository.DefaultSettlementRepository
import com.mobile.travelaja.module.settlement.repository.TripServiceLocator
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.module.settlement.viewmodel.TripViewModel
import com.mobile.travelaja.module.signin.login.viewmodel.UserServiceLocator
import com.mobile.travelaja.module.signin.login.viewmodel.UserViewModel
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.network.ServiceApi
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class DefaultViewModelFactory(private val isFake: Boolean, private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val baseUrl = Globals.getBaseUrl(context)
        val tkn = Globals.getToken()
        val api = ServiceApi.createRequest(tkn, baseUrl)
        if (modelClass.isAssignableFrom(SettlementViewModel::class.java)) {
            return SettlementViewModel(DefaultSettlementRepository(api)) as T
        } else if (modelClass.isAssignableFrom(TripViewModel::class.java)) {
            val repository = TripServiceLocator.instance(api).getRepository()
            return TripViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ItineraryViewModel::class.java)){
            val repository = CityDefaultRepository(api)
            return ItineraryViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val repository = HomeDefaultRepository(api)
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            val repository = UserServiceLocator.instance(api).getRepository()
            return UserViewModel(repository) as T
        } else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}