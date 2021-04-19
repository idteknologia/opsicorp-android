package com.opsicorp.train_feature.seat

import opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.getseat.SeatMapTrainParamRequest
import opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.getseat.GetSeatMapTrainRequest
import opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.seatseat.SetSeatTrainRequest
import opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.seatseat.TrainSeat
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.train.CabinModel
import opsigo.com.domainlayer.callback.CallbackSetSeatMapTrain
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.model.SetSeatMapModelTrain
import opsigo.com.domainlayer.callback.CallbackSeatMap
import com.opsigo.travelaja.module.cart.model.CartModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.os.Build
import com.opsicorp.train_feature.R
import com.opsicorp.train_feature.dialog.DialogSelectCabin
import kotlinx.android.synthetic.main.detail_train_result_activity.*
import kotlinx.android.synthetic.main.detail_train_result_activity.toolbar
import kotlinx.android.synthetic.main.seatmap_view.*
import java.util.*


class SeatActivityTrain : BaseActivity(),
        OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.seatmap_view }

//    val dataPassanger = ArrayList<PassangerSeatMapModel>()
//    val adapterPassanger by lazy { AdapterPassangerSeatMap(this,dataPassanger) }
//    val dataSeatTable = ArrayList<SeatTableModel>()
//    val adapterSeat by lazy { AdapterSeatMapTable(this,dataSeatTable) }

    var posisitionPage = 0
    var lastSelectPage   = -1
    var lastSelectSeat   = -1
    var seatSelectTed = TrainSeat()
    var classTrain = "Economy"
    val dataCabins = ArrayList<CabinModel>()
    val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
    val dataDialogCabin = ArrayList<String>()
    var dataItem = CartModel()


    val adapterCabin by lazy { AdapterSeatMapCabin(this,dataCabins) }

    override fun OnMain() {
        dataItem = Serializer.deserialize(intent.getStringExtra("data"),CartModel::class.java)
        classTrain = dataItem.dataCardTrain.classTrain
        getDataSeatMap()
        initRecyclerViewCabin()
        countDownExpiredTime()
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        val dateDeparture = if (dataItem.dataCardTrain.dateDeparture.contains(" ")) dataItem.dataCardTrain.dateDeparture.split(" ")[0] else dataItem.dataCardTrain.dateDeparture
        toolbar.setDoubleTitle("${dataItem.dataCardTrain.stationDeparture} - ${dataItem.dataCardTrain.stationArrival}","${DateConverter().getDate(dateDeparture,"yyyy-MM-dd","EEE, dd MMM |")} 1 adult") //- 1 pax , ${dataOrder.dateArrival}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
    }

    private fun countDownExpiredTime() {
        object : CountDownTimer(getTimeExpiredTime(),1000){
            override fun onFinish() {
                finish()
            }

            override fun onTick(diff: Long) {
                val hour: Long = diff / (60 * 60 * 1000) % 24
                val minutes: Long = diff / (60 * 1000) % 60
                val seconds: Long = diff / 1000 % 60
                timeExpired.text = "Pick Seat Time ${minutes} : ${seconds}"
            }
        }.start()
    }

    private fun getTimeExpiredTime(): Long {
        return 14 * 60 * 1000
    }

    private fun getDataSeatMap() {
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).seatMapTrain(getToken(),getDataSeatTrain(),object :CallbackSeatMap{
            override fun success(data: ArrayList<CabinModel>) {
                dataCabins.clear()
                dataCabins.addAll(data)
                dataCabins.first().seatTables.filter { it.status == Constants.AvailableSeat }.first().status = Constants.PickSeat
                lastSelectSeat = dataCabins.first().seatTables.indexOf(dataCabins.first().seatTables.filter { it.status == Constants.PickSeat }.first())
                lastSelectPage = 0
                adapterCabin.setData(data)
                dataDialogCabin()
                hideLoadingOpsicorp()
            }

            override fun failed(errorMessage: String) {
                val builder = AlertDialog.Builder(this@SeatActivityTrain)
                builder.setTitle("Sorry")
                builder.setMessage(errorMessage)
                builder.setPositiveButton("Ok") { dialog, which -> finish() }
                builder.create().show()
            }
        })
    }

    private fun getDataSeatTrain(): HashMap<Any, Any> {
        val dummy = "{\n" +
                "  \"TripId\" : \"9a8a0357-86d6-4516-a3ec-a7f08e459e8f\",\n" +
                "  \"TravelAgent\" : \"apidev\",\n" +
                "  \"SeatParam\" :\n" +
                "  {\n" +
                "    \"Origin\" : \"GMR\",\n" +
                "    \"Destination\" : \"BD\",\n" +
                "    \"DepartureDate\" : \"2020-03-08\",\n" +
                "    \"SubClass\" : \"S\",\n" +
                "    \"CarrierNumber\": \"66F\",\n" +
                "    \"FareBasisCode\": \"66F,EKO,S,ARGO PARAHYANGAN,40360592,230454,4,1,3,318704413,GMR,BD\"\n" +
                "  }\n" +
                "}"

        val model = GetSeatMapTrainRequest()
        model.seatParam   = getSeatMapTrainParam()
        model.travelAgent = Globals.getConfigCompany(this).defaultTravelAgent
        model.tripId      = dataItem.dataCardTrain.idTrain

        return Globals.classToHashMap(model,GetSeatMapTrainRequest::class.java)
//        return Globals.classToHashMap(Serializer.deserialize(dummy,GetSeatMapTrainRequest::class.java),GetSeatMapTrainRequest::class.java)
    }

    private fun getSeatMapTrainParam(): SeatMapTrainParamRequest {
        val model = SeatMapTrainParamRequest()
        model.origin        = dataItem.dataCardTrain.origin
        model.subClass      = dataItem.dataCardTrain.classCode
        model.destination   = dataItem.dataCardTrain.destination
        model.carrierNumber = dataItem.dataCardTrain.carrierNumber
        model.fareBasisCode = dataItem.dataCardTrain.fareBasisCode
        model.departureDate = dataItem.dataCardTrain.dateDeparture
        return model
    }

    private fun initRecyclerViewCabin() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        recyclere_seatmap_parent.layoutManager = layoutManager
        recyclere_seatmap_parent.setHasFixedSize(true)
        recyclere_seatmap_parent.setAdapter(adapterCabin)

        snapHelper.attachToRecyclerView(recyclere_seatmap_parent)

        recyclere_seatmap_parent.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val centerView = snapHelper.findSnapView(layoutManager)
                posisitionPage = layoutManager.getPosition(centerView!!)
                setTitleBtnDialogCabin(dataDialogCabin[posisitionPage])
                if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE || (posisitionPage == 0 && newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING)) {
                    setLog("BINDING positionView SCROLL_STATE_IDLE: $posisitionPage")
                }
            }
        })

        adapterCabin.setOnclickListener(object :OnclickListenerRecyclerViewParent{
            override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

                if (dataCabins[positionParent].seatTables[positionChild].status != Constants.SoldSeat){
                    if (dataCabins[positionParent].seatTables[positionChild].status!=Constants.PickSeat){
                        dataCabins[lastSelectPage].seatTables[lastSelectSeat].status = Constants.AvailableSeat
                        adapterCabin.notifyItemChanged(lastSelectPage)
                        dataCabins[positionParent].seatTables[positionChild].status  = Constants.PickSeat
                        lastSelectPage = positionParent
                        lastSelectSeat = positionChild
                        adapterCabin.notifyItemChanged(positionParent)

                        tv_number_seat_select.text = "Seat Number ${dataCabins[positionParent].seatTables[positionChild].numberSeat}"

                        seatSelectTed.Y = dataCabins[positionParent].seatTables[positionChild].y.toString()
                        seatSelectTed.X = dataCabins[positionParent].seatTables[positionChild].x.toString()
                        seatSelectTed.seatNumber = dataCabins[positionParent].seatTables[positionChild].numberSeat
                        seatSelectTed.seatName = dataCabins[positionParent].seatTables[positionChild].seatName


                    }
                }

            }
        })

        /*adapterSeat.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                setLog(dataSeatTable[position].status+" position "+position)
                when(views){
                    -3 -> {
                        if (dataSeatTable[position].status != Constants.SoldSeat){
                            if (dataSeatTable[position].status == Constants.SelectSeat){
                                dataPassanger.forEachIndexed { index, passangerSeatMapModel ->
                                    if (passangerSeatMapModel.namePassanger == dataSeatTable[position].namePassanger){
                                        adapterPassanger.positionPick = index
                                        adapterPassanger.notifyDataSetChanged()
                                    }
                                }
                                val mDataPassanger = dataPassanger.filter { it.namePassanger == dataSeatTable[position].namePassanger }.first()
                                val index =  dataPassanger.indexOf(mDataPassanger)
                                adapterPassanger.positionPick = index
                                adapterPassanger.notifyDataSetChanged()

                                dataSeatTable[lastPick].status = Constants.SelectSeat
                                adapterSeat.notifyItemChanged(lastPick)

                                dataSeatTable[position].status = Constants.PickSeat
                                lastPick = position
                                adapterSeat.notifyItemChanged(position)

                            }
                            else {
                                dataPassanger[adapterPassanger.positionPick].numberSeatSelected = dataSeatTable[position].idSeat
                                dataPassanger[adapterPassanger.positionPick].namePassanger = dataSeatTable[position].namePassanger
                                adapterPassanger.notifyDataSetChanged()

                                dataSeatTable[lastPick].status = Constants.AvailableSeat
                                adapterSeat.notifyItemChanged(lastPick)

                                dataSeatTable[position].status = Constants.PickSeat
                                lastPick = position
                                adapterSeat.notifyItemChanged(position)

                            }
                        }
                    }
                }
            }
        })*/
    }
