package com.opsicorp.sliderdatepicker.lib

import java.util.*
import android.view.View
import android.widget.Toast
import android.content.Context
import android.util.AttributeSet
import java.text.SimpleDateFormat
import android.widget.LinearLayout
import kotlin.collections.ArrayList
import com.opsicorp.sliderdatepicker.R
import com.opsicorp.sliderdatepicker.utils.Constant
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.opsicorp.sliderdatepicker.utils.DataCalendar
import com.opsicorp.sliderdatepicker.model.CalendarModel
import com.opsicorp.sliderdatepicker.utils.CallbackCalendar
import com.opsicorp.sliderdatepicker.utils.Constant.endSelectDate
import com.opsicorp.sliderdatepicker.utils.Constant.startSelectDate
import com.opsicorp.sliderdatepicker.utils.StickHeaderItemDecoration
import com.opsicorp.sliderdatepicker.adapter.CalendarVerticalAdapter
import kotlinx.android.synthetic.main.calendar_vertical_view.view.*
import com.opsicorp.sliderdatepicker.adapter.OnclickListenerRecyclerViewParent
import com.opsicorp.sliderdatepicker.model.HolidayModel
import java.lang.Exception

class CalendarVertical : LinearLayout,
    View.OnClickListener,
    OnclickListenerRecyclerViewParent {

    val adapter by lazy { CalendarVerticalAdapter(context) }
    lateinit var callback : CallbackCalendar
    val data = ArrayList<CalendarModel>()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.calendar_vertical_view, this)

        setDafaultData()
        initRecyclerView()
    }

    private fun setDafaultData() {
        startSelectDate = ""
        endSelectDate   = ""
        Constant.minDate = Date()
        Constant.maxDate = SimpleDateFormat("dd MM yyyy").parse("20 10 2050")
        Constant.TYPE_SELECTED = Constant.DOUBLE_SELECTED
        Constant.formatDateOutput = ""
    }


    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_calendar.layoutManager = layoutManager
        rv_calendar.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        rv_calendar.setHasFixedSize(true)
        rv_calendar.addItemDecoration(StickHeaderItemDecoration(adapter));
        rv_calendar.setAdapter(adapter)


        DataCalendar().getDataDates().forEach {
            data.add(addDataHeader(it.date,it.dateStr))
            data.add(it)
        }

        adapter.setData(data)
        adapter.callbackRecyclerView(this)
    }

    private fun addDataHeader(date: Date, dateStr: String): CalendarModel {
        val data = CalendarModel(date,dateStr, ArrayList(), ArrayList(),Constant.VIEW_TYPE_HEADER)
        return data
    }

    override fun onClick(v: View?) {

    }

    fun callbackCalendarListener(callbackCalendar : CallbackCalendar){
        callback = callbackCalendar
    }


    override fun callbackRecyclerView(
        viewParent: Int,
        positionParent: Int,
        view: Int,
        position: Int) {

        when(viewParent) {
            Constant.ONCLICK_DATE -> {
                val mCalendar = Calendar.getInstance()
                if (startSelectDate.isEmpty()) {
                    if (!data[positionParent].data[position].date.before(mCalendar.time) || (SimpleDateFormat(
                                    Constant.formatDate
                            ).format(mCalendar.time) == data[positionParent].data[position].fullDay)
                    ) {
                        startSelectDate =
                                SimpleDateFormat(Constant.formatDate).format(data[positionParent].data[position].date)
                        adapter.notifyDataSetChanged()

                        if (Constant.formatDateOutput.isNotEmpty()) {
                            callback.startDate(
                                    convertFormatDate(
                                            startSelectDate,
                                            Constant.formatDate,
                                            Constant.formatDateOutput
                                    )
                            )
                        } else {
                            callback.startDate(startSelectDate)
                        }
                    }
                } else {
                    val dateSelected =
                            SimpleDateFormat(Constant.formatDate).format(data[positionParent].data[position].date)
                    if (startSelectDate == dateSelected) {
                        startSelectDate = ""
                        endSelectDate = ""
                        adapter.notifyDataSetChanged()
                    } else {
                        if (Constant.TYPE_SELECTED == Constant.SINGGLE_SELECTED) {
                            startSelectDate = SimpleDateFormat(Constant.formatDate).format(
                                    data[positionParent].data[position].date
                            )
                            adapter.notifyDataSetChanged()
                            if (Constant.formatDateOutput.isNotEmpty()) {
                                callback.startDate(
                                        convertFormatDate(
                                                startSelectDate,
                                                Constant.formatDate,
                                                Constant.formatDateOutput
                                        )
                                )
                            } else {
                                callback.startDate(startSelectDate)
                            }
                        } else {
                            if (data[positionParent].data[position].date.before(
                                            SimpleDateFormat(
                                                    Constant.formatDate
                                            ).parse(startSelectDate)
                                    )
                            ) {
                                Toast.makeText(
                                        context,
                                        "You can't select the previous date",
                                        Toast.LENGTH_LONG
                                ).show()
                            } else {
                                endSelectDate = dateSelected
                                adapter.notifyDataSetChanged()

                                if (Constant.formatDateOutput.isNotEmpty()) {
                                    callback.startDate(
                                            convertFormatDate(
                                                    startSelectDate,
                                                    Constant.formatDate,
                                                    Constant.formatDateOutput
                                            )
                                    )
                                    callback.endDate(
                                            convertFormatDate(
                                                    endSelectDate,
                                                    Constant.formatDate,
                                                    Constant.formatDateOutput
                                            )
                                    )
                                } else {
                                    callback.startDate(startSelectDate)
                                    callback.endDate(endSelectDate)
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    fun setMinDate(minDate:Date){
        Constant.minDate = minDate
    }

    fun setMaxDate(maxDate : Date){
        Constant.maxDate = maxDate
    }

    fun setStartDateSelected(date: Date){
        startSelectDate = SimpleDateFormat(Constant.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setEndDateSelected(date:Date){
        endSelectDate = SimpleDateFormat(Constant.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setTextFontDayHeader(fontDay: Int) {
        Constant.fontDayHeader = fontDay
        adapter.notifyDataSetChanged()
    }

    fun setTextFontMonthHeader(fontMonth: Int) {
        Constant.fontMonthHeader = fontMonth
        adapter.notifyDataSetChanged()
    }

    fun setTextFontDay(fontDay: Int) {
        Constant.fontDay = fontDay
        adapter.notifyDataSetChanged()
    }

    fun typeSelected(type:Int){
        Constant.TYPE_SELECTED = type
    }

    fun setFormatDateOutput(format:String){
        Constant.formatDateOutput = format
    }

    fun setDataHoliday(holiday:ArrayList<HolidayModel>){

        holiday.forEachIndexed { index, model ->
            data.forEachIndexed { i, calendarModel ->
                if (SimpleDateFormat("MM yyyy").format(model.date)==SimpleDateFormat("MM yyyy").format(calendarModel.date)){
                    Log.e("TAG",SimpleDateFormat("dd MM yyyy").format(model.date))
                    try {
                        data[i].data.filter { SimpleDateFormat("dd MM yyyy").format(it.date)==SimpleDateFormat("dd MM yyyy").format(model.date) }.first().typeDay = Constant.DAY_HOLIDAY
                    }catch (e:Exception){}
                    data[i].dataHoliday.add(model)
                }
            }
        }

        adapter.setData(data)
    }

    fun convertFormatDate(stringDate :String, formatInput:String, formatOutput:String):String{
        try {
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat(formatInput)//"yyyy-MM-dd"
            cal.setTime(sdf.parse(stringDate))
            val format = SimpleDateFormat(formatOutput,Locale.ENGLISH)//"EEEE, yyyy-MM-dd"
            return format.format(cal.getTime())
        }catch (e: Exception){
            return Constant.formatDate
        }
    }

}