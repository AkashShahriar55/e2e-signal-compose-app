package com.nsa.app.di


import com.nsa.app.model.AppInfo
import com.nsa.network.BuildConfig
import com.nsa.socket.SocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppInfo(): AppInfo {
        return AppInfo(
            baseUrl = "",
        )
    }


    @Singleton
    @Provides
    fun provideSocketManager(): SocketManager {
        return SocketManager.build(BuildConfig.BASE_URL)
    }

}