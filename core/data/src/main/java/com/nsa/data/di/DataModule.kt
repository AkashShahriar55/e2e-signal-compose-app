package com.nsa.data.di

import com.nsa.data.repository.AuthRepository
import com.nsa.data.repository.repositoryImpl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAuthRepository(
        authRepository: AuthRepositoryImpl,
    ): AuthRepository

}