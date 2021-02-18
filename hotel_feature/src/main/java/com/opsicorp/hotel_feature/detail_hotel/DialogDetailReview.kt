package com.opsicorp.hotel_feature.detail_hotel

import android.os.Bundle
import android.view.View
import android.graphics.Color
import android.widget.TextView
import com.opsicorp.hotel_feature.R
import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import com.unicode.kingmarket.Base.BaseDialogFragment

@SuppressLint("ValidFragment")
class DialogDetailReview(val date:String,
                         val name:String,
                         val value:String,
                         val rating:String) : BaseDialogFragment() {
    override fun getLayout(): Int {
        transparantBackground()
        return R.layout.dialog_review_hotel
    }

    lateinit var dates: TextView
    lateinit var names: TextView
    lateinit var descript: TextView
    lateinit var ratings: TextView


    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        dates = fragment.findViewById(R.id.tv_date_riview_hotel) as TextView
        names = fragment.findViewById(R.id.tv_name_riview_hotel) as TextView
        descript = fragment.findViewById(R.id.tv_value_riview_hotel) as TextView
        ratings = fragment.findViewById(R.id.tv_rating_riview_hotel) as TextView

        dates.text   = date
        names.text   = name
        descript.text  = value
        ratings.text = rating
    }

    interface CallbackDetailReview{
        fun callbacl()
    }
}