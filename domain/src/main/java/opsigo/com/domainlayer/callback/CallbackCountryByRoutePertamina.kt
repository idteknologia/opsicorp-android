package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.CountryHotel

interface CallbackCountryByRoutePertamina {
    fun success(country:ArrayList<CountryHotel>)
    fun failed(message:String)
}