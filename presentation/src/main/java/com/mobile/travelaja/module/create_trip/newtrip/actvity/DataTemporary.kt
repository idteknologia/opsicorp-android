package com.mobile.travelaja.module.create_trip.newtrip.actvity

import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.PurposeModel
import opsigo.com.domainlayer.model.signin.CountryModel
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel

object DataTemporary {
    var dataPurphose = ArrayList<PurposeModel>()
    var dataActivity = ArrayList<TypeActivityTravelRequestModel>()
    var dataCity  = ArrayList<SelectNationalModel>()
    var dataSelectBudget = ArrayList<SelectNationalModel>()
    var dataCostCenter   = ArrayList<SelectNationalModel>()
    var dataCountry = ArrayList<CountryModel>()
}