package com.mobile.travelaja.di.component

import com.mobile.travelaja.module.home.presenter.HomePresenter
import com.mobile.travelaja.module.home.view.HomeView
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class HomeComponent {

    val modules = module {
        factory { (view: HomeView) -> HomePresenter(androidContext(),view) }
    }

}