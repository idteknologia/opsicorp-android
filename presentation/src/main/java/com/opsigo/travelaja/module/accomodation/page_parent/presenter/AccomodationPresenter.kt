package com.opsigo.travelaja.module.accomodation.page_parent.presenter

import com.opsigo.travelaja.module.accomodation.page_parent.view.AccomodationView
import com.opsigo.travelaja.module.item_custom.menu_bottom.MenuBottomOpsicorp
import android.content.Context
import com.opsigo.travelaja.R

class AccomodationPresenter {

    val context:Context
    val accomodationView : AccomodationView
    var imageDefaults = ArrayList<Int>()
    var imageSelected = ArrayList<Int>()
    var imageTitle    = ArrayList<String>()



    constructor(context: Context, accomodationView: AccomodationView) {
        this.context = context
        this.accomodationView = accomodationView
    }

    fun setDataButton(btnBottomAccomodation: MenuBottomOpsicorp) {
        imageDefaults.clear()
        imageDefaults.add(R.drawable.ic_fligh_up)
        imageDefaults.add(R.drawable.ic_hotel)
        imageDefaults.add(R.drawable.ic_train)
        imageDefaults.add(R.drawable.ic_car)
        imageDefaults.add(R.drawable.ic_profile)

        imageSelected.clear()
//        imageSelected.add(R.drawable.ic_flight_up_selected)
//        imageSelected.add(R.drawable.ic_hotel_selected)
//        imageSelected.add(R.drawable.ic_train_selected)
        imageSelected.add(R.drawable.bottom_bar_airlines)
        imageSelected.add(R.drawable.bottom_bar_hotel)
        imageSelected.add(R.drawable.bottom_bar_train)
        imageSelected.add(R.drawable.ic_car_green)

        imageSelected.add(R.drawable.ic_profile_selected)

        imageTitle.clear()
        imageTitle.add("Flights")
        imageTitle.add("Hotels")
        imageTitle.add("Train")
        imageTitle.add("Car & Bus")
        imageTitle.add("Profile")

        btnBottomAccomodation.setDataImage(imageDefaults)
        btnBottomAccomodation.setDataImageSelected(imageSelected)
        btnBottomAccomodation.setDataTitle(imageTitle)
    }

}