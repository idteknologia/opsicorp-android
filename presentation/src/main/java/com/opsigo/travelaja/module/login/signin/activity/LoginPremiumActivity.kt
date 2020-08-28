package com.opsigo.travelaja.module.login.signin.activity

import android.util.Log
import android.view.View
import org.koin.core.inject
import com.opsigo.travelaja.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Globals
import org.koin.core.parameter.parametersOf
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.domainlayer.callback.CallbackConfig
import android.text.method.PasswordTransformationMethod
import opsigo.com.domainlayer.model.signin.DataLoginModel
import com.opsigo.travelaja.module.login.signin.view.LoginView
import kotlinx.android.synthetic.main.login_activity_view_travel_aja.*
import com.opsigo.travelaja.module.login.splash.activity.SplashActivity
import com.opsigo.travelaja.module.login.signin.presenter.LoginPresenter

class LoginPremiumActivity : BaseActivity(),
        LoginView,View.OnClickListener {

    override fun getLayout(): Int { return R.layout.login_activity_view }

    val presenter by inject<LoginPresenter> { parametersOf(this) }

    override fun OnMain() {
        presenter.checkLogin()
        onClickListener()
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance())
    }

    private fun onClickListener() {
        btnSignIn.setOnClickListener(this)
        eye_password.setOnClickListener(this)
    }

    private fun getDataLogin() {
        showDialog("")
        GetDataLogin(getBaseUrl()).getDataLogin(et_username.text.toString(),et_password.text.toString(),object : CallbackLogin {
            override fun successGetData(data: DataLoginModel) {
                //initFirebase()
                saveDataLogin(data)
                getConfig("Bearer "+data.token)
            }

            override fun failedGetData(message: String) {
                hideDialog()
                failedWarning(message)
            }
        })
    }

    private fun getConfig(token: String) {
        GetDataLogin(getBaseUrl()).getDataConfig(token,object : CallbackConfig {
            override fun failedLoad(message: String) {

            }

            override fun successLoad(data: ConfigModel) {
                val dataConfig = data
                Log.d("xconfigx","onloginact : " + dataConfig.isShowCreateTripOnMobile)
                Globals.setDataPreferenceString(this@LoginPremiumActivity,"config",Serializer.serialize(data, ConfigModel::class.java))
                gotoSplashScreen()
                hideDialog()
            }
        })
    }


    private fun failedWarning(message:String) {
        Globals.showAlert("Failed",message,this)
    }

    private fun saveDataLogin(data: DataLoginModel) {
        Globals.setDataPreferenceString(this,"login_user",Serializer.serialize(data, DataLoginModel::class.java))
        Globals.setDataPreferenceString(this,"token","Bearer "+data.token)
        Globals.setDataPreferenceString(this,"username", data.userName)
    }

    override fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
        finish()
    }

    override fun onClick(p0: View?) {
        when(p0){
            btnSignIn -> {
                getDataLogin()
            }
            eye_password -> {
                presenter.visibilityPasswordListener(eye_password, et_password, this)
            }
        }
    }

}