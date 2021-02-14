package com.khoiron.hotel_feature.result

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.datalayer.request_model.accomodation.hotel.search.SearcHotelRequest
import com.khoiron.hotel_feature.detail_hotel.DetailHotelActivity
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
import java.lang.Exception
import java.util.*

class ResultSearchHotelActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult,KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,
        OnclickListenerRecyclerViewAnimation{

    override fun getLayout(): Int { return R.layout.detail_search_hotel_activity }

    var loadingSearch     = false
    var current_sort      = 0
    val nameStation       = ArrayList<String>()
    var data              = ArrayList<AccomodationResultModel>()
    val adapter by inject<ResultAccomodationAdapter> { parametersOf() }
    var prizeMax          = 0
    var prizeMin          = 0
    val timeSelectFilterDeparture = ArrayList<String>()
    val timeSelectFilterArrival   = ArrayList<String>()

    var typeDestination = 0
    var latitude         = ""
    var longitude        = ""
    var idCountry        = ""
    var duration         = ""
    var idCity           = ""
    var checkIn          = ""
    var checkOut         = ""
    var nameCity         = ""
    var nameOffice       = ""
    var nameAirport      = ""

    object KeyBundle {
        var KEY_ID_COUNTRY       = "1"
        var KEY_DURATION         = "2"
        var KEY_ID_CITY          = "3"
        var KEY_DESTINATION      = "4"
        var KEY_LATITUDE         = "5"
        var KEY_LONGITUDE        = "6"
        var KEY_CHECKIN          = "7"
        var KEY_CHECKOUT         = "8"
        var KEY_NAME_CITY        = "9"
        var KEY_NAME_AIRPORT     = "10"
        var KEY_NAME_OFFICE      = "11"
        var KEY_NAME_COUNTRY     = "12"
    }

    override fun OnMain() {
        initItemViews()
    }

    private fun initItemViews() {
        filter.callbackOnclickFilter(this)
        getDataIntent()
        setToolbar(checkIn)
        setRecyclerView()
    }

    private fun getDataIntent() {
        try {
            typeDestination  = intent.getBundleExtra(Constants.KEY_BUNDLE).getInt(KeyBundle.KEY_DESTINATION)
            latitude         = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_LATITUDE,"")
            longitude        = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_LONGITUDE,"")
            idCountry        = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_ID_COUNTRY,"")
            duration         = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_DURATION,"")
            idCity           = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_ID_CITY,"")
            checkIn          = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_CHECKIN,"")
            checkOut         = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_CHECKOUT,"")
            nameCity         = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_NAME_CITY,"")
            nameOffice       = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_NAME_OFFICE,"")
            nameAirport      = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(KeyBundle.KEY_NAME_AIRPORT,"")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setToolbar(depart: String) {
        val departing = if (depart.contains(" ")) DateConverter().getDate(depart.split(" ")[0],"yyyy-MM-dd","EEE, yyyy MMM dd") else DateConverter().getDate(depart,"yyyy-MM-dd","EEE, yyyy MMM dd")
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
        when(typeDestination){
            Constants.SELECT_NEARBY_CITY -> {
                toolbar.setDoubleTitle(nameCity,"${departing} - ${duration.split(" ")[0].toInt()} Night(s)")
            }
            Constants.SELECT_NEARBY_AIRPORT -> {
                toolbar.setDoubleTitle(nameAirport,"${departing} - ${duration.split(" ")[0].toInt()} Night(s)")
            }
            Constants.SELECT_NEARBY_OFFICE -> {
                toolbar.setDoubleTitle(nameOffice,"${departing} - ${duration.split(" ")[0].toInt()} Night(s)")
            }
        }

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

    override fun onClick(views: Int, position: Int, viewAnim: View) {
        when(views){
            -3 ->{
                Constants.DATA_HOTEL    = Serializer.serialize(data.get(position).listHotelModel, ResultListHotelModel::class.java)
                gotoActivityUsingTransition(viewAnim, DetailHotelActivity::class.java)
            }
        }
    }

    fun getDataHotel(){
        addDataLoading()
        setLog("--------------")
        setLog(Serializer.serialize(dataSearch()))
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

        model.guestPassport    = idCountry
        model.origin           = dataTrip.originId
        model.checkInDate      = checkIn.split(" ")[0]
        model.checkOutDate     = dateCheckoutCalculation(checkIn.split(" ")[0])
        model.countGuest       = 1
        model.purpose          = dataTrip.purpose
        model.travelAgent      = Globals.getConfigCompany(this).defaultTravelAgent
        model.destination      = dataTrip.destinationId
        model.countRoom        = 1
        model.hotelName        = ""

        when (typeDestination){
            Constants.SELECT_NEARBY_CITY-> {
                model.destinationKey   = idCity
                model.latitude         = null
                model.longitude        = null
            }
            Constants.SELECT_NEARBY_OFFICE -> {
                model.destinationKey   = ""
                model.latitude         = if (latitude.isNotEmpty()) latitude.toDouble() else 0.0
                model.longitude        = if (longitude.isNotEmpty()) longitude.toDouble() else 0.0
            }
            Constants.SELECT_NEARBY_AIRPORT -> {
                model.destinationKey   = ""
                model.latitude         = if (latitude.isNotEmpty()) latitude.toDouble() else 0.0
                model.longitude        = if (longitude.isNotEmpty()) longitude.toDouble() else 0.0
            }
        }
        return Globals.classToHashMap(model,SearcHotelRequest::class.java)
    }

    private fun dateCheckoutCalculation(string: String): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        cal.time = sdf.parse(string)
        cal.add(Calendar.DATE, duration.split(" ")[0].toInt()-1)
        return sdf.format(cal.time)
    }

}