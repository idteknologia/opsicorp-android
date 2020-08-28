package com.opsigo.travelaja.module.accomodation.tour.model

import com.opsigo.travelaja.module.home.model.TourEventModel

class DataTourEventDummy {

    fun dataDummy():ArrayList<TourEventParentModel>{
        val data = ArrayList<TourEventParentModel>()
        val mDataName = ArrayList<String>()
        mDataName.add("Family")
        mDataName.add("Relaxation")
        mDataName.add("Sport & Hobbies")
        val mDataImage = ArrayList<String>()
        mDataImage.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/E97B3AE6-EB18-4A4C-A338-49D3DDD3233F.png")
        mDataImage.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/BA169E02-A16E-433C-9363-C98695481160.png")
        mDataImage.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/95136C8D-CC47-4668-AA61-4794EFC4CCC6.png")

        mDataName.forEachIndexed { index, s ->
            val mData = TourEventParentModel()
            val mDataChild = ArrayList<TourEventModel>()

            mDataImage.forEachIndexed { i, m ->
                val mData = TourEventModel()
                mData.imageTour = m
                mData.country   = "Indonesia"
                mData.dateTour  = "on April 2018"
                mData.idEvent   = "${i+1}"
                mData.pricing   = "Rp 20.000"

                mDataChild.add(mData)
            }

            mData.name  = s
            mData.id    = "1"
            mData.dataList = mDataChild

            data.add(mData)
        }

        return data
    }
}