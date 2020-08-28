package com.opsigo.travelaja.module.accomodation.seat.seatmap.flight

import com.opsigo.travelaja.module.accomodation.seat.seatmap.WrappableGridLayoutManager
import com.opsigo.travelaja.module.accomodation.train.dialog.DialogSelectCabin
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.seatmap_view_flight.*
import com.opsigo.travelaja.module.cart.model.CartModel
import android.support.v7.widget.DefaultItemAnimator
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import com.opsigo.travelaja.R
import android.view.View
import android.os.Build
import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel

class SeatActivityFlight : BaseActivity(),
        OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.seatmap_view_flight }

    var posisitionPageAirline  = 0
    val dataAirline            = ArrayList<SeatAirlineModel>()
    val dataDialogCabin        = ArrayList<String>()
    var dataItem               = CartModel()

    override fun OnMain() {
        addDataDummy()
        initRecyclerViewCabin()
        countDownExpiredTime()
//        initToolbar()
    }

    private fun addDataDummy() {
//        val modelAirLine = SeatAirlineModel()
//        modelAirLine.totalRows = 6
//        modelAirLine.dataSeat  = DummySeatFlight().getDataSeat1()
//
//        val modelAirLine2 = SeatAirlineModel()
//        modelAirLine2.totalRows = 8
//        modelAirLine2.dataSeat  = DummySeatFlight().getDataSeat2()
//
//        dataAirline.add(modelAirLine)
//        dataAirline.add(modelAirLine2)

        dataAirline.addAll(Constants.DATA_SEAT_AIRLINE)

        Constants.DATA_SEAT_AIRLINE.forEachIndexed { index, seatAirlineModel ->
            dataDialogCabin.add(seatAirlineModel.nameAirCraft)
        }

        setTitleAirline(dataAirline[0].nameFlight,dataAirline[0].nameAirCraft)

//        dataDialogCabin.add("Garuda")
//        dataDialogCabin.add("Abu dabi")

    }

    fun setTitleAirline(nameAirline:String,codeAirline:String){
        tv_name_airline.text = nameAirline
        tv_code_airline.text = codeAirline
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

    private fun initRecyclerViewCabin() {
        val adapterSeat by lazy { AdapterSeatMapFlight(this, dataAirline[posisitionPageAirline].dataSeat) }
        val mLayoutManager = WrappableGridLayoutManager(this, dataAirline[posisitionPageAirline].totalRows)
        recyclere_seatmap.setLayoutManager(mLayoutManager)
        recyclere_seatmap.setItemAnimator(DefaultItemAnimator())
        recyclere_seatmap.setHasFixedSize(true)
        recyclere_seatmap.hasFixedSize()
        recyclere_seatmap.setAdapter(adapterSeat)

        adapterSeat.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -3 -> {
                        if (dataAirline[posisitionPageAirline].dataSeat[position].status != Constants.SoldSeat){
                            if (dataAirline[posisitionPageAirline].dataSeat[position].status!=Constants.PickSeat){
                                dataAirline[posisitionPageAirline].dataSeat.filter { it.status == Constants.PickSeat }.first().status = Constants.AvailableSeat
                                dataAirline[posisitionPageAirline].dataSeat[position].status = Constants.PickSeat
                                adapterSeat.notifyDataSetChanged()
                                tv_number_seat_select.text = "Seat Number ${dataAirline[posisitionPageAirline].dataSeat[position].numberSeat}"
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1 -> {

            }
        }
    }

    fun showDialogAirline(views: View){
        DialogSelectCabin(this).create(posisitionPageAirline,dataDialogCabin,object :DialogSelectCabin.CallbackDialog{
            override fun selected(int: Int) {
//                setTitleBtnDialogCabin(dataDialogCabin[int])
                posisitionPageAirline = int

                tv_number_seat_select.text = "Seat Number ${dataAirline[posisitionPageAirline].dataSeat.filter { it.status == Constants.PickSeat }.first().numberSeat}"
                setTitleAirline(dataAirline[posisitionPageAirline].nameFlight,dataAirline[posisitionPageAirline].nameAirCraft)
                initRecyclerViewCabin()
            }
        })
    }

    private fun setTitleBtnDialogCabin(data: String) {
        tv_btn_dialog_cabin.text = data
    }

    fun applyListenerSeatFlight(views: View){
        Globals.finishResultOk(this)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }
}
