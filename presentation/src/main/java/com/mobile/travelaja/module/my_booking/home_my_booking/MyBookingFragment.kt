package com.mobile.travelaja.module.my_booking.home_my_booking

import android.os.Build
import android.os.Bundle
import android.view.View
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.my_booking.adapter.MyBookingAdapter
import com.mobile.travelaja.module.my_booking.purchase_list_detail.FilterPurchaseDialog
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.base.BaseFragment
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import kotlinx.android.synthetic.main.empty_cart_view.*
import kotlinx.android.synthetic.main.my_booking_layout.*

class MyBookingFragment : BaseFragment(),OnclickListenerRecyclerView ,
        ToolbarOpsicorp.OnclickButtonListener,ButtonDefaultOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.my_booking_layout }

    lateinit var callback : CallbackPressed
    val data = ArrayList<MyBookingModel>()
    val adapter by lazy { MyBookingAdapter(context!!, data) }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()

        initEmptyView()
    }

    fun showFilter(){
        FilterPurchaseDialog(context!!).create()
    }

    private fun initEmptyView() {
        img_empty.setImageDrawable(resources.getDrawable(R.drawable.no_transaction))
        tv_massage_empty.text = getString(R.string.you_havent_made_any_purchase)
        tv_title_empty.text = getString(R.string.oh_crap)
        btn_home_page.setTextButton(getString(R.string.make_a_new_purchase))
    }


    private fun initToolbar() {
        toolbar.setTitleBar(getString(R.string.purchase_list))
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { toolbar.singgleTitleGravity(toolbar.START) }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_my_booking.layoutManager = layoutManager
        rv_my_booking.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_my_booking.adapter = adapter

        adapter.setOnclickListener(this)

        getData()
    }

    private fun getData() {
        data.clear()
//        data.addAll(MapperModelListMybooking().mapper(DataDummyMyBooking().addDataListMybooking()))
        adapter.setData(data)
        checkEmpety()
    }

    private fun checkEmpety() {
        if (data.isEmpty()){
            showEemptyLayout()
        }
        else {
            showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        rv_my_booking.visibility = View.VISIBLE
        lay_empty.visibility     = View.GONE
    }

    private fun showEemptyLayout() {
        lay_empty.visibility     = View.VISIBLE
        rv_my_booking.visibility = View.GONE
        btn_home_page.callbackOnclickButton(this)
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1-> {

            }
        }
    }

    override fun btnBack() {
        callback.back()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {
        showFilter()
    }

    fun callback(mCallbackPressed: CallbackPressed){
        callback = mCallbackPressed
    }

    interface CallbackPressed{
        fun back()
    }

    override fun onClicked() {

    }


}