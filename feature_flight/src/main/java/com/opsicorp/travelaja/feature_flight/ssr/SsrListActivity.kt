package com.opsicorp.travelaja.feature_flight.ssr

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.opsigo.travelaja.BaseActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import android.util.Log
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.ssr_list_activity.*
import kotlinx.android.synthetic.main.ssr_list_activity.toolbar
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.SelectedSsrModel

class SsrListActivity : BaseActivity(), ToolbarOpsicorp.OnclickButtonListener, ButtonDefaultOpsicorp.OnclickButtonListener,
        OnclickListenerRecyclerViewParent {

    val data = ArrayList<String>()


    override fun getLayout(): Int {
        return R.layout.ssr_list_activity
    }

    val ssr = SsrModel()
    val adapter by lazy { SsrListAdapter(this) }

    lateinit var datalist: DataListOrderAccomodation
    val ssrSelected = ArrayList<SelectedSsrModel>()
    var positionFlight: Int = 0

    override fun OnMain() {
        btnApply.callbackOnclickButton(this)
        btnApply.setTextButton("Apply")
        initToolbar()
        initRecyclerView()
        setDataRecyclerView()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR list menu")
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun setDataRecyclerView() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        positionFlight = intent.getIntExtra(Constants.KEY_POSITION_SELECT_SSR, 0)
        datalist.dataFlight.forEach {
            Log.e("testdata", Serializer.serialize(it.passenger[positionFlight].ssr.dataSsr))
        }
        adapter.setData(datalist.dataFlight[0].passenger[positionFlight].ssr.dataSsr)
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_list_type_ssr.layoutManager = layoutManager
        rv_list_type_ssr.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_list_type_ssr.adapter = adapter

        adapter.setOnclickListener(this)
    }


    override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {
        /*val selectedItem = SelectedSsrModel()
        selectedItem.price = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].pricing
        selectedItem.ssrCode = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].ssrCode
        selectedItem.curency = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].curency
        selectedItem.ssrName = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].ssrName
        selectedItem.ssrType = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].ssrType
        selectedItem.ssrTypeName = datalist.dataFlight[positionFlight].dataSSR.dataSsr[positionParent].ssrItem[positionChild].ssrTypeName
        if (!ssrSelected.filter { it.ssrType  == selectedItem.ssrType}.isNullOrEmpty()){
            ssrSelected.removeAt(ssrSelected.indexOf(ssrSelected.filter { it.ssrType  == selectedItem.ssrType }.last()))
            ssrSelected.add(selectedItem)
        } else {
            ssrSelected.add(selectedItem)
        }
        ssrSelected.distinct()
        datalist.dataFlight[positionFlight].dataSSR.ssrSelected.clear()
        datalist.dataFlight[positionFlight].dataSSR.ssrSelected.addAll(ssrSelected)
        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)
        Log.e("testSave", ssrSelected[0].ssrName)*/

    }

    override fun onClicked() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK,returnIntent)
        val dataList = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        dataList.dataFlight[0].passenger[positionFlight].ssr.ssrSelected.forEach {
            setLog("testSave2",it.ssrName)
        }
        finish()
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

}