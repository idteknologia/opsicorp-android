package com.mobile.travelaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals

class InitActivityPertamina : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_pertamina)
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@InitActivityPertamina,PertaminaIdamanActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }





}