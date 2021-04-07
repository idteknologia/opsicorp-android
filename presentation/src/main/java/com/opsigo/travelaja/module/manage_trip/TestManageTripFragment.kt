package com.opsigo.travelaja.module.manage_trip

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.opsigo.travelaja.R
//import com.opsigo.opsicorp.module.item_custom.dasboard_approval.DasboardApproval

import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.manage_trip_fragment_test.*

class TestManageTripFragment : BaseFragment()
        ,TestDasboardApproval.CallbackDassboardApprofal
        ,View.OnClickListener, TestDasboardListApproval.CallbackTestDassboardListApproval {

    var dasboardPage = false

    override fun getLayout(): Int { return R.layout.manage_trip_fragment_test }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        checkPositionAmployer()
        line_list_approval.setInitCallback(this)
    }

    private fun checkPositionAmployer() {

        showListApprofal(
                DateConverter().getDay(-10,"yyyy-MM-dd").replace("Current Date : ", "").trim(),
                DateConverter().getDay("yyyy-MM-dd").replace("Current Date : ","").trim(),
                0,
                ""
        )

        line_dasboard.setCallbackListener(this)

    }

    private fun showListApprofal(tripDateFrom: String,tripDateTo: String,position:Int,key:String) {
        dasboardPage = false
        //tv_title_two.text = "Click list to see your trip detail"
        line_dasboard.gone()
        Constants.tripDateFrom = tripDateFrom
        Constants.tripDateTo   = tripDateTo
        Constants.poition      = position
        Constants.key          = key
        line_list_approval.show(tripDateFrom,tripDateTo,position,key)
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

    override fun draft() {
        showListApprofal(line_dasboard.dateFrom,line_dasboard.dateTo,7,"")
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


}