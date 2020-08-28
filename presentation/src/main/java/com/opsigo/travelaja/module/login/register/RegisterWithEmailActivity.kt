package com.opsigo.travelaja.module.login.register

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.login.signin.activity.OtpRegisterActivity
import com.opsigo.travelaja.module.login.splash.activity.SplashActivity
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.register_with_email_view.*
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.network.MyURL
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.signin.DataLoginModel
import java.util.HashMap

class RegisterWithEmailActivity : BaseActivity(),ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.register_with_email_view
    }

    override fun OnMain() {
        initOnclickListener()
    }

    private fun initOnclickListener() {
        btnSignIn.callbackOnclickButton(this)
        btn_back.setOnClickListener { finish() }
        /* et_username.setOnClickListener(this)
           et_password.setOnClickListener(this)
           et_email.setOnClickListener(this) */
    }

    override fun onClicked() {
        if (Globals.validatiEdittext(getDataField())){
            getRegister()
        }
        else {
            failedWarning("Field can not be empty")
        }
    }

    private fun getDataField(): java.util.ArrayList<String> {
        val data = ArrayList<String>()
        data.add(et_email.text.toString())
        return data
    }

    private fun getRegister() {
        showLoadingOpsicorp(true)
        GetDataLogin(MyURL.URL_TRAVELAJA).getDataRegister(emailUser(),object : CallbackString{
            override fun successLoad(data: String) {
                val bundle = Bundle()
                bundle.putString("Email",et_email.text.toString())
                gotoActivityWithBundle(OtpRegisterActivity::class.java,bundle)
                hideLoadingOpsicorp()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert("Sorry",message)
            }
        })
    }

    private fun emailUser(): HashMap<Any, Any> {
        val data = EmailModel()
        data.Email = et_email.text.toString()
        return Globals.classToHashMap(data,EmailModel::class.java)
    }

    class EmailModel{
        var Email = ""
    }

    private fun failedWarning(message: String) {
        Globals.showAlert("Failed",message,this)
    }
}