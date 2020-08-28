package com.opsigo.travelaja.module.item_custom.loading

import android.app.Activity
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.cunoraz.gifview.library.GifView
import com.opsigo.travelaja.R

class LoadingDialogSuccess {

    fun showDialogLoading(activity: Activity) {

        val dialog = android.support.v7.app.AlertDialog.Builder(activity, android.R.style.Theme_Translucent_NoTitleBar)

        val dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_loader_success, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

        val succes_image_finish = dialogView.findViewById(R.id.succes_image_finish) as ImageView

        val gifView1 = dialogView.findViewById(R.id.gif1) as GifView
        gifView1.visibility = View.VISIBLE
        gifView1.gifResource = R.mipmap.ellipsis
        gifView1.play()

        val alertDialog = dialog.create()

        alertDialog.show()

        Handler().postDelayed({
            succes_image_finish.visibility = View.VISIBLE
            gifView1.visibility = View.GONE
        }, 2000)

        val params = dialogView.getLayoutParams()
        params.height = activity.resources.displayMetrics.heightPixels
        params.width = activity.resources.displayMetrics.widthPixels
        dialogView.setLayoutParams(params)
    }

}