package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.detail.FacilitiesItem
import opsigo.com.datalayer.model.accomodation.hotel.detail.HotelDetailEntity
import opsigo.com.datalayer.model.accomodation.hotel.detail.ImagesItem
import opsigo.com.datalayer.model.accomodation.hotel.detail.ReviewsItem
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.GaleryModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.RiviewHotelModel

class HotelDetailMapper {
    fun mapping(mData: HotelDetailEntity): ResultListHotelModel {
        val data = ResultListHotelModel()
        data.nameHotel          = mData.data.hotelName
        data.hotelKey           = mData.data.hotelKey
        data.addressHotel       = mData.data.address
        data.lat                = mData.data.latitude.toString()
        data.long               = mData.data.longitude.toString()
        data.reviews            = mappingRivew(mData.data.reviews)
        data.faciltyHotel       = mappingFacility(mData.data.facilities)
        data.galery             = mappingGalery(mData.data.images)
        data.descriptioHotel    = mData.data.description.toString()
        return data
    }

    private fun mappingGalery(images: List<ImagesItem>): ArrayList<GaleryModel> {
        val data = ArrayList<GaleryModel>()
        images.forEach {
            val model = GaleryModel()
            model.description = it.description
            model.imageUri    = it.imageUri
            model.thumbUri    = it.thumbUri
            data.add(model)
        }
        return data
    }

    private fun mappingFacility(facilities: List<FacilitiesItem>): ArrayList<FacilityHotelModel> {
        val data = ArrayList<FacilityHotelModel>()
        facilities.forEach {
            val model = FacilityHotelModel()
            model.name  = it.description
            model.code    = it.code
            data.add(model)
        }
        return data
    }

    private fun mappingRivew(reviews: List<ReviewsItem>): ArrayList<RiviewHotelModel> {
        val data = ArrayList<RiviewHotelModel>()
        reviews.forEach {
            val model     = RiviewHotelModel()
            model.image   = it.uri
            model.massage = it.text
            model.time    = it.relativeTime.toString()
            model.rating  = it.rating
            model.name    = it.authorName
            data.add(model)
        }
        return data
    }
}