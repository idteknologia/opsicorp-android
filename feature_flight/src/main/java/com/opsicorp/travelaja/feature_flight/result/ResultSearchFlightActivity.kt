package com.opsicorp.travelaja.feature_flight.result

import java.util.*
import android.view.View
import org.koin.core.inject
import java.text.DateFormat
import android.app.Activity
import android.content.Intent
import androidx.transition.Fade
import kotlin.collections.HashMap
import java.text.SimpleDateFormat
import org.koin.core.KoinComponent
import kotlin.collections.ArrayList
import com.opsigo.travelaja.utility.*
import androidx.transition.Transition
import com.opsigo.travelaja.BaseActivity
import org.koin.core.parameter.parametersOf
import androidx.transition.TransitionManager
import opsigo.com.datalayer.mapper.Serializer
import com.opsicorp.travelaja.feature_flight.R
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackAirlinePreference
import opsigo.com.domainlayer.callback.CallbackResultSearchFlight
import com.opsigo.travelaja.module.item_custom.calendar.CalendarDialog
import com.opsicorp.travelaja.feature_flight.dialog.FlightShortByDialog
import com.opsicorp.travelaja.feature_flight.filter.FilterFlightActivity
import com.opsigo.travelaja.module.item_custom.menu_sort.BottomSheetSort
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import com.opsigo.travelaja.module.item_custom.btn_filter.FilterOpsicorp
import kotlinx.android.synthetic.main.detail_search_filter_activity_new.*
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.RoutesItem
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.AirlinePrefByCompanyRequest

class ResultSearchFlightActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult, KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        FlightShortByDialog.CallbackDialog {
    
    override fun getLayout(): Int {
        return R.layout.detail_search_filter_activity_new
    }

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
        try {
            dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
            setRecyclerView()
            filter.callbackOnclickFilter(this)
            btnChangeResult.setTextButton("Change Result")
            btnChangeResult.callbackOnclickButton(this)
        }catch (e:Exception){
            e.printStackTrace()
        }

