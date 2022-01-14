package com.mobile.travelaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.travelaja.module.home.activity.HomeActivity
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
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
                    val intent = Intent(this@InitActivityPertamina,PertaminaIdamanActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }
        timer.start()
    }

}