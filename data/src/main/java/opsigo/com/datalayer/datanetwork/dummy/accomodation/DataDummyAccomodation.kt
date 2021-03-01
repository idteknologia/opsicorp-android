package opsigo.com.datalayer.datanetwork.dummy.accomodation

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.flight.DataSsrModel
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.RiviewHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel

class DataDummyAccomodation {

    fun addDataListFlight():ArrayList<AccomodationResultModel>{

        val mDataList = ArrayList<AccomodationResultModel>()
        val mDataImageFlight = ArrayList<String>()
        val nameFlight      = ArrayList<String>()

        mDataList.clear()
        mDataImageFlight.clear()
        nameFlight.clear()

        mDataImageFlight.add("https://i.ibb.co/60gXJPG/logo-air-asia.png")
        mDataImageFlight.add("https://i.ibb.co/C0XzT6K/sriwijaya.png")
        mDataImageFlight.add("https://i.ibb.co/RjbS8Fd/garuda-indonesia.png")

        nameFlight.add("Air asia")
        nameFlight.add("Sriwijaya")
        nameFlight.add("garuda")

        mDataImageFlight.forEachIndexed { index, s ->
            val accomodationResultModel = AccomodationResultModel()
            val data = ResultListFlightModel()
            data.titleAirline    = nameFlight.get(index)
            data.imgAirline      = s
//            data.numberSheet     = "GA-210"
//            data.duration        = "1h, 30m"
//            data.typeClass       = "Economy (C)"
//            data.destination     = "CGK - JOG"
//            data.timeDeparture   = "06:45 - 09:40"
//            data.price           = "IDR 719.000"
//            data.transit         = "1 Transit"

            accomodationResultModel.listFlightModel = data
            mDataList.add(accomodationResultModel)
        }

        return mDataList
    }

    fun addDataListTrain():ArrayList<AccomodationResultModel>{

        val mDataList = ArrayList<AccomodationResultModel>()
        val nameFlight      = ArrayList<String>()

        mDataList.clear()
        nameFlight.clear()

        nameFlight.add("Gajayana")
        nameFlight.add("Pangandaran")
        nameFlight.add("Argo Parahyangan")
        nameFlight.add("Argo Parahyangan Extend")
        nameFlight.add("Argo Bromo")
        nameFlight.add("Jayabaya")
        nameFlight.add("Jayabaya Eksekutif")
        nameFlight.add("Jayabaya Bisnis")
        nameFlight.add("Argo Bromao Bisnis")
        nameFlight.add("Gajayana Bisnis")

        nameFlight.forEachIndexed { index, s ->
            val accomodationResultModel = AccomodationResultModel()
            val data = ResultListTrainModel()
            data.titleTrain      = nameFlight.get(index)
            data.totalSeat      = "GA-210"
            data.duration        = "1h, 30m"
            data.className       = "Executive (M)"
            data.nameStation     = "CGK - JOG"
            data.timeDeparture   = "07:50 - 11:07"
            data.timeArrifal     = "09:40"
            data.price           = "IDR 7${index}9.000"

            accomodationResultModel.listTrainModel = data
            mDataList.add(accomodationResultModel)
        }
        return mDataList
    }

    fun addDataLoading():ArrayList<AccomodationResultModel>{
        val data = ArrayList<AccomodationResultModel>()
        for (i in 0 until 10){
            val mData = AccomodationResultModel()
            mData.typeLayout = 5
            data.add(mData)
        }
        return data
    }

    fun addDataLoadingHotel():ArrayList<AccomodationResultModel>{
        val data = ArrayList<AccomodationResultModel>()
        for (i in 0 until 10){
            val mData = AccomodationResultModel()
            mData.typeLayout = 8
            data.add(mData)
        }
        return data
    }

