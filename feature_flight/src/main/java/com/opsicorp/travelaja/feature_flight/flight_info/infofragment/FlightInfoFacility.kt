package com.opsicorp.travelaja.feature_flight.flight_info.infofragment

import android.os.Bundle
import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.opsicorp.travelaja.feature_flight.adapter.FacilityFlightListAdapter
import com.mobile.travelaja.utility.Globals
import com.squareup.picasso.Picasso
import com.mobile.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.flight_info_facility_fragment_new.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.FacilityFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FlightInfoFacility : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.flight_info_facility_fragment_new
    }

    var data = ArrayList<FacilityFlightModel>()
    val adapter by lazy { FacilityFlightListAdapter(requireContext(),data) }

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
            tv_airport_terminal.text    = "${mData.originAirport} Terminal : ${mData.terminal.replace("null","")}"

            Picasso.get()
                    .load(mData.imgAirline)
                    .fit()
                    .into(img_airline)

            data.clear()

            if (mData.facility.isEmpty()){
                for (i in 0 until 2){
                    val mNull = FacilityFlightModel()
                    mNull.nameFacility = "-"
                    mNull.valueFacility = "-"
                    data.add(mNull)
                }
            }
            else {
                mData.facility.forEach {
                    val mFacility = FacilityFlightModel()
                    mFacility.nameFacility = it.nameFacility
                    mFacility.valueFacility = it.valueFacility
                    data.add(mFacility)
                }
                /*data.addAll(mData.facility)*/
            }

            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_facility.layoutManager = layoutManager
        rv_facility.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_facility.adapter = adapter
    }
}