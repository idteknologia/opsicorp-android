package com.opsigo.travelaja.module.home.presenter

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.module.home.adapter.TourEventAdapter
import com.opsigo.travelaja.module.home.adapter.UpcommingFlightAdapter
import com.opsigo.travelaja.module.home.model.TourEventModel
import com.opsigo.travelaja.module.home.model.UpcommingFlightModel
import com.opsigo.travelaja.module.home.view.HomeView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class HomePresenter : KoinComponent{

    var context             : Context
    val view                : HomeView
    var data                = ArrayList<TourEventModel>()

    var dataUpcommingFlight = ArrayList<UpcommingFlightModel>()
    val adapterTourEventAdapter by inject<TourEventAdapter> { parametersOf(data) }
    val adapterUpcommingFlightAdapter by inject<UpcommingFlightAdapter> { parametersOf(dataUpcommingFlight) }

    constructor(context: Context, view: HomeView) {
        this.context = context
        this.view = view
    }

//    fun initSlider() {
//        var image = "https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/DCCF7C4B-5D18-4C6E-8DD0-EC142FD7039E.png"
//        Picasso.get()
//                .load(image)
//                .fit()
//                .centerCrop()
//                .into(image_header)
//    }

//    private fun addDataDummy(slider: SliderLayout) {
//        val textSliderView = TextSliderView(context)
//        val nextSliderView = TextSliderView(context)
//        textSliderView
//                .description("")
//                .image("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/85DAB019-A8C2-411E-ACFF-0DE29F056464.png")
//                .setScaleType(BaseSliderView.ScaleType.Fit)
//                .setOnSliderClickListener(this)
//
//        textSliderView.bundle(Bundle())
//        textSliderView.bundle.putString("extra", "")
//        slider.addSlider(textSliderView)
//
//        nextSliderView
//                .description("")
//                .image("https://4.bp.blogspot.com/-sWczc8ZKQHg/WILbmXLwXsI/AAAAAAAAYh4/TfIrnEtPjpsFUYIJb0qUIqUT6VXLolCaQCLcB/s640/umroh-plus-eropa.jpg")
//                .setScaleType(BaseSliderView.ScaleType.Fit)
//                .setOnSliderClickListener(this)
//
//        textSliderView.bundle(Bundle())
//        textSliderView.bundle.putString("extra", "")
//        slider.addSlider(nextSliderView)
//
//        slider.setPresetTransformer(SliderLayout.Transformer.Fade)
//        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
//        slider.setDuration(3500)
//        slider.addOnPageChangeListener(this)
//    }
//
//    override fun onPageScrollStateChanged(state: Int) {
//
//    }
//
//    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//    }
//
//    override fun onPageSelected(position: Int) {
//
//    }
//
//    override fun onSliderClick(slider: BaseSliderView?) {
//
//    }

    fun presenterInitRecyclerAdapter(recyclerViewFamilyTime: RecyclerView, recyclerViewUpCommingFlight: RecyclerView) {

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        recyclerViewFamilyTime.layoutManager = layoutManager
        recyclerViewFamilyTime.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerViewFamilyTime.adapter = adapterTourEventAdapter

        adapterTourEventAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    1 ->{

                    }
                }
            }
        })

        val layoutManagerFlight = LinearLayoutManager(context)
        layoutManagerFlight.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewUpCommingFlight.layoutManager = layoutManagerFlight
        recyclerViewUpCommingFlight.itemAnimator = DefaultItemAnimator()
        recyclerViewUpCommingFlight.adapter = adapterUpcommingFlightAdapter

        adapterUpcommingFlightAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 -> {

                    }
                }
            }
        })

        getDataTourEvent()
        getDataUpcommingFlight()
    }

    private fun getDataUpcommingFlight() {
        dataUpcommingFlight.clear()
        var upcommingData = UpcommingFlightModel()
        upcommingData.airportName = "Jakarta - Surabaya"
        upcommingData.daysNumber  = "27"
        upcommingData.daysText    = "SUN"
        upcommingData.mount       = "Mar"
        upcommingData.time        = "14:00"
        upcommingData.today       = "Today"
        upcommingData.typeTour    = "AWS Workshop Part 2"

        var upcommingData1 = UpcommingFlightModel()
        upcommingData1.airportName = "Jakarta - Denpasar"
        upcommingData1.daysNumber  = "11"
        upcommingData1.daysText    = "WED"
        upcommingData1.mount       = "Apr"
        upcommingData1.time        = "10:00"
        upcommingData1.today       = "Sunday"
        upcommingData1.typeTour    = "Meeting"
        dataUpcommingFlight.add(upcommingData)
        dataUpcommingFlight.add(upcommingData1)
        adapterUpcommingFlightAdapter.setData(dataUpcommingFlight)
    }

    fun getDataTourEvent(){
        view.loadData()

        val dataImageTour = ArrayList<String>()
        val dataNameCountry = ArrayList<String>()
        dataImageTour.clear()
        dataNameCountry.clear()
        dataNameCountry.add("Spain")
        dataNameCountry.add("Maroco")
        dataNameCountry.add("France")

        dataImageTour.add("https://opstatic.blob.core.windows.net/img/opsicorp/special_promo/Spain.jpg")
        dataImageTour.add("https://opstatic.blob.core.windows.net/img/opsicorp/special_promo/Morocco.jpg")
        dataImageTour.add("https://opstatic.blob.core.windows.net/img/opsicorp/special_promo/Perancis.jpg")

        //dummy getDataLogin
        data.clear()
        dataImageTour.forEachIndexed { index, dataImage ->
            var mData = TourEventModel()
            mData.imageTour = dataImage
            mData.country   = dataNameCountry[index]
            mData.dateTour  = "on April 2018"
            mData.idEvent   = "1"
            mData.pricing   = "Rp 2${index}.000"

            data.add(mData)
        }

        adapterTourEventAdapter.setData(data)
        checkDataEmpety()
    }

    private fun checkDataEmpety() {
        if (data.isNotEmpty()){
            view.successLoadData()
        }
        else {
            view.failedLoadDataFamilyTime()
        }
    }

}