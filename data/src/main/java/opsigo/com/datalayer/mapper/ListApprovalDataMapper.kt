package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.cart.TripParticipantCustomApprovalsItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import java.lang.Exception

class ListApprovalDataMapper: Mapper<ArrayList<TripParticipantCustomApprovalsItem?>?, List<ParticipantModelDomain>>() {

    override fun mapFrom(from: ArrayList<TripParticipantCustomApprovalsItem?>?): ArrayList<ParticipantModelDomain> {

        from.let {

            val mData = ArrayList<ParticipantModelDomain>()
            mData.clear()
            val tpModelList    = it?.map { data ->
                val approval   = ParticipantModelDomain()
                approval.id    = data?.id.toString()
                approval.employId = data?.approverId.toString()
                approval.name  = data?.approverName.toString()
                approval.positionName = data?.approverPositionName.toString()
                approval.positionId = data?.approverPositionId.toString()
                approval.isPjs = if (data?.approverIsPjs==null) false else data.approverIsPjs
                approval.layer = if (data?.layers==null) -1 else data.layers
                approval.isCompletelyReviewed = if (data?.isCompletelyReviewed==null) false else data.isCompletelyReviewed
                approval.followUpFlight = exception(data?.followUpAirline)
                approval.followUpHotel  = exception(data?.followUpHotel)
                approval.followUpTrain  = exception(data?.followUpTrain)
                return@map approval
            }
            if (!tpModelList.isNullOrEmpty()){
                mData.addAll(tpModelList)
            }
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