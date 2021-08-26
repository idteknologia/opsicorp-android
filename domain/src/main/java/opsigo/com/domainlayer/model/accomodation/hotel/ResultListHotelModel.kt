package opsigo.com.domainlayer.model.accomodation.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResultListHotelModel (
    var checkIn      :String = "",
    var checkOut     :String = "",
    var duration     :String = "",
    var imageHotelSorcut :String = "",
    var imageHotel   :String = "",
    var typeHotel    :String = "",
    var starRating   :String = "",
    var totalGuest   :Int = 1,
    var rating          :String = "",
    var price           :String = "",
    var correlationId   :String = "",
    var city            :String = "",
    var idCity          :String = "",
    var idCountry       :String = "",
    var totalAvailable  :String = "",
    var nameHotel       :String = "",
    var addressHotel    :String = "",
    var lat             :String = "",
    var long            :String = "",
    var hotelKey        :String = "",
    var room            :ArrayList<SelectRoomModel>    = ArrayList(),
    var galery          :ArrayList<GaleryModel>        = ArrayList(),
    var reviews         :ArrayList<RiviewHotelModel>   = ArrayList(),
    var faciltyHotel    :ArrayList<FacilityHotelModel> = ArrayList(),
    var descriptioHotel :String = ""
): Parcelable