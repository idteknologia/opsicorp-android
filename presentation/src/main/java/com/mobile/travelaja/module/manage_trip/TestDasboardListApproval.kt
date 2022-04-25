package com.mobile.travelaja.module.manage_trip

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mobile.travelaja.R

import com.mobile.travelaja.module.approval.activity.DetailTripActivity
import com.mobile.travelaja.module.item_custom.button_manage_trip.ButtonManageTrip
//import com.opsigo.opsicorp.module.item_custom.button_top2.ButtonManageTrip

import com.mobile.travelaja.module.manage_trip.adapter.ManageTripAdapter
import com.mobile.travelaja.utility.*
import com.mobile.travelaja.utility.Globals.getBaseUrl
import com.mobile.travelaja.utility.Globals.getToken
import com.mobile.travelaja.utility.Globals.setLog
import kotlinx.android.synthetic.main.list_manage_trip.view.*
import kotlinx.android.synthetic.main.list_manage_trip.view.top_button
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.request_model.ApprovalAllRequest
import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.callback.CallbackListTripplan
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.HashMap

class TestDasboardListApproval: LinearLayout, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        OnclickListenerRecyclerView,KoinComponent,
        ButtonManageTrip.OnclickButtonApproval2,
        View.OnClickListener {

    val data = ArrayList<ApprovalModelAdapter>()
    val dataFilter = ArrayList<ApprovalModelAdapter>()
    val dataSearch = ArrayList<ApprovalModelAdapter>()

    val adapter by inject<ManageTripAdapter> { parametersOf(data) }
    var itemSelected = false
    var positionPage = 0
    lateinit var callback : CallbackTestDassboardListApproval

    fun setInitCallback(callback:CallbackTestDassboardListApproval){
        this.callback = callback
    }

    interface CallbackTestDassboardListApproval{
        fun showDialog()
        fun hidenDialog()
        fun failedLoad(string: String,tripCode:String)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.list_manage_trip, this)
        setInitRecyclerView()
        setOnClikListener()
    }

    fun show(tripDateFrom: String,tripDateTo: String,position: Int,key:String){
        resetData()
        empty_view.hide()
        error_view.hide()
        positionPage = position
        getData(tripDateFrom,tripDateTo,key)
        visibility = View.VISIBLE
    }

    private fun resetData() {
        if (dataFilter.isNotEmpty()){
            dataFilter.clear()
            adapter.notifyDataSetChanged()
        }
        if (dataSearch.isNotEmpty()){
            dataSearch.clear()
            adapter.notifyDataSetChanged()
        }
        if (data.isNotEmpty()){
            data.clear()
            adapter.notifyDataSetChanged()
        }
    }

    fun gone(){
        visibility = View.GONE
    }

    private fun setOnClikListener() {
        top_button.callbackOnclickFilter(this)
    }


    private fun setTitleButton() {

        if (Globals.isPertamina(context)) {
            top_button.setTextBtnLeft("Approved")
            top_button.setTextBtnRight("Completed")
        } else {
            top_button.setTextBtnLeft("Draft")
            top_button.setTextBtnRight("Approved")
        }

    }

    private fun setInitRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context!!)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_waiting_approval.layoutManager = layoutManager
        rv_waiting_approval.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_waiting_approval.adapter = adapter

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv_waiting_approval)

        adapter.setOnclickListener(this)

        val scrollListener = RecyclerViewLoadMoreScrollListener(layoutManager)
        scrollListener.setOnLoadMoreListener { }

        rv_waiting_approval.addOnScrollListener(scrollListener);

    }

    fun getData(tripDateFrom:String,tripDateTo:String,key: String) {
        loading_view.show()
        data.clear()
        GetDataGeneral(getBaseUrl(context)).getListTripplan(getToken(), "40", "1", "Code","1",tripDateFrom,tripDateTo,key, object : CallbackListTripplan{
            override fun successLoad(approvalModel: ArrayList<ApprovalModelAdapter>) {
                loading_view.hide()
                /*if (Globals.isPertamina(context)) {
                    onCompleted()
                    top_button.completedButtonSelected()
                } else {
                    onDraft()
                    top_button.draftButtonSelected()
                }*/
                onDraft()
                top_button.draftButtonSelected()

                if (approvalModel.isEmpty()) {
                    empty_view.show()
                } else {
                    empty_view.hide()
                    error_view.hide()
                    data.addAll(approvalModel)
                    adapter.setData(data)

                    when(positionPage){
                        0 -> {
                            onDraft()
                            top_button.draftButtonSelected()
                        }
                        1 -> {
                            onCompleted()
                            top_button.completedButtonSelected()
                        }
                    }

                    if (key.isNotEmpty()){
                        searchData(key)
                    }
                }
                setTitleButton()
            }

            override fun failedLoad(message: String) {
                loading_view.hide()
//                callback.failedLoad()
                error_view.show()
                Globals.showAlert(context.getString(R.string.sorry),message,context)
                setTitleButton()
            }
        } )


    }




    override fun onClick(views: Int, position: Int) {
        when(views){
            -1 ->{
                if (itemSelected){
                    checkPositionPageSelected(position)
                }
                else{
                    Constants.FROM_SUCCESS_CHECKOUT = false
                    val intent = Intent(context, DetailTripActivity::class.java)

                    var idTripPlane = ""
                    var tripCode    = ""
                    var isApproval  = false
                    var isParticipant = false
                    if (dataFilter.isNotEmpty()){
                        idTripPlane = dataFilter[position].id
                        tripCode    = dataFilter[position].tripCode
                        isApproval  = dataFilter[position].isApproval
                        isParticipant = dataFilter[position].isParticipant
                    }
                    else if(dataSearch.isNotEmpty()){
                        idTripPlane = dataSearch[position].id
                        tripCode    = dataSearch[position].tripCode
                        isApproval  = dataFilter[position].isApproval
                        isParticipant = dataFilter[position].isParticipant
                    }
                    else{
                        idTripPlane = data[position].id
                        tripCode    = data[position].tripCode
                        isApproval  = data[position].isApproval
                        isParticipant = data[position].isParticipant
                    }

                    intent.putExtra(Constants.KEY_FROM, Constants.FROM_DRAFT)
                    intent.putExtra(Constants.KEY_INTENT_TRIPID, idTripPlane)
                    intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, tripCode)
                    intent.putExtra(Constants.KEY_IS_APPROVAL,isApproval)
                    intent.putExtra(Constants.KEY_IS_PARTICIPANT,isParticipant)

                    (context as Activity).startActivityForResult(intent,Constants.OPEN_DETAIL_TRIP_PLANE)
                }
            }
            -109 ->{
                checkPositionPageSelected(position)
            }
        }
    }

    private fun checkPositionPageSelected(position: Int) {
        if (positionPage==0){
            checkListItem(position,data)
        }
        else if(positionPage==1){
            checkListItem(position,dataFilter)
        }
    }

    private fun checkSelection(data:ArrayList<ApprovalModelAdapter>) {
        if (data.filter { it.selected == true }.size>0){
            itemSelected = true
            top_button.visibility = View.GONE
        }
        else{
            itemSelected = false
            top_button.visibility = View.VISIBLE
        }
    }

    private fun checkListItem(position: Int,data:ArrayList<ApprovalModelAdapter>) {
        data.get(position).selected = !data.get(position).selected
        adapter.setData(data)

        if (positionPage==0){
            checkSelection(data)
        }
        else{
            checkSelection(dataFilter)
        }

    }

    override fun onDraft() {
        positionPage = 0
        dataFilter.clear()
        if (Globals.isPertamina(context)){
            data.filter {
                it.status == "Completely Approved"
            }.forEach {
                dataFilter.add(it)
            }
        } else {
            data.filter {
                it.status == "Draft"
            }.forEach {
                dataFilter.add(it)
            }
        }
        dataFilter.forEachIndexed { _, approvalModelAdapter -> approvalModelAdapter.selected = false }
//        dataFilter.sortBy { it.start_date }
//        dataFilter.reverse()
        adapter.setData(dataFilter)
        checkSelection(dataFilter)
    }

    override fun onCompleted() {
        positionPage = 1
        dataFilter.clear()
        if (Globals.isPertamina(context)){
            data.filter {
                it.status == "Trip Completed"
            }.forEach {
                dataFilter.add(it)
            }
        } else {
            data.filter {
                it.status == "Completely Approved" || it.status == "Partialy Approved"
            }.forEach {
                dataFilter.add(it)
            }
        }
        /*dataFilter.addAll(data.filter { it.status == "Completely Approved" || it.status == "Completely Rejected" || it.status == "Trip Completed" || it.status == "Canceled" })*/
        dataFilter.forEachIndexed { _, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)
    }

    override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int, position: Int) {

    }

    private fun restoreItem(deletedItem: ApprovalModelAdapter, deletedIndex: Int) {
        data.add(deletedIndex,deletedItem)
        adapter.setData(data)
        setTitleButton()
    }

    private fun removeAt(adapterPosition: Int) {
        rejectOrApproveSelected(data[adapterPosition],"0")
        data.removeAt(adapterPosition)
        adapter.setData(data)
        setTitleButton()
    }

    override fun onClick(v: View?) {

    }

    val dataUploaded : ArrayList<Int> = ArrayList()
    var totalRequested = 0
    var totalUploaded   = 0

    private fun cleareDataUpload() {
        dataUploaded.clear()
        totalRequested = 0
        totalUploaded  = 0
    }

    private fun rejectOrApproveSelected(mData :ApprovalModelAdapter, action: String){
        GetDataApproval(getBaseUrl(context)).approveAll(getToken(),dataBodyApproved(mData,action),object :CallbackApprovAll{
            override fun successLoad(data: String) {
                setLog(data)
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    private fun dataBodyApproved(data: ApprovalModelAdapter,action:String): HashMap<Any, Any> {
        val mData = ApprovalAllRequest()
        mData.approvalAction = action
        mData.employeeId     = Globals.getProfile(context).employId
        mData.tripId         = data.id

        return Globals.classToHashMap(mData, ApprovalAllRequest::class.java)
    }

    fun selectAll(select:Boolean){
        if (positionPage==0){
            data.forEachIndexed { index, approvalModelAdapter ->
                approvalModelAdapter.selected = select
            }
            adapter.setData(data)
        }
        else{
            dataFilter.forEachIndexed { index, approvalModelAdapter ->
                approvalModelAdapter.selected = select
            }
            adapter.setData(dataFilter)
        }
    }

    fun searchData(key:String){
        if(key.isNotEmpty()){
            dataSearch.clear()
            dataSearch.addAll(data.filter { it.tripCode.toLowerCase().contains(key.toLowerCase()) || it.title.toLowerCase().contains(key.toLowerCase()) })
            adapter.setData(dataSearch)

            if (dataSearch.isEmpty()){
                empty_view.showFast("Your Trip Code Not Found")
            }
            else{
                empty_view.hide()
            }
        }
        else{
            dataSearch.clear()
            adapter.setData(data)
            empty_view.hide()
        }
    }

}