package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.view.View
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.webkit.WebViewClient
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.databinding.PageDetailHotelBinding
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import com.mobile.travelaja.module.my_booking.model.PassangerPurchaseModel
import com.mobile.travelaja.module.my_booking.adapter.InfoSinggelTextAdapter
import com.mobile.travelaja.module.my_booking.model.ImportanPreProductInfoModel
import com.mobile.travelaja.module.item_custom.expandview.ExpandableLinearLayout
import com.mobile.travelaja.module.my_booking.adapter.ImportanPreProductInfoAdapter
import com.mobile.travelaja.module.my_booking.adapter.PassangerPurchaseAdapterHotel

class PageDetailHotelPurchase @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var binding: PageDetailHotelBinding

    lateinit var onclick : OnclickButtonListener
    var latitude = "-6.175906"
    var longitude = "106.8121863"
    var data = DetailMyBookingModel()
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

    init {
        binding = PageDetailHotelBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        setOrientation(VERTICAL)

        initWebview()
        initExpandView()
        initRecyclerView()
        addData()
        checkEmptyData()
    }

    private fun initExpandView() {
        binding.btnShowHotelMessage.setOnClickListener{
            expandListener(binding.expandHotelMessage)
        }
        binding.btnCanceledPolicy.setOnClickListener{
            expandListener(binding.expandCanceledPolicy)
        }
        binding.btnRemark.setOnClickListener{
            expandListener(binding.expandRemark)
        }
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
            binding.lineSparatorHotelMassage.visibility = View.GONE
        }
        else{
            binding.lineSparatorHotelMassage.visibility = View.VISIBLE
        }

        if (dataRemark.isEmpty()){
            binding.lineSeparatorRemark.visibility - View.GONE
        }
        else{
            binding.lineSeparatorRemark.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvHotelMassage.layoutManager = layoutManager
        binding.rvHotelMassage.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvHotelMassage.adapter = adapterHotelMessage

        adapterHotelMessage.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManagerRoom  = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerRoom.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvRoomDetail.layoutManager = layoutManagerRoom
        binding.rvRoomDetail.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvRoomDetail.adapter = adapterRoomfacility

        adapterRoomfacility.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegePolicy = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegePolicy.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvCancelPolicy.layoutManager = layoutManegePolicy
        binding.rvCancelPolicy.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvCancelPolicy.adapter       = adapterPolicy

        adapterPolicy.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegeRemark = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegeRemark.orientation  = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvRemark.layoutManager = layoutManegeRemark
        binding.rvRemark.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvRemark.adapter       = adapterRemark

        adapterRemark.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        val layoutManegeGuest = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManegeGuest.orientation  = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvRoomPassanger.layoutManager = layoutManegeGuest
        binding.rvRoomPassanger.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvRoomPassanger.adapter       = adapterGuest

        adapterGuest.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

    }

    private fun initWebview() {
        val url = "<object width=\"100%\" height=\"170\" style=\"border: none;margin:0 auto; padding:0; overflow-x:hidden;\" data=\"https://www.google.com/maps?q=${latitude},${longitude}&output=embed\" ></object>"
        binding.webview.loadData(url, "text/html", null)
        binding.webview.setWebViewClient(WebViewClient())
        binding.webview.clearCache(true)
        binding.webview.clearHistory()
        binding.webview.getSettings().setJavaScriptEnabled(true)
        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)

        binding.mapLine.setOnClickListener {
            openMapListener()
        }
        binding.btnDirection.setOnClickListener {
            openMapListener()
        }
    }

    private fun openMapListener() {
        Globals.openGoogleMap(context,latitude.toDouble(),longitude.toDouble())
    }


    interface OnclickButtonListener{
        fun onClicked()
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

    fun setDataHotel(mData: DetailMyBookingModel) {
        data = mData
        showDataHotel()
    }

    private fun showDataHotel() {

    }
}