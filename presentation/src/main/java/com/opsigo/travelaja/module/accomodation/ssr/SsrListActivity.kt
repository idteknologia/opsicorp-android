package com.opsigo.travelaja.module.accomodation.ssr

import android.os.Build
import com.opsigo.travelaja.BaseActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Constants
import opsigo.com.domainlayer.model.accomodation.flight.DataSsrModel
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.ssr_list_activity.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

class SsrListActivity : BaseActivity(),
        OnclickListenerRecyclerViewParent {

    val data = ArrayList<String>()


    override fun getLayout(): Int {
        return R.layout.ssr_list_activity
    }

    val ssr = SsrModel()
    val adapter by lazy { SsrListAdapter(this) }


    val ssrMeal = ArrayList<DataSsrModel>()
    val ssrAssistent = ArrayList<DataSsrModel>()
    lateinit var datalist: DataListOrderAccomodation

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        setDataRecyclerView()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR list menu")
        toolbar.hidenBtnCart()
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun setDataRecyclerView() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        var position = intent.getIntExtra(Constants.KEY_POSITION_SELECT_SSR,0)
        datalist.dataFlight.forEach {
            Log.e("testdata",Serializer.serialize(it.dataSSR.dataSsr))
        }
        adapter.setData(datalist.dataFlight[position].dataSSR.dataSsr)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list_type_ssr.layoutManager = layoutManager
        rv_list_type_ssr.itemAnimator = DefaultItemAnimator()
        rv_list_type_ssr.adapter = adapter

        adapter.setOnclickListener(this)
    }


    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

    }

}