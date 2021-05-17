package com.opsigo.travelaja.module.settlement.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.settlement.Settlement

class DefaultSettlementRepository(private val api: ServiceApi) : SettlementRepository {
    override fun getSettlements(query: MutableMap<String, Any>): Flow<PagingData<Settlement>> =
            Pager(PagingConfig(30)) {
                SettlementPagingSource(api, query)
            }.flow
}