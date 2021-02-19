package com.opsicorp.travelaja.feature_flight.seat_map

import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.seatmap_view_flight.*
import com.opsigo.travelaja.module.cart.model.CartModel
import android.support.v7.widget.DefaultItemAnimator
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.*
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import android.view.View
import android.os.Build
import com.opsicorp.travelaja.feature_flight.R
import com.squareup.picasso.Picasso
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel

class SeatActivityFlight : BaseActivity(),
        OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.seatmap_view_flight }

    var posisitionPageAirline  : Int = 0
    val dataAirline            = ArrayList<SeatAirlineModel>()
    val dataDialogCabin        = ArrayList<String>()
    var dataItem               = CartModel()
    lateinit var dataOrder: OrderAccomodationModel
    lateinit var datalist: DataListOrderAccomodation


    override fun OnMain() {
        initData()
        initRecyclerViewCabin()
        countDownExpiredTime()
        initToolbar()
    }

    private fun initData() {
        dataAirline.addAll(Constants.DATA_SEAT_AIRLINE)
        posisitionPageAirline = intent.getIntExtra(Constants.KEY_POSITION_SELECT_SEAT, 0)

        Constants.DATA_SEAT_AIRLINE.forEachIndexed { index, seatAirlineModel ->
            dataDialogCabin.add(seatAirlineModel.nameAirCraft)
        }

    }

    fun setAirlineImage(imgAirline: String) {
        if (imgAirline.isNotEmpty()){
            Picasso.get()
                    .load(imgAirline)
                    .fit()
                    .into(ivAirlines)
        }
    }

    fun setTitleAirline(nameAirline:String,codeAirline:String){
        tv_name_airline.text = nameAirline
        tv_code_airline.text = codeAirline
    }

    fun setAirlineClass(nameClass: String) {
        tvAirlineClass.text = nameClass
    }

    private fun initToolbar() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

        toolbar.setDoubleTitle("Select Seat","${datalist.dataFlight[posisitionPageAirline].originName} (${datalist.dataFlight[posisitionPageAirline].origin})"
                + " --> " + "${datalist.dataFlight[posisitionPageAirline].destinationName} (${datalist.dataFlight[posisitionPageAirline].destination})")

        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        /*toolbar.setDoubleTitle("Select Seat","${dataOrder.originName} (${dataOrder.idOrigin}) -> ${dataOrder.destinationName} (${dataOrder.idDestination})")*/

        setAirlineImage(datalist.dataFlight[posisitionPageAirline].imgAirline)
        setAirlineClass(datalist.dataFlight[posisitionPageAirline].nameClass)
        setTitleAirline(datalist.dataFlight[posisitionPageAirline].titleAirline,dataAirline[posisitionPageAirline].nameAirCraft)

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

                                val seatItem = SeatAirlineModel()
                                seatItem.nameFlight = dataAirline[posisitionPageAirline].nameFlight
                                seatItem.nameAirCraft = dataAirline[posisitionPageAirline].nameAirCraft
                                seatItem.dataSeat.add(dataAirline[posisitionPageAirline].dataSeat[position])
                                datalist.dataFlight[posisitionPageAirline].dataSeat = seatItem
                                Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)
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
        /*DialogSelectCabinFlight(this).create(posisitionPageAirline,dataDialogCabin,object :DialogSelectCabinFlight.CallbackDialog{
            override fun selected(int: Int) {
                setTitleBtnDialogCabin(dataDialogCabin[int])
                posisitionPageAirline = int

                tv_number_seat_select.text = "Seat Number ${dataAirline[posisitionPageAirline].dataSeat.filter { it.status == Constants.PickSeat }.first().numberSeat}"
                setTitleAirline(dataAirline[posisitionPageAirline].nameFlight,dataAirline[posisitionPageAirline].nameAirCraft)
                initRecyclerViewCabin()
            }
        })*/
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
