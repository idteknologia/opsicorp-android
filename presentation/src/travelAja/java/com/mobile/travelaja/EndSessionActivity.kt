package com.mobile.travelaja

import android.os.Bundle
import androidx.activity.ComponentActivity
import net.openid.appauth.AuthorizationException
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.EndSessionResponse

class EndSessionActivity :  ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val endSession = EndSessionResponse.fromIntent(intent)
        val ex = AuthorizationException.fromIntent(intent)
        print(endSession)
        print(ex)

    }
}