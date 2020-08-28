package com.opsigo.travelaja.module.item_custom.calendar

import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.utility.Globals
import kotlin.collections.ArrayList
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.app.AlertDialog
import android.content.Context
import android.view.ViewGroup
import android.graphics.Color
import com.opsigo.travelaja.R
import android.view.Gravity
import android.view.View
import java.util.*

class CalendarDialog(var context: Context) {

    lateinit var views : View
    lateinit var alertDialog: AlertDialog
    val data = ArrayList<Date>()
    lateinit var recyclerView : RecyclerView
    val adapterShortBy by lazy { CalendarDialogAdapter(context,data) }
    lateinit var callbackDialog : CallbackDialog
    lateinit var lineOutSideCalendar :LinearLayout

    fun create(callbackDialog: CallbackDialog){
        val adb = AlertDialog.Builder(context)
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.calendar_dialog,null)
        recyclerView = views.findViewById(R.id.recycler_shor_by)
        lineOutSideCalendar = views.findViewById(R.id.line_outside_dialog)

        initRecyclerView()
        lineOutSideCalendar.setOnClickListener {
            alertDialog.hide()
        }

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow2 //R.style.DialogAnimations_SmileWindow;
        alertDialog.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.RIGHT)
        alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        alertDialog.setCancelable(true)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterShortBy

        adapterShortBy.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                adapterShortBy.notifyDataSetChanged()
                Globals.delay(900,object :Globals.DelayCallback{
                    override fun done() {
                        callbackDialog.selected(data[position])
                        alertDialog?.hide()
                    }
                })
            }
        })

        addData()
    }

    interface CallbackDialog{
        fun selected(date: Date)
    }

    fun addData() {
        for (i in 0 until 70){
            val c: Calendar = Calendar.getInstance()
            c.add(Calendar.DATE, i)
            data.add(c.time)
        }
        adapterShortBy.setData(data)

    }

}