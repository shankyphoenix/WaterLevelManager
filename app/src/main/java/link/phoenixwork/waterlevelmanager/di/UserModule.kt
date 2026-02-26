package link.phoenixwork.waterlevelmanager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import link.phoenixwork.waterlevelmanager.AuthInterceptor
import link.phoenixwork.waterlevelmanager.domain.remote.MainApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @Provides @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    @Singleton
    @Provides
    fun getRetrofitClient(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://host4.phoenixwork.link:8080/app/")
        .client(client)
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