package com.opsigo.travelaja.module.accomodation.hotel.parent

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.flight_info_facility_fragment.*
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import opsigo.com.domainlayer.callback.CallbackListCompany
import opsigo.com.domainlayer.model.accomodation.hotel.ListCompanyModel

class NearbyActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.nearby_hotel_view
    }
    var data = ArrayList<ListCompanyModel>()
    val adapter by lazy { NearbyHotelAdapter(this,data) }

    override fun OnMain() {
        initRecyclerView()
        getDataListCompany()
    }

    fun getDataListCompany(){
        GetDataAccomodation(getBaseUrl()).getListCompanyHotel(getToken(),true,object : CallbackListCompany {
            override fun success(data: ArrayList<ListCompanyModel>) {
                this@NearbyActivity.data = data
                adapter.setData(data)
            }

            override fun failed(message: String) {
                Globals.showAlert("Sorry",message,this@NearbyActivity)
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_facility.layoutManager = layoutManager
        rv_facility.itemAnimator = DefaultItemAnimator()
        rv_facility.adapter = adapter
    }
}