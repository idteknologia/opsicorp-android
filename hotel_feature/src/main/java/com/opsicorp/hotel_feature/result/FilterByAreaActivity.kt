package com.opsicorp.hotel_feature.result

import android.content.Intent
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Constants
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.opsicorp.hotel_feature.adapter.FilterAreaAdapter
import kotlinx.android.synthetic.main.activity_filter_by_area.*
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView

class FilterByAreaActivity : BaseActivity() ,
        ToolbarOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerView {
    override fun getLayout(): Int { return R.layout.activity_filter_by_area }

    var data = ArrayList<String>()
    val adapterArea = FilterAreaAdapter(this,data)

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        setDataArea()
    }

    private fun setDataArea() {
        if (!intent.getBundleExtra(Constants.KEY_BUNDLE).getStringArrayList(Constants.KEY_DATA_AREA).isNullOrEmpty()){
            data.clear()
            data.addAll(intent.getBundleExtra(Constants.KEY_BUNDLE).getStringArrayList(Constants.KEY_DATA_AREA)!!)
            adapterArea.setData(data)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_area.layoutManager = layoutManager
        rv_area.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_area.adapter       = adapterArea
        adapterArea.setOnclickListener(this)
    }

    private fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        toolbar.hidenBtnCart()
        toolbar.setTitleBar("Filter Area")
        toolbar.changeImageBtnBack(com.opsigo.travelaja.R.drawable.ic_close_white)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    var positionSelected = 0
    override fun onClick(views: Int, position: Int) {
        positionSelected = position
        Globals.delay(1000,object :Globals.DelayCallback{
            override fun done() {
                val area = Intent()
                area.putExtra(Constants.RESULT_AREA_HOTEL,data[positionSelected])
                Globals.finishResultOk(this@FilterByAreaActivity,area)
            }
        })
        adapterArea.notifyDataSetChanged()
    }
}