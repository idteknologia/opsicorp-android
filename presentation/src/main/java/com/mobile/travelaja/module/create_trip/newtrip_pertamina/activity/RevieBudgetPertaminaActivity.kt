package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityReviewBudgetBinding
import com.mobile.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.Itinerary
import com.mobile.travelaja.module.create_trip.success_create_trip.SucessCreateTripPlaneActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_review_budget.*
import kotlinx.android.synthetic.main.detail_price_bottom.*
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.*
import opsigo.com.domainlayer.callback.CallbackCostCenter
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.model.CostCenterModel
import opsigo.com.domainlayer.model.create_trip_plane.ParticipantPertamina
import opsigo.com.domainlayer.model.create_trip_plane.RoutesItinerary
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import java.util.*
import kotlin.collections.ArrayList


class RevieBudgetPertaminaActivity : BaseActivityBinding<ActivityReviewBudgetBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, View.OnClickListener {

    override fun bindLayout(): ActivityReviewBudgetBinding {
        return ActivityReviewBudgetBinding.inflate(layoutInflater)
    }

    lateinit var dataTrip: DataBisnisTripModel
    lateinit var dataCost : EstimatedCostTravelRequestModel
    var tripRoute = ""
    var originName = ""
    var destinationName = ""
    var costCenterName = ""
    var cashAdvanceValue = 0
    var costCenterOther = false
    var isCashAdvance = false


    override fun onMain() {
        getDataIntent()
        initOnClick()
        /*getDataSelectCostCenter()*/
        costCenterName = getProfile().costCenter
        title_cost_name.setText(costCenterName)
        postDataForEstimateCost()
        tv_title_prize.text = getString(R.string.total_estimated_cost)
        tv_including.text = getString(R.string.exclude_cash_advance)
        tv_price.gone()
    }

    private fun postDataForEstimateCost() {
        showDialog(getString(R.string.please_wait))
        GetDataTravelRequest(getBaseUrl()).getEstimatedCost(Globals.getToken(), dataPurpose(), object : CallbackEstimatedCostTravelRequest {
            override fun successLoad(data: EstimatedCostTravelRequestModel) {
                dataCost = data
                iniPrice(data)

                val participanItem = ParticipantPertamina()
                participanItem.employeeId = getProfile().employId
                participanItem.useCostCenterOther = costCenterOther
                participanItem.useCashAdvance = isCashAdvance
                participanItem.cashAdvance = cashAdvanceValue
                participanItem.costCenterCode = costCenterName
                participanItem.estFlight = dataCost.estFlight.toInt()
                participanItem.estTransportation = dataCost.estTransportation.toInt()
                participanItem.estTotal = dataCost.total.toInt()
                participanItem.estAllowance = dataCost.estAllowance.toInt()
                participanItem.estAllowanceEvent = dataCost.estAllowanceEvent.toInt()
                participanItem.estLaundry = dataCost.estLaundry.toInt()
                participanItem.estHotel = dataCost.estHotel.toInt()
                dataTrip.participant.add(participanItem)
                hideDialog()
            }

            override fun failedLoad(message: String) {
            }

        })
    }

    private fun iniPrice(data: EstimatedCostTravelRequestModel) {
        tv_price.visible()
        tv_price.text = StringUtils().setCurrency("IDR", data.total, false)
        tv_price_flight.text = StringUtils().setCurrency("IDR", data.estFlight, false)
        tv_price_hotel.text = StringUtils().setCurrency("IDR", data.estHotel, false)
        tv_allowance_price.text = StringUtils().setCurrency("IDR", data.estAllowance, false)
        tv_allowance_event_price.text = StringUtils().setCurrency("IDR", data.estAllowanceEvent, false)
        tv_transportation_price.text = StringUtils().setCurrency("IDR", data.estTransportation, false)
        tv_laundry_price.text = StringUtils().setCurrency("IDR", data.estLaundry, false)

        tv_price.setOnClickListener {
            showOrHideDetailPrice()
        }
        ic_image.setOnClickListener {
            showOrHideDetailPrice()
        }
        tv_title_prize.setOnClickListener {
            showOrHideDetailPrice()
        }
        line_shadow.setOnClickListener {
            showOrHideDetailPrice()
        }
    }

    private fun dataPurpose(): HashMap<Any, Any> {
        val dataRequest = RequestEstimatedCost()
        dataRequest.tripType = dataTrip.nameActivity
        dataRequest.purpose = dataTrip.namePusrpose
        dataRequest.startDate = dataTrip.startDate
        dataRequest.endDate = dataTrip.endDate

        dataRequest.routes = ArrayList()
        val mDataRoutes = ArrayList<RoutesItem>()
        dataTrip.routes.forEachIndexed { index, routesItinerary ->
            val dataRoutes = RoutesItem()
            dataRoutes.transportation = routesItinerary.Transportation
            dataRoutes.departureDate = routesItinerary.DepartureDateView
            dataRoutes.departureDateView = routesItinerary.DepartureDateView
            dataRoutes.origin   = routesItinerary.Origin
            dataRoutes.destination = routesItinerary.Destination
            mDataRoutes.add(dataRoutes)
        }
        dataRequest.routes = mDataRoutes

        dataRequest.isDomestic = !dataTrip.isInternational
        dataRequest.withPartner = dataTrip.isTripPartner

        return Globals.classToHashMap(dataRequest, RequestEstimatedCost::class.java)
    }

