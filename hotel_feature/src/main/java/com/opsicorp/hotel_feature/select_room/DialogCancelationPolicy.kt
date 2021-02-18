package com.opsicorp.hotel_feature.select_room

import android.os.Bundle
import android.view.View
import com.opsicorp.hotel_feature.R
import android.annotation.SuppressLint
import com.opsigo.travelaja.utility.DateConverter
import com.unicode.kingmarket.Base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_cancelation_policy.*
import java.lang.Exception

@SuppressLint("ValidFragment")
class DialogCancelationPolicy(val date:String) : BaseDialogFragment() {
    override fun getLayout(): Int {
        transparantBackground()
        return R.layout.dialog_cancelation_policy }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        btn_cancel.setOnClickListener {
            dismiss()
        }
        try {
            tv_cancel_before.text = "No charge for cancellation before ${DateConverter().getDate(date,"yyyy-mm-dd","dd MMM yyyy")}"
        }catch (e:Exception){
            tv_cancel_before.text = "No charge for cancellation before ..."
            e.printStackTrace()
        }
    }

    interface CallbackCamcelation{
        fun callbacl()
    }
}