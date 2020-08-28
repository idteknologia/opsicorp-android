package com.opsigo.travelaja.module.login.signin.activity

import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.login.signin.presenter.LoginPresenter
import com.opsigo.travelaja.module.login.signin.view.LoginView
import com.opsigo.travelaja.module.login.splash.activity.SplashActivity
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.address_activity_view.*
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.domainlayer.callback.CallbackLogin
import opsigo.com.domainlayer.model.signin.DataLoginModel
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class AddressActivity : BaseActivity(), LoginView {

    override fun getLayout(): Int { return R.layout.address_activity_view }

    val presenter by inject<LoginPresenter> { parametersOf(this) }

    override fun OnMain() {
        presenter.checkLogin()
        onClickListener()
    }

    private fun onClickListener() {
        btnNext.setOnClickListener {
            //opsicorp-mobile
            val baseUrl = "https://"+et_url.text.toString().toLowerCase()+".opsicorp.com/"
            if (et_url.text.toString().toLowerCase().isNotEmpty()){
                showDialog("")
                GetDataLogin(baseUrl).getDataLogin("sck","gshc",object : CallbackLogin {
                    override fun successGetData(data: DataLoginModel) {

                    }

                    override fun failedGetData(message: String) {
                        if (message.contains(resources.getString(R.string.unable_host))){
                            hideDialog()
                            showAllert("Sorry","No address associated with hostname")
                        }
                        else{
                            hideDialog()
                            Globals.setBaseUrl(this@AddressActivity,baseUrl)
                            gotoActivity(LoginPremiumActivity::class.java)
                        }
                    }
                })
            }
            else{
                showAllert("Please","Insert Domain name your Corporate")
            }

        }

    }


    override fun gotoSplashScreen() {
        gotoActivity(SplashActivity::class.java)
    }

}