package com.opsigo.travelaja.di.component

import com.opsigo.travelaja.module.signin.detail_profile.presenter.CompletedDataProfilePresenter
import com.opsigo.travelaja.module.signin.detail_profile.view.CompletedDataProfileView
import com.opsigo.travelaja.module.signin.select_nationality.presenter.SelectedNationalityPresenter
import com.opsigo.travelaja.module.signin.select_nationality.view.SelectNationalityView
import com.opsigo.travelaja.module.signin.select.presenter.LookUpPresenter
import com.opsigo.travelaja.module.signin.select.view.LookUpView
import com.opsigo.travelaja.module.signin.login.presenter.LoginPresenter
import com.opsigo.travelaja.module.signin.login.view.LoginView
import com.opsigo.travelaja.module.signin.splash.presenter.SplashPresenter
import com.opsigo.travelaja.module.signin.splash.view.SplashView
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