package link.phoenixwork.waterlevelmanager.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import link.phoenixwork.waterlevelmanager.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideTokenStorage(@ApplicationContext ctx: Context) = TokenStorage(ctx)
}