package com.opsicorp.train_feature.detail

import com.opsigo.travelaja.module.accomodation.dialog.accomodation_reason_trip.SelectReasonAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.domainlayer.model.accomodation.train.ConfirmationTrainModel
import com.opsicorp.train_feature.booking_contact.BookingContactTrain
import com.opsicorp.train_feature.adapter.ConfirmationTrainAdapter
import kotlinx.android.synthetic.main.detail_prize_bottom_train.*
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.confirm_train_order.*
import com.opsigo.travelaja.module.cart.model.CartModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import com.opsicorp.train_feature.R
import java.text.SimpleDateFormat
import java.lang.Exception
import android.view.View
import kotlinx.android.synthetic.main.confirm_train_order.line_reason_code

class ConfirmOrderTrainActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener,
        View.OnClickListener{

    override fun getLayout(): Int { return R.layout.confirm_train_order }

    val data = ArrayList<ConfirmationTrainModel>()
    var allreadySelectReasonCode = false
    val adapter by lazy { ConfirmationTrainAdapter(this, data) }

    override fun OnMain() {
        btn_next.setTextButton("Book")
        btn_next.callbackOnclickButton(this)
        ic_back.setOnClickListener(this)

        initDataView()
        initPrize()

        Globals.delay(100,object :Globals.DelayCallback{
            override fun done() {
                collapsePrize()
            }
        })
    }

    private fun initDataView() {
        tv_price.text = "IDR 0"
        initRecyclerView()
        if (intent.getStringExtra(Constants.FROM_CART)!=null){
            try {
                setDataDetail()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        else {
            setDataOrder()
        }
    }

    private fun initPrize() {
        tv_price.setOnClickListener {
            showOrHideDetailPrize()
        }
        ic_image.setOnClickListener {
            showOrHideDetailPrize()
        }
        tv_title_prize.setOnClickListener {
            showOrHideDetailPrize()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrize()
        }

        if (Globals.typeAccomodation=="Train"){
            val dataListTrain = Serializer.deserialize(Constants.DATA_LIST_TRAIN, DataListOrderAccomodation::class.java)

            tv_prize_departure.text   = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)
            tv_station_departure.text = dataListTrain.dataTrain[0].nameStation

            if (dataListTrain.dataTrain.size>1){
                line_arrival.visibility     = View.VISIBLE
                tv_prize_arrival.text       = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[1].price)
                tv_station_arrival.text     = dataListTrain.dataTrain[1].nameStation
                tv_price_total.text         = "IDR "+Globals.formatAmount((dataListTrain.dataTrain[0].price.toInt()+dataListTrain.dataTrain[1].price.toInt()).toString())
                tv_price.text               = "IDR "+Globals.formatAmount((dataListTrain.dataTrain[0].price.toInt()+dataListTrain.dataTrain[1].price.toInt()).toString())
            }
            else{
                line_arrival.visibility     = View.GONE
                tv_price_total.text         = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)
                tv_price.text               = "IDR "+Globals.formatAmount(dataListTrain.dataTrain[0].price)

            }
        }
    }

    private fun setDataDetail() {
        data.clear()
        val dataOrder                = Serializer.deserialize(intent.getStringExtra(Constants.DATA_DETAIL_TRAIN),CartModel::class.java)
        val dataTrain                = dataOrder.dataCardTrain

        val mData                    = ConfirmationTrainModel()
        mData.pnr_code               = dataTrain.pnrCode
        mData.status                 = dataTrain.status
        mData.title_train            = dataTrain.titleTrain
        mData.class_type             = dataTrain.classCode
        mData.number_sheet           = dataTrain.seatNumber

        val formatter                = SimpleDateFormat("dd MMM")
        val formatter2               = SimpleDateFormat("EEEE, yyyy MMM dd")

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

        mData.total_passager         = if ("".isEmpty()) "Adult x 1" else ""
        mData.total_prize            = dataTrain.price //"IDR "+Globals.formatAmount("")

        data.add(mData)


    }

    fun showOrHideDetailPrize(){
        if (body_prize.isExpanded){
            collapsePrize()
        }
        else{
            expandPrize()
        }
    }

    private fun expandPrize() {
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down_green))
        line_shadow.visibility  = View.VISIBLE
        tv_including.visibility = View.GONE
        body_prize.expand()
    }
    private fun collapsePrize() {
        ic_image.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_green))
        tv_including.visibility = View.VISIBLE
        line_shadow.visibility  = View.GONE
        body_prize.collapse()
    }


    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_confirmation_order_train.layoutManager = layoutManager
        rv_confirmation_order_train.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_confirmation_order_train.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    private fun setDataOrder() {
        val dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        val dataListTrain = Serializer.deserialize(Constants.DATA_LIST_TRAIN, DataListOrderAccomodation::class.java)

        data.clear()

        dataListTrain.dataTrain.forEachIndexed { index, resultListTrainModel ->
            val mData = ConfirmationTrainModel()
            mData.pnr_code = System.currentTimeMillis().toString()
            mData.status = resultListTrainModel.statusTrain
            mData.title_train = resultListTrainModel.titleTrain
            mData.class_type = resultListTrainModel.className
            mData.number_sheet = resultListTrainModel.totalSeat

            val formatter  = SimpleDateFormat("dd MMM")
            val formatter2 = SimpleDateFormat("EEEE, yyyy MMM dd")

            mData.date_arrival_depart = formatter2.format(resultListTrainModel.dateArrival)
            mData.timeDeparture = resultListTrainModel.timeDeparture
            mData.dateDeparture = formatter.format(resultListTrainModel.dateDeparture)
            mData.line_total_duration   = resultListTrainModel.duration

            mData.time_arrival = resultListTrainModel.timeArrifal
            mData.date_arrival = formatter.format(resultListTrainModel.dateArrival)


            mData.notcomply = resultListTrainModel.isViolatedTrainRules

            if (Globals.ALL_READY_SELECT_DEPARTING){
                mData.name_arrival = resultListTrainModel.origin
                mData.name_departure = resultListTrainModel.destination
                mData.name_station_departure = dataOrder.destinationStationName
                mData.name_station_arrival = dataOrder.originStationName
            }
            else {
                mData.name_arrival = resultListTrainModel.destination
                mData.name_departure = resultListTrainModel.origin
                mData.name_station_departure = dataOrder.originStationName
                mData.name_station_arrival = dataOrder.destinationStationName
            }

            mData.total_passager = if (dataOrder.totalPassagerString.isEmpty()) "Adult x 1" else dataOrder.totalPassagerString
//            mData.total_prize    = resultListTrainModel.price
            mData.total_prize    = "IDR "+Globals.formatAmount(resultListTrainModel.price)


            data.add(mData)

        }

        adapter.setData(data)
        showOrHideNotComply()

    }

    private fun showOrHideNotComply() {
        if (data.filter { it.notcomply }.isNotEmpty()){
            line_reason_code.visibility = View.VISIBLE
            line_reason_code.setOnClickListener(this)
        }
        else {
            line_reason_code.visibility = View.GONE
        }
    }

    override fun onClicked() {
        if (data.filter { it.notcomply }.isNotEmpty()){
            if (allreadySelectReasonCode){
                gotoActivity(BookingContactTrain::class.java)
            }
            else {
                showAllert("Sorry","Please Select ReasonCode")
            }
        }
        else{
            gotoActivity(BookingContactTrain::class.java)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Constants.DATA_LIST_TRAIN = ""
    }

    private fun selectReasonCode() {
        val selectAccomodation = SelectReasonAccomodation(true,R.style.CustomDialog, Constants.DATA_REASON_CODE_TRAIN)
        selectAccomodation.show(supportFragmentManager,"dialog")

        selectAccomodation.setCallbackListener(object :SelectReasonAccomodation.CallbackSelectPreferance{
            override fun callback(model: ReasonCodeModel) {

                allreadySelectReasonCode = true
                val dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
                dataOrder.reaseonCode = model.codeBrief+"-"+model.description

                Constants.DATA_ORDER_TRAIN = Serializer.serialize(dataOrder)
            }
        })

    }

    override fun onClick(p0: View?) {
        when(p0){
            line_reason_code ->{
                selectReasonCode()
            }
            ic_back->{
                Constants.DATA_LIST_TRAIN = ""
                finish()
            }
        }
    }
}