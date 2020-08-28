package com.opsigo.library

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.opsigo.library.adapter.CalendarAdapter
import com.opsigo.library.impl.OnDaySelectedListener
import com.opsigo.library.model.ColorData
import com.opsigo.library.model.DayData
import com.opsigo.library.utils.*

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

import pyxis.uzuki.live.pyxinjector.PyxInjector

class CustomCalendarView : LinearLayout, AdapterView.OnItemClickListener {
    private val injector = PyxInjector()
    private var onDaySelectedListener: OnDaySelectedListener? = null

    lateinit var btnPrevMonth: ImageButton
    lateinit var txtDayText: TextView
    lateinit var btnNextMonth: ImageButton
    lateinit var gridView: GridView

    private var mCalendar: Calendar? = null
    lateinit var mAdapter: CalendarAdapter
    private var mDateFormatStr = "MMM yyyy"
    private var mErrToastStr = ""
    private var mColorData: ColorData? = null
    private var mStartDay = ""
    private var mEndDay = ""
    private var mNowFullDay: String? = null
    private var mPreventPreviousDate = true
    private val mList = ArrayList<DayData>()

    private var x1: Float = 0.toFloat()
    private var x2: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun setOnDaySelectedListener(onDaySelectedListener: OnDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener
    }

    fun buildCalendar() {
        mStartDay = ""
        mEndDay = ""

        mAdapter     = CalendarAdapter(context, mColorData, mPreventPreviousDate)

        gridView.adapter = mAdapter

        makeCalendar()
        gridView.onItemClickListener = this
        btnPrevMonth.setOnClickListener { goToPrevMonth() }
        btnNextMonth.setOnClickListener { goToNextMonth() }
    }

    fun setDateFormat(dateFormat: String) {
        this.mDateFormatStr = dateFormat
    }

    fun goToPrevMonth() {
        mCalendar!!.add(Calendar.MONTH, -1)
        makeCalendar()
    }

    fun goToNextMonth() {
        mCalendar!!.add(Calendar.MONTH, +1)
        makeCalendar()
    }


    fun clearDate() {
        clearState()
    }

    fun sendNowValue() {
        if (notEmptyString(mStartDay, mEndDay)) {
            sendCallback()
        }
    }

    fun setErrToastMessage(message: String) {
        this.mErrToastStr = message
    }


    fun setErrToastMessage(messageRes: Int) {
        this.mErrToastStr = context.getString(messageRes)
    }

    fun setColorData(colorData: ColorData) {
        this.mColorData = colorData
    }

    fun setStartDay(startDay: Date) {
        mStartDay = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(startDay)
        mAdapter.setStart(true)
        mAdapter.setStartDay(mStartDay)
        mAdapter.notifyDataSetChanged()
        sendCallback()
    }

    fun setEndDay(endDay: Date) {
        mEndDay = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(endDay)
        mAdapter.setEnd(true)
        mAdapter.setEndDay(mEndDay)
        mAdapter.notifyDataSetChanged()
        sendCallback()
    }

    fun setPreventPreviousDate(preventPreviousDate: Boolean) {
        mPreventPreviousDate = preventPreviousDate
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val (_, _, _, dayStr, fullDay) = mAdapter!!.getItem(position)
        if (mAdapter.getItem(position) == null || TextUtils.isEmpty(fullDay)) {
            return
        }

        val dayFormatted = String.format("%s.%s.%s", mCalendar!!.get(Calendar.YEAR),
                assignPad10(mCalendar!!.get(Calendar.MONTH) + 1), assignPad10(dayStr))

        if (!mAdapter.isStart) {
            if (mPreventPreviousDate && compareLess(fullDay, mNowFullDay!!)) {
                showErrToast()
                return
            }

            mAdapter.isStart = !mAdapter!!.isStart
            mStartDay = dayFormatted
            mAdapter.notifyDataSetChanged()
            mAdapter.setStartDay(mStartDay)

            sendCallback()
            return
        }

        if (compareDayEqual(mStartDay, fullDay)) {
            clearState()
            return
        }

        if (mPreventPreviousDate && compareLess(fullDay, mNowFullDay!!)) {
            showErrToast()
            return
        }

        if (compareDayGreat(mStartDay, fullDay)) {
            showErrToast()
            return
        }

        mEndDay = dayFormatted
        mAdapter.setEnd(true)
        mAdapter.setEndDay(mEndDay)
        mAdapter.notifyDataSetChanged()
        sendCallback()
    }

    private fun init() {
        View.inflate(context, R.layout.calendar_view, this)
        injector.execute(context, this, this)

        gridView     = findViewById(R.id.gridView)
        btnPrevMonth = findViewById(R.id.btnPrevMonth)
        txtDayText   = findViewById(R.id.txtDayText)
        btnNextMonth = findViewById(R.id.btnNextMonth)

        mColorData = ColorData.Builder().build()
        mCalendar = Calendar.getInstance()
        mNowFullDay = mCalendar!!.get(Calendar.YEAR).toString()
        mNowFullDay += assignPad10(mCalendar!!.get(Calendar.MONTH) + 1)
        mNowFullDay += assignPad10(mCalendar!!.get(Calendar.DAY_OF_MONTH))
    }

    private fun makeCalendar() {
        mList.clear()
        val calendar = mCalendar!!.clone() as Calendar
        val dateFormat = SimpleDateFormat(mDateFormatStr)
        txtDayText!!.text = dateFormat.format(mCalendar!!.time)

        calendar.set(mCalendar!!.get(Calendar.YEAR), mCalendar!!.get(Calendar.MONTH), 1)
        val dayNum = calendar.get(Calendar.DAY_OF_WEEK)

        for (i in 1 until dayNum) {
            mList.add(DayData())
        }

        val mPrize = ArrayList<String>()
        mPrize.clear()

        mCalendar!!.set(Calendar.MONTH, mCalendar!!.get(Calendar.MONTH))

        var i = 0
        val icnt = mCalendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        while (i < icnt) {
            val year = mCalendar!!.get(Calendar.YEAR)
            val month = mCalendar!!.get(Calendar.MONTH) + 1
            val monthStr = assignPad10(month)
            val dayStr = assignPad10(i + 1)

            mPrize.add((i + 1).toString())

            val dayData = DayData(year, month, i + 1, (i + 1).toString(), String.format("%s%s%s", year, monthStr, dayStr))

            mList.add(dayData)
            i++
        }

        val mData = ArrayList<String>()
        mData.clear()
        mData.add("20")
        mData.add("28")

        mAdapter.setmPrize(mPrize)
        mAdapter.setmListHolidayDate(mData)
        mAdapter.notifyDataSetChanged(mList)
    }

    private fun clearState() {
        mAdapter.isStart = false
        mAdapter.setEnd(false)
        mStartDay = ""
        mEndDay = ""
        mAdapter.notifyDataSetChanged()
        mAdapter.setStartDay("")
        mAdapter.setEndDay("")
        sendCallback()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun getOnTouchListener() {
        gridView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> x1 = event.x
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1

                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        if (x2 > x1) {
                            btnPrevMonth!!.performClick()
                        } else {
                            btnNextMonth!!.performClick()
                        }
                    } else {

                    }
                }
            }
            false
        }
    }


    private fun showErrToast() {
        var message = context.getString(R.string.cant_select_prev_date)
        if (!TextUtils.isEmpty(mErrToastStr)) {
            message = mErrToastStr
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun sendCallback() {
        if (onDaySelectedListener != null) {
            onDaySelectedListener!!.onDaySelected(mStartDay, mEndDay)
        }
    }

    companion object {
        internal val MIN_DISTANCE = 150
    }
}