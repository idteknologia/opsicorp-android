package com.mobile.travelaja.module.create_trip.newtrip.presenter

import com.mobile.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.mobile.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.domainlayer.model.PurposeModel
import opsigo.com.domainlayer.model.BudgetModel
import org.koin.core.parameter.parametersOf
import opsigo.com.domainlayer.callback.*
import com.mobile.travelaja.utility.*
import org.koin.core.KoinComponent
import android.content.Context
import com.mobile.travelaja.R
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel
import org.koin.core.inject
import java.io.File

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
        } else if (DataTemporary.dataActivity.isEmpty()){
            getDataActivity()
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
//        view.setDataAutomatically2(startDateView,endDateView,startDate,endDate)
    }

    private fun getDataCity() {
        view.loadDataView()
        GetDataTripPlane(baseUrl).getDataCity(Globals.getToken(),object :CallbackListCityTrip{
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

    private fun getDataActivity() {
        GetDataTravelRequest(baseUrl).getTypeActivity(Globals.getToken(),object :CallbackTypeActivity{
            override fun success(data: ArrayList<TypeActivityTravelRequestModel>) {
                DataTemporary.dataActivity.clear()
                DataTemporary.dataActivity.addAll(data)
            }

            override fun failed(message: String) {
            }

        })
    }

    fun initRecyclerViewAttachment(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
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

    fun addDataAttactment(imagePath: String,file:File,type : String?) {
        val splitName = imagePath.split("/")
        val mData = UploadModel()
        mData.pathOriginalLocalImage = imagePath
        mData.pathLocalImage = splitName.get(splitName.size-1)
        mData.statusUploaded = "load"
        mData.file           = file
        mData.type           = type
        dataAttachment.add(mData)
        adapter.setData(dataAttachment)
        uploadImage(dataAttachment.lastIndex)
    }

    fun createTripNow() {
        view.SuccessCreateTrip()
    }

    fun dataDokumentUploaded(): ArrayList<UploadModel> {
        return dataAttachment
    }

    fun uploadImage(position: Int){
        val uri = dataAttachment[position].pathOriginalLocalImage
        val type = dataAttachment[position].type ?: "image/jpeg"
        val part = Utils.createMultipart(type,uri)

        GetDataTripPlane(baseUrl).uploadFile(Globals.getToken(),part,object : CallbackUploadFile {
            override fun successLoad(data: UploadModel) {
                dataAttachment[position].url = data.url
                dataAttachment[position].nameImage = data.nameImage
                dataAttachment[position].statusUploaded = "success"
                adapter.notifyItemChanged(position)
            }

            override fun failedLoad(message: String) {
                dataAttachment[position].statusUploaded = "failed"
                adapter.notifyItemChanged(position)
                Globals.setToast(message,context)
            }
        })
    }

}