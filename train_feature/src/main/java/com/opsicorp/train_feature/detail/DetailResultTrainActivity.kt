package com.opsicorp.train_feature.detail

import android.os.Build
import opsigo.com.datalayer.request_model.accomodation.train.validation.SegmentsItemValidationTrainRequest
import opsigo.com.datalayer.request_model.accomodation.train.validation.ContactValidationTrainRequest
import opsigo.com.datalayer.request_model.accomodation.train.validation.ValidationTrainRequest
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel
import opsigo.com.domainlayer.model.accomodation.train.ValidationTrainModel
import opsigo.com.domainlayer.callback.CallbackValidationTrain
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.BaseActivity
import java.text.SimpleDateFormat
import java.lang.Exception
import android.view.View
import com.opsicorp.train_feature.R
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.detail_train_result_activity.*

class DetailResultTrainActivity : BaseActivity(),ToolbarOpsicorp.OnclickButtonListener,ButtonDefaultOpsicorp.OnclickButtonListener,View.OnClickListener {

    override fun getLayout(): Int { return R.layout.detail_train_result_activity }

    override fun OnMain() {

        setLog("data train  "+Constants.DATA_LIST_TRAIN)
        initButtonNext()
        initToolbar()
        getValidationTrain()

    }

    private fun setDataDetail() {
//        _root_ide_package_.com.opsigo.opsicorp.utility.Constants.DATA_ORDER_TRAIN = "{\"airlinePreferance\":\"\",\"dateArrival\":\"2020-02-19\",\"dateDeparture\":\"2020-02-19\",\"destinationStattionName\":\"BANDUNG (BD)\",\"idDestination\":\"BD\",\"idOrigin\":\"GMR\",\"originStationName\":\"GAMBIR (GMR)\",\"totalPassagerString\":\"Adult x1\",\"totalPassangerInt\":\"1\",\"typeTrip\":\"one_trip\"}"

        val data      = Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java)
        val dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        val formatter = SimpleDateFormat("dd MMM")

        tv_time_departure.text  = data.timeDeparture
        tv_date_departure.text  = formatter.format(data.dateDeparture)
        tv_time_arrival.text    = data.timeArrifal
        tv_date_arrival.text    = formatter.format(data.dateArrival)

        if (Globals.ALL_READY_SELECT_DEPARTING){
            tv_name_city_departure.text    = dataOrder.destinationStationName
            tv_name_station_departure.text = dataOrder.destinationStationName.split(" ")[0]+ " Station"
            tv_name_city_arrival.text      = dataOrder.originStationName
            tv_name_station_arrival.text   = dataOrder.originStationName.split(" ")[0]+ " Station"

        }else{
            tv_name_city_departure.text    = dataOrder.originStationName
            tv_name_station_departure.text = dataOrder.originStationName.split(" ")[0]+ " Station"
            tv_name_city_arrival.text      = dataOrder.destinationStationName
            tv_name_station_arrival.text   = dataOrder.destinationStationName.split(" ")[0]+ " Station"
        }
        line_total_duration.text       = data.duration

