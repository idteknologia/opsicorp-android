package com.opsigo.travelaja.module.notif

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.activity_firebase_test.*

class   YesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_test)

        tvInfo.text = "ok"
    }


}
