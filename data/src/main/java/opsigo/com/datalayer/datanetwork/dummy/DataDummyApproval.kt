package opsigo.com.datalayer.datanetwork.dummy

import opsigo.com.domainlayer.model.aprover.AllApprovalModel
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain

class DataDummyApproval {

    fun addData():ArrayList<ApprovalModelAdapter>{
        val data = ArrayList<ApprovalModelAdapter>()
        data.clear()

        val images = ArrayList<String>()
        images.add("https://i.ibb.co/yBMvJr3/Group-6.png")
        images.add("https://i.ibb.co/xG1PwjX/Row.png")

        val names = ArrayList<String>()
        names.add("dodik")
        names.add("Kurnia")

        val mStatus = ArrayList<String>()
        val mTitle  = ArrayList<String>()

        val destinations = ArrayList<String>()
        destinations.clear()

        destinations.add("Surabaya")
        destinations.add("Jakarta")
        destinations.add("Medan")
        destinations.add("Makasar")
        destinations.add("Medan")
        destinations.add("Jogaja")

        mStatus.add("Draft")
        mStatus.add("Waiting")
        mStatus.add("Waiting")
        mStatus.add("Partially Rejected")
        mStatus.add("Approved")
        mStatus.add("Partially Rejected")

        mTitle.add("Meeting in Yogyakarta")
        mTitle.add("Meeting in Bali")
        mTitle.add("Meeting in Surakarta")
        mTitle.add("Meeting in Jogja")
        mTitle.add("Training Development")
        mTitle.add("Meeting UAT")


        mStatus.forEachIndexed { index, s ->
            val mData = ApprovalModelAdapter()
            mData.status       = s
            mData.title        = mTitle.get(index)
            mData.tripCode     = "TP201802080003"
            mData.time         = "27 Mar - 31 Mar"
            mData.selected     = false
            mData.destination  = destinations.get(index)
            if (s.equals("Waiting")){
                mData.timeExperied = "9 days left to expired"
            }
            else{
                mData.timeExperied = ""
            }

            val mDataImage = ArrayList<ParticipantModelDomain>()

            if (index==3){
                val participant = ParticipantModelDomain()
                participant.name = names.get(0)
                participant.image = images.get(0)
                mDataImage.add(participant)
            }else{
                images.forEachIndexed { indexes, m ->
                    val participant = ParticipantModelDomain()
                    participant.name = names.get(indexes)
                    participant.image = m
                    mDataImage.add(participant)
                }
            }



            mData.participant  = mDataImage

            data.add(mData)
        }

        return data
    }

    fun addDataDraft2(type:String):ArrayList<AllApprovalModel>{
        val data = ArrayList<AllApprovalModel>()
        data.clear()

        return data
    }

    fun addDataDraft(type:String):ArrayList<ApprovalModelAdapter>{
        val data = ArrayList<ApprovalModelAdapter>()
        data.clear()

        val images = ArrayList<String>()
        images.add("https://i.ibb.co/yBMvJr3/Group-6.png")
        images.add("https://i.ibb.co/xG1PwjX/Row.png")

        val names = ArrayList<String>()
        names.add("dodik")
        names.add("Kurnia")

        val mStatus = ArrayList<String>()
        val mTitle  = ArrayList<String>()
        val mTripCode = ArrayList<String>()
        val mIsApprover = ArrayList<Boolean>()
        val mHeader  = ArrayList<String>()

        val destinations = ArrayList<String>()
        destinations.clear()

        destinations.add("Surabaya")
        destinations.add("Jakarta")
        destinations.add("Medan")
        destinations.add("Makasar")
        destinations.add("Medan")
        destinations.add("Jogaja")

        if (type=="draft"){
            mStatus.add("Draft")
            mStatus.add("Draft")
            mStatus.add("Draft")
        }
        else{
            mStatus.add("Completed")
            mStatus.add("Completed")
            mStatus.add("Completed")
        }

        mHeader.add("This Month")
        mHeader.add("April 2019")
        mHeader.add("May 2019")


        mTitle.add("Meeting IBM Workshop")
        mTitle.add("Traininng")
        mTitle.add("Internal Meeting")
//        mTitle.add("Customer Meeting")
//        mTitle.add("Training Development")
//        mTitle.add("Meeting UAT")

        mIsApprover.add(false)
        mIsApprover.add(true)
        mIsApprover.add(false)
        mIsApprover.add(false)
        mIsApprover.add(true)
        mIsApprover.add(false)

        mTripCode.add("TP2019001002")
        mTripCode.add("TP2019001003")
        mTripCode.add("TP2019004005")
        mTripCode.add("TP2019004005")
        mTripCode.add("TP2019004005")
        mTripCode.add("TP2019004005")


        mStatus.forEachIndexed { index, s ->
            val mData = ApprovalModelAdapter()
            mData.isApproval   = mIsApprover.get(index)
            mData.status       = s
            mData.header       = mHeader.get(index)
            mData.title        = mTitle.get(index)
            mData.tripCode     = mTripCode.get(index)
            mData.time         = "27 Mar - 31 Mar"
            mData.selected     = false
            mData.destination  = destinations.get(index)
            if (s.equals("Waiting")){
                mData.timeExperied = "9 days left to expired"
            }
            else{
                mData.timeExperied = ""
            }

            val mDataImage = ArrayList<ParticipantModelDomain>()

            if (index==3){
                val participant = ParticipantModelDomain()
                participant.name = names.get(0)
                participant.image = images.get(0)
                mDataImage.add(participant)
            }else{
                images.forEachIndexed { indexes, m ->
                    val participant = ParticipantModelDomain()
                    participant.name = names.get(indexes)
                    participant.image = m
                    mDataImage.add(participant)
                }
            }



            mData.participant  = mDataImage

            data.add(mData)
        }

        return data
    }
}