package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.flow.Flow
import opsigo.com.domainlayer.model.settlement.Settlement

class SettlementViewModel(private val repository : SettlementRepository)  : ViewModel(){
    val buttonNextEnabled = ObservableBoolean(false)
    val selectedCode = ObservableField<String>()

    private var currentResult : Flow<PagingData<Settlement>>?= null

    fun getSettlement(query : MutableMap<String,Any>) : Flow<PagingData<Settlement>> {
        val lastResult = currentResult
        if (lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<Settlement>> = repository.getSettlements(query)
        currentResult = newResult
        return newResult
    }

}