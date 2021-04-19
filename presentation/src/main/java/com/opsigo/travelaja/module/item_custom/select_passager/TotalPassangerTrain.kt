package com.opsigo.travelaja.module.item_custom.select_passager

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.view.Gravity
import android.view.View

class TotalPassangerTrain() {

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    lateinit var callbackDialog : CallbackDialog

    fun create(context: Context,callbackDialog: CallbackDialog){
        val adb = AlertDialog.Builder(context)
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.train_select_total_passanger,null)
//        recyclerView = views.findViewById(R.id.recycler_shor_by)
//        background = views.findViewById(R.id.line_background)

        adb.setView(views)
        alertDialog?.show()
        alertDialog = adb.create()
        alertDialog?.setCancelable(true)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    interface CallbackDialog{
        fun selected(int: Int)
    }
}