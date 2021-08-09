package com.opsicorp.travelaja.feature_flight.filter

import com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance.AccomodationPreferanceModel
import com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance.SelectAccomodationPreferance
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import com.mobile.travelaja.module.item_custom.btn_filter.FilterTransitOpsicorp
import com.opsicorp.travelaja.feature_flight.adapter.FilterFlightCabinAdapter
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel
import kotlinx.android.synthetic.main.filter_flight_activity_new.btn_next
import com.opsicorp.travelaja.feature_flight.adapter.FilterFlightAdapter
import kotlinx.android.synthetic.main.filter_flight_activity_new.*
import kotlinx.android.synthetic.main.flight_filter_fragment_new.*
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.base.BaseActivity
import org.koin.core.KoinComponent
import android.view.View
import android.os.Build
import com.mobile.travelaja.utility.DateConverter
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FilterFlightActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener, FilterTransitOpsicorp.OnclickFilterListener, KoinComponent, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.filter_flight_activity_new
    }

    val d1="{\"listFlightModel\":{\n" +
            "      \"airline\":11,\n" +
            "      \"arrivalDate\":\"2021-08-10 13:00:00\",\n" +
            "      \"arriveDate\":\"2021-08-10\",\n" +
            "      \"arriveDateTimeView\":\"2021-08-10 13:00\",\n" +
            "      \"arriveTime\":\"13:00\",\n" +
            "      \"classCode\":\"N\",\n" +
            "      \"classId\":\"N\",\n" +
            "      \"code\":\"N\",\n" +
            "      \"dataFareRules\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"dataSeat\":{\n" +
            "        \"dataSeat\":[\n" +
            "          \n" +
            "        ],\n" +
            "        \"flightNumber\":\"\",\n" +
            "        \"nameAirCraft\":\"\",\n" +
            "        \"nameFlight\":\"\",\n" +
            "        \"totalRows\":0\n" +
            "      },\n" +
            "      \"dateDeparture\":\"Aug 8,2021 8:04:33 PM\",\n" +
            "      \"departDate\":\"2021-08-10\",\n" +
            "      \"departTime\":\"11:30\",\n" +
            "      \"departureDate\":\"2021-08-10 11:30:00\",\n" +
            "      \"destination\":\"SUB\",\n" +
            "      \"destinationAirport\":\"Juanda Airport\",\n" +
            "      \"destinationName\":\"Surabaya\",\n" +
            "      \"duration\":\"01:30\",\n" +
            "      \"durationIncludeTransit\":\"01:30\",\n" +
            "      \"durationIncludeTransitView\":\"1h 30m\",\n" +
            "      \"durationView\":\"1h 30m\",\n" +
            "      \"facility\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"fareBasisCode\":\"null\",\n" +
            "      \"fareRuleKeys\":\"null\",\n" +
            "      \"flightId\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"flightNumber\":\"KD 830\",\n" +
            "      \"flightType\":\"NonGds\",\n" +
            "      \"flightTypeView\":\"Direct\",\n" +
            "      \"id\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"imgAirline\":\"http://portalvhds11000v9mfhk0k.blob.core.windows.net/airline/KD-mail.png\",\n" +
            "      \"isAvailable\":true,\n" +
            "      \"isComply\":false,\n" +
            "      \"isConnecting\":false,\n" +
            "      \"isFlightArrival\":false,\n" +
            "      \"isHolderFlight\":false,\n" +
            "      \"isMultiClass\":false,\n" +
            "      \"nameClass\":\"Economy\",\n" +
            "      \"notComply\":false,\n" +
            "      \"num\":\"\",\n" +
            "      \"number\":\"KD 830\",\n" +
            "      \"numberSeat\":\"4\",\n" +
            "      \"origin\":\"CGK\",\n" +
            "      \"originAirport\":\"Soekarno Hatta\",\n" +
            "      \"originName\":\"Jakarta\",\n" +
            "      \"passenger\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"price\":165000.0,\n" +
            "      \"seq\":\"0\",\n" +
            "      \"sequence\":0,\n" +
            "      \"terminal\":\"null\",\n" +
            "      \"titleAirline\":\"Kalstar\",\n" +
            "      \"totalTransit\":0,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }}"

    val d2 = "{\"listFlightModel\":{\n" +
            "      \"airline\":11,\n" +
            "      \"arrivalDate\":\"2021-08-10 17:00:00\",\n" +
            "      \"arriveDate\":\"2021-08-10\",\n" +
            "      \"arriveDateTimeView\":\"2021-08-10 17:00\",\n" +
            "      \"arriveTime\":\"07:00\",\n" +
            "      \"classCode\":\"N\",\n" +
            "      \"classId\":\"N\",\n" +
            "      \"code\":\"N\",\n" +
            "      \"dataFareRules\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"dataSeat\":{\n" +
            "        \"dataSeat\":[\n" +
            "          \n" +
            "        ],\n" +
            "        \"flightNumber\":\"\",\n" +
            "        \"nameAirCraft\":\"\",\n" +
            "        \"nameFlight\":\"\",\n" +
            "        \"totalRows\":0\n" +
            "      },\n" +
            "      \"dateDeparture\":\"Aug 8,2021 8:04:33 PM\",\n" +
            "      \"departDate\":\"2021-08-10\",\n" +
            "      \"departTime\":\"11:30\",\n" +
            "      \"departureDate\":\"2021-08-10 11:30:00\",\n" +
            "      \"destination\":\"SUB\",\n" +
            "      \"destinationAirport\":\"Juanda Airport\",\n" +
            "      \"destinationName\":\"Surabaya\",\n" +
            "      \"duration\":\"01:30\",\n" +
            "      \"durationIncludeTransit\":\"01:30\",\n" +
            "      \"durationIncludeTransitView\":\"1h 30m\",\n" +
            "      \"durationView\":\"1h 30m\",\n" +
            "      \"facility\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"fareBasisCode\":\"null\",\n" +
            "      \"fareRuleKeys\":\"null\",\n" +
            "      \"flightId\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"flightNumber\":\"KD 830\",\n" +
            "      \"flightType\":\"NonGds\",\n" +
            "      \"flightTypeView\":\"Direct\",\n" +
            "      \"id\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"imgAirline\":\"http://portalvhds11000v9mfhk0k.blob.core.windows.net/airline/KD-mail.png\",\n" +
            "      \"isAvailable\":true,\n" +
            "      \"isComply\":false,\n" +
            "      \"isConnecting\":false,\n" +
            "      \"isFlightArrival\":false,\n" +
            "      \"isHolderFlight\":false,\n" +
            "      \"isMultiClass\":false,\n" +
            "      \"nameClass\":\"Economy\",\n" +
            "      \"notComply\":false,\n" +
            "      \"num\":\"\",\n" +
            "      \"number\":\"KD 830\",\n" +
            "      \"numberSeat\":\"4\",\n" +
            "      \"origin\":\"CGK\",\n" +
            "      \"originAirport\":\"Soekarno Hatta\",\n" +
            "      \"originName\":\"Jakarta\",\n" +
            "      \"passenger\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"price\":165000.0,\n" +
            "      \"seq\":\"0\",\n" +
            "      \"sequence\":0,\n" +
            "      \"terminal\":\"null\",\n" +
            "      \"titleAirline\":\"Kalstar\",\n" +
            "      \"totalTransit\":0,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }}"

    val d3 = "{\"listFlightModel\":{\n" +
            "      \"airline\":11,\n" +
            "      \"arrivalDate\":\"2021-08-10 20:00:00\",\n" +
            "      \"arriveDate\":\"2021-08-10\",\n" +
            "      \"arriveDateTimeView\":\"2021-08-10 20:00\",\n" +
            "      \"arriveTime\":\"20:00\",\n" +
            "      \"classCode\":\"N\",\n" +
            "      \"classId\":\"N\",\n" +
            "      \"code\":\"N\",\n" +
            "      \"dataFareRules\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"dataSeat\":{\n" +
            "        \"dataSeat\":[\n" +
            "          \n" +
            "        ],\n" +
            "        \"flightNumber\":\"\",\n" +
            "        \"nameAirCraft\":\"\",\n" +
            "        \"nameFlight\":\"\",\n" +
            "        \"totalRows\":0\n" +
            "      },\n" +
            "      \"dateDeparture\":\"Aug 8,2021 8:04:33 PM\",\n" +
            "      \"departDate\":\"2021-08-10\",\n" +
            "      \"departTime\":\"11:30\",\n" +
            "      \"departureDate\":\"2021-08-10 11:30:00\",\n" +
            "      \"destination\":\"SUB\",\n" +
            "      \"destinationAirport\":\"Juanda Airport\",\n" +
            "      \"destinationName\":\"Surabaya\",\n" +
            "      \"duration\":\"01:30\",\n" +
            "      \"durationIncludeTransit\":\"01:30\",\n" +
            "      \"durationIncludeTransitView\":\"1h 30m\",\n" +
            "      \"durationView\":\"1h 30m\",\n" +
            "      \"facility\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"fareBasisCode\":\"null\",\n" +
            "      \"fareRuleKeys\":\"null\",\n" +
            "      \"flightId\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"flightNumber\":\"KD 830\",\n" +
            "      \"flightType\":\"NonGds\",\n" +
            "      \"flightTypeView\":\"Direct\",\n" +
            "      \"id\":\"ad2d14af-353f-4e45-87fb-49b8aa17e996\",\n" +
            "      \"imgAirline\":\"http://portalvhds11000v9mfhk0k.blob.core.windows.net/airline/KD-mail.png\",\n" +
            "      \"isAvailable\":true,\n" +
            "      \"isComply\":false,\n" +
            "      \"isConnecting\":false,\n" +
            "      \"isFlightArrival\":false,\n" +
            "      \"isHolderFlight\":false,\n" +
            "      \"isMultiClass\":false,\n" +
            "      \"nameClass\":\"Economy\",\n" +
            "      \"notComply\":false,\n" +
            "      \"num\":\"\",\n" +
            "      \"number\":\"KD 830\",\n" +
            "      \"numberSeat\":\"4\",\n" +
            "      \"origin\":\"CGK\",\n" +
            "      \"originAirport\":\"Soekarno Hatta\",\n" +
            "      \"originName\":\"Jakarta\",\n" +
            "      \"passenger\":[\n" +
            "        \n" +
            "      ],\n" +
            "      \"price\":165000.0,\n" +
            "      \"seq\":\"0\",\n" +
            "      \"sequence\":0,\n" +
            "      \"terminal\":\"null\",\n" +
            "      \"titleAirline\":\"Kalstar\",\n" +
            "      \"totalTransit\":0,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }}"

    var dataCabin = ArrayList<FilterFlightModel>()
    var dataDeparture  = ArrayList<FilterFlightModel>()
    var dataArrival    = ArrayList<FilterFlightModel>()

    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()
    var namesAirlines  = ArrayList<String>()
    val totalFlight    = "0"
    val adapterCabinClass by lazy { FilterFlightCabinAdapter(this,dataCabin) }
    val adapterDeparture by lazy { FilterFlightAdapter(this,dataDeparture) }
    val adapterArrival by lazy { FilterFlightAdapter(this,dataArrival) }

    var dataFilter = ArrayList<AccomodationResultModel>()

    override fun OnMain() {
        initToolbar()
        initRangeBar()
        initButtonTransit()
        initRecyclerView()
        addDataDummyTime()
        initButtonNext()

        dataFilter.add(dataDummy(d1))
        dataFilter.add(dataDummy(d2))
        dataFilter.add(dataDummy(d3))
    }

    private fun dataDummy(string: String): AccomodationResultModel {
        val mData = AccomodationResultModel()
        mData.listFlightModel = Serializer.deserialize(string, ResultListFlightModel::class.java)
        return mData
    }

    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("See ${totalFlight} Flight(s)")
    }

    private fun addDataDummyTime() {
        dataCabin.addAll(DataDummyAccomodation().addCabinClass())
        dataDeparture.addAll(DataDummyAccomodation().addDataDepartureTime())
        dataArrival.addAll(DataDummyAccomodation().addDataDepartureTime())

        adapterCabinClass.setData(dataCabin)
        adapterDeparture.setData(dataDeparture)
        adapterArrival.setData(dataArrival)
    }

    private fun initRecyclerView() {
        val layoutManagerCabin = GridLayoutManager(this, 2)
        rvCabinClass.layoutManager = layoutManagerCabin
        rvCabinClass.itemAnimator = DefaultItemAnimator()
        rvCabinClass.adapter = adapterCabinClass

        adapterCabinClass.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        dataCabin[position].isSelected = !dataCabin[position].isSelected
                        adapterCabinClass.notifyItemChanged(position)
                    }
                }
            }
        })

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_departure_time.layoutManager = layoutManager
        rv_departure_time.itemAnimator = DefaultItemAnimator()
        rv_departure_time.adapter = adapterDeparture

        adapterDeparture.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        dataDeparture[position].isSelected = !dataDeparture[position].isSelected
                        adapterDeparture.notifyItemChanged(position)
                    }
                }
            }
        })

        val layoutManagerArrival = LinearLayoutManager(this)
        layoutManagerArrival.orientation = LinearLayoutManager.VERTICAL
        rv_arrival_time.layoutManager = layoutManagerArrival
        rv_arrival_time.itemAnimator = DefaultItemAnimator()
        rv_arrival_time.adapter = adapterArrival

        adapterArrival.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 ->{
                        dataArrival[position].isSelected = !dataArrival[position].isSelected
                        adapterArrival.notifyItemChanged(position)
                    }
                }
            }
        })
    }

    private fun initButtonTransit() {
        btn_transit.callbackOnclickFilter(this)
    }

    private fun initRangeBar() {
        rangebar.setOnRangeSeekBarChangeListener(object : RangeSeekBar.OnRangeSeekBarChangeListener{
            override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
                setLog("rangeBar p1 = ${p1}  p1 = ${p2}" )
                tvRangeBar.text = "${p1} - ${p2}"
            }

            override fun onStartTrackingTouch(p0: RangeSeekBar?) {

            }

            override fun onStopTrackingTouch(p0: RangeSeekBar?) {
                setLog("rangeBar strat = ${p0?.progressStart}  end = ${p0?.progressEnd}" )
                tvRangeBar.text = "${p0?.progressStart} - ${p0?.progressEnd}"
            }
        })
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Filter")
        toolbar.showBtnReset()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    override fun onClicked() {
        finish()
    }

    override fun onDirect() {
    }

    override fun onOneTransit() {
    }

    override fun onTwoTransit() {
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    fun airlinesPreferance(view: View){
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(supportFragmentManager,"dialog")

        selectAccomodationPreferance.setCallbackListener(object : SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String) {
                tv_airline_prreferance.text = string
            }
        })

    }

    private fun addDataAirPreferance() {
        namesAirlines.forEachIndexed { index, names ->
            val mData = AccomodationPreferanceModel()
            mData.id = "${index+1}"
            mData.checked = false
            mData.name = names
            dataPrefarance.add(mData)
        }
    }

    fun filterData(){
        if (!dataArrival.filter { it.isSelected }.isNullOrEmpty()){
            dataArrival.filter { it.isSelected }.forEachIndexed { index, filterFlightModel ->
                dataFilter.filter {
                    val timeArrival = DateConverter().stringToDate("yyyy-MM-dd HH:mm",it.listFlightModel.arrivalDate)
                    val afterTime   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[0]}")
                    val beforeArrival   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[0]}")
                    timeArrival.after(afterTime)&& timeArrival.before(beforeArrival)
                }
            }
        }
    }

    private fun addNamesAirline() {
        namesAirlines.clear()
        namesAirlines.add("Select All")
        namesAirlines.add("Air France")
        namesAirlines.add("British Airways")
        namesAirlines.add("Cathay Pacific")
        namesAirlines.add("China Airlines")
        namesAirlines.add("Emirates")
        namesAirlines.add("Etihad")
        namesAirlines.add("Garuda Indonesia")
        namesAirlines.add("KLM")
        namesAirlines.add("Malaysia Airlines")
        namesAirlines.add("Qatar Airways")
        namesAirlines.add("Singapore Airlines")
        namesAirlines.add("Turkey Airlines")
    }
}