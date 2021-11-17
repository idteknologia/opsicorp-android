package opsigo.com.datalayer.datanetwork.dummy.signin

import opsigo.com.domainlayer.model.signin.DataLoginModel
import opsigo.com.domainlayer.model.signin.ProfileModel

class DataDummyUser {

    fun addDataUserLogin1(): ProfileModel {

        val data = ProfileModel()

        data.fullName            = "Dwi wahyudi"
        data.nameAgent       = "Golden Rama"
        data.position        = "Aprover"
        data.address         = "jl tanah abang 2 no 54 jakarta pusat"
        data.email           = "dwi@gmail.com"
        data.employId              = "1"
        data.phone           = "081542563"
        //data.codeNationality = "09"
        data.nationality     = "Indonesia"
        data.languange       = "indonesia"
        data.imageUrl        = "https://i.ibb.co/D9zgWdF/profile-ironman.png"


        return data
    }

    fun addDataUserLogin2(): ProfileModel {

        val data = ProfileModel()
        data.fullName            = "vodi"
        data.nameAgent       = "Opsigo"
        data.position        = "karyawan"
        data.address         = "jl tanah abang 3 no 04 jakarta pusat"
        data.email           = "vodi@gmail.com"
        data.employId              = "2"
        data.phone           = "081542563"
        //data.codeNationality = "09"
        data.nationality     = "Indonesia"
        data.languange       = "indonesia"
        data.imageUrl        = ""

        return data
    }

    fun addLoginDummy1(): DataLoginModel {

        val data = DataLoginModel()
        data.employId = "783224"
        data.userName = "Dwi wahyudi"
        data.token    = "783224" //783224

        return data
    }

    fun addLoginDummy2(): DataLoginModel {

        val data = DataLoginModel()
        data.employId = "783292455"
        data.userName = "Vodi"
        data.token    = "2367842"

        return data
    }

}