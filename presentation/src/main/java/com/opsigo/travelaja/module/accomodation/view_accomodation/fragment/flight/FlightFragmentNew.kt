package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight.adapter.FlightMultiAdapter
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.utility.*
import com.unicode.kingmarket.Base.BaseFragment
import kotlinx.android.synthetic.main.flight_fragment_2.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.signin.CountryModel
import org.json.JSONArray

class FlightFragmentNew : BaseFragment(),
        OnclickListenerRecyclerView,
        View.OnClickListener,
        ButtonTopRoundedOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        NewCalendarViewOpsicorp.CallbackResult {

    override fun getLayout(): Int {
        return R.layout.flight_fragment_2
    }

    var typeTrip = ""
    val adapter by lazy { FlightMultiAdapter() }
    var mFlightMulti = ArrayList<OrderAccomodationModel>()

    var startDate = ""
    var endDate = ""
    var dataTripPlan = SuccessCreateTripPlaneModel()
    var originName = ""
    var idOrigin = ""
    var destinationName = ""
    var idDestination = ""
    var currentPosition: Int = -1

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)

        setOnClick()
        initRecycleView()
        setData(true)
    }

    private fun setData(first: Boolean) {
        val orderFlight = OrderAccomodationModel()
        if (first){
            val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            orderFlight.dateDeparture = DateConverter().getDate(data.startDate, "yyyy-MM-dd","EEEE, dd MMM yyyy")
            orderFlight.originName = data.originName
            orderFlight.destinationName = data.destinationName
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
        btAddOtherFlight.setOnClickListener(this)
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
            }
        }

    }

    private fun addOtherFlight() {
        setData(false)
    }

    override fun btnLeft() {
        Globals.ONE_TRIP = true
        typeTrip = "round_trip"
        rvFlightMultiCity.gone()
        btAddOtherFlight.gone()
    }

    override fun btnRight() {
        Globals.ONE_TRIP = false
        typeTrip = "multi_city"
        rvFlightMultiCity.visible()
        btAddOtherFlight.visible()
    }

    override fun onClicked() {
        var isDataEmptyDestination = false
        var isDateEmptyOrigin = false
        var isDataEmptyDate = false
        var positionFlightEmpty = -1
        mFlightMulti.forEach {
            if (it.destinationName.isNotEmpty()){
                isDataEmptyDestination = true
                positionFlightEmpty = currentPosition+2
            }
            if (it.originName.isNotEmpty()){
                isDateEmptyOrigin = true
                positionFlightEmpty = currentPosition+2
            }
            if (it.dateDeparture.isNotEmpty()){
                isDataEmptyDate = true
                positionFlightEmpty = currentPosition+2
            }
        }
        if (isDataEmptyDestination && isDateEmptyOrigin && isDataEmptyDate){
            // error
            Globals.showAlert("sorry","Please Select Flight ${positionFlightEmpty}",context!!)
        } else {
            Constants.DATA_FLIGHT_MULTI_CITY.addAll(mFlightMulti)
            // gotoactivity
        }

    }

    override fun startDate(displayStartDate: String, startDate: String) {
        mFlightMulti[currentPosition].dateDeparture = DateConverter().getDate(startDate,"yyyy-MM-dd","EEEE, dd MMM yyyy")
        if(startDate.length > 10) {
            val sDate   = startDate.substring(0, 10)
            this.startDate = sDate
        }else {
            this.startDate = startDate
        }
        adapter.notifyDataSetChanged()
    }

    override fun endDate(displayEndDate: String, endDate: String) {
    }

    override fun canceledCalendar() {

    }


    private fun openCityTo(position: Int) {
        val bundle = Bundle()
        currentPosition = position
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Popular Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, Constants.REQUEST_CODE_SELECT_TO_MULTI)
    }

    private fun openCityFrom(position: Int) {
        val bundle = Bundle()
        currentPosition = position
        bundle.putString("emplaoyId", "city")
        bundle.putString("invisibleSearch", "yes")
        bundle.putString("searchHint", "Enter city or airport name")
        bundle.putString("titleHeader", "Popular Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java, bundle, Constants.REQUEST_CODE_SELECT_FROM_MULTI)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

        when (requestCode) {
            Constants.REQUEST_CODE_SELECT_FROM_MULTI -> {
                if (resultCode == Activity.RESULT_OK) {
                    mFlightMulti[currentPosition].originName = data?.getStringExtra("nameCountry").toString()
                    originName = data?.getStringExtra("nameCountry").toString()
                    idOrigin = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
            Constants.REQUEST_CODE_SELECT_TO_MULTI -> {
                if (resultCode==Activity.RESULT_OK){
                    mFlightMulti[currentPosition].destinationName = data?.getStringExtra("nameCountry").toString()
                    destinationName = data?.getStringExtra("nameCountry").toString()
                    idDestination   = data?.getStringExtra("idCountry").toString()
                    adapter.notifyDataSetChanged()
                }
                else {

                }
            }
        }
    }
}