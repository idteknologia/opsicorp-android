package com.opsigo.travelaja.module.accomodation.flight.result

import opsigo.com.datalayer.request_model.accomodation.flight.search.SearchFlightRequest
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.accomodation.flight.filter.FilterFlightActivity
import com.opsigo.travelaja.module.accomodation.flight.dialog.FlightShortByDialog
import com.opsigo.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import com.opsigo.travelaja.module.item_custom.btn_filter.FilterOpsicorp
import com.opsigo.travelaja.module.item_custom.menu_sort.BottomSheetSort
import kotlinx.android.synthetic.main.detail_search_filter_activity.*
import com.opsigo.travelaja.module.item_custom.calendar.CalendarDialog
import opsigo.com.domainlayer.callback.CallbackResultSearchFlight
import com.opsigo.travelaja.utility.Constants.DATA_FLIGHT_ARIVAL
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.transition.TransitionManager
import com.opsigo.travelaja.utility.DateConverter
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import android.support.transition.Transition
import org.koin.core.parameter.parametersOf
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import android.support.transition.Fade
import kotlin.collections.ArrayList
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import org.koin.core.inject
import java.text.DateFormat
import android.view.View
import android.util.Log
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.AirlinePrefByCompanyRequest
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.RoutesItem
import opsigo.com.domainlayer.callback.CallbackAirlinePreference
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem
import java.util.*

class ResultSearchFlightActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult,KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,FlightShortByDialog.CallbackDialog{


    override fun getLayout(): Int { return R.layout.detail_search_filter_activity }

    var current_sort = 0
    val nameStation = ArrayList<String>()
    lateinit var dataOrder: OrderAccomodationModel
    var data = ArrayList<AccomodationResultModel>()
    var dataFilter = ArrayList<AccomodationResultModel>()
    val adapter by inject<ResultAccomodationAdapter> { parametersOf() }
    var departureDate = ""
    var prizeMax = 0
    var prizeMin = 0
    var totalGetDataFlight = 0
    lateinit var dataCodeAirline: AirlineCodeCompanyModel

    val timeSelectFilterDeparture = ArrayList<String>()
    val timeSelectFilterArrival = ArrayList<String>()
    val tFormarter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
    val dFormarter: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun OnMain() {
        initItemViews()
    }

    private fun initItemViews() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        setRecyclerView()
        filter.callbackOnclickFilter(this)

        Log.d("data order",":" + dataOrder.toString())

