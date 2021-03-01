package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.hotel

import android.os.Bundle
import android.view.View
import android.content.Intent
import android.support.v4.content.ContextCompat
import com.opsigo.travelaja.R
import org.koin.core.KoinComponent
import kotlin.collections.ArrayList
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.unicode.kingmarket.Base.BaseFragment
import com.opsigo.travelaja.utility.DateConverter
import com.opsicorp.sliderdatepicker.utils.Constant
import kotlinx.android.synthetic.main.hotel_fragment.*
import opsigo.com.domainlayer.callback.CallbackReasonCode
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.opsigo.travelaja.utility.DialogSelectDurationHotel
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyOfficeModel
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyAirportModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

class HotelFragment : BaseFragment(),
        NewCalendarViewOpsicorp.CallbackResult,
        View.OnClickListener,
        KoinComponent,
        ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.hotel_fragment }

    var typeDestination     = -1
    var checkIn             = ""
    var checkOut            = ""
    var dataSelectCity      = CityHotelModel()
    var dataSelectCountry   = SelectNationalModel()
    var dataOffice          = NearbyOfficeModel()
    var dataAirport         = NearbyAirportModel()

    lateinit var data: SuccessCreateTripPlaneModel
    var latitude  = ""
    var longitude = ""

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        getReasonCode()
        setOnClickListener()
        setDateDefault()
        checkTypeOrder()
    }

    private fun setOnClickListener() {
        tv_departur_date.setOnClickListener(this)
        tv_duration.setOnClickListener(this)
        title_duration.setOnClickListener(this)
        btn_next.callbackOnclickButton(this)
        tv_city.setOnClickListener(this)
        ic_airport.setOnClickListener(this)

        btn_office.setOnClickListener(this)
        btn_city.setOnClickListener(this)
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
        if (Constants.DATA_SUCCESS_CREATE_TRIP.isNotEmpty()){
            data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            startDate(DateConverter().getDate(data.startDate,"yyyy-MM-dd","dd MMM yyyy"),data.startDate)
            endDate(DateConverter().getDate(data.endDate,"yyyy-MM-dd","dd MMM yyyy"),data.endDate)
            checkIn  = data.startDate
            checkOut = data.endDate
            setDurationDate(checkIn,checkOut)
            setDataCityDefault()
        }
        else{
            startDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            endDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            tv_duration.text = "1 Night(s)"
        }
    }

    private fun setDataCityDefault() {
        ic_airport.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_before_cheklist))
        typeDestination             = Constants.SELECT_NEARBY_CITY
        dataSelectCity.cityName     = "Bandung"
        dataSelectCity.idCity       = "7sdn5U3LQUaiqnGJUhXQpg"
        tv_city.text                = dataSelectCity.cityName
        tv_title_destination.text   = context?.getString(R.string.title_nearby_city)

    }

    private fun setDurationDate(startDate: String,endDate:String) {
        tv_duration.text = "${Globals.countDaysBettwenTwoDate(startDate,endDate,"yyyy-MM-dd")+1} Night(s)"
    }

    override fun onClicked() {
        if (tv_city.text.toString().isNotEmpty()){
            Globals.typeAccomodation = "Hotel"
            val bundle = Bundle()
            bundle.putString(Constants.KeyBundle.KEY_DURATION,tv_duration.text.toString())
            bundle.putString(Constants.KeyBundle.KEY_NAME_AIRPORT,dataAirport.nameAirport)
            bundle.putString(Constants.KeyBundle.KEY_NAME_OFFICE,dataOffice.nameCompany)
            bundle.putString(Constants.KeyBundle.KEY_NAME_CITY,dataSelectCity.cityName)
            bundle.putString(Constants.KeyBundle.KEY_ID_COUNTRY,dataSelectCountry.id)
            bundle.putString(Constants.KeyBundle.KEY_ID_CITY,dataSelectCity.idCity)
            bundle.putInt(Constants.KeyBundle.KEY_DESTINATION,typeDestination)
            bundle.putString(Constants.KeyBundle.KEY_LATITUDE,longitude)
            bundle.putString(Constants.KeyBundle.KEY_LONGITUDE,latitude)
            bundle.putString(Constants.KeyBundle.KEY_CHECKOUT,checkOut)
            bundle.putString(Constants.KeyBundle.KEY_CHECKIN,checkIn)

            setLog("---------------------------")
            setLog("1 -> "+tv_duration.text.toString())
            setLog("2 -> "+typeDestination.toString())
            setLog("3 -> "+dataAirport.nameAirport)
            setLog("4 -> "+dataSelectCity.cityName)
            setLog("6 -> "+dataSelectCity.idCity)
            setLog("5 -> "+dataOffice.nameCompany)
            setLog("7 -> "+longitude)
            setLog("8 -> "+dataSelectCountry.id)
            setLog("9 -> "+latitude)
            setLog("10 ->"+checkOut)
            setLog("11 ->"+checkIn)

            val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_HOTEL +"result.ResultSearchHotelActivity"))
            intent.putExtra(Constants.KEY_BUNDLE,bundle)
            gotoActivityModule(context!!,intent)
        }
        else{
            Globals.showAlert("Please","Select city",context!!)
        }
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_departur_date.text = displayStartDate
        checkIn = startDate
        setDurationDate(checkIn,checkOut)
    }

    override fun endDate(displayEndDate: String, endDate: String) {

    }

    override fun canceledCalendar() {

    }

    override fun onClick(v: View?) {
        when(v){
            tv_departur_date -> {
                NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",data.startDate,data.endDate, Constant.SINGGLE_SELECTED)
//                if (Globals.ONE_TRIP){
//                }
//                else{
//                    NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",data.startDate,data.endDate, Constant.DOUBLE_SELECTED)
//                }
            }
            tv_duration -> {
                selectDuration()
            }
            title_duration-> {
                selectDuration()
            }
            btn_office -> {
                getNearBySelected(Constants.SELECT_NEARBY_OFFICE)
            }
            btn_city -> {
                getNearBySelected(Constants.SELECT_NEARBY_COUNTRY)
            }
            tv_city -> {
                when (typeDestination){
                    Constants.SELECT_NEARBY_CITY -> {
                        getNearBySelected(Constants.SELECT_NEARBY_COUNTRY)
                    }
                    Constants.SELECT_NEARBY_AIRPORT -> {
                        getNearBySelected(Constants.SELECT_NEARBY_AIRPORT)
                    }
                    Constants.SELECT_NEARBY_OFFICE -> {
                        getNearBySelected(Constants.SELECT_NEARBY_OFFICE)
                    }
                }
            }
            ic_airport->{
                if (ic_airport.drawable.constantState==ContextCompat.getDrawable(context!!,R.drawable.ic_after_checklist)?.constantState){
                    setDataCityDefault()
                }
            }
        }
    }

    private fun getNearBySelected(selectNearbyOffice: Int) {
        val intent = Intent(
                context,
                Class.forName(Constants.BASE_PACKAGE_HOTEL +"nearby.NearbyActivity")
        )
        intent.putExtra(Constants.TYPE_SELECT_NEARBY,selectNearbyOffice)
        gotoActivityForResultModule(context!!,intent,Constants.REQUEST_CODE_NEARBY)
    }

    fun selectDuration(){
        DialogSelectDuration(context!!).create(
                Globals.countDaysBettwenTwoDate(checkIn,checkOut,"yyyy-MM-dd"),
                object : DialogSelectDurationHotel {
            override fun duration(duration: String) {
                tv_duration.text = duration+" Night(s)"
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
        when(requestCode){
            Constants.REQUEST_CODE_NEARBY -> {
                when(data?.getIntExtra(Constants.TYPE_SELECT_NEARBY,Constants.SELECT_NEARBY_CITY)){
                    Constants.SELECT_NEARBY_CITY -> {
                        setLog("chbjkj ",data.getStringExtra(Constants.KeyBundle.KEY_ID_CITY).toString())
                        dataSelectCity.idCity     = data.getStringExtra(Constants.KeyBundle.KEY_ID_CITY).toString()
                        dataSelectCity.cityName   = data.getStringExtra(Constants.KeyBundle.KEY_NAME_CITY).toString()
                        tv_country.text           = data.getStringExtra(Constants.KeyBundle.KEY_NAME_COUNTRY).toString()
                        dataSelectCountry.id      = data.getStringExtra(Constants.KeyBundle.KEY_ID_COUNTRY).toString()
                        tv_title_destination.text = context?.getString(R.string.title_nearby_city)
                        ic_address.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_location))
                        typeDestination           = Constants.SELECT_NEARBY_CITY
                        ic_airport.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_before_cheklist))
                        tv_city.text = dataSelectCity.cityName
                    }
                    Constants.SELECT_NEARBY_AIRPORT -> {
                        dataAirport.nameAirport  = data.getStringExtra(Constants.KeyBundle.KEY_NAME_AIRPORT).toString()
                        latitude  = data.getStringExtra(Constants.KeyBundle.KEY_LATITUDE).toString()
                        longitude = data.getStringExtra(Constants.KeyBundle.KEY_LONGITUDE).toString()
                        dataSelectCountry.id      = data.getStringExtra(Constants.KeyBundle.KEY_ID_COUNTRY).toString()
                        tv_title_destination.text = context?.getString(R.string.title_nearby_airport)
                        ic_address.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.flight_arrived))
                        typeDestination           = Constants.SELECT_NEARBY_AIRPORT
                        ic_airport.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_after_checklist))
                        tv_city.text = dataAirport.nameAirport
                    }
                    Constants.SELECT_NEARBY_OFFICE -> {
                        dataOffice.nameCompany  = data.getStringExtra(Constants.KeyBundle.KEY_NAME_OFFICE).toString()
                        latitude  = data.getStringExtra(Constants.KeyBundle.KEY_LATITUDE).toString()
                        longitude = data.getStringExtra(Constants.KeyBundle.KEY_LONGITUDE).toString()
                        dataSelectCountry.id      = data.getStringExtra(Constants.KeyBundle.KEY_ID_COUNTRY).toString()
                        tv_title_destination.text = context?.getString(R.string.title_nearby_office)
                        ic_address.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_tower_gray))
                        typeDestination           = Constants.SELECT_NEARBY_OFFICE
                        ic_airport.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.ic_after_checklist))
                        tv_city.text = dataOffice.nameCompany
                    }
                }
            }
        }
    }
    /*fun getLatLongWithGprs(){
        val location = object : MyLocation.LocationResult(){
            override fun gotLocation(location: Location?) {
                var latitude = location?.latitude
                var longitude = location?.longitude
            }
        }

        MyLocation().getLocation(context,location)
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
*/

}