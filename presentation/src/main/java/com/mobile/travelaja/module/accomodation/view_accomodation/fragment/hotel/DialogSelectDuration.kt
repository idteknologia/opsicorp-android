package com.mobile.travelaja.module.accomodation.view_accomodation.fragment.hotel

import android.view.Gravity
import com.mobile.travelaja.R
import android.view.ViewGroup
import android.graphics.Color
import android.content.Context
import android.app.AlertDialog
import android.widget.TextView
import android.view.LayoutInflater
import android.graphics.drawable.ColorDrawable
import com.mobile.travelaja.utility.DialogSelectDurationHotel
import com.mobile.travelaja.module.item_custom.scrolchoice.ScrollChoice


class DialogSelectDuration {

    var context : Context
    lateinit var alertDialog: AlertDialog

    constructor(context: Context) {
        this.context = context
    }

    var numberDuration = ArrayList<String>()
    var totalDuration = "1"


    fun create(totalNight:Int,actionSheetCallBack: DialogSelectDurationHotel){
        val adb = AlertDialog.Builder(context)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_select_duration,null)

        val scroll_guest : ScrollChoice = v.findViewById(R.id.duration_select)
        val batal        : TextView = v.findViewById(R.id.tv_batal_select)
        val selesai      : TextView = v.findViewById(R.id.tv_selesai_select)

        addDataNumber(totalNight)
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

    private fun addDataNumber(total:Int) {
        numberDuration.clear()
        for (i in 1 .. total+1){
            numberDuration.add("${i}")
        }
    }
}