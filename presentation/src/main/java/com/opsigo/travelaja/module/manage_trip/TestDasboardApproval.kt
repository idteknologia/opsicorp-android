package com.opsigo.travelaja.module.manage_trip

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_top_filter_approval.ButtonTopFilterApproval
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.domainlayer.callback.CallbackTotalApproval
import opsigo.com.domainlayer.model.aprover.TotalApprovalModel
import kotlinx.android.synthetic.main.list_month_approval.view.*
import java.text.DecimalFormat

class TestDasboardApproval: LinearLayout, View.OnClickListener,ButtonTopFilterApproval.OnclickButtonApproval {

    lateinit var onclick : CallbackDassboardApprofal

    fun setCallbackListener(callbackDassboardApprofal: CallbackDassboardApprofal){
        onclick = callbackDassboardApprofal
    }

    var dateFrom = ""
    var dateTo   = ""

    override fun onClick(v: View?) {

        when(v){
            tv_waiting_for_approval     -> onclick.waitingApprofal()
            tv_total_rejected           -> onclick.rejected()
            tv_total_approved           -> onclick.approfal()
            tv_total_partially_rejected -> onclick.partialRejected()
            tv_total_partially_approved -> onclick.partialApproved()
            tv_list_approval            -> onclick.seeAllList()

            line_waiting_for_approval     -> onclick.waitingApprofal()
            line_total_rejected           -> onclick.rejected()
            line_total_approved           -> onclick.approfal()
            line_total_partially_rejected -> onclick.partialRejected()
            lien_total_partially_approved -> onclick.partialApproved()
            line_list_approval            -> onclick.seeAllList()
        }

    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.test_list_month_approval, this)


        btn_filter_approval.callbackOnclickFilter(this)
        //tv_waiting_for_approval.setOnClickListener(this)

//        tv_total_rejected.setOnClickListener(this)
//        tv_total_approved.setOnClickListener(this)
//        tv_total_partially_rejected.setOnClickListener(this)
//        tv_total_partially_approved.setOnClickListener(this)
//        tv_list_approval.setOnClickListener(this)

        line_waiting_for_approval.setOnClickListener(this)
        line_total_rejected.setOnClickListener(this)
        line_total_approved.setOnClickListener(this)
        line_total_partially_rejected.setOnClickListener(this)
        lien_total_partially_approved.setOnClickListener(this)
        line_list_approval.setOnClickListener(this)

        btn_filter_approval.firstButtonOn()

