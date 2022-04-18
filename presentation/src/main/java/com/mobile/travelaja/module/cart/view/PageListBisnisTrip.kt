package com.mobile.travelaja.module.cart.view

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.module.cart.adapter.CartListAdapter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Globals.getBaseUrl

import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
import kotlinx.android.synthetic.main.page_cart_bisnis_trip.view.*
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.request_model.create_trip_plane.CancelTripPlan
import opsigo.com.domainlayer.callback.CallbackCancelTripplan
import opsigo.com.domainlayer.model.cart.CartModelAdapter
import java.util.HashMap

class PageListBisnisTrip : LinearLayout, View.OnClickListener,OnclickListenerRecyclerView{

//    var data  = ArrayList<CartTripModel>()
    var data  = ArrayList<CartModelAdapter>()
    val adapter by lazy { CartListAdapter(context,data) }
    lateinit var onclick : Callback
    var mTitle = ""

    fun callbackOnclickButton(onclickButtonListener: Callback){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ButtonDefaultOpsicorp, defStyle, 0)
        a.recycle()

        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.page_cart_bisnis_trip, this)

        initSetOnClick()
        initRecyclerView()
        initCheckEmpty()

        btn_delet.visibility = View.GONE
    }

    private fun initCheckEmpty() {
        if (data.isEmpty()){
            empty_view.visible()
            rv_list_bisnis_trip.gone()
        } else {
            empty_view.gone()
            rv_list_bisnis_trip.visible()
        }
    }

    private fun initSetOnClick() {
        tv_select_all.setOnClickListener(this)
        btn_delet.setOnClickListener(this)
        cb_select_all.setOnClickListener(this)

    }

    private fun initRecyclerView() {

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_list_bisnis_trip.layoutManager = layoutManager
        rv_list_bisnis_trip.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_list_bisnis_trip.adapter = adapter
        adapter.setOnclickListener(this)
    }

//    fun setDataOrder(mData:ArrayList<CartTripModel>){
    fun setDataOrder(mData:ArrayList<CartModelAdapter>){
        data.clear()
        data.addAll(mData)
        adapter.setData(data)
    }

    fun setLoadingView(){
        rv_list_bisnis_trip.visibility = View.GONE
        loading_view.show()
    }

    fun hideLoadingView(){
        rv_list_bisnis_trip.visibility = View.VISIBLE
        loading_view.hide()
    }

    fun showEmptyCart(){
        empty_view.visible()
    }

    fun hideEmptyCart(){
        empty_view.gone()
    }

    interface Callback{
        fun onClicked(position: Int)
    }

    override fun onClick(v: View?) {
        when(v){
            tv_select_all -> {

            }
            btn_delet -> {
                deleteConfirmation()
            }
            cb_select_all -> {
                if (cb_select_all.isChecked){
                    selectAllListener()
                }
                else{
                    unSelectAllListener()
                }
            }
        }
    }

    fun deleteConfirmation(){
        AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.warning_delete_this_tripplan))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.yes)) { dialog, id ->
                    deleteListener()
                }
                .setNegativeButton(context.getString(R.string.no), null)
                .show()
    }

    fun deleteListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data.filter {
                it.cheked
            }.forEach {
                data.remove(it)

                removeTrip(it.id)

                Log.d("xdeletedx","" + it.tripCode)
                //Toast.makeText(context,"deletee! " + it.tripCode,Toast.LENGTH_SHORT).show()

            }
            adapter.notifyDataSetChanged()

        }
    }

    private fun removeTrip(id:String){

        GetDataTripPlane(getBaseUrl(context)).cancelTripplan(Globals.getToken(), getDataTripId(id), object : CallbackCancelTripplan {
            override fun successLoad(isSuccess:Boolean) {

            }

            override fun failedLoad(message: String) {
                Globals.showAlert(context.getString(R.string.sorry),message,context)
            }
        } )

    }

    private fun getDataTripId(id: String): HashMap<String, Any> {
        val model = CancelTripPlan()
        model.id = id
        return Globals.classToHasMap(model, CancelTripPlan::class.java)
    }

    private fun unSelectAllListener() {
        tv_select_all.text = context.getString(R.string.select)
        data.forEachIndexed { index, cartTripModel ->
            data[index].cheked = false
        }
        adapter.notifyDataSetChanged()
    }

    private fun selectAllListener() {
        tv_select_all.text = context.getString(R.string.select_all)
        btn_delet.visibility = View.VISIBLE

        data.forEachIndexed { index, cartTripModel ->
            data[index].cheked = true
        }
        adapter.notifyDataSetChanged()
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1 -> {
                onclick.onClicked(position)
            }
            -2 -> {
                data[position].cheked = !data[position].cheked
                adapter.notifyItemChanged(position)
                checkSelectedCondition()
            }
        }
    }

    var totalSelected = 0

    private fun checkSelectedCondition() {
        totalSelected = data.filter { it.cheked }.size
        if (totalSelected>0){
            btn_delet.visibility = View.VISIBLE
        }
        else {
            btn_delet.visibility = View.GONE
        }
    }

}