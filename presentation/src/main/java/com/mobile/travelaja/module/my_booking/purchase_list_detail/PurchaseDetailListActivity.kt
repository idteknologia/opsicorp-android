package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.URLUtil
import android.widget.TextView
import androidx.core.content.FileProvider
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.DetailPurchaseListActivityBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.my_booking.adapter.PurchaseDetailTripFlightAdapter
import com.mobile.travelaja.module.my_booking.model.DetailFlightPurchaseModel
import com.mobile.travelaja.module.my_booking.model.DetailHotelPurchaseModel
import com.mobile.travelaja.module.my_booking.model.DetailTrainPurchaseModel
import com.mobile.travelaja.module.my_booking.model.PurchaseDetailTripFlightAndTrainModel
import com.mobile.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageFlight
import com.mobile.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageHote
import com.mobile.travelaja.module.my_booking.purchase_list_detail.DummyDataPurchaseFlight.getDataPageTrain
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import java.io.File


class PurchaseDetailListActivity : BaseActivityBinding<DetailPurchaseListActivityBinding>(),ToolbarOpsicorp.OnclickButtonListener{

    val dataFlight by lazy { ArrayList<PurchaseDetailTripFlightAndTrainModel>() }
    val adapterFlighDetail by lazy { PurchaseDetailTripFlightAdapter(this,dataFlight) }
    var positionSelectedItem = 0
    lateinit var dataPurchaseDetail: DetailMyBookingModel

    override fun bindLayout(): DetailPurchaseListActivityBinding {
        return DetailPurchaseListActivityBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
        setDataIntent()
        validationLayout()
        Globals.scrollToUp(viewBinding.nestedView)
    }

    private fun setDataIntent() {
        positionSelectedItem = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.KEY_POSITION_SELECTED_ITEM,0)!!
        dataPurchaseDetail   = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getParcelable<DetailMyBookingModel>(Constants.KEY_DATA_PARCELABLE)!!
    }

    private fun validationLayout() {
        when(dataPurchaseDetail.itemType){
            Constants.TripType.Airline -> {
                initPageFlightDetail()
            }
            Constants.TripType.KAI -> {
                initPageTrainDetail()
            }
            Constants.TripType.Hotel -> {
                initPageHotelDetail()
            }
        }
    }

    private fun initPageHotelDetail() {
        viewBinding.flightHeader.lineFlightHeader.visibility = View.GONE
        viewBinding.lineTicket.visibility   = View.VISIBLE
        viewBinding.layDetailHotel.lineDetailHotel.visibility = View.VISIBLE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.GONE
        viewBinding.toolbar.showtitlePurchaseHotel()
//        val data : DetailHotelPurchaseModel = getDataPageHote()

        viewBinding.pageHotel.showLayout()
        viewBinding.pageHotel.setDataHotel(dataPurchaseDetail)
        viewBinding.pageFlightAndTrain.hidenLayout()
    }

    private fun initPageFlightDetail() {
        viewBinding.flightHeader.lineFlightHeader.visibility = View.VISIBLE
        viewBinding.lineTicket.visibility = View.GONE

        initRecyclerView()

        val data: DetailFlightPurchaseModel = getDataPageFlight()
        adapterFlighDetail.setData(data.flights)

        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataFlight(data)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initRecyclerView() {
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        viewBinding.flightHeader.rvDetailFlight.layoutManager = layoutManager
        viewBinding.flightHeader.rvDetailFlight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        viewBinding.flightHeader.rvDetailFlight.adapter = adapterFlighDetail

        adapterFlighDetail.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    private fun initPageTrainDetail() {
        viewBinding.flightHeader.lineFlightHeader.visibility = View.GONE
        viewBinding.lineTicket.visibility   = View.VISIBLE
        viewBinding.layDetailHotel.lineDetailHotel.visibility = View.GONE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.VISIBLE
        val data : DetailTrainPurchaseModel = getDataPageTrain()
        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataTrain(data)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initToolbar() {
        viewBinding.toolbar.showtitlePurchase()
        viewBinding.toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        viewBinding.toolbar.callbackOnclickToolbar(this)

    }

    private fun initPopUpMenu(view:View) {
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_purchase_detail_list, null)
        val btnReimbuirse     = layout.findViewById(R.id.btn_reimbursement) as TextView
        val btnBackTopurchase = layout.findViewById(R.id.btn_back_to_purchase_list) as TextView
        val btnbackToHompage  =  layout.findViewById(R.id.btn_back_to_homepage) as TextView
        val btnDownload       = layout.findViewById(R.id.btn_download) as TextView

        val dialog = Globals.showPopup(view,layout)

        btnBackTopurchase.setOnClickListener { dialog.dismiss() }
        btnReimbuirse.setOnClickListener { dialog.dismiss() }
        btnbackToHompage.setOnClickListener { dialog.dismiss() }
        btnDownload.setOnClickListener{
            showDialog("Please Wait")
            dialog.dismiss()
            Globals.downloadFile("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",this,object :Globals.CallbackDownload{
                override fun succeessDownload(parse: Uri, downloadMimeType: String) {
                    hideDialog()
                    Globals.openDownloadedAttachment(this@PurchaseDetailListActivity,parse,downloadMimeType)
                }

                override fun failedDownload() {
                    hideDialog()
                }
            })
//            Globals.downloadFile("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1024px-Image_created_with_a_mobile_phone.png",this)
        }
    }




    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        initPopUpMenu(viewBinding.toolbar.getImageCart())
    }

}