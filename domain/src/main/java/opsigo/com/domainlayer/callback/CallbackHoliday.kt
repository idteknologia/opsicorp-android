package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.HolidayModel

interface CallbackHoliday {
    fun success(transform: ArrayList<HolidayModel>)
    fun failed(string: String)
}