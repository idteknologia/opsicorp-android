package com.opsigo.travelaja.module.home.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
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
import com.opsigo.travelaja.module.approval.activity.DetailTripActivity
import com.opsigo.travelaja.module.home.activity.HomeWebActivity
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity.CreateTripPertaminaActivity
import com.opsigo.travelaja.module.home.presenter.DefaultViewModelFactory
import com.opsigo.travelaja.module.home.presenter.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.header_home.*
import kotlinx.android.synthetic.main.home_fourth_content.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_item_upcomming.view.*
import kotlinx.android.synthetic.main.home_schedule_content.*
import kotlinx.android.synthetic.main.home_second_content.*
import kotlinx.android.synthetic.main.home_third_content.*
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.network.MyURL
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftPersonalRequest
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import opsigo.com.domainlayer.model.trip.Trip
import opsigo.com.domainlayer.model.trip.TripResult
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import java.util.HashMap

class HomeFragment : BaseFragment(), KoinComponent, HomeView, View.OnClickListener {
    lateinit var viewModel: HomeViewModel
    private var isFake = false
    val presenter by inject<HomePresenter> { parametersOf(this) }

    override fun getLayout(): Int {
        isFake = arguments?.getBoolean("isFake") ?: false
        return R.layout.home_fragment
    }

