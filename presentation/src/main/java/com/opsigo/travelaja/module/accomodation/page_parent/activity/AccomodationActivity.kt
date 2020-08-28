package com.opsigo.travelaja.module.accomodation.page_parent.activity

import com.opsigo.travelaja.module.accomodation.page_parent.presenter.AccomodationPresenter
import com.opsigo.travelaja.module.accomodation.flight.search.FlightFragment
import com.opsigo.travelaja.module.accomodation.page_parent.view.AccomodationView
import com.opsigo.travelaja.module.item_custom.menu_bottom.MenuBottomOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.module.accomodation.hotel.parent.HotelFragment
import com.opsigo.travelaja.module.accomodation.train.search.TrainFragment
import com.opsigo.travelaja.module.accomodation.tour.TourFragment
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import kotlinx.android.synthetic.main.accomodation_activity.*
import com.opsigo.travelaja.module.profile.ProfileFragment
import org.koin.core.parameter.parametersOf
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import android.view.WindowManager
import android.widget.FrameLayout
import android.util.TypedValue
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.opsigo.travelaja.R
import org.koin.core.inject
import java.lang.Exception
import android.view.View
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Constants.READ_REQUEST_LOCATION
import com.opsigo.travelaja.utility.Constants.TYPE_ACCOMODATION
import com.opsigo.travelaja.utility.Constants.TYPE_FLIGHT
import com.opsigo.travelaja.utility.Constants.TYPE_HOTEL
import com.opsigo.travelaja.utility.Constants.TYPE_TOUR
import com.opsigo.travelaja.utility.Constants.TYPE_TRAIN

class AccomodationActivity : BaseActivity() ,AccomodationView,ToolbarOpsicorp.OnclickButtonListener, MenuBottomOpsicorp.OnclickButtonListener{
    override fun getLayout(): Int { return R.layout.accomodation_activity }

    val presenter by inject<AccomodationPresenter> { parametersOf(this) }
    var flightFragment = FlightFragment()
    var hotelFragment  = HotelFragment()
    var tourFragment   = TourFragment()
    var trainFragment  = TrainFragment()
    var profileFragment        = ProfileFragment()

    var positionPage        = 0
    var FLIGHT_POSITION     = 1
    var HOTEL_POSITION      = 2
    var TOUR_POSITION       = 3
    var TRAIN_POSITION      = 4
    //var PROFILE_POSITION    = 5
    var data = 0

    override fun OnMain() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getDataIntent()
        initToolbar()
    }

    private fun getDataIntent() {
        try {
            data = intent.getBundleExtra("data").getInt(TYPE_ACCOMODATION)
            if (Constants.KEY_ACCOMODATION.equals(data)){
                Globals.BisnisTrip = true
                btn_bottom_accomodation.visibility = View.VISIBLE
                initButtonBottom()
            }
            else if(TYPE_FLIGHT.equals(data)){
                Globals.BisnisTrip = false
                getFlightFragment()
                btn_bottom_accomodation.visibility = View.GONE
            }
            else if(TYPE_TRAIN.equals(data)){
                Globals.BisnisTrip = false
                getTrainFragment()
                btn_bottom_accomodation.visibility = View.GONE
            }
            else if(TYPE_HOTEL.equals(data)){
                Globals.BisnisTrip = false
                getHotelFragment()
                btn_bottom_accomodation.visibility = View.GONE
            }
            else if(TYPE_TOUR.equals(data)){
                Globals.BisnisTrip = false
                getTourFragment()
                btn_bottom_accomodation.visibility = View.GONE
            }
        }catch (e:Exception){
            e.printStackTrace()
            btn_bottom_accomodation.visibility = View.VISIBLE
            initButtonBottom()
        }

    }

    fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
    }

    fun initButtonBottom() {

        btn_bottom_accomodation.goneFive()

        btn_bottom_accomodation.setBackgroundLine(R.color.white)
        presenter.setDataButton(btn_bottom_accomodation)
        btn_bottom_accomodation.callbackAccomodationMenuBottom(this)
        btn_bottom_accomodation.setButtonSelectedPosition(0)

    }

    override fun one() {
        getFlightFragment()
//        setContruction()
    }

    override fun two() {
//        setContruction()
        getHotelFragment()
    }

    override fun three() {
        //getFlightFragment()
        getTrainFragment()
    }

    override fun four() {
//        setContruction()
        getTourFragment()
    }

    override fun five() {

    }

//    override fun five() {
////        setContruction()
//        getProfileFragment()
//    }

    fun setContruction(){
        showDialogContruction(false)
    }

    override fun btnBack() {
        backListerner()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        gotoActivity(NewCartActivity::class.java)
    }

    private fun getTourFragment() {
//        toolbar.setTitleBar("Tour or Event")
        toolbar.visibility = View.GONE
        loadFragment(tourFragment,place_fragment.id)
        positionPage = TOUR_POSITION
    }

    private fun getTrainFragment() {
        Log.d("xcekx",": here trainn " )
        toolbar.visibility = View.VISIBLE
        toolbar.setTitleBar("Find Train")
        loadFragment(trainFragment,place_fragment.id)
        positionPage = TRAIN_POSITION
    }

    private fun getHotelFragment() {
        toolbar.visibility = View.VISIBLE
        toolbar.setTitleBar("Find Hotel")
        loadFragment(hotelFragment,place_fragment.id)
        positionPage = HOTEL_POSITION
    }

    private fun getFlightFragment() {
        Log.d("xcekx",": here flightx " )
        toolbar.visibility = View.VISIBLE
        toolbar.setTitleBar("Find Flight")
        loadFragment(flightFragment,place_fragment.id)
        positionPage = FLIGHT_POSITION
    }

    override fun loadingGetData() {

    }

    override fun getData() {

    }

    override fun successGetData() {

    }

    override fun failedGetData() {

    }

    fun setMarginFramlayout(){
        val sizeInDP = 50
        val marginInDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInDP.toFloat(), resources
                .displayMetrics).toInt()
        val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(0, 0, 0, marginInDp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CalendarViewOpsicorp().REQUEST_CODE_CALENDAR->{
               when(positionPage){
                   FLIGHT_POSITION ->{
                       flightFragment.onActivityResult(requestCode, resultCode, data)
                   }
                   HOTEL_POSITION  ->{
                       hotelFragment.onActivityResult(requestCode, resultCode, data)
                   }
                   TOUR_POSITION   ->{
                       hotelFragment.onActivityResult(requestCode, resultCode, data)
                   }
                   TRAIN_POSITION  ->{
                       trainFragment.onActivityResult(requestCode, resultCode, data)
                   }
               }
            }

        }
    }

    override fun onBackPressed() {
        backListerner()
    }

    private fun backListerner() {
        if (!"accomodation".equals(data)){
            finish()
        }
        else{
            Globals.showAlert("Maaf","Apakah anda yakin ingin meninggalkan halaman ini?",this,object :Globals.OnclikAllert{
                override fun yes() {
                    finish()
                }

                override fun no() {

                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            READ_REQUEST_LOCATION -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    hotelFragment.checkPermissionLocation()
                }
            }
        }

    }

}