package com.mobile.travelaja.module.signin.login

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Utils
import net.openid.appauth.*

object OpenIdLogin {
    private const val scopes = "openid email api.auth profile offline_access user.read"
    private const val responseType = "${ResponseTypeValues.CODE} ${ResponseTypeValues.ID_TOKEN}"
    const val REQ_CODE_OPEN_ID = 9

    fun loginWithSSO(
        context: Activity,
        clientId: String,
        endpointIssuer : String,
        authService: AuthorizationService,
        isLogin : Boolean =true,
        callback: () -> Any
    ) {
        AuthorizationServiceConfiguration.fetchFromIssuer(
            Uri.parse(endpointIssuer)
        ) { sc, ex ->
            if (ex != null) {
                Utils.handleErrorMessage(context, ex) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            if (sc != null) {
                if (isLogin)
                builderOpenId(context, sc, authService, clientId)
                else
                    endSessionOpenId(context,sc,authService)
            }
            callback.invoke()
        }
    }

    private fun endSessionOpenId( context: Activity,
                                  config: AuthorizationServiceConfiguration,
                                  authService: AuthorizationService){
        val idToken = getTokenIdOpenId(context)
        val redirectUri = Uri.parse("${context.packageName}:/oauth2callback")
        val endSessionRequest = EndSessionRequest.Builder(config)
            .setIdTokenHint(idToken)
            .setPostLogoutRedirectUri(redirectUri).build()
        val intent = authService.getEndSessionRequestIntent(endSessionRequest)
        context.startActivityForResult(intent, REQ_CODE_OPEN_ID)

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