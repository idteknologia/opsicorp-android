package com.opsigo.travelaja.module.signin.detail_profile.presenter

import android.content.Context
import com.opsigo.travelaja.module.signin.detail_profile.view.CompletedDataProfileView

class CompletedDataProfilePresenter {

    val context         : Context
    val view            : CompletedDataProfileView

    constructor(context: Context, view: CompletedDataProfileView) {
        this.context = context
        this.view = view
    }

}