        tv_title_train.text = data.titleTrain
        tv_class_type.text  = data.className
        tv_gerbong.text     = ""//data.carrierNumber
//        tv_total_passager.text = "Adult x"+dataOrder.totalPassagerString
        tv_total_prize.text    = "IDR "+Globals.formatAmount(data.price)
    }


    private fun initToolbar() {
        val dataOrder = Serializer.deserialize(Constants.DATA_ORDER_TRAIN, OrderAccomodationModel::class.java)
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        val dateDeparture = if (dataOrder.dateDeparture.contains(" ")) dataOrder.dateDeparture.split(" ")[0] else dataOrder.dateDeparture
        toolbar.setDoubleTitle("${dataOrder.originStationName} - ${dataOrder.destinationStationName}","${DateConverter().getDate(dateDeparture,"yyyy-MM-dd","EEE, dd MMM |")} 1 adult") //- 1 pax , ${dataOrder.dateArrival}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
    }

    private fun initButtonNext() {
        btn_next.callbackOnclickButton(this)
        if (Globals.ONE_TRIP){
            btn_next.setTextButton("Select Departing")
        }
        else{
            if (Globals.ALL_READY_SELECT_DEPARTING){
                btn_next.setTextButton("Select Returning")
            }
            else {
                btn_next.setTextButton("Select Departing ")
            }
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onClicked() {
        checkTotalTrip()
    }

    private fun checkTotalTrip() {
        if (Globals.ONE_TRIP){
            if (Globals.BisnisTrip){
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderTrainActivity::class.java)
            }
            else{
                saveDataListAccomodation()
                gotoActivity(ConfirmOrderTrainActivity::class.java)
            }
        }
        else{
            if (Globals.ALL_READY_SELECT_DEPARTING){
                saveDataListAccomodation()
                Globals.ALL_READY_SELECT_DEPARTING = false
                gotoActivity(ConfirmOrderTrainActivity::class.java)
                finish()
            }
            else{
                saveDataListAccomodation()
                Globals.ALL_READY_SELECT_DEPARTING = true
                finish()
            }

        }
    }


    private fun saveDataListAccomodation() {
        var datalist = Serializer.deserialize(Constants.DATA_LIST_TRAIN,DataListOrderAccomodation::class.java)
        try {
            datalist.dataTrain.add(Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java))
        }catch (e:Exception){
            datalist = DataListOrderAccomodation()
            datalist.dataTrain.add(Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java))
        }
        Constants.DATA_LIST_TRAIN = Serializer.serialize(datalist,DataListOrderAccomodation::class.java)

    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    fun getValidationTrain(){
        showLoadingOpsicorp(true)
        GetDataAccomodation(getBaseUrl()).getValidationTrain(Globals.getToken(),getDataTrain(),object : CallbackValidationTrain {
            override fun successLoad(data: ValidationTrainModel) {
                hideLoadingOpsicorp()
                val mData                     = Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java)

                mData.isSecuritySensitivity   = data.isSecuritySensitivity
                mData.descSecuritySensitivity = data.descSecuritySensitivity
                mData.isViolatedTrainRules    = data.isViolatedTrainRules
                mData.causeViolatedTrainRules = data.causeViolatedTrainRules

                Constants.DATA_TRAIN = Serializer.serialize(mData)

                setDataDetail()
                tv_price.text = "IDR "+Globals.formatAmount(Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java).price)
            }

            override fun failedLoad(message: String) {
                setLog(message)
            }
        })
    }

    private fun getDataTrain(): HashMap<Any, Any> {

        val data      = Serializer.deserialize(Constants.DATA_TRAIN, ResultListTrainModel::class.java)

        val model = ValidationTrainRequest()
        model.origin      = data.origin
        model.destination = data.destination
        model.levelJob    = getProfile().idPosition
        model.remarks     = ArrayList()
        model.flightType  = 0
        model.segments    = getListSegment(data)
        model.purpose     = "Meeting"
        model.members     = getDataMembers()
        model.contact     = getContact()

        setLog(Serializer.serialize(model,ValidationTrainRequest::class.java))

        return Globals.classToHashMap(model,ValidationTrainRequest::class.java)
    }

    private fun getContact(): ContactValidationTrainRequest {
        val contact = ContactValidationTrainRequest()
        contact.email     = getProfile().email
        contact.homePhone = getProfile().phone
        contact.firstName = getProfile().firstName
        contact.title     = getProfile().title
        contact.lastName  = getProfile().lastName
        contact.mobilePhone = getProfile().phone

        return contact
    }

    private fun getDataMembers(): List<String> {
        val members = ArrayList<String>()
        members.add(getProfile().employId)
        return members
    }

    private fun getListSegment(data: ResultListTrainModel): List<SegmentsItemValidationTrainRequest> {

        val listSegment = ArrayList<SegmentsItemValidationTrainRequest>()

        val segment = SegmentsItemValidationTrainRequest ()
        segment.origin      =  data.origin
        segment.subClass    =  data.subClass
        segment.destination =  data.destination
        segment.arriveTime  =  data.timeArrifal
        segment.arriveDate  =  data.dateArrivalString
        segment.departDate  =  data.dateDepartureString
        segment.departTime  =  data.timeDeparture
        segment.trainName   =  data.trainName
        segment.num         =  "0"
        segment.carrierNumber =  data.carrierNumber
        segment.classKey    =  data.classKey
        segment.clas        =  data.className
        segment.journeyCode =  data.journeyCode
        listSegment.add(segment)
        return listSegment
    }

}