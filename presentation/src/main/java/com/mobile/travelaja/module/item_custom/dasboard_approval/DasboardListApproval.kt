package com.mobile.travelaja.module.item_custom.dasboard_approval

import android.view.View
import java.util.HashMap
import android.app.Activity
import org.koin.core.inject
import android.content.Intent
import com.mobile.travelaja.R
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import org.koin.core.KoinComponent
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import com.mobile.travelaja.utility.*
import org.koin.core.parameter.parametersOf
import com.mobile.travelaja.utility.Globals.setLog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.utility.Globals.getToken
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.callback.CallbackListTripplan
import opsigo.com.datalayer.request_model.ApprovalAllRequest
import opsigo.com.datalayer.request_model.ApproverPerItemRequest
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import kotlinx.android.synthetic.main.list_layout_approval.view.*
import com.mobile.travelaja.module.approval.adapter.ApprovalAdapter
import com.mobile.travelaja.module.approval.activity.DetailTripActivity
import com.mobile.travelaja.module.item_custom.button_top_approval.ButtonTopApproval

class DasboardListApproval: LinearLayout,
        OnclickListenerRecyclerView,KoinComponent,
        ButtonTopApproval.OnclickButtonApproval, View.OnClickListener {

    val data = ArrayList<ApprovalModelAdapter>()
    val dataFilter = ArrayList<ApprovalModelAdapter>()
    val dataSearch = ArrayList<ApprovalModelAdapter>()

    val adapter by inject<ApprovalAdapter> { parametersOf(data) }
    var itemSelected = false
    var positionPage = 0
    lateinit var callback : CallbackDassboardListApproval
    var baseUrl = ""
    var firstDate = ""
    var endDate   = ""

    fun setInitCallback(callback:CallbackDassboardListApproval){
        this.callback = callback
    }

    interface CallbackDassboardListApproval{
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
        View.inflate(context, R.layout.list_layout_approval, this)
        baseUrl = Globals.getBaseUrl(context)
        setInitRecyclerView()
        setOnClikListener()
    }

    fun show(tripDateFrom: String,tripDateTo: String,position: Int,key:String){
        firstDate = tripDateFrom
        endDate   = tripDateTo
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
        btn_approv.setOnClickListener(this)
        btn_reject.setOnClickListener(this)
        tv_title_select_all.setOnClickListener(this)
        line_checkbox.setOnClickListener(this)
        btn_approval.callbackOnclickFilter(this)
    }


    private fun setTitleButton() {
        btn_approval.setTextAllButton("All (${data.size})")
        btn_approval.setTextWaitingButton("Waiting (${data.filter { it.status.equals("Waiting") }.size})")
        btn_approval.setTextApproveButton("Approval (${data.filter { it.status.equals("Completely Approved") }.size})")
        btn_approval.setTextRejectedButton("Rejected (${data.filter { it.status.equals("Completely Rejected") }.size})")
        btn_approval.setTextExpiredButton("Expired (${data.filter { it.status.equals("Expired") }.size})")
        btn_approval.setTextParticiallyRejectedButton("Partially Rejected (${data.filter { it.status.equals("Partially Rejected")}.size})")
        btn_approval.setTextPartiallApprovedyButton("Partially Approved (${data.filter { it.status.equals("Partially Approved") }.size})")

        /*    if(positionPage==0){
                setLog("Test ==== >>> "+data.size)
                setLog("Test ==== >>> "+dataFilter.size)
                btn_approval.setTextAllButton("All(${data.size})")
                btn_approval.setTextWaitingButton("Waiting(${data.filter { it.status.equals("Waiting") }.size})")
                btn_approval.setTextApproveButton("Approval(${data.filter { it.status.equals("Completely Approved") }.size})")
                btn_approval.setTextRejectedButton("Rejected(${data.filter { it.status.equals("Completely Rejected") }.size})")
                btn_approval.setTextExpiredButton("Expired(${data.filter { it.status.equals("Expired") }})")
                btn_approval.setTextParticiallyRejectedButton("Partially Rejected(${data.filter { it.status.equals("Partially Rejected") }})")
                btn_approval.setTextPartiallApprovedyButton("Partially Approved(${data.filter { it.status.equals("Partially Approved") }})")
            }
            else{
                setLog("Test ==== >) "+data.size)
                setLog("Test ==== >) "+dataFilter.size)
                btn_approval.setTextAllButton("All(${dataFilter.size})")
                btn_approval.setTextWaitingButton("Waiting(${dataFilter.filter { it.status.equals("Waiting") }.size})")
                btn_approval.setTextApproveButton("Approval(${dataFilter.filter { it.status.equals("Completely Approved") }.size})")
                btn_approval.setTextRejectedButton("Rejected(${dataFilter.filter { it.status.equals("Completely Rejected") }.size})")
                btn_approval.setTextExpiredButton("Expired(${dataFilter.filter { it.status.equals("Expired") }})")
                btn_approval.setTextParticiallyRejectedButton("Partially Rejected(${dataFilter.filter { it.status.equals("Partially Rejected") }})")
                btn_approval.setTextPartiallApprovedyButton("Partially Approved(${dataFilter.filter { it.status.equals("Partially Approved") }})")
            }*/
    }

    private fun setInitRecyclerView() {
        val layoutManager = LinearLayoutManager(context!!)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_waiting_approval.layoutManager = layoutManager
        rv_waiting_approval.itemAnimator = DefaultItemAnimator()
        rv_waiting_approval.adapter = adapter

        adapter.setOnclickListener(this)

        val scrollListener = RecyclerViewLoadMoreScrollListener(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {

            }
        })

        rv_waiting_approval.addOnScrollListener(scrollListener)

        object : SwipeHelper(context, rv_waiting_approval) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: ArrayList<UnderlayButton>
            ) {
                underlayButtons.add(SwipeHelper.UnderlayButton("REJECT",0,
                    Color.parseColor("#eb0011"),object :SwipeHelper.UnderlayButtonClickListener{
                        override fun onClick(pos: Int) {
                            removeAt(pos)
                        }
                    }))
            }
        }

    }

    fun getData(tripDateFrom:String,tripDateTo:String,key: String,reloadData:Boolean=false) {
        if (!reloadData){
            loading_view.show()
        }
        data.clear()
        GetDataGeneral(baseUrl).getListTripplan(getToken(), "40", "1", "Code","1",tripDateFrom,tripDateTo,key, object : CallbackListTripplan{
            override fun successLoad(approvalModel: ArrayList<ApprovalModelAdapter>) {
                if (reloadData){
                    callback.hidenDialog()
                }
                loading_view.hide()
                if (approvalModel.isEmpty()) {
                    empty_view.show()
                } else {
                    empty_view.hide()
                    error_view.hide()
                    data.addAll(approvalModel)
                    adapter.setData(data)

                    //set position filter
                    when(positionPage){

                        0 -> {
                            onAll()
                            btn_approval.allButtonSelected()
                        }
                        1 -> {
                            onWaiting()
                            btn_approval.waitingButtonSelected()
                        }
                        2 -> {
                            onApproval()
                            btn_approval.approvalButtonSelected()
                        }
                        3 -> {
                            onRejected()
                            btn_approval.rejectedButtonSelected()
                        }
                        4->{
                            onPartiallyApproved()
                            btn_approval.partiallyApprovedButtonSelected()
                        }
                        5-> {
                            onPartiallyRejected()
                            btn_approval.partiallyRejectedButtonSelected()
                        }
                        6 -> {
                            onExpired()
                            btn_approval.expiredSetSelection()
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
                error_view.show()
                Globals.showAlert("Sorry",message,context)
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


                    intent.putExtra(Constants.KEY_FROM, Constants.FROM_LIST_DASBOARD)
                    intent.putExtra(Constants.KEY_INTENT_TRIPID, idTripPlane)
                    intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, tripCode)
                    intent.putExtra(Constants.KEY_IS_APPROVAL,isApproval)
                    intent.putExtra(Constants.KEY_IS_PARTICIPANT,isParticipant)
                    (context as Activity).startActivityForResult(intent,Constants.OPEN_DETAIL_TRIP_PLANE)
                }
            }
            -109 ->{
                if (data[position].isApproval){
                    checkPositionPageSelected(position)
                }
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
            line_approve.visibility = View.VISIBLE
            btn_approval.visibility = View.GONE
        }
        else{
            itemSelected = false
            line_approve.visibility = View.GONE
            btn_approval.visibility = View.VISIBLE
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

    override fun onAll() {
        positionPage = 0
        dataFilter.clear()
        dataFilter.addAll(data)
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onWaiting() {
        positionPage = 1
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Waiting") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onApproval() {
        positionPage = 2
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Completely Approved") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onRejected() {
        positionPage = 3
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Completely Rejected") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onPartiallyApproved() {
        positionPage = 4
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Partially Approved") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onPartiallyRejected() {
        positionPage = 5
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Partially Rejected") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }

    override fun onExpired() {
        positionPage = 6
        dataFilter.clear()
        dataFilter.addAll(data.filter { it.status.equals("Expired") })
        dataFilter.forEachIndexed { index, approvalModelAdapter -> approvalModelAdapter.selected = false }
        adapter.setData(dataFilter)
        checkSelection(dataFilter)

        if(dataFilter.size == 0){
            empty_view.show()
        } else {
            empty_view.hide()
        }
    }


    private fun restoreItem(deletedItem: ApprovalModelAdapter, deletedIndex: Int) {
        data.add(deletedIndex,deletedItem)
        adapter.setData(data)
        setTitleButton()
    }

    private fun removeAt(position: Int) {
        rejectOrApproveSelected(data[position],"0")
        data.removeAt(position)
        adapter.setData(data)
        onWaiting()
        setTitleButton()
    }

    override fun onClick(v: View?) {
        when(v){
            btn_reject->{
                btnRejectedBySelected()
            }
            btn_approv->{
                btnApproveBySelected()
            }
            tv_title_select_all->{
                checkBoxListener()
            }
            line_checkbox->{
                checkBoxListener()
            }
        }
    }

    private fun checkBoxListener() {
        if (checkbox.isChecked){
            selectAll(false)
            checkbox.isChecked = false
        }
        else{
            selectAll(true)
            checkbox.isChecked = true
        }
    }

    fun btnApproveBySelected(){
        if (positionPage==0){
            approveBySelected(data)
        }
        else{
            approveBySelected(dataFilter)
        }
    }

    fun btnRejectedBySelected(){
        if (positionPage==0){
            rejectingBySelected(data)
        }
        else{
            rejectingBySelected(dataFilter)
        }

    }

    val dataUploaded : ArrayList<Int> = ArrayList()
    var totalRequested = 0
    var totalUploaded   = 0

    fun rejectingBySelected(data: ArrayList<ApprovalModelAdapter>){
        data.forEachIndexed { index, approvalModelAdapter ->
            if (approvalModelAdapter.selected&&approvalModelAdapter.status == "Waiting"){
                dataUploaded.add(index)
            }
        }
        totalRequested = 0
        approveAll(data,"0")
        callback.showDialog()
    }

    fun approveBySelected(data: ArrayList<ApprovalModelAdapter>) {
        data.forEachIndexed { index, approvalModelAdapter ->
            if (approvalModelAdapter.selected&&approvalModelAdapter.status == "Waiting"){
                dataUploaded.add(index)
//                approvalModelAdapter.status = "Approved"
            }
//            approvalModelAdapter.selected = false
        }
        totalRequested = 0
        approveAll(data,"1")
        callback.showDialog()

    }


    fun approveAll(mData: ArrayList<ApprovalModelAdapter>,action: String){
        totalUploaded = dataUploaded.size
//        setLog("TAG ---> "+mData[dataUploaded[totalRequested]].tripCode)
//        setLog("total upload "+totalUploaded)
//        setLog("total request "+totalRequested)
        if (Globals.getConfigCompany(context).codeCompany==11){
            GetDataApproval(baseUrl).approveItem(getToken(),dataBodyApproved(mData[dataUploaded[totalRequested]],action),object :CallbackApprovAll{
                override fun successLoad(data: String) {
                    totalRequested++
                    if (totalUploaded==totalRequested){

                        if (action=="0"){
                            positionPage = 3
                        }
                        else{
                            positionPage = 2
                        }
                        this@DasboardListApproval.data.clear()
                        getData(firstDate,endDate,"",true)
                        setTitleButton()
                        cleareDataUpload()
                    }
                    else{
                        approveAll(mData,action)
                    }
                }

                override fun failedLoad(message: String) {
                    approveAll(mData,action)
                }
            })
        }
        else {
            GetDataApproval(baseUrl).approveAll(getToken(),dataBodyApproved(mData[dataUploaded[totalRequested]],action),object :CallbackApprovAll{
                override fun successLoad(data: String) {
                    totalRequested++
                    if (totalUploaded==totalRequested){

                        if (action=="0"){
                            positionPage = 3
                        }
                        else{
                            positionPage = 2
                        }

                        this@DasboardListApproval.data.clear()
                        getData(firstDate,endDate,"",true)
                        setTitleButton()
                        cleareDataUpload()
                    }
                    else{
                        approveAll(mData,action)
                    }
                }

                override fun failedLoad(message: String) {
                    callback.hidenDialog()
                    callback.failedLoad(message,mData[dataUploaded[totalRequested]].tripCode)
                    cleareDataUpload()
                }
            })
        }

    }


    private fun cleareDataUpload() {
        dataUploaded.clear()
        totalRequested = 0
        totalUploaded  = 0
    }

    fun rejectOrApproveSelected(mData :ApprovalModelAdapter,action: String){
        if (Globals.getConfigCompany(context).codeCompany==11){
            GetDataApproval(baseUrl).approveItem(getToken(),dataRejected(action,mData),object :CallbackApprovAll{
                override fun successLoad(data: String) {

                }

                override fun failedLoad(message: String) {

                }
            })
        }
        else {
            GetDataApproval(baseUrl).approveAll(getToken(),dataBodyApproved(mData,action),object :CallbackApprovAll{
                override fun successLoad(data: String) {

                }

                override fun failedLoad(message: String) {

                }
            })
        }
    }

    private fun dataRejected(action: String, approval: ApprovalModelAdapter): HashMap<Any, Any> {
        val data = ApproverPerItemRequest()
        data.approvalAction  = action
        data.tripType   = "0"
        data.employeeId = Globals.getProfile(context).employId
        data.tripId     = approval.id
        data.tripParticipantId = approval.participant.first().id
        return Globals.classToHashMap(data, ApproverPerItemRequest::class.java)
    }

    private fun dataBodyApproved(data: ApprovalModelAdapter,action:String): HashMap<Any, Any> {
        val mData = ApprovalAllRequest()
        mData.approvalAction = action
        mData.employeeId     = Globals.getProfile(context).employId
        mData.tripId         = data.id
        mData.tripType       = "0"

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
        if(key.length>0){
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