package com.mobile.travelaja.di.component

import com.mobile.travelaja.module.create_trip.newtrip.presenter.CreateTripPresenter
import com.mobile.travelaja.module.create_trip.newtrip.view.CreateTripView
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class CreateTripComponent {
    val modules = module {
        factory { (view: CreateTripView) -> CreateTripPresenter(androidContext(),view) }
    }
}