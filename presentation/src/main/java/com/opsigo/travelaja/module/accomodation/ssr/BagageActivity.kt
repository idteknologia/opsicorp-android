package com.opsigo.travelaja.module.accomodation.ssr

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.activity_bagage.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class BagageActivity : BaseActivity(),OnclickListenerRecyclerViewParent {

    override fun getLayout(): Int {
        return R.layout.activity_bagage
    }

    val adapter by lazy { BaggageAdapter(this) }

    override fun OnMain() {
        initRecyclerView()
        setData()
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
