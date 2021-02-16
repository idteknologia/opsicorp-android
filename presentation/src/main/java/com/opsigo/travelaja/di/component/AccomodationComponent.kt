package com.opsigo.travelaja.di.component

import com.opsigo.travelaja.module.accomodation.view_accomodation.presenter.AccomodationPresenter
import com.opsigo.travelaja.module.accomodation.view_accomodation.view.AccomodationView
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AccomodationComponent {
    val modules = module {
        factory { (view: AccomodationView) -> AccomodationPresenter(androidContext(),view) }
    }
}