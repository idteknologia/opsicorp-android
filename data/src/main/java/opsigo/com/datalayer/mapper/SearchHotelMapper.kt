package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.HotelsItem
import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.SearchHotelEntity
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.RiviewHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import java.util.ArrayList

class SearchHotelMapper {

    fun mapping(mData: SearchHotelEntity): ArrayList<AccomodationResultModel> {
        val data = ArrayList<AccomodationResultModel>()

        mData.hotels.forEachIndexed { index, hotelsItem ->
            val model = AccomodationResultModel()
            model.typeLayout                      = 4
            model.listHotelModel.addressHotel     = hotelsItem.address
            model.listHotelModel.imageHotelSorcut = hotelsItem.thumbUri
            model.listHotelModel.imageHotel       = hotelsItem.imageUri
            model.listHotelModel.nameHotel        = hotelsItem.hotelName
            model.listHotelModel.typeHotel        = "Hotel"
            model.listHotelModel.starRating       = hotelsItem.starRating.toString()
            model.listHotelModel.rating           = hotelsItem.placeRating.toString()
            model.listHotelModel.addressHotel     = hotelsItem.address
            model.listHotelModel.prize            = hotelsItem.price.toInt().toString()
            model.listHotelModel.lat              = hotelsItem.latitude.toString()
            model.listHotelModel.long             = hotelsItem.longitude.toString()
            model.listHotelModel.city             = hotelsItem.cityName
            model.listHotelModel.reviews          = reviewsMapper()
            model.listHotelModel.faciltyHotel     = facilityMapper()
            model.listHotelModel.hotelKey         = hotelsItem.hotelKey
            model.listHotelModel.correlationId    = mData.correlationId
            model.listHotelModel.totalAvailable   = hotelsItem.rooms.size.toString()
            model.listHotelModel.room             = mappingRoom(mData,hotelsItem)
            model.listHotelModel.descriptioHotel  = ""

            data.add(model)
        }
        return data
    }

    private fun mappingRoom(mData: SearchHotelEntity, hotelsItem: HotelsItem): ArrayList<SelectRoomModel> {
        val data = ArrayList<SelectRoomModel>()
        hotelsItem.rooms.forEach {
            val model = SelectRoomModel()
            model.typeRefund        = ""
            model.titleRoom         = it.roomName
            model.BedFacility       = ""
            model.listFacility      = ""
            model.policyDescription = ""
            model.breakfastType     = ""
            model.cancelLimit       = ""
            model.prize             = it.totalPrice.toString()
            model.roomCodeHash      = it.roomHash
            model.roomKey           = it.roomKey
            model.isGuaranteedBooking = false
            model.isFullCharge        = false
            model.isBreakfast         = it.includeBreakfast
            model.isGuaranteedBooking  = it.isGuaranteedBooking
            data.add(model)
        }
        return data
    }

    private fun facilityMapper(): ArrayList<FacilityHotelModel> {
        return ArrayList()
    }

    private fun reviewsMapper(): ArrayList<RiviewHotelModel> {
        return ArrayList()
    }
}