    var positionImageSlider = -1

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        initHeader()
        setViewOnclickListener()
//        initShowcase()
//        initSlider()
        //Todo hide
        blinkCartAnimation()
        presenter.presenterInitRecyclerAdapter(rv_tour, rv_upcomming_flight)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(isFake, requireContext())).get(HomeViewModel::class.java)
        viewModel.getTrip()
        viewModel.trip.observe(viewLifecycleOwner) {
            setScheduleTripContent(it)
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            contentButtonSchedule.isVisible = isError
            contentItemSchedule.isVisible = !isError
        }
        contentButtonSchedule.setOnClickListener((context) as View.OnClickListener)
    }

    private fun setScheduleTripContent(result: TripResult) {
        val list = result.data
        var i = 0
        tvViewAll.isVisible = list.size > 2
        itemSchedule2.isVisible = list.size > 1
        contentItemSchedule.isVisible = list.isNotEmpty()
        contentButtonSchedule.isVisible = list.isEmpty()
        tvViewAll.setOnClickListener((context) as View.OnClickListener)
        list.forEach {
            i++
            if (i == 1) {
                setScheduleTripFirst(it, itemSchedule1)
            }
            if (i == 2) {
                setScheduleTripFirst(it, itemSchedule2)
            }
        }
    }

    private fun setScheduleTripFirst(trip: Trip, itemView: View) {
        itemView.setOnClickListener {
            openDetailTrip(trip)
        }
        itemView.tvDate.text = trip.getDateNumber()
        itemView.tvMonth.text = trip.getMonth()
        itemView.tvPurposeTrip.text = trip.purpose
        itemView.tvDestination.text = trip.destinationName
        if (trip.isToday()) {
            itemView.tvDateSchedule.text = "Today"
            itemView.tvDateSchedule.setTextColor(Color.parseColor("#d0021b"))
        } else {
            itemView.tvDateSchedule.text = trip.getDay()
            itemView.tvDateSchedule.setTextColor(Color.parseColor("#009688"))
        }
    }

    private fun openDetailTrip(trip: Trip) {
        val intent = Intent(context, DetailTripActivity::class.java)
        intent.putExtra(Constants.KEY_FROM, Constants.FROM_LIST_DASBOARD)
        intent.putExtra(Constants.KEY_INTENT_TRIPID, trip.Id)
        intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, trip.Code)
        startActivity(intent)
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

        for (i in 1 until 6) {
            val mData = SliderImageModel()
            mData.id = 1
            mData.urlImage = "https://opstatic.blob.core.windows.net/img/opsicorp/slider/slider${i}.png"

            mDataSlider.add(mData)
        }

        lay_slider.setData(mDataSlider)
        lay_slider.callbackSlider(object : SliderImageOpsicorp.OnclickButtonListener {
            override fun onCenter(position: Int) {
                positionImageSlider = position
            }
        })
    }

    private fun initHeader() {
        val dataProfile = getProfile()
        tv_greeting.text = "Hi, ${formatName(dataProfile).substring(0, 1).toUpperCase()}${formatName(dataProfile).substring(1, formatName(dataProfile).length).toLowerCase()}"
        if (dataProfile.imageUrl.isNotEmpty())
            Picasso.get().load(dataProfile.imageUrl)
                    .into(img_profile)
        if (dataProfile.name.isNotEmpty()) {
            tvAlphabetName.text = generateAlphabetName(dataProfile.name)
        }
        setTypeAccount()
        setImageContent()
    }

    private fun setTypeAccount() {
        if (Globals.getBaseUrl(requireContext()) == MyURL.URL_TRAVELAJA) {
            tvAccountType.text = getString(R.string.txt_travelaja_basic)
            tvAccountType.setTextColor(Color.parseColor("#da2128"))
            textView3.isEnabled = false
            textView3.setTextColor(Color.parseColor("#EFEFEF"))
        } else {
            tvAccountType.text = getString(R.string.txt_premium_account)
            tvAccountType.setTextColor(Color.parseColor("#009688"))
            ivProfile.visibility = View.VISIBLE
        }
    }

    private fun generateAlphabetName(fullName: String): String {
        val first = fullName.split(" ")
        return if (first.size >= 2) {
            "${first[0].first()}${first[1].firstOrNull() ?: ""}"
        } else {
            "${first[0].firstOrNull()}"
        }
    }

    private fun setImageContent() {
        itemContentSunset.setContent("Sunset Gili Laba", R.drawable.bg_gili_laba){
            openWebActivity("https://tic.wonderin.id/destination/nusa-tenggara-timur/mengenang-sunset-di-gili-laba")
        }
        itemContentPesona.setContent("Pesona Pantai Pink", R.drawable.bg_pesona){
            openWebActivity("https://tic.wonderin.id/destination/nusa-tenggara-timur/pesona-pantai-pink-taklukkan-para-wisatawan-yang-datang")
        }
        itemContentEksotisme.setContent("Eksotisme Labuan Bajo", R.drawable.bg_eksotisme){
            openWebActivity("https://tic.wonderin.id/destination/nusa-tenggara-timur/jelajahi-eksotisme-labuan-bajo")
        }
        itemContentKeindahan.setContent("Keindahan Manta Point", R.drawable.bg_keindahan){
            openWebActivity("https://tic.wonderin.id/destination/nusa-tenggara-timur/keindahan-manta-point-surganya-para-pecinta-diving")
        }
    }

    private fun formatName(dataProfile: ProfileModel): String {
        var name = ""
        if (dataProfile.name.length > 15) {
            if ((dataProfile.name.split(" ")[1].length + dataProfile.name.split(" ")[0].length) < 15) {
                name = dataProfile.name.split(" ")[0] + " " + dataProfile.name.split(" ")[1]
            } else {
                name = dataProfile.name.split(" ")[0]
            }
        } else {
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

    override fun failedLoadUpcommingFlight() {
        line_title_upcomming_flight.visibility = View.GONE
    }

    private fun businessTrip() {
        val dataConfig = getConfig()
        if (dataConfig.isShowCreateTripOnMobile) {
            if (getProfile().companyCode.equals(11)){
                gotoActivity(CreateTripPertaminaActivity::class.java)
            }
            gotoActivity(CreateTripActivity::class.java)
        } else {
            showContactAdmin()
        }
        /*gotoActivity(CreateTripPertaminaActivity::class.java)*/
    }

    override fun onClick(v: View?) {
        when (v) {
            img_bisnis_trip -> {
                // Todo bisnis trip
                businessTrip()
            }
            llBisnisTrip -> {
                businessTrip()
            }
            img_flight -> {
                chekExistPersonalTrip(Constants.TYPE_FLIGHT)
            }
            img_train -> {
                chekExistPersonalTrip(Constants.TYPE_TRAIN)
            }
            img_hotel -> {
                chekExistPersonalTrip(Constants.TYPE_HOTEL)
            }

            btnFlight -> {
                chekExistPersonalTrip(Constants.TYPE_FLIGHT)
            }
            btnTrain -> {
                chekExistPersonalTrip(Constants.TYPE_TRAIN)
            }
            btnHotel -> {
                chekExistPersonalTrip(Constants.TYPE_HOTEL)
            }
            img_tour -> {
                setContruction()
            }
            line_cart -> {
                gotoCart()
            }
            img_cart -> {
                gotoCart()
            }
        }
    }

    private fun chekExistPersonalTrip(type: Int) {
        showLoadingOpsicorp(true)
        GetDataTripPlane(getBaseUrl()).checkExistTripPersonal(getToken(), object : CallbackString {
            override fun successLoad(data: String) {
                if (data.isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putString(Constants.FROM_CART,Constants.FROM_PERSONAL_TRIP)
                    bundle.putString(Constants.ID_PERSONAL_TRIP, data)
                    gotoActivityWithBundle(NewCartActivity::class.java, bundle)
                } else {
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
        GetDataTripPlane(getBaseUrl()).saveAsDraftTripPlantPersonal(getToken(), dataPersonalTrip(), object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data)
                val bundle = Bundle()
                bundle.putInt(Constants.TYPE_ACCOMODATION, type)
                gotoActivityWithBundle(AccomodationActivity::class.java, bundle)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                setToast(message)
            }
        })
    }

    private fun dataPersonalTrip(): HashMap<String, Any> {
        val request = SaveAsDraftPersonalRequest()
        request.destination = "-"
        request.origin = "-"
        request.purpose = "-"
        request.travelAgentAccount = Globals.getConfigCompany(requireContext()).defaultTravelAgent
        return Globals.classToHasMap(request, SaveAsDraftPersonalRequest::class.java)
    }

    private fun gotoCart() {
        val dataConfig = getConfig()
        if (dataConfig.isShowCreateTripOnMobile) {
            val bundle = Bundle()
            bundle.putString(Constants.FROM_CART,Constants.FROM_HOME)
            gotoActivityWithBundle(NewCartActivity::class.java,bundle)
        } else {
            showContactAdmin()
        }
    }


    fun setContruction() {
        showDialogContruction(false)
    }

    fun showContactAdmin() {
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

        btnFlight.setOnClickListener(this)
        btnHotel.setOnClickListener(this)
        btnTrain.setOnClickListener(this)
        llBisnisTrip.setOnClickListener(this)
        tvWonderfull.setOnClickListener {
            openWebActivity("https://wonderin.id/")
        }
    }

    private fun openWebActivity(url : String){
        val intent = Intent(requireContext(),HomeWebActivity::class.java)
        intent.putExtra(HomeWebActivity.URL,url)
        requireContext().startActivity(intent)
    }

}