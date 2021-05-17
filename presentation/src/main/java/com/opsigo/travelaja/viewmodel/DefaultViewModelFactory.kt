package com.opsigo.travelaja.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.opsigo.travelaja.module.settlement.repository.DefaultSettlementRepository
import com.opsigo.travelaja.module.settlement.repository.TripListRepository
import com.opsigo.travelaja.module.settlement.repository.TripServiceLocator
import com.opsigo.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.opsigo.travelaja.module.settlement.viewmodel.TripViewModel
import opsigo.com.datalayer.network.ServiceApi
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class DefaultViewModelFactory(private val isFake: Boolean, private val context: Context) : ViewModelProvider.Factory {
    private val api = ServiceApi.createRequest(context)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettlementViewModel::class.java)){
            return SettlementViewModel(DefaultSettlementRepository(api)) as T
        }else if (modelClass.isAssignableFrom(TripViewModel::class.java)){
            val repository = TripServiceLocator.instance(context).getRepository()
            return TripViewModel(repository) as T
        } else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}