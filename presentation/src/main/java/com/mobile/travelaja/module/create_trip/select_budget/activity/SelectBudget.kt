package com.mobile.travelaja.module.create_trip.select_budget.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurpose
import com.mobile.travelaja.module.create_trip.success_create_trip.SucessCreateTripPlaneActivity
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.loading.DialogErrorConection
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.ContactRequest
import opsigo.com.datalayer.request_model.create_trip_plane.SaveAsDraftRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.create_trip_plane.TripParticipantsItem
import opsigo.com.domainlayer.callback.CallbackCostCenter
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.model.CostCenterModel
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import kotlinx.android.synthetic.main.select_budget_first.*
import opsigo.com.domainlayer.callback.CallbackDataAirport
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyAirportModel
import org.koin.core.KoinComponent
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.forEachIndexed


class SelectBudget : BaseActivity(),KoinComponent,ToolbarOpsicorp.OnclickButtonListener
        ,ButtonDefaultOpsicorp.OnclickButtonListener,
        DialogErrorConection.CallbackErrorDialog{


    override fun getLayout(): Int { return R.layout.select_budget_first }

    var SELECT_BUDGET = 97
    var SELECT_COST_CENTER = 78
    var budgetIsEmpty = true
    var costCenterIsEmpty = true
    var codeSelectBudget = ""
    lateinit var data: DataBisnisTripModel

    var airportData = ArrayList<NearbyAirportModel>()
    var idBudget = ""
    var idCostCenter = ""

    override fun OnMain() {

        getDataAirport()
        getDataFromIntent()
        btn_next.callbackOnclickButton(this)
        btn_next.setTextButton(resources.getString(R.string.manage_transport))
        toolbarView.setToolbarColorView(R.color.colorPureWhite)
        toolbarView.changeImageBtnBack(R.drawable.ic_back)
        toolbarView.callbackOnclickToolbar(this)
        toolbarView.hidenBtnCart()

        changeButtonNextGrayColor()
    }

    private fun getDataAirport() {
        airportData.clear()
        GetDataTripPlane(getBaseUrl()).getDataAiport(getToken(),object : CallbackDataAirport {
            override fun success(data: ArrayList<NearbyAirportModel>) {
                airportData.addAll(data)
                DataTemporary.dataAirport.clear()
                data.forEachIndexed { index, airportModel ->
                    val mData = SelectNationalModel()
                    mData.name = airportModel.nameAirport
                    mData.id   = airportModel.cityCode
                    DataTemporary.dataAirport.add(mData)
                }
            }

            override fun failed(message: String) {
                showAllert("Sorry",message)
            }
        })
    }

    private fun getDataSelectCostCenter() {
        GetDataTripPlane(getBaseUrl()).getDataCostCenter(Globals.getToken(),getProfile().employId,codeSelectBudget,object : CallbackCostCenter {
            override fun successLoad(approvalModel: ArrayList<CostCenterModel>) {

                DataTemporary.dataCostCenter.clear()
                approvalModel.forEachIndexed { index, costCenterModel ->
                    val mData = SelectNationalModel()
                    mData.name = Globals.formatAmount(costCenterModel.value)
                    mData.id   = costCenterModel.idCost
                    DataTemporary.dataCostCenter.add(mData)
                }
            }

            override fun failedLoad(message: String) {

                Globals.showAlert(getString(R.string.failed),message,this@SelectBudget)
            }
        })

    }

    private fun getDataFromIntent() {

        data = Serializer.deserialize(intent?.getBundleExtra("data")?.getString("data_order"), DataBisnisTripModel::class.java)

        tv_start_date.text  = DateConverter().getDate(data.startDate,"yyyy-MM-dd","EEE, dd MMM yyyy")
        tv_date_end.text    = DateConverter().getDate(data.endDate,"yyyy-MM-dd","EEE, dd MMM yyyy")
        tv_destination.text = data.nameDestination
        tv_purpose.text     = data.namePusrpose
        tv_trip_code.text   = data.tripcode
        tv_date_create_trip_plane.text = "${data.dateCreated}"

    }

    fun showDataBudget(view:View){
        val bundle = Bundle()
        bundle.putString("titleHeader","Select Budget")
        bundle.putString("emplaoyId","budget")
        gotoActivityResultWithBundle(DialogPurpose::class.java,bundle,SELECT_BUDGET)
    }

    fun showDataCostCenter(view: View){

        if (codeSelectBudget.isNotEmpty()){
            val bundle = Bundle()
            bundle.putString("titleHeader","Select Cost Center")
            bundle.putString("emplaoyId","cost_center")
            gotoActivityResultWithBundle(DialogPurpose::class.java,bundle,SELECT_COST_CENTER)
        }
        else{
            showAllert("Please","Select your budget")
        }
    }


    private fun changeButtonNextOrangeColor() {
        btn_next.changeTextColorButton(R.color.textButtonColor)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_yellow)
    }

    private fun changeButtonNextGrayColor() {
        btn_next.changeTextColorButton(R.color.gray_total)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_dark_select_budget)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
                SELECT_BUDGET -> {
                    if (resultCode==Activity.RESULT_OK){
                        budgetIsEmpty = false
                        img_budget.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down))
                        checkEmpetyField()
                        tv_budget.text = data?.getStringExtra("nameCountry")
                        idBudget       = data?.getStringExtra("idCountry").toString()
                        codeSelectBudget = data?.getStringExtra("nameCountry")?.split("-")?.get(0).toString()
                        getDataSelectCostCenter()
                    }
                }
                SELECT_COST_CENTER ->{
                    if(resultCode==Activity.RESULT_OK){
                        costCenterIsEmpty = false
                        val amount = data?.getStringExtra("nameCountry")
                        idCostCenter = data?.getStringExtra("idCountry").toString()
                        img_cost_center.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down))
                        checkEmpetyField()
                        tv_cost_center.text = amount.toString()
                        tv_cost_avaibility.text = "IDR ${amount.toString()}"
                    }
                }
        }
    }

    fun checkEmpetyField(){
        if (!budgetIsEmpty&&!costCenterIsEmpty){
            changeButtonNextOrangeColor()
        }
        else{
            changeButtonNextGrayColor()
        }
    }

    override fun onClicked() {
        if (budgetIsEmpty&&costCenterIsEmpty){
            Globals.showAlert(getString(R.string.sorry),getString(R.string.please_select_budget_and_cost_center),this)
        } else if(!budgetIsEmpty&&!costCenterIsEmpty){
            saveAsDraft()
        }
        else if (budgetIsEmpty){
            Globals.showAlert(getString(R.string.sorry),getString(R.string.please_select_budget_first),this)
        }
        else if(costCenterIsEmpty){
            Globals.showAlert(getString(R.string.sorry),getString(R.string.please_select_cost_center),this)
        }
    }

    private fun saveAsDraft() {
        setLog(dataRequest().toString())
        showLoadingOpsicorp(true)
        data.budget = tv_budget.text.toString()
        data.costCenter = tv_cost_center.text.toString().replace(".","").trim()
        Constants.DATA_CREATE_TRIP = Serializer.serialize(data,DataBisnisTripModel::class.java)
        GetDataTripPlane(getBaseUrl()).saveAsDraftTripPlant(Globals.getToken(),dataRequest(),object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                val bundle = Bundle()
                data.costCenter = idCostCenter
                data.buggetId   = idBudget
                data.destinationName = this@SelectBudget.data.nameDestination
                data.originName      = Globals.getConfigCompany(this@SelectBudget).defaultOrigin
                data.airport.addAll(airportData)
                Constants.DATA_SUCCESS_CREATE_TRIP = Serializer.serialize(data)
                gotoActivityWithBundle(SucessCreateTripPlaneActivity::class.java,bundle)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showDialogErrorOpsicorp(false,this@SelectBudget)
                showAllert(getString(R.string.sorry),message)
            }
        })

