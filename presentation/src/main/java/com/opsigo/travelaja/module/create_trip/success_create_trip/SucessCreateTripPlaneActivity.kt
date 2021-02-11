package com.opsigo.travelaja.module.create_trip.success_create_trip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.page_parent.activity.AccomodationActivity
import com.opsigo.travelaja.module.home.activity.HomeActivity
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Constants.TYPE_ACCOMODATION
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.success_create_trip_plane.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel


class SucessCreateTripPlaneActivity : BaseActivity(){
    override fun getLayout(): Int { return R.layout.success_create_trip_plane }

    var data = SuccessCreateTripPlaneModel()

    override fun OnMain() {
        setData()
    }

    private fun setData() {
        data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP,SuccessCreateTripPlaneModel::class.java)
        if(data.tripCode!=null) {
            image_barcode.setImageBitmap(Globals.stringToBarcodeImage(data.tripCode))
        }else{

        }

        tv_status.text = data.status
        tv_tripcode.text = data.tripCode
        tv_purpose.text  = data.purpose
        tv_created_date.text = "Created Date ${data.createDateView}"
        //tv_expired_date.text = "1 days left to expired"
        tv_expired_date.visibility = View.GONE //don't need expire for draft
        tv_destination.text = data.destinationName

        tv_start_date.text = DateConverter().setDateFormatDayEEEddMMM(data.startDate)
        tv_end_date.text = DateConverter().setDateFormatDayEEEddMMM(data.endDate)

        setLog(Constants.DATA_SUCCESS_CREATE_TRIP)

        Globals.delay(1500,object :Globals.DelayCallback{
            override fun done() {
                nested_view.post(Runnable {
//                    nested_view.fullScroll(View.FOCUS_DOWN)
                    nested_view.smoothScrollBy(0,nested_view.bottom)
                })
            }
        })
    }

    fun laterListener(view: View){
        later()
    }

    fun later(){
        finish()

        val exit = Intent(this, HomeActivity::class.java)
        exit.addCategory(Intent.CATEGORY_HOME)
        exit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        //exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(exit)
    }

    fun addTripPlaneListener(view: View){
        Globals.setDataPreferenceString(this,Constants.DATA_CREATE_TRIP_PLAN,Serializer.serialize(data,SuccessCreateTripPlaneModel::class.java))
        val bundle = Bundle()
        bundle.putInt(TYPE_ACCOMODATION,Constants.KEY_ACCOMODATION)
        gotoActivityWithBundle(AccomodationActivity::class.java,bundle)
    }

    override fun onBackPressed() {
        /*backListerner()*/
        later()
    }

    /*private fun backListerner() {

            Globals.showAlert("Maaf","Apakah anda yakin?",this,object :Globals.OnclikAllert{
                override fun yes() {
                    later()
                }

                override fun no() {

                }
            })

    }*/
}