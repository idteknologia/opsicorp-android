package com.opsigo.travelaja.locale

import android.os.Bundle
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R

class ApplyLanguageActivity : BaseActivity(){

    override fun getLayout(): Int { return R.layout.activity_apply_lang
    }

    override fun OnMain() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finish()
    }
}