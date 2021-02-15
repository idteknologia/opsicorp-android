package com.opsicorp.train_feature.result

import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.AccomodationPreferanceModel
import opsigo.com.datalayer.request_model.accomodation.train.search.SearchTrainRequest
import com.opsigo.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import com.opsigo.travelaja.module.item_custom.btn_filter.FilterOpsicorp
import com.opsigo.travelaja.module.item_custom.menu_sort.BottomSheetSort
import com.opsigo.travelaja.module.item_custom.calendar.CalendarDialog
import kotlinx.android.synthetic.main.search_result_train_activity.*
import com.opsicorp.train_feature.detail.DetailResultTrainActivity
import opsigo.com.domainlayer.callback.CallbackResultSearchTrain
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsicorp.train_feature.filter.TrainFilterActivity
import com.opsicorp.train_feature.dialog.TrainShortByDialog
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.transition.TransitionManager
import com.opsigo.travelaja.utility.DateConverter
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import android.support.transition.Transition
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import android.support.transition.Fade
import kotlin.collections.ArrayList
import com.opsicorp.train_feature.R
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import android.content.Intent
import android.app.Activity
import java.text.DateFormat
import android.view.View
import java.util.*

class ResultSearchTrainActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult,KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,
        TrainShortByDialog.CallbackDialog{

    override fun getLayout(): Int { return R.layout.search_result_train_activity}

    var loadingSearch = false
    var current_sort = 0
    val nameStation = ArrayList<String>()
    lateinit var dataOrder: OrderAccomodationModel
    var data = ArrayList<AccomodationResultModel>()
    var dataFilter = ArrayList<AccomodationResultModel>()
    val adapter by lazy { ResultAccomodationAdapter() }
    var departureDate = ""
    var prizeMax = 0
    var prizeMin = 0

    val timeSelectFilterDeparture = ArrayList<String>()
    val timeSelectFilterArrival = ArrayList<String>()
    val tFormarter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
    val dFormarter: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun OnMain() {
        initItemViews()
    }

    private fun initItemViews() {
        dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        setRecyclerView()
        getDataListener()
        filter.callbackOnclickFilter(this)

        //init toolbar
        setToolbar(dataOrder.dateDeparture)
    }

    private fun getDataListener() {
        getDataTrain()
    }

    override fun onResume() {
        if (Globals.ALL_READY_SELECT_DEPARTING){
            getDataTrain()
        }
        super.onResume()
    }

    private fun setToolbar(depart: String) {
        val departing = if (depart.contains(" ")) DateConverter().getDate(depart.split(" ")[0],"yyyy-MM-dd","EEE, yyyy MMM dd") else DateConverter().getDate(departureDate,"yyyy-MM-dd","EEE, yyyy MMM dd")
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        toolbar.setDoubleTitle("${dataOrder.originStationName} - ${dataOrder.destinationStationName}","Depart Date : ${departing} - 1 pax")
    }

    private fun addDataDummyFlight() {
        data.clear()
        data.addAll(DataDummyAccomodation().addDataListFlight())
        adapter.setDataList(data,this)
    }

    private fun addDataLoadng(){
        loadingSearch = true
        data.clear()
        data.addAll(DataDummyAccomodation().addDataLoading())
        adapter.setDataList(data,this)
    }

    private fun getDataTrain() {
        setLog(Serializer.serialize(dataSearchTrain()))
        addDataLoadng()
        GetDataAccomodation(getBaseUrl()).getSearchTrain(getToken(),dataSearchTrain(),object :CallbackResultSearchTrain{
            override fun success(mData: ArrayList<AccomodationResultModel>) {
                loadingSearch = false
                data.clear()
                data = mData
                firstFilter()
                showTotalData()
                checkEmptyData()
                setToolbar(departureDate)
            }

            override fun failed(error: String) {
                loadingSearch = false
                showAllert("Sorry",error)
            }
        })
    }

    private fun checkEmptyData() {
        if (data.isEmpty()){
            empty_result.visibility = View.VISIBLE
        }
        else {
            empty_result.visibility = View.GONE
        }
    }

    private fun dataSearchTrain(): HashMap<Any, Any> {
        val data = SearchTrainRequest()
        data.airline     = "21"
        data.jobTitleId  = getProfile().idPosition
        if (Globals.ALL_READY_SELECT_DEPARTING){
            departureDate  = if (dataOrder.dateArrival.contains(" ")) dataOrder.dateArrival.split(" ")[0] else dataOrder.dateArrival//"2020-01-26"
            data.destination = dataOrder.idOrigin//"BD"
            data.origin      = dataOrder.idDestination//"GMR"
        }else{
            data.destination = dataOrder.idDestination//"BD"
            data.origin      = dataOrder.idOrigin//"GMR"
            departureDate  = if (dataOrder.dateDeparture.contains(" ")) dataOrder.dateDeparture.split(" ")[0] else dataOrder.dateDeparture//"2020-01-26"
        }
        data.departDate  = departureDate

        data.returnDate  = ""
        data.travelAgent = Globals.getConfigCompany(this).defaultTravelAgent//"GoldenNusa"

        return Globals.classToHashMap(data, SearchTrainRequest::class.java)
    }

    private fun showTotalData() {
        Globals.delay(1000.toLong(),object :Globals.DelayCallback{
            override fun done() {
                tv_total_data.text = "${data.size} Tickets Found"
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
                    -2 ->{
                        Constants.DATA_TRAIN  = Serializer.serialize(dataFilter.get(position).listTrainModel, ResultListTrainModel::class.java)
                        gotoActivity(DetailResultTrainActivity::class.java)
                    }
                }
            }
        })
    }

    override fun onFilter() {
        if (data.isNotEmpty()&&!loadingSearch){
            setDataNameTrainForFilter()
            gotoActivityResult(TrainFilterActivity::class.java,Constants.GET_FILTER)
        }
    }

    private fun setDataNameTrainForFilter() {
        Constants.dataNameTrainAll.clear()
        data.forEach {
            val mData = AccomodationPreferanceModel()
            mData.name = it.listTrainModel.titleTrain
            Constants.dataNameTrainAll.add(mData)
        }
    }

    override fun onSort() {
        if (data.isNotEmpty()&&!loadingSearch){
            TrainShortByDialog(this).create(current_sort,this)
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
                getDataTrain()
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
                    setLog("==========---------")
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

                    filterByPrizeTrain()
                    filterByNameTrain()
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
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
    }

    private fun firstFilter() {
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0 &&!it.listTrainModel.notComply}.sortedBy { it.listTrainModel.price.toInt() })
        if (data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0}.isNotEmpty()){
            dataFilter.add(headerNotComply())
            dataFilter.addAll(data.filter { it.listTrainModel.notComply && it.listTrainModel.totalSeat.toInt()>0 }.sortedBy { it.listTrainModel.price.toInt() })
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0}.sortedBy { it.listTrainModel.price.toInt() })
        }
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
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
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
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
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
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
            adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
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
            adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
        }
    }

    fun filterByPrizeTrain(){
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0&&it.listTrainModel.price.toInt()>prizeMin&&it.listTrainModel.price.toInt()<prizeMax})
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0&&it.listTrainModel.price.toInt()>prizeMin&&it.listTrainModel.price.toInt()<prizeMax})
        }
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
    }

    fun filterByNameTrain(){
        nameStation.forEachIndexed { index, s ->
            dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()>0&&it.listTrainModel.nameStation==s})
        }
        if (data.filter { it.listTrainModel.totalSeat.toInt()==0}.isNotEmpty()){
            dataFilter.add(headerNotAvailable())
            nameStation.forEachIndexed { index, s ->
                dataFilter.addAll(data.filter { it.listTrainModel.totalSeat.toInt()==0&&it.listTrainModel.nameStation==s})
            }
        }
        adapter.setDataList(dataFilter,this@ResultSearchTrainActivity)
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
}