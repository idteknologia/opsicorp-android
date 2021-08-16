package opsigo.com.domainlayer.model.booking_contact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import opsigo.com.domainlayer.model.summary.PassportModel

@Parcelize
class BookingContactAdapterModel (
    var typeContact :String = "",
    var checktype   :String = "",
    var idcard      :IdCartModel =IdCartModel(),
    var pasport     :PassportModel= PassportModel(),
    var sim         :SimModel = SimModel(),
    var infantID    :InfantIdModel = InfantIdModel(),
    var ssr         :SsrModel = SsrModel()
):Parcelable