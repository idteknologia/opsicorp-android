package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.net.Uri
import android.view.View
import android.content.*
import android.util.Log
import com.mobile.travelaja.R
import android.widget.TextView
import android.view.LayoutInflater
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.base.BaseActivityBinding
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import com.mobile.travelaja.databinding.DetailPurchaseListActivityBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.my_booking.PurchaseDetailTripFlightAndTrainModel
import com.mobile.travelaja.module.my_booking.adapter.SegmentMybookingAdapter
import opsigo.com.datalayer.mapper.Serializer

class PurchaseDetailListActivity : BaseActivityBinding<DetailPurchaseListActivityBinding>(),ToolbarOpsicorp.OnclickButtonListener{

    val dataFlight by lazy { ArrayList<PurchaseDetailTripFlightAndTrainModel>() }
    val adapterSegemntFligh by lazy { SegmentMybookingAdapter(this,dataFlight) }
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
        Log.e("TAG",Serializer.serialize(dataPurchaseDetail))
        Log.e("TAG",Serializer.serialize(positionSelectedItem))
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
//        val data : DetailHotelPurchaseModel = getDataPageHote()
        viewBinding.toolbar.showtitlePurchaseHotel()
        viewBinding.flightHeader.lineFlightHeader.visibility  = View.GONE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.GONE
        viewBinding.layHeaderHotel.lineDetailHotel.visibility = View.VISIBLE
        viewBinding.lineTicket.visibility                     = View.VISIBLE

        viewBinding.tvTitleTicket.text  = getString(R.string.title_ticket_hotel)
        viewBinding.tvPnrCode.text      = dataPurchaseDetail.dataHotel.voucerCode
        viewBinding.layHeaderHotel.tvNameHotel.text         = dataPurchaseDetail.dataHotel.hotelName
        viewBinding.layHeaderHotel.tvCity.text              = dataPurchaseDetail.dataHotel.area
        viewBinding.layHeaderHotel.tvChekinDate.text        = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkInDate.toString(),"yyyy-MM-dd HH:mm:ss","E, dd MMM")
        viewBinding.layHeaderHotel.tvCheckinTime.text       = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkInDate.toString(),"yyyy-MM-dd HH:mm:ss","HH:mm")
        viewBinding.layHeaderHotel.tvCheckoutDate.text      = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkOutDate.toString(),"yyyy-MM-dd HH:mm:ss","E, dd MMM")
        viewBinding.layHeaderHotel.tvCheckoutTime.text      = DateConverter().getDate(dataPurchaseDetail.dataHotel.checkOutDate.toString(),"yyyy-MM-dd HH:mm:ss","HH:mm")
        viewBinding.layHeaderHotel.tvNight.text             = "${dataPurchaseDetail.dataHotel.totalNight} night(s)"
        viewBinding.layHeaderHotel.tvNameBookingContact.text = dataPurchaseDetail.bookingContact?.fullName.toString()

        viewBinding.pageHotel.showLayout()
        viewBinding.pageHotel.setDataHotel(dataPurchaseDetail)
        Globals.viewRatingStarHotel(arrayListOf(viewBinding.layHeaderHotel.icStart1,viewBinding.layHeaderHotel.icStart2,viewBinding.layHeaderHotel.icStart3,viewBinding.layHeaderHotel.icStart4,viewBinding.layHeaderHotel.icStart5),dataPurchaseDetail.dataHotel.ratingStar.toDouble().toString())
        viewBinding.pageFlightAndTrain.hidenLayout()
    }

    private fun initPageFlightDetail() {
        viewBinding.flightHeader.lineFlightHeader.visibility = View.VISIBLE
        viewBinding.flightHeader.pnrCodeFlight.text          = dataPurchaseDetail.dataFlight[positionSelectedItem].pnrCode.toString()
        viewBinding.flightHeader.icCopyCode.setOnClickListener { Globals.copyText(dataPurchaseDetail.dataFlight[positionSelectedItem].pnrCode.toString(),this) }
        viewBinding.lineTicket.visibility = View.GONE
        viewBinding.toolbar.showtitlePurchaseTrain(dataPurchaseDetail.dataFlight[positionSelectedItem].originCity,dataPurchaseDetail.dataFlight[positionSelectedItem].destinationCity)

        initRecyclerView()
        adapterSegemntFligh.setData(dataPurchaseDetail.dataFlight[positionSelectedItem].Segment)

        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataFlight(dataPurchaseDetail.dataFlight[positionSelectedItem],dataPurchaseDetail.totalPaid)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initRecyclerView() {
        val layoutManager: androidx.recyclerview.widget.LinearLayoutManager
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        viewBinding.flightHeader.rvDetailFlight.layoutManager = layoutManager
        viewBinding.flightHeader.rvDetailFlight.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        viewBinding.flightHeader.rvDetailFlight.adapter = adapterSegemntFligh
    }

    private fun initPageTrainDetail() {
        viewBinding.lineTicket.visibility                     = View.VISIBLE
        viewBinding.flightHeader.lineFlightHeader.visibility  = View.GONE
        viewBinding.layHeaderHotel.lineDetailHotel.visibility = View.GONE
        viewBinding.layDetailTrain.lineDetailTrain.visibility = View.VISIBLE

        val data = dataPurchaseDetail.dataTrain[positionSelectedItem]
        viewBinding.layDetailTrain.tvNameTrain.text           = data.nameTrain
        viewBinding.layDetailTrain.tvTrainCode.text           = data.trainCode
        viewBinding.layDetailTrain.tvClassNameTrain.text      = data.className
        viewBinding.layDetailTrain.tvTimeDeparture.text       = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd HH:mm","HH:mm")
        viewBinding.layDetailTrain.tvTimeArrival.text         = DateConverter().getDate(data.dateArrival,"yyyy-MM-dd HH:mm","HH:mm")
        viewBinding.layDetailTrain.tvDateDeparture.text       = DateConverter().getDate(data.dateDeparture,"yyyy-MM-dd HH:mm","dd MMM")
        viewBinding.layDetailTrain.tvDateArrival.text         = DateConverter().getDate(data.dateArrival,"yyyy-MM-dd HH:mm","dd MMM")

        viewBinding.layDetailTrain.tvDepartureTrain.text             = data.originCity
        viewBinding.layDetailTrain.tvStationDepartureTrain.text      = data.originStation
        viewBinding.layDetailTrain.tvArrivalTrain.text               = data.destinationCity
        viewBinding.layDetailTrain.tvArrivalStationTrain.text        = data.destinationStation
        viewBinding.layDetailTrain.tvTimeDurationTrain.text          = data.durationTime

        viewBinding.tvPnrCode.text = dataPurchaseDetail.dataTrain[positionSelectedItem].pnrCode
        viewBinding.toolbar.showtitlePurchaseTrain(dataPurchaseDetail.dataTrain[positionSelectedItem].originCity.toString(),dataPurchaseDetail.dataTrain[positionSelectedItem].destinationCity.toString())
//        val data : DetailTrainPurchaseModel = getDataPageTrain()
        viewBinding.pageFlightAndTrain.showLayout()
        viewBinding.pageFlightAndTrain.setDataTrain(dataPurchaseDetail.dataTrain[positionSelectedItem],dataPurchaseDetail.totalPaid)
        viewBinding.pageHotel.hidenlayout()
    }

    private fun initToolbar() {
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