package opsigo.com.datalayer.di

import dagger.Component
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.datalayer.datanetwork.GetDataApproval
import opsigo.com.datalayer.datanetwork.GetDataAccomodation

@CustomScope
@Component(modules = [ApiModule::class], dependencies = [NetworkComponent::class])
interface ApiComponent {
    fun inject(getDataLogin: GetDataLogin)
    fun inject(getDataGeneral: GetDataGeneral)
    fun inject(getDataTripPlane : GetDataTripPlane)
    fun inject(getDataApproval: GetDataApproval)
    fun inject(getAccomodation: GetDataAccomodation)
}
