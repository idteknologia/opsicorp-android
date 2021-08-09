package com.mobile.travelaja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.travelaja.module.signin.splash.activity.SplashActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.network.MyURL

class InitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setGlobal()
        compareActivity()
    }

    private fun setGlobal() {
        if (Globals.getBaseUrl(this).isEmpty() || Globals.getBaseUrl(this).isBlank())
            Globals.setBaseUrl(this, MyURL.URL_TRAVELAJA)
    }

    private fun compareActivity() {
        if (Globals.getDataPreferenceBolean(this, Constants.IsLogin)) {
            val intent = Intent(this,SplashActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        }else {
            val intent = Intent(getString(R.string.init_activity))
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        }
    }
}