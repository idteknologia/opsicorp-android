package com.mobile.travelaja.module.item_custom.success_view

import com.mobile.travelaja.module.approval.activity.DetailTripActivity
import com.mobile.travelaja.module.home.activity.HomeActivity
import kotlinx.android.synthetic.main.success_view.*
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.base.BaseActivity
import androidx.transition.TransitionManager
import androidx.transition.Fade
import android.content.Intent
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.os.Handler
import android.view.View
import com.squareup.picasso.Picasso

class SuccessView : BaseActivity() {
    override fun getLayout(): Int { return R.layout.success_view }

    var idTrip   = ""
    var tripCode = ""

    override fun OnMain() {
        implementDataFromIntent()
    }

    private fun implementDataFromIntent() {
        Picasso.get()
                .load(R.drawable.ic_sucees_image)
                .into(image_success)

        idTrip   = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.ID_TRIP_PLANE,"")
        tripCode = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.TRIP_CODE,"")

        Handler().postDelayed({
            if (idTrip.isNotEmpty()){
                Constants.FROM_SUCCESS_CHECKOUT = true
                val intent = Intent(this, DetailTripActivity::class.java)
                intent.putExtra(Constants.KEY_FROM, Constants.FROM_SUCCESS_CREATE)
                intent.putExtra(Constants.KEY_INTENT_TRIPID, idTrip)
                intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, tripCode)
                startActivityForResult(intent,Constants.OPEN_DETAIL_TRIP_PLANE)
            }
            else {
                gotoActivity(HomeActivity::class.java)
            }
        }, 2000)

    }

    fun showLine(){
        val transition = Fade()
        transition.setDuration(1000)
        transition.addTarget(R.id.line_success)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_success.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        gotoActivity(HomeActivity::class.java)
    }
}