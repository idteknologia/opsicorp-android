package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.train.ValidationTrainModel

interface CallbackValidationTrain {
    fun successLoad(data: ValidationTrainModel)
    fun failedLoad(message:String)
}