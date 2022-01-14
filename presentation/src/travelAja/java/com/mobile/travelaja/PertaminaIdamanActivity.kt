package com.mobile.travelaja

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.module.signin.login.OpenIdLogin
import com.mobile.travelaja.module.signin.login.view.LoginView
import com.mobile.travelaja.module.signin.login.viewmodel.UserViewModel
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackConfig
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.domainlayer.model.signin.DataLoginModel

class PertaminaIdamanActivity : BaseActivity(),LoginView {
    private lateinit var authService: AuthorizationService
    private lateinit var viewModel: UserViewModel
    private lateinit var button : Button

    override fun getLayout(): Int = R.layout.activity_pertamina_idama

    override fun OnMain() {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, this)
        ).get(UserViewModel::class.java)
        authService = AuthorizationService(this)
        viewModel.login.observe(this) {
            Globals.setBaseUrl(this, getString(R.string.base_api_pertamina))
            val data = DataLoginModel()
            data.employId = it.employeeId
            data.token = it.accessToken
            data.userName = it.userName
            saveDataLogin(data)
            getConfig("Bearer " + data.token)
        }
        viewModel.isError.observe(this) {
            Utils.handleErrorMessage(this, it) { errorString ->
                failedWarning(errorString)
            }
        }
        viewModel.isLoading.observe(this) { loading ->
            if (loading)
                showDialog(getString(R.string.waiting))
            else
                hideDialog()
        }
        button = findViewById(R.id.buttonIdaman)
        button.setOnClickListener {
            gotoIdaman()
        }
    }

    private fun gotoIdaman(){
        showDialog(getString(R.string.waiting))
        OpenIdLogin.loginWithSSO(
            this,
            getString(R.string.client_id_sso_pertamina),
            getString(R.string.endpoint_issuer_pertamina),
            authService
        ) {
            hideDialog()
        }
    }

    private fun failedWarning(message: String) {
        Globals.showAlert(getString(R.string.failed), message, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OpenIdLogin.REQ_CODE_OPEN_ID) {
            if (data != null) {
                val resp = AuthorizationResponse.fromIntent(data)
                val ex = AuthorizationException.fromIntent(data)
                if (ex != null) {
                    Utils.handleErrorMessage(this, ex) {
                        if (it.isNotEmpty()) {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val uriString = data.data?.toString()?.replace("#code", "?code")
                    val code = Uri.parse(uriString).getQueryParameter("code")
                    val codeVerifier = resp?.request?.codeVerifier
                    if (code != null && codeVerifier != null) {
                        val body = mutableMapOf<String, Any>(
                            "code" to code,
                            "codeVerifier" to codeVerifier,
                            "grant_type" to "password"
                        )
//                        navigateTokenIdaman(code,codeVerifier)
                        viewModel.onLogin("${getString(R.string.base_api_pertamina)}token",body)

                    }
                }
            }
        }
    }
    private fun saveDataLogin(data: DataLoginModel) {
        Globals.setDataPreferenceString(
            this,
            "login_user",
            Serializer.serialize(data, DataLoginModel::class.java)
        )
        Globals.setDataPreferenceString(this, "token", "Bearer " + data.token)
        Globals.setDataPreferenceString(this, "username", data.userName)
    }

    private fun getConfig(token: String) {
        GetDataLogin(getBaseUrl()).getDataConfig(token, object : CallbackConfig {
            override fun failedLoad(message: String) {

            }

            override fun successLoad(data: ConfigModel) {
                val dataConfig = data
                Log.d("xconfigx", "onloginact : " + dataConfig.isShowCreateTripOnMobile)
                Globals.setDataPreferenceString(
                    this@PertaminaIdamanActivity, "config",
                    Serializer.serialize(data, ConfigModel::class.java)
                )
                gotoSplashScreen()
                hideDialog()
            }
        })
    }

    override fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
        finish()
    }
}