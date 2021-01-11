package com.opsigo.travelaja.module.accomodation.ssr

import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.activity_bagage.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class BagageActivity : BaseActivity(),OnclickListenerRecyclerViewParent {

    override fun getLayout(): Int {
        return R.layout.activity_bagage
    }

    val adapter by lazy { BaggageAdapter(this) }

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        setData()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("Baggage")
        toolbar.hidenBtnCart()
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_baggage.layoutManager = layoutManager
        rv_baggage.itemAnimator = DefaultItemAnimator()
        rv_baggage.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun setData() {
        val datalist   = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        adapter.setData(datalist.dataFlight)
    }

    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

    }
}
