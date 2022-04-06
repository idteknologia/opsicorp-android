package com.mobile.travelaja.module.profile

import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityFaqActivityBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_faq_activity.*

class FAQActivity : BaseActivityBinding<ActivityFaqActivityBinding>(),
                    ToolbarOpsicorp.OnclickButtonListener {

    override fun bindLayout(): ActivityFaqActivityBinding {
        return ActivityFaqActivityBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.setTitleBar("Frequently Ask Questions")
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

}