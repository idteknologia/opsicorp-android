package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.BudgetModel

interface CallbackBudget {
    fun successLoad(data: ArrayList<BudgetModel>)
    fun failedLoad(message:String)
}