package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.flight.adapter.FlightMultiAdapter
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.unicode.kingmarket.Base.BaseFragment
import kotlinx.android.synthetic.main.flight_fragment_2.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

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
    var SELECT_CODE_COUNTRY_FROM = 28
    var SELECT_CODE_COUNTRY_TO = 26

    var startDate     = ""
    var endDate       = ""
    var dataTripPlan = SuccessCreateTripPlaneModel()
    var originName   = ""
    var idOrigin        = ""

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        dataTripPlan = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP,SuccessCreateTripPlaneModel::class.java)

        setOnClick()
        initRecycleView()
        setData()
        setDateDefault()
    }

    private fun setDateDefault() {
        Globals.ALL_READY_SELECT_DEPARTING = false

        if (Constants.DATA_SUCCESS_CREATE_TRIP.isNotEmpty()){
            val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP, SuccessCreateTripPlaneModel::class.java)
            startDate(DateConverter().getDate(data.startDate,"yyyy-MM-dd","dd MMM yyyy"),data.startDate)
        } else {
            startDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
        }
    }

    private fun setData() {
        val orderFlight = OrderAccomodationModel()
        mFlightMulti.add(orderFlight)
        adapter.setData(mFlightMulti)
        adapter.setOnclickListener(this)
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
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.REQUEST_CODE_SELECT_FROM_MULTI -> {
                openCity(position)
            }
            Constants.REQUEST_CODE_SELECT_TO_MULTI -> {
                openCity(position)
            }
            Constants.REQUEST_CODE_SELECT_DEPARTURE -> {
                selectDate(position)
            }
            Constants.REQUEST_CODE_DELETE_MULTI -> {
                mFlightMulti.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            Constants.REQUEST_CODE_SWITCH_DATA -> {
                if(mFlightMulti[position].originName.isNotEmpty()){
                    val currentOrigin = mFlightMulti[position].originName
                    mFlightMulti[position].originName = mFlightMulti[position].destinationName
                    mFlightMulti[position].destinationName = currentOrigin
                }
            }
        }
    }

    private fun selectDate(position: Int) {
        if (Globals.ONE_TRIP){
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",dataTripPlan.startDate,dataTripPlan.endDate, Constant.SINGGLE_SELECTED)
        }
        else{
            NewCalendarViewOpsicorp().showCalendarViewMinMax(activity!!,"yyyy-MM-dd",dataTripPlan.startDate,dataTripPlan.endDate, Constant.DOUBLE_SELECTED)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            btAddOtherFlight -> {
                addOtherFlight()
            }
        }

    }

    private fun addOtherFlight() {
        setData()
    }

    override fun btnLeft() {
        Globals.ONE_TRIP = true
        typeTrip  = "round_trip"
    }

    override fun btnRight() {
        Globals.ONE_TRIP = false
        typeTrip  = "multi_city"
    }

    override fun onClicked() {
    }

    override fun startDate(displayStartDate: String, startDate: String) {

    }

    override fun endDate(displayEndDate: String, endDate: String) {

    }

    override fun canceledCalendar() {

    }



    private fun openCity(position: Int) {
        val bundle = Bundle()
        bundle.putString("emplaoyId","city")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("searchHint","Enter city or airport name")
        bundle.putString("titleHeader","Popular Cities and Airports")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_COUNTRY_TO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            SELECT_CODE_COUNTRY_FROM -> {
                if (resultCode== Activity.RESULT_OK){
                    originName  = data?.getStringExtra("nameCountry").toString()
                    idOrigin    = data?.getStringExtra("idCountry").toString()
                }
                else {

                }
            }
        }
    }
}