package com.opsicorp.sliderdatepicker.utils

import java.text.SimpleDateFormat
import java.util.*

object Constant {
    var formatDateOutput   = ""
    val formatDate         = "dd-MM-yyyy"
    val VIEW_TYPE_HEADER   = 1
    val VIEW_TYPE_BODY     = 2
    val DAY_PREVIOUS_MONTH = 3
    val DAY_THIS_MONTH     = 4
    val DAY_NEXT_MONTH     = 5
    val ONCLICK_DATE       = 6
    val DAY_SUNDAY         = 7
    val DAY_HOLIDAY        = 9
    var fontDay            = -1
    var fontMonthHeader    = -1
    var fontDayHeader      = -1
    val SINGGLE_SELECTED   = 7
    val DOUBLE_SELECTED    = 8
    var TYPE_SELECTED  = DOUBLE_SELECTED
    var startSelectDate    = ""
    var endSelectDate      = ""



    var minDate =  Date()
    var maxDate =  SimpleDateFormat("dd MM yyyy").parse("20 10 2050")
}
