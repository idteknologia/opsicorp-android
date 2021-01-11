package com.opsigo.travelaja.module.accomodation.ssr

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.activity_bagage.*
import kotlinx.android.synthetic.main.ssr_flight_activity.*
import kotlinx.android.synthetic.main.ssr_flight_activity.toolbar
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class SsrActivity : BaseActivity(), OnclickListenerRecyclerViewParent {

    val adapter by lazy { SsrAdapter(this) }

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        setData()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR(Special Service Request)")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvSsr.layoutManager = layoutManager
        rvSsr.itemAnimator = DefaultItemAnimator()
        rvSsr.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun setData() {
        val datalist   = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        adapter.setData(datalist.dataFlight)
    }

    override fun getLayout(): Int {
        return R.layout.ssr_flight_activity
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

    }
}