package com.opsicorp.train_feature.detail

import android.view.View
import com.opsicorp.train_feature.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.module.cart.model.CartModel
import com.opsicorp.train_feature.adapter.ConfirmationTrainAdapter
import kotlinx.android.synthetic.main.activity_detail_cart_train.*
import kotlinx.android.synthetic.main.confirm_train_order.line_reason_code
import opsigo.com.domainlayer.model.accomodation.train.ConfirmationTrainModel

class CartTrainDetailActivity : BaseActivity() {

    val data = ArrayList<ConfirmationTrainModel>()
    val adapter by lazy { ConfirmationTrainAdapter(this, data) }

    override fun getLayout(): Int {
        return R.layout.activity_detail_cart_train
    }

    override fun OnMain() {
        setDataDetail()
    }

    private fun setDataDetail() {
        data.clear()
        val dataOrder                = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_TRAIN), CartModel::class.java)
        val dataTrain                = dataOrder.dataCardTrain

        val mData                    = ConfirmationTrainModel()
        mData.pnr_code               = dataTrain.pnrCode
        mData.status                 = dataTrain.status
        mData.title_train            = dataTrain.titleTrain
        mData.class_type             = dataTrain.classCode
        mData.number_sheet           = dataTrain.seatNumber


        mData.date_arrival_depart    = dataTrain.dateArrival //formatter2.format("")
        mData.timeDeparture          = dataTrain.timeDeparture
        mData.dateDeparture          = dataTrain.dateDeparture//formatter.format("")
        mData.line_total_duration    = ""// dataTrain.totalDiration

        mData.time_arrival           = dataTrain.timeArrival
        mData.date_arrival           = dataTrain.dateArrival

        mData.name_departure         = "" // dataTrain.nameDeparture
        mData.name_arrival           = "" // dataTrain.nameArrival

        mData.notcomply              = false // dataTrain.notComply
        mData.name_station_departure = dataTrain.stationDeparture
        mData.name_station_arrival   = dataTrain.stationArrival

        mData.total_passager         = if ("".isEmpty()) "${getString(R.string.txt_adult)} x 1" else ""
        mData.total_prize            = dataTrain.price //"IDR "+Globals.formatAmount("")

        data.add(mData)

        if (mData.notcomply){
            line_reason_code.visibility = View.VISIBLE
            tv_reason_code.text = mData.dataReasonCode.codeBrief
            tv_reason_code_desc.text = mData.dataReasonCode.description
        }
        else {
            line_reason_code.visibility = View.GONE
        }

        adapter.setData(data)
        setTotalPrice(mData)
    }

    private fun setTotalPrice(mData: ConfirmationTrainModel) {
        name_train.text = mData.title_train
        tv_total_price_train1.text  = "${Globals.formatAmount(mData.total_prize)} IDR"
        tv_total_price_service_fee.text = "0 IDR"
        tv_total_price_tax.text    = "0 IDR"
        tv_total_amount.text        = "${Globals.formatAmount(mData.total_prize)} IDR"
    }
}