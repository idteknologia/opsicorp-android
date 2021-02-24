package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.accomodation.flight.ssr.SsrResponseEntity
import opsigo.com.datalayer.model.accomodation.flight.ssr.SsrsItem
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import opsigo.com.domainlayer.model.accomodation.flight.DataSsrModel
import opsigo.com.domainlayer.model.accomodation.flight.SsrItemModel

class GetSsrMapper {

    lateinit var model: SsrResponseEntity
    var currentType = -1
    object TypeSsr {
        var Unspecified = 0
        var Baggage = 1
        var SportEquipment = 2
        var Meal = 3
        var Product = 4
        var Beverage = 4
        var Seat = 5
        var Infant = 6
        var Drink = 7
        var WhellChair = 7
        var InFlightEntertainment = 8
        var ComfortKit = 9
        var Other = 99

    }

    fun mapper(toString: String): SsrModel {
        val data = SsrModel()

        model = Serializer.deserialize(toString, SsrResponseEntity::class.java)

        if (model.errorMessage.toString().isEmpty()) {
            data.isHaveDrink = model.data.first().isHaveDrink
            data.isDoubleDrink = model.data.first().isDoubleDrink
            data.isHaveMeal = model.data.first().isHaveMeals
            data.isHaveSport = model.data.first().isHaveSport
            data.isHaveDrink = model.data.first().isHaveDrink

            try {
                model.data.first().ssrs.filter { it.ssrType == TypeSsr.Baggage }.forEach {
                    val mDataBagage = DataSsrModel()
                    data.isBagage = true
                    mDataBagage.curency = it.ccy.toString()
                    mDataBagage.id = model.data.first().id.toString()
                    mDataBagage.pricing = it.ssrFare.toString()
                    mDataBagage.ssrName = it.ssrName.toString()
                    mDataBagage.ssrCode = it.ssrCode.toString()
                    mDataBagage.ssrType = it.ssrType.toString()
                    mDataBagage.ssrTypeName = it.ssrTypeName.toString()
                    data.dataBagage.add(mDataBagage).toString()
                }
                data.isBagage = data.dataBagage.isNotEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                    model.data.first().ssrs.filter { it.ssrType != TypeSsr.Baggage }.sortedBy { it.ssrType }.forEach {
                        if (it.ssrType != currentType){
                            currentType = it.ssrType!!
                            data.dataSsr.add(dataSsrMapper(it))
                        }
                    }


            } catch (e:Exception){
                e.printStackTrace()
            }

                /*try {
                if (model.data.first().isHaveDrink){
                    model.data.first().ssrs.filter { it.ssrType != TypeSsr.Baggage }.forEach {
                        val mDataDrink = DataSsrModel()
                        mDataDrink.curency = it.ccy.toString()
                        mDataDrink.id      = model.data.first().id.toString()
                        mDataDrink.pricing = it.ssrFare.toString()
                        mDataDrink.ssrName = it.ssrName.toString()
                        mDataDrink.ssrCode = it.ssrCode.toString()
                        mDataDrink.ssrType = it.ssrType.toString()
                        mDataDrink.ssrTypeName = it.ssrTypeName.toString()
                        data.dataSsr.add(mDataDrink).toString()
                    }
                    data.isHaveDrink = data.dataSsr.isNotEmpty()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }*/
                /*try {
                if (model.data.first().isHaveSport){
                    model.data.first().ssrs.filter { it.ssrType == model.data.first().sportEquipmentType  }.forEach {
                        val mDataSport = DataSsrModel()
                        mDataSport.curency = it.ccy.toString()
                        mDataSport.id      = model.data.first().id.toString()
                        mDataSport.pricing = it.ssrFare.toString()
                        mDataSport.ssrName = it.ssrName.toString()
                        mDataSport.ssrCode = it.ssrCode.toString()
                        mDataSport.ssrType = it.ssrType.toString()
                        mDataSport.ssrTypeName = it.ssrTypeName.toString()
                        data.dataSsr.add(mDataSport).toString()
                    }
                    data.isHaveSport = data.dataSsr.isNotEmpty()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }*/

                /*try {
                if (model.data.first().isHaveMeals){
                    model.data.first().ssrs.filter { it.ssrType == model.data.first().mealType  }.forEach {
                        val mDataMeal = DataSsrModel()
                        mDataMeal.curency = it.ccy.toString()
                        mDataMeal.id      = model.data.first().id.toString()
                        mDataMeal.pricing = it.ssrFare.toString()
                        mDataMeal.ssrName = it.ssrName.toString()
                        mDataMeal.ssrCode = it.ssrCode.toString()
                        mDataMeal.ssrType = it.ssrType.toString()
                        mDataMeal.ssrTypeName = it.ssrTypeName.toString()
                        data.dataSsr.add(mDataMeal).toString()
                    }
                    data.isHaveMeal = data.dataSsr.isNotEmpty()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }*/
                /*try {
                if (model.data.first().isHaveMeals){
                    model.data.first().ssrs.filter {
                            it.ssrType != model.data.first().mealType &&
                            it.ssrType != model.data.first().sportEquipmentType &&
                            it.ssrType != model.data.first().drinkType &&
                            it.ssrType != TypeSsr.Baggage
                    }.forEach {
                        val mDataOther = DataSsrModel()
                        mDataOther.curency = it.ccy.toString()
                        mDataOther.id      = model.data.first().id.toString()
                        mDataOther.pricing = it.ssrFare.toString()
                        mDataOther.ssrName = it.ssrName.toString()
                        mDataOther.ssrCode = it.ssrCode.toString()
                        mDataOther.ssrType = it.ssrType.toString()
                        mDataOther.ssrTypeName = it.ssrTypeName.toString()
                        data.dataOther.add(mDataOther).toString()
                    }
                    data.isOther = data.dataOther.isNotEmpty()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }*/

        }

                return data

    }

    private fun dataSsrMapper(it: SsrsItem): SsrItemModel {
        var mData = SsrItemModel()
        mData.ssrTypeName = it.ssrTypeName.toString()
        mData.ssrFlightNumber    = it.flightNumber.toString()
        mData.ssrItem.addAll(ssrItemMapper())
        return mData
    }

    private fun ssrItemMapper(): ArrayList<DataSsrModel> {
        var mData = ArrayList<DataSsrModel>()
        model.data.first().ssrs.filter { it.ssrType == currentType }.map {
            val model = DataSsrModel()
            model.id = it.id.toString()
            model.ssrCode = it.ssrCode.toString()
            model.curency = it.ccy.toString()
            model.pricing = it.ssrFare.toString()
            model.ssrName = it.ssrName.toString()
            model.ssrType = it.ssrType.toString()
            model.ssrTypeName = it.ssrTypeName.toString()
            mData.add(model)
        }
        return mData
    }
}