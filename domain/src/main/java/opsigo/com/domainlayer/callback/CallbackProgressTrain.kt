package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.train.ProgressTrainModel

interface CallbackProgressTrain {
    fun success(progressData: ProgressTrainModel)
    fun failed(message:String)
}