//            LoadingDialogSuccess().showDialogLoading(this)
//        Handler().postDelayed({
//            data.budget = tv_budget.text.toString()
//            data.costCenter = tv_cost_center.text.toString()
//            Globals.DATA_CREATE_TRIP = Serializer.serialize(data,DataBisnisTripModel::class.java)
//
//            val bundle = Bundle()
//            bundle.putString("emplaoyId","accomodation")
//            gotoActivityWithBundle(AccomodationActivity::class.java,bundle)
//        }, 3000)
    }

    private fun dataRequest(): HashMap<String, Any> {
        val dataRequest             = SaveAsDraftRequest()
        dataRequest.startDate       = data.startDate
        dataRequest.returnDate      = data.endDate
        dataRequest.remark          = data.notes
        dataRequest.origin          = Globals.getConfigCompany(this).defaultOrigin
        dataRequest.type            = Globals.getConfigCompany(this).travelingPurposeFormType
        dataRequest.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        dataRequest.destination     = data.nameDestination
        dataRequest.purpose         = data.namePusrpose

        dataRequest.tripAttachments = ArrayList()

        val contactPerson = ContactRequest()
        contactPerson.lastName  = getProfile().lastName
        contactPerson.firstName = getProfile().firstName
        dataRequest.contact         = contactPerson

        val attactmant = ArrayList<TripAttachmentsItemRequest>()
        data.image.forEachIndexed { index, uploadModel ->
            val mDataAttcmant = TripAttachmentsItemRequest()
            mDataAttcmant.description = uploadModel.nameImage
            mDataAttcmant.url         = uploadModel.url
            attactmant.add(mDataAttcmant)
        }
        dataRequest.tripAttachments = attactmant

        val tripParticipant = ArrayList<TripParticipantsItem>()
        val mDataParticipant = TripParticipantsItem()
        mDataParticipant.budgetId     = idBudget//"a52fbb50-aeac-4cf1-9deb-312ddd059ba1"
        mDataParticipant.costCenterId = idCostCenter//"cb2fa51e-efd1-4f66-86f2-d9171663b70d"
        mDataParticipant.employeeId   = getProfile().employId
        tripParticipant.add(mDataParticipant)
        dataRequest.tripParticipants   = tripParticipant

        /*"Type":2,
        "Purpose" : "Breafing",
        "StartDate" : "2018-08-06",
        "ReturnDate" : "2018-08-08",
        "TravelAgentAccount" : "apidev",
        "Origin" : "CGK",
        "Destination" : "DPS",

        {
            "EmployeeId" : "a52fbb50-aeac-4cf1-9deb-312ddd059ba1",
            "BudgetId":"cb2fa51e-efd1-4f66-86f2-d9171663b70d",
            "CostCenterId":"b4df3880-b1af-41af-89ce-a0d1b7eb5c3c"
        }*/

//        setLog(Serializer.serialize(dataRequest,SaveAsDraftRequest::class.java))
        return Globals.classToHasMap(dataRequest,SaveAsDraftRequest::class.java)
    }

    fun copyListener(view: View){
        Globals.copyText(tv_trip_code.text.toString(),this)
        setToast(getString(R.string.you_have_copied_the_trip_code))
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun gotoHomePage() {
        gotoActivity(HomeActivity::class.java)
        finish()
    }

}