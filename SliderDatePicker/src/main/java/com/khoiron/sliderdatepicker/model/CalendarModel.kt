package com.khoiron.sliderdatepicker.model

import java.util.*
import kotlin.collections.ArrayList

data class CalendarModel(var date        : Date = Date(),
                         var dateStr     : String = "",
                         var data        : ArrayList<DayDataModel> = ArrayList(),
                         var dataHoliday : ArrayList<HolidayModel> = ArrayList(),
                         var typeLayout  : Int
    )