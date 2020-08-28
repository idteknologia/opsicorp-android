package com.opsigo.travelaja.di.component

import com.opsigo.travelaja.module.login.detail_profile.presenter.CompletedDataProfilePresenter
import com.opsigo.travelaja.module.login.detail_profile.view.CompletedDataProfileView
import com.opsigo.travelaja.module.login.select_nationality.presenter.SelectedNationalityPresenter
import com.opsigo.travelaja.module.login.select_nationality.view.SelectNationalityView
import com.opsigo.travelaja.module.login.select.presenter.LookUpPresenter
import com.opsigo.travelaja.module.login.select.view.LookUpView
import com.opsigo.travelaja.module.login.signin.presenter.LoginPresenter
import com.opsigo.travelaja.module.login.signin.view.LoginView
import com.opsigo.travelaja.module.login.splash.presenter.SplashPresenter
import com.opsigo.travelaja.module.login.splash.view.SplashView
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class LoginComponent {
    val modules = module {
        factory { (view: LoginView) -> LoginPresenter(androidContext(),view) }
        factory { (view: SplashView) -> SplashPresenter(androidContext(),view) }
        factory { (view: CompletedDataProfileView)-> CompletedDataProfilePresenter(androidContext(),view) }
        factory { (view: SelectNationalityView) -> SelectedNationalityPresenter(androidContext(),view) }
        factory { (view: LookUpView) -> LookUpPresenter(androidContext(),view) }
    }
}