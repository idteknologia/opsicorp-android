package com.mobile.travelaja.module.my_booking.home_my_booking

import androidx.fragment.app.Fragment
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityMyBookingBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_my_booking.*

class MyBookingActivity : BaseActivityBinding<ActivityMyBookingBinding>(), ToolbarOpsicorp.OnclickButtonListener {
    override fun bindLayout(): ActivityMyBookingBinding {
        return ActivityMyBookingBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
        loadMyBookingFragment()
    }

    private fun initToolbar() {
        toolbar.setTitleBar(getString(R.string.purchase_list))
        toolbar.showBtnFilter()
        toolbar.callbackOnclickToolbar(this)
        toolbar.singgleTitleGravity(toolbar.START)
    }

    private fun loadMyBookingFragment() {
        val myBooking = MyBookingFragment()
        loadFragment(myBooking, R.id.place_fragment)
    }

    private fun loadFragment(fragment: Fragment?, place: Int) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(place, fragment)
                .addToBackStack("")
                .commit()
        }
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

    override fun onBackPressed() {
        finish()
    }
}