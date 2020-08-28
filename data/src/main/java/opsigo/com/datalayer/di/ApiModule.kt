package opsigo.com.datalayer.di


import dagger.Module
import dagger.Provides
import opsigo.com.data.network.UrlEndpoind
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    internal fun provideApiService(retrofit: Retrofit): UrlEndpoind {
        return retrofit.create(UrlEndpoind::class.java)
    }
}