    fun addDataListHotel():ArrayList<AccomodationResultModel>{

        val data   = ArrayList<AccomodationResultModel>()
        val images = ArrayList<String>()
        val nameHotel =  ArrayList<String>()
        val dummyFacilty = ArrayList<String>()
        val dummyReview  = ArrayList<String>()
        val imageFacility = ArrayList<String>()

        data.clear()
        images.clear()
        dummyFacilty.clear()
        dummyReview.clear()
        imageFacility.clear()

        dummyReview.add("Nur baity")
        dummyReview.add("tommy walon")
        dummyReview.add("Adit")
        dummyReview.add("didik")

        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")
        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")
        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")
        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")
        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")
        imageFacility.add("https://i.ibb.co/qR5fhc9/91-ac.png")

        dummyFacilty.add("Ac")
        dummyFacilty.add("Restaurant")
        dummyFacilty.add("Swimming Pool")
        dummyFacilty.add("24 Hours Room Service")
        dummyFacilty.add("Gym")
        dummyFacilty.add("Parking")

        dummyFacilty.add("Air Condition")
        dummyFacilty.add("Airport Transfer")
        dummyFacilty.add("Banquet")
        dummyFacilty.add("Bar Lounge")
        dummyFacilty.add("Bath Robe")
        dummyFacilty.add("Buffet")
        dummyFacilty.add("Coffee")
        dummyFacilty.add("Concierge")
        dummyFacilty.add("Drycleaning Onsite")
        dummyFacilty.add("Elevator")
        dummyFacilty.add("Fitness Center")
        dummyFacilty.add("Hairdryer")
        dummyFacilty.add("Laundry")
        dummyFacilty.add("Luggage Storage")
        dummyFacilty.add("Massage")
        dummyFacilty.add("Meeting Rooms")
        dummyFacilty.add("Minibar")
        dummyFacilty.add("Money Exchange")
        dummyFacilty.add("Morningcall")
        dummyFacilty.add("Newspaper")
        dummyFacilty.add("Non Smoking Room")
        dummyFacilty.add("Parking")
        dummyFacilty.add("Pets Not Allowed")
        dummyFacilty.add("Rent car")
        dummyFacilty.add("Restaurant")
        dummyFacilty.add("Room service")
        dummyFacilty.add("Safe Deposit Box")
        dummyFacilty.add("Shuttle Service")
        dummyFacilty.add("Spa")
        dummyFacilty.add("Swimming Pool")
        dummyFacilty.add("Tabletennis")
        dummyFacilty.add("Teacoffee")
        dummyFacilty.add("Telephone")
        dummyFacilty.add("TV")
        dummyFacilty.add("Wheelchair Accessible")
        dummyFacilty.add("Wifi")

        nameHotel.add("The Trans Luxury Bandung")
        nameHotel.add("Courtyard by Marriott Dago B...")
        nameHotel.add("Banana Inn Hotel and Spa")
        nameHotel.add("Citradream")

        images.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/55C1A0B5-6FAC-4728-8635-BFF6D2A4D006.png")
        images.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/00FAF8E9-EAF7-4C06-AD6D-A9DB9E9EBF9E.png")
        images.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/B99967CF-EB64-4CCB-AD1D-7CE09F9CBD46.png")
        images.add("https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/B687D9D1-119D-4F6D-8CCB-1AA039DF2F0C.png")

        images.forEachIndexed { index, image ->
            val mData = AccomodationResultModel()

            val listHotelModel = ResultListHotelModel()

            val reviewHotel = ArrayList<RiviewHotelModel>()
            val facility    = ArrayList<FacilityHotelModel>()

            dummyReview.forEachIndexed { index, r ->
                val mReview     = RiviewHotelModel()
                mReview.name    = r
                mReview.massage = "This is by far the best in term of payment. You can book via website or application and pay at the hotel without ... "
                reviewHotel.add(mReview)
            }

            dummyFacilty.forEachIndexed { index, f ->
                val mFacility = FacilityHotelModel()
                mFacility.name  = f
                if (index<6){
//                    mFacility.image = imageFacility[index]
                }
                else{
                    mFacility.image = 0
                }
                facility.add(mFacility)
            }


            listHotelModel.reviews = reviewHotel
            listHotelModel.faciltyHotel = facility
            listHotelModel.imageHotel   = "https://i.ibb.co/q56pgg1/hilton-bandung.png"
            listHotelModel.imageHotelSorcut = image
            listHotelModel.nameHotel    = nameHotel[index]
            listHotelModel.typeHotel    = "Hotels"
            listHotelModel.starRating  = "4"
            listHotelModel.rating       = "4.5"
            listHotelModel.addressHotel = "Jl. HOS Tjokroaminoto No. 41-43 40171 \n" + "Bandung Java Indonesia, 10250"
            listHotelModel.city         = "Arjuna, Bandung"
            listHotelModel.prize        = "2561551"
            listHotelModel.lat          = "-6.175906"
            listHotelModel.long         = "106.8121863"
            listHotelModel.totalAvailable  = (index+1).toString()
            listHotelModel.descriptioHotel = "Hotel Trans Luxury Bandung is in Jakarta's business district and offers luxurious accommodations with spacious sitting areas and a marble bathtub. It features a rejuvenating spa and an outdoor hot tub.\n"

            mData.listHotelModel = listHotelModel
            mData.typeLayout     = 4

            data.add(mData)
        }

        return data
    }



