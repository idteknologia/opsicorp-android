package com.mobile.travelaja.module.signin.register

import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.domainlayer.callback.CallbackConfig
import opsigo.com.domainlayer.model.signin.DataLoginModel
import kotlinx.android.synthetic.main.complitely_register_view.*
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.network.MyURL
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.signin.CompletelyRegisterRequest
import java.util.HashMap

class ComplitelyRegisterActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.complitely_register_view
    }

    var email = ""
    var otp   = ""
    var id    = ""

    override fun OnMain() {
        parsingDataIntent()
        initOnclickListener()
    }

    private fun parsingDataIntent() {
        email = intent?.getBundleExtra("data")?.getString("Email").toString()
        otp   = intent?.getBundleExtra("data")?.getString("Otp").toString()
        id    = intent?.getBundleExtra("data")?.getString("id").toString()
    }

    private fun initOnclickListener() {
        btnSignIn.callbackOnclickButton(this)
        btn_back.setOnClickListener { finish() }
    }

    override fun onClicked() {
        if (Globals.validatiEdittext(getDataField())){
            getRegister()
        }
        else {
            failedWarning(getString(R.string.warning_canot_be_empty))
        }
    }

    private fun getDataField(): java.util.ArrayList<String> {
        val data = ArrayList<String>()
        data.add(et_username.text.toString())
        data.add(et_password.text.toString())
        data.add(et_confirmation_password.text.toString())
        data.add(et_company_name.text.toString())
        return data
    }

    private fun getRegister() {
        showLoadingOpsicorp(true)
        GetDataLogin(MyURL.URL_TRAVELAJA).getCompletlyRegister(dataCompletly(),object :CallbackString{
            override fun successLoad(data: String) {
//                showAllert("Success","you have successfully registered, please login")
                getLogin()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry),message)
            }
        })
    }

    private fun getLogin() {
        GetDataLogin(MyURL.URL_TRAVELAJA).getDataLogin(email,et_password.text.toString(),object : CallbackLogin {
            override fun successGetData(data: DataLoginModel) {
                //initFirebase()
                Globals.setBaseUrl(this@ComplitelyRegisterActivity,MyURL.URL_TRAVELAJA)
                saveDataLogin(data)
                getConfig("Bearer "+data.token)
            }

            override fun failedGetData(message: String) {
                hideLoadingOpsicorp()
                failedWarning(message)
            }
        })
    }

    private fun dataCompletly(): HashMap<Any, Any> {
        val data = CompletelyRegisterRequest()
        data.password        = et_password.text.toString()
        data.confirmPassword = et_confirmation_password.text.toString()
        data.fullName        = et_username.text.toString()
        data.companyName     = et_company_name.text.toString()
        data.id              = id
        data.token           = otp
        return Globals.classToHashMap(data,CompletelyRegisterRequest::class.java)
    }

    private fun saveDataLogin(data: DataLoginModel) {
        Globals.setDataPreferenceString(this,"login_user", Serializer.serialize(data, DataLoginModel::class.java))
        Globals.setDataPreferenceString(this,"token","Bearer "+data.token)
        Globals.setDataPreferenceString(this,"username", data.userName)
    }

    private fun getConfig(token: String) {
        GetDataLogin(getBaseUrl()).getDataConfig(token,object : CallbackConfig {
            override fun failedLoad(message: String) {
                failedWarning(message)
            }

            override fun successLoad(data: ConfigModel) {
                Globals.setDataPreferenceString(this@ComplitelyRegisterActivity,"config",Serializer.serialize(data, ConfigModel::class.java))
                gotoSplashScreen()
                hideLoadingOpsicorp()
            }
        })
    }

    private fun failedWarning(message: String) {
        Globals.showAlert(getString(R.string.failed),message,this)
    }

    fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
        finish()
    }

}