package com.opsigo.travelaja.module.accomodation.hotel.result

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.hotel.search.SearcHotelRequest
import com.opsigo.travelaja.module.accomodation.hotel.detail_hotel.DetailHotelActivity
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import com.opsigo.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import com.opsigo.travelaja.module.item_custom.menu_sort.BottomSheetSort
import com.opsigo.travelaja.module.item_custom.btn_filter.FilterOpsicorp
import kotlinx.android.synthetic.main.detail_search_hotel_activity.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackSearchHotel
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.transition.TransitionManager
import opsigo.com.datalayer.mapper.Serializer
import android.support.transition.Transition
import org.koin.core.parameter.parametersOf
import com.opsigo.travelaja.BaseActivity
import android.support.transition.Fade
import com.opsigo.travelaja.utility.*
import kotlin.collections.ArrayList
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import org.koin.core.inject
import android.view.View
import android.os.Build
import java.util.*

class ResultSearchHotelActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult,KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,
        /*FlightShortByDialog.CallbackDialog ,*/
        OnclickListenerRecyclerViewAnimation{

    override fun getLayout(): Int { return R.layout.detail_search_hotel_activity }

    val jsonData = "{\n" +
            "  \"GuestPassport\": \"ID\",\n" +
            "  \"DestinationKey\": \"A9oThaCBPU27Sez5ghOkzA\",\n" +
            "  \"CheckInDate\": \"2020-11-11\",\n" +
            "  \"CheckOutDate\": \"2020-11-14\",\n" +
            "  \"CountRoom\": 1,\n" +
            "  \"CountGuest\": 1,\n" +
            "  \"HotelName\": \"\",\n" +
            "  \"travelAgent\":\"apiDev\",\n" +
            "  \"purpose\":\"data\",\n" +
            "  \"Origin\":\"Bandung\",\n" +
            "  \"Destination\":\"Surabaya\"\n" +
            "}"

    var loadingSearch  = false
    var current_sort      = 0
    val nameStation       = ArrayList<String>()
    var data              = ArrayList<AccomodationResultModel>()
    var dataFilter        = ArrayList<AccomodationResultModel>()
    val adapter by inject<ResultAccomodationAdapter> { parametersOf() }
    var departureDate     = ""
    var prizeMax          = 0
    var prizeMin          = 0

    val timeSelectFilterDeparture = ArrayList<String>()
    val timeSelectFilterArrival = ArrayList<String>()

    override fun OnMain() {
        initItemViews()
    }

    private fun initItemViews() {
        setToolbar(Constants.CheckInDate)
        setRecyclerView()
        filter.callbackOnclickFilter(this)
    }


    private fun setToolbar(depart: String) {
        val departing = if (depart.contains(" ")) DateConverter().getDate(depart.split(" ")[0],"yyyy-MM-dd","EEE, yyyy MMM dd") else DateConverter().getDate(departureDate,"yyyy-MM-dd","EEE, yyyy MMM dd")
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
        toolbar.setDoubleTitle(Constants.City.name,"${departing} - ${Constants.TotalNight.split(" ")[0].toInt()} Night(s)")
    }

    private fun addDataLoading(){
        loadingSearch = true
        data.clear()
        data.addAll(DataDummyAccomodation().addDataLoadingHotel())
        adapter.setDataList(data,this)
    }

    private fun checkEmptyData() {
        if (data.isEmpty()){
            empty_result.visibility = View.VISIBLE
        }
        else {
            showTotalData()
            empty_result.visibility = View.GONE
        }
    }

    private fun showTotalData() {
        Globals.delay(1000.toLong(),object :Globals.DelayCallback{
            override fun done() {
                tv_total_data.text = "${data.size} Best departing flights"
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
        adapter.setOnclickListenerWithAnimation(this)

        getDataHotel()
    }


    override fun onFilter() {
        /*if (data.isNotEmpty()){
            gotoActivityResult(FilterFlightActivity::class.java,Constants.GET_FILTER)
        }*/
    }

    override fun onSort() {

    }

    override fun onOptionSortClick(text: String, sort: Int) {
        current_sort = sort
    }

    override fun onChangeDate() {
        /*CalendarDialog(this).create(object :CalendarDialog.CallbackDialog{
            override fun selected(date: Date) {
                tv_total_data.visibility = View.GONE
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                departureDate = formatter.format(date.time)
            }
        })*/
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

    /*override fun selected(int: Int) {
        current_sort = int
        when(int){
            0 -> {

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
    }*/

    override fun onClick(views: Int, position: Int, viewAnim: View) {
        when(views){
            -3 ->{
                Constants.DATA_HOTEL    = Serializer.serialize(data.get(position).listHotelModel, ResultListHotelModel::class.java)
                gotoActivityUsingTransition(viewAnim,DetailHotelActivity::class.java)
            }
        }
    }

    fun getDataHotel(){
        addDataLoading()
        GetDataAccomodation(getBaseUrl()).getSearchHotel(getToken(),dataSearch(),object : CallbackSearchHotel {
            override fun success(mData: ArrayList<AccomodationResultModel>) {
                loadingSearch = false
                data.clear()
                data.addAll(mData)
                adapter.setDataList(data,this@ResultSearchHotelActivity)
                checkEmptyData()
            }

            override fun failed(errorMessage: String) {
                loadingSearch = false
                data.clear()
                checkEmptyData()
//                showAllert("Sorry",errorMessage)
            }
        })
    }

    private fun dataSearch(): HashMap<Any, Any> {
        val dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        val model = SearcHotelRequest() //Serializer.deserialize(jsonData,SearcHotelRequest::class.java) //SearcHotelRequest()

        model.guestPassport    = Constants.Country.id
        model.origin           = dataTrip.originId
        model.checkInDate      = Constants.CheckInDate.split(" ")[0]
        model.checkOutDate     = dateCheckoutCalculation(Constants.CheckInDate.split(" ")[0])
        model.countGuest       = 1
        model.purpose          = dataTrip.purpose
        model.travelAgent      = Globals.getConfigCompany(this).defaultTravelAgent
        model.destination      = dataTrip.destinationId
        model.countRoom        = 1
        model.hotelName        = ""

        when (Constants.typeDestination){
            "destinationByCompany" -> {
                model.destinationKey   = ""
                model.latitude         = Constants.Latitude
                model.longitude        = Constants.Longitude
            }
            "destinationByCity" -> {
                model.destinationKey   = Constants.City.id
                model.latitude         = null
                model.longitude        = null
            }
            "destinationByMap" -> {
                model.destinationKey   = ""
                model.latitude         = Constants.Latitude
                model.longitude        = Constants.Longitude
            }
        }
        return Globals.classToHashMap(model,SearcHotelRequest::class.java)
    }

    private fun dateCheckoutCalculation(string: String): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        cal.time = sdf.parse(string)
        cal.add(Calendar.DATE, Constants.TotalNight.split(" ")[0].toInt())
        return sdf.format(cal.time)
    }

    /*
    private fun addDataDummyHotel() {
        data.clear()
        data.addAll(DataDummyAccomodation().addDataListHotel())
        adapter.setDataList(data,this)
    }*/

}