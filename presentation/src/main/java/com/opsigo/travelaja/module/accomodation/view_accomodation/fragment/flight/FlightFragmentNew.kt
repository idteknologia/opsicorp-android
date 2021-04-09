package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.BaseFragment
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.module.accomodation.dialog.accomodation_preferance.AccomodationPreferanceModel
import com.opsigo.travelaja.module.accomodation.dialog.accomodation_preferance.SelectAccomodationPreferance
import com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight.adapter.FlightMultiAdapter
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_swicth.ButtonSwicth
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.select_passager.SelectAgePassanger
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.flight_fragment_2.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackReasonCode
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.signin.CountryModel
import org.json.JSONArray

class FlightFragmentNew : BaseFragment(),
        OnclickListenerRecyclerView,
        View.OnClickListener,
        ButtonTopRoundedOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ButtonSwicth.OnclickButtonSwitch,
        NewCalendarViewOpsicorp.CallbackResult,
        SelectAgePassanger.CallbackSelectPasanger{

    override fun getLayout(): Int {
        return R.layout.flight_fragment_2
    }

    var typeTrip = ""
    val adapter by lazy { FlightMultiAdapter() }
    var mFlightMulti = ArrayList<OrderAccomodationModel>()

    var startDate = ""
    var endDate = ""
    var idClassAirline = "1"
    var nameClassAirline = "Economy Class"
    var dataTripPlan = SuccessCreateTripPlaneModel()
    var originName = ""
    var idOrigin = ""
    var destinationName = ""
    var idDestination = ""
    var currentPosition: Int = -1

    var totalAdult : Int = 0
    var totalInfant: Int = 0
    var totalChild : Int = 0

    var SELECT_CODE_COUNTRY_FROM = 28
    var SELECT_CODE_COUNTRY_TO = 26

    var namesAirlines  = ArrayList<String>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()

    val BASE_PACKAGE_MODULE = "com.opsicorp.travelaja.feature_flight.result."
    val BASE_PACKAGE_MODULE_MULTI_CITY = "com.opsicorp.travelaja.feature_flight.multi_city."

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        Globals.typeAccomodation = "Flight"

        /*checkTypeOrder()*/
        setOnClick()
        initRecycleView()
        setDataDefaultOneTrip()
        setData(true)
        setData(false)
        getReasonCode()
    }

    private fun checkTypeOrder() {
        if (Constants.isBisnisTrip){
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


    private fun setDataDefaultOneTrip() {
        Globals.ALL_READY_SELECT_DEPARTING = false
        Globals.ONE_TRIP = false
        typeTrip = "round_trip"
        setDefaultOriginDestination()
        setDefaultDate()
    }

    private fun setDefaultDate() {
        val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
        startDate(DateConverter().getDate(data.startDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy"), data.startDate)
        endDate(DateConverter().getDate(data.endDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy"), data.endDate)
    }

    private fun setDefaultOriginDestination() {
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
                tv_from.text = "${originName} (${idOrigin})"
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
                tv_to.text = "${destinationName} (${idDestination})"

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
                    tv_to.text = "${destinationName} (${idDestination})"
                    break
                }
            }
        }
    }

    private fun checkSize() {
        if (mFlightMulti.size < 5) {
            btAddOtherFlight.visible()
        } else {
            btAddOtherFlight.gone()
        }
    }

    private fun setData(first: Boolean) {
        val orderFlight = OrderAccomodationModel()
        if (first) {
            val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            orderFlight.dateDeparture = DateConverter().getDate(data.startDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy")
            orderFlight.originName = "${data.originName} (${data.originId})"
            orderFlight.destinationName = "${data.destinationName} (${data.destinationId})"
        }
        mFlightMulti.add(orderFlight)
        adapter.setData(mFlightMulti)
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvFlightMultiCity.layoutManager = layoutManager
        rvFlightMultiCity.itemAnimator = DefaultItemAnimator()
        rvFlightMultiCity.adapter = adapter
    }

    private fun setOnClick() {
        top_button.callbackOnclickToolbar(this)
        top_button.setTextBtnLeft("Round trip/ Oneway")
        top_button.setTextBtnRight("Multi City")

        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("Search Flight")

        btn_switch.callbackOnclickButtonSwicht(this)
        btn_switch.setItemSwicth(tv_from,tv_to)

        tv_from.setOnClickListener(this)
        tv_to.setOnClickListener(this)

        tv_departur_date.setOnClickListener(this)
        tv_end_date.setOnClickListener(this)

        lay_parent_passager.setOnClickListener(this)
        lay_air_class.setOnClickListener(this)
        lay_air_pref.setOnClickListener(this)

        btAddOtherFlight.setOnClickListener(this)
        flightSwitch.setOnClickListener(this)
        adapter.setOnclickListener(this)

    }

    override fun onClick(views: Int, position: Int) {
        when (views) {
            Constants.REQUEST_CODE_SELECT_FROM_MULTI -> {
                openCityFrom(position)
            }
            Constants.REQUEST_CODE_SELECT_TO_MULTI -> {
                openCityTo(position)
            }
            Constants.REQUEST_CODE_SELECT_DEPARTURE -> {
                selectDate(position)
            }
            Constants.REQUEST_CODE_DELETE_MULTI -> {
                mFlightMulti.removeAt(position)
                adapter.notifyDataSetChanged()
                checkSize()
            }
            Constants.REQUEST_CODE_SWITCH_DATA -> {
                if (mFlightMulti[position].originName.isNotEmpty() && mFlightMulti[position].destinationName.isNotEmpty()) {
                    val currentOrigin = mFlightMulti[position].originName
                    mFlightMulti[position].originName = mFlightMulti[position].destinationName
                    mFlightMulti[position].destinationName = currentOrigin
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun selectDate(position: Int) {
        currentPosition = position
        NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!, "yyyy-MM-dd", dataTripPlan.startDate, dataTripPlan.endDate, Constant.SINGGLE_SELECTED)
    }

    override fun onClick(v: View?) {
        when (v) {
            btAddOtherFlight -> {
                addOtherFlight()
                checkSize()
            }
            flightSwitch -> {
                if (flightSwitch.isChecked) {
                    typeTrip = "one_trip"
                    Globals.ONE_TRIP = true
                    lay_return_date.gone()
                } else {
                    Globals.ONE_TRIP = false
                    typeTrip = "round_trip"
                    lay_return_date.visible()
                }
            }
            tv_from -> {
                selectCityFrom()
            }
            tv_to -> {
                selectCityTo()
            }
            tv_departur_date -> {
                openCalendar()
            }
            tv_end_date -> {
                openCalendar()
            }
            lay_parent_passager -> {
                val fm = requireActivity().getSupportFragmentManager()
                val selectPassager = SelectAgePassanger(true,R.style.CustomDialog)
                selectPassager.show(fm, "yesNoAlert")
                selectPassager.callback = this
                selectPassager.setLimitSelect(4,3,2)
            }
            lay_air_class -> {
                airlineClass()
            }
            lay_air_pref -> {
                airlinePref()
            }
        }

    }

    private fun airlinePref() {
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance = SelectAccomodationPreferance(true,R.style.CustomDialog,dataPrefarance)
        selectAccomodationPreferance.show(requireFragmentManager(),"dialog")

        selectAccomodationPreferance.setCallbackListener(object : SelectAccomodationPreferance.CallbackSelectPreferance{
            override fun callback(string: String) {
                tv_airline_prreferance.text = string
            }
        })
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

    private fun addNamesAirline() {
        namesAirlines.clear()
        namesAirlines.add("Select All")
        val dataJson = JSONArray(Globals.readJsonFromFile(context!!,Constants.FILE_NAME_ALL_CODE_AIRPORT))
        for (i in 0 until dataJson.length()){
            namesAirlines.add(dataJson.getJSONObject(i).getString("nameAirline"))
        }
    }

    private fun airlineClass() {
        showPopUpRemove(tv_airline_class_new)
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
            nameClassAirline = "First Class"
            tv_airline_class_new.text = nameClassAirline
        }

        btnEconomy.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "3"
            nameClassAirline = "Economy Class"
            tv_airline_class_new.text = nameClassAirline
        }

        btnBisnis.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "2"
            nameClassAirline = "Business Class"
            tv_airline_class_new.text = nameClassAirline
        }

        popupWindow.showAsDropDown(option)
    }

    private fun openCalendar() {
        if (Globals.ONE_TRIP) {
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!, "yyyy-MM-dd", dataTripPlan.startDate, dataTripPlan.endDate, Constant.SINGGLE_SELECTED)
        } else {
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!, "yyyy-MM-dd", dataTripPlan.startDate, dataTripPlan.endDate, Constant.DOUBLE_SELECTED)
        }
    }

    private fun selectCityFrom() {
        val bundle = Bundle()
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, SELECT_CODE_COUNTRY_FROM)

    }

    private fun selectCityTo() {
        val bundle = Bundle()
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, SELECT_CODE_COUNTRY_TO)
    }

    private fun addOtherFlight() {
        setData(false)
    }

    override fun btnLeft() {
        Globals.ONE_TRIP = false
        typeTrip = "round_trip"
        cardFlightOneTrip.visible()
        rvFlightMultiCity.gone()
        btAddOtherFlight.gone()
    }

    override fun btnRight() {
        Globals.ONE_TRIP = false
        typeTrip = "multi_city"
        cardFlightOneTrip.gone()
        rvFlightMultiCity.visible()
        btAddOtherFlight.visible()
    }

    override fun onResume() {
        super.onResume()
        Globals.ALL_READY_SELECT_DEPARTING = false
    }

    override fun onClicked() {
        if (typeTrip.equals("multi_city")) {
            var isDataEmptyDestination = false
            var isDateEmptyOrigin = false
            var isDataEmptyDate = false
            var positionFlightEmpty = 0
            mFlightMulti.forEach {
                if (it.destinationName.isNotEmpty()) {
                    isDataEmptyDestination = true
                    positionFlightEmpty = currentPosition + 1
                }
                if (it.originName.isNotEmpty()) {
                    isDateEmptyOrigin = true
                    positionFlightEmpty = currentPosition + 1
                }
                if (it.dateDeparture.isNotEmpty()) {
                    isDataEmptyDate = true
                    positionFlightEmpty = currentPosition + 1
                }
            }
            gotoActivityModule(context!!, BASE_PACKAGE_MODULE_MULTI_CITY + "FlightMultiCityListActivity")
            /*if (isDataEmptyDestination && isDateEmptyOrigin && isDataEmptyDate) {
                Globals.showAlert("sorry", "Please Select Flight ${positionFlightEmpty}", context!!)
            } else {
                Constants.DATA_FLIGHT_MULTI_CITY.addAll(mFlightMulti)
                gotoActivityModule(context!!, BASE_PACKAGE_MODULE_MULTI_CITY + "FlightMultiCityListActivity")
            }*/
        } else {
            val dataOrder = OrderAccomodationModel()
            dataOrder.typeTrip = typeTrip

            dataOrder.idOrigin = idOrigin
            dataOrder.idDestination = idDestination

            dataOrder.originName = originName
            dataOrder.destinationName = destinationName

            dataOrder.dateDeparture = startDate
            dataOrder.dateArrival = endDate
            dataOrder.classFlightCode = idClassAirline
            dataOrder.classFlightName = nameClassAirline

            dataOrder.totalPassagerString = tv_passanger_new.text.toString()
            dataOrder.totalPassangerInt = "${totalAdult},${totalChild},${totalInfant}"
            dataOrder.airlinePreferance = tv_airline_prreferance.text.toString()

            Globals.DATA_ORDER_FLIGHT = Serializer.serialize(dataOrder, OrderAccomodationModel::class.java)
            Globals.DATA_LIST_FLIGHT = ""
            setLog("Test Reservasi",Serializer.serialize(dataOrder))

            gotoActivityModule(context!!, BASE_PACKAGE_MODULE + "ResultSearchFlightActivity")

        }

    }

    override fun startDate(displayStartDate: String, startDate: String) {
        if (typeTrip.equals("multi_city")) {
            mFlightMulti[currentPosition].dateDeparture = DateConverter().getDate(startDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy")
        } else {
            tv_departur_date.text = DateConverter().getDate(startDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy")
        }

        if (startDate.length > 10) {
            val sDate = startDate.substring(0, 10)
            this.startDate = sDate
        } else {
            this.startDate = startDate
        }
        adapter.notifyDataSetChanged()
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        if (endDate.isNotEmpty()){
            tv_end_date.text = DateConverter().getDate(endDate, "yyyy-MM-dd", "EEEE, dd MMM yyyy")
        } else {
            tv_end_date.text = "Select Return Date"
        }
        if (endDate.length > 10) {
            val sDate = endDate.substring(0, 10)
            this.endDate = sDate
        } else {
            this.endDate = endDate
        }

        adapter.notifyDataSetChanged()
    }

    override fun canceledCalendar() {

    }


    private fun openCityTo(position: Int) {
        val bundle = Bundle()
        currentPosition = position
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select City or Airport")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, Constants.REQUEST_CODE_SELECT_TO_MULTI)
    }

    private fun openCityFrom(position: Int) {
        val bundle = Bundle()
        currentPosition = position
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select City or Airport")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, Constants.REQUEST_CODE_SELECT_FROM_MULTI)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data, this)

        when (requestCode) {
            Constants.REQUEST_CODE_SELECT_FROM_MULTI -> {
                if (resultCode == Activity.RESULT_OK) {
                    mFlightMulti[currentPosition].originName = data?.getStringExtra("nameCountry").toString()
                    mFlightMulti[currentPosition].idOrigin = data?.getStringExtra("idCountry").toString()
                    originName = data?.getStringExtra("nameCountry").toString()
                    idOrigin = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
            Constants.REQUEST_CODE_SELECT_TO_MULTI -> {
                if (resultCode == Activity.RESULT_OK) {
                    mFlightMulti[currentPosition].destinationName = data?.getStringExtra("nameCountry").toString()
                    mFlightMulti[currentPosition].idDestination = data?.getStringExtra("idCountry").toString()
                    destinationName = data?.getStringExtra("nameCountry").toString()
                    idDestination = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
            SELECT_CODE_COUNTRY_FROM -> {
                if (resultCode == Activity.RESULT_OK) {
                    tv_from.text = "${data?.getStringExtra("nameCountry")} (${data?.getStringExtra("idCountry").toString()})"
                    originName = data?.getStringExtra("nameCountry").toString()
                    idOrigin = data?.getStringExtra("idCountry").toString()
                } else {

                }
            }
            SELECT_CODE_COUNTRY_TO -> {
                if (resultCode == Activity.RESULT_OK) {
                    tv_to.text = "${data?.getStringExtra("nameCountry")} (${data?.getStringExtra("idCountry").toString()})"
                    destinationName = data?.getStringExtra("nameCountry").toString()
                    idDestination = data?.getStringExtra("idCountry").toString()
                } else {

                }
            }
        }
    }

    override fun onSwitch() {
        if (originName.isNotEmpty() && destinationName.isNotEmpty()) {
            val currentOrigin = originName
            val currentOriginId = idOrigin
            originName = destinationName
            idOrigin = idDestination
            destinationName = currentOrigin
            idDestination = currentOriginId
        }
    }

    private fun getReasonCode() {
        GetDataAccomodation(getBaseUrl()).getReasonCodeTrain(getToken(), Constants.TripType.Airline.toString(), object : CallbackReasonCode {
            override fun success(reasonCodeModel: ArrayList<ReasonCodeModel>) {
                Constants.DATA_REASON_CODE_FLIGHT = reasonCodeModel
            }

            override fun failed(string: String) {

            }
        })
    }

    override fun total(mTotalInfant: Int, mTotalChild: Int, mTotalAdult: Int) {
        if (mTotalAdult==1&&mTotalInfant==1&&mTotalChild==1){
            tv_passanger_new.setText("${mTotalAdult} Adult, ${mTotalChild} Child, ${mTotalInfant} Infant")
        }
        else if(mTotalAdult==1&&mTotalInfant==1&&mTotalChild==0){
            tv_passanger_new.setText("${mTotalAdult} Adult, ${mTotalInfant} Infant")
        }
        else if(mTotalAdult>1&&mTotalInfant==1&&mTotalChild==0){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalInfant} Infant")
        }
        else if(mTotalAdult>1&&mTotalInfant>1&&mTotalChild==0){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalInfant} Infants")
        }
        else if(mTotalAdult==1&&mTotalInfant==0&&mTotalChild==1){
            tv_passanger_new.setText("${mTotalAdult} Adult, ${mTotalChild} Child")
        }
        else if(mTotalAdult>1&&mTotalInfant==0&&mTotalChild==1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Child")
        }
        else if(mTotalAdult>1&&mTotalInfant==0&&mTotalChild>1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Children")
        }
        else if(mTotalAdult==1&&mTotalInfant==0&&mTotalChild==0){
            tv_passanger_new.setText("${mTotalAdult} Adult ")
        }
        else if (mTotalAdult>1&&mTotalInfant==0&&mTotalChild==0){
            tv_passanger_new.setText("${mTotalAdult} Adults ")
        }
        else if(mTotalAdult>1&&mTotalChild>1&&mTotalInfant>1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Children, ${mTotalInfant} Infants")
        }
        else if(mTotalAdult>1&&mTotalChild>1&&mTotalInfant==1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Children, ${mTotalInfant} Infant")
        }
        else if(mTotalAdult>1&&mTotalChild==1&&mTotalInfant>1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Child, ${mTotalInfant} Infants")
        }
        else if(mTotalAdult>1&&mTotalChild==1&&mTotalInfant==1){
            tv_passanger_new.setText("${mTotalAdult} Adults, ${mTotalChild} Child, ${mTotalInfant} Infant")
        }

        totalAdult = mTotalAdult
        totalInfant = mTotalInfant
        totalChild = mTotalChild
    }
}