    fun addDataDepartureTime():ArrayList<FilterFlightModel>{
        val data = ArrayList<FilterFlightModel>()

        val names = ArrayList<String>()
        names.add("Early Morning")
        names.add("Morning")
        names.add("Morning")
        names.add("Night")

        val times = ArrayList<String>()
        times.add("00:00 - 06:00")
        times.add("06:00 - 12:00")
        times.add("12:00 - 18:00")
        times.add("18:00 - 24:00")

        names.forEachIndexed { index, s ->
            val mData = FilterFlightModel()
            mData.id = "${index+1}"
            mData.name = s
            mData.time = times.get(index)
            data.add(mData)
        }

        return data
    }

    fun addCabinClass():ArrayList<FilterFlightModel>{
        val data = ArrayList<FilterFlightModel>()

        val names = ArrayList<String>()
        names.add("Economy")
        names.add("Business")
        names.add("Premium Economy")
        names.add("First Class")

        names.forEachIndexed { index, s ->
            val mData = FilterFlightModel()
            mData.id = "${index+1}"
            mData.name = s
            data.add(mData)
        }

        return data
    }

    fun addBaggae():ArrayList<DataSsrModel>{
        val data = ArrayList<DataSsrModel>()

        val value = ArrayList<String>()
        value.add("0 Kg")
        value.add("5 Kg")
        value.add("10 Kg")
        value.add("15 Kg")
        value.add("20 Kg")

        val price = ArrayList<Int>()
        price.add(0)
        price.add(100000)
        price.add(200000)
        price.add(300000)
        price.add(400000)

        value.forEachIndexed { index, s ->
            val mData = DataSsrModel()
            mData.id = "${index+1}"
            mData.ssrName = s
            mData.pricing = price.get(index).toString()
            data.add(mData)
        }

        return data
    }

    fun addDataDummyRoom():ArrayList<SelectRoomModel>{
        val data = ArrayList<SelectRoomModel>()

        val title = ArrayList<String>()
        title.add("Deluxe Room")
        title.add("Deluxe Room")
        title.add("Superior Room")
        title.add("Basic Room")
        title.add("Deluxe Room")
        title.add("Sultan Room")

        val facility = ArrayList<String>()
        facility.add("Guaranteed")
        facility.add("Guaranteed")
        facility.add("Free Meal")
        facility.add("Free Cancelation")
        facility.add("Guaranteed")
        facility.add("Free Meal")

        title.forEachIndexed { index, s ->
            val mData = SelectRoomModel()
            mData.typeRefund        = facility[index]
            mData.titleRoom         = s
            mData.BedFacility       = "Twin bed or Double bed"
            mData.listFacility      = ""
            mData.policyDescription = "This Reservation is non-refundable"
            mData.prize             = "1.${index}00.000"
            data.add(mData)
        }

        return data

    }
}