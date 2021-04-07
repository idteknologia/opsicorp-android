package com.opsigo.travelaja.module.item_custom.success_view

import com.opsigo.travelaja.module.approval.activity.DetailTripActivity
import com.opsigo.travelaja.module.home.activity.HomeActivity
import kotlinx.android.synthetic.main.success_view.*
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.BaseActivity
import androidx.transition.TransitionManager
import androidx.transition.Fade
import android.content.Intent
import android.view.ViewGroup
import com.opsigo.travelaja.R
import android.os.Handler
import android.view.View
import com.squareup.picasso.Picasso

class SuccessView : BaseActivity() {
    override fun getLayout(): Int { return R.layout.success_view }

    override fun OnMain() {
        implementDataFromIntent()
    }

    private fun implementDataFromIntent() {
//        https://i.ibb.co/j4v46MP/BT-thank-you.png
//        https://i.ibb.co/RQC9bJj/thank-you.png

        if (intent.getBundleExtra("data")==null){
            Picasso.get()
                    .load(R.drawable.ic_sucees_image)
                    .into(image_success)
        }
        else {
            Picasso.get()
                    .load(intent.getBundleExtra("data").getString("image"))
                    .fit()
                    .into(image_success)
        }

        Handler().postDelayed({
            if (Constants.ID_BOOKING_TEMPORARY.isNotEmpty()){
                Constants.FROM_SUCCESS_CHECKOUT = true
                val intent = Intent(this, DetailTripActivity::class.java)
                intent.putExtra(Constants.KEY_FROM, Constants.FROM_SUCCESS_CREATE)
                intent.putExtra(Constants.KEY_INTENT_TRIPID, Constants.ID_BOOKING_TEMPORARY)
                intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, Constants.TRIP_CODE)
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