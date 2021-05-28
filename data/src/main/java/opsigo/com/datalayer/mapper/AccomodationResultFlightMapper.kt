package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.search.SearchFlightResultEntity
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.FacilityFlightModel
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.flight.TransiteFlight

class AccomodationResultFlightMapper {
    fun mapping(resultData:String):ArrayList<AccomodationResultModel> {
        val data  = ArrayList<AccomodationResultModel>()
        val model = Serializer.deserialize(resultData, SearchFlightResultEntity::class.java)

        model.result.departureFlights.forEachIndexed { index, departureflightItem ->
            departureflightItem.classesView.forEachIndexed { _ , classView ->
                val model = ResultListFlightModel()

                model.isFlightArrival   = false
                model.airline           = departureflightItem.airline //11
                if (departureflightItem.airlineImageUrl.isNullOrEmpty()){
                    if (departureflightItem.connectingFlights.isNullOrEmpty()){
                        model.imgAirline =  "https://toppng.com/uploads/preview/aircraft-png-photos-plane-icon-vector-11562926974aztqwsacuj.png"
                    } else {
                        model.imgAirline = departureflightItem.connectingFlights[0].airlineImageUrl.toString() // "https://toppng.com/uploads/preview/aircraft-png-photos-plane-icon-vector-11562926974aztqwsacuj.png"
                    }
                }
                else {
                    model.imgAirline        = departureflightItem.airlineImageUrl
                }
                if(departureflightItem.airlineName.isNullOrEmpty()){
                    model.titleAirline      = departureflightItem.connectingFlights[0].airlineName.toString()
                }
                else{
                    model.titleAirline      = departureflightItem.airlineName.toString()
                }

                model.arrivalDate       = departureflightItem.arrivalDate.toString()
                model.arriveDate        = departureflightItem.arriveDate.toString()
                model.arriveDateTimeView    = departureflightItem.arriveDateTimeView.toString()
                model.arriveTime        = departureflightItem.arriveTime.toString()

                model.nameClass         = classView.category.toString()

                model.classCode         = departureflightItem.classCode.toString()
                model.classId           = departureflightItem.classId.toString()
                model.code              = classView.code.toString()

                model.departDate        = departureflightItem.departDate.toString()
                model.departTime        = departureflightItem.departTime.toString()
                model.departureDate     = departureflightItem.departureDate.toString()

                //model.duration        = departureflightItem.duration

                model.duration        = departureflightItem.duration.toString()
                model.durationView    = departureflightItem.durationView.toString()
                model.durationIncludeTransit        = departureflightItem.durationIncludeTransit.toString()
                model.durationIncludeTransitView    = departureflightItem.durationIncludeTransitView.toString()

                model.price             = classView.fare
                model.fareBasisCode     = classView.fareBasisCode.toString()
                model.fareRuleKeys      = classView.fareRuleKeys.toString()
                model.flightId          = classView.flightId.toString()
                model.flightNumber      = departureflightItem.flightNumber.toString()
                model.flightType        = departureflightItem.flightType.toString()
                model.flightTypeView    = departureflightItem.flightTypeView.toString()

                model.id                = departureflightItem.id.toString()
                model.isAvailable       = departureflightItem.isAvailable
                model.isComply          = departureflightItem.isComply
                model.isConnecting      = departureflightItem.isConnecting
                model.isMultiClass      = departureflightItem.isMultiClass
                model.isHolderFlight    = departureflightItem.isHolderFlight

                model.sequence          = departureflightItem.sequence

                model.number            = departureflightItem.number.toString()
                model.origin            = departureflightItem.origin.toString()
                model.originName        = departureflightItem.originCity.toString()
                model.destination       = departureflightItem.destination.toString()
                model.destinationName   = departureflightItem.destinationCity.toString()

                model.originAirport     = departureflightItem.originAirport.toString()
                model.destinationAirport= departureflightItem.destinationAirport.toString()

                model.totalTransit      = departureflightItem.totalTransit

                model.numberSeat        = classView.seat.toString()
                model.terminal          = departureflightItem.originTerminal.toString()

                if (departureflightItem.isConnecting){
                    val transitFlight  = ArrayList<TransiteFlight>()
                    departureflightItem.connectingFlights.forEachIndexed { index, transitFlightsEntity ->
                        val transit =  TransiteFlight()
                        transit.numberFlight = transitFlightsEntity.flightNumber.toString()
                        transit.originId     = transitFlightsEntity.origin.toString()
                        transit.destinationId = transitFlightsEntity.destination.toString()
                        transit.priceTotal    = transitFlightsEntity.classesView[0].totalFare.toString() //2598000.0
                        transit.isMultiClass =  false
                        transit.isAvailable  = false
                        transit.departDate   = transitFlightsEntity.departDate.toString()// "2020-09-22"
                        transit.departTime   = transitFlightsEntity.departTime.toString()//05:25
                        transit.arriveDate   = transitFlightsEntity.arriveDate.toString() //: "2020-09-22",
                        transit.arriveTime   = transitFlightsEntity.arriveTime.toString() //""//07:00"
                        transit.duration     = transitFlightsEntity.duration.toString() //""//01:35:00",
                        transit.durationView = transitFlightsEntity.durationView.toString() //""//1h 35m",
                        transit.transitDuration = transitFlightsEntity.transitDuration.toString()//00:45:00",
                        transit.transitDurationView = transitFlightsEntity.transitDurationView.toString()//0h 45m"

                        transit.operatingNumber = transitFlightsEntity.operatingNumber.toString() //GA
                        transit.titleAirline    = transitFlightsEntity.airlineName.toString() //Garuda Indonesia
                        transit.classFlight     = transitFlightsEntity.classesView[0].category.toString()
                        transit.seatNumber      = transitFlightsEntity.classesView[0].seat.toString()
                        transit.originTerminal  = transitFlightsEntity.originTerminal.toString()//1
                        transit.destinationTerminal = transitFlightsEntity.destinationTerminal.toString()//2
                        transit.originCity      = transitFlightsEntity.originCity.toString()//Surabaya
                        transit.originAirport   = transitFlightsEntity.originAirport.toString()//"Juanda Airport"
                        transit.destinationCity = transitFlightsEntity.destinationCity.toString()//Jakarta",
                        transit.destinationAirport = transitFlightsEntity.destinationAirport.toString()//Soekarno Hatta",
                        transit.crossDay        = transitFlightsEntity.crossDay.toString()// 0.0,
                        transit.airlineName     = transitFlightsEntity.airlineName.toString()//Garuda Indonesia",
                        transitFlight.add(transit)
                    }

                    departureflightItem.connectingFlights[0].facilities?.forEachIndexed { index, it ->
                        val mFacility = FacilityFlightModel()
                        mFacility.nameFacility = it?.category.toString()
                        mFacility.valueFacility = it?.value.toString()
                        model.facility.add(mFacility)
                    }
                    model.transiteFlight.addAll(transitFlight)
                }
                else {
                    try {
                        departureflightItem.facilities.forEach {
                            val mFacility = FacilityFlightModel()
                            mFacility.nameFacility = it.category.toString()
                            mFacility.valueFacility = it.value.toString()
                            model.facility.add(mFacility)
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                val mData = AccomodationResultModel()
                mData.typeLayout        = 2 //flight = 2
                mData.listFlightModel   = model

                data.add(mData)
            }
        }

        model.result.returnFlights.forEachIndexed { index, returnFlight ->
            returnFlight.classesView.forEachIndexed { _ , classView ->
                val model = ResultListFlightModel()

                model.isFlightArrival   = true
                model.airline           = returnFlight.airline //11
                model.imgAirline        = returnFlight.airlineImageUrl.toString()
                model.titleAirline      = returnFlight.airlineName.toString()

                model.arrivalDate       = returnFlight.arrivalDate.toString()
                model.arriveDate        = returnFlight.arriveDate.toString()
                model.arriveDateTimeView    = returnFlight.arriveDateTimeView.toString()
                model.arriveTime        = returnFlight.arriveTime.toString()

                //model.category          = departureflightItem.categoryxxx
                model.nameClass          = classView.category.toString()

                model.classCode         = returnFlight.classCode.toString()
                model.classId           = returnFlight.classId.toString()
                model.code              = classView.code.toString()

                model.departDate        = returnFlight.departDate.toString()
                model.departTime        = returnFlight.departTime.toString()
                model.departureDate     = returnFlight.departureDate.toString()

                //model.duration        = departureflightItem.duration

                model.duration        = returnFlight.duration.toString()
                model.durationView    = returnFlight.durationView.toString()
                model.durationIncludeTransit        = returnFlight.durationIncludeTransit.toString()
                model.durationIncludeTransitView    = returnFlight.durationIncludeTransitView.toString()

                model.price            = classView.fare
                model.fareBasisCode    = classView.fareBasisCode.toString()
                model.fareRuleKeys      = classView.fareRuleKeys.toString()
                model.flightId          = classView.flightId.toString()
                model.flightNumber      = returnFlight.flightNumber.toString()
                model.flightType        = returnFlight.flightType.toString()
                model.flightTypeView    = returnFlight.flightTypeView.toString()

                model.id                = returnFlight.id.toString()
                model.isAvailable       = returnFlight.isAvailable
                model.isComply          = returnFlight.isComply
                model.isConnecting      = returnFlight.isConnecting
                model.isMultiClass      = returnFlight.isMultiClass
                model.isHolderFlight    = returnFlight.isHolderFlight

                model.sequence      = returnFlight.sequence

                model.number        = returnFlight.number.toString()

                model.origin            = returnFlight.origin.toString()
                model.originName        = returnFlight.originCity.toString()
                model.destination       = returnFlight.destination.toString()
                model.destinationName   = returnFlight.destinationCity.toString()

                model.originAirport     = returnFlight.originAirport.toString()
                model.destinationAirport= returnFlight.destinationAirport.toString()

                model.totalTransit      = returnFlight.totalTransit

                model.numberSeat        = classView.seat.toString()
                model.terminal          = returnFlight.originTerminal.toString()

                if (returnFlight.isConnecting){
                    val transitFlight  = ArrayList<TransiteFlight>()
                    returnFlight.connectingFlights.forEachIndexed { index, transitFlightsEntity ->
                        val transit =  TransiteFlight()
                        transit.numberFlight = transitFlightsEntity.flightNumber.toString()
                        transit.originId     = transitFlightsEntity.origin.toString()
                        transit.destinationId = transitFlightsEntity.destination.toString()
                        transit.priceTotal    = transitFlightsEntity.classesView[0].totalFare.toString() //2598000.0
                        transit.isMultiClass =  false
                        transit.isAvailable  = false
                        transit.departDate   = transitFlightsEntity.departDate.toString()// "2020-09-22"
                        transit.departTime   = transitFlightsEntity.departTime.toString()//05:25
                        transit.arriveDate   = transitFlightsEntity.arriveDate.toString() //: "2020-09-22",
                        transit.arriveTime   = transitFlightsEntity.arriveTime.toString() //""//07:00"
                        transit.duration     = transitFlightsEntity.duration.toString() //""//01:35:00",
                        transit.durationView = transitFlightsEntity.durationView.toString() //""//1h 35m",
                        transit.transitDuration = transitFlightsEntity.transitDuration.toString()//00:45:00",
                        transit.transitDurationView = transitFlightsEntity.transitDurationView.toString()//0h 45m"

                        transit.operatingNumber = transitFlightsEntity.operatingNumber.toString() //GA
                        transit.titleAirline    = transitFlightsEntity.airlineName.toString() //Garuda Indonesia
                        transit.classFlight     = transitFlightsEntity.classesView[0].category.toString()
                        transit.seatNumber      = transitFlightsEntity.classesView[0].seat.toString()
                        transit.originTerminal  = transitFlightsEntity.originTerminal.toString()//1
                        transit.destinationTerminal = transitFlightsEntity.destinationTerminal.toString()//2
                        transit.originCity      = transitFlightsEntity.originCity.toString()//Surabaya
                        transit.originAirport   = transitFlightsEntity.originAirport.toString()//"Juanda Airport"
                        transit.destinationCity = transitFlightsEntity.destinationCity.toString()//Jakarta",
                        transit.destinationAirport = transitFlightsEntity.destinationAirport.toString()//Soekarno Hatta",
                        transit.crossDay        = transitFlightsEntity.crossDay.toString()// 0.0,
                        transit.airlineName     = transitFlightsEntity.airlineName.toString()//Garuda Indonesia",
                        transitFlight.add(transit)
                    }

                    model.transiteFlight.addAll(transitFlight)
                }

                val mData = AccomodationResultModel()
                mData.typeLayout        = 2 //flight = 2
                mData.listFlightModel   = model
                data.add(mData)
            }
        }

        model.result.multiCity?.forEachIndexed { index, returnFlight ->
            returnFlight?.outgoingFlight?.forEach {
                it?.classesView?.forEachIndexed { _ , classView ->
                    val model = ResultListFlightModel()
                    model.isFlightArrival   = false
                    model.airline           = it.airline //11
                    model.imgAirline        = it.airlineImageUrl.toString()
                    model.titleAirline      = it.airlineName.toString()
                    model.arrivalDate       = it.arrivalDate.toString()
                    model.arriveDate        = it.arriveDate.toString()
                    model.arriveDateTimeView    = it.arriveDateTimeView.toString()
                    model.arriveTime        = it.arriveTime.toString()

                    //model.category          = departureflightItem.categoryxxx
                    model.nameClass          = classView.category.toString()

                    model.classCode         = it.classCode.toString()
                    model.classId           = it.classId.toString()
                    model.code              = classView.code.toString()

                    model.departDate        = it.departDate.toString()
                    model.departTime        = it.departTime.toString()
                    model.departureDate     = it.departureDate.toString()

                    //model.duration        = departureflightItem.duration

                    model.duration        = it.duration.toString()
                    model.durationView    = it.durationView.toString()
                    model.durationIncludeTransit        = it.durationIncludeTransit.toString()
                    model.durationIncludeTransitView    = it.durationIncludeTransitView.toString()

                    model.price            = classView.fare
                    model.fareBasisCode    = classView.fareBasisCode.toString()
                    model.fareRuleKeys      = classView.fareRuleKeys.toString()
                    model.flightId          = classView.flightId.toString()
                    model.flightNumber      = it.flightNumber.toString()
                    model.flightType        = it.flightType.toString()
                    model.flightTypeView    = it.flightTypeView.toString()

                    model.id                = it.id.toString()
                    model.isAvailable       = it.isAvailable
                    model.isComply          = it.isComply
                    model.isConnecting      = it.isConnecting
                    model.isMultiClass      = it.isMultiClass
                    model.isHolderFlight    = it.isHolderFlight

                    model.sequence      = it.sequence

                    model.number        = it.number.toString()

                    model.origin            = it.origin.toString()
                    model.originName        = it.originCity.toString()
                    model.destination       = it.destination.toString()
                    model.destinationName   = it.destinationCity.toString()

                    model.originAirport     = it.originAirport.toString()
                    model.destinationAirport= it.destinationAirport.toString()

                    model.totalTransit      = it.totalTransit

                    model.numberSeat        = classView.seat.toString()
                    model.terminal          = it.originTerminal.toString()

                    if (it.isConnecting){
                        val transitFlight  = ArrayList<TransiteFlight>()
                        it.connectingFlights?.forEachIndexed { index, transitFlightsEntity ->
                            val transit =  TransiteFlight()
                            transit.numberFlight = transitFlightsEntity.flightNumber.toString()
                            transit.originId     = transitFlightsEntity.origin.toString()
                            transit.destinationId = transitFlightsEntity.destination.toString()
                            transit.priceTotal    = transitFlightsEntity.classesView[0].totalFare.toString() //2598000.0
                            transit.isMultiClass =  false
                            transit.isAvailable  = false
                            transit.departDate   = transitFlightsEntity.departDate.toString()// "2020-09-22"
                            transit.departTime   = transitFlightsEntity.departTime.toString()//05:25
                            transit.arriveDate   = transitFlightsEntity.arriveDate.toString() //: "2020-09-22",
                            transit.arriveTime   = transitFlightsEntity.arriveTime.toString() //""//07:00"
                            transit.duration     = transitFlightsEntity.duration.toString() //""//01:35:00",
                            transit.durationView = transitFlightsEntity.durationView.toString() //""//1h 35m",
                            transit.transitDuration = transitFlightsEntity.transitDuration.toString()//00:45:00",
                            transit.transitDurationView = transitFlightsEntity.transitDurationView.toString()//0h 45m"

                            transit.operatingNumber = transitFlightsEntity.operatingNumber.toString() //GA
                            transit.titleAirline    = transitFlightsEntity.airlineName.toString() //Garuda Indonesia
                            transit.classFlight     = transitFlightsEntity.classesView[0].category.toString()
                            transit.seatNumber      = transitFlightsEntity.classesView[0].seat.toString()
                            transit.originTerminal  = transitFlightsEntity.originTerminal.toString()//1
                            transit.destinationTerminal = transitFlightsEntity.destinationTerminal.toString()//2
                            transit.originCity      = transitFlightsEntity.originCity.toString()//Surabaya
                            transit.originAirport   = transitFlightsEntity.originAirport.toString()//"Juanda Airport"
                            transit.destinationCity = transitFlightsEntity.destinationCity.toString()//Jakarta",
                            transit.destinationAirport = transitFlightsEntity.destinationAirport.toString()//Soekarno Hatta",
                            transit.crossDay        = transitFlightsEntity.crossDay.toString()// 0.0,
                            transit.airlineName     = transitFlightsEntity.airlineName.toString()//Garuda Indonesia",
                            transitFlight.add(transit)
                        }

                        model.transiteFlight.addAll(transitFlight)
                    }

                    val mData = AccomodationResultModel()
                    mData.typeLayout        = 2 //flight = 2
                    mData.listFlightModel   = model
                    data.add(mData)
                }
            }
        }

        return data
    }
}