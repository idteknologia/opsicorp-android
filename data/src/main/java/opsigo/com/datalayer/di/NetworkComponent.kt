package opsigo.com.datalayer.di

import javax.inject.Singleton

import dagger.Component
import retrofit2.Retrofit

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun retrofit(): Retrofit
}
