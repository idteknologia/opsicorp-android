package opsigo.com.datalayer.mapper

import com.google.gson.Gson

object Serializer {
    private val gson = Gson()

    fun serialize(`object`: Any, clazz: Class<*>): String {
        return gson.toJson(`object`, clazz)
    }

    fun serialize(`object`: Any): String {
        return gson.toJson(`object`)
    }

    fun <T> deserialize(string: String, clazz: Class<T>): T {
        return gson.fromJson(string, clazz)
    }
}