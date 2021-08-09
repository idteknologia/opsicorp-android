package com.mobile.travelaja.module.signin.splash.activity

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cunoraz.gifview.library.GifView
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.loading.LoadingDialog
import com.mobile.travelaja.module.signin.detail_profile.activity.CompletedDataProfileActivity
import com.mobile.travelaja.module.signin.login.activity.LoginActivity
import com.mobile.travelaja.module.signin.splash.presenter.SplashPresenter
import com.mobile.travelaja.module.signin.splash.view.SplashView
import com.mobile.travelaja.utility.CallbackSnackBar
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import opsigo.com.domainlayer.model.ConfigModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class SplashActivity :AppCompatActivity(),KoinComponent , SplashView{

    val presenter by inject<SplashPresenter> { parametersOf(this) }

    val loading = LoadingDialog()

    lateinit var config: ConfigModel
    var timeSplashDelay = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    val target = object :Target{
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Picasso.get()
                    .load(config.mobileLogo)
                    .into(this)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            Globals.setDataPreferenceString(this@SplashActivity,Constants.IMAGE_LOGO_SPLASH,Globals.BitMapToString(bitmap!!))
            logo.setImageBitmap(bitmap)
            getData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)

        val gifView1 = findViewById(R.id.gif1) as GifView
        gifView1.visibility = View.VISIBLE
        gifView1.gifResource = R.mipmap.ellipsis
        gifView1.play()

        Handler().postDelayed({
            timeSplashDelay = true
        }, 3000)

        config = Globals.getConfigCompany(this)
        val background = config.mobileBackgroundImage
        if (background.isNotEmpty()){
            if (cacheImageBackgroundSplash()){
                parent_layout_splash.setImageBitmap(Globals.StringToBitMap(Globals.getDataPreferenceString(this,Constants.IMAGE_BACKGROUND_SPLASH)))
                inVisibleLogo()
                getData()
            }
            else {
                val target = object :Target{
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        Log.e("TAG","load -----")
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        Log.e("TAG","failed -----")
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        Globals.setDataPreferenceString(this@SplashActivity,Constants.IMAGE_BACKGROUND_SPLASH,Globals.BitMapToString(bitmap!!))
                        parent_layout_splash.setImageBitmap(bitmap)
                        inVisibleLogo()
                        getData()
                    }
                }
                Picasso.get()
                        .load(background)
                        .into(target)
            }

        }
        else{
            visibleLogo()
        }

    }

    fun getData(){
        presenter.getData(getToken(), getModelPhone(), getUsername())
    }

    private fun cacheImageBackgroundSplash(): Boolean {
        return Globals.getDataPreferenceString(this,Constants.IMAGE_BACKGROUND_SPLASH).isNotEmpty()
    }
    private fun cacheImageLogoSplash(): Boolean {
        return Globals.getDataPreferenceString(this,Constants.IMAGE_LOGO_SPLASH).isNotEmpty()
    }

    private fun visibleLogo() {
        logo.visibility = View.VISIBLE
        title_splash.visibility = View.VISIBLE
        footer_splash_screen.visibility = View.VISIBLE

        if (cacheImageLogoSplash()){
            logo.setImageBitmap(Globals.StringToBitMap(Globals.getDataPreferenceString(this,Constants.IMAGE_LOGO_SPLASH)))
            getData()
        }
        else{
            if (config.mobileLogo.isNotEmpty()){
                Picasso.get()
                        .load(config.mobileLogo)
                        .into(target)
            } else {
                Picasso.get()
                        .load(config.imageUrl)
                        .into(target)
            }

        }
        if (config.mobileTextLogo.isNullOrEmpty()){
            title_splash.text = "Corporate Travel Management"
        } else {
            title_splash.text = config.mobileTextLogo
        }

        if (config.mobileBackgroundColor.isNullOrEmpty()){
            parent_layout.setBackgroundColor(Color.WHITE)
        } else {
            parent_layout.setBackgroundColor(Color.parseColor(config.mobileBackgroundColor))
        }

        if (config.mobileTextColorLogo.isNullOrEmpty()){
            title_splash.setTextColor(ContextCompat.getColor(applicationContext, R.color.green_price))

        } else {
            title_splash.setTextColor(Color.parseColor(config.mobileTextColorLogo))
        }
    }


    private fun inVisibleLogo() {
        logo.visibility = View.INVISIBLE
        title_splash.visibility = View.INVISIBLE
        footer_splash_screen.visibility = View.INVISIBLE
    }

    fun getToken():String{
        return Globals.getDataPreferenceString(this,"token")
    }

    fun getModelPhone():String{
        return Build.MANUFACTURER + " " + Build.MODEL
    }

    fun getUsername():String{
        return Globals.getDataPreferenceString(this,"username")
    }

    override fun loadingData() {
        showLoadingOpsicorp(true)
    }

