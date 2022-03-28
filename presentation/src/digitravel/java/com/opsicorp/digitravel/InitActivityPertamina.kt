package com.opsicorp.digitravel

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals

class InitActivityPertamina : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_pertamina)
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val isLogin = Globals.getDataPreferenceBolean(this@InitActivityPertamina, Constants.IsLogin)
                if (isLogin){
                    val intent = Intent(this@InitActivityPertamina,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@InitActivityPertamina, PertaminaIdamanActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }
        timer.start()
    }

}