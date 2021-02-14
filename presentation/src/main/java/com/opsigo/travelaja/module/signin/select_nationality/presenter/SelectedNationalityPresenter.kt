package com.opsigo.travelaja.module.signin.select_nationality.presenter

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.signin.select_nationality.adapter.SelectNationalityAdapter
import com.opsigo.travelaja.module.signin.select_nationality.view.SelectNationalityView
import com.opsigo.travelaja.utility.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SelectedNationalityPresenter :KoinComponent {

    val context     : Context
    val view        : SelectNationalityView
    val data = ArrayList<SelectNationalModel>()
    val temp = ArrayList<SelectNationalModel>()
    var filterActivated = false
//    val adapter  by lazy { LookUpAdapter(getDataLogin)}
    val adapter by inject<SelectNationalityAdapter> { parametersOf(data) }

    constructor(context: Context, view: SelectNationalityView) {
        this.context = context
        this.view = view
    }

    fun initRecyclerView(recyclerView:RecyclerView){
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        val scrollListener = RecyclerViewLoadMoreScrollListener(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        recyclerView.addOnScrollListener(scrollListener);

        adapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        if (filterActivated){
                            view.callbackFromThisActivity(temp.get(position).name,temp[position].id)
                        }
                        else {
                            view.callbackFromThisActivity(data.get(position).name,data[position].id)
                        }
                    }
                }
            }
        })
    }

    fun getDataNationality(){
        data.clear()
        Constants.DATA_NATIONAL.forEach {
            val model = SelectNationalModel()
            model.name = it.name
            model.id   = it.id
            data.add(model)
        }
        adapter.setData(data)
    }

    fun filterDataAdapter(string: String) {
        val temp = ArrayList<SelectNationalModel>()
        for (n in 0..data.size-1) {
            val d = data.get(n)
            if (d.name.toLowerCase().contains(string.toLowerCase())) {
                val mData = SelectNationalModel()
                mData.name = d.name
                mData.id   = d.id
                temp.add(mData)
            }
        }
        adapter.setData(temp)
        this.temp.clear()
        this.temp.addAll(temp)
    }

    fun getDataPurpose() {
        data.clear()
//        data.add("Event")
//        data.add("Board meeting")
//        data.add("Conference")
//        data.add("Holiday")
//        data.add("Launch meeting")
//        data.add("Seles meeting")
//        data.add("Training")
        DataTemporary.dataPurphose.forEachIndexed { index, purposeModel ->
            val mData = SelectNationalModel()
            mData.name = purposeModel.value
            data.add(mData)
        }
        adapter.setData(data)
    }

    fun getDataStationTrain(){
        data.clear()
        Constants.DATA_TRAIN_STASION.forEach {
            val model = SelectNationalModel()
            model.id   = it.code
            model.name = it.nameCity
            data.add(model)
        }
        adapter.setData(data)
    }

    fun getDataCity() {
        data.clear()
        data.addAll(Constants.DATA_CITY)
        adapter.setData(data)

    }

    fun getDataSelectBudged(){
        data.clear()
//        data.add("Budget KSP")
//        data.add("Budget SND")
//        data.add("Budget Branch")
        data.addAll(DataTemporary.dataSelectBudget)
        adapter.setData(data)
    }

    fun getDataCostCenter(){
        data.clear()
//        data.add("KSP rawamangun")
//        data.add("KSP karawang")
//        data.add("KSP Denpasar")
        data.addAll(DataTemporary.dataCostCenter)
        adapter.setData(data)
    }

    fun getDataReasonCode() {
        data.clear()
        for ( i in 0..3){
            val mData = SelectNationalModel()
            mData.name = "CZ - Fare option not available"
            mData.id   = (i+1).toString()
            data.add(mData)
        }
    }

    fun getDataLanguage() {
        data.clear()
        val mData = SelectNationalModel()
        mData.name = "English"
        mData.id   = "01"
        data.add(mData)
        val mData1 = SelectNationalModel()
        mData1.name = "Indonesia"
        mData1.id   = "02"
        data.add(mData1)
    }


}