        thisDay()

    }


    fun show(){
        visibility = View.VISIBLE
    }

    fun gone(){
        visibility = View.GONE
    }

    override fun thisDay() {
//        getDataTotalApprofal(dummy())
//        getData("2019-11-01","2019-12-31")
        dateFrom = DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim()
        dateTo   = DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim()
        getData(dateFrom,dateTo)
    }

    override fun onWeek() {
        dateFrom = DateConverter().getDay(-7,"yyyy-MM-dd").replace("Current Date : ","").trim()
        dateTo   = DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim()
        getData(dateFrom,dateTo)
//        getDataTotalApprofal(dummy1())
    }

    override fun onLastMonth() {
        dateFrom = DateConverter().getDay(-30,"yyyy-MM-dd").replace("Current Date : ","").trim()
        dateTo   = DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim()
        getData(dateFrom,dateTo)
//        getDataTotalApprofal(dummy2())
    }

    private fun getDataTotalApprofal(data :TotalModelApprofal) {
        //hide
//        animateCountTextView(tv_waiting_for_approval,data.waitingApprofal)
//        animateCountTextView(tv_total_rejected,data.rejected)
//        animateCountTextView(tv_total_approved,data.approval)
//        animateCountTextView(tv_total_partially_rejected,data.partiallyApproved)
//        animateCountTextView(tv_total_partially_approved,data.partiallyRejected)
//        animateCountTextView(tv_list_approval,data.totalListApprofal)

/*        tv_waiting_for_approval.text     = data.waitingApprofal
        tv_total_rejected.text           = data.rejected
        tv_total_approved.text           = data.approval
        tv_total_partially_rejected.text = data.partiallyApproved
        tv_total_partially_approved.text = data.partiallyRejected
        tv_list_approval.text            = data.totalListApprofal*/

    }

    private fun getData(tripDateFrom: String,tripDateTo:String) {
        //resetValue()
        GetDataApproval(Globals.getBaseUrl(context)).getDataTotalApproval(Globals.getToken()
                ,"10","1","Code"
                ,"1", tripDateFrom,tripDateTo
                ,object :CallbackTotalApproval{
            override fun successLoad(data: ArrayList<TotalApprovalModel>) {

                val mData = TotalModelApprofal()
                mData.waitingApprofal = data.filter { it.statusName == "WaitingForApproval" }.first().total
                mData.rejected = data.filter { it.statusName == "CompletelyRejected" }.first().total
                mData.approval = data.filter { it.statusName == "CompletelyApproved" }.first().total
                mData.partiallyRejected = data.filter { it.statusName == "PartiallyRejected" }.first().total
                mData.partiallyApproved = data.filter { it.statusName == "PartiallyApproved" }.first().total
                val total = data.filter { it.statusName == "WaitingForApproval" }.first().total.toInt()+data.filter { it.statusName == "CompletelyRejected" }.first().total.toInt()+data.filter { it.statusName == "CompletelyApproved" }.first().total.toInt()+data.filter { it.statusName == "PartiallyRejected" }.first().total.toInt()+data.filter { it.statusName == "PartiallyApproved" }.first().total.toInt()
                mData.totalListApprofal = total.toString()

                getDataTotalApprofal(mData)

            }

            override fun failedLoad(message: String) {

            }
        })
    }


    fun resetValue(){
        tv_waiting_for_approval.text = "0"
        tv_total_rejected.text = "0"
        tv_total_approved.text = "0"
        tv_total_partially_rejected.text = "0"
        tv_total_partially_approved.text = "0"
        tv_list_approval.text = "0"
    }

    fun animateCountTextView(textView: TestCountAnimationTextView,string: String){
        textView
                .setDecimalFormat(DecimalFormat("###,###,###"))
                .setAnimationDuration(1000)
                .countAnimation(0, string.replace(".","").toInt())
    }

    interface CallbackDassboardApprofal{
        fun waitingApprofal()
        fun draft()
        fun rejected()
        fun approfal()
        fun partialRejected()
        fun partialApproved()
        fun seeAllList()
    }

    fun dummy():TotalModelApprofal{
        val data = TotalModelApprofal()
        data.waitingApprofal   = "207"
        data.partiallyApproved = "124"
        data.partiallyRejected = "410"
        data.approval          = "120"
        data.rejected          = "50"
        data.totalListApprofal = "911"

        return data
    }

    fun dummy1():TotalModelApprofal{
        val data = TotalModelApprofal()
        data.waitingApprofal   = "10.007"
        data.partiallyApproved = "10.924"
        data.partiallyRejected = "4.010"
        data.approval          = "1.000"
        data.rejected          = "590"
        data.totalListApprofal = "9.911"

        return data
    }

    fun dummy2():TotalModelApprofal{
        val data = TotalModelApprofal()
        data.waitingApprofal   = "2.047"
        data.partiallyApproved = "1.524"
        data.partiallyRejected = "2.210"
        data.approval          = "1.120"
        data.rejected          = "150"
        data.totalListApprofal = "8.911"

        return data
    }


    class TotalModelApprofal{
        var waitingApprofal     = ""
        var draft               = ""
        var rejected            = ""
        var approval            = ""
        var partiallyRejected   = ""
        var partiallyApproved   = ""
        var totalListApprofal   = ""
    }


}