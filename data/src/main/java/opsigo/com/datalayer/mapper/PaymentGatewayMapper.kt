package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.payment.PaymentEntity
import opsigo.com.domainlayer.model.PaymentModel


class PaymentGatewayMapper {
    fun mapping(data: PaymentEntity): PaymentModel {
        val mData = PaymentModel()
        mData.paymentLink = data.data?.paymentLink.toString()
        mData.errorMessage = data.errorMessage.toString()
        return mData
    }
}