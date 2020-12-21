package com.opsigo.travelaja.module.create_trip.newtrip.presenter

import com.opsigo.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.create_trip.newtrip.view.CreateTripView
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import opsigo.com.domainlayer.model.PurposeModel
import opsigo.com.domainlayer.model.BudgetModel
import android.support.v7.widget.RecyclerView
import org.koin.core.parameter.parametersOf
import opsigo.com.domainlayer.callback.*
import com.opsigo.travelaja.utility.*
import org.koin.core.KoinComponent
import android.content.Context
import com.opsigo.travelaja.R
import org.koin.core.inject

class CreateTripPresenter(val context: Context, val view: CreateTripView) :KoinComponent {
    var dataAttachment  = ArrayList<UploadModel>()
    val adapter  by inject<AttachmentAdapter> { parametersOf(dataAttachment)  }
    val baseUrl :String

    init {
        baseUrl = Globals.getBaseUrl(context)
    }

    fun setDataAutomatically() {
        if (DataTemporary.dataPurphose.isEmpty()){
            getDataPurphose()
        }
        else if(DataTemporary.dataSelectBudget.isEmpty()){
            getDataBudget()
        }
        getDataCity()
    }

    private fun getDataBudget() {
        GetDataTripPlane(baseUrl).getDataBudget(Globals.getToken(),Globals.getProfile(context).employId,Globals.getConfigCompany(context).defaultTravelAgent,object : CallbackBudget {
            override fun successLoad(data: ArrayList<BudgetModel>) {
                DataTemporary.dataSelectBudget.clear()
                data.forEachIndexed { index, budgetModel ->
                    val mData = SelectNationalModel()
                    mData.name = budgetModel.value
                    mData.id   = budgetModel.id
                    DataTemporary.dataSelectBudget.add(mData)
                }
                initView()
            }

            override fun failedLoad(message: String) {
            }
        })

    }

    fun initView(){
        val startDateView = Globals.getDateAfterNow(2,"dd MMM yyyy")//FormatingMonthIndonesian().format(Globals.getDateAfterNow(2))
        val endDateView   = Globals.getDateAfterNow(6,"dd MMM yyyy")//FormatingMonthIndonesian().format(Globals.getDateAfterNow(6))
        val startDate     = DateConverter().getAfterDay("yyyy-MM-dd",2)
        val endDate       = DateConverter().getAfterDay("yyyy-MM-dd",6)
        val city          = Constants.DATA_CITY.filter { it.name.toLowerCase().contains("bandung") }.first() //"SURABAYA (SUB)"

        view.setDataAutomatically(startDateView,endDateView,city.name,city.id,startDate,endDate)
    }

    private fun getDataCity() {
        view.loadDataView()
        GetDataTripPlane(baseUrl).getDataCity(Globals.getToken(),object :CallbackArrayListCity{
            override fun failedLoad(message: String) {
                view.failedLoadDataView()
            }

            override fun successLoad(data: ArrayList<SelectNationalModel>) {
                Constants.DATA_CITY = data
                initView()
                view.successLoadDataView()
            }
        })
    }

    private fun getDataPurphose() {
        GetDataTripPlane(baseUrl).getDataPurpose(Globals.getToken(),object :CallbackPurpose{
            override fun successLoad(data: ArrayList<PurposeModel>) {
                DataTemporary.dataPurphose.clear()
                DataTemporary.dataPurphose.addAll(data)
                getDataCity()
            }

            override fun failedLoad(message: String) {

            }

        })
    }

    fun initRecyclerViewAttachment(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        adapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when (views){
                    R.id.image_delet -> {
                        dataAttachment.removeAt(position)
                        adapter.setData(dataAttachment)
                    }
                    R.id.tv_failed -> {
                        uploadImage(position)
                    }
                }
            }
        })
    }

    fun addDataAttactment(imagePath: String) {
        val splitName = imagePath.split("/")
        val mData = UploadModel()
        mData.pathOriginalLocalImage = imagePath
        mData.pathLocalImage = splitName.get(splitName.size-1)
        mData.statusUploaded = "load"
        dataAttachment.add(mData)
        adapter.setData(dataAttachment)
        uploadImage(dataAttachment.size-1)
    }

    fun createTripNow() {
        view.SuccessCreateTrip()
    }

    fun dataDokumentUploaded(): ArrayList<UploadModel> {
        return dataAttachment
    }

    fun uploadImage(position: Int){
        GetDataTripPlane(baseUrl).uploadFile(Globals.getToken(),Globals.getImageFile(context,dataAttachment[position].pathOriginalLocalImage,"file"),object : CallbackUploadFile {
            override fun successLoad(data: UploadModel) {
                dataAttachment[position].url = data.url
                dataAttachment[position].nameImage = data.nameImage
                dataAttachment[position].statusUploaded = "success"
                adapter.notifyItemChanged(position)
            }

            override fun failedLoad(message: String) {
                dataAttachment[position].statusUploaded = "failed"
                adapter.notifyItemChanged(position)
            }
        })
    }

}