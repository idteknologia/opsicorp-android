package com.opsigo.travelaja.module.cart.activity

import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.cart.model.CartModel
import com.opsigo.travelaja.module.cart.model.ItemCardFlightModel
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.detail_cart_activity.*
import opsigo.com.datalayer.mapper.Serializer

class DetailCartActivity : BaseActivity() {

    lateinit var dataOrderDetail : ItemCardFlightModel

    override fun getLayout(): Int {
        return R.layout.detail_cart_activity
    }

    override fun OnMain() {
        dataOrderDetail = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_FLIGHT), CartModel::class.java).dataCardFlight
        initToolbar()
        initData()
        initPrice()
    }

    private fun initPrice() {
        tv_prize_departure.text   = StringUtils().setCurrency("IDR", dataOrderDetail.price.toDouble(), false)
        tv_station_departure.text = dataOrderDetail.titleFlight

        if (dataOrderDetail.typeFlight!==0){
            line_arrival.visible()
            tv_prize_arrival.text       =  StringUtils().setCurrency("IDR", dataOrderDetail.price.toDouble() , false)
            tv_station_arrival.text     = dataOrderDetail.titleFlight
            tv_price_total.text         = StringUtils().setCurrency("IDR", (dataOrderDetail.price.toDouble() + dataOrderDetail.price.toDouble()) , false)
        }
        else{
            line_arrival.gone()
            tv_price_total.text         = StringUtils().setCurrency("IDR", dataOrderDetail.price.toDouble() , false)
        }
    }

    private fun initData() {

    }

    private fun initToolbar() {
        if (dataOrderDetail.typeFlight==0){
            tvoneway.text = "Oneway"
        } else {
            tvoneway.text = "Roundtrip"
        }
        ic_back.setOnClickListener {
            onBackPressed()
        }
        if (dataOrderDetail.isComply.equals(true)){
            line_reason_code.gone()
        } else {
            line_reason_code.visible()
        }
    }

}