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
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import kotlinx.android.synthetic.main.filter_flight_activity_new.*
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.travelaja.utility.DateConverter
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import org.koin.core.KoinComponent
import android.content.Intent
import android.view.View
import android.os.Build

class FilterFlightActivity : BaseActivity(), ButtonDefaultOpsicorp.OnclickButtonListener, FilterTransitOpsicorp.OnclickFilterListener, KoinComponent, ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int {
        return R.layout.filter_flight_activity_new
    }

    var filterTransitSelected = -1

    /*val d1="{\n" +
            "      \"airline\":11,\n" +
            "      \"arrivalDate\":\"2021-08-10 06:00:00\",\n" +
            "      \"arriveDate\":\"2021-08-10\",\n" +
            "      \"arriveDateTimeView\":\"2021-08-10 06:00\",\n" +
            "      \"arriveTime\":\"06:00\",\n" +
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
            "      \"dateDeparture\":null,\n" +
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
            "      \"flightId\":\"ad2d14af-353f-4e45-87fb-49b8aa17e991\",\n" +
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
            "      \"nameClass\":\"First Class\",\n" +
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
            "      \"titleAirline\":\"Singapore Airline\",\n" +
            "      \"totalTransit\":0,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }"

    val d2 = "{\n" +
            "      \"airline\":11,\n" +
            "      \"arrivalDate\":\"2021-08-10 10:00:00\",\n" +
            "      \"arriveDate\":\"2021-08-10\",\n" +
            "      \"arriveDateTimeView\":\"2021-08-10 10:00\",\n" +
            "      \"arriveTime\":\"10:00\",\n" +
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
            "      \"dateDeparture\":null,\n" +
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
            "      \"nameClass\":\"Business\",\n" +
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
            "      \"titleAirline\":\"Garuda\",\n" +
            "      \"totalTransit\":1,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }"

    val d3 = "{\n" +
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
            "      \"dateDeparture\":null,\n" +
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
            "      \"flightId\":\"ad2d14af-353f-4e45-87fb-49b8aa17e990\",\n" +
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
            "      \"totalTransit\":2,\n" +
            "      \"transiteFlight\":[\n" +
            "        \n" +
            "      ]\n" +
            "    }"*/

    var dataCabin      = ArrayList<FilterFlightModel>()
    var dataDeparture  = ArrayList<FilterFlightModel>()
    var dataArrival    = ArrayList<FilterFlightModel>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()

    val adapterCabinClass by lazy { FilterFlightCabinAdapter(this,dataCabin) }
    val adapterDeparture by lazy { FilterFlightAdapter(this,dataDeparture) }
    val adapterArrival by lazy { FilterFlightAdapter(this,dataArrival) }
    val filterThemphorary = ArrayList<AccomodationResultModel>()

    var dataFilter = ArrayList<AccomodationResultModel>()

    override fun OnMain() {
//        dataFilter.add(dataDummy(d1))
//        dataFilter.add(dataDummy(d2))
//        dataFilter.add(dataDummy(d3))

        dataFilter = intent?.getParcelableArrayListExtra<AccomodationResultModel>(Constants.REQUEST_FLIGHT_FILTER)!!

        initToolbar()
        initRangeBar()
        initButtonTransit()
        initRecyclerView()
        mappingDataFilter()
        initButtonNext()
    }

    /*private fun dataDummy(string: String): AccomodationResultModel {
        val mData = AccomodationResultModel()
        mData.listFlightModel = Serializer.deserialize(string, ResultListFlightModel::class.java)
        return mData
    }
*/
    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
    }

    private fun mappingDataFilter() {
        if (!intent.getBooleanExtra(Constants.IS_ALLREADY_FLITER_FLIGHT,false)){
            dataCabin.addAll(DataDummyAccomodation().addCabinClass())
            dataDeparture.addAll(DataDummyAccomodation().addDataDepartureTime())
            dataArrival.addAll(DataDummyAccomodation().addDataDepartureTime())
            addDataAirPreferance()
            btn_next.setTextButton("See ${dataFilter.size} Flight(s)")
        }
        else {
            dataPrefarance = intent?.getParcelableArrayListExtra(Constants.FILTER_FLIGHT_PREFARANCE)!!
            dataArrival    = intent?.getParcelableArrayListExtra(Constants.FILTER_FLIGHT_ARRIVAL)!!
            dataDeparture  = intent?.getParcelableArrayListExtra(Constants.FILTER_FLIGHT_DEPARTURE)!!
            dataCabin      = intent?.getParcelableArrayListExtra(Constants.FILTER_FLIGHT_CABIN)!!
            filterTransitSelected = intent.getIntExtra(Constants.FILTER_FLIGHT_TRANSIT,-1)
            updateViewAdapter()
            filterData()
        }
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
                        filterData()
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
                        filterData()
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
                        filterData()
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
        val intent = Intent()
        intent.putExtra(Constants.FILTER_FLIGHT_ARRIVAL,dataArrival)
        intent.putExtra(Constants.FILTER_FLIGHT_DEPARTURE,dataDeparture)
        intent.putExtra(Constants.FILTER_FLIGHT_CABIN,dataCabin)
        intent.putExtra(Constants.FILTER_FLIGHT_PREFARANCE,dataPrefarance)
        intent.putExtra(Constants.FILTER_FLIGHT_TRANSIT,filterTransitSelected)
        if (filterThemphorary.isNotEmpty()){
            intent.putExtra(Constants.REQUEST_FLIGHT_FILTER,filterThemphorary)
        }
        else {
            intent.putExtra(Constants.FILTER_FLIGHT_TRANSIT,dataFilter)
        }
        Globals.finishResultOk(this,intent)
    }

    override fun onDirect() {
        filterTransitSelected = Constants.ON_DIRECT
        filterData()
    }

    override fun onOneTransit() {
        filterTransitSelected = Constants.ON_ONE_TRANSIT
        filterData()
    }

    override fun onTwoTransit() {
        filterTransitSelected = Constants.ON_TWO_TRANSIT
        filterData()
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
        dataPrefarance.forEach {
            it.checked = false
        }
        dataCabin.forEach {
            it.isSelected = false
        }
        dataDeparture.forEach {
            it.isSelected = false
        }
        dataArrival.forEach {
            it.isSelected = false
        }
        filterData()
        updateViewAdapter()
        filterTransitSelected = -1
        btn_transit.resetSelected()
        btn_next.setTextButton("See ${dataFilter.size} Flight(s)")
    }

    fun airlinesPreferance(view: View){
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(supportFragmentManager,"dialog")

        selectAccomodationPreferance.setCallbackListener(object : SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String,data:ArrayList<AccomodationPreferanceModel>) {
                tv_airline_prreferance_filter.text = string
                dataPrefarance = data
                filterData()
            }
        })
    }

    private fun addDataAirPreferance() {
        dataFilter.forEachIndexed { index, names ->
            val mData = AccomodationPreferanceModel()
            mData.id = "${index+1}"
            mData.name = names.listFlightModel.titleAirline
            dataPrefarance.add(mData)
        }
        for (i in 0 until dataPrefarance.size) {
            var j = i + 1
            while (j < dataPrefarance.size) {
                if (dataPrefarance.get(i).name.equals(dataPrefarance.get(j).name)) {
                    dataPrefarance.removeAt(j)
                    j--
                }
                j++
            }
        }
    }

    fun filterData(){
        filterThemphorary.clear()
        if (!dataArrival.filter { it.isSelected }.isNullOrEmpty()){
            dataArrival.filter { it.isSelected }.forEachIndexed { index, filterFlightModel ->
                dataFilter.forEach {
                    val timeArrival = DateConverter().stringToDate("yyyy-MM-dd HH:mm",it.listFlightModel.arrivalDate)
                    val afterTime   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[0]}")
                    val beforeArrival   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[1]}")
                    if ((timeArrival.after(afterTime)||timeArrival==afterTime)&&(timeArrival.before(beforeArrival)||timeArrival==beforeArrival)){
                        filterThemphorary.add(it)
                    }
                }
            }
        }

        if (!dataDeparture.filter { it.isSelected }.isNullOrEmpty()){
            dataDeparture.filter { it.isSelected }.forEachIndexed { index, filterFlightModel ->
                dataFilter.forEach {
                    val timeArrival = DateConverter().stringToDate("yyyy-MM-dd HH:mm",it.listFlightModel.arrivalDate)
                    val afterTime   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[0]}")
                    val beforeArrival   = DateConverter().stringToDate("yyyy-MM-dd HH:mm","${it.listFlightModel.arriveDate} ${filterFlightModel.time.trim().split("-")[1]}")
                    if ((timeArrival.after(afterTime)||timeArrival==afterTime)&&(timeArrival.before(beforeArrival)||timeArrival==beforeArrival)){
                        filterThemphorary.add(it)
                    }
                }
            }
        }

        if (!dataCabin.filter { it.isSelected }.isNullOrEmpty()){
            dataCabin.filter { it.isSelected }. forEachIndexed { index, className ->
                dataFilter.forEach {
                    if (className.name.toLowerCase().contains(it.listFlightModel.nameClass.toLowerCase())){
                        filterThemphorary.add(it)
                    }
                }
            }
        }

        if (filterTransitSelected!=-1){
            filterThemphorary.addAll(dataFilter.filter { it.listFlightModel.totalTransit.equals(filterTransitSelected) })
        }

        if (!dataPrefarance.filter { it.checked }.isNullOrEmpty()){
            dataPrefarance.filter { it.checked }.forEachIndexed { index, accomodationPreferanceModel ->
                dataFilter.forEach {
                    if (accomodationPreferanceModel.name.toLowerCase().contains(it.listFlightModel.titleAirline.toLowerCase())){
                        filterThemphorary.add(it)
                    }
                }
            }
        }

        for (i in 0 until filterThemphorary.size) {
            var j = i + 1
            while (j < filterThemphorary.size) {
                if (filterThemphorary.get(i).listFlightModel.selectedClassId.equals(filterThemphorary.get(j).listFlightModel.selectedClassId)&&filterThemphorary.get(i).listFlightModel.price.equals(filterThemphorary.get(j).listFlightModel.price)&&filterThemphorary.get(i).listFlightModel.flightNumber.equals(filterThemphorary.get(j).listFlightModel.flightNumber)) {
                    filterThemphorary.removeAt(j)
                    j--
                }
                j++
            }
        }
        btn_next.setTextButton("See ${filterThemphorary.size} Flight(s)")
    }

    fun updateViewAdapter(){
        adapterCabinClass.notifyDataSetChanged()
        adapterArrival.notifyDataSetChanged()
        adapterDeparture.notifyDataSetChanged()
        if (filterTransitSelected!=-1){
            when(filterTransitSelected){
                Constants.ON_DIRECT -> {
                    btn_transit.onDirectSeleted()
                }
                Constants.ON_ONE_TRANSIT -> {
                    btn_transit.oneTransitSeleted()
                }
                Constants.ON_TWO_TRANSIT -> {
                    btn_transit.twoTransitSeleted()
                }
            }
        }
        if (dataPrefarance.filter { it.checked }.isNotEmpty()){
            var dataName = ""
            dataPrefarance.filter {
                it.checked
            }.forEachIndexed { index, accomodationPreferanceModel ->
                if (index!=dataPrefarance.filter { it.checked }.size-1){
                    dataName = dataName+accomodationPreferanceModel.name+ " , "
                }
                else {
                    dataName = dataName+accomodationPreferanceModel.name
                }
            }
            tv_airline_prreferance_filter.text = dataName
        }
    }

}