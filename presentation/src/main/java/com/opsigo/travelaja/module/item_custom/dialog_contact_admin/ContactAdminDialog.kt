package com.opsigo.travelaja.module.item_custom.dialog_contact_admin

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import com.opsigo.travelaja.R

class ContactAdminDialog {

    lateinit var alertDialog: AlertDialog

    fun showDialogLoading(activity: Context,disableClose:Boolean) {

        val dialog = AlertDialog.Builder(activity)

        val dialogView = (activity as Activity).getLayoutInflater().inflate(R.layout.dialog_please_contact_admin, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

//
        val layClose = dialogView.findViewById(R.id.btn_cancel) as Button


        alertDialog = dialog.create()

        layClose.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

//        val params = dialogView.getLayoutParams()
//        params.height = activity.resources.displayMetrics.heightPixels
//        params.width = activity.resources.displayMetrics.widthPixels
//        dialogView.setLayoutParams(params)
    }

    fun dismiss(){
        alertDialog.dismiss()
    }

}