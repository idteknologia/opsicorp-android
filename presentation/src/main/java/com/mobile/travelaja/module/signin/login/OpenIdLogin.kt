package com.mobile.travelaja.module.signin.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Utils
import net.openid.appauth.*
import java.util.prefs.Preferences

object OpenIdLogin {
    private const val scopes = "openid email api.auth profile offline_access user.read"
    private const val responseType = "${ResponseTypeValues.CODE} ${ResponseTypeValues.ID_TOKEN}"
    const val REQ_CODE_OPEN_ID = 9

    fun readAuthState(context: Activity) : AuthState {
        val authPref = context.getSharedPreferences("auth",Activity.MODE_PRIVATE)
        val stateJson = authPref.getString("stateJson",null)
        if (stateJson != null){
            return  AuthState.jsonDeserialize(stateJson)
        }else
            return AuthState()
    }

    fun writeAuthState(context: Activity,authState: AuthState){
        val authPrefs = context.getSharedPreferences("auth",Activity.MODE_PRIVATE)
        authPrefs.edit().putString("stateJson",authState.jsonSerializeString()).apply()
    }

    fun loginWithSSO(
        context: Activity,
        clientId: String,
        endpointIssuer : String,
        authService: AuthorizationService,
        isLogin : Boolean =true,
        callback: () -> Any
    ) {

        if (isLogin){
            val redirectUri = Uri.parse("${context.packageName}:/oauth2callback")

            val serviceConfig = AuthorizationServiceConfiguration(
                Uri.parse("$endpointIssuer/connect/authorize"), // authorization endpoint
                Uri.parse("$endpointIssuer/connect/token") // token endpoint
            )
            val builder = AuthorizationRequest.Builder(
                serviceConfig,
                clientId,
                ResponseTypeValues.CODE,
                redirectUri
            )
            builder.setScopes("openid","profile","email")
            val authRequest = builder.build()
            val intent = authService.getAuthorizationRequestIntent(authRequest)
            context.startActivityForResult(intent, REQ_CODE_OPEN_ID)
        }else {
            AuthorizationServiceConfiguration.fetchFromIssuer(
            Uri.parse(endpointIssuer)
        ) { sc, ex ->
            if (ex != null) {
                Utils.handleErrorMessage(context, ex) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            if (sc != null) {
                if (isLogin){
                    val authState = AuthState(sc)
                    writeAuthState(context, authState)
                    builderOpenId(context, sc, authService, clientId)
                }
                else
                    endSessionOpenId(context,sc,authService)
            }
            callback.invoke()
        }
        }
    }

    fun tokenSessionOpenId(context: Activity,
                                   clientId: String,
                                   endpointIssuer : String,
                                   authService: AuthorizationService){
        val redirectUri = Uri.parse("${context.packageName}:/oauth2callback")

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("$endpointIssuer/connect/authorize"), // authorization endpoint
            Uri.parse("$endpointIssuer/connect/token") // token endpoint
        )
        val builder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri
        )
        builder.setScopes("openid","profile","email")
        val authRequest = builder.build()
        val intent = authService.getAuthorizationRequestIntent(authRequest)
        context.startActivityForResult(intent, REQ_CODE_OPEN_ID)
    }

    private fun endSessionOpenId( context: Activity,
                                  config: AuthorizationServiceConfiguration,
                                  authService: AuthorizationService){

        val redirectUri = Uri.parse("${context.packageName}:/oauth2callback")

        val idToken = getTokenIdOpenId(context)
        val endSessionRequest = EndSessionRequest.Builder(config)
            .setIdTokenHint(idToken)
            .setPostLogoutRedirectUri(redirectUri)
            .build()
        val intent = authService.getEndSessionRequestIntent(endSessionRequest)
        context.startActivityForResult(intent, REQ_CODE_OPEN_ID)

//        val pendingIntent = PendingIntent.getActivity(context,0,
//            Intent(context,EndSessionActivity::class.java),0
//        )
//        authService.performEndSessionRequest(endSessionRequest,pendingIntent,pendingIntent)

        //with auth state
//        val currentState = readAuthState(context)
//        val clearedState = currentState.authorizationServiceConfiguration?.let { AuthState(it) }
//        if (currentState.lastRegistrationResponse != null){
//            clearedState?.update(currentState.lastRegistrationResponse)
//        }

    }



    private fun builderOpenId(
        context: Activity,
        config: AuthorizationServiceConfiguration,
        authService: AuthorizationService,
        clientId: String
    ) {
        val redirectUri = Uri.parse("${context.packageName}:/oauth2callback")
        val builder = AuthorizationRequest.Builder(
            config,
            clientId,
            responseType,
            redirectUri
        )

        builder.setState(null)
        builder.setScopes(scopes)
        builder.setPrompt("login")
        val request = builder.build()
        val intent = authService.getAuthorizationRequestIntent(request)
        context.startActivityForResult(intent, REQ_CODE_OPEN_ID)
    }

    const val IDTOKENIDAMAN = "IDTOKENIDAMAN"
    fun setTokenIdOpenId(idToken : String,context: Activity){
        Globals.setDataPreferenceString(context, IDTOKENIDAMAN,idToken)
    }

    private fun getTokenIdOpenId(context: Activity) : String {
       return Globals.getDataPreferenceString(context, IDTOKENIDAMAN)
    }
}