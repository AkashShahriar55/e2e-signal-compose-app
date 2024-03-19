package com.nsa.network.di

import com.nsa.network.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideAuthApi(@NoAuthClient retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }
}