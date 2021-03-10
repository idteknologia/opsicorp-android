package com.opsicorp.hotel_feature.detail_hotel

import com.opsicorp.hotel_feature.R
import com.squareup.picasso.Picasso
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.module.cart.model.CartModel
import kotlinx.android.synthetic.main.activity_detail_summary_hotel.*

class DetailSummaryHotelActivity : BaseActivity() {

    override fun getLayout(): Int { return R.layout.activity_detail_summary_hotel }

    var data = CartModel()

    override fun OnMain() {
        initDataHotel()
        setDataView()
    }

    private fun initDataHotel() {
        data = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_HOTEL),CartModel::class.java)
        setLog("---------------------")
        setLog(intent.getStringExtra(Constants.DATA_DETAIL_HOTEL))
    }

    private fun setDataView() {
        val dataHotel =  data.dataCardHotel
        total_night.text                = ""

        tv_status_hotel_cart.text       = dataHotel.status
        tv_pnr_hotel_cart.text          = dataHotel.pnrHotel
        tv_date_booking_hotel_cart.text = dataHotel.dateBooking
        tv_name_hotel_cart.text         = dataHotel.nameHotel
        tv_type_hotel_cart.text         = dataHotel.typeHotel
        tv_description_hotel_cart.text  = dataHotel.descreption
        tv_prize_hotel_cart.text        = dataHotel.price
//        tv_reason_code.text             = ""
//        tv_description.text             = ""
        tv_total_price_item.text        = dataHotel.price
        tv_total_amount.text            = dataHotel.price
        setImageHotel(dataHotel.image)
    }

    private fun setImageHotel(url:String) {
        Picasso.get()
                .load(url)
                .fit()
                .into(tv_image_hotel_cart)
    }
}