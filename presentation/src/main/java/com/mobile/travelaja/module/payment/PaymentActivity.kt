package com.mobile.travelaja.module.payment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclikAllertDoubleSelected
import kotlinx.android.synthetic.main.payment_activity.*
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.domainlayer.callback.CallbackPayment
import opsigo.com.domainlayer.model.PaymentModel

class PaymentActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener{

    private lateinit var callBackApi: String
    private lateinit var tripCode: String
    private lateinit var tripPlanId: String
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
        initWebView()
    }

    private fun initWebView() {
        val webSettings = webViewPayment.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.loadsImagesAutomatically = true
        webSettings.setLoadWithOverviewMode(true)
        webSettings.setUseWideViewPort(true)
        webViewPayment.webViewClient = MyWebViewClient()
        loadUrl()

    }

    private fun initData() {
        val bundle = intent.getBundleExtra("data")
        tripPlanId = bundle?.getString(Constants.TRIP_PLAN_ID).toString()
        expiredTime = bundle?.getString(Constants.EXPIRED_TIME).toString()
    }

    private fun loadUrl() {
        showLoadingOpsicorp(true)
        GetDataTripPlane(getBaseUrl()).getPaymentLink(getToken(),tripPlanId, object :CallbackPayment{
            override fun successLoad(data: PaymentModel) {
                webViewPayment.loadUrl(data.paymentLink)
                swipeRefreshPay.isRefreshing = false
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                Globals.showAlert("Sorry",message,applicationContext)
            }

        })
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
                gotoMainMenu()
            }

            override fun no() {
            }
        })
    }

    private fun gotoMainMenu() {
        val intent = Intent(applicationContext,HomeActivity::class.java)
        intent.putExtra(Constants.FROM_PAYMENT, true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            hideLoadingOpsicorp()
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
        }
    }
}