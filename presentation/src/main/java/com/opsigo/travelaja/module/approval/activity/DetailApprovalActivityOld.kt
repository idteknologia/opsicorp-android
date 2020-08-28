package com.opsigo.travelaja.module.approval.activity

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.approval.summary.*
import opsigo.com.domainlayer.model.summary.ItemFlightModel
import opsigo.com.domainlayer.model.summary.ItemHotelModel
import opsigo.com.domainlayer.model.summary.ItemTrainModel
import opsigo.com.domainlayer.model.summary.SummaryModelItems
import kotlinx.android.synthetic.main.layout_detail_approval.*

class DetailApprovalActivityOld: BaseActivity(){

    override fun getLayout(): Int { return R.layout.layout_detail_approval }

    val data = ArrayList<SummaryModelItems>()
    val adapter by lazy { SummaryAdapter(this) }

    override fun OnMain() {
        initRecyclerView()
        addDataDummy()

    }

    private fun addDataDummy() {

        data.clear()

        val mData = SummaryModelItems()
        val dataTrain  = ItemTrainModel()
        dataTrain.imageTrain  = "https://i.ibb.co/5Wv9ksW/Screen-Shot-2019-08-27-at-13-34-15.png"
        dataTrain.titleTrain  = "Argo Parahyangan"
        dataTrain.carrierNumber      = "K02"
        dataTrain.oriDest     = "Jakarta (GMR) - Bandung (BD) "
        dataTrain.status           = "Reserved"
        dataTrain.timeArrival      = "08:00"
        dataTrain.timeDeparture    = "06:40 "
        dataTrain.dateDeparture             = "Sun, 27 Mar 2018"
        dataTrain.pnrCode              = "AW5CRP"
        dataTrain.price            = "IDR 110.000"

        mData.typeCard = "TRAIN"
        mData.dataItemTrain = dataTrain

        val mData2 = SummaryModelItems()
        val dataTrain2  = ItemFlightModel()
        dataTrain2.imageFlight = "https://i.ibb.co/C0XzT6K/sriwijaya.png"
        dataTrain2.titleFlight = "Sriwijaya"
        dataTrain2.flightNumber     = "SJ-0412"
        dataTrain2.airportDeparture = "Jakarta (CGK) - Yogyakarta (JOG)"
        dataTrain2.status           = "Reserved"
        dataTrain2.timeArrival      = "08:00"
        dataTrain2.timeDeparture    = "07:09"
        dataTrain2.dateArrival      = "Sun, 27 Mar 2018"
        dataTrain2.pnrCode          = "ATGSHJ"
        dataTrain2.price            = "IDR 400.000/pax"

        mData2.typeCard = "FLIGHT"
        mData2.dataItemFlight = dataTrain2

        val mData3 = SummaryModelItems()
        val dataTrain3  = ItemHotelModel()
        dataTrain3.image       = "https://i.ibb.co/h84vVdS/grand-aston-3.png"
        dataTrain3.status      = "Reserved"
        dataTrain3.nameHotel   = "Grand Aston Yogyakarta"
        dataTrain3.address     = "Jl. Urip Sumoharjo No 30 \n" + "Sleman - Yogyakarta"
        dataTrain3.dateBooking = "Sun, 27 Mar 2018"
        dataTrain3.starRating  = ""
        dataTrain3.price       = "IDR 695.800/room"

        mData3.typeCard = "HOTEL"
        mData3.dataItemHotel = dataTrain3

        val mDataHeaderFlight = SummaryModelItems()
        val mDataHeaderHotel  = SummaryModelItems()
        val mDataHeaderTrain  = SummaryModelItems()

        mDataHeaderFlight.typeCard = "HEADER"
        mDataHeaderHotel.typeCard  = "HEADER"
        mDataHeaderTrain.typeCard  = "HEADER"

        mDataHeaderFlight.title = "1. Flight Summary"
        mDataHeaderHotel.title  = "2. Hotel Summary"
        mDataHeaderTrain.title  = "3. Train Summary"

        data.add(mDataHeaderFlight)
        data.add(mData2)
        data.add(mDataHeaderHotel)
        data.add(mData3)
        data.add(mDataHeaderTrain)
        data.add(mData)

        adapter.setData(data)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_summary.layoutManager = layoutManager
        rv_summary.itemAnimator = DefaultItemAnimator()
        rv_summary.adapter = adapter

    }


}