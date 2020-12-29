package com.opsicorp.travelaja.feature_flight.flight_info.infofragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.opsicorp.travelaja.feature_flight.adapter.FacilityFlightListAdapter
import com.opsigo.travelaja.utility.Globals
import com.squareup.picasso.Picasso
import com.unicode.kingmarket.Base.BaseFragment
import kotlinx.android.synthetic.main.flight_info_facility_fragment_new.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.FacilityFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FlightInfoFacility : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.flight_info_facility_fragment_new
    }

    var data = ArrayList<FacilityFlightModel>()
    val adapter by lazy { FacilityFlightListAdapter(context!!,data) }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setDataView()
    }

    private fun setDataView() {
        if(Globals.DATA_FLIGHT.isNotEmpty()) {
            val mData = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

            tv_flight_number.text       = mData.flightNumber
            tv_title_flight.text        = mData.titleAirline
            tv_class_flight.text        = "${mData.nameClass} Class (${mData.classCode})"
            tv_airport_terminal.text    = "${mData.originAirport} Terminal : ${mData.terminal}"

            Picasso.get()
                    .load(mData.imgAirline)
                    .fit()
                    .into(img_airline)

            data.clear()
            val mFacility = FacilityFlightModel()
            mFacility.nameFacility = "Cabin baggage"
            mFacility.valueFacility = "7 kg"
            data.add(mFacility)

            if (mData.facility.isEmpty()){
                for (i in 0 until 2){
                    val mNull = FacilityFlightModel()
                    mNull.nameFacility = "null"
                    mNull.valueFacility = ""
                    data.add(mNull)
                }
            }
            else {
                data.addAll(mData.facility)
            }

            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_facility.layoutManager = layoutManager
        rv_facility.itemAnimator = DefaultItemAnimator()
        rv_facility.adapter = adapter
    }
}