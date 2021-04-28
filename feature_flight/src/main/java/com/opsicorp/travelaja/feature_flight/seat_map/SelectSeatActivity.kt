package com.opsicorp.travelaja.feature_flight.seat_map

import android.content.Intent
import android.os.Build
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.select_seat_activity.*
import kotlinx.android.synthetic.main.select_seat_activity.btnDone
import kotlinx.android.synthetic.main.select_seat_activity.toolbar
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class SelectSeatActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView, ToolbarOpsicorp.OnclickButtonListener {

    val adapter by lazy { SelectSeatAdapter(this) }
    lateinit var datalist: DataListOrderAccomodation

    override fun getLayout(): Int {
        return R.layout.select_seat_activity
    }

    override fun OnMain() {
        btnDone.callbackOnclickButton(this)
        btnDone.setTextButton("Done")
        initToolbar()
        initRecyclerView()
        setData()
    }

    private fun setData() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        val dataProfile = Globals.getProfile(applicationContext)
        tvPassengerSeat.text = "${dataProfile.title}.${dataProfile.name}"
        adapter.setData(datalist.dataFlight)
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rvSelectSeat.layoutManager = layoutManager
        rvSelectSeat.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvSelectSeat.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Seat Map")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            Constants.GET_SEAT_MAP -> {
                val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
                setLog("test save seat",Serializer.serialize(dataList.dataFlight[0].dataSeat))
                setData()
            }
        }
    }

    override fun onClicked() {
        finish()
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.REQUEST_CODE_SELECT_SEAT ->{
                val intent = Intent(this, SeatActivityFlight::class.java)
                intent.putExtra(Constants.KEY_POSITION_SELECT_SEAT,position)
               gotoActivityResultIntent(intent,Constants.GET_SEAT_MAP)
            }
            Constants.REQUEST_CODE_DELETE_SEAT -> {
                datalist.dataFlight[position].dataSeat.dataSeat.clear()
                Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)
                adapter.setData(datalist.dataFlight)
            }
        }
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {

    }


}