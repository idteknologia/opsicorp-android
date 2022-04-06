package com.mobile.travelaja.module.profile

import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityPrivacyPolicyBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_faq_activity.*

class PrivacyPolicyActivity : BaseActivityBinding<ActivityPrivacyPolicyBinding>(),
                                ToolbarOpsicorp.OnclickButtonListener {

    override fun bindLayout(): ActivityPrivacyPolicyBinding {
       return ActivityPrivacyPolicyBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.setTitleBar("Privacy Policy")
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

}