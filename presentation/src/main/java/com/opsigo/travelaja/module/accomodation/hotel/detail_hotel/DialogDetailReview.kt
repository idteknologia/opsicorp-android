package com.opsigo.travelaja.module.accomodation.hotel.detail_hotel

import android.os.Bundle
import android.view.View
import com.opsigo.travelaja.R
import com.unicode.kingmarket.Base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_review_hotel.*

class DialogDetailReview : BaseDialogFragment() {
    override fun getLayout(): Int {
        return R.layout.dialog_review_hotel
    }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {

    }

    fun setDataReview(date:String,
                              name:String,
                              value:String,
                              rating:String) {

        tv_date_riview_hotel.text   = date
        tv_name_riview_hotel.text   = name
        tv_value_riview_hotel.text  = value
        tv_rating_riview_hotel.text = rating
    }

    interface CallbackDetailReview{
        fun callbacl()
    }
}