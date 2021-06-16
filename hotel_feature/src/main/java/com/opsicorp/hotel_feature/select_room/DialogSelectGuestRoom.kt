package com.opsicorp.hotel_feature.select_room

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.module.item_custom.scrolchoice.ScrollChoice
import com.mobile.travelaja.utility.DialogSelectGuestRoomCallback

class DialogSelectGuestRoom {
    var context : Context
    lateinit var alertDialog: AlertDialog

    constructor(context: Context) {
        this.context = context
    }

    var numberGuest = ArrayList<String>()
    var numberRoom  = ArrayList<String>()

    var totalRoom = "1"
    var totalGuest = "1"

    fun create( actionSheetCallBack: DialogSelectGuestRoomCallback){
        val adb = AlertDialog.Builder(context)
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_select_guest_room,null)

        val scroll_guest : ScrollChoice = v.findViewById(R.id.guest_select)
        val scroll_room  : ScrollChoice = v.findViewById(R.id.room_select)
        val batal        : TextView     = v.findViewById(R.id.tv_batal_select)
        val selesai      : TextView     = v.findViewById(R.id.tv_selesai_select)

        addDataNumber()

        scroll_guest.addItems(numberGuest,3)
        scroll_room.addItems(numberRoom,3)

        adb.setView(v)
        alertDialog = adb.create()
        alertDialog.window?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false)
        alertDialog.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        scroll_guest.setOnItemSelectedListener { scrollChoice, position, name ->
            totalGuest = name
        }

        scroll_room.setOnItemSelectedListener { scrollChoice, position, name ->
            totalRoom = name
        }

        batal.setOnClickListener {
            alertDialog.dismiss()
        }

        selesai.setOnClickListener {
            actionSheetCallBack.selected(totalGuest,totalRoom)
            alertDialog.dismiss()
        }

    }

    private fun addDataNumber() {
        numberRoom.clear()
        numberGuest.clear()

        for (i in 1 until 7){
            numberGuest.add("${i}")
            numberRoom.add("${i}")
        }
    }
}