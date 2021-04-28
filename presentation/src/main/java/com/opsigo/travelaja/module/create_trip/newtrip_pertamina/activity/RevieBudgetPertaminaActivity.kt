package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.BaseActivityBinding
import com.opsigo.travelaja.databinding.ActivityReviewBudgetBinding
import com.opsigo.travelaja.module.create_trip.newtrip.actvity.DataTemporary
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.Itinerary
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.*
import kotlinx.android.synthetic.main.activity_review_budget.*
import kotlinx.android.synthetic.main.detail_price_bottom.*
import opsigo.com.datalayer.datanetwork.GetDataTravelRequest
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.datanetwork.dummy.bisni_strip.DataBisnisTripModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.request_model.travel_request.EstimatedRequest
import opsigo.com.domainlayer.callback.CallbackCostCenter
import opsigo.com.domainlayer.callback.CallbackEstimatedCostTravelRequest
import opsigo.com.domainlayer.model.CostCenterModel
import opsigo.com.domainlayer.model.create_trip_plane.RoutesItinerary
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel
import java.util.HashMap

class RevieBudgetPertaminaActivity : BaseActivityBinding<ActivityReviewBudgetBinding>(),
        ButtonDefaultOpsicorp.OnclickButtonListener, View.OnClickListener {

    override fun bindLayout(): ActivityReviewBudgetBinding {
        return ActivityReviewBudgetBinding.inflate(layoutInflater)
    }

    lateinit var data: DataBisnisTripModel
    var codeSelectBudget = "BI000008"
    var costCenterName = ""


    override fun onMain() {
        getDataIntent()
        initOnClick()
        initPrice()
        getDataSelectCostCenter()
        postDataForEstimateCost()
        tv_title_prize.setText("Total Estimated Cost")
        tv_including.setText("Exclude Cash Advance")
        tv_price.gone()
    }

    private fun postDataForEstimateCost() {
        GetDataTravelRequest(getBaseUrl()).getEstimatedCost(Globals.getToken(), dataPurpose(), object : CallbackEstimatedCostTravelRequest {
            override fun successLoad(data: EstimatedCostTravelRequestModel) {
                tv_price.visible()
                tv_price.setText(StringUtils().setCurrency("IDR", data.total, false))
                tv_price_flight.setText(StringUtils().setCurrency("IDR", data.estFlight, false))
                tv_price_hotel.setText(StringUtils().setCurrency("IDR", data.estHotel, false))
                tv_allowance_price.setText(StringUtils().setCurrency("IDR", data.estAllowance, false))
                tv_allowance_event_price.setText(StringUtils().setCurrency("IDR", data.estAllowanceEvent, false))
                tv_transportation_price.setText(StringUtils().setCurrency("IDR", data.estTransportation, false))
                tv_laundry_price.setText(StringUtils().setCurrency("IDR", data.estLaundry, false))
            }

            override fun failedLoad(message: String) {

            }

        })
    }

    private fun dataPurpose(): HashMap<Any, Any> {
        /*val dataRequest = DataBisnisTripModel()

        return Globals.classToHashMap(dataRequest,DataBisnisTripModel::class.java)*/

        val dataDummy = "{\n" +
                "\t\"TripType\":\"Dinas non-residensial di luar tempat kedudukan\",\n" +
                "\t\"Purpose\":\"Koordinasi Eksternal\",\n" +
                "\t\"StartDate\":\"2021-05-19\",\n" +
                "\t\"EndDate\":\"2021-05-28\",\n" +
                "\t\"Golper\":2,\n" +
                "\t\"Routes\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"DepartureDateView\":\"19-05-2021\",\n" +
                "\t\t\t\"Origin\":\"Jakarta\",\n" +
                "\t\t\t\"Destination\":\"Surabaya\",\n" +
                "\t\t\t\"Transportation\":\"1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"DepartureDateView\":\"24-05-2021\",\n" +
                "\t\t\t\"Origin\":\"Surabaya\",\"\n" +
                "\t\t\tDestination\":\"Medan\",\n" +
                "\t\t\t\"Transportation\":\"2\"\n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"IsDomestic\":true,\n" +
                "\t\"WithPartner\":true\n" +
                "\t\n" +
                "}"
        val request = Serializer.deserialize(dataDummy, EstimatedRequest::class.java)
        return Globals.classToHashMap(request, EstimatedRequest::class.java)
    }

    private fun initOnClick() {
        btn_next.setTextButton("Continue")
        btn_next.callbackOnclickButton(this)

        ic_back.setOnClickListener(this)
        tvCostNameAdd.setOnClickListener(this)
    }

    private fun initPrice() {
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
        val itinerary = intent.getSerializableExtra("itinerary") as Itinerary?
        data = Serializer.deserialize(intent.getBundleExtra("data").getString("data_order"), DataBisnisTripModel::class.java)
        itinerary?.let {
            data.routes.add(RoutesItinerary(Transportation = itinerary.Transportation,
                    DepartureDateView = itinerary.DepartureDateView, Origin = itinerary.Origin,Destination = itinerary.Destination))
        }
        println(data)
        tv_purpose.text = data.namePusrpose
        tv_activity.text = data.nameActivity
        tv_trip_route.text = data.nameDestination
        tv_start_date.text = DateConverter().getDate(data.startDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")
        tv_date_end.text = DateConverter().getDate(data.endDate, "yyyy-MM-dd", "EEE, dd MMM yyyy")
    }

    private fun getDataSelectCostCenter() {
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

                Globals.showAlert("failed", message, this@RevieBudgetPertaminaActivity)
            }
        })

    }

    override fun onClicked() {

    }

    override fun onClick(v: View?) {
        when (v) {
            ic_back -> {
                onBackPressed()
            }
            tvCostNameAdd -> {
                costCenterName = title_cost_name.text.toString()
            }
        }

    }
}