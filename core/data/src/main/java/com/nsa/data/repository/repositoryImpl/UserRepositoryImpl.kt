package com.nsa.data.repository.repositoryImpl

import com.nsa.data.model.UserInfo
import com.nsa.data.repository.UserRepository
import com.nsa.network.datasources.RemoteUserDataSource

class UserRepositoryImpl(
    private val remoteUserDataSource:RemoteUserDataSource,
):UserRepository {



    override suspend fun getUserData(): UserInfo {
        remoteUserDataSource
    }
}