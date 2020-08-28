package com.opsigo.travelaja.module.my_booking.purchase_list_detail

import com.opsigo.travelaja.module.my_booking.model.*

object DummyDataPurchaseFlight {
    fun getDataPageFlight(): DetailFlightPurchaseModel {
        val data = DetailFlightPurchaseModel()

        for (i in 0..1){
            var mData =  PurchaseDetailTripFlightAndTrainModel()
            mData.status                  = "Body"
            mData.nameFlight              = "Sriwijaya Air"
            mData.numberSeat              = "SA-110"
            mData.classFlight             = "Economy Class"
            mData.typeFlight              = "Boeing 737"
            mData.timeDeparture           = "13:00"
            mData.dateDepartute           = "Thu, 27 Apr"
            mData.nameAirportDepature     = "Jakarta (CGK)"
            mData.addressAirportDeparture = "Soekarno Hatta International Airport"
            mData.terminalDeparture       = "Terminal 2F"
            mData.timeArrival             = "15:20"
            mData.totalHour               = "2h 20m"
            mData.dateArrival             = "Thu, 27 Apr"
            mData.nameStasiunArrival      = "Jogjakarta (JOG)"
            mData.addressStationArrival   = "Adi Sutjipto Airport"

            data.flights.add(mData)
            if (i == 0){
                val mData =  PurchaseDetailTripFlightAndTrainModel()
                mData.layover            = "Layover: 1h 35m"
                mData.nameAirportLayover = "Adi Sutjipto Airport"
                mData.status             = "Header"
                data.flights.add(mData)
            }
        }

        val mData1 =  PurchaseDetailTripFlightAndTrainModel()
        mData1.layover            = "Layover: 1h 35m"
        mData1.nameAirportLayover = "Adi Sutjipto Airport"
        mData1.status             = "Header"
        data.flights.add(mData1)

        var mData =  PurchaseDetailTripFlightAndTrainModel()
        mData.status                  = "Body"
        mData.nameFlight              = "Sriwijaya Air"
        mData.numberSeat              = "SA-110"
        mData.classFlight             = "Economy Class"
        mData.typeFlight              = "Boeing 737"
        mData.timeDeparture           = "13:00"
        mData.dateDepartute           = "Thu, 27 Apr"
        mData.nameAirportDepature     = "Jakarta (CGK)"
        mData.addressAirportDeparture = "Soekarno Hatta International Airport"
        mData.terminalDeparture       = "Terminal 2F"
        mData.timeArrival             = "15:20"
        mData.totalHour               = "2h 20m"
        mData.dateArrival             = "Thu, 27 Apr"
        mData.nameStasiunArrival      = "Jogjakarta (JOG)"
        mData.addressStationArrival   = "Adi Sutjipto Airport"

        data.flights.add(mData)



//        288

        for (i in 0 until 1){
            val mData = ImportanPreProductInfoModel()
            mData.description = "Bukti pemesanan ini harus ditukarkan dengan Boarding Pass mulai 7x24 jam sebelum keberangkatan KA pada mesin check-in counter di stasiun keberangkatan."
            data.importan.add(mData)

        }

        for (i in 0 until 2){
            val mData = PassangerPurchaseModel()
            mData.age           = "Adult"
            mData.totalBagage   = "KTP - 36858786410001"
            mData.Name          = "Vody Andrian"
            mData.totalPassager = "Executive 2 / Seat 8B"
            data.passanger.add(mData)
        }


        data.totalPrize  = "IDR 1.500.819.000 "
        return data
    }

    fun getDataPageTrain(): DetailTrainPurchaseModel {
        val data = DetailTrainPurchaseModel()
        data.nameTrain               = "Argo Parahyangan"
        data.numberSeat              = "KA-210"
        data.classTrain              = "Executive(A)"
        data.timeDeparture           = "16:55"
        data.dateDepartute           = "Thu, 27 Apr"
        data.nameStasiunDepature     = "Jakarta (GMR)"
        data.addressStationDeparture = "Gambir Station"
        data.timeArrival             = "22:55"
        data.dateArrival             = "Thu, 27 Apr"
        data.nameStasiunArrival      = "Bandung (BD)"
        data.addressStationArrival   = "Bandung Hall Station"
        data.totalPrize              = "IDR 1.500.819.000"

        for (i in 0 until 1){
            val mData = ImportanPreProductInfoModel()
            mData.description = "Bukti pemesanan ini harus ditukarkan dengan Boarding Pass mulai 7x24 jam sebelum keberangkatan KA pada mesin check-in counter di stasiun keberangkatan."
            data.importan.add(mData)

        }

        for (i in 0 until 4){
            val mData = PassangerPurchaseModel()
            mData.age           = "Adult"
            mData.totalBagage   = "KTP - 36858786410001"
            mData.Name          = "Vody Andrian"
            mData.totalPassager = "Executive 2 / Seat 8B"
            data.passanger.add(mData)
        }

        return data
    }

