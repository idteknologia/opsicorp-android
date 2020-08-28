package com.opsigo.travelaja.module.accomodation.train.search

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import com.opsigo.travelaja.module.login.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.button_swicth.ButtonSwicth
import opsigo.com.domainlayer.model.DestinationAccomodationModel
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackReasonCode
import kotlinx.android.synthetic.main.train_fragment.*
import com.opsigo.travelaja.utility.DateConverter
import com.unicode.kingmarket.Base.BaseFragment
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import android.view.View
import android.os.Bundle
import com.opsigo.travelaja.module.accomodation.train.result.ResultSearchTrainActivity

class TrainFragment : BaseFragment(), CalendarViewOpsicorp.CallbackResult,
        View.OnClickListener, ButtonTopRoundedOpsicorp.OnclickButtonListener,
        ButtonSwicth.OnclickButtonSwitch,
        ButtonDefaultOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.train_fragment }
    var typeTrip = ""

    val SELECT_CODE_TRAIN_STATION = 78
    var typeSelectStation         = "from"
    var idDestination = ""
    var idOrigin      = ""
    var startDate     = ""
    var endDate       = ""

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        top_button.callbackOnclickToolbar(this)
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton("Search Train")
        btn_switch.callbackOnclickButtonSwicht(this)
        btn_switch.setItemSwicth(tv_from,tv_to)
        tv_departur_date.setOnClickListener(this)
        tv_end_date.setOnClickListener(this)
        tv_from.setOnClickListener(this)
        tv_to.setOnClickListener(this)

        checkTypeOrder()
        setDateDefault()
        getReasonCode()
        typeTrip  = "round_trip"
    }

    fun checkTypeOrder() {

        if (Globals.BisnisTrip){
            lay_parent_passager.visibility = View.GONE
        }
        else{
            lay_parent_passager.visibility = View.VISIBLE
        }
    }

    fun setDateDefault() {
        Globals.ALL_READY_SELECT_DEPARTING = false

        val stationTrain = ArrayList<DestinationAccomodationModel>()
        stationTrain.clear()
        stationTrain.addAll(Constants.DATA_TRAIN_STASION)

        val queryOrigin = stationTrain.filter { it.code=="BD" }.first()
        val queryDestination = stationTrain.filter { it.code=="GMR" }.first()
        tv_from.text  = queryOrigin.nameCity
        tv_to.text    = queryDestination.nameCity
        idOrigin      = queryOrigin.code
        idDestination = queryDestination.code

        if (Constants.DATA_SUCCESS_CREATE_TRIP.isNotEmpty()){
            val data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP,SuccessCreateTripPlaneModel::class.java)
            startDate(DateConverter().getDate(data.startDate,"yyyy-MM-dd","dd MMM yyyy"),data.startDate)
            endDate(DateConverter().getDate(data.endDate,"yyyy-MM-dd","dd MMM yyyy"),data.endDate)
        }
        else{
            startDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())
            endDate(DateConverter().getDayFormatOpsicorp(), DateConverter().getDayFormatOpsicorp2())

        }
    }

    override fun btnLeft() {
        Globals.ONE_TRIP = false
        typeTrip  = "one_trip"
        lay_return_date.visibility = View.VISIBLE
    }

    override fun btnRight() {
        typeTrip  = "round_trip"
        Globals.ONE_TRIP = true
        lay_return_date.visibility = View.GONE
    }

    override fun onSwitch() {
        val switch = idDestination
        idDestination = idOrigin
        idOrigin      = switch
    }

    override fun onClicked() {
        gotoSearchTrain()
    }

    private fun gotoSearchTrain() {
        val dataOrder = OrderAccomodationModel()
        dataOrder.typeTrip        = typeTrip
        dataOrder.idOrigin        = idOrigin
        dataOrder.idDestination   = idDestination
        dataOrder.dateDeparture   = startDate
        dataOrder.dateArrival     = endDate
        dataOrder.originStationName      = tv_from.text.toString()
        dataOrder.destinationStationName = tv_to.text.toString()
        dataOrder.totalPassagerString = tv_passanger.text.toString()
        Constants.tipeTrip       = typeTrip
        setLog("typeee "+typeTrip)
//        dataOrder.airlinePreferance = tv_airline_prreferance.text.toString()

        Globals.typeAccomodation = "Train"
        Constants.DATA_ORDER_TRAIN = Serializer.serialize(dataOrder,OrderAccomodationModel::class.java)

        //clear data list order dummy
        Constants.DATA_LIST_TRAIN   = ""
//        setLog(Globals.DATA_ORDER_TRAIN)
        gotoActivity(ResultSearchTrainActivity::class.java)
    }

    /*private fun checkData() {
        val data = Serializer.deserialize(Globals.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)

        setLog(" ==============:: ")
        setLog("start destination")
        setLog(idOrigin)
        setLog(idDestination)
        setLog("start origin")
        setLog(data.dateArrival)
        setLog("start date")
        setLog(data.dateDeparture)

        setLog(Globals.DATA_ORDER_TRAIN)
    }
*/
    override fun startDate(displayStartDate: String, startDate: String) {
        tv_departur_date.text = displayStartDate
        this.startDate = startDate
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        tv_end_date.text = displayEndDate
        this.endDate          = endDate
    }


    override fun canceledCalendar() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

        when(requestCode){
            SELECT_CODE_TRAIN_STATION -> {
                if (resultCode== Activity.RESULT_OK){
                    if (typeSelectStation.equals("from")){
                        tv_from.text = data?.getStringExtra("nameCountry")
                        idOrigin     = data?.getStringExtra("idCountry").toString()
                    }
                    else {
                        tv_to.text  = data?.getStringExtra("nameCountry")
                        idDestination = data?.getStringExtra("idCountry").toString()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            tv_departur_date -> {
                CalendarViewOpsicorp().showCalendarView(activity!!,"yyyy-MM-dd",startDate,endDate)
            }
            tv_end_date -> {
                CalendarViewOpsicorp().showCalendarView(activity!!,"yyyy-MM-dd",startDate,endDate)
            }
            tv_from -> {
                typeSelectStation = "from"
                selectStattionTrain()
            }
            tv_to -> {
                typeSelectStation = "to"
                selectStattionTrain()
            }
        }
    }

    fun selectStattionTrain(){
        val bundle = Bundle()
        bundle.putString("emplaoyId","train_station")
        bundle.putString("invisibleSearch","yes")
        bundle.putString("titleHeader","Train Station")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_TRAIN_STATION)

    }

    fun getReasonCode(){
        GetDataAccomodation(getBaseUrl()).getReasonCodeTrain(getToken(),Constants.TripType.KAI.toString(),object : CallbackReasonCode {
            override fun success(reasonCodeModel: ArrayList<ReasonCodeModel>) {
                Constants.DATA_REASON_CODE_TRAIN = reasonCodeModel
            }

            override fun failed(string: String) {

            }
        })
    }




}