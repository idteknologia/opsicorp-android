package com.mobile.travelaja.module.profile

import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivityBinding
import com.mobile.travelaja.databinding.ActivityTermsBinding
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_faq_activity.*

class TermsActivity : BaseActivityBinding<ActivityTermsBinding>(),
                        ToolbarOpsicorp.OnclickButtonListener{

    override fun bindLayout(): ActivityTermsBinding {
        return ActivityTermsBinding.inflate(layoutInflater)
    }

    override fun onMain() {
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.setTitleBar("Terms and Conditions")
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }

}