package com.mobile.travelaja.module.home.activity

import android.app.Activity
import android.view.View
import com.mobile.travelaja.R
import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import opsigo.com.domainlayer.callback.*
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.base.BaseActivity
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import opsigo.com.domainlayer.model.HolidayModel
import kotlinx.android.synthetic.main.home_view.*
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import com.mobile.travelaja.module.profile.ProfileFragment
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.mobile.travelaja.module.home.fragment.HomeFragment
import com.mobile.travelaja.module.manage_trip.TestManageTripFragment
import com.mobile.travelaja.module.approval.fragment.ApprovalFragment
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.mobile.travelaja.module.item_custom.menu_bottom.MenuBottomOpsicorp
import com.mobile.travelaja.module.my_booking.home_my_booking.MyBookingFragment
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible

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
    var REQUEST_CODE_CALENDAR = 76
    var UPDATE_APP_REQUEST_CODE = 1020

    private var isSelectApprovedAll = false
    private var appUpdateManager: AppUpdateManager? = null

    var positionPage = 0

    override fun OnMain() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()

        initMenuBottom(menu_bottom)
        menu_bottom.callbackAccomodationMenuBottom(this)
        initPageCallback()

        try {
            checkUserLogin()
        }catch (e:Exception){
            e.printStackTrace()
        }

        if (Globals.readJsonFromFile(this,Constants.FILE_NAME_DATA_HOLIDAY).isEmpty()){
            getDataHoliday()
        }

        if (Globals.readJsonFromFile(this,Constants.FILE_NAME_DATA_COUNTRY_HOTEL).isEmpty()){
            getDataCountryHotel()
        }

        if (intent.getBooleanExtra(Constants.FROM_PAYMENT,false)){
            four()
        }
    }

    private fun checkUpdate(){
        val task = appUpdateManager?.appUpdateInfo

        task?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                setLog("Update Available")
                appUpdateManager!!.registerListener(listener)
                appUpdateManager?.startUpdateFlowForResult(
                    appUpdateInfo,AppUpdateType.FLEXIBLE,this,UPDATE_APP_REQUEST_CODE
                )
            } else {
                setLog("No Update Available")
            }
        }
        task?.addOnFailureListener {
            setLog(it.message.toString())
        }
    }

    private fun appUpdateInProgress(){
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            /*if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                setLog("App update in progress")
                appUpdateManager?.startUpdateFlowForResult(
                    appUpdateInfo,AppUpdateType.FLEXIBLE,this,UPDATE_APP_REQUEST_CODE
                )
            }*/
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                showSnackBarForCompleteUpdate()
            }
        }
    }

    private val listener: InstallStateUpdatedListener = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == InstallStatus.DOWNLOADING) {
            val bytesDownloaded = installState.bytesDownloaded()
            val totalBytesToDownload = installState.totalBytesToDownload()
            llProgress.visible()
            progressBar.visible()
            setLog(bytesDownloaded.toString())
            setLog(totalBytesToDownload.toString())
        } else if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            llProgress.gone()
            progressBar.gone()
            showSnackBarForCompleteUpdate()
        }
    }

    private fun showSnackBarForCompleteUpdate() {
        appUpdateManager?.unregisterListener(listener)
        Snackbar.make(
            findViewById(R.id.rootView),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager?.completeUpdate() }
            setActionTextColor(resources.getColor(R.color.white))
            show()
        }.show()
    }

    override fun onResume() {
        super.onResume()
        appUpdateInProgress()
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
        imageTitle.add(getString(R.string.home))
        imageTitle.add(getString(R.string.title_manage_trip))
        imageTitle.add(getString(R.string.approval))
        imageTitle.add(getString(R.string.my_booking))
        imageTitle.add(getString(R.string.profile))
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
            REQUEST_CODE_CALENDAR -> {
                myBookingFragment.onActivityResult(requestCode,resultCode,data)
            }
            Constants.OPEN_DETAIL_TRIP_PLANE -> {
                approvalFragment.onActivityResult(requestCode,resultCode,data)
            }
            UPDATE_APP_REQUEST_CODE -> {
                when(resultCode){
                    Activity.RESULT_OK -> {
                        setLog("Update Success")
                    }
                    Activity.RESULT_CANCELED -> {
                        setLog("Update canceled")
                    }
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                        setLog("Error while updating the app")
                        checkUpdate()
                    }
                }
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