package com.opsigo.travelaja.utility

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

class GetNameCountry {

    fun nameCountry(context: Context?, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>? = null

        addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val result: Address

        if (addresses != null && !addresses.isEmpty()) {
            return addresses[0].locality+", "+addresses[0].getCountryName()
        }else {
            return ""
        }

    }
}