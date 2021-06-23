package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.listtripplan.StatusTrip
import opsigo.com.datalayer.model.approval.list_approval.ListApprovalEntity
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import org.json.JSONObject

class MapperModelListTripplan {

    fun mapper(data1:ListApprovalEntity) : ArrayList<ApprovalModelAdapter> {

        val mData = ArrayList<ApprovalModelAdapter> ()
        val data = data1.data
        data.forEachIndexed { index, dataItem ->

            val approvalModel = ApprovalModelAdapter()
            approvalModel.id            = dataItem.id//dataItem.emplaoyId
            approvalModel.status        = dataItem.status
            approvalModel.tripCode      = dataItem.code
            approvalModel.header        = dataItem.startDate
            approvalModel.title         = dataItem.purpose
            approvalModel.destination   = if (dataItem.destination==null) "" else dataItem.destination
            approvalModel.isApproval    = dataItem.isApprover
            approvalModel.isParticipant = dataItem.isParticipant

            dataItem.listApprover.forEachIndexed { index, listParticipantItem ->
                val mDataApproval = ParticipantModelDomain()
                mDataApproval.image    = ""
                mDataApproval.jobTitle = listParticipantItem.jobTitle
                mDataApproval.id       = listParticipantItem.approverId
                mDataApproval.name     = listParticipantItem.firstName
                approvalModel.listApproval.add(mDataApproval)
            }

            dataItem.listParticipant.forEachIndexed { index, listApproverItem ->
                val mDataApproval = ParticipantModelDomain()
                mDataApproval.image = ""
                mDataApproval.jobTitle = listApproverItem.jobTitle
                mDataApproval.name  = listApproverItem.firstName
                approvalModel.participant.add(mDataApproval)
            }

            approvalModel.start_date    = dataItem.startDate
            approvalModel.end_date      = dataItem.returnDate

            when(dataItem.status) {
                StatusTrip.WaitingForApproval -> {
                    approvalModel.status = "Waiting"
                }

                StatusTrip.Draft -> {
                    approvalModel.status = "Draft"
                }

                StatusTrip.PartiallyApproved -> {
                    approvalModel.status = "Partially Approved"
                }

                StatusTrip.PartiallyRejected -> {
                    approvalModel.status = "Partially Rejected"
                }

                StatusTrip.CompletelyApproved -> {
                    approvalModel.status = "Completely Approved"
                }

                StatusTrip.CompletelyRejected -> {
                    approvalModel.status = "Completely Rejected"
                }

                StatusTrip.Canceled -> {
                    approvalModel.status = "Canceled"
                }

                StatusTrip.Expired -> {
                    approvalModel.status = "Expired"
                }

                StatusTrip.PartiallyApprovedAndReject -> {
                    approvalModel.status = "Partially Approved And Reject"
                }

                StatusTrip.TripCompleted -> {
                    approvalModel.status = "Trip Completed"
                }

            }
            dataItem.code

            mData.add(approvalModel)
        }

        return mData
    }

    fun mapper2(string:String) : ArrayList<ApprovalModelAdapter> {

        val json = JSONObject(string)
        val data = json.getJSONArray("data")

        val mData = ArrayList<ApprovalModelAdapter> ()

        for(i in 0 until data.length()) {

            val approvalModel = ApprovalModelAdapter()
            approvalModel.id            = "1"//dataItem.emplaoyId
            approvalModel.status        = "Waiting"//dataItem.status
//            approvalModel.tripCode      = dataItem.code
//            approvalModel.title         = dataItem.purpose
//            approvalModel.destination   = dataItem.destination
//
            when(data.optJSONObject(i).optString("status")) {
                StatusTrip.WaitingForApproval -> {
                    approvalModel.status = "Waiting"
                }

                StatusTrip.Draft -> {
                    approvalModel.status = "Draft"
                }

                StatusTrip.PartiallyApproved -> {
                    approvalModel.status = "Partially Approved"
                }

                StatusTrip.PartiallyRejected -> {
                    approvalModel.status = "Partially Rejected"
                }

                StatusTrip.CompletelyApproved -> {
                    approvalModel.status = "Partially Completely Approved"
                }

                StatusTrip.CompletelyRejected -> {
                    approvalModel.status = "Completely Rejected"
                }

                StatusTrip.Canceled -> {
                    approvalModel.status = "Canceled"
                }

                StatusTrip.Expired -> {
                    approvalModel.status = "Expired"
                }

                StatusTrip.PartiallyApprovedAndReject -> {
                    approvalModel.status = "Partially Approved And Reject"
                }

                StatusTrip.TripCompleted -> {
                    approvalModel.status = "Trip Completed"
                }

            }
            data.optJSONObject(i).optString("code")

            mData.add(approvalModel)

        }

        return mData
    }
}