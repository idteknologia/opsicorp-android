package com.opsicorp.travelaja.feature_flight.detail_cart

import androidx.core.content.ContextCompat
import com.mobile.travelaja.utility.*
import com.mobile.travelaja.base.BaseActivity
import opsigo.com.datalayer.mapper.Serializer
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.module.cart.model.CartModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.detail_cart_activity.*
import opsigo.com.domainlayer.model.summary.FlightSegmentItem

class DetailCartFlightActivity : BaseActivity() {

    val data = ArrayList<FlightSegmentItem>()
    val adapter by lazy { DetailCartFlightAdapter(this, data) }
    val adapter2 by lazy { DetailCartPriceAdapter(this) }
    lateinit var dataOrder: CartModel

    override fun getLayout(): Int {
        return R.layout.detail_cart_activity
    }

    override fun OnMain() {
        dataOrder = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_FLIGHT), CartModel::class.java)
        setDataDetail()
        initToolbar()
        initRecyclerView()
        initPriceRv()
        if (dataOrder.dataCardFlight.isComply.equals(true)){
            line_reason_code.gone()
        } else {
            line_reason_code.visible()
        }
    }

    private fun initPriceRv() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvPriceItemDetail.layoutManager = layoutManager
        rvPriceItemDetail.itemAnimator = DefaultItemAnimator()
        rvPriceItemDetail.adapter = adapter2
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_item_flight.layoutManager = layoutManager
        rv_item_flight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_item_flight.adapter = adapter
    }

    private fun setDataDetail() {
        data.clear()

            val dataFlight                = dataOrder.dataCardFlight

        adapter.setData(dataOrder.dataCardFlight.flightSegmentItem)
        adapter2.setData(dataOrder.dataCardFlight.priceItem)

        total_pax.text = "${this.getString(R.string.text_adult_times)} ${dataOrder.dataCardFlight.flightSegmentItem.first().totalPassenger}"
        name_flight.text = dataFlight.titleFlight
        tv_total_amount.text        = "IDR ${Globals.formatAmount(dataFlight.price.split(".")[0])}"
        /*if (dataOrder.dataCardFlight.typeFlight==0){
            tv_total_amount.text        = "IDR ${Globals.formatAmount(dataFlight.price.split(".")[0])}"
        } else {
            tv_total_amount.text        = StringUtils().setCurrency("IDR",dataFlight.price.toDouble()/2, false)
        }*/

    }


    private fun initToolbar() {
        if (dataOrder.dataCardFlight.typeFlight==0){
            tripType.text = getString(R.string.text_oneway)
        } else {
            tripType.text = getString(R.string.text_roundtrip)
        }
        ic_close.setOnClickListener {
            onBackPressed()
        }
    }

}