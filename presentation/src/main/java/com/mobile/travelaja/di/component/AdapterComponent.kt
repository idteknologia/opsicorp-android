package com.mobile.travelaja.di.component

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import com.mobile.travelaja.module.approval.adapter.ApprovalAdapter
import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter
import com.mobile.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.mobile.travelaja.module.home.adapter.TourEventAdapter
import com.mobile.travelaja.module.home.adapter.UpcommingFlightAdapter
import com.mobile.travelaja.module.home.model.TourEventModel
import com.mobile.travelaja.module.home.model.UpcommingFlightModel
import com.mobile.travelaja.module.signin.select_nationality.adapter.SelectNationalityAdapter
import com.mobile.travelaja.module.accomodation.adapter.ResultAccomodationAdapter
import com.mobile.travelaja.module.approval.summary.SummaryAdapter
import com.mobile.travelaja.module.approval.summary.SummaryFlightModelAdapter
import com.mobile.travelaja.module.create_trip.newtrip_pertamina.adapter.DialogPurposeAdapter
import com.mobile.travelaja.module.item_custom.slider.SliderImageAdapter
import com.mobile.travelaja.module.item_custom.slider.SliderImageModel
import com.mobile.travelaja.module.signin.select.adapter.LookUpAdapter
import com.mobile.travelaja.module.manage_trip.adapter.ManageTripAdapter
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AdapterComponent {
    val modules = module {
        factory { (data: ArrayList<TourEventModel>) ->TourEventAdapter(androidContext(),data) }
        factory { (data: ArrayList<UpcommingFlightModel>) ->UpcommingFlightAdapter(androidContext(),data) }
        factory { (data: ArrayList<SelectNationalModel>) ->SelectNationalityAdapter(data) }
        factory { (data: ArrayList<SelectNationalModel>) ->DialogPurposeAdapter(data) }
        factory { (data: ArrayList<SelectNationalModel>) ->LookUpAdapter(data) }
        factory { (data: ArrayList<UploadModel>) ->AttachmentAdapter(androidContext(),data) }
        factory { (data: ArrayList<ApprovalModelAdapter>) ->ApprovalAdapter(androidContext(),data) }
        factory { (_: ArrayList<AccomodationResultModel>) -> ResultAccomodationAdapter() }
        factory { (_: ArrayList<SummaryFlightModelAdapter>) -> SummaryAdapter(androidContext()) }
        factory { (data: ArrayList<SliderImageModel>) -> SliderImageAdapter(androidContext(),data) }
//        factory { (data: ArrayList<CartModelAdapter>) -> ManageTripAdapter(androidContext(),data) }
        factory { (data: ArrayList<ApprovalModelAdapter>) -> ManageTripAdapter(androidContext(),data) }
    }
}