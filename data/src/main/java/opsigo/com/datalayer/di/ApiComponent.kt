package opsigo.com.datalayer.di

import dagger.Component
import opsigo.com.datalayer.datanetwork.*

@CustomScope
@Component(modules = [ApiModule::class], dependencies = [NetworkComponent::class])
interface ApiComponent {
    fun inject(getDataLogin: GetDataLogin)
    fun inject(getDataGeneral: GetDataGeneral)
    fun inject(getDataTripPlane : GetDataTripPlane)
    fun inject(getDataApproval: GetDataApproval)
    fun inject(getAccomodation: GetDataAccomodation)
    fun inject(getAccomodation: GetDataTravelRequest)
    fun inject(getMyBooking: GetDataMyBooking)
}
