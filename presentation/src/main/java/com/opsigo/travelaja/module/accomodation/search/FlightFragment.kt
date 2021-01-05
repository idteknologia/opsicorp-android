package com.opsigo.travelaja.module.accomodation.search

import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.SelectAccomodationPreferance
import com.opsigo.travelaja.module.accomodation.booking_dialog.accomodation_preferance.AccomodationPreferanceModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import com.opsigo.travelaja.module.login.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.select_passager.SelectAgePassager
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.opsigo.travelaja.module.item_custom.button_swicth.ButtonSwicth
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackReasonCode
import kotlinx.android.synthetic.main.flight_fragment.*
import opsigo.com.domainlayer.model.signin.CountryModel
import com.opsigo.travelaja.base.InitApplications
import com.unicode.kingmarket.Base.BaseFragment
import opsigo.com.datalayer.mapper.Serializer
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.TextView
import android.content.Context
import android.view.ViewGroup
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import org.json.JSONArray
import android.view.View
import android.os.Bundle
import android.util.Log
import com.khoiron.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.utility.*

class FlightFragment : BaseFragment(),
        View.OnClickListener,
        NewCalendarViewOpsicorp.CallbackResult
        ,ButtonTopRoundedOpsicorp.OnclickButtonListener,
        ButtonSwicth.OnclickButtonSwitch,
        ButtonDefaultOpsicorp.OnclickButtonListener
        ,SelectAgePassager.CallbackSelectPasanger{

    override fun getLayout(): Int { return R.layout.flight_fragment }

    var namesAirlines  = ArrayList<String>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()

    var SELECT_CODE_COUNTRY_FROM = 28
    var SELECT_CODE_COUNTRY_TO = 26

    var typeTrip = ""
    var totalAdult : Int = 0
    var totalInfant: Int = 0
    var totalChild : Int = 0

    var idClassAirline = "1"
    var nameClassAirline = ""

    var startDate     = ""
    var endDate       = ""

    var idDestination   = ""
    var idOrigin        = ""
    var originName      = ""
    var destinationName = ""
    var dataTripPlan = SuccessCreateTripPlaneModel()

    val BASE_PACKAGE_MODULE = "com.opsicorp.travelaja.feature_flight.result."


    override fun onMain(fragment: View, savedInstanceState: Bundle?) {

        dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP,SuccessCreateTripPlaneModel::class.java)

        checkTypeOrder()
        setOnClickListener()
        setDateDefault()
        setDefaultOriginDestination()
        getReasonCode()
    }

    private fun setDateDefault() {
        Globals.ALL_READY_SELECT_DEPARTING = false

        if (Constants.DATA_SUCCESS_CREATE_TRIP.isNotEmpty()){
            val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            startDate(DateConverter().getDate(data.startDate,"yyyy-MM-dd","dd MMM yyyy"),data.startDate)
            endDate(DateConverter().getDate(data.endDate,"yyyy-MM-dd","dd MMM yyyy"),data.endDate)
        }
        else{
            startDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            endDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
        }
    }

    private fun checkTypeOrder() {

        if (Globals.BisnisTrip){
            lay_parent_passager.visibility = View.GONE
            lay_air_class.gone()
            lay_air_pref.gone()
        }
        else{
            lay_parent_passager.visibility = View.VISIBLE
            lay_air_class.visible()
            lay_air_pref.visible()
        }

    }

    override fun onResume() {
        super.onResume()

        Globals.ALL_READY_SELECT_DEPARTING = false
        Log.d("xhere","oke " )

    }

    private fun setOnClickListener() {
        top_button.callbackOnclickToolbar(this)
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("Search Flight")
        btn_switch.callbackOnclickButtonSwicht(this)
        btn_switch.setItemSwicth(tv_from,tv_to)
        tv_departur_date.setOnClickListener(this)
        tv_end_date.setOnClickListener(this)

        tv_airline_prreferance.setOnClickListener(this)
        tv_titile_airline_prreferance.setOnClickListener(this)

        tv_titile_airline_class.setOnClickListener(this)
        tv_airline_class.setOnClickListener(this)

        tv_from.setOnClickListener(this)
        tv_to.setOnClickListener(this)

        tv_passanger.setOnClickListener {
            val fm = activity?.getSupportFragmentManager()
            var selectPassager = SelectAgePassager(true,R.style.CustomDialog)
            selectPassager.show(fm, "yesNoAlert")
            selectPassager.callback = this
            selectPassager.setLimitSelect(4,3,2)
        }
    }

    override fun btnLeft() {
        Globals.ONE_TRIP = false
        typeTrip  = "round_trip"
        lay_return_date.visibility = View.VISIBLE
    }

    override fun btnRight() {
        Globals.ONE_TRIP = true
        typeTrip  = "one_trip"
        lay_return_date.visibility = View.GONE
    }

    override fun onSwitch() {

    }

    override fun onClicked() {
        val dataOrder = OrderAccomodationModel()
        dataOrder.typeTrip            = typeTrip

        dataOrder.idOrigin            = idOrigin
        dataOrder.idDestination       = idDestination

        dataOrder.originName        = originName
        dataOrder.destinationName   = destinationName

        dataOrder.dateDeparture     = startDate
        dataOrder.dateArrival       = endDate
        dataOrder.classFlightCode   = idClassAirline
        dataOrder.classFlightName   = nameClassAirline

        dataOrder.totalPassagerString = tv_passanger.text.toString()
        dataOrder.airlinePreferance   = tv_airline_prreferance.text.toString()
        dataOrder.totalPassangerInt   = "${totalAdult},${totalChild},${totalInfant}"
        Globals.typeAccomodation      = "Flight"
        Globals.DATA_ORDER_FLIGHT     = Serializer.serialize(dataOrder,OrderAccomodationModel::class.java)
        Globals.DATA_LIST_FLIGHT      = ""

//        setLog(Serializer.serialize(dataOrder))
        gotoActivityModule(context!!,BASE_PACKAGE_MODULE +"ResultSearchFlightActivity")
    }

    override fun onClick(v: View?) {
        when(v){
            tv_departur_date -> {
                //openCalendarView()
                openCalendar()
            }
            tv_end_date -> {
                //openCalendarView()
                openCalendar()
            }

            tv_airline_prreferance ->{
                airlinesPreferance()
            }

            tv_titile_airline_prreferance ->{
                airlinesPreferance()
            }

            tv_titile_airline_class -> {
                airlinesClass()
            }

            tv_airline_class -> {
                airlinesClass()
            }

            tv_from ->{
                selectCityFrom()
            }

            tv_to ->{
                selectCityTo()
            }
        }
    }

    private fun openCalendar() {
        if (Globals.ONE_TRIP){
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",dataTripPlan.startDate,dataTripPlan.endDate,Constant.SINGGLE_SELECTED)
        }
        else{
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",dataTripPlan.startDate,dataTripPlan.endDate,Constant.DOUBLE_SELECTED)
        }
    }

    private fun airlinesClass() {
        showPopUpRemove(tv_airline_class)
    }

    private fun showPopUpRemove(option: TextView) {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_class_airline, null)
        val btnEconomy = layout.findViewById(R.id.btn_economy_class) as TextView
        val btnBisnis = layout.findViewById(R.id.btn_bisnis_class) as TextView
        val btnFirst = layout.findViewById(R.id.btn_first_class) as TextView

        val popupWindow = PopupWindow(
                layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)


        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.setOutsideTouchable(true)
        popupWindow.setOnDismissListener(object : PopupWindow.OnDismissListener{
            override fun onDismiss() {

            }
        })

        btnFirst.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "1"
            nameClassAirline = "First"
        }

        btnEconomy.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "3"
            nameClassAirline = "Economy"
        }

        btnBisnis.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "2"
            nameClassAirline = "Business"
        }

        popupWindow.showAsDropDown(option)
    }

    private fun selectCityTo() {
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("searchHint","Enter city or airport name")
        bundle.putString("titleHeader","Popular Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY_TO)
    }

    private fun selectCityFrom() {
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("searchHint","Enter city or airport name")
        bundle.putString("titleHeader","Popular Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY_FROM)

    }

    private fun setDefaultOriginDestination(){

        val listdata = JSONArray(Serializer.serialize(Constants.DATA_CITY))

        val def_origin = Globals.getConfigCompany(InitApplications.appContext).defaultOrigin
        val def_destinationn = Globals.getConfigCompany(InitApplications.appContext).defaultDestination

        for (i in 0 until listdata.length()){
            val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
            val model = SelectNationalModel()
            model.name = mData.name
            model.id   = mData.id

            setLog(model.name)
            setLog(model.id)

            if(model.name.toLowerCase().contains(def_origin.toLowerCase())){
                idOrigin    = model.id
                originName  = model.name
                tv_from.text = originName
                break
            }
        }

        var isFoundDestination = false
        for (i in 0 until listdata.length()){
            val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
            val model = SelectNationalModel()
            model.name = mData.name
            model.id   = mData.id

            if(model.name.equals(dataTripPlan.destinationName)){
                idDestination    = model.id
                destinationName  = model.name
                tv_to.text = destinationName

                isFoundDestination = true;

                Log.d("xfligx","desti trip : " + dataTripPlan.destinationId);
                break
            }
        }
        if(!isFoundDestination) {

            for (i in 0 until listdata.length()) {
                val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
                val model = SelectNationalModel()
                model.name = mData.name
                model.id = mData.id

                if (model.id.equals(def_destinationn)) {
                    idDestination = model.id
                    destinationName = model.name
                    tv_to.text = destinationName
                    break
                }
            }
        }
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_departur_date.text   = displayStartDate

        if(startDate.length > 10) {
            val sDate   = startDate.substring(0, 10)
            this.startDate = sDate
        }else {
            this.startDate = startDate
        }

    }

    override fun endDate(displayEndDate: String, endDate: String) {
        tv_end_date.text    = displayEndDate

        if(endDate.length > 10 ) {
            val sDate = endDate.substring(0, 10)
            this.endDate = sDate
        }else{
            this.endDate = endDate
        }
    }

    override fun canceledCalendar() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

        when(requestCode){
            SELECT_CODE_COUNTRY_FROM -> {
                if (resultCode== Activity.RESULT_OK){
                    tv_from.text = data?.getStringExtra("nameCountry")
                    originName  = data?.getStringExtra("nameCountry").toString()
                    idOrigin    = data?.getStringExtra("idCountry").toString()
                }
                else {

                }
            }
            SELECT_CODE_COUNTRY_TO -> {
                if (resultCode==Activity.RESULT_OK){
                    tv_to.text = data?.getStringExtra("nameCountry")
                    destinationName = data?.getStringExtra("nameCountry").toString()
                    idDestination   = data?.getStringExtra("idCountry").toString()
                }
                else {

                }
            }
        }
    }


    fun airlinesPreferance(){
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(fragmentManager,"dialog")

        selectAccomodationPreferance.setCallbackListener(object :SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String) {
                tv_airline_prreferance.text = string
            }
        })
    }

    private fun addNamesAirline() {
        namesAirlines.clear()
        namesAirlines.add("Select All")
        val dataJson = JSONArray(Globals.readJsonFromFile(context!!,Constants.FILE_NAME_ALL_CODE_AIRPORT))
        for (i in 0 until dataJson.length()){
            namesAirlines.add(dataJson.getJSONObject(i).getString("nameAirline"))
        }

        /*namesAirlines.add("Air France")
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
        namesAirlines.add("Turkey Airlines")*/
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

    override fun total(mTotalInfant: Int, mTotalChild: Int, mTotalAdult: Int) {
        if (mTotalAdult>0&&mTotalInfant>0&&mTotalChild>0){
            tv_passanger.setText("${mTotalAdult} Adults ${mTotalChild} Child ${mTotalInfant} Infant")
        }
        else if(mTotalAdult>0&&mTotalInfant>0&&mTotalChild==0){
            tv_passanger.setText("${mTotalAdult} Adults ${mTotalInfant} Infant")
        }
        else if(mTotalAdult>0&&mTotalInfant==0&&mTotalChild>0){
            tv_passanger.setText("${mTotalAdult} Adults ${mTotalChild} Child")
        }
        else if(mTotalAdult>0&&mTotalInfant==0&&mTotalChild==0){
            tv_passanger.setText("${mTotalAdult} Adults ")
        }

        totalAdult = mTotalAdult
        totalInfant = mTotalInfant
        totalChild = mTotalChild
    }

    fun getReasonCode(){
        GetDataAccomodation(getBaseUrl()).getReasonCodeTrain(getToken(),Constants.TripType.Airline.toString(),object : CallbackReasonCode {
            override fun success(reasonCodeModel: ArrayList<ReasonCodeModel>) {
                Constants.DATA_REASON_CODE_FLIGHT = reasonCodeModel
            }

            override fun failed(string: String) {

            }
        })
    }

}