    fun getDataPageHote(): DetailHotelPurchaseModel {
        val data = DetailHotelPurchaseModel()
        data.id                    = "1"
        data.code                  = "284724SH"
        data.nameHotel             = "The Trans Luxury Bandung"
        data.rating                = "4"
        data.locationHotel         = "Arjuna, Bandung"
        data.dateCheckIn           = "Thu, 18 Apr"
        data.timeCheckIn           = "14:00"
        data.totalNight            = "1 night(s)"
        data.dateCheckOut          = "Fri, 19 Apr"
        data.timeCheckOut          = "14:00"
        data.nameBookingContact    = "Vodyrizam"
        data.addressHotel          = "Jl. Lengkong Kecil 76-80, Asia Afrika, /n Arjuna, Bandung, Jawa Barat, Indonesia 536563"
        data.latitude              = "Superior Room"
        data.longitude             = "Max 2 Guest/room"
        val facility              = ArrayList<DetailHotelPurchaseModel.FacilityRoomModel>()
        val cancellationPolicy    = ArrayList<String>()
        val remark                = ArrayList<String>()
        val guest                 = ArrayList<String>()
        data.facility             = facility
        data.cancellationPolicy   = cancellationPolicy
        data.remark               = remark
        data.guest                = guest
        data.totalPrice           = "IDR 1.500.819.000 "

        for (i in 0 until 4){

            val mData = DetailHotelPurchaseModel.FacilityRoomModel()
            mData.image         = ""
            mData.nameFacility  = "Breakfast"

        }

        for (i in 0 until 4){
            cancellationPolicy.add("\t• \tThis reservation is non-refundable\n" +
                    "\t• \tStay period and room type are non-rechargable\n" +
                    "\t• \tGuaranted Booking")
        }

        for (i in 0 until  4){
            remark.add("\t• \tRequest Twin bed room for Mr Zulfadli and Mr Hendy Mrs Ira Guci Non Smoking room\n" +
                    "\t• \tNON SMOKING ROOM, LATE CHECK IN (FLIGHT ARRIVED BY 18.50 GA322. KING SIZE BED WITH 1 BREAKFAST\n" +
                    "\t• \tWith Breakfast Twin Bed No Smoking Room Lantai Kamar jangan yang tinggi-tinggi di sahakan Lt. 3 Ke \n" +
                    "\t• \tSAKUDA/KAZUTOYO MR & HIROKI/NAKAO  MR - Smoking room OKI/TAKAFUSA  MR - Non Smoking room")
        }

        for (i in 0 until 4){
            guest.add("Vody Andrian")
        }

        return data
    }

    fun addDataRoomFacility():ArrayList<ImportanPreProductInfoModel>{
        val mData = ArrayList<String>()
        mData.add("Breakfast")
        mData.add("Free Wifi")
        mData.add("No Smoking")
        mData.add("Breakfast not included (Room only)")
        val list = ArrayList<ImportanPreProductInfoModel>()

        mData.forEachIndexed { index, s ->
            val data = ImportanPreProductInfoModel()
            data.description = s
            list.add(data)
        }
        return list
    }


    fun addDataHotelMessage():ArrayList<String>{
        val mData = ArrayList<String>()
        mData.add("Guaranteed Late Check-Out till 6pm")
        mData.add("\$5 Ya Kun Kaya Toast Food Voucher Per Room")
        mData.add("\$5 Old Chang Kee Food Voucher Per Room")
        mData.add("1 Customised Tote Bag")
        mData.add("1 Customised Lunch Bag")
        return mData
    }

    fun addDataCancelPolicy():ArrayList<String>{
        val mData = ArrayList<String>()
        mData.add("This reservation is non-refundable")
        mData.add("Stay period and room type are non-rechargable")
        mData.add("Guaranted Booking")
        return mData
    }

    fun addDataRemark():ArrayList<String>{
        val mData = ArrayList<String>()
        mData.add("Request Twin bed room for Mr Zulfadli and Mr Hendy Mrs Ira Guci Non Smoking room")
        mData.add("NON SMOKING ROOM, LATE CHECK IN (FLIGHT ARRIVED BY 18.50 GA322. KING SIZE BED WITH 1 BREAKFAST")
        mData.add("With Breakfast Twin Bed No Smoking Room Lantai Kamar jangan yang tinggi-tinggi di sahakan Lt. 3 Ke")
        mData.add("SAKUDA/KAZUTOYO MR & HIROKI/NAKAO  MR - Smoking room OKI/TAKAFUSA  MR - Non Smoking room")
        return mData
    }

    fun addDataGuest():ArrayList<PassangerPurchaseModel>{
        val list = ArrayList<PassangerPurchaseModel>()

        for ( i in 0..2){
            val mData = PassangerPurchaseModel()
            mData.Name ="Vody"
            mData.totalBagage ="Max 3 Guest/room"
            mData.age   = "Triple"
            list.add(mData)
        }

        return list
    }

}