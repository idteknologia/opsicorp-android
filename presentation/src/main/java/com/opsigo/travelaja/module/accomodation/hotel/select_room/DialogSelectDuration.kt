package com.opsigo.travelaja.module.accomodation.hotel.select_room

import android.content.Context

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.scrolchoice.ScrollChoice
import com.opsigo.travelaja.utility.DialogSelectDurationHotel
import android.view.ViewGroup


class DialogSelectDuration {

    var context : Context
    lateinit var alertDialog: AlertDialog

    constructor(context: Context) {
        this.context = context
    }

    var numberDuration = ArrayList<String>()
    var totalDuration = "1"


    fun create( actionSheetCallBack: DialogSelectDurationHotel){
        val adb = AlertDialog.Builder(context)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_select_duration,null)

        val scroll_guest : ScrollChoice = v.findViewById(R.id.duration_select)
        val batal        : TextView = v.findViewById(R.id.tv_batal_select)
        val selesai      : TextView = v.findViewById(R.id.tv_selesai_select)

        addDataNumber()
        scroll_guest.addItems(numberDuration,3)

        adb.setView(v)
        alertDialog = adb.create()
        alertDialog.window.getAttributes().windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false)
        alertDialog.window.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        scroll_guest.setOnItemSelectedListener { scrollChoice, position, name ->
            totalDuration = name
        }


        batal.setOnClickListener {
            alertDialog.dismiss()
        }

        selesai.setOnClickListener {
            actionSheetCallBack.duration(totalDuration)
            alertDialog.dismiss()
        }

    }

    private fun addDataNumber() {
        numberDuration.clear()
        for (i in 1 until 7){
            numberDuration.add("${i}")
        }
    }
}