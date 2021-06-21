package com.mobile.travelaja.module.signin.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.travelaja.databinding.ActivityOpenIdDirectlySuccessBinding

class OpenIdDirectlySuccess : AppCompatActivity() {
    private lateinit var binding: ActivityOpenIdDirectlySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOpenIdDirectlySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}