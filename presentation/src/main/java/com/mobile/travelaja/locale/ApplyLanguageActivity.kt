package com.mobile.travelaja.locale

import android.os.Bundle
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R

class ApplyLanguageActivity : BaseActivity(){

    override fun getLayout(): Int { return R.layout.activity_apply_lang
    }

    override fun OnMain() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finish()
    }
}