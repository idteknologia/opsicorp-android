package com.mobile.travelaja.module.item_custom.dialog_contact_admin

import android.app.Activity
import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.mobile.travelaja.R

class NotAuthorizedDialog {
    lateinit var alertDialog: AlertDialog

    fun showDialogLoading(activity: Context, disableClose:Boolean) {

        val dialog = AlertDialog.Builder(activity)

        val dialogView = (activity as Activity).getLayoutInflater().inflate(R.layout.dialog_not_authorized, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        val layClose = dialogView.findViewById(R.id.btn_cancel) as Button


        alertDialog = dialog.create()

        layClose.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun dismiss(){
        alertDialog.dismiss()
    }
}