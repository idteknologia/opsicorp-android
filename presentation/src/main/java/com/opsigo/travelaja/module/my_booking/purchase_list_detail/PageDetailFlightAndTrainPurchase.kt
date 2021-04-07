package com.opsigo.travelaja.module.my_booking.purchase_list_detail

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.adapter.ImportanPreProductInfoAdapter
import com.opsigo.travelaja.module.my_booking.adapter.PassangerPurchaseAdapter
import com.opsigo.travelaja.module.my_booking.model.DetailFlightPurchaseModel
import com.opsigo.travelaja.module.my_booking.model.DetailTrainPurchaseModel
import com.opsigo.travelaja.module.my_booking.model.ImportanPreProductInfoModel
import com.opsigo.travelaja.module.my_booking.model.PassangerPurchaseModel
import kotlinx.android.synthetic.main.page_detail_flight_and_train.view.*

class PageDetailFlightAndTrainPurchase : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener
    val data by lazy { ArrayList<ImportanPreProductInfoModel>() }
    val dataPassager by lazy { ArrayList<PassangerPurchaseModel>() }
    val adapterPassager by lazy { PassangerPurchaseAdapter(context,dataPassager) }
    val adapterFlightInfo by lazy { ImportanPreProductInfoAdapter(context,data) }


    fun callbackOnclickButton(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        init()
    }


    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.page_detail_flight_and_train, this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_pree_flight_info.layoutManager = layoutManager
        rv_pree_flight_info.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_pree_flight_info.adapter = adapterFlightInfo

        val layoutManagerPassanger = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerPassanger.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_passanger.layoutManager = layoutManagerPassanger
        rv_passanger.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_passanger.adapter = adapterPassager
    }


    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {

    }
    

    fun hidenLayout() {
        visibility = View.GONE
    }

    fun showLayout(){
        visibility = View.VISIBLE
    }

    fun setDataFlight(data: DetailFlightPurchaseModel) {
        tv_total_pricing.text = data.totalPrize
        this.data.clear()
        this.dataPassager.clear()
        this.data.addAll(data.importan)
        this.dataPassager.addAll(data.passanger)
        adapterPassager.setData(this.dataPassager)
        adapterFlightInfo.setData(this.data)
    }

    fun setDataTrain(data: DetailTrainPurchaseModel) {
        tv_tittle_prize.text = data.totalPrize
        this.data.clear()
        this.dataPassager.clear()
        this.data.addAll(data.importan)
        this.dataPassager.addAll(data.passanger)
        adapterPassager.setData(dataPassager)
        adapterFlightInfo.setData(this.data)
    }
}