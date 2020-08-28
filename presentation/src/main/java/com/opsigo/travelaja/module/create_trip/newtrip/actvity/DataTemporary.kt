package com.opsigo.travelaja.module.create_trip.newtrip.actvity

import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.PurposeModel
import opsigo.com.domainlayer.model.signin.CountryModel

object DataTemporary {
    var dataPurphose = ArrayList<PurposeModel>()
    var dataCity  = ArrayList<SelectNationalModel>()
    var dataSelectBudget = ArrayList<SelectNationalModel>()
    var dataCostCenter   = ArrayList<SelectNationalModel>()
    var dataCountry = ArrayList<CountryModel>()
}