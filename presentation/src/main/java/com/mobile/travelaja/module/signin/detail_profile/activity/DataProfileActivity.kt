package com.mobile.travelaja.module.signin.detail_profile.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.signin.detail_profile.presenter.CompletedDataProfilePresenter
//import com.opsigo.opsicorp.module.login.select_nationality.activity.SelectNationalityActivity
import com.mobile.travelaja.module.signin.select.activity.LookUpActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.StringUtils
import kotlinx.android.synthetic.main.data_profile_view.*
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackSetProfile
import opsigo.com.domainlayer.model.signin.ProfileModel
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class DataProfileActivity: BaseActivity() {

    val presenter by inject<CompletedDataProfilePresenter> { parametersOf(this) }
    var RESULT_CODE_NATIONALITY = 78
    var temp_country_code = ""
    var temp_country_name = ""

    override fun getLayout(): Int {
        return R.layout.data_profile_view
    }

    override fun OnMain() {
        setDataProfile()
        setOnclickListener()
//        if(Globals.getDataPreferenceBolean(this,"first")){
//            gotoActivity(HomeActivity::class.java)
//        }
    }

    private fun setDataProfile() {
        val dataProfile = getProfile()
        tv_name.text = "Hi, " + StringUtils().setUppercaseFirstLetter(dataProfile.firstName) + " " + StringUtils().setUppercaseFirstLetter(dataProfile.lastName)
        et_phone.setText(getProfile().phone)
        tv_nationality_code.setText(getProfile().nationality)
        temp_country_code = getProfile().nationality
        tv_nationality.setText(getProfile().nationalityName)
        temp_country_name = getProfile().nationalityName

        Log.d("xCtrxx",":"+getProfile().nationalityName)
    }

    private fun updateDataProfile() {
        var phone = et_phone.text.toString()

        setProfile(getToken(), phone, temp_country_code)

    }

    private fun setOnclickListener() {
        tv_nationality.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("emplaoyId","country")
            bundle.putString("titleHeader","Nationality")
            bundle.putString("hint","Enter country name")
            gotoActivityResultWithBundle(LookUpActivity::class.java,bundle,RESULT_CODE_NATIONALITY)
        }

    }

    fun saveListener(view: View){
        updateDataProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            RESULT_CODE_NATIONALITY ->{
                if (resultCode==Activity.RESULT_OK){
                    tv_nationality_code.text = data?.getStringExtra("idCountry")
                    temp_country_code = data?.getStringExtra("idCountry").toString()
                    tv_nationality.text     = data?.getStringExtra("nameCountry")
                    temp_country_name       = data?.getStringExtra("nameCountry").toString()
                }
            }
        }
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

            }

            override fun failedLoad(message: String) {
                Log.d("proxx 11",": failed " + message )
                hideLoadingOpsicorp()
            }
        })

    }

//    fun getToken():String{
//        return Globals.getDataPreferenceString(this,"token")
//    }

//    override fun onBackPressed() {
//        closeApplication()
//    }
}