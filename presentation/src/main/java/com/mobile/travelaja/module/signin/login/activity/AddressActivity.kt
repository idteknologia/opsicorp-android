package com.mobile.travelaja.module.signin.login.activity

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.BuildConfig
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.signin.login.OpenIdLogin
import com.mobile.travelaja.module.signin.login.presenter.LoginPresenter
import com.mobile.travelaja.module.signin.login.view.LoginView
import com.mobile.travelaja.module.signin.login.viewmodel.UserViewModel
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import kotlinx.android.synthetic.main.address_activity_view.*
import kotlinx.android.synthetic.main.login_activity_view_travel_aja.*
import net.openid.appauth.*
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackConfig
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.domainlayer.model.signin.DataLoginModel
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class AddressActivity : BaseActivity(), LoginView {
    private lateinit var authService: AuthorizationService
    private lateinit var viewModel: UserViewModel

    override fun getLayout(): Int {
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, this)
        ).get(UserViewModel::class.java)
        authService = AuthorizationService(this)
        return R.layout.address_activity_view
    }

    val presenter by inject<LoginPresenter> { parametersOf(this) }

    override fun OnMain() {
        presenter.checkLogin()
        onClickListener()
        buttonTextBottom.setOnClickListener {
            finish()
        }
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
    }

    override fun onBackPressed() {
        closeApplication()
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideStatusBar()
    }

    override fun onPause() {
        super.onPause()
        hideStatusBar()
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
                        val body = mutableMapOf<String, Any?>(
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

    private fun onClickListener() {
        btnNext.setOnClickListener {
            //opsicorp-mobile
            var url = et_url.text.toString().toLowerCase()
            var baseUrl = "https://$url.opsicorp.com/"
            if (BuildConfig.DEBUG){
                if (url.contains("opsinfradev",false)){
                    url = getString(R.string.base_api_pertamina)
                    baseUrl = getString(R.string.base_api_pertamina)
                }
            }
            if (url.isNotEmpty()) {
//                if (url.equals(getString(R.string.pertamina),false)) {
//                    showDialog(getString(R.string.waiting))
//                    OpenIdLogin.loginWithSSO(
//                        this,
//                        getString(R.string.client_id_sso_pertamina),
//                        getString(R.string.endpoint_issuer_pertamina),
//                        authService
//                    ) {
//                        hideDialog()
//                    }
//                } else {
                    showDialog("")
                    onLogin("sck", "gshc", baseUrl)
//                }
            } else {
                showAllert(getString(R.string.txt_please), getString(R.string.insert_domain_name_your_corporate))
            }
        }
    }

    private fun onLogin(username: String, password: String, baseUrl: String) {
        GetDataLogin(baseUrl).getDataLogin(username, password, object : CallbackLogin {
            override fun successGetData(data: DataLoginModel) {

            }

            override fun failedGetData(message: String) {
                hideDialog()
                if (message.contains(resources.getString(R.string.unable_host))) {
                    showAllert(getString(R.string.sorry), getString(R.string.no_address_associated_with_hostname))
                } else {
                    Globals.setBaseUrl(this@AddressActivity, baseUrl)
                    gotoActivity(LoginPremiumActivity::class.java)
                    finish()
                }
            }
        })

    }

    private fun getConfig(token: String) {
        GetDataLogin(getBaseUrl()).getDataConfig(token, object : CallbackConfig {
            override fun failedLoad(message: String) {

            }

            override fun successLoad(data: ConfigModel) {
                val dataConfig = data
                Log.d("xconfigx", "onloginact : " + dataConfig.isShowCreateTripOnMobile)
                Globals.setDataPreferenceString(
                    this@AddressActivity, "config",
                    Serializer.serialize(data, ConfigModel::class.java)
                )
                gotoSplashScreen()
                hideDialog()
            }
        })
    }

    private fun failedWarning(message: String) {
        Globals.showAlert(getString(R.string.failed), message, this)
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

    override fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
        finish()
    }

//    //Todo navigate token idaman for Kang Ezhar
//    private fun navigateTokenIdaman(code : String , codeVerifier : String){
//        val intent = Intent(this, TokenIdamanActivity::class.java)
//        intent.putExtra(TokenIdamanActivity.CODE,code)
//        intent.putExtra(TokenIdamanActivity.CODE_VERIFIER,codeVerifier)
//        startActivity(intent)
//    }

}