package com.mobile.travelaja.module.my_booking.purchase_detail

import android.os.Build
import android.view.View
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.my_booking.purchase_list_detail.PurchaseDetailListActivity
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.purchase_activity.*



class PurchaseDetailActivity : BaseActivity(),
        OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener{

    override fun getLayout(): Int { return R.layout.purchase_activity }

    val data by lazy { ArrayList<PurchaseDetailProductModel>() }
    val adapter by lazy { PurchaseDetailProductAdapter(this,data) }

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        Globals.scrollToUp(nested_view)
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()

        if (Globals.typeAccomodation=="Flight"||Globals.typeAccomodation=="Train"){
            toolbar.showTitleRoundtripLeft("Surabaya","Jakarta")
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toolbar.doubleTitleGravity(toolbar.START)
                toolbar.setDoubleTitle("Trans Luxury Bandung","Arjuna, Bandung")
            }
        }
        toolbar.callbackOnclickToolbar(this)
    }

    fun showDetailData(views: View){
        if (btn_drop_down.drawable.constantState.equals(resources.getDrawable(R.drawable.ic_chevron_up_green).constantState)){
            btn_drop_down.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down_green))
            lay_detail.collapse()
        }
        else{
            btn_drop_down.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_green))
            lay_detail.expand()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_product_detail.layoutManager = layoutManager
        rv_product_detail.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_product_detail.adapter = adapter

        adapter.setOnclickListener(this)

        addDataDummy()
    }

    private fun addDataDummy() {

        if (Globals.typeAccomodation=="Flight"){
            for (i in 0..1){
                val mData = PurchaseDetailProductModel()
                mData.typeAccomodation = "Flight"
                mData.type             = "E-ticket Issued"
                mData.arrival          = "Surabaya"
                mData.departure        = "Jakarta"
                data.add(mData)
            }
        }
        else if (Globals.typeAccomodation=="Train"){
            for (i in 0..1){
                val mData = PurchaseDetailProductModel()
                mData.typeAccomodation = "Train"
                mData.type             = "E-ticket Issued"
                mData.arrival          = "Surabaya"
                mData.departure        = "Jakarta"
                data.add(mData)
            }
        }
        else {
            for (i in 0..1){
                val mData = PurchaseDetailProductModel()
                mData.typeAccomodation = "Hotel"
                mData.type             = "Voucher Issued"
                mData.departure        = "Trans Luxury Bandung"
                mData.arrival          = ""
                data.add(mData)
            }
        }
    }

    override fun onClick(views: Int, position: Int) {
        gotoActivity(PurchaseDetailListActivity::class.java)
    }

    override fun btnBack() {

    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }


}