package com.opsicorp.travelaja.feature_flight.filter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.AccomodationPreferanceAdapter
import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.AccomodationPreferanceModel
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.train_filter_actvity_view.*

class FlightFilterActivity : BaseActivity() ,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener {

    val dataDepartureTime = ArrayList<AccomodationPreferanceModel>()
    val dataArrivalTime   = ArrayList<AccomodationPreferanceModel>()
    val dataNameTrain     = ArrayList<AccomodationPreferanceModel>()
    val totalTrain        = "0"

    val adapterDepartureTime by lazy {  AccomodationPreferanceAdapter(this,dataDepartureTime) }
    val adapterArrivalTime   by lazy {  AccomodationPreferanceAdapter(this,dataArrivalTime) }
    val adapterTrainName     by lazy {  AccomodationPreferanceAdapter(this,dataNameTrain) }

    override fun getLayout(): Int {
        return R.layout.train_filter_actvity_view
    }

    override fun OnMain() {
        initToolbar()
        setInitRecylerView()
        initRangeBar()
        initButtonFilter()
    }

    private fun initButtonFilter() {
        btn_filter.setTextButton("See ${totalTrain} Ticket(s)")
        btn_filter.callbackOnclickButton(this)
    }

    private fun initRangeBar() {
        rangebar.max = 1500000
        rangebar.setProgress(0,1500000)
        rangebar.setStepSize(10000)
        rangebar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener{
            override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
                setLog("rangeBar p1 = ${p1}  p1 = ${p2}")
            }

            override fun onStartTrackingTouch(p0: RangeSeekBar?) {

            }

            override fun onStopTrackingTouch(p0: RangeSeekBar) {
                setLog("rangeBar strat = ${p0.progressStart}  end = ${p0.progressEnd}" )
                Constants.dataFIlterMinPriceAccomodation = p0.progressStart
                Constants.dataFilterMaxPriceAccomodation = p0.progressEnd
            }

        })
    }

    private fun setInitRecylerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_departure_time.layoutManager = layoutManager
        rv_departure_time.itemAnimator = DefaultItemAnimator()
        rv_departure_time.adapter = adapterDepartureTime

        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        rv_arrival_time.layoutManager = layoutManager2
        rv_arrival_time.itemAnimator = DefaultItemAnimator()
        rv_arrival_time.adapter = adapterArrivalTime

        val layoutManager3 = LinearLayoutManager(this)
        layoutManager3.orientation = LinearLayoutManager.VERTICAL
        rv_name_train.layoutManager = layoutManager3
        rv_name_train.itemAnimator = DefaultItemAnimator()
        rv_name_train.adapter = adapterTrainName

        adapterTrainName.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -5 -> {
                        dataNameTrain[position].checked = !dataNameTrain[position].checked
                        adapterTrainName.notifyItemChanged(position)
                    }
                }
            }
        })

        adapterArrivalTime.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -5 -> {
                        dataArrivalTime[position].checked = !dataArrivalTime[position].checked
                        adapterArrivalTime.notifyItemChanged(position)
                    }
                }
            }
        })

        adapterDepartureTime.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -5 -> {
                        dataDepartureTime[position].checked = !dataDepartureTime[position].checked
                        adapterDepartureTime.notifyItemChanged(position)
                    }
                }
            }
        })

        setDataDummy()
    }

    private fun setDataDummy() {
        for (i in 0 until 4){
            val model = AccomodationPreferanceModel()
            model.name = "Early Morning"
            model.time = "12:00 - 18:00"
            model.id   = "${i}"
            dataArrivalTime.add(model)
            dataDepartureTime.add(model)
        }

        for (i in 0 until 5){
            val model = AccomodationPreferanceModel()
            model.name = "Argo Parahyangan"
            model.id   = "${i}"
            dataNameTrain.add(model)
        }

        adapterDepartureTime.setData(dataDepartureTime)
        adapterArrivalTime.setData(dataArrivalTime)
        adapterTrainName.setData(dataNameTrain)
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
    }

    override fun onClicked() {
        Constants.dataDepartureTime.clear()
        Constants.dataArrivalTime.clear()
        Constants.dataNameTrainSelected.clear()

        Constants.dataDepartureTime = dataDepartureTime
        Constants.dataArrivalTime   = dataArrivalTime
        Constants.dataNameTrainSelected     = dataNameTrain

        val returnIntent = Intent()
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }

    override fun btnBack() {
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }
}