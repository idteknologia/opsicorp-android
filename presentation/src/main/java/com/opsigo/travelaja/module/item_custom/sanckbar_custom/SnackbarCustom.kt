package com.opsigo.travelaja.module.item_custom.sanckbar_custom

import android.app.Activity
import android.view.View
import android.support.design.widget.Snackbar
import com.opsigo.travelaja.R
import android.widget.FrameLayout

class SnackbarCustom {

    fun showSnackbar(rootLayout: View,inflateView:Int ,duration: Int,context:Activity,marginFromSides:Int): Snackbar {

        val snackbar = Snackbar.make(rootLayout, "", duration)

        //inflate myView
        val snackView = context.getLayoutInflater().inflate(inflateView, null)

        snackbar.view.setBackgroundColor(context.getResources().getColor(R.color.red_alert))

        val snackBarView = snackbar.view as Snackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        parentParams.setMargins(marginFromSides, marginFromSides, marginFromSides, marginFromSides)
        parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        snackBarView.layoutParams = parentParams

        snackBarView.addView(snackView, 0)

        return snackbar
    }

    /*var myView = snackbar.getMyView()
        var tv = myView.findViewById(R.emplaoyId.snackbar_action) as ImageView
        tv.setOnClickListener {
            snackbar.dismiss();
        }*/


}