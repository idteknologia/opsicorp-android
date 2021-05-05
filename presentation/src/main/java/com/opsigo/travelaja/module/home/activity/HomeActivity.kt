package com.opsigo.travelaja.module.home.activity

import android.view.View
import android.content.Intent
import com.opsigo.travelaja.R
import androidx.core.os.bundleOf
import opsigo.com.domainlayer.callback.*
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import opsigo.com.domainlayer.model.HolidayModel
import kotlinx.android.synthetic.main.home_view.*
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import com.opsigo.travelaja.module.profile.ProfileFragment
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.opsigo.travelaja.module.home.fragment.HomeFragment
import com.opsigo.travelaja.module.manage_trip.TestManageTripFragment
import com.opsigo.travelaja.module.approval.fragment.ApprovalFragment
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.item_custom.menu_bottom.MenuBottomOpsicorp
import com.opsigo.travelaja.module.my_booking.home_my_booking.MyBookingFragment

class HomeActivity : BaseActivity(),MenuBottomOpsicorp.OnclickButtonListener , View.OnClickListener{

    override fun getLayout(): Int {
        return R.layout.home_view }

    var imageDefaults       = ArrayList<Int>()
    var imageSelected       = ArrayList<Int>()
    var imageTitle          = ArrayList<String>()
    var profileFragment     = ProfileFragment()
    var approvalFragment    = ApprovalFragment()
    var homeFragment        = HomeFragment()
    var manageTripFragment  = TestManageTripFragment()
    var myBookingFragment   = MyBookingFragment()

    private var isSelectApprovedAll = false

    var positionPage = 0

    override fun OnMain() {
        initMenuBottom(menu_bottom)
        menu_bottom.callbackAccomodationMenuBottom(this)
        initPageCallback()

        try {
            checkUserLogin()
        }catch (e:Exception){
            e.printStackTrace()
        }
//        setLog(Globals.getWitdhAndHeightLayout(this).first.toString()+" "+Globals.getWitdhAndHeightLayout(this).second)

        if (Globals.readJsonFromFile(this,Constants.FILE_NAME_DATA_HOLIDAY).isEmpty()){
            getDataHoliday()
        }

        if (Globals.readJsonFromFile(this,Constants.FILE_NAME_DATA_COUNTRY_HOTEL).isEmpty()){
            getDataCountryHotel()
        }

//        setLog("---------------------")
//        setLog(Globals.getDataPreferenceString(this,Constants.KEY_DATA_CODE_AIRPORT))
    }

    private fun getDataCountryHotel() {
        if (Globals.getDataPreferenceString(this,Constants.COUNTRY_HOTEL).isEmpty()){
            GetDataAccomodation(getBaseUrl()).getSearchCountry(getToken(),Globals.getConfigCompany(this).defaultTravelAgent,object :CallbackArrayListCountry{
                override fun successLoad(data: ArrayList<SelectNationalModel>) {
                    Globals.setDataPreferenceString(this@HomeActivity,Constants.COUNTRY_HOTEL,Serializer.serialize(data))
                }

                override fun failedLoad(message: String) {

                }
            })
        }
    }

    fun getDataHoliday(){
        GetDataGeneral("http://mobapi.svc.opsinfra.net/").getHolidayApi("2020","ID",object : CallbackHoliday {
            override fun success(transform: ArrayList<HolidayModel>) {
                Constants.LIST_DATA_HOLIDAY = transform
            }

            override fun failed(string: String) {

            }
        })
    }

    private fun initPageCallback() {

        myBookingFragment.callback(object : MyBookingFragment.CallbackPressed{
            override fun back() {
                one()
            }
        })
    }

    private fun checkUserLogin() {
        val dataUser = getProfile()
        if (dataUser.isApproval){
            menu_bottom.setButtonSelectedPosition(0)
        }
        else{
            menu_bottom.setButtonSelectedPosition(0)
        }
    }

    fun initMenuBottom(menuBottom: MenuBottomOpsicorp) {
        setDataButton()
        menuBottom.setBackgroundLine(R.color.white)
        menuBottom.setDataImage(imageDefaults)
        menuBottom.setDataImageSelected(imageSelected)
        menuBottom.setDataTitle(imageTitle)
    }

    private fun setDataButton() {
        imageDefaults.clear()
        imageDefaults.add(R.drawable.ic_home)
        imageDefaults.add(R.drawable.ic_manage_trip)
        imageDefaults.add(R.drawable.bottom_bar_approval)
        imageDefaults.add(R.drawable.ic_booking)
        imageDefaults.add(R.drawable.ic_profile)

        imageSelected.clear()
        imageSelected.add(R.drawable.ic_home_selected)
        imageSelected.add(R.drawable.manage_trip_default)
        imageSelected.add(R.drawable.bottom_bar_approval_green)
        imageSelected.add(R.drawable.ic_booking_selected)
        imageSelected.add(R.drawable.ic_profile_selected)

        imageTitle.clear()
        imageTitle.add("Home")
        imageTitle.add("Manage trip")
        imageTitle.add("Approval")
        imageTitle.add("My Booking")
        imageTitle.add("Profile")
    }

    fun btnPersonalTrip(view: View){

    }

    override fun one() {
        showHomeFragment()
    }

    override fun two() {
        showManageTripFragment()
    }

    override fun three() {
        showApprovalFragment()
    }

    override fun four() {
        showMyBookingFragment()
    }

    override fun five() {
        showProfileFragment()
    }

    fun showHomeFragment(){
        positionPage = 1
        loadFragment(homeFragment,R.id.place_fragment)
    }

    fun showManageTripFragment(){
        positionPage = 2
        loadFragment(manageTripFragment,R.id.place_fragment)
    }

    fun showMyBookingFragment(){
        positionPage  = 4
        loadFragment(myBookingFragment,R.id.place_fragment)
    }

    fun showApprovalFragment(){
        approvalFragment.arguments = bundleOf(ApprovalFragment.OPEN_APPROVAL to isSelectApprovedAll)
        positionPage = 3
        loadFragment(approvalFragment,R.id.place_fragment)
        isSelectApprovedAll = false
    }

    fun showProfileFragment(){
        positionPage = 5
        loadFragment(profileFragment,R.id.place_fragment)
    }

    override fun onBackPressed() {
        when(positionPage){

            1 ->{
                closeApplication()
            }

            2 ->{
                firstPage()
            }

            3 ->{
                approvalFragment.backPressed(object : ApprovalFragment.CallbackPressed{
                    override fun back() {
                        firstPage()
                    }
                })
            }

            4 ->{
                firstPage()
            }

            5 ->{
                firstPage()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constants.OPEN_DETAIL_TRIP_PLANE -> {
                approvalFragment.onActivityResult(requestCode,resultCode,data)
            }
        }
    }

    private fun firstPage() {

        one()
        menu_bottom.setButtonSelectedPosition(0)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.contentButtonSchedule){
            menu_bottom.setButtonSelectedPosition(2)
        }else if (v?.id == R.id.tvViewAll){
            isSelectApprovedAll = true
            menu_bottom.setButtonSelectedPosition(2)
        }
    }


}