        //init toolbar
        setToolbar()
        getAirlineByCompany()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_result_flightnew.layoutManager = layoutManager
        rv_result_flightnew.itemAnimator = DefaultItemAnimator()
        rv_result_flightnew.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 ->{
                        if (!Constants.multitrip){
                            if (totalGetDataFlight!=dataCodeAirline.listSchedule.size){
                                data.removeAll(data.filter { it.typeLayout == 5 })
                                gotoDetailFlight(position)
                            }
                            else{
                                gotoDetailFlight(position)
                            }
                        }
                        else {
                            val dataSelected = data.get(position).listFlightModel
                            val intent = Intent()
                            intent.putExtra(Constants.KEY_INTENT_SELECT_FLIGHT,Serializer.serialize(dataSelected))
                            Globals.finishResultOk(this@ResultSearchFlightActivity,intent)
                        }

                    }
                }
            }
        })
    }

    private fun gotoDetailFlight(position: Int) {
        val dataSelected = data.get(position).listFlightModel
        Globals.DATA_FLIGHT = Serializer.serialize(dataSelected, ResultListFlightModel::class.java)
        gotoActivity(DetailResultFlightActivity::class.java)
    }

    private fun getAirlineByCompany() {
        if (Constants.ALREADY_SEARCH_FLIGHT){
            mappingByDate()
        }
        else {
            GetDataAccomodation(getBaseUrl()).getPreferedFlight(getToken(),dataRequestAirlinePref(),object : CallbackAirlinePreference {
                override fun successLoad(data: AirlineCodeCompanyModel) {
                    clearDataListFlight()
                    dataCodeAirline = data
                    addDataLoading()
                    Constants.ALREADY_SEARCH_FLIGHT = true
                    data.listSchedule.forEach {
                        getDataFlight(it)
                    }
                }

                override fun failedLoad(message: String) {

                }
            })
        }

    }

    private fun dataRequestAirlinePref(): HashMap<Any, Any> {
        val data = AirlinePrefByCompanyRequest()
        data.preferredCarriers = ArrayList()
        if (!Constants.multitrip){
            if (Globals.ONE_TRIP){
                data.flightTripType    = 1
            }
            else{
                data.flightTripType    = 2
            }
            data.routes            = dataRoutesRequest()
        }
        else {
            data.routes            = dataRoutesRequestMultiTrip()
            data.flightTripType    = 3
        }
        data.cabinClassList    = dataCabinClass()
        data.travelAgent       = Globals.getConfigCompany(this).defaultTravelAgent
        data.adult             = 1
        data.infant            = 0
        data.employeeId        = Globals.getProfile(this).employId
        data.child             = 0
        return Globals.classToHashMap(data, AirlinePrefByCompanyRequest::class.java)

    }

    private fun dataRoutesRequestMultiTrip(): List<RoutesItem?> {
        val dataRoutes    = ArrayList<RoutesItem>()
        val dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)
        dataOrder.routes.forEach {
            val model         = RoutesItem()
            model.origin      = it.idOrigin
            model.destination = it.idDestination //"BDO"
            model.departDate  = it.dateDeparture  //"2020-08-28"
            dataRoutes.add(model)
        }
        return dataRoutes
    }

    private fun dataCabinClass(): List<Int?> {
        val dataCabin = ArrayList<Int>()
        dataCabin.add(dataOrder.classFlightCode.toInt())
        return dataCabin
    }

    private fun dataRoutesRequest(): List<RoutesItem?> {
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

    private fun addDepartureData(): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idOrigin
        model.destination = dataOrder.idDestination //"BDO"
        model.departDate  = dataOrder.dateDeparture //"2020-08-28"
        return model
    }

    private fun addReturnData(): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idDestination
        model.destination = dataOrder.idOrigin //"BDO"
        model.departDate  = dataOrder.dateArrival //"2020-08-28"
        return model
    }

    private fun clearDataListFlight() {
        data.clear()
//        dataFromServer.clear()
        Constants.DATA_FLIGHT_ARIVAL.clear()
        dataFilter.clear()
    }

    private fun getDataFlight(airlineCode: ListScheduleItem) {
        GetDataAccomodation(getBaseUrl()).getSearchFlight(getToken(),dataSearchFlight(airlineCode),object : CallbackResultSearchFlight {
            override fun success(mData: ArrayList<AccomodationResultModel>) {
                if (Constants.multitrip){
                    totalGetDataFlight++
                    Constants.DATA_RESULT_FLIGHT_MULTI_CITY.addAll(mData)
                    empty_result.gone()
                    if (totalGetDataFlight==dataCodeAirline.listSchedule.size){
                        mappingByDate()
                    }
                    else{
                        addDataLoading()
                    }
                }
                else {
                    totalGetDataFlight++
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        data.removeIf { it.typeLayout==5 }
                    }
                    mData.filter { it.listFlightModel.isFlightArrival == false }.forEach {
                        data.add(it)
                    }
                    mData.filter { it.listFlightModel.isFlightArrival == true }.forEach {
                        Constants.DATA_FLIGHT_ARIVAL.add(it)
                    }
                    /*data.clear()
                    data.addAll(dataFromServer)*/
                    empty_result.gone()
                    if (totalGetDataFlight==dataCodeAirline.listSchedule.size){
                        checkEmptyData()
                    }
                    else{
                        addDataLoading()
                    }
                }
            }

            override fun failed(error: String) {
                showAllert("Sorry",error)
            }
        })
    }

    private fun mappingByDate() {
        data.clear()
        val position = intent.getBundleExtra(Constants.KEY_BUNDLE).getInt(Constants.positionFlightMulticity)
        data.addAll(Constants.DATA_RESULT_FLIGHT_MULTI_CITY.filter { it.listFlightModel.departDate.equals(dataOrder.routes[position].dateDeparture) })
        checkEmptyData()
    }

    private fun dataSearchFlight(airlineCode: ListScheduleItem): HashMap<Any, Any> {
        return Globals.classToHashMap(airlineCode, ListScheduleItem::class.java)
    }

    private fun checkEmptyData() {
        if (data.isEmpty()){
            empty_result.visibility = View.VISIBLE
            rv_result_flightnew.gone()
        }
        else {
            empty_result.visibility = View.GONE
            showTotalData()
            dataFilter.clear()
            dataFilter.addAll(data)
            adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
        }
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

    private fun addDataLoading() {
        data.addAll(DataDummyAccomodation().addDataLoading())
        adapter.setDataList(data,this)
    }

    override fun onResume() {
        if (Globals.ALL_READY_SELECT_DEPARTING){
            setDataArrival()
        }
        super.onResume()
        setToolbar()
    }

    private fun setToolbar(mDate : String = "") {
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        var originName      = ""
        var destinationName = ""
        var date            = ""
        var titleDate       = ""

        if (!Constants.multitrip){
            if (Globals.ALL_READY_SELECT_DEPARTING){
                if (mDate.isNotEmpty()) date = mDate else date = DateConverter().setDateFormat3(dataOrder.dateArrival)
                originName      = dataOrder.destinationName
                destinationName = dataOrder.originName
                titleDate       = "Returning"
            }
            else {
                if (mDate.isNotEmpty()) date = mDate  else date = DateConverter().setDateFormat3(dataOrder.dateDeparture)
                originName      = dataOrder.originName
                destinationName = dataOrder.destinationName
                titleDate       = "Departure"
            }
            toolbar.setDoubleTitle("${originName} - ${destinationName}","$titleDate Date : ${date} - 1 pax")
        }
        else {
            val position = intent.getBundleExtra(Constants.KEY_BUNDLE).getInt(Constants.positionFlightMulticity)
            toolbar.setDoubleTitle("${dataOrder.routes[position].originName}(${dataOrder.routes[position].idOrigin}) - ${dataOrder.routes[position].destinationName}(${dataOrder.routes[position].idDestination})","${DateConverter().getDate(dataOrder.routes[position].dateDeparture,"yyyy-MM-dd","EEE, dd MMM yyyy")} - 1 pax")
        }
    }

    private fun setDataArrival() {
        data.clear()
        data.addAll(Constants.DATA_FLIGHT_ARIVAL)
        adapter.setDataList(Constants.DATA_FLIGHT_ARIVAL,this)
    }

    private fun addDataDummyFlight() {
        data.clear()
        data.addAll(DataDummyAccomodation().addDataListFlight())
        adapter.setDataList(data,this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
        when(requestCode){
            Constants.GET_FILTER -> {
                if (resultCode== Activity.RESULT_OK){
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

    private fun filterByDurationTime() {
        val dateFormatter: DateFormat = SimpleDateFormat("hh:mm")
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()>0 && !it.listFlightModel.isComply}.sortedBy { dateFormatter.parse(it.listFlightModel.duration) })
        if (data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0 }.sortedBy { dateFormatter.parse(it.listFlightModel.duration)  })
        }
        if (data.filter { it.listFlightModel.numberSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()==0}.sortedBy { dateFormatter.parse(it.listFlightModel.duration) })
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

    private fun filterByEarliestDeparture(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()>0 && !it.listFlightModel.isComply}.sortedBy { it.listFlightModel.dateDeparture })
        if (data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0 }.sortedBy { it.listFlightModel.dateDeparture })
        }
        if (data.filter { it.listFlightModel.numberSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()==0}.sortedBy { it.listFlightModel.dateDeparture })
        }
        adapter.setDataList(dataFilter,this@ResultSearchFlightActivity)
    }

    private fun filterByLatestDeparture(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()>0 && !it.listFlightModel.isComply}.sortedBy { it.listFlightModel.dateDeparture }.reversed())
        if (data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listFlightModel.isComply && it.listFlightModel.numberSeat.toInt()>0 }.sortedBy { it.listFlightModel.dateDeparture })
        }
        if (data.filter { it.listFlightModel.numberSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listFlightModel.numberSeat.toInt()==0}.sortedBy { it.listFlightModel.dateDeparture }.reversed())
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

    override fun onFilter() {
        if (data.isNotEmpty()){
            gotoActivityResult(FilterFlightActivity::class.java,Constants.GET_FILTER)
        }
    }

    override fun onSort() {
        if (data.isNotEmpty()){
            /*if(Globals.typeAccomodation=="Flight"){
                val bottomSheet = BottomSheetSort(current_sort)
                bottomSheet.show(supportFragmentManager, "FlightSort")
            }*/
            FlightShortByDialog(this).create(current_sort, this)
        }
    }

    override fun onChangeDate() {
        CalendarDialog(this).create(object : CalendarDialog.CallbackDialog{
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

                getAirlineByCompany()
                setToolbar(DateConverter().getDate(departureDate,"yyyy-MM-dd","EEE, dd MMM yyyy"))
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

    override fun onOptionSortClick(text: String, sort: Int) {
        current_sort = sort
    }

    override fun selected(int: Int) {
        current_sort = int
        when(int){
            0 -> {
                firstFilter()
            }
            1 -> {
                filterByEarliestDeparture()
            }
            2 -> {
                filterByLatestDeparture()
            }
            3 -> {
                filterByDurationTime()
            }
        }
    }

    override fun onClicked() {
        finish()
    }
}