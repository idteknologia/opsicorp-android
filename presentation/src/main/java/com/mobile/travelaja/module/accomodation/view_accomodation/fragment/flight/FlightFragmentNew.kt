package com.mobile.travelaja.module.accomodation.view_accomodation.fragment.flight

import android.view.View
import android.os.Bundle
import org.json.JSONArray
import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.content.Context
import android.widget.TextView
import android.widget.PopupWindow
import android.view.LayoutInflater
import com.mobile.travelaja.utility.*
import com.mobile.travelaja.base.BaseFragment
import opsigo.com.datalayer.mapper.Serializer
import android.graphics.drawable.BitmapDrawable
import com.mobile.travelaja.base.InitApplications
import com.opsicorp.sliderdatepicker.utils.Constant
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance.AccomodationPreferanceModel
import com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance.SelectAccomodationPreferance
import com.mobile.travelaja.module.accomodation.view_accomodation.fragment.flight.adapter.FlightMultiAdapter
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.button_swicth.ButtonSwicth
import com.mobile.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_cabin_class.CabisClassDialog
import com.mobile.travelaja.module.item_custom.select_passager.TotalPassengerFlight
import com.mobile.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.mobile.travelaja.utility.Constants.SELECT_RESULT
import opsigo.com.domainlayer.model.signin.CountryModel
import opsigo.com.domainlayer.callback.CallbackReasonCode
import kotlinx.android.synthetic.main.flight_fragment_2.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import kotlinx.android.synthetic.main.flight_fragment_2.tv_to
import kotlinx.android.synthetic.main.flight_fragment_2.tv_from
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import kotlinx.android.synthetic.main.flight_fragment_2.btn_next
import kotlinx.android.synthetic.main.flight_fragment_2.btn_switch
import kotlinx.android.synthetic.main.flight_fragment_2.top_button
import kotlinx.android.synthetic.main.flight_fragment_2.tv_end_date
import kotlinx.android.synthetic.main.flight_fragment_2.lay_air_pref
import kotlinx.android.synthetic.main.flight_fragment_2.lay_air_class
import kotlinx.android.synthetic.main.flight_fragment_2.lay_return_date
import kotlinx.android.synthetic.main.flight_fragment_2.tv_departur_date
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel
import kotlinx.android.synthetic.main.flight_fragment_2.lay_parent_passager
import kotlinx.android.synthetic.main.flight_fragment_2.tv_airline_prreferance
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.request_model.accomodation.flight.search.ValidationRouteAvailable
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.AirlinePrefByCompanyRequest
import opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref.RoutesItem
import opsigo.com.domainlayer.callback.CallbackAirlinePreference
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import java.util.HashSet


