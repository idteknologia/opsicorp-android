package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.create_trip_plane.trip_plan.BudgetEntity
import opsigo.com.domainlayer.model.BudgetModel
import org.json.JSONArray


class BudgetEntityDataMapper{

    fun transform(listBudgetEntity: JSONArray): ArrayList<BudgetModel> {

        val data = ArrayList<BudgetModel>()
        data.clear()
        for (i in 0 until listBudgetEntity.length()){
            val mData = Serializer.deserialize(listBudgetEntity.get(i).toString(), BudgetEntity::class.java)
            mData.budgetDetails.forEachIndexed { index, budgetDetailsItem ->
                val model = BudgetModel()
                model.value = budgetDetailsItem.code+" - "+budgetDetailsItem.name
                model.id    = budgetDetailsItem.id
                data.add(model)
            }
        }

        return data
    }
}