/*
    private fun addDummySeatTable() {
        for (i in 1 until 11){
            val numb = SeatTableModel()
            numb.type = "NUMBER"
            numb.number = i.toString()
            dataSeatTable.add(numb)

//            val spach = SeatTableModel()
//            spach.type = "SPACING"
//            dataSeatTable.add(spach)

//            val seat0 = SeatTableModel()
//            seat0.type = "SEAT"
//            seat0.numberSeat = "K"
//            seat0.idSeat ="A${i}"
//            dataSeatTable.add(seat0)

            val seat = SeatTableModel()
            seat.type = "SEAT"
            seat.numberSeat = "A"
            seat.status = Constants.AvailableSeat
            seat.idSeat ="A${i}"
            dataSeatTable.add(seat)

            val seat1 = SeatTableModel()
            seat1.type = "SEAT"
            seat1.numberSeat = "B"
            seat1.status = Constants.AvailableSeat
            seat1.idSeat ="B${i}"
            dataSeatTable.add(seat1)

            val spaching = SeatTableModel()
            spaching.type = "SPACING_CENTER"
            dataSeatTable.add(spaching)

            val seat2 = SeatTableModel()
            seat2.type = "SEAT"
            seat2.numberSeat = "C"
            seat2.status = Constants.AvailableSeat
            seat2.idSeat ="C${i}"
            dataSeatTable.add(seat2)

            val seat3 = SeatTableModel()
            seat3.type = "SEAT"
            seat3.numberSeat = "D"
            seat3.status = Constants.AvailableSeat
            seat3.idSeat ="D${i}"
            dataSeatTable.add(seat3)

            val spach2 = SeatTableModel()
            spach2.type = "SPACING"
            dataSeatTable.add(spach2)
//            dataSeatTable.add(spach2)

            setLog("000>D<"+dataSeatTable.size.toString())
        }

        dataSeatTable[1].status = Constants.PickSeat
        dataSeatTable[1].namePassanger = "Khoiron"
        dataSeatTable[2].status = Constants.SelectSeat
        dataSeatTable[2].namePassanger = "Vody"
        dataSeatTable[4].status = Constants.SelectSeat
        dataSeatTable[4].namePassanger = "Icha"
        dataSeatTable[5].status = Constants.SelectSeat
        dataSeatTable[5].namePassanger = "Boneng"

        dataSeatTable[7].status = Constants.SoldSeat
        dataSeatTable[7].namePassanger = ""
        dataSeatTable[8].status = Constants.SoldSeat
        dataSeatTable[8].namePassanger = ""
        lastPick = 1

        adapterSeat.setData(dataSeatTable)


    }
*/
/*
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_select_passanger.layoutManager = layoutManager
        rv_select_passanger.adapter = adapterPassanger

        adapterPassanger.setOnclickListener(this)

        addDataDummy()
    }

    private fun addDataDummy() {
        val names = ArrayList<String>()
        names.clear()
        names.add("Khoiron")
        names.add("Vody")
        names.add("Icha")
        names.add("Boneng")

        dataPassanger.clear()
        for (i in 1 until 5){
            val mData = PassangerSeatMapModel()
            mData.namePassanger = names[i-1]
            mData.cabinSelected = 9
            mData.nik  = "${i}+${names}"
//            mData.numberSeatSelected ="${i}A"
            dataPassanger.add(mData)
        }

        dataPassanger[0].numberSeatSelected = "A1"
        dataPassanger[1].numberSeatSelected = "B1"
        dataPassanger[2].numberSeatSelected = "C1"
        dataPassanger[3].numberSeatSelected = "D1"

        adapterPassanger.setData(dataPassanger)

    }
    */

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1 -> {
/*                dataSeatTable[lastPick].status = Constants.SelectSeat
                adapterSeat.notifyItemChanged(lastPick)

                val dataSeat = dataSeatTable.filter { it.idSeat == dataPassanger[position].numberSeatSelected }.first()
                val positionSeat = dataSeatTable.indexOf(dataSeat)

                dataSeatTable[positionSeat].status = Constants.PickSeat
                lastPick = positionSeat
                adapterSeat.notifyItemChanged(positionSeat)*/
            }
        }
    }

    fun showDialogCabinListener(views: View){
        DialogSelectCabin(this).create(posisitionPage,dataDialogCabin,object :DialogSelectCabin.CallbackDialog{
            override fun selected(int: Int) {
                setTitleBtnDialogCabin(dataDialogCabin[int])
                posisitionPage = int
                recyclere_seatmap_parent.smoothScrollToPosition(int)
            }
        })
    }

    private fun dataDialogCabin(){
        dataDialogCabin.clear()

        dataCabins.forEachIndexed { index, cabinModel ->
            dataDialogCabin.add("$classTrain ${(index+1)}")
        }

        setTitleBtnDialogCabin(dataDialogCabin[posisitionPage])
    }

    private fun setTitleBtnDialogCabin(data: String) {
        tv_btn_dialog_cabin.text = data
    }

    fun applyListener(views: View){
        setSeatMapTrain()
    }

    fun setSeatMapTrain(){
//        showLoadingOpsicorp(true)
//        Globals.delay(5000,object :Globals.DelayCallback{
//            override fun done() {
//                finish()
//            }
//        })

        if (seatSelectTed.X.isNullOrEmpty()){
            showAllert("Please","Select Seat")
        }
        else {
            setLog(Serializer.serialize(getDataSelectedSeatMap()))
            showLoadingOpsicorp(true)
            GetDataAccomodation(getBaseUrl()).setSeatMapTrain(getToken(),getDataSelectedSeatMap(),object : CallbackSetSeatMapTrain {
                override fun successLoad(data: SetSeatMapModelTrain) {
                    hideLoadingOpsicorp()
                    val intent = Intent()
                    intent.putExtra(Constants.ID_TRIP_PLANE,dataItem.dataCardTrain.tripId)
                    Globals.finishResultOk(this@SeatActivityTrain,intent)
                }
                override fun failedLoad(message: String) {
                    hideLoadingOpsicorp()
                    showAllert("Sorry",message)
                }
            })
        }


    }

    private fun getDataSelectedSeatMap(): HashMap<Any, Any> {
        val data = SetSeatTrainRequest()
        data.pnrId         = dataItem.dataCardTrain.pnrID
        data.trainSeat     = getTrainSeat()
        data.travelAgent   = Globals.getConfigCompany(this).defaultTravelAgent
        data.referenceCode = dataItem.dataCardTrain.refrenceCode
        data.tripId        = dataItem.dataCardTrain.tripId
        data.trainId       = dataItem.dataCardTrain.idTrain
        data.pnrCode       = dataItem.dataCardTrain.pnrCode
        return Globals.classToHashMap(data,SetSeatTrainRequest::class.java)
    }

    private fun getTrainSeat(): TrainSeat {
        return seatSelectTed
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }
}
