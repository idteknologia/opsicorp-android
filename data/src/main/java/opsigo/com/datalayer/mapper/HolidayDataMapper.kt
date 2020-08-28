package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.calendar.CalendarHolidayEntity
import opsigo.com.domainlayer.model.HolidayModel
import java.text.SimpleDateFormat

class HolidayDataMapper {
    fun transform(data:CalendarHolidayEntity):ArrayList<HolidayModel>{
        val mData = ArrayList<HolidayModel>()
        data.data.forEachIndexed { index, dataItemHolidayEntity ->
            val formarter  = SimpleDateFormat("yyyy-MM-dd")
            val model = HolidayModel()
            model.country =  dataItemHolidayEntity.country
            model.dateholiday = formarter.parse("${dataItemHolidayEntity.dateholiday.split("T")[0]}")
            model.year        = dataItemHolidayEntity.year.toString()
            model.nameholiday = dataItemHolidayEntity.nameholiday
            mData.add(model)
        }
        return mData
    }
}