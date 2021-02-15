package com.opsigo.travelaja.module.accomodation.hotel

import java.util.*
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import org.json.JSONArray
import java.lang.Exception
import android.app.Activity
import android.content.Intent
import com.opsigo.travelaja.R
import android.widget.TextView
import android.location.Address
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.widget.LinearLayout
import org.koin.core.KoinComponent
import kotlin.collections.ArrayList
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.unicode.kingmarket.Base.BaseFragment
import com.opsigo.travelaja.utility.DateConverter
import com.opsicorp.sliderdatepicker.utils.Constant
import kotlinx.android.synthetic.main.hotel_fragment.*
import opsigo.com.domainlayer.model.signin.CountryModel
import opsigo.com.domainlayer.callback.CallbackReasonCode
import opsigo.com.domainlayer.callback.CallbackListCompany
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackListCityHotel
import com.opsigo.travelaja.utility.DialogSelectDurationHotel
import com.opsigo.travelaja.module.item_custom.map.MyLocation
import com.opsigo.travelaja.module.item_custom.map.MapActivity
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import com.opsigo.travelaja.utility.Constants.CODE_MAP_ACTIVITY
import com.opsigo.travelaja.utility.Constants.READ_REQUEST_LOCATION
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyOfficeModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.item_custom.search_dialog.SearchDialog
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