class FlightFragmentNew : BaseFragment(),
    OnclickListenerRecyclerView,
    View.OnClickListener,
    ButtonTopRoundedOpsicorp.OnclickButtonListener,
    ButtonDefaultOpsicorp.OnclickButtonListener,
    ButtonSwicth.OnclickButtonSwitch,
    NewCalendarViewOpsicorp.CallbackResult,
    TotalPassengerFlight.CallbackSelectPasanger,
    CabisClassDialog.CallbackSelectCabin {

    override fun getLayout(): Int {
        return R.layout.flight_fragment_2
    }

    var typeTrip = ""
    val adapter by lazy { FlightMultiAdapter() }
    var mFlightMulti   = OrderAccomodationModel()

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

    var totalAdult: Int = 1
    var totalInfant: Int = 0
    var totalChild: Int = 0

    var SELECT_CODE_COUNTRY_FROM = 28
    var SELECT_CODE_COUNTRY_TO = 26

    var namesAirlines = ArrayList<String>()
    var dataPrefarance = ArrayList<AccomodationPreferanceModel>()

    val BASE_PACKAGE_MODULE = "com.opsicorp.travelaja.feature_flight.result."
    val BASE_PACKAGE_MODULE_MULTI_CITY = "com.opsicorp.travelaja.feature_flight.multi_city."

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        dataTripPlan = Serializer.deserialize(
            Constants.DATA_SUCCESS_CREATE_TRIP,
            SuccessCreateTripPlaneModel::class.java
        )
        Globals.typeAccomodation = "Flight"

        checkTypeOrder()
        setOnClick()
        initRecycleView()
        setDataDefaultOneTrip()
        setDataDefault()
        getReasonCode()
    }

    private fun checkTypeOrder() {
        /*if (Constants.isBisnisTrip) {
            lay_parent_passager.visibility = View.GONE
            lay_air_class.gone()
            lay_air_pref.gone()
        } else {
            lay_parent_passager.visibility = View.VISIBLE
            lay_air_class.visible()
            lay_air_pref.visible()
        }*/
        if ((Globals.getBaseUrl(requireContext()) == "https://pertamina-dtm3-qa.opsicorp.com/")){
            cardExtras.gone()
        } else {
            cardExtras.visible()
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
        val data = Serializer.deserialize(
            Constants.DATA_SUCCESS_CREATE_TRIP,
            SuccessCreateTripPlaneModel::class.java
        )
        startDate(
            DateConverter().getDate(data.startDate, "yyyy-MM-dd", "dd MMM yyyy"),
            data.startDate
        )
        endDate(DateConverter().getDate(data.endDate, "yyyy-MM-dd", "dd MMM yyyy"), data.endDate)
    }

    private fun setDefaultOriginDestination() {
//        val listdata = JSONArray(Serializer.serialize(Constants.DATA_CITY))

        val def_origin = Globals.getConfigCompany(InitApplications.appContext).defaultOrigin
        val def_destinationn = Globals.getConfigCompany(InitApplications.appContext).defaultDestination

        try {
            if (dataTripPlan.route.isNotEmpty()){
                // setDefaultorigin
                idOrigin = Constants.DATA_CITY.find { it.name.toLowerCase().equals(dataTripPlan.route.first().originName.toLowerCase()) }?.id.toString()
                originName = Constants.DATA_CITY.find { it.name.toLowerCase().equals(dataTripPlan.route.first().originName.toLowerCase()) }?.name.toString()
                tv_from.text = "$originName (${idOrigin})"

                //setDefaultDestination
                idDestination = Constants.DATA_CITY.find { it.name.toLowerCase().equals(dataTripPlan.route.first().destinationName.toLowerCase()) }?.id.toString()
                destinationName = Constants.DATA_CITY.find { it.name.toLowerCase().equals(dataTripPlan.route.first().destinationName.toLowerCase()) }?.name.toString()
                tv_to.text = "$destinationName (${idDestination})"
            }
            else {
                // setDefaultorigin
                idOrigin = Constants.DATA_CITY.find { it.name.toLowerCase().equals(def_origin.toLowerCase()) }?.id.toString()
                originName = Constants.DATA_CITY.find { it.name.toLowerCase().equals(def_origin.toLowerCase()) }?.name.toString()
                tv_from.text = "$originName (${idOrigin})"

                //setDefaultDestination
                idDestination = Constants.DATA_CITY.find { it.name.toLowerCase().equals(def_destinationn.toLowerCase()) }?.id.toString()
                destinationName = Constants.DATA_CITY.find { it.name.toLowerCase().equals(def_destinationn.toLowerCase()) }?.name.toString()
                tv_to.text = "$destinationName (${idDestination})"

            }
        }catch (e:Exception){}


        /*for (i in 0 until listdata.length()) {
            val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
            val model = SelectNationalModel()
            model.name = mData.name
            model.id = mData.id

            if (model.name.toLowerCase().contains(def_origin.toLowerCase())) {
                idOrigin = model.id
                originName = model.name
                tv_from.text = "$originName (${idOrigin})"
                break
            }
        }*/

        /*for (i in 0 until listdata.length()) {
            val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
            val model = SelectNationalModel()
            model.name = mData.name
            model.id = mData.id

            if (model.name.equals(dataTripPlan.destinationName)) {
                idDestination = model.id
                destinationName = model.name
                tv_to.text = "$destinationName (${idDestination})"

                isFoundDestination = true;

                Log.d("xfligx", "desti trip : " + dataTripPlan.destinationId);
                break
            }
        }*/
        /*if (!isFoundDestination) {

            for (i in 0 until listdata.length()) {
                val mData = Serializer.deserialize(listdata[i].toString(), CountryModel::class.java)
                val model = SelectNationalModel()
                model.name = mData.name
                model.id = mData.id

                if (model.id.equals(def_destinationn)) {
                    idDestination = model.id
                    destinationName = model.name
                    tv_to.text = "$destinationName (${idDestination})"
                    break
                }
            }
        }*/
    }

    private fun checkSize() {
        if (mFlightMulti.routes.size < 5) {
            btAddOtherFlight.visible()
        } else {
            btAddOtherFlight.gone()
        }
    }

    private fun setDataDefault() {
        mFlightMulti.routes.clear()
        val data = Serializer.deserialize(
                Constants.DATA_SUCCESS_CREATE_TRIP,
                SuccessCreateTripPlaneModel::class.java
        )

        if (data.route.isNotEmpty()){
            data.route.forEachIndexed { index, routeMultiCityModel ->
                val orderFlight = RouteMultiCityModel()
                orderFlight.dateDeparture   = routeMultiCityModel.dateDeparture
                if (Constants.DATA_CITY.filter { it.name.contains(routeMultiCityModel.originName) }.isNotEmpty()){
                    orderFlight.originName   = routeMultiCityModel.originName
                    orderFlight.idOrigin     = Constants.DATA_CITY.filter { it.name.toLowerCase().equals(routeMultiCityModel.originName.toLowerCase()) }.first().id
                }
                if (Constants.DATA_CITY.filter { it.name.contains(routeMultiCityModel.destinationName) }.isNotEmpty()){
                    orderFlight.destinationName = routeMultiCityModel.destinationName
                    orderFlight.idDestination   = Constants.DATA_CITY.filter { it.name.toLowerCase().equals(routeMultiCityModel.destinationName.toLowerCase()) }.first().id
                }
                mFlightMulti.routes.add(orderFlight)
            }
        }else {
            for (i in 0 until 2){
                val orderFlight = RouteMultiCityModel()
                if (i == 0) {
                    orderFlight.dateDeparture = if (data.startDate.contains(":")) data.startDate.split(" ")[0].trim() else data.startDate
                    orderFlight.originName = originName
                    orderFlight.idOrigin = idOrigin
                    orderFlight.destinationName = destinationName
                    orderFlight.idDestination = idDestination
                } else {
                    orderFlight.dateDeparture =  if (data.endDate.contains(":")) data.endDate.split(" ")[0].trim() else data.endDate//DateConverter().getNextDay("yyyy-MM-dd", "yyyy-MM-dd", data.startDate, 3)
                    orderFlight.originName = destinationName
                    orderFlight.idOrigin = idDestination
                    orderFlight.destinationName = originName
                    orderFlight.idDestination = idOrigin}
                mFlightMulti.routes.add(orderFlight)
            }
        }

        adapter.setData(mFlightMulti.routes)
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
        top_button.setTextBtnLeft(getString(R.string.round_trip_or_oneway))
        top_button.setTextBtnRight(getString(R.string.multi_city))

        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton(getString(R.string.search_flights))

        btn_switch.callbackOnclickButtonSwicht(this)
        btn_switch.setItemSwicth(tv_from, tv_to)

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
                mFlightMulti.routes.removeAt(position)
                adapter.notifyDataSetChanged()
                checkSize()
            }
            Constants.REQUEST_CODE_SWITCH_DATA -> {
                if (mFlightMulti.routes[position].originName.isNotEmpty() && mFlightMulti.routes[position].destinationName.isNotEmpty()) {
                    val currentOrigin = mFlightMulti.routes[position].originName
                    mFlightMulti.routes[position].originName = mFlightMulti.routes[position].destinationName
                    mFlightMulti.routes[position].destinationName = currentOrigin
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun selectDate(position: Int) {
        currentPosition = position
//        var minStartDate = mFlightMulti.routes.filter { it.dateDeparture.isNotEmpty() }.last().dateDepartureNewCalendarViewOpsicorp().showCalendarViewMinMax(
//            requireActivity(),
//            "yyyy-MM-dd",
//            minStartDate,
//            dataTripPlan.endDate,
//            Constant.SINGGLE_SELECTED
//        )
        NewCalendarViewOpsicorp().showCalendarViewMinMax(
                requireActivity(),
                "yyyy-MM-dd",
                dataTripPlan.startDate,
                dataTripPlan.endDate,
                Constant.SINGGLE_SELECTED
        )
        /*var minStartDate = mFlightMulti.routes.filter { it.dateDeparture.isNotEmpty() }.last().dateDeparture
        NewCalendarViewOpsicorp().showCalendarViewMinMax(requireActivity(), "yyyy-MM-dd", minStartDate, dataTripPlan.endDate, Constant.SINGGLE_SELECTED)*/

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
                openCityFrom(-1)
            }
            tv_to -> {
                openCityTo(-1)
            }
            tv_departur_date -> {
                openCalendar()
            }
            tv_end_date -> {
                openCalendar()
            }
            lay_parent_passager -> {
                val totalPassangerFlight = TotalPassengerFlight()
                totalPassangerFlight.setLimitSelect(5, 2, 3)
                totalPassangerFlight.setCurrentSelect(totalAdult, totalInfant, totalChild)
                totalPassangerFlight.create(requireContext(), this)
            }
            lay_air_class -> {
                val cabinClass = CabisClassDialog()
                cabinClass.setCurrentSelect(nameClassAirline)
                cabinClass.create(requireContext(), this)
            }
            lay_air_pref -> {
                airlinePref()
            }
        }

    }

    private fun airlinePref() {
        addNamesAirline()
        addDataAirPreferance()
        val selectAccomodationPreferance =
            SelectAccomodationPreferance(true, R.style.CustomDialog, dataPrefarance)
        selectAccomodationPreferance.show(requireFragmentManager(), "dialog")

        selectAccomodationPreferance.setCallbackListener(object :
            SelectAccomodationPreferance.CallbackSelectPreferance {
            override fun callback(
                string: String,
                dataAirlines: ArrayList<AccomodationPreferanceModel>
            ) {
                tv_airline_prreferance.text = string
            }
        })
    }

    private fun addDataAirPreferance() {
        namesAirlines.forEachIndexed { index, names ->
            val mData = AccomodationPreferanceModel()
            mData.id = "${index + 1}"
            mData.checked = false
            mData.name = names
            dataPrefarance.add(mData)
        }
    }

    private fun addNamesAirline() {
        namesAirlines.clear()
        namesAirlines.add(getString(R.string.select_all))
        val dataJson = JSONArray(
            Globals.readJsonFromFile(
                requireContext(),
                Constants.FILE_NAME_ALL_CODE_AIRPORT
            )
        )
        for (i in 0 until dataJson.length()) {
            namesAirlines.add(dataJson.getJSONObject(i).getString("nameAirline"))
        }
    }

    private fun airlineClass() {
        showPopUpRemove(tv_airline_class_new)
    }

    private fun showPopUpRemove(option: TextView) {
        val layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_class_airline, null)
        val btnEconomy = layout.findViewById(R.id.btn_economy_class) as TextView
        val btnBisnis = layout.findViewById(R.id.btn_bisnis_class) as TextView
        val btnFirst = layout.findViewById(R.id.btn_first_class) as TextView

        val popupWindow = PopupWindow(
            layout,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener(object : PopupWindow.OnDismissListener {
            override fun onDismiss() {

            }
        })

        btnFirst.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "1"
            nameClassAirline = getString(R.string.first_class)
            tv_airline_class_new.text = nameClassAirline
        }

        btnEconomy.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "3"
            nameClassAirline = getString(R.string.txt_economy_class)
            tv_airline_class_new.text = nameClassAirline
        }

        btnBisnis.setOnClickListener {
            popupWindow.dismiss()
            idClassAirline = "2"
            nameClassAirline = getString(R.string.business_class)
            tv_airline_class_new.text = nameClassAirline
        }

        popupWindow.showAsDropDown(option)
    }

    private fun openCalendar() {
        if (Globals.ONE_TRIP) {
            NewCalendarViewOpsicorp().showCalendarViewMinMax(
                requireActivity(),
                "yyyy-MM-dd",
                dataTripPlan.startDate,
                dataTripPlan.endDate,
                Constant.SINGGLE_SELECTED
            )
        } else {
            NewCalendarViewOpsicorp().showCalendarViewMinMax(
                requireActivity(),
                "yyyy-MM-dd",
                dataTripPlan.startDate,
                dataTripPlan.endDate,
                Constant.DOUBLE_SELECTED
            )
        }
    }



    private fun addOtherFlight() {
        val orderFlight = RouteMultiCityModel()
        mFlightMulti.routes.add(orderFlight)
        adapter.setData(mFlightMulti.routes)
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
        if (getProfile().companyCode!="000002"){
            btAddOtherFlight.visible()
        }
        else {
            btAddOtherFlight.gone()
        }
    }

    override fun onResume() {
        super.onResume()
        Globals.ALL_READY_SELECT_DEPARTING = false
    }

    override fun onClicked() {
        val dataOrder = OrderAccomodationModel()
        dataOrder.typeTrip = typeTrip

        dataOrder.idOrigin = idOrigin
        dataOrder.idDestination = idDestination

        dataOrder.originName = originName
        dataOrder.destinationName = destinationName

        dataOrder.dateDeparture = startDate
        dataOrder.dateArrival   = endDate
        dataOrder.classFlightCode = idClassAirline
        dataOrder.classFlightName = nameClassAirline

        dataOrder.totalPassengerString = tv_passanger_new.text.toString()
        dataOrder.totalPassengerInt = "${totalAdult},${totalChild},${totalInfant}"
        dataOrder.adult = totalAdult
        dataOrder.child = totalChild
        dataOrder.infant = totalInfant
        dataOrder.totalPassenger = totalAdult + totalChild + totalInfant
        dataOrder.airlinePreference = tv_airline_prreferance.text.toString()
        Globals.DATA_LIST_FLIGHT    = ""

        if (getBaseUrl()==Constants.pertaminaUrl){
            showDialog("Waiting check available route")
            GetDataAccomodation(getBaseUrl()).getPreferedFlight(getToken(),dataRequestAirlinePref(dataOrder),object :
                CallbackAirlinePreference {
                override fun successLoad(data: AirlineCodeCompanyModel) {
                    GetDataAccomodation(getBaseUrl()).getRouteFlightAvailable(getToken(),dataRoute(data.listSchedule),object :
                        CallbackString {
                        override fun successLoad(mData: String) {
                            gotoResultFlightActivity(dataOrder)
                            hideDialog()
                        }

                        override fun failedLoad(message: String) {
                            hideDialog()
                            Globals.showAlert("Sorry",message,requireActivity())
                        }
                    })
                }

                override fun failedLoad(message: String) {

                }
            })

        }
        else {
            gotoResultFlightActivity(dataOrder)
        }
    }

    private fun dataRoute(data :ArrayList<ListScheduleItem>): HashMap<Any, Any> {
        val dataTripPlan = Serializer.deserialize(
            Constants.DATA_SUCCESS_CREATE_TRIP,
            SuccessCreateTripPlaneModel::class.java
        )
        val mData = ValidationRouteAvailable()
        mData.schedule.addAll(data)
        mData.tripId = dataTripPlan.idTripPlane
        return Globals.classToHashMap(mData, ValidationRouteAvailable::class.java)
    }

    private fun gotoResultFlightActivity(dataOrder: OrderAccomodationModel) {
        if (typeTrip.equals("multi_city")){
            Constants.ALREADY_SEARCH_FLIGHT = false
            Constants.multitrip             = true
            if (isEmptyMulticity().first) {
                Globals.showAlert("sorry", "Please Select Flight ${isEmptyMulticity().second}", requireActivity())
            } else {
                mFlightMulti.adult = totalAdult
                mFlightMulti.child = totalChild
                mFlightMulti.infant = totalInfant
                mFlightMulti.totalPassenger = totalAdult + totalChild + totalInfant
                mFlightMulti.classFlightCode = "1"
                Globals.DATA_ORDER_FLIGHT = Serializer.serialize(mFlightMulti, OrderAccomodationModel::class.java)
                gotoActivityModule(requireContext(), BASE_PACKAGE_MODULE_MULTI_CITY + "FlightMultiCityListActivity")
            }
        }
        else {
            Globals.DATA_ORDER_FLIGHT =
                Serializer.serialize(dataOrder, OrderAccomodationModel::class.java)
            Constants.ALREADY_SEARCH_FLIGHT = false
            Constants.multitrip             = false
            Globals.DATA_LIST_FLIGHT = ""
            Constants.isBisnisTrip = !dataTripPlan.tripCode.contains("PT")
            gotoActivityModule(requireContext(), BASE_PACKAGE_MODULE + "ResultSearchFlightActivity")
        }
        setLog("Test Reservasi",Serializer.serialize(Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)))

    }

    private fun dataRequestAirlinePref(dataOrder: OrderAccomodationModel): HashMap<Any, Any> {
        val data = AirlinePrefByCompanyRequest()
        data.preferredCarriers = ArrayList()
        if (dataTripPlan.route.size>1){
            data.routes            = dataRoutesRequestMultiTrip(dataOrder)
            data.flightTripType    = 3
        }
        else {
            if (Globals.ONE_TRIP){
                data.flightTripType    = 1
            }
            else{
                data.flightTripType    = 2
            }
            data.routes            = dataRoutesRequest(dataOrder)
        }
        data.cabinClassList    = dataCabinClass(dataOrder)
        data.travelAgent       = Globals.getConfigCompany(requireContext()).defaultTravelAgent
        data.adult             = 1
        data.infant            = 0
        data.employeeId        = Globals.getProfile(requireContext()).employId
        data.child             = 0
        return Globals.classToHashMap(data, AirlinePrefByCompanyRequest::class.java)

    }

    private fun dataRoutesRequestMultiTrip(dataOrder: OrderAccomodationModel): List<RoutesItem?> {
        val dataRoutes    = ArrayList<RoutesItem>()
        dataOrder.routes.forEach {
            val model         = RoutesItem()
            model.origin      = it.idOrigin
            model.destination = it.idDestination //"BDO"
            model.departDate  = it.dateDeparture  //"2020-08-28"
            dataRoutes.add(model)
        }
        return dataRoutes
    }

    private fun dataCabinClass(dataOrder: OrderAccomodationModel): List<Int?> {
        val dataCabin = ArrayList<Int>()
        dataCabin.add(dataOrder.classFlightCode.toInt())
        return dataCabin
    }

    private fun dataRoutesRequest(dataOrder: OrderAccomodationModel): List<RoutesItem?> {
        val dataRoutes    = ArrayList<RoutesItem>()
        when (dataOrder.typeTrip){
            "one_trip"->{
                dataRoutes.add(addDepartureData(dataOrder))
            }
            "round_trip"->{
                dataRoutes.add(addDepartureData(dataOrder))
                dataRoutes.add(addReturnData(dataOrder))
            }
        }
        return dataRoutes
    }

    private fun addDepartureData(dataOrder: OrderAccomodationModel): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idOrigin
        model.destination = dataOrder.idDestination //"BDO"
        model.departDate  = dataOrder.dateDeparture //"2020-08-28"
        return model
    }

    private fun addReturnData(dataOrder: OrderAccomodationModel): RoutesItem {
        val model         = RoutesItem()
        model.origin      = dataOrder.idDestination
        model.destination = dataOrder.idOrigin //"BDO"
        model.departDate  = dataOrder.dateArrival //"2020-08-28"
        return model
    }

    private fun isEmptyMulticity(): Pair<Boolean, Int> {
        var isDataEmptyDestination = false
        var isDateEmptyOrigin = false
        var isDataEmptyDate = false
        var positionFlightEmpty = 0
        mFlightMulti.routes.forEachIndexed { index, it ->
            if (it.destinationName.isEmpty()) {
                isDataEmptyDestination = true
                positionFlightEmpty = index + 1
            }
            if (it.originName.isEmpty()) {
                isDateEmptyOrigin = true
                positionFlightEmpty = index + 1
            }
            if (it.dateDeparture.isEmpty()) {
                isDataEmptyDate = true
                positionFlightEmpty = index + 1
            }
        }
        if (isDataEmptyDestination || isDateEmptyOrigin || isDataEmptyDate) {
            return Pair(true, positionFlightEmpty)
        } else {
            return Pair(false, positionFlightEmpty)
        }
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        if (typeTrip.equals("multi_city")) {
            mFlightMulti.routes[currentPosition].dateDeparture = startDate
        } else {
            if (displayStartDate != "Error Formating")
            tv_departur_date.text = displayStartDate
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
        if (endDate.isNotEmpty()) {
            tv_end_date.text = displayEndDate
        } else {
            tv_end_date.text = getString(R.string.select_return_date)
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
        val listCity = ArrayList<String>()

        dataTripPlan.route.forEach {
            listCity.add(it.originName)
            listCity.add(it.destinationName)
        }
        /*
        * remove duplicate items
        * */
        val set: Set<String> = HashSet(listCity)
        listCity.clear()
        listCity.addAll(set)

        val bundle = Bundle()
        currentPosition = position
        bundle.putString(SELECT_RESULT, "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select City or Airport")
        bundle.putStringArrayList("listCity",listCity)
        if (position==-1){
            gotoActivityResultWithBundle(
                SelectNationalityActivity::class.java,
                bundle,
                SELECT_CODE_COUNTRY_TO
            )
        }
        else {
            gotoActivityResultWithBundle(
                SelectNationalityActivity::class.java,
                bundle,
                Constants.REQUEST_CODE_SELECT_TO_MULTI
            )
        }
    }

    private fun openCityFrom(position: Int) {
        val listCity = ArrayList<String>()

        dataTripPlan.route.forEach {
            listCity.add(it.originName)
            listCity.add(it.destinationName)
        }
        /*
        * remove duplicate items
        * */
        val set: Set<String> = HashSet(listCity)
        listCity.clear()
        listCity.addAll(set)

        val bundle = Bundle()
        currentPosition = position
        bundle.putString(SELECT_RESULT, "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select City or Airport")
        bundle.putStringArrayList("listCity",listCity)

        if (position==-1){
            gotoActivityResultWithBundle(
                SelectNationalityActivity::class.java,
                bundle,
                SELECT_CODE_COUNTRY_FROM
            )
        }
        else {
            gotoActivityResultWithBundle(
                SelectNationalityActivity::class.java,
                bundle,
                Constants.REQUEST_CODE_SELECT_FROM_MULTI
            )
        }
    }
/*

    private fun selectCityFrom() {
        var listCity = ArrayList<String>()
        listCity.add("Jakarta")
        listCity.add("Surabaya")
        val bundle = Bundle()
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select Cities and Airports")
        bundle.putStringArrayList("listCity",listCity)
        gotoActivityResultWithBundle(
            SelectNationalityActivity::class.java,
            bundle,
            SELECT_CODE_COUNTRY_FROM
        )

    }

    private fun selectCityTo() {
        var listCity = ArrayList<String>()
        listCity.add("Jakarta")
        listCity.add("Surabaya")
        val bundle = Bundle()
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Select Cities and Airports")
        bundle.putStringArrayList("listCity",listCity)
        gotoActivityResultWithBundle(
            SelectNationalityActivity::class.java,
            bundle,
            SELECT_CODE_COUNTRY_TO
        )
    }
*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data, this)

        when (requestCode) {
            Constants.REQUEST_CODE_SELECT_FROM_MULTI -> {
                if (resultCode == Activity.RESULT_OK) {
                    mFlightMulti.routes[currentPosition].originName =
                        data?.getStringExtra("nameCountry").toString()
                    mFlightMulti.routes[currentPosition].idOrigin =
                        data?.getStringExtra("idCountry").toString()
                    originName = data?.getStringExtra("nameCountry").toString()
                    idOrigin = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
            Constants.REQUEST_CODE_SELECT_TO_MULTI -> {
                if (resultCode == Activity.RESULT_OK) {
                    mFlightMulti.routes[currentPosition].destinationName =
                        data?.getStringExtra("nameCountry").toString()
                    mFlightMulti.routes[currentPosition].idDestination =
                        data?.getStringExtra("idCountry").toString()
                    destinationName = data?.getStringExtra("nameCountry").toString()
                    idDestination = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
            SELECT_CODE_COUNTRY_FROM -> {
                if (resultCode == Activity.RESULT_OK) {
                    tv_from.text = "${data?.getStringExtra("nameCountry")} (${
                        data?.getStringExtra("idCountry").toString()
                    })"
                    originName = data?.getStringExtra("nameCountry").toString()
                    idOrigin = data?.getStringExtra("idCountry").toString()
                } else {

                }
            }
            SELECT_CODE_COUNTRY_TO -> {
                if (resultCode == Activity.RESULT_OK) {
                    tv_to.text = "${data?.getStringExtra("nameCountry")} (${
                        data?.getStringExtra("idCountry").toString()
                    })"
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
        GetDataAccomodation(getBaseUrl()).getReasonCodeTrain(
            getToken(),
            Constants.TripType.Airline.toString(),
            object : CallbackReasonCode {
                override fun success(reasonCodeModel: ArrayList<ReasonCodeModel>) {
                    Constants.DATA_REASON_CODE_FLIGHT = reasonCodeModel
                }

                override fun failed(string: String) {

                }
            })
    }

    override fun total(mTotalInfant: Int, mTotalChild: Int, mTotalAdult: Int) {
        val txtChild = getString(R.string.child)
        val txtInfant = getString(R.string.infant)
        val txtAdult = getString(R.string.txt_adult)
        val txtChildren = getString(R.string.children)
        val txtAdults = getString(R.string.adults)
        val txtInfants = getString(R.string.infants)
        var text = ""
        if (mTotalAdult == 1 && mTotalInfant == 1 && mTotalChild == 1) {
            text = "$mTotalAdult $txtAdult, $mTotalChild $txtChild, $mTotalInfant $txtInfant"
        } else if (mTotalAdult == 1 && mTotalInfant == 1 && mTotalChild == 0) {
            text = "$mTotalAdult $txtAdult, $mTotalInfant $txtInfant"
        } else if (mTotalAdult > 1 && mTotalInfant == 1 && mTotalChild == 0) {
            text = "$mTotalAdult $txtAdults, $mTotalInfant $txtInfant"
        } else if (mTotalAdult > 1 && mTotalInfant > 1 && mTotalChild == 0) {
            text = "$mTotalAdult $txtAdults, $mTotalInfant $txtInfants"
        } else if (mTotalAdult == 1 && mTotalInfant == 0 && mTotalChild == 1) {
            text = "$mTotalAdult $txtAdult, $mTotalChild $txtChild"
        } else if (mTotalAdult > 1 && mTotalInfant == 0 && mTotalChild == 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChild"
        } else if (mTotalAdult > 1 && mTotalInfant == 0 && mTotalChild > 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChildren"
        } else if (mTotalAdult == 1 && mTotalInfant == 0 && mTotalChild == 0) {
            text = "$mTotalAdult $txtAdult "
        } else if (mTotalAdult > 1 && mTotalInfant == 0 && mTotalChild == 0) {
            text = "$mTotalAdult $txtAdults "
        } else if (mTotalAdult > 1 && mTotalChild > 1 && mTotalInfant > 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChildren, $mTotalInfant $txtInfants"
        } else if (mTotalAdult > 1 && mTotalChild > 1 && mTotalInfant == 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChildren, $mTotalInfant $txtInfant"
        } else if (mTotalAdult > 1 && mTotalChild == 1 && mTotalInfant > 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChild, $mTotalInfant $txtInfants"
        } else if (mTotalAdult > 1 && mTotalChild == 1 && mTotalInfant == 1) {
            text = "$mTotalAdult $txtAdults, $mTotalChild $txtChild, $mTotalInfant $txtInfant"
        }

        tv_passanger_new.text = text

        totalAdult = mTotalAdult
        totalInfant = mTotalInfant
        totalChild = mTotalChild
    }

    override fun select(selectedClass: String) {
        tv_airline_class_new.text = selectedClass
        nameClassAirline = selectedClass
    }
}