package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.view.View
import com.mobile.travelaja.R
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.mobile.travelaja.utility.Globals
import kotlinx.android.synthetic.main.page_detail_flight_and_train.view.*
import opsigo.com.domainlayer.model.my_booking.DetailFlightMyBookingModel
import opsigo.com.domainlayer.model.my_booking.ImportanPreProductInfoModel
import com.mobile.travelaja.module.my_booking.adapter.PassangerPurchaseAdapter
import com.mobile.travelaja.module.my_booking.adapter.ImportanPreProductInfoAdapter
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
import opsigo.com.domainlayer.model.my_booking.ItemPurchaseTrainModel

class PageDetailFlightAndTrainPurchase : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener
    val dataImportant by lazy { ArrayList<ImportanPreProductInfoModel>() }
    val dataPassager by lazy { ArrayList<Any>() }
    val adapterPassager by lazy { PassangerPurchaseAdapter(context,dataPassager) }
    val adapterFlightInfo by lazy { ImportanPreProductInfoAdapter(context,dataImportant) }

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
        if (Globals.isPertamina(context)){
            relatedItemButton.gone()
            btn_send_receip.gone()
        } else {
            relatedItemButton.visible()
            btn_send_receip.visible()
        }
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

    fun setDataFlight(data: DetailFlightMyBookingModel,totalPrice:Double) {
        tv_total_pricing.text = "IDR ${Globals.formatAmount(totalPrice)}"
        this.dataImportant.clear()
        this.dataPassager.clear()
        addImportant()
        this.dataPassager.addAll(data.passanger)
        adapterPassager.setData(this.dataPassager)
        adapterFlightInfo.setData(this.dataImportant)
    }

    private fun addImportant(){
        dataImportant.add(ImportanPreProductInfoModel("Passenger need to show this e-ticket and passenger identity when check-in.",R.drawable.ic_info_pre_flight))
        dataImportant.add(ImportanPreProductInfoModel("Please arrive at the airport for check-in 90 minutes prior departure.",R.drawable.ic_stop_watch))
        dataImportant.add(ImportanPreProductInfoModel("The time listed above is the local airport time.",R.drawable.ic_expiry_time))
    }

    fun setDataTrain(data: ItemPurchaseTrainModel,totalPrice: Double) {
        tv_total_pricing.text = "IDR ${Globals.formatAmount(totalPrice)}"
        this.dataImportant.clear()
        this.dataPassager.clear()
        addImportant()
        this.dataPassager.addAll(data.passager)
        adapterPassager.setData(dataPassager)
        adapterFlightInfo.setData(this.dataImportant)
    }
}