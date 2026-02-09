package link.phoenixwork.waterlevelmanager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import link.phoenixwork.waterlevelmanager.domain.remote.MainApiInterface
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @Singleton
    @Provides
    fun getRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl("http://host4.phoenixwork.link:8080/app/")
        .addConverterFactory( GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun getApiClient(
        retrofit: Retrofit
    ): MainApiInterface = retrofit.create(
        MainApiInterface::class.java
    )

}