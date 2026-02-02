package link.phoenixwork.waterlevelmanager.di

import dagger.Provides
import link.phoenixwork.waterlevelmanager.data.remote.MainApiInterface
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

class UserModule {

    @Singleton
    @Provides
    fun getRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl("http://host4.phoenixwork.link:8080")
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