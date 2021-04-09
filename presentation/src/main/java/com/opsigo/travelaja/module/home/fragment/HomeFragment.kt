package com.opsigo.travelaja.module.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.view_accomodation.activity.AccomodationActivity
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.CreateTripActivity
import com.opsigo.travelaja.module.home.presenter.HomePresenter
import com.opsigo.travelaja.module.home.view.HomeView
import com.opsigo.travelaja.module.item_custom.slider.SliderImageModel
import com.opsigo.travelaja.module.item_custom.slider.SliderImageOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.header_home.*
import kotlinx.android.synthetic.main.home_fragment.*
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftPersonalRequest
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import java.util.HashMap


class HomeFragment : BaseFragment(),KoinComponent, HomeView, View.OnClickListener{

    val presenter by inject<HomePresenter> { parametersOf(this) }

    override fun getLayout(): Int { return R.layout.home_fragment }

    var positionImageSlider = -1

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        initShowcase()
        setViewOnclickListener()
        initSlider()
        initView()
        blinkCartAnimation()
        presenter.presenterInitRecyclerAdapter(rv_tour,rv_upcomming_flight)
    }

    private fun initShowcase() {
        val config = ShowcaseConfig()
        config.delay = 500 // half second between each showcase view


        val sequence = MaterialShowcaseSequence(activity, Constants.SHOWCASE_ID)

        sequence.setConfig(config)

        sequence.addSequenceItem(img_bisnis_trip,
                "This is button one", "GOT IT")

        sequence.addSequenceItem(img_flight,
                "This is button two", "GOT IT")


        sequence.start()
    }

    private fun blinkCartAnimation() {
        Globals.blinkImageAnimation(blink_image)
    }

    private fun initSlider() {
        val mDataSlider = ArrayList<SliderImageModel>()
        mDataSlider.clear()

        for (i in 1 until 6){
            val mData = SliderImageModel()
            mData.id = 1
            mData.urlImage = "https://opstatic.blob.core.windows.net/img/opsicorp/slider/slider${i}.png"

            mDataSlider.add(mData)
        }

        lay_slider.setData(mDataSlider)
        lay_slider.callbackSlider(object :SliderImageOpsicorp.OnclickButtonListener{
            override fun onCenter(position: Int) {
                positionImageSlider = position
            }
        })


    }

    private fun initView() {
        val dataProfile = getProfile()
        tv_greeting.text = "Hi, ${formatName(dataProfile).substring(0,1).toUpperCase()}${formatName(dataProfile).substring(1,formatName(dataProfile).length).toLowerCase()}"
    }

    private fun formatName(dataProfile: ProfileModel): String {
        var name = ""
        if (dataProfile.name.length>15){
            if ((dataProfile.name.split(" ")[1].length+dataProfile.name.split(" ")[0].length)<15){
                name = dataProfile.name.split(" ")[0]+" "+dataProfile.name.split(" ")[1]
            }
            else{
                name = dataProfile.name.split(" ")[0]
            }
        }
        else{
            name = dataProfile.name
        }
        return name
    }

    override fun loadData() {

    }

    override fun successLoadData() {

    }

    override fun failedLoadDataFamilyTime() {
        line_title_family_time.visibility = View.GONE
    }

    override fun failedLoadUpcommingFlight(){
        line_title_upcomming_flight.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when (v){
            img_bisnis_trip->{

                val dataConfig = getConfig()
                if(dataConfig.isShowCreateTripOnMobile){
                    gotoActivity(CreateTripActivity::class.java)
                }else{
                    showContactAdmin()
                }

            }
            img_flight->{
                chekExistPersonalTrip(Constants.TYPE_FLIGHT)
            }
            img_train ->{
                chekExistPersonalTrip(Constants.TYPE_TRAIN)
            }
            img_hotel->{
                chekExistPersonalTrip(Constants.TYPE_HOTEL)
            }
            img_tour ->{
                setContruction()
            }
            line_cart->{
                gotoCart()
            }
            img_cart -> {
                gotoCart()
            }
        }
    }

    private fun chekExistPersonalTrip(type:Int) {
        showLoadingOpsicorp(true)
        GetDataTripPlane(getBaseUrl()).checkExistTripPersonal(getToken(),object :CallbackString{
            override fun successLoad(data: String) {
                if (data.isNotEmpty()){
                    val bundle = Bundle()
                    bundle.putString(Constants.ID_PERSONAL_TRIP,data)
                    gotoActivityWithBundle(NewCartActivity::class.java,bundle)
                }
                else {
                    createPersonalTrip(type)
                }
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                setToast(message)
            }
        })

    }

    private fun createPersonalTrip(type: Int) {
        GetDataTripPlane(getBaseUrl()).saveAsDraftTripPlantPersonal(getToken(),dataPersonalTrip(),object :CallbackSaveAsDraft{
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data)
                val bundle = Bundle()
                bundle.putInt(Constants.TYPE_ACCOMODATION,type)
                gotoActivityWithBundle(AccomodationActivity::class.java,bundle)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                setToast(message)
            }
        })
    }

    private fun dataPersonalTrip(): HashMap<String, Any> {
        val request= SaveAsDraftPersonalRequest()
        request.destination = "-"
        request.origin      = "-"
        request.purpose     = "Personal Trip"
        request.travelAgentAccount = Globals.getConfigCompany(context!!).defaultTravelAgent
        return Globals.classToHasMap(request,SaveAsDraftPersonalRequest::class.java)
    }

    private fun gotoCart() {
        val dataConfig = getConfig()
        Log.d("xconfigx",": " + dataConfig.isShowCreateTripOnMobile)
        if(dataConfig.isShowCreateTripOnMobile){

            Constants.ID_BOOKING_TEMPORARY = ""
            gotoActivity(NewCartActivity::class.java)

        }else{
            showContactAdmin()
        }
    }


    fun setContruction(){
        showDialogContruction(false)
    }

    fun showContactAdmin(){
        showDialogContactAdmin(false)
    }

    private fun setViewOnclickListener() {
        img_flight.setOnClickListener(this)
        img_train.setOnClickListener(this)
        img_hotel.setOnClickListener(this)
        img_tour.setOnClickListener(this)
        img_bisnis_trip.setOnClickListener(this)

        line_cart.setOnClickListener(this)
        img_cart.setOnClickListener(this)
    }

}