class HotelFragment : BaseFragment(),
        NewCalendarViewOpsicorp.CallbackResult,
        View.OnClickListener,
        KoinComponent,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        SearchDialog.CallbackSelectPreferance {

    override fun getLayout(): Int { return R.layout.hotel_fragment }


    //sementara
    var lineButtons = ArrayList<LinearLayout>()
    var lineSelected = ArrayList<Int>()
    var lineDefault = ArrayList<Int>()
    var textButtons = ArrayList<TextView>()
    var typeDestination = "destinationByCity"
    var checkIn  = ""
    var checkOut = ""


    val dummyDataCreateTrip = "{\"buggetId\":\"06bbab51-be9e-4fe4-93a9-a5fc184e0242\",\"costCenter\":\"73383b32-fb63-4276-9388-c466a295bd3e\",\"createDate\":\"2020-06-02 06:02:06\",\"createDateView\":\"2 Jun 2020 13:02\",\"destination\":\"BDO\",\"destinationName\":\"BANDUNG-BDO\",\"endDate\":\"2020-06-08 00:00:00\",\"idTripPlant\":\"5b4c2b90-9b47-4f68-a7a2-3e9a5dfdfb12\",\"origin\":\"CGK\",\"originName\":\"CGK\",\"purpose\":\"Customer Meeting\",\"startDate\":\"2020-06-04 00:00:00\",\"status\":\"Draft\",\"timeExpired\":\"00:00:00\",\"tripCode\":\"TP202006020003\"}"
    var typeDialog          = ""
    var dataCountry         = ArrayList<SelectNationalModel>()
    var dataCity            = ArrayList<SelectNationalModel>()
    var dataCompany         = ArrayList<SelectNationalModel>()
    var dataSelectCity      = SelectNationalModel()
    var dataSelectCountry   = SelectNationalModel()
    var dataSelectCompany   = SelectNationalModel()
    var dataSelectMap       = SelectNationalModel()
    lateinit var data: SuccessCreateTripPlaneModel
    var latitude:Double?  = null
    var longitude:Double? = null

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        checkPermissionLocation()
        getReasonCode()
        setOnClickListener()
        setDateDefault()
        checkTypeOrder()
        initButtonTop()
        getDataListCompany()
    }

    private fun initButtonTop() {
        lineButtons.clear()
        lineDefault.clear()
        lineSelected.clear()

//        lineButtons.add(line_button_left)
//        lineButtons.add(line_button_center)
//        lineButtons.add(line_button_right)
//        textButtons.add(title_btn_left)
//        textButtons.add(title_btn_center)
//        textButtons.add(title_button_right)

        lineDefault.add(R.drawable.rounded_button_up_flight_default_left)
        lineDefault.add(R.drawable.rounded_button_up_flight_default_center)
        lineDefault.add(R.drawable.rounded_button_up_flight_default_right)
        lineSelected.add(R.drawable.rounded_button_up_flight_selected_left)
        lineSelected.add(R.drawable.rounded_button_up_flight_selected_center)
        lineSelected.add(R.drawable.rounded_button_up_flight_selected_right)

    }

    private fun changeImageBtn(position:Int) {

        lineButtons.forEachIndexed { index, lineView ->
            if (index==position){
                lineView.background = (resources.getDrawable(lineSelected[position]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.colorWhite))

                val face = Typeface.createFromAsset(context?.getAssets(),
                        "font/Roboto_Black.ttf")
                textButtons.get(index).typeface = face
            }
            else{
                lineView.background = (resources.getDrawable(lineDefault[index]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.colorGrayTextButtonFlight))
                val face = Typeface.createFromAsset(context?.getAssets(),
                        "font/Roboto_Regular.ttf")
                textButtons.get(index).typeface = face
            }
        }
    }

    private fun setOnClickListener() {
        tv_departur_date.setOnClickListener(this)
        tv_duration.setOnClickListener(this)
        title_duration.setOnClickListener(this)
        btn_next.callbackOnclickButton(this)
        tv_city.setOnClickListener(this)
//        tv_country.setOnClickListener(this)
//        btn_map.setOnClickListener(this)
//        line_button_left.setOnClickListener(this)
//        line_button_center.setOnClickListener(this)
//        line_button_right.setOnClickListener(this)
//        title_btn_left.setOnClickListener(this)
//        title_btn_center.setOnClickListener(this)
//        title_button_right.setOnClickListener(this)
    }


    private fun checkTypeOrder() {
        if (Globals.BisnisTrip){
            lay_passanger.visibility = View.GONE
        }
        else{
            lay_passanger.visibility = View.VISIBLE
        }
    }

    private fun setDateDefault() {
        //dummy
//        Constants.DATA_SUCCESS_CREATE_TRIP = dummyDataCreateTrip

        if (Constants.DATA_SUCCESS_CREATE_TRIP.isNotEmpty()){
            data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            startDate(DateConverter().getDate(data.startDate,"yyyy-MM-dd","dd MMM yyyy"),data.startDate)
            endDate(DateConverter().getDate(data.endDate,"yyyy-MM-dd","dd MMM yyyy"),data.endDate)
            checkIn  = data.startDate
            checkOut = data.endDate
            setDurationDate(checkIn,checkOut)
        }
        else{
            startDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            endDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            tv_duration.text = "1 Nigh"

        }

        setDataCountry()
    }

    private fun setDurationDate(startDate: String,endDate:String) {
        tv_duration.text = "${Globals.countDaysBettwenTwoDate(startDate,endDate,"yyyy-MM-dd")} Nigh"
    }

    override fun onClicked() {
        if (dataSelectCity.name.isNotEmpty()){
            Globals.typeAccomodation = "Hotel"
            /*Constants.Country        = dataSelectCountry
            Constants.City           = dataSelectCity
            Constants.Company        = dataSelectCompany
            Constants.typeDestination = typeDestination
            Constants.TotalNight     = tv_duration.text.toString()
            Constants.CheckInDate    = checkIn
            Constants.CheckOutDate   = checkOut
            Constants.Latitude       = latitude
            Constants.Longitude      = longitude*/
            gotoActivityModule(context!!,Constants.BASE_PACKAGE_HOTEL +"ResultSearchHotelActivity")

            //            gotoActivity(com.khoiron.hotel_feature.ResultSearchHotelActivity::class.java)
        }
        else{
            Globals.showAlert("Please","Select city",context!!)
        }

    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_departur_date.text = displayStartDate
        checkIn = startDate
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        checkOut = endDate
        setDurationDate(checkIn,checkOut)
    }

    override fun canceledCalendar() {

    }

    override fun onClick(v: View?) {
        when(v){
            tv_departur_date -> {
                if (Globals.ONE_TRIP){
                    NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",data.startDate,data.endDate, Constant.SINGGLE_SELECTED)
                }
                else{
                    NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",data.startDate,data.endDate, Constant.DOUBLE_SELECTED)
                }
            }
            tv_duration -> {
                selectDuration()
            }
            title_duration-> {
                selectDuration()
            }
            tv_country -> {
                when(typeDestination) {
                    "destinationByCity" -> {
                        typeDialog = "country"
                        val dialogSeacrh = SearchDialog(true,R.style.CustomDialog, dataCountry)
                        dialogSeacrh.show(fragmentManager,"dialog")
                        dialogSeacrh.setCallbackListener(this)
                    }
                    "destinationByCompany" -> {
                        typeDialog = "company"
                        val dialogSeacrh = SearchDialog(true,R.style.CustomDialog, dataCompany)
                        dialogSeacrh.show(fragmentManager,"dialog")
                        dialogSeacrh.setCallbackListener(this)
                    }
                    "destinationByMap" -> {
                        if (latitude!=null){
                            gotoActivityForResult(MapActivity::class.java,CODE_MAP_ACTIVITY)
                        }
                    }
                }
            }
            tv_city -> {
                when(typeDestination){
                    "destinationByCity" -> {
                        typeDialog = "city"
                        val dialogSeacrh = SearchDialog(true,R.style.CustomDialog, dataCity)
                        dialogSeacrh.show(fragmentManager,"dialog")
                        dialogSeacrh.setCallbackListener(this)
                    }
                    "destinationByMap" -> {
                        if (latitude!=null){
                            gotoActivityForResult(MapActivity::class.java,CODE_MAP_ACTIVITY)
                        }
                    }
                }
            }

//            btn_map -> {
//                typeDestination = "destinationByMap"
//                changeUiMapListener()
//            }
//
//            line_button_left -> {
//                btnCityListener()
//            }
//            line_button_center -> {
//                typeDestination = "destinationByCompany"
//                btnByCompanyListener()
//            }
//            line_button_right -> {
//                typeDestination = "destinationByMap"
//                changeUiMapListener()
//            }
//            title_btn_left -> {
//                btnCityListener()
//            }
//            title_btn_center -> {
//                typeDestination = "destinationByCompany"
//                btnByCompanyListener()
//            }
//            title_button_right -> {
//                typeDestination = "destinationByMap"
//                changeUiMapListener()
//            }

        }
    }

    private fun changeUiMapListener() {
        changeImageBtn(2)
        line_city.visibility = View.VISIBLE
//        tv_title_country.text = "Country"
//        tv_title_city.text    = "Location"
        tv_city.text          = dataSelectMap.name
    }

    private fun btnByCompanyListener() {
//        tv_title_country.text = "Company"
        line_city.visibility = View.GONE
        changeImageBtn(1)
    }


    private fun btnCityListener() {
        typeDestination = "destinationByCity"
        line_city.visibility  = View.VISIBLE
//        tv_title_country.text = "Country"
//        tv_title_city.text    = "City"
        tv_city.text          = dataSelectCity.name
        changeImageBtn(0)
    }

    private fun setDataCountry() {
        dataCountry.clear()
        if (Constants.COUNTRY_HOTEL.isNotEmpty()){
            val listdata = JSONArray(Globals.getDataPreferenceString(context!!,Constants.COUNTRY_HOTEL))
            for (i in 0 until listdata.length()){
                val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
                val model = SelectNationalModel()
                model.name      = mData.name
                model.id        = mData.id
                model.callCode  = mData.callCode
                dataCountry.add(model)
            }

            if (dataCountry.isNotEmpty()){
                dataSelectCountry = dataCountry.filter { it.name.contains("Indonesia") }.first()
//                tv_country.text = dataSelectCountry.name
                getDataCity(dataSelectCountry)
            }
        }
    }



    override fun callback(model: SelectNationalModel) {
        when(typeDialog){
            "country" -> {
                dataSelectCountry = model
//                tv_country.text = model.name
                getDataCity(model)
            }
            "city" -> {
                dataSelectCity    = model
                tv_city.text      = model.name
            }

            "company" -> {
//                tv_country.text   = model.name
                dataSelectCompany = model
                try {
                    latitude = model.latitude.toDouble()
                    longitude = model.longitude.toDouble()
                }catch (e:Exception){

                }

            }
        }
    }

    private fun getDataCity(model: SelectNationalModel) {
        GetDataAccomodation(getBaseUrl()).getSearchCity(getToken(),model.id,Globals.getConfigCompany(context!!).defaultTravelAgent,object :CallbackListCityHotel{
            override fun successLoad(data: ArrayList<CityHotelModel>) {
                if (data.isNotEmpty()){
                    if (data.filter { it.cityName.toLowerCase().contains(this@HotelFragment.data.destinationName.toLowerCase()) }.isNotEmpty()){
                        val dataSelectCity = data.filter { it.cityName.toLowerCase().contains(this@HotelFragment.data.destinationName.toLowerCase()) }.first()
                        tv_city.text   = dataSelectCity.cityName
                    }
                    else{
                        var dataSelectCity = data[0]
                        tv_city.text   = dataSelectCity.cityName
                    }
                }
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    fun selectDuration(){
        DialogSelectDuration(context!!).create(6,object : DialogSelectDurationHotel {
            override fun duration(duration: String) {
                tv_duration.text = duration+" night(s)"
            }
        })
    }

    fun getReasonCode(){
        GetDataAccomodation(getBaseUrl()).getReasonCodeTrain(getToken(),Constants.TripType.KAI.toString(),object : CallbackReasonCode {
            override fun success(reasonCodeModel: ArrayList<ReasonCodeModel>) {
                Constants.DATA_REASON_CODE_HOTEL = reasonCodeModel
            }

            override fun failed(string: String) {

            }
        })
    }

    fun getLatLongWithGprs(){
        val location = object : MyLocation.LocationResult(){
            override fun gotLocation(location: Location?) {
                latitude = location?.latitude
                longitude = location?.longitude
                setLog(latitude.toString())
                setLog(longitude.toString())
            }
        }

        MyLocation().getLocation(context,location)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_MAP_ACTIVITY){
            if (resultCode == Activity.RESULT_OK){
                val lat  = data?.getDoubleExtra("lat",0.0)
                val long = data?.getDoubleExtra("long",0.0)
                setLatAndLong(lat,long)
            }
        }
        else {
            NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
        }
    }

    private fun setLatAndLong(lat: Double?, long: Double?) {
        latitude = lat
        longitude = long

        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = gcd.getFromLocation(lat!!, long!!, 1)
        if (addresses.size > 0) {
//            tv_country.text = addresses[0].countryName
            tv_city.text    = addresses[0].locality
        }
        dataSelectMap.name  = addresses[0].locality
        dataSelectMap.latitude  = addresses[0].latitude.toString()
        dataSelectMap.longitude = addresses[0].longitude.toString()
    }

    fun checkPermissionLocation(){
        val activity = (context as Activity)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== android.content.pm.PackageManager.PERMISSION_GRANTED &&
                    activity.checkSelfPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)!== android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),
                        READ_REQUEST_LOCATION)
            }
            else {
                getLatLongWithGprs()
            }
        }
        else{
            getLatLongWithGprs()
        }
    }


    fun getDataListCompany(){
        GetDataAccomodation(getBaseUrl()).getListCompanyHotel(getToken(),true,object :CallbackListCompany{
            override fun success(data: ArrayList<NearbyOfficeModel>) {
                setLog("------------------------------->>???")
                setLog(Serializer.serialize(data))
                data.forEach {
                    val model = SelectNationalModel()
                    model.name     = it.nameCompany
                    model.latitude = it.latitude
                    model.longitude = it.longitude
                    dataCompany.add(model)
                }
            }

            override fun failed(message: String) {
                Globals.showAlert("Sorry",message,context!!)
            }
        })
    }

//    var data    = ArrayList<TourEventModel>()
//    val adapterTourEventAdapter by lazy { TourEventAdapter(context!!,data) }
    /*private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_tour.layoutManager = layoutManager
        rv_tour.adapter = adapterTourEventAdapter

        adapterTourEventAdapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    1 ->{

                    }
                }
            }
        })
    }
*/
    /*fun getDataTourEvent(){
        //dummy getDataLogin
        data.clear()
        var mData = TourEventModel()
        mData.imageTour = "https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/BA169E02-A16E-433C-9363-C98695481160.png"
        mData.country   = "Indonesia"
        mData.dateTour  = "on April 2019"
        mData.idEvent   = "1"
        mData.pricing   = "Rp 200.000"

        data.add(mData)
        adapterTourEventAdapter.setData(data)
    }*/
}