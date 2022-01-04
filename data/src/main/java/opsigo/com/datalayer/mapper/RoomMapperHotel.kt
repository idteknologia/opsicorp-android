package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.room.RoomHotelEntity
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel

class RoomMapperHotel {
    fun mapping(data:RoomHotelEntity):ArrayList<SelectRoomModel>{
        val mData = ArrayList<SelectRoomModel>()
        data.cancelPolicy.rooms.forEachIndexed { index, roomsItem ->
            val model = SelectRoomModel()
            model.titleRoom         = roomsItem.roomName
            model.roomKey           = roomsItem.roomKey
            model.roomCodeHash      = roomsItem.roomCodeHash
            model.breakfastType     = roomsItem.breakfastType
            model.cancelLimit       = roomsItem.cancelLimit
            model.isGuaranteedBooking = roomsItem.isGuaranteedBooking
            model.isFullCharge      = roomsItem.isFullCharge
            model.summary           = roomsItem.summaries
            mData.add(model)
        }
        return mData
    }
}
