package com.opsicorp.travelaja.feature_flight.ssr

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.mobile.travelaja.base.BaseActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsicorp.travelaja.feature_flight.R
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.Constants
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerViewParent
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
    var positionPassanger : Int = 0

    override fun OnMain() {
        btnApply.callbackOnclickButton(this)
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
        positionPassanger = intent.getIntExtra(Constants.KEY_POSITION_SELECT_PASSENGER, 0)
        adapter.setData(datalist.dataFlight[0].passenger[positionFlight].ssr.dataSsr)
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
        val selectedItem = SelectedSsrModel()
        selectedItem.price = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].pricing
        selectedItem.ssrCode = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].ssrCode
        selectedItem.curency = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].curency
        selectedItem.ssrName = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].ssrName
        selectedItem.ssrType = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].ssrType
        selectedItem.ssrTypeName = datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.dataSsr[positionParent].ssrItem[positionChild].ssrTypeName
        if (!ssrSelected.filter { it.ssrType  == selectedItem.ssrType}.isNullOrEmpty()){
            ssrSelected.removeAt(ssrSelected.indexOf(ssrSelected.filter { it.ssrType  == selectedItem.ssrType }.last()))
            ssrSelected.add(selectedItem)
        } else {
            ssrSelected.add(selectedItem)
        }
        ssrSelected.distinct()
        datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.ssrSelected.clear()
        datalist.dataFlight[positionFlight].passenger[positionPassanger].ssr.ssrSelected.addAll(ssrSelected)
        Globals.DATA_LIST_FLIGHT = Serializer.serialize(datalist)

    }

    override fun onClicked() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK,returnIntent)
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