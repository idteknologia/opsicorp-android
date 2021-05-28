package com.opsigo.travelaja.module.settlement.repository

import com.opsigo.travelaja.base.paging.PageKeyedPagingSource
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.ResultList
import opsigo.com.domainlayer.model.settlement.Settlement

class SettlementPagingSource(private val api : ServiceApi,private val query : MutableMap<String,Any>) : PageKeyedPagingSource<Settlement>() {
    override suspend fun getResultFromService(page: Int): ResultList<Settlement> {
        query[INDEX_KEY] = page
        query[SIZE_KEY] = SIZE
        query["Direction"] = 1
        query["OrderBy"] = "Code"
        return api.getSettlement(query)
    }
}