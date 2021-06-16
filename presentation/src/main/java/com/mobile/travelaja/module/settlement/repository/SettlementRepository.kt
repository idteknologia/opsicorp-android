package com.mobile.travelaja.module.settlement.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import opsigo.com.domainlayer.model.settlement.Settlement

interface SettlementRepository {
    fun getSettlements(query : MutableMap<String,Any>) : Flow<PagingData<Settlement>>
}