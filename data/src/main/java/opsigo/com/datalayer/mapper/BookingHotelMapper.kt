package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.booking.BookingHotelEntity
import opsigo.com.domainlayer.model.accomodation.hotel.BookingHotelModel

class BookingHotelMapper {
    fun mapping(response: String):BookingHotelModel{
        val data = BookingHotelModel()
        val model = Serializer.deserialize(response, BookingHotelEntity::class.java)

        data.hotelId      = model.model?.hotel?.id.toString()
        data.mapUri       = model.model?.hotel?.mapUri.toString()
        data.nameHotel    = model.model?.hotel?.name.toString()
        data.pnr          = model.model?.hotel?.pnrId.toString()
        data.image        = model.model?.hotel?.image.toString()

        return data
    }
}