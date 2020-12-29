package com.opsicorp.travelaja.feature_flight.flight_info.infofragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.unicode.kingmarket.Base.BaseFragment
import kotlinx.android.synthetic.main.flight_info_fragment_new.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FlightInfoFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.flight_info_fragment_new
    }

    val data = ArrayList<ModelListDepartureAndArivalAdapter>()
    val adapter by lazy { InfoDepartureArrivalAdapter(context!!,data) }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_departure_arrival.layoutManager = layoutManager
        rv_departure_arrival.itemAnimator = DefaultItemAnimator()
        rv_departure_arrival.adapter = adapter

        addData()
    }

    private fun addData() {
        if(Globals.DATA_FLIGHT.isNotEmpty()) {
            val data = Serializer.deserialize(Globals.DATA_FLIGHT, ResultListFlightModel::class.java)

            val listData = ArrayList<ModelListDepartureAndArivalAdapter>()

            if (data.isConnecting){
                data.transiteFlight.forEachIndexed { index, transiteFlight ->
                    val mData = ModelListDepartureAndArivalAdapter()

                    mData.imageFlight = transiteFlight.airlineImageUrl
                    mData.titleFlight = transiteFlight.titleAirline
                    mData.classNameFlight  = transiteFlight.classFlight
                    mData.numberFlight = transiteFlight.numberFlight
                    mData.timeDeparture    = transiteFlight.departTime
                    mData.departureDisplay = "${transiteFlight.originCity} (${transiteFlight.originId})" //Jakarta (Cgk)
                    mData.dateDeparture    = DateConverter().getDate(transiteFlight.departDate,"yyyy-MM-dd","dd MMM") //27 Apr
                    mData.departureAirport = transiteFlight.originAirport
                    mData.duration         = transiteFlight.durationView
                    mData.timeArrival      = transiteFlight.arriveTime
                    mData.arrivalDisplay   = "${transiteFlight.destinationCity} (${transiteFlight.destinationId})" //Jakarta (Cgk)
                    mData.dateArrival      = DateConverter().getDate(transiteFlight.arriveDate,"yyyy-MM-dd","dd MMM") //27 Apr
                    mData.arrivalAirport   = transiteFlight.destinationAirport
                    listData.add(mData)
                }
            }
            else{
                val mData = ModelListDepartureAndArivalAdapter()

                mData.imageFlight = data.imgAirline
                mData.titleFlight = data.titleAirline
                mData.classNameFlight  = "${data.nameClass} Class (${data.classCode})"
                mData.numberFlight = data.flightNumber
                mData.timeDeparture    = data.departTime
                mData.departureDisplay = "${data.originName} (${data.origin})" //Jakarta (Cgk)
                mData.dateDeparture    = DateConverter().getDate(data.departDate,"yyyy-MM-dd","dd MMM") //27 Apr
                mData.departureAirport = data.originAirport
                mData.duration         = data.durationView
                mData.timeArrival      = data.arriveTime
                mData.arrivalDisplay   = "${data.destinationName} (${data.destination})" //Jakarta (Cgk)
                mData.dateArrival      = DateConverter().getDate(data.arrivalDate,"yyyy-MM-dd","dd MMM") //27 Apr
                mData.arrivalAirport   = data.destinationAirport
                listData.add(mData)
            }


            adapter.setData(listData)
        }
    }
}