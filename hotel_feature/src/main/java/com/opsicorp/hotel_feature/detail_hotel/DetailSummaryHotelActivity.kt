package com.opsicorp.hotel_feature.detail_hotel

import android.view.View
import android.widget.ImageView
import kotlin.collections.ArrayList
import com.opsicorp.hotel_feature.R
import com.squareup.picasso.Picasso
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.module.cart.model.CartModel
import kotlinx.android.synthetic.main.activity_detail_summary_hotel.*

class DetailSummaryHotelActivity : BaseActivity() {

    override fun getLayout(): Int { return R.layout.activity_detail_summary_hotel }

    var data = CartModel()
    val imageView = ArrayList<ImageView>()

    override fun OnMain() {
        initDataHotel()
        setDataView()
    }

    private fun initDataHotel() {
        data = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_HOTEL),CartModel::class.java)
        /*setLog("---------------------")
        setLog(data.dataCardHotel.checkIn+" "+data.dataCardHotel.checkOut)
        setLog(intent.getStringExtra(Constants.DATA_DETAIL_HOTEL))*/
    }

    private fun setDataView() {
        val dataHotel =  data.dataCardHotel
        val totalDate                   = Globals.calculationDate(dataHotel.checkIn.split(" ")[0],dataHotel.checkOut.split(" ")[0],"yyyy-MM-dd").toString()
        total_night.text                = "$totalDate Nights"
        total_night_room.text           = "Nights x$totalDate"

        tv_status_hotel_cart.text       = dataHotel.status
        tv_pnr_hotel_cart.text          = dataHotel.pnrCode
        tv_date_booking_hotel_cart.text = dataHotel.dateBooking
        tv_name_hotel_cart.text         = dataHotel.nameHotel
        tv_type_hotel_cart.text         = dataHotel.typeHotel
        tv_description_hotel_cart.text  = dataHotel.descreption
        tv_prize_hotel_cart.text        = "IDR ${Globals.formatAmount(dataHotel.price)}"

        tv_total_price_item.text        = "IDR 0"
        tv_total_amount.text            = "IDR ${Globals.formatAmount(dataHotel.price)}"
        tv_total_price_service_fee.text = "IDR 0"
        tv_total_price_vat.text         = "IDR ${Globals.formatAmount(dataHotel.price)}"
        startViewListener(dataHotel.starRating.toDouble().toInt())
        setImageHotel(dataHotel.image)

        if (dataHotel.reasonCode.isEmpty()){
            line_reason_code.visibility = View.GONE
        }
        else {
            line_reason_code.visibility = View.VISIBLE
            tv_reason_code.text             = dataHotel.reasonCode
            tv_description.text             = ""
        }
        icClose.setOnClickListener {
            finish()
        }
    }

    fun startViewListener(rating:Int){
        imageView.add(ic_start1)
        imageView.add(ic_start2)
        imageView.add(ic_start3)
        imageView.add(ic_start4)
        imageView.add(ic_start5)

        imageView.forEachIndexed { index, imageView ->
            if (index<=rating-1){
                imageView.visibility = View.VISIBLE
            }
            else {
                imageView.visibility = View.GONE
            }
        }
    }

    private fun setImageHotel(url:String) {
        Picasso.get()
                .load(url)
                .fit()
                .into(tv_image_hotel_cart)
    }


}