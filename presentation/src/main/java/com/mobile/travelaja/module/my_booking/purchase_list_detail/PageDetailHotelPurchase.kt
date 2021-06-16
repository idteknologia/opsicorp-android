package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.expandview.ExpandableLinearLayout
import com.mobile.travelaja.module.my_booking.adapter.ImportanPreProductInfoAdapter
import com.mobile.travelaja.module.my_booking.adapter.InfoSinggelTextAdapter
import com.mobile.travelaja.module.my_booking.adapter.PassangerPurchaseAdapterHotel
import com.mobile.travelaja.module.my_booking.model.DetailHotelPurchaseModel
import com.mobile.travelaja.module.my_booking.model.ImportanPreProductInfoModel
import com.mobile.travelaja.module.my_booking.model.PassangerPurchaseModel
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.page_detail_hotel.view.*

class PageDetailHotelPurchase : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    var latitude = "-6.175906"
    var longitude = "106.8121863"

    val dataHotelMessage by lazy { ArrayList<String>() }
    val dataPolicy       by lazy { ArrayList<String>() }
    val dataRemark       by lazy { ArrayList<String>() }
    val dataRoomFacility by lazy { ArrayList<ImportanPreProductInfoModel>() }
    val dataGuest        by lazy { ArrayList< PassangerPurchaseModel>() }

    val adapterGuest     by lazy { PassangerPurchaseAdapterHotel(context, dataGuest)}
    val adapterRemark    by lazy { InfoSinggelTextAdapter(context,dataRemark) }
    val adapterPolicy    by lazy { InfoSinggelTextAdapter(context,dataPolicy) }
    val adapterRoomfacility by lazy { ImportanPreProductInfoAdapter(context,dataRoomFacility) }
    val adapterHotelMessage by lazy { InfoSinggelTextAdapter(context,dataHotelMessage) }

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
        View.inflate(context, R.layout.page_detail_hotel, this)

        initWebview()
        initExpandView()
        initRecyclerView()
        addData()
        checkEmptyData()
    }

    private fun initExpandView() {
        btn_show_hotel_message.setOnClickListener(this)
        btn_canceled_policy.setOnClickListener(this)
        btn_remark.setOnClickListener(this)
    }



    private fun addData() {

        dataHotelMessage.clear()
        dataPolicy      .clear()
        dataRemark      .clear()
        dataRoomFacility.clear()
        dataGuest       .clear()

        dataPolicy.addAll(DummyDataPurchaseFlight.addDataCancelPolicy())
        dataRemark.addAll(DummyDataPurchaseFlight.addDataRemark())
        dataRoomFacility.addAll(DummyDataPurchaseFlight.addDataRoomFacility())
        dataGuest.addAll(DummyDataPurchaseFlight.addDataGuest())
        dataHotelMessage.addAll(DummyDataPurchaseFlight.addDataHotelMessage())

        adapterHotelMessage.setData(dataHotelMessage)
        adapterPolicy.setData(dataPolicy)
        adapterRemark.setData(dataRemark)
        adapterRoomfacility.setData(dataRoomFacility)
        adapterGuest.setData(dataGuest)

    }

    private fun checkEmptyData() {
        if (dataHotelMessage.isEmpty()){
            line_sparator_hotel_massage.visibility = View.GONE
        }
        else{
            line_sparator_hotel_massage.visibility = View.VISIBLE
        }

        if (dataRemark.isEmpty()){
            line_separator_remark.visibility - View.GONE
        }
        else{
            line_separator_remark.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_hotel_massage.layoutManager = layoutManager
        rv_hotel_massage.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_hotel_massage.adapter = adapterHotelMessage

        adapterHotelMessage.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManagerRoom  = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerRoom.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_room_detail.layoutManager = layoutManagerRoom
        rv_room_detail.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_room_detail.adapter = adapterRoomfacility

        adapterRoomfacility.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegePolicy = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegePolicy.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_cancel_policy.layoutManager = layoutManegePolicy
        rv_cancel_policy.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_cancel_policy.adapter       = adapterPolicy

        adapterPolicy.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegeRemark = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegeRemark.orientation  = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_remark.layoutManager = layoutManegeRemark
        rv_remark.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_remark.adapter       = adapterRemark

        adapterRemark.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegeGuest = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegeGuest.orientation  = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_room_passanger.layoutManager = layoutManegeGuest
        rv_room_passanger.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_room_passanger.adapter       = adapterGuest

        adapterGuest.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

    }

    private fun initWebview() {
        val url = "<object width=\"100%\" height=\"170\" style=\"border: none;margin:0 auto; padding:0; overflow-x:hidden;\" data=\"https://www.google.com/maps?q=${latitude},${longitude}&output=embed\" ></object>"
        webview.loadData(url, "text/html", null)
        webview.setWebViewClient(WebViewClient())
        webview.clearCache(true)
        webview.clearHistory()
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)

        map_line.setOnClickListener {
            openMapListener()
        }
        btn_direction.setOnClickListener {
            openMapListener()
        }
    }

    private fun openMapListener() {
        Globals.openGoogleMap(context,latitude.toDouble(),longitude.toDouble())
    }


    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {
        when(v){
            btn_show_hotel_message -> {
                expandListener(expand_hotel_message)
            }
            btn_canceled_policy -> {
                expandListener(expand_canceled_policy)
            }
            btn_remark -> {
                expandListener(expand_remark)
            }
        }
    }

    private fun expandListener(expandHotelMessage: ExpandableLinearLayout) {
        if (expandHotelMessage.isExpanded){
            expandHotelMessage.collapse()
        }
        else {
            expandHotelMessage.expand()
        }
    }

    fun showLayout() {
        visibility = View.VISIBLE
    }

    fun hidenlayout() {
        visibility = View.GONE
    }

    fun setDataHotel(data: DetailHotelPurchaseModel) {

    }
}