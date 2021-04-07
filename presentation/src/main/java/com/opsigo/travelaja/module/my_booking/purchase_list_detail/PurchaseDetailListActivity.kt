package com.opsigo.travelaja.module.my_booking.purchase_list_detail

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.module.my_booking.adapter.PurchaseDetailTripFlightAdapter
import com.opsigo.travelaja.module.my_booking.model.*
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.detail_purchase_list_activity.*
import com.opsigo.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageFlight
import com.opsigo.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageHote
import com.opsigo.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageTrain
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.detail_purchase_list_header_flight.*


class PurchaseDetailListActivity : BaseActivity(),ToolbarOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.detail_purchase_list_activity }
    val dataFlight by lazy { ArrayList<PurchaseDetailTripFlightAndTrainModel>() }
    val adapterFlighDetail by lazy { PurchaseDetailTripFlightAdapter(this,dataFlight) }

    override fun OnMain() {
        initToolbar()
        validationLayout()
        Globals.scrollToUp(nested_view)
    }

    private fun validationLayout() {
        when(Globals.typeAccomodation){
            "Flight" -> {
                initPageFlightDetail()
            }
            "Train" -> {
                initPageTrainDetail()
            }
            "Hotel" -> {
                initPageHotelDetail()
            }
            "Tour" -> {

            }
        }
    }

    private fun initPageHotelDetail() {
        flight_header.visibility = View.GONE
        line_ticket.visibility   = View.VISIBLE
        lay_detail_hotel.visibility = View.VISIBLE
        lay_detail_train.visibility = View.GONE
        val data : DetailHotelPurchaseModel = getDataPageHote()

        pageHotel.showLayout()
        pageHotel.setDataHotel(data)
        pageFlightAndTrain.hidenLayout()
    }



    private fun initPageFlightDetail() {
        flight_header.visibility = View.VISIBLE
        line_ticket.visibility   = View.GONE

        initRecyclerView()

        val data : DetailFlightPurchaseModel = getDataPageFlight()
        adapterFlighDetail.setData(data.flights)

        pageFlightAndTrain.showLayout()
        pageFlightAndTrain.setDataFlight(data)
        pageHotel.hidenlayout()
    }


    private fun initRecyclerView() {
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_detail_flight.layoutManager = layoutManager
        rv_detail_flight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_detail_flight.adapter = adapterFlighDetail

        adapterFlighDetail.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    private fun initPageTrainDetail() {
        flight_header.visibility = View.GONE
        line_ticket.visibility   = View.VISIBLE
        lay_detail_hotel.visibility = View.GONE
        lay_detail_train.visibility = View.VISIBLE
        val data : DetailTrainPurchaseModel = getDataPageTrain()
        pageFlightAndTrain.showLayout()
        pageFlightAndTrain.setDataTrain(data)
        pageHotel.hidenlayout()
    }

    private fun initToolbar() {
        toolbar.showtitlePurchase()
        toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        toolbar.callbackOnclickToolbar(this)

    }

    private fun initPopUpMenu(view:View) {
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_purchase_detail_list, null)
        val btnReimbuirse = layout.findViewById(R.id.btn_reimbursement) as TextView
        val btnBackTopurchase = layout.findViewById(R.id.btn_back_to_purchase_list) as TextView
        val btnbackToHompage =  layout.findViewById(R.id.btn_back_to_homepage) as TextView

        btnBackTopurchase.setOnClickListener {  }
        btnReimbuirse.setOnClickListener {  }
        btnbackToHompage.setOnClickListener {  }

        Globals.showPopup(view,layout)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        initPopUpMenu(toolbar.getImageCart())
    }

}