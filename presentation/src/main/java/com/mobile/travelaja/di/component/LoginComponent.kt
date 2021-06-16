package com.mobile.travelaja.di.component

import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurposePresenter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog.DialogPurposeView
import com.mobile.travelaja.module.signin.detail_profile.presenter.CompletedDataProfilePresenter
import com.mobile.travelaja.module.signin.detail_profile.view.CompletedDataProfileView
import com.mobile.travelaja.module.signin.select_nationality.presenter.SelectedNationalityPresenter
import com.mobile.travelaja.module.signin.select_nationality.view.SelectNationalityView
import com.mobile.travelaja.module.signin.select.presenter.LookUpPresenter
import com.mobile.travelaja.module.signin.select.view.LookUpView
import com.mobile.travelaja.module.signin.login.presenter.LoginPresenter
import com.mobile.travelaja.module.signin.login.view.LoginView
import com.mobile.travelaja.module.signin.splash.presenter.SplashPresenter
import com.mobile.travelaja.module.signin.splash.view.SplashView
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class LoginComponent {
    val modules = module {
        factory { (view: LoginView) -> LoginPresenter(androidContext(),view) }
        factory { (view: SplashView) -> SplashPresenter(androidContext(),view) }
        factory { (view: CompletedDataProfileView)-> CompletedDataProfilePresenter(androidContext(),view) }
        factory { (view: SelectNationalityView) -> SelectedNationalityPresenter(androidContext(),view) }
        factory { (view: DialogPurposeView) -> DialogPurposePresenter(androidContext(),view) }
        factory { (view: LookUpView) -> LookUpPresenter(androidContext(),view) }
    }
}