package com.nsa.data.repository.repositoryImpl

import android.util.Log
import com.nsa.coroutines.di.Dispatcher
import com.nsa.coroutines.di.NsaDispatchers
import com.nsa.data.model.Auth
import com.nsa.data.model.SignUpData
import com.nsa.data.model.UserInfo
import com.nsa.data.repository.AuthRepository
import com.nsa.network.datasources.RemoteAuthDataSource
import com.nsa.network.model.request.LoginRequest
import com.nsa.network.model.request.RegistrationRequest
import com.nsa.setting.managers.AuthManager
import com.nsa.setting.model.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource:RemoteAuthDataSource,
    @Dispatcher(NsaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val authManager: AuthManager
):AuthRepository {



    override val isLoggedIn = authManager.isLoggedInFlow

    override suspend fun getAuth(): Flow<Result<Auth>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): Flow<Result<UserInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun loginWithEmail(email: String, password: String): Flow<Result<Unit>> {
        return flow<Result<Unit>> {
            val response = remoteAuthDataSource.loginWithEmail(LoginRequest(email,password))
            if(response.status){
                response.data?.let {
                    authManager.loginUser(
                        it.userId,
                        Token(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        accessTokenExpireAt = it.accessTokenExpireAt)
                    )
                    emit(Result.success(Unit))
                }
            }else{
                emit(Result.failure(Throwable(response.message)))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun loginWithSocialMedia(): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmail(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<Result<Unit>> {
        return flow<Result<Unit>> {
            val response = remoteAuthDataSource.signUpWithEmail( RegistrationRequest(firstName,lastName,email,password))
            if(response.status){
                response.data?.let {
                    emit(Result.success(Unit))
                }
            }else{
                emit(Result.failure(Throwable(response.message)))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun logOut() {
        TODO("Not yet implemented")
    }
}