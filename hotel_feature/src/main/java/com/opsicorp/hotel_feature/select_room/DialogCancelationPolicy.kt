package com.opsicorp.hotel_feature.select_room

import android.os.Bundle
import android.view.View
import java.lang.Exception
import com.opsicorp.hotel_feature.R
import android.annotation.SuppressLint
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.DateConverter
import com.unicode.kingmarket.Base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_cancelation_policy.*
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

@SuppressLint("ValidFragment")
class DialogCancelationPolicy(val date:String) : BaseDialogFragment() {
    override fun getLayout(): Int {
        transparantBackground()
        return R.layout.dialog_cancelation_policy }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        btn_cancel.setOnClickListener {
            dismiss()
        }

        try {
            tv_cancel_before.text = "Charge 100% - Start from ${DateConverter().getDate(dataTrip.startDate.split(" ")[0],"yyyy-mm-dd","dd MMM yyyy")}"//22 May 2020
            tv_cancelation_start_from.text = "No charge for cancellation before ${DateConverter().getDate(dataTrip.endDate.split(" ")[0],"yyyy-mm-dd","dd MMM yyyy")}"
        }catch (e:Exception){
            tv_cancel_before.text = "Charge 100% - Start from ..."//22 May 2020
            tv_cancelation_start_from.text = "No charge for cancellation before ...}"
            e.printStackTrace()
        }
    }

}