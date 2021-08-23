package com.mobile.travelaja.module.payment

import android.content.Intent
import android.graphics.Bitmap
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
import kotlinx.android.synthetic.main.payment_activity.*
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.ContactRequest
import opsigo.com.datalayer.request_model.create_trip_plane.SubmitTripPlant
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsItem
import opsigo.com.domainlayer.callback.CallbackPayment
import opsigo.com.domainlayer.callback.CallbackSubmitTripPlant
import opsigo.com.domainlayer.callback.CallbackSummary
import opsigo.com.domainlayer.model.PaymentModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.summary.SummaryModel
import java.util.ArrayList
import java.util.HashMap

class PaymentActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener{

    private lateinit var callBackApi: String
    private lateinit var tripCode: String
    private lateinit var tripPlanId: String
    private lateinit var expiredTime: String
    var tripSummary = SummaryModel()


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
                Globals.showAlert(getString(R.string.sorry),message,applicationContext)
            }

        })
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.singgleTitleGravity(toolbar.START)
        toolbar.setTitleBar(getString(R.string.select_payment))
    }

    override fun btnBack() {
        showDialog(getString(R.string.checking_payment))
        GetDataGeneral(getBaseUrl()).getDataSummary(getToken(), tripPlanId, object : CallbackSummary {
            override fun successLoad(summaryModel: SummaryModel) {
                setLog(Serializer.serialize(summaryModel))
                tripSummary = summaryModel
                if (tripSummary.paymentStatusView.equals("Paid")){
                    hideDialog()
                    if (tripSummary.type==Constants.PERSONAL_TRIP){
                        showDialog(getString(R.string.payment_success))
                        paymentSuccess()
                    }else {
                        showDialog(getString(R.string.payment_success))
                        gotoMainMenu()
                    }
                } else {
                    hideDialog()
                    onBackPressed()
                }
            }

            override fun failedLoad(message: String) {
                hideDialog()
                showAllert(getString(R.string.sorry), message)
            }
        })
        /*Globals.showAlertComplete("Exit Payment","The payment process will be canceled if you leave",this,object : OnclikAllertDoubleSelected {
            override fun yes() {
                gotoMainMenu()
            }

            override fun no() {
            }
        })*/
    }

    private fun paymentSuccess() {
        showLoadingOpsicorp(true)
        GetDataTripPlane(getBaseUrl()).submitTripPlant(getToken(), getDataTripItem(), object : CallbackSubmitTripPlant {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideDialog()
                hideLoadingOpsicorp()
                val intent = Intent(applicationContext,HomeActivity::class.java)
                intent.putExtra(Constants.FROM_PAYMENT, true)
                startActivity(intent)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry), message)
            }
        })
    }

    private fun getDataTripItem(): HashMap<String, Any> {
        val model = SubmitTripPlant()
        model.startDate = tripSummary.startDate
        model.returnDate = tripSummary.returnDate
        model.origin = tripSummary.origin
        model.destination = tripSummary.destination
        model.type = tripSummary.type.toString()
        model.tripParticipants = getDataParticipant()
        model.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        model.purpose = tripSummary.purpose
        model.id = tripSummary.tripId
        model.code = tripSummary.tripCode
        model.contact = getContactRequest()
        return Globals.classToHasMap(model, SubmitTripPlant::class.java)
    }

    private fun getContactRequest(): ContactRequest {
        val contactPerson = ContactRequest()
        contactPerson.lastName = getProfile().lastName
        contactPerson.firstName = getProfile().firstName
        return contactPerson
    }

    private fun getDataParticipant(): List<TripParticipantsItem> {
        val participants = ArrayList<TripParticipantsItem>()

        if (tripSummary.tripParticipantModels.isNotEmpty()) {
            tripSummary.tripParticipantModels.forEach {
                val data = TripParticipantsItem()
                data.budgetId = it.budgetId
                data.costCenterId = it.costId
                data.employeeId = it.employId
                participants.add(data)
            }
        }

        return participants
    }

    private fun gotoMainMenu() {
        hideDialog()
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