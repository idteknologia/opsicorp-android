package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.dialog

import android.content.Context
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.DialogPurposeAdapter
import com.opsigo.travelaja.utility.*
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class DialogPurposePresenter : KoinComponent {

    val context: Context
    val view: DialogPurposeView
    val data = ArrayList<SelectNationalModel>()
    val temp = ArrayList<SelectNationalModel>()
    var filterActivated = false

    val adapter by inject<DialogPurposeAdapter> { parametersOf(data) }

    constructor(context: Context, view: DialogPurposeView) {
        this.context = context
        this.view = view
    }

    fun initRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.adapter = adapter

        val scrollListener = RecyclerViewLoadMoreScrollListener(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        recyclerView.addOnScrollListener(scrollListener);

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views) {
                    -1 -> {
                        Globals.delay(1000,object : Globals.DelayCallback{
                            override fun done() {
                                if (filterActivated) {
                                    view.callbackFromThisActivity(temp.get(position).name, temp[position].id)
                                } else {
                                    view.callbackFromThisActivity(data.get(position).name, data[position].id)
                                }
                            }
                        })
                    }
                }
            }
        })
    }


    fun filterDataAdapter(string: String) {
        val temp = ArrayList<SelectNationalModel>()
        for (n in 0..data.size - 1) {
            val d = data.get(n)
            if (d.name.toLowerCase().contains(string.toLowerCase())) {
                val mData = SelectNationalModel()
                mData.name = d.name
                mData.country = d.country
                mData.id = d.id
                temp.add(mData)
            }
        }
        adapter.setData(temp)
        this.temp.clear()
        this.temp.addAll(temp)
    }

    fun getDataPurpose() {
        data.clear()
        DataTemporary.dataPurphose.forEachIndexed { index, purposeModel ->
            val mData = SelectNationalModel()
            mData.name = purposeModel.value
            data.add(mData)
        }
        adapter.setData(data)
    }

    fun getDataActivity() {
        data.clear()
        DataTemporary.dataActivity.mapIndexed { index, typeActivityTravelRequestModel ->
            val mData = SelectNationalModel()
            mData.name = typeActivityTravelRequestModel.purpose
            data.add(mData)
        }
        adapter.setData(data)
    }


    fun getDataCity() {
        data.clear()
        data.addAll(Constants.DATA_CITY)
        adapter.setData(data)

    }

    fun getDataSelectBudged() {
        data.clear()
        data.addAll(DataTemporary.dataSelectBudget)
        adapter.setData(data)
    }

    fun getDataCostCenter() {
        data.clear()
        data.addAll(DataTemporary.dataCostCenter)
        adapter.setData(data)
    }

    fun getDataReasonCode() {
        data.clear()
        for (i in 0..3) {
            val mData = SelectNationalModel()
            mData.name = "CZ - Fare option not available"
            mData.id = (i + 1).toString()
            data.add(mData)
        }
    }



}