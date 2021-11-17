package opsigo.com.domainlayer.model.create_trip_plane

import com.google.gson.annotations.SerializedName

class ParticipantPertamina {
    var useCostCenterOther: Boolean = false
    var employeeId: String = ""
    var email: String = ""
    var positionName : String = ""
    var costCenterCode: String = ""
    var costCenterName :String = ""
    var useCashAdvance: Boolean = false
    var cashAdvance: Int = 0
    var estFlight: Int = 0
    var estTransportation: Int = 0
    var estTotal: Int = 0
    var estAllowance: Int = 0
    var estLaundry: Int = 0
    var estAllowanceEvent: Int = 0
    var estHotel: Int = 0
}