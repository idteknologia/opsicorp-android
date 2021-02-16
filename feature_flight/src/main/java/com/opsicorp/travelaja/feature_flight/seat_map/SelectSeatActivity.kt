package com.opsicorp.travelaja.feature_flight.seat_map

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.select_seat_activity.*
import kotlinx.android.synthetic.main.select_seat_activity.btnDone
import kotlinx.android.synthetic.main.select_seat_activity.toolbar
import kotlinx.android.synthetic.main.toolbar_view.view.*
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
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvSelectSeat.layoutManager = layoutManager
        rvSelectSeat.itemAnimator = DefaultItemAnimator()
        rvSelectSeat.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Seat Map")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onClicked() {
        finish()
    }

    override fun onClick(views: Int, position: Int) {
        /*when(views){
            -1 ->{
                val bundle = Bundle()
                bundle.putInt(Constants.KEY_POSITION_SELECT_SEAT,position)
                gotoActivityResultWithBundle(SeatActivityFlight::class.java,bundle, Constants.GET_SEAT_MAP)
            }
        }*/
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {

    }


}