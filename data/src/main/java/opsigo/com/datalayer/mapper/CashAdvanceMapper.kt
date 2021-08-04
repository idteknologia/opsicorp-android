package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.travel_request.CashAdvanceEntity
import opsigo.com.domainlayer.model.travel_request.CashAdvanceModel


class CashAdvanceMapper {
    fun mapping(response: CashAdvanceEntity): CashAdvanceModel {
        val data = CashAdvanceModel()
        data.isAllowed = response.isAllowed!!
        data.currency = response.currency.toString()
        data.maxAmount = response.maxAmount

        return data
    }
}