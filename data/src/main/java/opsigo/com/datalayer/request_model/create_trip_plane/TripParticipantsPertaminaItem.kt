package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class TripParticipantsPertaminaItem(

        @field:SerializedName("UseCostCenterOther")
        var useCostCenterOther: Boolean = false,

        @field:SerializedName("CostCenterPicEmail")
        var costCenterPicEmail: String = "",

        @field:SerializedName("EmployeeId")
        var employeeId: String = "",

        @field:SerializedName("CostCenterCode")
        var costCenterCode: String = "",

        @field:SerializedName("UseCashAdvance")
        var useCashAdvance: Boolean = false,

        @field:SerializedName("CashAdvance")
        var cashAdvance: Int = 0,

        @field:SerializedName("EstFlight")
        var estFlight: Int = 0,

        @field:SerializedName("EstTransportation")
        var estTransportation: Int = 0,

        @field:SerializedName("EstTotal")
        var estTotal: Int = 0,

        @field:SerializedName("EstAllowance")
        var estAllowance: Int = 0,

        @field:SerializedName("EstLaundry")
        var estLaundry: Int = 0,

        @field:SerializedName("EstAllowanceEvent")
        var estAllowanceEvent: Int = 0,

        @field:SerializedName("EstHotel")
        var estHotel: Int = 0
)