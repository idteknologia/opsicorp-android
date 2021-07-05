package com.mobile.travelaja.module.item_custom.barcode.popup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout

import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals

object QRPopUp {

    fun show(c: Context, code: String) {
        onMain(c, code)
    }

    private fun onMain(c: Context, code: String) {
        val d = Dialog(c)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val v = LayoutInflater.from(c).inflate(R.layout.pop_up_qr, null)
        d.setContentView(v)
        val qrCode = d.findViewById<ImageView>(R.id.qr_code)
        val layout = d.findViewById<RelativeLayout>(R.id.layout_parent_dialog)
        layout.setOnClickListener { d.dismiss() }

        qrCode.setImageBitmap(Globals.stringToBarcodeImage(code))

        d.show()
        d.setCancelable(true)
        d.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        d.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

    }


}
