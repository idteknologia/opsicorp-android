package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.TripParticipantCustomApprovalsItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import java.lang.Exception

class ListApprovalDataMapper
constructor() : Mapper<ArrayList<TripParticipantCustomApprovalsItem>, List<ParticipantModelDomain>>() {

    override fun mapFrom(from: ArrayList<TripParticipantCustomApprovalsItem>): ArrayList<ParticipantModelDomain> {

        from.let {

            val mData = ArrayList<ParticipantModelDomain>()
            mData.clear()
            val tpModelList    = it.map { data ->
                val approval   = ParticipantModelDomain()
                approval.id    = data.id
                approval.employId = data.approverId
                approval.name  = data.approverName
                approval.layer = data.layers
                approval.isCompletelyReviewed = data.isCompletelyReviewed
                approval.followUpFlight = exception(data.followUpAirline)
                approval.followUpHotel  = exception(data.followUpHotel)
                approval.followUpTrain  = exception(data.followUpTrain)
                return@map approval
            }
            mData.addAll(tpModelList)
            return mData
        }

    }

    private fun exception(string: String?): String {
        try {
            return string!!
        }catch (e:Exception){
            return ""
        }
    }
}