//    fun getModelPhone():String{
//        return Build.MANUFACTURER + " " + Build.MODEL
//    }

//    fun getUsername():String{
//        return Globals.getDataPreferenceString(this,"username")
//    }

//    override fun loadingData() {
//        showLoadingOpsicorp(true)
//    }


    fun showLoadingOpsicorp(disable:Boolean){
        loading.showDialogLoading(this,disable)
    }

    override fun successLoadData(){
        if (timeSplashDelay){
            gotoActivity(CompletedDataProfileActivity::class.java)
            finish()
        }
        else {
            Handler().postDelayed({
                successLoadData()
            }, 1000)
        }

    }


    override fun failedGetData(loadData:String) {
        showSnackbar(parent_layout,object : CallbackSnackBar{
            override fun onclikRetry() {
                when(loadData){
                    "profile"->{
                        presenter.getDataProfile(getToken())
                    }
                    "purphose"->{
                        presenter.getDataPurphose(getToken())
                    }
                    "budget"->{
                        presenter.getDataBudget(getToken())
                    }
                    "city"->{
                        presenter.getDataCity(getToken())
                    }
                    "activity"->{
                        presenter.getDataActivity(getToken())
                    }


                    "getAllAirline" -> {
                        presenter.getAllAirLine(getToken())
                    }
//                    "country" -> {
//                        presenter.getDataCountry(getToken())
//                    }
                    "station_train" -> {
                        presenter.getDataStation(getToken())
                    }
                    "setDeviceId" -> {
                        Log.d("xsetdevice","01")
                        presenter.setDeviceId(getToken(), getModelPhone(),Globals.getDataPreferenceString(this@SplashActivity,Constants.FCM_TOKEN), getUsername())
                    }
                }

            }
        })
    }

    fun gotoActivity(clas : Class<*>?){
        startActivity(Intent(this,clas))
    }

    fun gotoActivityResult(clas : Class<*>?,code:Int){
        startActivityForResult(Intent(this,clas),code)
    }

    fun hideLoadingOpsicorp(){
        try {
            loading.dismiss()
        }catch (e: Exception){

        }
    }

    fun showSnackbar(viewParent: View, calback: CallbackSnackBar){
        val snackbar = Snackbar
                .make(viewParent, getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        calback.onclikRetry()
                    }
                })

        snackbar.setActionTextColor(Color.RED)
        val sbView = snackbar.getView()
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.YELLOW)
        snackbar.show()
    }

    override fun cancelApp() {
        Globals.clearCache(this)
        onBackPressed()
    }

    override fun updateApp() {
        var url = "https://play.google.com/store/apps/details?id=" + getPackageName()
        val uri = Uri.parse(url)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            url = "market://details?id=" + getPackageName();
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)))
        }
    }

    fun showLoadingOpsicowrp(disable:Boolean){
        loading.showDialogLoading(this,disable)
    }

    override fun showDialogUpdate(message: String, string: String) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.dialog_ok_only_2, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialog)
        val tvYes = dialog.findViewById(R.id.tvYES) as TextView
        tvYes.text = "OK"
        val tvNo = dialog.findViewById(R.id.tvNO) as TextView
        //tvNo.visibility = View.GONE
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        tvTitle.text = title
        val tvDesc = dialog.findViewById(R.id.tvDesc) as TextView
        tvDesc.text = string

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        val btnClose = dialog.findViewById(R.id.layClose) as LinearLayout
        btnClose.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                alertDialog.cancel()
                updateApp()
            }
        })

        tvYes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                alertDialog.cancel()
                updateApp()
            }
        })

        tvNo.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                alertDialog.cancel()
                cancelApp()
            }
        })
    }

    override fun tokenExpired() {
        logoutListener()
    }

    fun logoutListener(){
        Globals.setDataPreferenceBolean(this,"login",false)
        Globals.setDataPreferenceBolean(this,"first",false)

        Globals.setDataPreferenceString(this,"login_user","")
        Globals.setDataPreferenceString(this,"token","")
        Globals.setDataPreferenceString(this,"username", "")

        gotoActivity(LoginActivity::class.java)
        finish()
    }

}
