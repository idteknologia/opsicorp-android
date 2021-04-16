package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.train

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.button_top.ButtonTopRoundedOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.select_passager.SelectOldPassager
import com.opsigo.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.opsigo.travelaja.module.item_custom.button_swicth.ButtonSwicth
import kotlinx.android.synthetic.main.train_fragment.lay_parent_passager
import kotlinx.android.synthetic.main.train_fragment.tv_departur_date
import kotlinx.android.synthetic.main.train_fragment.lay_return_date
import kotlinx.android.synthetic.main.train_fragment.tv_passanger
import kotlinx.android.synthetic.main.train_fragment.tv_end_date
import opsigo.com.domainlayer.model.DestinationAccomodationModel
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel
import kotlinx.android.synthetic.main.train_fragment.btn_switch
import kotlinx.android.synthetic.main.train_fragment.top_button
import kotlinx.android.synthetic.main.train_fragment.btn_next
import kotlinx.android.synthetic.main.train_fragment.tv_from
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import kotlinx.android.synthetic.main.train_fragment.tv_to
import opsigo.com.domainlayer.callback.CallbackReasonCode
import kotlinx.android.synthetic.main.train_fragment.*
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.base.BaseFragment
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import android.content.Intent
import com.opsigo.travelaja.R
import android.app.Activity
import android.view.View
import android.os.Bundle

class TrainFragment : BaseFragment(), NewCalendarViewOpsicorp.CallbackResult,
        View.OnClickListener, ButtonTopRoundedOpsicorp.OnclickButtonListener,
        ButtonSwicth.OnclickButtonSwitch,
        ButtonDefaultOpsicorp.OnclickButtonListener,
        SelectOldPassager.CallbackSelectPasanger {

    override fun getLayout(): Int { return R.layout.train_fragment }
    var typeTrip = ""

    val SELECT_CODE_TRAIN_STATION = 78
    var typeSelectStation         = "from"
    var idDestination = ""
    var idOrigin      = ""
    var startDate     = ""
    var endDate       = ""

    var totalAdult  = 0
    var totalInfant = 0
    var totalChild  = 0


    lateinit var data: SuccessCreateTripPlaneModel

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
        btn_passenger.setOnClickListener(this)

        checkTypeOrder()
        setDateDefault()
        getReasonCode()
        typeTrip  = "round_trip"
    }

    fun checkTypeOrder() {

        if (Constants.isBisnisTrip){
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
        setLog(Serializer.serialize(stationTrain))
        val queryOrigin = stationTrain.filter { it.code=="BD" }.first()
        val queryDestination = stationTrain.filter { it.code=="GMR" }.first()
        tv_from.text  = queryOrigin.nameCity
        tv_to.text    = queryDestination.nameCity
        idOrigin      = queryOrigin.code
        idDestination = queryDestination.code

        if (Constants.isBisnisTrip){
            data = Serializer.deserialize(Constants.DATA_SUCCESS_CREATE_TRIP,SuccessCreateTripPlaneModel::class.java)
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
        dataOrder.totalPassengerString = tv_passanger.text.toString()
        dataOrder.adult           = totalAdult
        dataOrder.child           = totalChild
        dataOrder.infant          = totalInfant

        Constants.DATA_ORDER_TRAIN = Serializer.serialize(dataOrder,OrderAccomodationModel::class.java)

        Globals.typeAccomodation    = "Train"
        Constants.DATA_LIST_TRAIN   = ""
        gotoActivityModule(requireContext(),Constants.BASE_PACKAGE_TRAIN+"result.ResultSearchTrainActivity")
    }

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
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)

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
                openCalendar()
            }
            tv_end_date -> {
                openCalendar()
            }
            tv_from -> {
                typeSelectStation = "from"
                selectStattionTrain()
            }
            tv_to -> {
                typeSelectStation = "to"
                selectStattionTrain()
            }
            btn_passenger -> {
                selectTotalPassenger()
            }
        }
    }

    private fun openCalendar() {
        if (Globals.ONE_TRIP){
            if(Constants.isBisnisTrip) {
                NewCalendarViewOpsicorp().showCalendarViewMinMax(requireActivity(),"yyyy-MM-dd",data.startDate,data.endDate, Constant.SINGGLE_SELECTED)
            }
            else {
                NewCalendarViewOpsicorp().showCalendarView(requireActivity(),"yyyy-MM-dd",this.startDate,this.endDate, Constant.SINGGLE_SELECTED)
            }
        }
        else{
            if(Constants.isBisnisTrip) {
                NewCalendarViewOpsicorp().showCalendarViewMinMax(requireActivity(),"yyyy-MM-dd",data.startDate,data.endDate, Constant.DOUBLE_SELECTED)
            }
            else {
                NewCalendarViewOpsicorp().showCalendarView(requireActivity(),"yyyy-MM-dd",this.startDate,this.endDate, Constant.DOUBLE_SELECTED)
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

    fun selectTotalPassenger(){
        val fm = requireActivity().getSupportFragmentManager()
        var selectPassager = SelectOldPassager(true,R.style.CustomDialog,false)
        selectPassager.show(fm, "yesNoAlert")
        selectPassager.callback = this
        selectPassager.setLimitSelect(4,3,2)
    }

    override fun total(totalInfant: Int, totalChild: Int, totalAdult: Int) {
        if (totalAdult>0&&totalInfant>0&&totalChild>0){
            tv_passanger.setText("${totalAdult} Adults ${totalChild} Child ${totalInfant} Infant")
        }
        else if(totalAdult>0&&totalInfant>0&&totalChild==0){
            tv_passanger.setText("${totalAdult} Adults ${totalInfant} Infant")
        }
        else if(totalAdult>0&&totalInfant==0&&totalChild>0){
            tv_passanger.setText("${totalAdult} Adults ${totalChild} Child")
        }
        else if(totalAdult>0&&totalInfant==0&&totalChild==0){
            tv_passanger.setText("${totalAdult} Adults ")
        }

        this.totalAdult = totalAdult
        this.totalInfant = totalInfant
        this.totalChild = totalChild
    }

}