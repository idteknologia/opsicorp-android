package com.mobile.travelaja.module.approval.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.approval_fragment.*
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import com.mobile.travelaja.module.item_custom.dasboard_approval.DasboardApproval
import com.mobile.travelaja.module.item_custom.dasboard_approval.DasboardListApproval
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals

class ApprovalFragment : BaseFragment()
        ,DasboardApproval.CallbackDassboardApprofal
        ,View.OnClickListener,DasboardListApproval.CallbackDassboardListApproval {


    var dasboardPage = false
    private var isOpenApproval = false

    override fun getLayout(): Int { return R.layout.approval_fragment }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        isOpenApproval = arguments?.getBoolean(OPEN_APPROVAL) ?: false
        checkPositionAmployer()
        searchData()
        line_list_approval.setInitCallback(this)
        if (isOpenApproval){
            showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,2,"")
        }

    }

    private fun checkPositionAmployer() {

//        showDasboardApprofal()

        // sementara.. selanjutnya bisa di kondisikan dari status jabatan / posisi
        // karyawan sebagai approfal atau bukan
        if (getProfile().isApproval){
            tv_title.text = "Hi, ${getProfile().name.substring(0,1).toUpperCase()}${getProfile().name.substring(1,getProfile().name.length).toLowerCase()}"
            showDasboardApprofal()
        }
        else{
            line_search.visibility = View.GONE
            showListApprofal(DateConverter().getDay(-30,"yyyy-MM-dd").replace("Current Date : ","").trim(),DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim(),0,"")
        }

        line_dasboard.setCallbackListener(this)
    }

    private fun showListApprofal(tripDateFrom: String,tripDateTo: String,position:Int,key:String) {
        dasboardPage = false
        tv_title_two.text = "Click list to see your trip detail"
        line_dasboard.gone()
        Constants.tripDateFrom = tripDateFrom
        Constants.tripDateTo   = tripDateTo
        Constants.poition      = position
        Constants.key          = key
        line_list_approval.show(tripDateFrom,tripDateTo,position,key)
    }


    private fun showDasboardApprofal() {
        tv_title_two.text = "Today: ${Globals.getDateNow()}"
        dasboardPage = true
        line_list_approval.gone()
        line_dasboard.show()
    }

    fun searchData(){
        var key = ""
        tv_search_approval.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                line_list_approval.searchData(s.toString())
                key = s.toString()
            }
        })

        tv_search_approval.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode ==  KeyEvent.KEYCODE_DPAD_CENTER
                    || keyCode ==  KeyEvent.KEYCODE_ENTER) {
                showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,0,key)
//                showListApprofal()

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    fun backPressed(onbackPressed: CallbackPressed){
        if (getProfile().isApproval){
            if (dasboardPage){
                onbackPressed.back()
            }
            else{
                showDasboardApprofal()
            }
        }
        else{
            onbackPressed.back()
        }
    }

    interface CallbackPressed{
        fun back()
    }

    override fun onClick(v: View?) {

    }

    override fun waitingApprofal() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,1,"")
    }

    override fun rejected() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,3,"")
    }

    override fun approfal() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,2,"")
    }

    override fun partialRejected() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,5,"")
    }

    override fun partialApproved() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,4,"")
    }

    override fun seeAllList() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,0,"")
    }

    override fun showDialog() {
        showDialog("")
    }

    override fun hidenDialog() {
        hideDialog()
    }

    override fun failedLoad(string: String,tripCode:String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Sorry")
        builder.setCancelable(false)
        builder.setMessage("Tripcode "+tripCode+" "+string)
        builder.setPositiveButton("Ok") { dialog, which ->
            line_list_approval.show(Constants.tripDateFrom,Constants.tripDateTo,Constants.poition,Constants.key)
        }
        builder.create().show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Constants.OPEN_DETAIL_TRIP_PLANE ->{
                if (resultCode==Activity.RESULT_OK){
                    if (data?.getStringExtra("action") == "load list"){
                        showListApprofal(Constants.tripDateFrom,
                                Constants.tripDateTo,
                                Constants.poition,
                                Constants.key
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val OPEN_APPROVAL = "IS_OPEN_APPROVAL"
    }
}