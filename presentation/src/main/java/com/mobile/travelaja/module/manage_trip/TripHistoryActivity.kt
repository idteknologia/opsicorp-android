package com.mobile.travelaja.module.manage_trip

import android.app.Activity
import android.content.Intent
import android.view.View
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityTripHistoryBinding
import com.mobile.travelaja.module.approval.activity.DetailTripActivity
import com.mobile.travelaja.module.manage_trip.adapter.TripHistoryAdapter
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.activity_trip_history.*
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.domainlayer.callback.CallbackListTripplan
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TripHistoryActivity : BaseActivityBinding<ActivityTripHistoryBinding>(),
        View.OnClickListener, OnclickListenerRecyclerView {

    val data = ArrayList<ApprovalModelAdapter>()
    val adapter by inject<TripHistoryAdapter> { parametersOf(data) }
    private var fromDate = ""
    private var toDate = ""
    private var key = ""

    override fun bindLayout(): ActivityTripHistoryBinding {
        return ActivityTripHistoryBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        ivBack.setOnClickListener(this)
        val bundle = intent.getBundleExtra("data")
        fromDate = bundle?.getString(TRIP_DATE_FROM) ?: ""
        toDate = bundle?.getString(TRIP_DATE_TO) ?: ""
        key = bundle?.getString(KEY) ?: ""
        getData(fromDate,toDate,key)

        setInitRecyclerView()
    }

    private fun setInitRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_history.layoutManager = layoutManager
        rv_history.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_history.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun getData(fromDate: String, toDate: String, key: String) {
        loading_view.show()
        data.clear()
        GetDataGeneral(Globals.getBaseUrl(this)).getListTripplan(Globals.getToken(), "40", "1", "Code","1",fromDate,toDate,key, object :
            CallbackListTripplan {
            override fun successLoad(approvalModel: ArrayList<ApprovalModelAdapter>) {
                loading_view.hide()
                data.addAll(approvalModel.filter { it.status == "Trip Completed" || it.status == "Completely Rejected" || it.status == "Canceled" || it.status == "Expired"})
                adapter.setData(data)
            }

            override fun failedLoad(message: String) {
                Globals.showAlert(this@TripHistoryActivity.getString(R.string.sorry),message,this@TripHistoryActivity)
            }

        })
    }

    override fun onClick(v: View?) {
        when(v){
            ivBack -> {
                onBackPressed()
            }
        }
    }

    companion object {
        const val TRIP_DATE_FROM = "SFROM"
        const val TRIP_DATE_TO = "STO"
        const val KEY = "KEY"
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1 -> {
                Constants.FROM_SUCCESS_CHECKOUT = false
                val intent = Intent(this, DetailTripActivity::class.java)

                intent.putExtra(Constants.KEY_FROM, Constants.FROM_DRAFT)
                intent.putExtra(Constants.KEY_INTENT_TRIPID, data[position].id)
                intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, data[position].tripCode)
                intent.putExtra(Constants.KEY_IS_APPROVAL,data[position].isApproval)
                intent.putExtra(Constants.KEY_IS_PARTICIPANT,data[position].isParticipant)

                (this as Activity).startActivityForResult(intent,Constants.OPEN_DETAIL_TRIP_PLANE)
            }
        }
    }
}