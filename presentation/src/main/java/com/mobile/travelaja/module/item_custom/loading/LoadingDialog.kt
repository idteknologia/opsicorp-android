package com.mobile.travelaja.module.item_custom.loading

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import com.cunoraz.gifview.library.GifView
import com.mobile.travelaja.R

class LoadingDialog {

    lateinit var alertDialog: AlertDialog

    fun showDialogLoading(activity: Activity,disableClose:Boolean) {

        val dialog = AlertDialog.Builder(activity, android.R.style.Theme_Translucent_NoTitleBar)

        val dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_loader, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

        val layClose = dialogView.findViewById(R.id.layClose) as LinearLayout

        val gifView1 = dialogView.findViewById(R.id.gif1) as GifView
        gifView1.visibility = View.VISIBLE
        gifView1.gifResource = R.mipmap.ellipsis
        gifView1.play()

        alertDialog = dialog.create()

        layClose.setOnClickListener {
            alertDialog.dismiss()
        }

        if (disableClose){
            layClose.visibility = View.GONE
        }
        else{
            layClose.visibility = View.VISIBLE
        }

        alertDialog.show()

        val params = dialogView.getLayoutParams()
        params.height = activity.resources.displayMetrics.heightPixels
        params.width = activity.resources.displayMetrics.widthPixels
        dialogView.setLayoutParams(params)
    }

    fun dismiss(){
        alertDialog.dismiss()
    }

}