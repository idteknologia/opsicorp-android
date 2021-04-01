package com.opsigo.travelaja.module.payment

import android.content.Intent
import android.os.Build
import android.support.v4.content.ContextCompat
import android.webkit.WebViewClient
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.home.activity.HomeActivity
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclikAllertDoubleSelected
import kotlinx.android.synthetic.main.payment_activity.*

class PaymentActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener{

    private lateinit var callBackApi: String
    private lateinit var tripCode: String
    private lateinit var expiredTime: String


    override fun getLayout(): Int {
        return R.layout.payment_activity
    }

    override fun OnMain() {
        initToolbar()
        initData()
        swipeRefreshPay.setOnRefreshListener {
            loadUrl()
        }
        loadUrl()
    }

    private fun initData() {
        val bundle = intent.getBundleExtra("data")
        tripCode  = bundle.getString(Constants.TRIP_CODE).toString()
        expiredTime = bundle.getString(Constants.EXPIRED_TIME).toString()
        webViewPayment.webViewClient = MyWebViewClient()
        webViewPayment.settings.loadsImagesAutomatically = true
        webViewPayment.settings.javaScriptEnabled = true
        webViewPayment.settings.useWideViewPort = true
    }

    private fun loadUrl() {
        webViewPayment.loadUrl(callBackApi)
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        toolbar.setTitleBar("Select Payment")
    }

    override fun btnBack() {
        Globals.showAlertComplete("Exit Payment","The payment process will be canceled if you leave",this,object : OnclikAllertDoubleSelected {
            override fun yes() {
                val intent = Intent(applicationContext,HomeActivity::class.java)
                intent.putExtra(Constants.FROM_PAYMENT, true)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

            override fun no() {
            }
        })
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    inner class MyWebViewClient : WebViewClient() {

    }
}