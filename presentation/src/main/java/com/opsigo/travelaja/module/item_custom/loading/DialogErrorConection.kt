package com.opsigo.travelaja.module.item_custom.loading

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp

class DialogErrorConection {

    lateinit var alertDialog: AlertDialog

    fun showDialogLoading(activity: Activity,cancelable:Boolean,callback:CallbackErrorDialog) {

        val dialog = AlertDialog.Builder(activity, android.R.style.Theme_Translucent_NoTitleBar)

        val dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_error_conection, null)
        dialog.setView(dialogView)
        dialog.setCancelable(cancelable)

        val btnHomePage = dialogView.findViewById(R.id.btn_home_page) as ButtonDefaultOpsicorp


        alertDialog = dialog.create()

        btnHomePage.callbackOnclickButton(object :ButtonDefaultOpsicorp.OnclickButtonListener{
            override fun onClicked() {
                callback.gotoHomePage()
            }
        })

        alertDialog.show()

        val params = dialogView.getLayoutParams()
        params.height = activity.resources.displayMetrics.heightPixels
        params.width = activity.resources.displayMetrics.widthPixels
        dialogView.setLayoutParams(params)
    }

    interface CallbackErrorDialog{
        fun gotoHomePage()
    }

    fun dismiss(){
        alertDialog.dismiss()
    }


}