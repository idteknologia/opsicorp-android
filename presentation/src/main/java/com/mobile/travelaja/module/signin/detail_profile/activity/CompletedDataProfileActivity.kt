package com.mobile.travelaja.module.signin.detail_profile.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.base.InitApplications
import com.mobile.travelaja.locale.AppLocale
import com.mobile.travelaja.locale.LocaleManager
import com.mobile.travelaja.locale.LocalePrefrences
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.module.signin.detail_profile.presenter.CompletedDataProfilePresenter
import com.mobile.travelaja.module.signin.select.activity.LookUpActivity
import com.mobile.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.mobile.travelaja.utility.Constants
//import com.opsigo.opsicorp.module.login.select_nationality.activity.SelectNationalityActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.StringUtils
import kotlinx.android.synthetic.main.complete_data_profile_view.*
import kotlinx.android.synthetic.main.complete_data_profile_view.et_phone
import kotlinx.android.synthetic.main.complete_data_profile_view.tv_nationality
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackSetProfile
import opsigo.com.domainlayer.model.signin.ProfileModel
import org.json.JSONArray
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class CompletedDataProfileActivity: BaseActivity() {

    val presenter by inject<CompletedDataProfilePresenter> { parametersOf(this) }
    var RESULT_CODE_NATIONALITY = 78
    private var RESULT_CODE_LANGUANGE = 79
    var temp_country_code = ""
    var temp_country_name = ""

    override fun getLayout(): Int {
        hideStatusBar()
        return R.layout.complete_data_profile_view
    }

    override fun OnMain() {
        setDataProfile()
        setOnclickListener()
        if(Globals.getDataPreferenceBolean(this,"first")){
            gotoActivity(HomeActivity::class.java)
        }
    }

    private fun setDataProfile() {

        val dataProfile = getProfile()
        tv_greeting.text = "Hi, " + StringUtils().setUppercaseFirstLetter(dataProfile.firstName) + " " + StringUtils().setUppercaseFirstLetter(dataProfile.lastName)
        et_phone.setText(getProfile().phone)
        //tv_nationality_code.setText(getProfile().nationality)
        temp_country_code = getProfile().nationality
        tv_nationality.setText(getProfile().nationalityName)
        temp_country_name = getProfile().nationalityName

        Log.d("xCtrxx",":"+getProfile().nationalityName)
    }

    private fun setOnclickListener() {
        tv_nationality.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("emplaoyId","city")
            bundle.putString("titleHeader","Select City")
            gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,RESULT_CODE_NATIONALITY)
            //gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,RESULT_CODE_NATIONALITY)
        }

        tv_language.setOnClickListener {
            selectLanguageListener()
        }
    }

    private fun selectLanguageListener() {
        val bundle = Bundle()
        bundle.putString("emplaoyId","language")
        bundle.putString("titleHeader","Select Language")
        bundle.putBoolean(SelectNationalityActivity.SELECT,true)
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,RESULT_CODE_LANGUANGE)
    }

    fun nextListener(view: View){
        updateDataProfile()
    }

    private fun updateDataProfile() {
        var phone = et_phone.text.toString()

        setProfile(getToken(), phone, temp_country_code)

    }

    fun setProfile(token:String, mobilePhone:String, nationality: String) {

        showLoadingOpsicorp(true)

        var baseUrl = Globals.getBaseUrl(applicationContext)

        GetDataGeneral(baseUrl).setProfile(token, mobilePhone, nationality, object : CallbackSetProfile {

            override fun successLoad(isSuccess: Boolean) {

                var profile = getProfile()

                profile.nationality     = nationality
                profile.nationalityName = temp_country_name
                profile.phone           = mobilePhone

                Globals.setDataPreferenceString(applicationContext,"profile", Serializer.serialize(profile, ProfileModel::class.java))
                setDataProfile()

                hideLoadingOpsicorp()


                Globals.setDataPreferenceBolean(applicationContext,"first",true)
                Globals.setDataPreferenceBolean(applicationContext,"login",true)
                gotoActivity(HomeActivity::class.java)
                finish()
            }

            override fun failedLoad(message: String) {
                Log.d("proxx 11",": failed " + message )
                hideLoadingOpsicorp()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            RESULT_CODE_NATIONALITY ->{
                if (resultCode==Activity.RESULT_OK){
                    //tv_nationality_code.text = data?.getStringExtra("idCountry")
                    temp_country_code = data?.getStringExtra("countryCode").toString()
                    tv_nationality.text     = data?.getStringExtra("countryName")
                    temp_country_name       = data?.getStringExtra("countryName").toString()
                }
            }

            RESULT_CODE_LANGUANGE -> {
                if (resultCode == Activity.RESULT_OK){
                    tv_language.text = data?.getStringExtra("nameCountry")
                    setLanguage()
                }
            }
        }
    }

    private fun setLanguage(){
        val local = AppLocale.English
        LocalePrefrences.getInstance(InitApplications.appContext).selectedLocale = local
        LocaleManager.getInstance().setCurrentLocale(InitApplications.appContext, Locale(local.lang))

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            val localList = LocaleList(Locale.ENGLISH)
//            LocaleList.setDefault(localList)
//            resources.configuration.setLocales(localList)
//        }
    }

    override fun onBackPressed() {
        closeApplication()
    }
}