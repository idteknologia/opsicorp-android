package opsigo.com.datalayer.mapper

import com.google.gson.Gson
import opsigo.com.domainlayer.model.accomodation.hotel.CountryHotel

object Serializer {
    private val gson = Gson()

    fun serialize(`object`: Any, clazz: Class<*>): String {
        return gson.toJson(`object`, clazz)
    }

    fun serialize(`object`: Any): String {
        return gson.toJson(`object`)
    }

    fun <T> deserialize(string: String?, clazz: Class<T>): T {
        return gson.fromJson(string, clazz)
    }

    fun <T> deserialize(string: String?, clazz: Class<Array<T>>): Array<T>? {
        return gson.fromJson(string, clazz)
    }
}