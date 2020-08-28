package com.opsigo.travelaja.module.login.signin.presenter

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.login.signin.view.LoginView
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals

class LoginPresenter {


    val context: Context
    val view: LoginView

    constructor(context: Context, view: LoginView) {
        this.context = context
        this.view = view
    }

    fun visibilityPasswordListener(image: ImageView, edittext: EditText, context: Context){
        if(image.drawable.constantState==context.resources.getDrawable(R.drawable.ic_visibility_black).constantState){
            image.setImageDrawable(image.resources.getDrawable(R.drawable.ic_visibility_off_black))
            edittext.setTransformationMethod(PasswordTransformationMethod.getInstance())
        }else{
            image.setImageDrawable(image.resources.getDrawable(R.drawable.ic_visibility_black))
            edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
        }
    }

    fun checkLogin() {
        if(Globals.getDataPreferenceBolean(context,Constants.IsLogin)){
            view.gotoSplashScreen()
        }
    }

}
