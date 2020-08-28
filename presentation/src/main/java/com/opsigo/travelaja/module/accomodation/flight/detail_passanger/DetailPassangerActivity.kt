package com.opsigo.travelaja.module.accomodation.flight.detail_passanger

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.cart.activity.NewCartActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.datalayer.database.AccessDb
import opsigo.com.datalayer.database.OrderDbEntity
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import kotlinx.android.synthetic.main.detail_passanger_activity.*

class DetailPassangerActivity : BaseActivity()
        ,ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.detail_passanger_activity
    }

    var totalAdult = 0
    var totalChild = 0
    var totalInfant = 0
    var data = ArrayList<DetailPassangerModel>()
    var adapterExpand = DetailPassangerAdapter(this)

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        btn_next.callbackOnclickButton(this)
    }

    private fun initToolbar() {
        toolbar.setToolbarColorView(R.color.white)
        toolbar.changeImageBtnBack(R.drawable.ic_back_dark_blue)
        toolbar.hidenBtnCart()
        toolbar.hidenLogoCenter()
        toolbar.callbackOnclickToolbar(this)
        btn_next.callbackOnclickButton(this)
        btn_next.changeBackground(R.color.colorPrimary)
        btn_next.changeTextColorButton(R.color.colorPureWhite)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_passanger.layoutManager = layoutManager
        rv_passanger.itemAnimator = DefaultItemAnimator()
        rv_passanger.adapter = adapterExpand

        adapterExpand.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })

        if (Globals.typeAccomodation=="Flight"){
            totalAdult  = totalPassanger("Flight").first.split(",")[0].toInt()
            totalChild  = totalPassanger("Flight").first.split(",")[1].toInt()
            totalInfant = totalPassanger("Flight").first.split(",")[2].toInt()
        }
        else if(Globals.typeAccomodation=="Train"){
            totalAdult  = totalPassanger("Train").second.split(",")[0].toInt()
            totalChild  = totalPassanger("Train").second.split(",")[1].toInt()
            totalInfant = totalPassanger("Train").second.split(",")[2].toInt()
        }


        for (i in  1..totalAdult){
            val mData = DetailPassangerModel()
            mData.age = "Adult"
            mData.title = "MR."
            data.add(mData)
        }

        for (i in  1..totalChild){
            val mData = DetailPassangerModel()
            mData.age = "Child"
            mData.title = "Mrs."
            data.add(mData)
        }

        for (i in  1..totalInfant){
            val mData = DetailPassangerModel()
            mData.age = "Infant"
            mData.title = "Ms."
            data.add(mData)
        }


        adapterExpand.setData(data)
    }

    fun totalPassanger(type: String): Pair<String,String> {
        val dataOrderflight = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        val dataOrderTrain  = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)

        /*var totals = dataOrder.totalPassagerString
        if (totals.contains("Adults")){
            totals  = totals.replace("Adults",",")
        }
        if(totals.contains("Child")){
            totals = totals.replace("Child",",")
        }
        if(totals.contains("Infant")){
            totals = totals.replace("Infant",",")
        }

        var totalPassanger = 0
        totals.replace(" ","").split(",").forEachIndexed { index, s ->
            if (s.isNotEmpty()){
                totalPassanger = totalPassanger+(s.toInt())
            }
        }*/
