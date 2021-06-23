package com.mobile.travelaja.module.signin.login.activity

import java.util.*
import android.net.Uri
import android.util.Log
import android.view.View
import android.os.Bundle
import org.koin.core.inject
import com.mobile.travelaja.R
import android.content.Intent
import com.google.android.gms.tasks.Task
import opsigo.com.datalayer.network.MyURL
import com.google.android.gms.auth.api.Auth
import com.mobile.travelaja.utility.Globals
import org.koin.core.parameter.parametersOf
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.base.BaseActivity
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.callback.CallbackConfig
import com.google.android.gms.common.api.ApiException
import android.text.method.PasswordTransformationMethod
import com.google.android.gms.common.api.GoogleApiClient
import com.mobile.travelaja.utility.Constants.RC_SIGN_IN
import opsigo.com.domainlayer.model.signin.DataLoginModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.mobile.travelaja.module.signin.login.view.LoginView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import opsigo.com.datalayer.datanetwork.dummy.signin.DataDummyUser
import kotlinx.android.synthetic.main.login_activity_view_travel_aja.*
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.module.signin.login.presenter.LoginPresenter
import com.mobile.travelaja.module.signin.register.RegisterWithEmailActivity


class LoginActivity : BaseActivity(),
    LoginView, View.OnClickListener {

    override fun getLayout(): Int {
        hideStatusBar()
        if (Globals.getBaseUrl(this).isEmpty() || Globals.getBaseUrl(this).isBlank())
            Globals.setBaseUrl(this, MyURL.URL_TRAVELAJA)
        return R.layout.login_activity_view_travel_aja
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        hideStatusBar()
    }

    val presenter by inject<LoginPresenter> { parametersOf(this) }
    lateinit var mGoogleApiClient: GoogleApiClient
    var personName = ""
    var personGivenName = ""
    var personFamilyName = ""
    var personEmail = ""
    var personId = ""
    lateinit var personPhoto: Uri


    override fun OnMain() {

        initGoogleLogin()
        presenter.checkLogin()
        onClickListener()
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance())
    }

    private fun onClickListener() {
        btnSignIn.setOnClickListener(this)
        eye_password.setOnClickListener(this)
        btn_login_premium_account.setOnClickListener(this)
        btn_login_with_email.setOnClickListener(this)
        btn_login_with_google.setOnClickListener(this)
    }

    private fun getDataLogin(username: String, password: String) {
        showDialog("")
        GetDataLogin(MyURL.URL_TRAVELAJA).getDataLogin(
            username,
            password,
            object : CallbackLogin {
                override fun successGetData(data: DataLoginModel) {
                    //initFirebase()
                    Globals.setBaseUrl(this@LoginActivity, MyURL.URL_TRAVELAJA)
                    saveDataLogin(data)
                    getConfig("Bearer " + data.token)
                }

                override fun failedGetData(message: String) {
                    hideDialog()
                    failedWarning(message)
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
                    this@LoginActivity,
                    "config",
                    Serializer.serialize(data, ConfigModel::class.java)
                )
                gotoSplashScreen()
//                    saveDataLogin(DataDummyUser().addLoginDummy1())
//                    saveDataLogin(DataDummyUser().addLoginDummy2())
                hideDialog()
            }
        })
    }

    private fun checkLoginDummy() {
        if (et_username.text.toString().equals("dwi@gmail.com")) {
            Globals.setDataPreferenceString(this, "emplaoyId", "1")
            saveDataLogin(DataDummyUser().addLoginDummy1())
            hideDialog()
        } else if (et_username.text.toString().equals("vodi@gmail.com")) {
            Globals.setDataPreferenceString(this, "emplaoyId", "2")
            saveDataLogin(DataDummyUser().addLoginDummy2())
            hideDialog()
        } else {
            hideDialog()
            failedWarning("email atau password tidak dikenali")
        }
    }

    private fun failedWarning(message: String) {
        Globals.showAlert("Failed", message, this)
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

    fun insertAutoField(view: View) {
        //et_username.setText("klarisha")
        //et_password.setText("Opsicorp2020!")
    }

    /*fun addDataUserLogin1():UserModel{

        val getDataLogin = UserModel()
        getDataLogin.namaUser = "Ari"
        getDataLogin.jabatan  = "Direktur"
        getDataLogin.alamat   = "Jl wiyung asli surabaya jawatimur"
        getDataLogin.noHp     = "081542563"
        getDataLogin.imageProfile = ""
        getDataLogin.email    = "m.ari@gmail.com"
        getDataLogin.password = "123456"
        getDataLogin.perusahaan  = "Astra"
        getDataLogin.travelAgent = "Golden Rama"

        return getDataLogin
    }*/

    override fun onBackPressed() {
        closeApplication()
    }

    override fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnSignIn -> {
                val username = et_username.text.toString()
                val password = et_password.text.toString()
                getDataLogin(username, password)
            }
            eye_password -> {
                presenter.visibilityPasswordListener(eye_password, et_password, this)
            }
            btn_login_premium_account -> {
                gotoActivity(AddressActivity::class.java)
            }
            btn_login_with_email -> {
                gotoActivity(RegisterWithEmailActivity::class.java)
            }

            btn_login_with_google -> {
                getDataGoogle()
            }
        }
    }

    private fun getDataGoogle() {
        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    fun initGoogleLogin() {
        val serverClientId =
            "370202765925-lucp63jthasvjivopvg88qg4daf7paa4.apps.googleusercontent.com"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) { Log.d("GOOGLE", "onConnectionFailed: ") }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {
                personName = acct.displayName.toString()
                personFamilyName = acct.familyName.toString()
                personId = acct.id.toString()
                personEmail = acct.email.toString()
                personGivenName = acct.givenName.toString()
                if (acct.photoUrl != null) personPhoto
                getRegister()
            }


        } catch (e: ApiException) {
            setLog("signInResult:failed code=" + e.statusCode)
        }
    }

    private fun getRegister() {
        showLoadingOpsicorp(true)
        GetDataLogin(MyURL.URL_TRAVELAJA).getDataRegister(emailUser(), object : CallbackString {
            override fun successLoad(data: String) {
                val bundle = Bundle()
                bundle.putString("Email", personEmail)
                gotoActivityWithBundle(OtpRegisterActivity::class.java, bundle)
                hideLoadingOpsicorp()
            }

            override fun failedLoad(message: String) {
                failedLoad(message)
                hideLoadingOpsicorp()
            }
        })
    }

    private fun emailUser(): HashMap<Any, Any> {
        val data = RegisterWithEmailActivity.EmailModel()
        data.Email = personEmail
        return Globals.classToHashMap(data, RegisterWithEmailActivity.EmailModel::class.java)
    }


}