    private fun initOnClick() {
        btn_next.setTextButton("Next")
        btn_next.callbackOnclickButton(this)

        ic_back.setOnClickListener(this)
        tvCostNameAdd.setOnClickListener(this)
        title_cost_name.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    title_cost_name.isFocusable = false
                    val imm: InputMethodManager = getSystemService(
                            Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(title_cost_name.windowToken, 0)
                    return true
                }
                return false
            }
        })
    }

    private fun showOrHideDetailPrice() {
        if (body_prize.isExpanded) {
            collapsePrice()
        } else {
            expandPrice()
        }
    }

    private fun expandPrice() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_down))
        line_shadow.visibility = View.VISIBLE
        tv_including.visibility = View.GONE
        body_prize.expand()
    }

    private fun collapsePrice() {
        ic_image.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_chevron_up_orange))
        tv_including.visibility = View.VISIBLE
        line_shadow.visibility = View.GONE
        body_prize.collapse()
    }

    private fun getDataIntent() {
        val itineraries = intent.getParcelableArrayExtra("itineraries")
        dataTrip = Serializer.deserialize(intent?.getBundleExtra("data")?.getString("data_order"), DataBisnisTripModel::class.java)
        itineraries?.forEach {
            if (it is Itinerary){
                dataTrip.routes.add(RoutesItinerary(it.Transportation,it.DepartureDateView, it.Origin, it.Destination))
            }
        }
        setLog("Test Request",Serializer.serialize(dataTrip))

        tv_purpose.text = dataTrip.namePusrpose
        tv_activity.text = dataTrip.nameActivity
        if (dataTrip.isInternational.equals(true)){
            tripRoute = "International Route"
        } else {
            tripRoute = "Domestic Route"
        }
        originName = dataTrip.routes[0].Origin
        destinationName = dataTrip.routes[0].Destination
        if (dataTrip.routes.size >1){
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}-${dataTrip.routes[1].Destination}"
        } else if (dataTrip.routes.size >2){
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}-${dataTrip.routes[1].Destination}-${dataTrip.routes[2].Destination}"
        } else if (dataTrip.routes.size >3){
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}-${dataTrip.routes[1].Destination}-${dataTrip.routes[2].Destination}-${dataTrip.routes[3].Destination}"
        } else if (dataTrip.routes.size >4){
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}-${dataTrip.routes[1].Destination}-${dataTrip.routes[2].Destination}-${dataTrip.routes[3].Destination}-${dataTrip.routes[4].Destination}"
        } else if (dataTrip.routes.size >5){
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}-${dataTrip.routes[1].Destination}-${dataTrip.routes[2].Destination}-${dataTrip.routes[3].Destination}-${dataTrip.routes[4].Destination}-${dataTrip.routes[5].Destination}"
        } else {
            tv_trip_route.text = "${dataTrip.routes[0].Origin}-${dataTrip.routes[0].Destination}"
        }
        tv_start_date.text = DateConverter().getDate(dataTrip.startDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")
        tv_date_end.text = DateConverter().getDate(dataTrip.endDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")
    }

    private fun getDataSelectCostCenter() {
        val codeSelectBudget = getProfile().costCenter
        GetDataTripPlane(getBaseUrl()).getDataCostCenter(Globals.getToken(), getProfile().employId, codeSelectBudget, object : CallbackCostCenter {
            override fun successLoad(approvalModel: ArrayList<CostCenterModel>) {

                DataTemporary.dataCostCenter.clear()
                approvalModel.forEachIndexed { index, costCenterModel ->
                    val mData = SelectNationalModel()
                    mData.name = costCenterModel.code
                    mData.id = costCenterModel.idCost
                    DataTemporary.dataCostCenter.add(mData)
                }
                costCenterName = approvalModel[0].code
                title_cost_name.setText(costCenterName)
            }

            override fun failedLoad(message: String) {

                Globals.showAlert(getString(R.string.failed), message, this@RevieBudgetPertaminaActivity)
            }
        })

    }

    override fun onClicked() {
        /*saveAsDraft()*/
        succesCreateTrip()
    }

    private fun succesCreateTrip() {
        Constants.DATA_CREATE_TRIP = Serializer.serialize(dataTrip,DataBisnisTripModel::class.java)
        setLog("Test Save",Serializer.serialize(dataTrip))
        val bundle = Bundle()
        gotoActivity(SucessCreateTripPlaneActivity::class.java)
    }

    override fun onClick(v: View?) {
        when (v) {
            ic_back -> {
                onBackPressed()
            }
            tvCostNameAdd -> {
                title_cost_name.isFocusableInTouchMode = true
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                costCenterName = title_cost_name.text.toString()
                costCenterOther = true
            }
        }

    }
}