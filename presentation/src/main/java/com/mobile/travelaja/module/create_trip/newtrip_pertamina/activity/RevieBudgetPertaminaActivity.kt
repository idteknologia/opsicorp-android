package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityReviewBudgetBinding
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel.Itinerary
import com.mobile.travelaja.module.create_trip.success_create_trip.SucessCreateTripPlaneActivity
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_new_createtripplan.*
import kotlinx.android.synthetic.main.activity_review_budget.*
import kotlinx.android.synthetic.main.activity_review_budget.ic_back
import kotlinx.android.synthetic.main.detail_price_bottom.*
import kotlinx.android.synthetic.main.detail_price_bottom.btn_next
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.create_trip_plane.*
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.callback.CallbackSaveAsDraft
import opsigo.com.domainlayer.model.create_trip_plane.ParticipantPertamina
import opsigo.com.domainlayer.model.create_trip_plane.RoutesItinerary
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel
import opsigo.com.domainlayer.model.travel_request.CashAdvanceModel
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class RevieBudgetPertaminaActivity : BaseActivityBinding<ActivityReviewBudgetBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, View.OnClickListener {

    override fun bindLayout(): ActivityReviewBudgetBinding {
        return ActivityReviewBudgetBinding.inflate(layoutInflater)
    }

    lateinit var dataTrip: DataBisnisTripModel
    lateinit var dataCost: EstimatedCostTravelRequestModel
    lateinit var dataCashAdvance: CashAdvanceModel
    var tripRoute = ""
    var originName = ""
    var destinationName = ""
    var costCenterName = ""
    var picCostCenter = ""
    var cashAdvanceValue = 0
    var cashAdvanceValueLimit = 0
    var costCenterOther = false
    var isCashAdvance = false
    var picCostCentreEmpty = true
    var bankTransferEmpty = true


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
                hideDialog()
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
            dataRoutes.departureDateView = DateConverter().getDate(routesItinerary.DepartureDateView, "dd MMM yyyy", "dd-MM-yyyy")
            dataRoutes.origin = routesItinerary.Origin
            dataRoutes.destination = routesItinerary.Destination
            mDataRoutes.add(dataRoutes)
        }
        dataRequest.routes = mDataRoutes

        dataRequest.isDomestic = !dataTrip.isInternational
        dataRequest.withPartner = dataTrip.isTripPartner

        return Globals.classToHashMap(dataRequest, RequestEstimatedCost::class.java)
    }

    private fun initOnClick() {
        btn_next.setTextButton("Submit")
        btn_next.callbackOnclickButton(this)

        ic_back.setOnClickListener(this)
        tvCostNameAdd.setOnClickListener(this)
        tvCostNameReset.setOnClickListener(this)
        et_pic.setOnClickListener(this)
        et_min.setOnClickListener(this)
        etBank.setOnClickListener(this)
        btn_switch_cash_advance.setOnClickListener(this)
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
        et_min.addTextChangedListener(object : TextWatcher {
            var textValue = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    if (!s.isNullOrEmpty()) {
                        val sEdit = s.toString().replace(".","")
                        val value = sEdit.toInt()
                        cashAdvanceValue = value
                        textValue = Globals.formatCurrency(value)
                        if (value > cashAdvanceValueLimit) {
                            Globals.showAlert(getString(R.string.sorry), getString(R.string.limit_cash_advance), this@RevieBudgetPertaminaActivity)
                            tv_max_amount.visible()
                            tv_max_amount.text = "Max.${(Globals.formatAmount(dataCashAdvance.maxAmount))}"
                            checkEmptyField(false)
                        } else {
                            tv_max_amount.gone()
                            checkEmptyField(true)
                        }
                    } else {
                        cashAdvanceValue = 0
                        textValue = ""
                    }
                }
                catch (e:Exception){
                    e.printStackTrace()
                }


            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    et_min.removeTextChangedListener(this)
                    et_min.setText(textValue)
                    et_min.setSelection(textValue.length)
                    et_min.addTextChangedListener(this)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
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
            if (it is Itinerary) {
                dataTrip.routes.add(RoutesItinerary(it.Transportation, it.DepartureDateView, it.Origin, it.Destination))
            }
        }
        setLog("Test Request", Serializer.serialize(dataTrip))
        dataCashAdvance = Serializer.deserialize(Constants.DATA_CASH_ADVANCE, CashAdvanceModel::class.java)

        tv_purpose.text = dataTrip.namePusrpose
        tv_activity.text = dataTrip.nameActivity
        if (dataTrip.isInternational.equals(true)) {
            tripRoute = "International Route"
        } else {
            tripRoute = "Domestic Route"
        }
        originName = dataTrip.routes[0].Origin
        destinationName = dataTrip.routes[0].Destination
        if (dataTrip.routes.size == 1) {
            tv_trip_route.text = "${dataTrip.routes[0].Origin} - ${dataTrip.routes[0].Destination}"
        } else if (dataTrip.routes.size == 2) {
            tv_trip_route.text = "${dataTrip.routes[0].Origin} - ${dataTrip.routes[0].Destination} - ${dataTrip.routes[1].Destination}"
        } else if (dataTrip.routes.size == 3) {
            tv_trip_route.text = "${dataTrip.routes[0].Origin} - ${dataTrip.routes[0].Destination} - ${dataTrip.routes[1].Destination} - ${dataTrip.routes[2].Destination}"
        } else if (dataTrip.routes.size == 4) {
            tv_trip_route.text = "${dataTrip.routes[0].Origin} - ${dataTrip.routes[0].Destination} - ${dataTrip.routes[1].Destination} - ${dataTrip.routes[2].Destination} - ${dataTrip.routes[3].Destination}"
        } else if (dataTrip.routes.size == 5) {
            tv_trip_route.text = "${dataTrip.routes[0].Origin} - ${dataTrip.routes[0].Destination} - ${dataTrip.routes[1].Destination} - ${dataTrip.routes[2].Destination} - ${dataTrip.routes[3].Destination} - ${dataTrip.routes[4].Destination}"
        }
        tv_start_date.text = DateConverter().getDate(dataTrip.startDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")
        tv_date_end.text = DateConverter().getDate(dataTrip.endDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")

        if (dataCashAdvance.isAllowed.equals(true)) {
            llCashAdvanceToggle.visible()
            tv_currency.text = dataCashAdvance.currency
            cashAdvanceValueLimit = dataCashAdvance.maxAmount.toInt()
            /*et_min.hint = "Limit ${(Globals.formatAmount(dataCashAdvance.maxAmount))}"*/
        } else {
            llCashAdvanceToggle.gone()
        }
    }

    override fun onClicked() {
        checkData()
    }

    private fun checkData() {
        if (costCenterOther == true) {
            if (picCostCentreEmpty == true) {
                Globals.showAlert(getString(R.string.txt_please), getString(R.string.fill_your_pic_email), this)
            } else {
                succesCreateTrip()
            }
        } else if (dataCashAdvance.isAllowed.equals(true)) {
            if (cashAdvanceValue > cashAdvanceValueLimit) {
                Globals.showAlert(getString(R.string.sorry), getString(R.string.limit_cash_advance), this)
                tv_max_amount.visible()
                tv_max_amount.text = "Max.${(Globals.formatAmount(dataCashAdvance.maxAmount))}"
            } else {
                tv_max_amount.gone()
                succesCreateTrip()
            }
        } else {
            succesCreateTrip()
        }

    }

    fun checkEmptyField(enable : Boolean) {
        if (enable) {
            changeButtonNextOrangeColor()
        } else {
            changeButtonNextGrayColor()

        }
    }

    private fun changeButtonNextGrayColor() {
        btn_next.changeTextColorButton(R.color.gray_total)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_dark_select_budget)
        btn_next.isClickable = false
    }

    private fun changeButtonNextOrangeColor() {
        btn_next.changeTextColorButton(R.color.textButtonColor)
        btn_next.changeBackgroundDrawable(R.drawable.rounded_button_yellow)
        btn_next.isClickable = true
    }

    private fun succesCreateTrip() {
        showLoadingOpsicorp(true)
        picCostCenter = et_pic.text.toString()
        dataTrip.picCostCenter = picCostCenter
        GetDataTravelRequest(getBaseUrl()).submitTravelRequest(Globals.getToken(), dataRequest(), object : CallbackSaveAsDraft {
            override fun successLoad(data: SuccessCreateTripPlaneModel) {
                hideLoadingOpsicorp()
                data.originName = dataTrip.routes.first().Origin
                data.destinationName = tv_trip_route.text.toString()
                data.activityType = dataTrip.nameActivity
                data.isTripPartner = dataTrip.isTripPartner
                data.tripPartnerName = dataTrip.tripPartnerName
                data.tripType = tripRoute
                Constants.DATA_CREATE_TRIP = Serializer.serialize(data)
                setLog("Test Save", Serializer.serialize(Constants.DATA_CREATE_TRIP))
                gotoActivity(SucessCreateTripPlaneActivity::class.java)
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry), message)
            }

        })
    }

    private fun dataRequest(): HashMap<String, Any> {
        val dataRequest = SaveAsDraftRequestPertamina()
        dataRequest.origin = dataTrip.routes[0].Origin
        dataRequest.destination = dataTrip.routes[0].Destination
        dataRequest.purpose = dataTrip.namePusrpose
        dataRequest.businessTripType = dataTrip.nameActivity
        dataRequest.startDate = dataTrip.startDate
        dataRequest.returnDate = dataTrip.endDate
        dataRequest.type = Globals.getConfigCompany(this).travelingPurposeFormType.toInt()
        dataRequest.travelAgentAccount = Globals.getConfigCompany(this).defaultTravelAgent
        dataRequest.isDomestic = !dataTrip.isInternational
        dataRequest.remark = dataTrip.notes
        dataRequest.wbsNo = dataTrip.wbsNumber
        dataRequest.withPartner = dataTrip.isTripPartner
        dataRequest.partnerName = dataTrip.tripPartnerName
        dataRequest.isChangeTrip = dataTrip.isChangeTrip
        dataRequest.trnNumber = dataTrip.trnNumber
        dataRequest.tripCodeOld = dataTrip.tripCodeOld
        dataRequest.tripIdOld = dataTrip.tripIdOld

        dataRequest.routes = ArrayList()
        val mDataRoutes = ArrayList<RoutesItem>()
        dataTrip.routes.forEachIndexed { index, routesItinerary ->
            val dataRoutes = RoutesItem()
            dataRoutes.transportation = routesItinerary.Transportation
            dataRoutes.departureDate = DateConverter().getDate(routesItinerary.DepartureDateView, "dd MMM yyyy", "yyyy-MM-dd")
            dataRoutes.departureDateView = DateConverter().getDate(routesItinerary.DepartureDateView, "dd MMM yyyy", "dd-MM-yyyy")
            dataRoutes.origin = routesItinerary.Origin
            dataRoutes.destination = routesItinerary.Destination
            mDataRoutes.add(dataRoutes)
        }
        dataRequest.routes = mDataRoutes

        val attachments = ArrayList<TripAttachmentsItemRequest>()
        dataTrip.image.forEachIndexed { index, uploadModel ->
            val mDataAttachments = TripAttachmentsItemRequest()
            mDataAttachments.description = uploadModel.nameImage
            mDataAttachments.url = uploadModel.url
            attachments.add(mDataAttachments)
        }
        dataRequest.tripAttachments = attachments

        dataRequest.tripParticipants = ArrayList()
        val participants = ArrayList<TripParticipantsPertaminaItem>()
        val mDataParticipants = TripParticipantsPertaminaItem()
        mDataParticipants.employeeId = getProfile().employId
        mDataParticipants.useCostCenterOther = costCenterOther
        mDataParticipants.costCenterPicEmail = picCostCenter
        mDataParticipants.useCashAdvance = isCashAdvance
        mDataParticipants.cashAdvance = cashAdvanceValue
        mDataParticipants.cashAdvanceTransfer = etBank.text.toString()
        mDataParticipants.costCenterCode = dataTrip.participant[0].costCenterCode
        mDataParticipants.estFlight = dataTrip.participant[0].estFlight
        mDataParticipants.estTransportation = dataTrip.participant[0].estTransportation
        mDataParticipants.estTotal = dataTrip.participant[0].estTotal
        mDataParticipants.estAllowance = dataTrip.participant[0].estAllowance
        mDataParticipants.estAllowanceEvent = dataTrip.participant[0].estAllowanceEvent
        mDataParticipants.estLaundry = dataTrip.participant[0].estLaundry
        mDataParticipants.estHotel = dataTrip.participant[0].estHotel
        participants.add(mDataParticipants)
        dataRequest.tripParticipants = participants


        return Globals.classToHasMap(dataRequest, SaveAsDraftRequestPertamina::class.java)
    }

    override fun onClick(v: View?) {
        when (v) {
            ic_back -> {
                onBackPressed()
            }
            tvCostNameAdd -> {
                tvCostCenterTitle.text = "Please input new cost center"
                title_cost_name.text.clear()
                title_cost_name.hint = getProfile().costCenter
                title_cost_name.isFocusableInTouchMode = true
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                costCenterName = title_cost_name.text.toString()
                costCenterOther = true
                if (costCenterOther.equals(true)) {
                    tvCostNameReset.visible()
                    tvCostNameAdd.gone()
                    lineCash.visible()
                    layPIC.visible()
                } else {
                    tvCostNameReset.gone()
                    tvCostNameAdd.visible()
                    lineCash.gone()
                    layPIC.gone()
                }


            }
            tvCostNameReset -> {
                tvCostCenterTitle.text = "Cost Center"
                costCenterName = getProfile().costCenter
                title_cost_name.setText(costCenterName)
                costCenterOther = false
                tvCostNameReset.gone()
                tvCostNameAdd.visible()
                lineCash.gone()
                layPIC.gone()
            }
            et_pic -> {
                if (et_pic.text.isNotEmpty()) {
                    picCostCentreEmpty = false
                }
            }
            btn_switch_cash_advance -> {
                isCashAdvance = btn_switch_cash_advance.isChecked
                if (isCashAdvance.equals(true)){
                    rlCashAd.visible()
                } else {
                    rlCashAd.gone()
                }
            }
            et_min -> {
                if (et_min.text.isNotEmpty()) {
                    cashAdvanceValue = et_min.text.toString().replace(".","").toInt()
                } else {
                    cashAdvanceValue = 0
                }
            }
            etBank -> {
                gotoActivityResult(SelectBankTransferActivity::class.java, 1)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val id = data?.getStringExtra("keyBank")
        etBank.setText(id)
        bankTransferEmpty = false
    }
}