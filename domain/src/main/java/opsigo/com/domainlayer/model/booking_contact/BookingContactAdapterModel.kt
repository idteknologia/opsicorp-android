package opsigo.com.domainlayer.model.booking_contact

import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import opsigo.com.domainlayer.model.summary.PassportModel

class BookingContactAdapterModel {
    var typeContact = ""
    var checktype   = ""
    var idcard      = IdCartModel()
    var pasport     = PassportModel()
    var sim         = SimModel()
    var infantID    = InfantIdModel()
    var ssr         = SsrModel()
}