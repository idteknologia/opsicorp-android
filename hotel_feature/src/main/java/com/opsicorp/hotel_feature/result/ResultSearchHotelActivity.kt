package com.opsicorp.hotel_feature.result

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import com.mobile.travelaja.module.item_custom.btn_filter.FilterOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.menu_sort.BottomSheetSort
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerViewAnimation
import com.opsicorp.hotel_feature.R
import com.opsicorp.hotel_feature.detail_hotel.DetailHotelActivity
import kotlinx.android.synthetic.main.detail_search_hotel_activity.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.accomodation.hotel.search.PageHotelRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.search.SearcHotelRequest
import opsigo.com.domainlayer.callback.CallbackSearchHotel
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ResultSearchHotelActivity : BaseActivity(),
        CalendarViewOpsicorp.CallbackResult,KoinComponent,
        FilterOpsicorp.OnclickFilterListener,
        ToolbarOpsicorp.OnclickButtonListener,
        BottomSheetSort.BottomSheetListener ,
        HotelShortByDialog.CallbackDialog,
        OnclickListenerRecyclerViewAnimation {

    override fun getLayout(): Int { return R.layout.detail_search_hotel_activity }

    val adapter by inject<ResultAccomodationAdapter> { parametersOf() }
    var loadingSearch     = false
    var current_sort      = 0
    var data              = ArrayList<AccomodationResultModel>()
    var dataFilter        = ArrayList<AccomodationResultModel>()
    val dataArea          = ArrayList<String>()
    var star              = ArrayList<String>()
    var minPrice          = ""
    var maxPrice          = ""
    var facilitys         = ArrayList<String>()
    var area              = ""
    var correlationId     = ""
    var maxPage           = 5
    var scrolPage         = 1

    lateinit var dataTrip: SuccessCreateTripPlaneModel

    var typeDestination  = 0
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
    var totalGuest       = 1
    var filterActif      = false

    override fun OnMain() {
        initItemViews()
        initSearch()
    }

    private fun initSearch() {
        et_filter.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterActif = p0.toString().length>0
                try {
                    if (p0.toString().length>0){
                        dataFilter.clear()
                        dataFilter.addAll(data.filter { it.listHotelModel.nameHotel.toLowerCase().contains(p0.toString().toLowerCase()) })
                        adapter.setDataList(dataFilter,this@ResultSearchHotelActivity)
                    }
                    else {
                        adapter.setDataList(data,this@ResultSearchHotelActivity)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        imgCloseDest.setOnClickListener { et_filter.setText("") }
    }

    private fun initItemViews() {
        filter.callbackOnclickFilter(this)
        filter.setTextButtonChangeDate("Pick Area")
        getDataIntent()
        setToolbar(checkIn)
        setRecyclerView()
    }

    private fun getDataIntent() {
        try {
            typeDestination  = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.KeyBundle.KEY_DESTINATION,0)!!
            totalGuest       = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.KeyBundle.KEY_TOTAL_GUEST,0)!!
            latitude         = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_LATITUDE,"").toString()
            longitude        = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_LONGITUDE,"").toString()
            idCountry        = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_ID_COUNTRY,"").toString()
            duration         = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_DURATION,"").toString()
            idCity           = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_ID_CITY,"").toString()
            checkIn          = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_CHECKIN,"").toString()
            checkOut         = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_CHECKOUT,"").toString()
            nameCity         = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_NAME_CITY,"").toString()
            nameOffice       = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_NAME_OFFICE,"").toString()
            nameAirport      = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KeyBundle.KEY_NAME_AIRPORT,"").toString()
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

    private fun addDataLoading(isLoadingPage:Boolean){
        tv_total_data.visibility = View.GONE
        loadingSearch = true
        empty_result.visibility = View.GONE
        if (isLoadingPage){
            dataFilter.addAll(DataDummyAccomodation().addDataLoadingHotel())
        }
        else {
            data.addAll(DataDummyAccomodation().addDataLoadingHotel())
        }
        adapter.notifyDataSetChanged()
    }

    private fun checkEmptyData(mData: ArrayList<AccomodationResultModel>) {
        if (mData.isEmpty()){
            empty_result.visibility = View.VISIBLE
            tv_total_data.visibility = View.GONE
            rv_result_hotel.visibility = View.GONE
        }
        else {
            showTotalData()
            empty_result.visibility = View.GONE
            rv_result_hotel.visibility = View.VISIBLE
        }
    }

    private fun showTotalData() {
        Globals.delay(1000.toLong(),object :Globals.DelayCallback{
            override fun done() {
                tv_total_data.text = "${data.size} Best departing Hotel"
                val transition: Transition = Fade()
                transition.setDuration(700)
                transition.addTarget(R.id.tv_total_data)
                TransitionManager.beginDelayedTransition(parent_layout, transition)
                tv_total_data.setVisibility(View.VISIBLE)
            }
        })
    }

    private fun setRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_result_hotel.layoutManager = layoutManager
        rv_result_hotel.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_result_hotel.adapter = adapter
        adapter.setOnclickListenerWithAnimation(this)
        adapter.setDataList(data,this)

        rv_result_hotel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!loadingSearch){
                        if (scrolPage<maxPage){
                            scrolPage = scrolPage+1
                            getSearchPageHotel(scrolPage)
                        }
                    }

                }
            }
        })

        getDataHotel()
    }

    override fun onFilter() {
        if (data.isNotEmpty()&&!loadingSearch){
            val minPrice = getPriceMinMax().first
            val maxPrice = getPriceMinMax().second
            val bundle = Bundle()
            bundle.putInt(Constants.MIN_PRICE,minPrice)
            bundle.putInt(Constants.MAX_PRICE,maxPrice)
            gotoActivityResultWithBundle(FilterPriceActivity::class.java,bundle,Constants.REQUEST_CODE_HOTEL_FILTER)
        }
    }

    private fun getPriceMinMax(): Pair<Int,Int> {
        val sortingData: ArrayList<AccomodationResultModel>
        if (filterActif) sortingData = dataFilter else sortingData = data
        sortingData.sortBy { it.listHotelModel.price.toInt() }
        val min = sortingData.first().listHotelModel.price.toInt()
        val max = sortingData.last().listHotelModel.price.toInt()
        return Pair(min,max)
    }

    override fun onSort() {
        if (data.isNotEmpty()&&!loadingSearch){
            HotelShortByDialog(this).create(current_sort,this)
        }
    }

    override fun onOptionSortClick(text: String, sort: Int) {
        current_sort = sort
    }

    override fun onChangeDate() {
        if (dataArea.isNotEmpty()&&!loadingSearch){
            val bundle = Bundle()
            bundle.putStringArrayList(Constants.KEY_DATA_AREA,dataArea)
            gotoActivityResultWithBundle(FilterByAreaActivity::class.java,bundle,Constants.REQUEST_CODE_HOTEL_AREA)
        }
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
            Constants.REQUEST_CODE_HOTEL_AREA -> {
                if (resultCode==Activity.RESULT_OK){
                    filterActif = true
                    minPrice  = ""
                    maxPrice  = ""
                    star      = ArrayList()
                    area      = data?.getStringExtra(Constants.RESULT_AREA_HOTEL).toString()
                    getSearchPageHotel(1)
                }
            }
            Constants.REQUEST_CODE_HOTEL_FILTER -> {
                if (resultCode==Activity.RESULT_OK){
                    filterActif = true
                    minPrice  = data?.getIntExtra(Constants.MIN_PRICE,0).toString()
                    maxPrice  = data?.getIntExtra(Constants.MAX_PRICE,0).toString()
                    facilitys = data?.getStringArrayListExtra(Constants.RESULT_FACILITY)!!
                    star      = data.getStringArrayListExtra(Constants.RESULT_STAR)!!
                    area      = ""
                    getSearchPageHotel(1)
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
                if (filterActif){
                    dataFilter[position].listHotelModel.idCity     = idCity
                    dataFilter[position].listHotelModel.idCountry  = idCountry
                    dataFilter[position].listHotelModel.checkIn    = checkIn.split(" ")[0]
                    dataFilter[position].listHotelModel.checkOut   = dateCheckoutCalculation(checkIn.split(" ")[0])
                    dataFilter[position].listHotelModel.duration   = duration
                    dataFilter[position].listHotelModel.totalGuest = totalGuest
                    Constants.DATA_HOTEL                           = Serializer.serialize(dataFilter.get(position).listHotelModel, ResultListHotelModel::class.java)
                }else {
                    data[position].listHotelModel.idCountry        = idCountry
                    data[position].listHotelModel.checkIn          = checkIn.split(" ")[0]
                    data[position].listHotelModel.duration         = duration
                    data[position].listHotelModel.totalGuest       = totalGuest
                    data[position].listHotelModel.checkOut         = dateCheckoutCalculation(checkIn.split(" ")[0])
                    Constants.DATA_HOTEL                           = Serializer.serialize(data.get(position).listHotelModel, ResultListHotelModel::class.java)
                }
                gotoActivityUsingTransition(viewAnim, DetailHotelActivity::class.java)
            }
        }
    }

    fun getDataHotel(){
        addDataLoading(false)
        setLog(Serializer.serialize(dataSearch()))
        GetDataAccomodation(getBaseUrl()).getSearchHotel(getToken(),dataSearch(),object : CallbackSearchHotel {
            override fun success(mData: ArrayList<AccomodationResultModel>,areas:ArrayList<String>,maximalPage:Int) {
                maxPage = maximalPage
                loadingSearch = false
                data.clear()
                dataArea.clear()
                data.addAll(mData)
                dataArea.addAll(areas)
                adapter.setDataList(data,this@ResultSearchHotelActivity)
                if (mData.isNotEmpty()){
                    correlationId = mData[0].listHotelModel.correlationId
                }
                checkEmptyData(data)
            }

            override fun failed(errorMessage: String) {
                loadingSearch = false
                data.clear()
                adapter.notifyDataSetChanged()
                checkEmptyData(data)
            }
        })
    }

    private fun dataSearch(): HashMap<Any, Any> {
        dataTrip = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
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
        model.adult            = totalGuest

        when (typeDestination){
            Constants.SELECT_NEARBY_CITY-> {
                model.destinationKey   = idCity
                model.destinationKey   = idCity
                model.latitude         = null
                model.longitude        = null
            }
            Constants.SELECT_NEARBY_OFFICE -> {
                model.destinationKey   = null
                model.latitude         = if (latitude.isNotEmpty()) latitude.toDouble() else 0.0
                model.longitude        = if (longitude.isNotEmpty()) longitude.toDouble() else 0.0
            }
            Constants.SELECT_NEARBY_AIRPORT -> {
                model.destinationKey   = null
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
        cal.add(Calendar.DATE, duration.split(" ")[0].toInt())
        return sdf.format(cal.time)
    }

    override fun selected(int: Int) {
        current_sort = int
        when(int){
            0 -> {
                sortLowestPrice()
            }
            1 -> {
                sortHighetRating()
            }
            2 -> {
                sortHSSESertified()
            }
            3 -> {
                sortHighestPrice()
            }
        }
    }

    private fun sortHSSESertified() {

    }

    private fun sortHighetRating() {
        if(filterActif){
            dataFilter.sortBy { it.listHotelModel.starRating.toDouble() }
            dataFilter.reverse()
        }
        else {
            data.sortBy { it.listHotelModel.starRating.toDouble() }
            data.reverse()
        }
    }

    private fun sortHighestPrice() {
        if(filterActif){
            dataFilter.sortBy { it.listHotelModel.price.toInt() }
            dataFilter.reverse()
        }
        else {
            data.sortBy { it.listHotelModel.price.toInt() }
            data.reverse()
        }
        adapter.notifyDataSetChanged()
    }

    private fun sortLowestPrice() {
        if(filterActif){
            dataFilter.sortBy { it.listHotelModel.price.toInt() }
        }
        else {
            data.sortBy { it.listHotelModel.price.toInt() }
        }
        adapter.notifyDataSetChanged()
    }

    fun getSearchPageHotel(page:Int){
        if (page==2) {
            dataFilter.addAll(data)
            adapter.setDataList(dataFilter,this)
        }
        addDataLoading(true)
        GetDataAccomodation(getBaseUrl()).getSearchPageHotel(getToken(),dataFilterPage(page),object :CallbackSearchHotel{
            override fun success(mData: ArrayList<AccomodationResultModel>, areas: ArrayList<String>,maxPage:Int) {
                loadingSearch = false
                if (page==1){
                    /*this if for filter area dll*/
                    dataFilter.clear()
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        data.removeIf { it.typeLayout==8 }
                        dataFilter.removeIf { it.typeLayout==8 }
                    }
                    else {
                        data.removeAll(DataDummyAccomodation().addDataLoadingHotel())
                        dataFilter.removeAll(DataDummyAccomodation().addDataLoadingHotel())
                    }
                }
                data.addAll(mData)
                dataFilter.addAll(mData)
                adapter.notifyDataSetChanged()
                checkEmptyData(mData)
            }

            override fun failed(errorMessage: String) {
                adapter.setDataList(ArrayList(),this@ResultSearchHotelActivity)
                checkEmptyData(ArrayList())
            }
        })
    }

    private fun dataFilterPage(page: Int): HashMap<Any, Any> {
        val data = PageHotelRequest()
        data.page       = page
        data.hotelName  = ""
        if (page==1){
            data.star       = if (star.isNotEmpty()) star.first() else ""
            data.minPrice   = if (minPrice!="0") minPrice else null
            data.maxPrice   = if (maxPrice!="0") maxPrice else null
            data.area       = area
        }
        data.orderBy     = ""//price_asc
        data.correlationId = correlationId
        data.travelAgent = Globals.getConfigCompany(this).defaultTravelAgent
        data.origin      = dataTrip.originId
        data.destination = dataTrip.destinationId
        return Globals.classToHashMap(data,PageHotelRequest::class.java)
    }

    override fun onBackPressed() {
        if (filterActif){
            filterActif = false
            checkEmptyData(data)
            adapter.setDataList(data,this)
        }
        else {
            finish()
        }
    }

}