package com.opsicorp.hotel_feature.detail_hotel

import com.opsicorp.hotel_feature.description_hotel.DescriptionAndFacilityHotel
import opsigo.com.datalayer.request_model.accomodation.hotel.detail.DetailHotelRequest
import com.opsicorp.hotel_feature.select_room.SelectRoomActivity
import opsigo.com.datalayer.request_model.accomodation.hotel.room.RoomHotelRequest
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.module.item_custom.galery.CallbackGalery
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.module.item_custom.galery.MyGalery
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackDetailHotel
import opsigo.com.domainlayer.callback.CallbackRoomHotel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import com.squareup.picasso.Picasso
import android.webkit.WebViewClient
import com.opsicorp.hotel_feature.R
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.detail_hotel_activity.*
import kotlinx.android.synthetic.main.detail_hotel_layout.*
import opsigo.com.domainlayer.model.accomodation.hotel.*
import java.util.HashMap


class DetailHotelActivity :BaseActivity(),
        DescriptionAndFacilityHotel.OnclickButtonListener,
        View.OnClickListener,
        ToolbarOpsicorp.OnclickButtonListener,
        CallbackGalery{

    override fun getLayout(): Int { return R.layout.detail_hotel_activity }

    val dataFacility = ArrayList<FacilityHotelModel>()
    val dataReview   = ArrayList<RiviewHotelModel>()
    var latitude  :Double   = 0.0
    var longitude :Double   = 0.0
    lateinit var data: ResultListHotelModel

    val facilityAdapter by lazy { FacilityHotelAdapter(this,dataFacility) }
    val reviewAdapter   by lazy { RiviewHotelAdapter(this,dataReview) }

    override fun OnMain() {
        initToolbar()
        initOnclickListener()
        initRecyclerView()
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.hidenLogoCenter()
        toolbar.setToolbarColorView(R.color.transparent)
        toolbar.callbackOnclickToolbar(this)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }


    private fun initRecyclerView() {
        val layoutManagerReview = LinearLayoutManager(this)
        layoutManagerReview.orientation = LinearLayoutManager.HORIZONTAL
        rv_review.layoutManager = layoutManagerReview
        rv_review.itemAnimator = DefaultItemAnimator()
        rv_review.adapter = reviewAdapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_facility.layoutManager = layoutManager
        rv_facility.itemAnimator = DefaultItemAnimator()
        rv_facility.adapter = facilityAdapter

        reviewAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    Constants.REVIEW_HOTEL_SELECT -> {
                        val dialog = DialogDetailReview(
                                dataReview[position].time, //"Tue, 29 AUG 2020"
                                dataReview[position].name,
                                dataReview[position].massage,
                                data.rating)
                        showDialogFragment(dialog)
                    }
                }
            }
        })

        facilityAdapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {

            }
        })

        getData()

    }

    private fun initWebview() {
        val url = "<object width=\"360\" height=\"178\" style=\"border: 1px solid #cccccc;\" data=\"https://www.google.com/maps?q=${latitude},${longitude}&output=embed\" ></object>"
        webview.loadData(url, "text/html", null)
        webview.setWebViewClient(WebViewClient())
        webview.clearCache(true)
        webview.clearHistory()
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
    }

    private fun getData() {

        data = Serializer.deserialize(Constants.DATA_HOTEL, ResultListHotelModel::class.java)

        dataFacility.clear()
        dataReview.clear()
        setImageHotel(data.imageHotel)

        showLoadingLayout()
        GetDataAccomodation(getBaseUrl()).getHotelDetail(getToken(),dataRequestHotel(),object :CallbackDetailHotel{
            override fun success(data: ResultListHotelModel) {
                mappingDataDetail(data)
                getDataRoom()
            }

            override fun failed(error: String) {
                setLog("error "+error)
            }
        })

    }

    private fun mappingDataDetail(mData: ResultListHotelModel) {
        data.let {
            it.nameHotel          = mData.nameHotel
            it.hotelKey           = mData.hotelKey
            it.addressHotel       = mData.addressHotel
            it.lat                = mData.lat
            it.long               = mData.long
            it.reviews            = mData.reviews
            it.faciltyHotel       = mData.faciltyHotel
            it.galery             = mData.galery
            it.descriptioHotel    = mData.descriptioHotel
        }
        /*data.nameHotel          = mData.nameHotel
        data.hotelKey           = mData.hotelKey
        data.addressHotel       = mData.addressHotel
        data.lat                = mData.lat
        data.long               = mData.long
        data.reviews            = mData.reviews
        data.faciltyHotel       = mData.faciltyHotel
        data.galery             = mData.galery
        data.descriptioHotel    = mData.descriptioHotel*/
    }

    private fun getDataRoom() {
        GetDataAccomodation(getBaseUrl()).getCancellationPolicyHotel(getToken(),dataRequestRoom(),object :CallbackRoomHotel{
            override fun successLoad(data: ArrayList<SelectRoomModel>) {
                mappingDataRoom(data)
                setDataHotel()
            }

            override fun failedLoad(message: String) {
                showAllert("Sorry",message)
            }
        })
    }

    private fun mappingDataRoom(mData: ArrayList<SelectRoomModel>) {

        mData.forEach {
            val selectRoomModel = it
            val room = data.room.filter { it.roomKey == selectRoomModel.roomKey }.first()
            data.room[data.room.indexOf(room)].let {
                it.roomCodeHash = room.roomCodeHash
                it.breakfastType = room.breakfastType
                it.cancelLimit = room.cancelLimit
                it.isGuaranteedBooking = room.isGuaranteedBooking
                it.isFullCharge = room.isFullCharge
            }

            /*val room = data.room.filter { it.roomKey == selectRoomModel.roomKey }.first()
            data.room[data.room.indexOf(room)].roomCodeHash = room.roomCodeHash
            data.room[data.room.indexOf(room)].breakfastType = room.breakfastType
            data.room[data.room.indexOf(room)].cancelLimit = room.cancelLimit
            data.room[data.room.indexOf(room)].isGuaranteedBooking = room.isGuaranteedBooking
            data.room[data.room.indexOf(room)].isFullCharge = room.isFullCharge*/
        }
    }

    private fun setDataHotel() {
        dataFacility.addAll(this.data.faciltyHotel)
        dataReview.addAll(this.data.reviews)

        reviewAdapter.setData(dataReview)
        facilityAdapter.setData(dataFacility)

        latitude = this.data.lat.toDouble()
        longitude = this.data.long.toDouble()
        initWebview()

        val mList = ArrayList<FacilityHotelModel>()
        mList.addAll(this.data.faciltyHotel.filter { it.image.isEmpty() })
        showLineDescription(mList)

        tv_name_hotel.text = data.nameHotel
        tv_type_hotel.text = data.typeHotel
        tv_city.text       = data.city
        totalRiviews.text  = "from ${data.reviews.size} reviews"
        tv_rating.text     = data.rating
        tv_address_hotel.text = data.addressHotel


        when(data.starRating){
            "1.0"-> {
                ic_start1.visibility = View.VISIBLE
            }
            "2.0"-> {
                ic_start1.visibility = View.VISIBLE
                ic_start2.visibility = View.VISIBLE
            }
            "3.0"-> {
                ic_start1.visibility = View.VISIBLE
                ic_start2.visibility = View.VISIBLE
                ic_start3.visibility = View.VISIBLE
            }
            "4.0"-> {
                ic_start1.visibility = View.VISIBLE
                ic_start2.visibility = View.VISIBLE
                ic_start3.visibility = View.VISIBLE
                ic_start4.visibility = View.VISIBLE
            }
            "5.0"-> {
                ic_start1.visibility = View.VISIBLE
                ic_start2.visibility = View.VISIBLE
                ic_start3.visibility = View.VISIBLE
                ic_start4.visibility = View.VISIBLE
                ic_start5.visibility = View.VISIBLE
            }
        }

        btn_show_galery.setOnClickListener {
            showGalery()
        }
        hideLoadingLayout()
    }

    private fun dataRequestRoom(): HashMap<Any, Any> {
        val mData = RoomHotelRequest()
        mData.correlationId  = this.data.correlationId
        mData.hotelKey       = this.data.hotelKey
        mData.travelAgent    = Globals.getConfigCompany(this).defaultTravelAgent
        return Globals.classToHashMap(mData,RoomHotelRequest::class.java)
    }

    private fun dataRequestHotel(): HashMap<Any, Any> {
        val data = DetailHotelRequest()
        data.correlationId       = this.data.correlationId
        data.hotelKey            = this.data.hotelKey
        data.includeCancelPolicy = false
        data.includeImages       = true
        data.includeReviews      = true
        data.travelAgent         = Globals.getConfigCompany(this).defaultTravelAgent
        return Globals.classToHashMap(data,DetailHotelRequest::class.java)
    }

    private fun showLineDescription(mData: ArrayList<FacilityHotelModel>) {
        line_desc.callbackOnclickButton(this)
        line_desc.setDataListFacility(mData)
        line_desc.initLinePricing(data)
        line_desc.setDescriptions(data.descriptioHotel)
    }

    private fun setImageHotel(string: String) {
        Picasso.get()
                .load(string)
                .centerCrop()
                .fit()
                .into(img_hotel)
    }

    override fun nextStep() {
        Constants.DATA_HOTEL    = Serializer.serialize(data, ResultListHotelModel::class.java)
        setLog(Serializer.serialize(data.room))
        gotoActivity(SelectRoomActivity::class.java)
    }

    private fun initOnclickListener() {
        line_web.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0){
            line_web -> {
                Globals.openGoogleMap(this@DetailHotelActivity,latitude ,longitude)
            }
        }
    }

    fun showLoadingLayout(){
        line_detail_loading.visibility = View.VISIBLE
        line_detail.visibility         = View.GONE
        line_desc.visibility           = View.GONE
    }

    fun hideLoadingLayout(){
        line_detail_loading.visibility = View.GONE
        line_detail.visibility         = View.VISIBLE
        line_desc.visibility           = View.VISIBLE
    }

    fun showGalery(){
        MyGalery.setData(data.galery)
                .show(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MyGalery.result(requestCode,resultCode,data,this)
    }

    override fun result(data: GaleryModel) {

    }

    override fun cancelled() {

    }
}