        //init toolbar
        val date = DateConverter().setDateFormat3(dataOrder.dateDeparture)
        setToolbar(date)
    }

    override fun onResume() {
        super.onResume()
        noFilter()
    }


    private fun setToolbar(depart: String) {

        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        toolbar.setDoubleTitle("${dataOrder.originName} - ${dataOrder.destinationName}","Departure Date : ${depart} - 1 pax")
    }

    private fun addDataDummyFlight() {
        data.clear()
        data.addAll(DataDummyAccomodation().addDataListFlight())
        adapter.setDataList(data,this)
    }

    private fun addDataLoadng(){
        data.clear()
        data.addAll(DataDummyAccomodation().addDataLoading())
        adapter.setDataList(data,this)
    }

    private fun getDataFlight(airlineCode: ListScheduleItem) {
        GetDataAccomodation(getBaseUrl()).getSearchFlight(getToken(),dataSearchFlight(airlineCode),object :CallbackResultSearchFlight{
            override fun success(mData: ArrayList<AccomodationResultModel>) {
                if (Globals.ALL_READY_SELECT_DEPARTING){
                    mData.filter { it.listFlightModel.isFlightArrival == true }.forEach {
                        data.add(it)
                    }
                }else{
                    mData.filter { it.listFlightModel.isFlightArrival == false }.forEach {
                        data.add(it)
                    }
                    mData.filter { it.listFlightModel.isFlightArrival == true }.forEach {
                        DATA_FLIGHT_ARIVAL.add(it)
                    }
                }
                totalGetDataFlight++
                checkEmptyData()
            }

            override fun failed(error: String) {
                showAllert("Sorry",error)
            }
        })

    }

    private fun checkEmptyData() {
        if (data.isEmpty()){
            if (totalGetDataFlight==dataCodeAirline.listSchedule.size){
                empty_result.visibility = View.VISIBLE
            }
        }
        else {
            empty_result.visibility = View.GONE
            noFilter()
            if (totalGetDataFlight==dataCodeAirline.listSchedule.size){
                showTotalData()
            }
        }
    }

    private fun dataSearchFlight(airlineCode: ListScheduleItem): HashMap<Any, Any> {
/*

        val data = SearchFlightRequest()

        data.airline     = airlineCode.codeAirline.toString()
        data.jobTitleId  = getProfile().idPosition
        data.departDate  = dataOrder.dateDeparture//"2020-01-26"
        data.destination = dataOrder.idDestination//"BD"
        data.origin      = dataOrder.idOrigin//"GMR"
        data.destination = dataOrder.idDestination
        data.preferredCarriers = airlineCode.iataCode

        if (Globals.ALL_READY_SELECT_DEPARTING){
            departureDate  = if (dataOrder.dateArrival.contains(" ")) dataOrder.dateArrival.split(" ")[0] else dataOrder.dateArrival//"2020-01-26"
        }else{
            departureDate  = if (dataOrder.dateDeparture.contains(" ")) dataOrder.dateDeparture.split(" ")[0] else dataOrder.dateDeparture//"2020-01-26"
        }

        when (dataOrder.typeTrip){
            "one_trip"->{
                data.returnDate = ""
            }
            "round_trip"->{
                data.returnDate = dataOrder.dateArrival
            }
        }

        data.travelAgentCode =  Globals.getConfigCompany(this).defaultTravelAgent //"GoldenNusa"
        data.compCode   = getProfile().companyCode //"000006"
*/
//        return Globals.classToHashMap(data, SearchFlightRequest::class.java)
        return Globals.classToHashMap(airlineCode, ListScheduleItem::class.java)
    }

    private fun showTotalData() {
        var str = ""
        if(Globals.ALL_READY_SELECT_DEPARTING){
            str = "returning"
        }else{
            str = "departing"
        }

        Globals.delay(1000.toLong(),object :Globals.DelayCallback{
            override fun done() {
                tv_total_data.text = "${data.size} Best "+ str +" flights"
                val transition: Transition = Fade()
                transition.setDuration(700)
                transition.addTarget(R.id.tv_total_data)
                TransitionManager.beginDelayedTransition(parent_layout, transition)
                tv_total_data.setVisibility(View.VISIBLE)
            }
        })
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_result_flight.layoutManager = layoutManager
        rv_result_flight.itemAnimator = DefaultItemAnimator()
        rv_result_flight.adapter = adapter

        adapter.setOnclickListener(object :OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 ->{
                        val dataSelected = data.get(position).listFlightModel

//                        if (Globals.ALL_READY_SELECT_DEPARTING){
//                            dataSelected.originName = dataOrder.destinationName
//                            dataSelected.destinationName = dataOrder.originName
//                        }
//                        else {
//                            dataSelected.originName = dataOrder.originName
//                            dataSelected.destinationName = dataOrder.destinationName
//                        }

                        Globals.DATA_FLIGHT = Serializer.serialize(dataSelected, ResultListFlightModel::class.java)
                        gotoActivity(DetailResultFlightActivity::class.java)
                    }
                }
            }
        })
    }

    private fun clearDataListFlight() {
        data.clear()
        DATA_FLIGHT_ARIVAL.clear()
        dataFilter.clear()
    }


    override fun onFilter() {
        if (data.isNotEmpty()){
            gotoActivityResult(FilterFlightActivity::class.java,Constants.GET_FILTER)
        }
    }

    override fun onSort() {
        if (data.isNotEmpty()){
            if(Globals.typeAccomodation=="Flight"){
                val bottomSheet = BottomSheetSort(current_sort)
                bottomSheet.show(supportFragmentManager, "FlightSort")
            }
        }

    }

    override fun onOptionSortClick(text: String, sort: Int) {
        current_sort = sort
    }

    override fun onChangeDate() {
        CalendarDialog(this).create(object :CalendarDialog.CallbackDialog{
            override fun selected(date: Date) {
                tv_total_data.visibility = View.GONE
                empty_result.visibility =  View.GONE
                setLog(SimpleDateFormat("yyyy-MM-dd").format(date))
                departureDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                if (Globals.ALL_READY_SELECT_DEPARTING){
                    dataOrder.dateArrival = departureDate
                }else{
                    dataOrder.dateDeparture = departureDate
                }
                clearDataListFlight()
                addDataLoadng()
                dataCodeAirline.listSchedule.forEach {
                    getDataFlight(it)
                }
            }
        })
    }



    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
        when(requestCode){
            Constants.GET_FILTER -> {
                if (resultCode==Activity.RESULT_OK){
                    setLog(Constants.dataFilterMaxPriceAccomodation.toString())
                    Constants.dataDepartureTime.forEachIndexed{
                        index, accomodationPreferanceModel ->
                        if (accomodationPreferanceModel.checked){
                            setLog(accomodationPreferanceModel.time.trim().split("-")[0])
                        }
                    }
                    timeSelectFilterArrival.clear()
                    timeSelectFilterDeparture.clear()
                    nameStation.clear()
                    Constants.dataDepartureTime.forEachIndexed { index, accomodationPreferanceModel -> timeSelectFilterDeparture.add(accomodationPreferanceModel.time)}
                    Constants.dataArrivalTime.forEachIndexed { index, accomodationPreferanceModel -> timeSelectFilterArrival.add(accomodationPreferanceModel.time)}
                    Constants.dataNameTrainSelected.forEachIndexed { index, accomodationPreferanceModel -> nameStation.add(accomodationPreferanceModel.name) }
                    this.prizeMax = Constants.dataFilterMaxPriceAccomodation
                    this.prizeMin = Constants.dataFIlterMinPriceAccomodation

                }
            }
        }
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        setToolbar(displayStartDate)
    }

    override fun endDate(displayEndDate: String, endDate: String) {

    }

    override fun canceledCalendar() {

    }

    override fun selected(int: Int) {
        current_sort = int
        when(int){
            0 -> {
                firstFilter()
            }
            1 -> {
                //filterByEarliestDeparture()
            }
            2 -> {
                //filterByLatestDeparture()
            }
            3 -> {
                //filterByDurationTime()
            }
        }
    }

    private fun filterByDurationTime() {
        val dateFormatter: DateFormat = SimpleDateFormat("hh:mm")
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0 && !it.listTrainModel.notComply}.sortedBy { dateFormatter.parse(it.listTrainModel.durationTime) })
        if (data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0 }.sortedBy { dateFormatter.parse(it.listTrainModel.durationTime)  })
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0}.sortedBy { dateFormatter.parse(it.listTrainModel.durationTime) })
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    private fun firstFilter() {
        dataFilter.clear()

        dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()>0 &&!it.listFlightModel.isComply}.sortedBy { it.listFlightModel.price.toInt() })

        if (data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0 }.sortedBy { it.listFlightModel.price.toInt() })
        }
        if (data.filter { it.listFlightModel.numberSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()==0}.sortedBy { it.listFlightModel.price.toInt() })
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    private fun noFilter() {
        if (Globals.ALL_READY_SELECT_DEPARTING){
//            data.clear()
//            dataFilter.clear()

            data.addAll(DATA_FLIGHT_ARIVAL)
            dataFilter.addAll(DATA_FLIGHT_ARIVAL)
            adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
        }else{
//            dataFilter.clear()
            dataFilter.addAll(data)
            adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
        }
    }

    private fun filterByEarliestDeparture(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0 && !it.listTrainModel.notComply}.sortedBy { it.listTrainModel.dateDeparture })
        if (data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0 }.sortedBy { it.listTrainModel.dateDeparture })
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0}.sortedBy { it.listTrainModel.dateDeparture })
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    private fun filterByLatestDeparture(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0 && !it.listTrainModel.notComply}.sortedBy { it.listTrainModel.dateDeparture }.reversed())
        if (data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0 }.sortedBy { it.listTrainModel.dateDeparture })
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0}.sortedBy { it.listTrainModel.dateDeparture }.reversed())
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    fun filterByTimeDeparture(){
        timeSelectFilterDeparture.forEachIndexed { index, s ->
            dataFilter.clear()
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0
                    && it.listTrainModel.dateDeparture.after(tFormarter.parse(dFormarter.format(it.listTrainModel.dateDeparture)+" ${s.split("-")[0]}"))
                    && it.listTrainModel.dateDeparture.before(tFormarter.parse(dFormarter.format(it.listTrainModel.dateDeparture)+" ${s.split("-")[1]}"))})
            if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
                dataFilter.add(headerNotAvailable())
                dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0
                        && it.listTrainModel.dateDeparture.after(tFormarter.parse(dFormarter.format(it.listTrainModel.dateDeparture)+" ${s.split("-")[0]}"))
                        && it.listTrainModel.dateDeparture.before(tFormarter.parse(dFormarter.format(it.listTrainModel.dateDeparture)+" ${s.split("-")[1]}"))})
            }
            adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
        }
    }



    fun filterByTimeArrivalTime(){
        timeSelectFilterArrival.forEachIndexed { index, s ->
            dataFilter.clear()
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0
                    && it.listTrainModel.dateArrival.after(tFormarter.parse(dFormarter.format(it.listTrainModel.dateArrival)+" ${s.trim().split("-")[0]}"))
                    && it.listTrainModel.dateArrival.before(tFormarter.parse(dFormarter.format(it.listTrainModel.dateArrival)+" ${s.trim().split("-")[1]}"))})

            if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
                dataFilter.add(headerNotAvailable())
                dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0
                        && it.listTrainModel.dateArrival.after(tFormarter.parse(dFormarter.format(it.listTrainModel.dateArrival)+" ${s.split("-")[0]}"))
                        && it.listTrainModel.dateArrival.before(tFormarter.parse(dFormarter.format(it.listTrainModel.dateArrival)+" ${s.split("-")[1]}"))})
            }
            adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
        }
    }

    fun filterByPrizeTrain(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0&&it.listTrainModel.price.toInt()>prizeMin&&it.listTrainModel.price.toInt()<prizeMax})
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0&&it.listTrainModel.price.toInt()>prizeMin&&it.listTrainModel.price.toInt()<prizeMax})
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    fun filterByNameTrain(){
        dataFilter.clear()
        nameStation.forEachIndexed { index, s ->
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0&&it.listTrainModel.nameStation==s})
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            nameStation.forEachIndexed { index, s ->
                dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0&&it.listTrainModel.nameStation==s})
            }
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    private fun headerNotComply(): AccomodationResultModel {
        val headerNotAvailable = AccomodationResultModel()
        headerNotAvailable.typeLayout = Constants.TYPE_HEADER_NOT_COMPLY
        return headerNotAvailable
    }

    private fun headerNotAvailable(): AccomodationResultModel {
        val headerNotAvailable = AccomodationResultModel()
        headerNotAvailable.typeLayout = Constants.TYPE_HEADER_SOLD_OUT
        return headerNotAvailable
    }

    fun getAirlineByCompany(){
        GetDataAccomodation(getBaseUrl()).getPreferedFlight(getToken(),dataRequestAirlinePref(),object : CallbackAirlinePreference {
            override fun successLoad(data: AirlineCodeCompanyModel) {
                clearDataListFlight()
                dataCodeAirline = data
                addDataLoadng()
                data.listSchedule.forEach {
                    getDataFlight(it)
                }
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    private fun dataRequestAirlinePref(): HashMap<Any, Any> {
        val data = AirlinePrefByCompanyRequest()
        data.preferredCarriers = ArrayList()
        data.flightTripType    = 0
        data.cabinClassList    = dataCabinClass()
        data.travelAgent       = ""
        data.adult             = 0
        data.infant            = 0
        data.routes            = dataRoutesRequest()
        data.employeeId        = ""
        data.child             = 0
        return Globals.classToHashMap(data,AirlinePrefByCompanyRequest::class.java)
    }

    private fun dataRoutesRequest(): ArrayList<RoutesItem> {
        val dataRoutes    = ArrayList<RoutesItem>()
        when (dataOrder.typeTrip){
            "one_trip"->{
                dataRoutes.add(addDepartureData())
            }
            "round_trip"->{
                dataRoutes.add(addDepartureData())
                dataRoutes.add(addReturnData())
            }
        }


        return dataRoutes
    }

    private fun addReturnData(): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idDestination
        model.destination = dataOrder.idOrigin //"BDO"
        model.departDate  = dataOrder.dateArrival //"2020-08-28"
        return model
    }

    private fun addDepartureData(): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idOrigin
        model.destination = dataOrder.idDestination //"BDO"
        model.departDate  = dataOrder.dateDeparture //"2020-08-28"
        return model
    }

    private fun dataCabinClass(): ArrayList<Int> {
        val dataCabin = ArrayList<Int>()
        dataCabin.add(dataOrder.classFlightCode.toInt())
        return dataCabin
    }
}