//        val totalPassangerFlight = dataOrderflight.totalPassangerInt.split(",")[0].toInt()+dataOrderflight.totalPassangerInt.split(",")[1].toInt()+dataOrderflight.totalPassangerInt.split(",")[2].toInt()
//        val totalPassangerTrain  = dataOrderTrain.totalPassangerInt.split(",")[0].toInt()+dataOrderTrain.totalPassangerInt.split(",")[1].toInt()+dataOrderTrain.totalPassangerInt.split(",")[2].toInt()

        if("Flight".equals(type)){
            return Pair(dataOrderflight.totalPassangerInt,"")
        }
        else{
            return Pair("",dataOrderTrain.totalPassangerInt)
        }
    }


    override fun onClicked() {

        setLog(Serializer.serialize(adapterExpand.items,ArrayList::class.java))


        val dataList = ListDetailPassanger()
        dataList.data = data
        Constants.LIST_DETAIL_PASSANGER = Serializer.serialize(dataList,ListDetailPassanger::class.java)
        if (Globals.typeAccomodation=="Flight"){
            if (completedData()){
                saveDataFlight()
                toCardActivity()
            }
            else {
                showAllert("Please","Completed data")
            }
        }
        else if(Globals.typeAccomodation=="Train"){
            if (completedData()){
                saveDataTrain()
                toCardActivity()
            }
            else {
                showAllert("Please","Completed data")
            }
        }
    }

    private fun completedData(): Boolean {
        var completed = false

        val totalEmpety = adapterExpand.items.filter {
            it.email.isEmpty()||
            it.firstname.isEmpty()||
            it.lastname.isEmpty()||
            it.mobileNumber.isEmpty()
//            it.title.isEmpty()
        }.size

        if (totalEmpety==0){
            completed = true
        }

        return completed
    }

    private fun saveDataFlight() {
//        val dataTrip  = Serializer.deserialize(Globals.DATA_CREATE_TRIP, DataBisnisTripModel::class.java)
        val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        val dataListTrain = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

        val data = OrderDbEntity()
        data.idUser           = getProfile().employId
        data.departtemen      = getProfile().position
        data.purpose          = "Personal trip"
        data.city             = "Senin , 14 january 2019"
        data.date             = "January"
        data.time             = "09:20-10:50"
        data.statusOrder      = "Card"
        data.statusTrip       = "Waiting"
        data.typeAccomodation = "Flight"
        data.typeOrder        = "bisnis_trip"
        data.dataParticipant  = Serializer.serialize(adapterExpand.items,ArrayList::class.java)
        data.totalParticipant = dataOrder.totalPassagerString
        data.tripCode         = System.currentTimeMillis().toString()
        data.listParticipant  = Constants.LIST_DETAIL_PASSANGER
        data.detailOrder      = Globals.DATA_ORDER_FLIGHT
        data.dataBisnisTrip   = Constants.DATA_CREATE_TRIP
        data.detailListAccomodation  = Globals.DATA_LIST_FLIGHT

        AccessDb().insertOrder(this,data)
        toCardActivity()
    }

    private fun saveDataTrain() {
//        val dataTrip = Serializer.deserialize(Globals.DATA_CREATE_TRIP, DataBisnisTripModel::class.java)
        val dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        val dataListTrain = Serializer.deserialize(Constants.DATA_LIST_TRAIN, DataListOrderAccomodation::class.java)

        val data = OrderDbEntity()
        data.idUser           = getProfile().employId
        data.departtemen      = getProfile().position
        data.purpose          = "Personal trip"//dataTrip.namePusrpose
        data.city             = "Surabaya"//dataTrip.nameDestination
        data.date             = "Senin, 14 january 2019"//dataTrip.startDate+ " "+""
        data.time             = "14:00-08:50"
        data.statusOrder      = "Card"
        data.statusTrip       = "Waiting"
        data.typeAccomodation = "Train"
        data.dataParticipant  = Serializer.serialize(adapterExpand.items,ArrayList::class.java)
        data.totalParticipant = dataOrder.totalPassagerString
        data.tripCode         = System.currentTimeMillis().toString()
        data.listParticipant  = Constants.LIST_DETAIL_PASSANGER
        data.detailOrder      = Constants.DATA_ORDER_TRAIN
        data.detailListAccomodation  = Constants.DATA_LIST_TRAIN

        AccessDb().insertOrder(this,data)
        toCardActivity()
    }

    private fun toCardActivity(){
        gotoActivity(NewCartActivity::class.java)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }


}