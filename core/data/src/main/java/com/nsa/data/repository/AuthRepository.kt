package com.nsa.data.repository

import com.nsa.data.model.Auth
import com.nsa.data.model.SignUpData
import com.nsa.data.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isLoggedIn:Flow<Boolean>

    suspend fun getAuth():Flow<Result<Auth>>

    suspend fun getUserInfo():Flow<Result<UserInfo>>

    suspend fun loginWithEmail(
        email:String,
        password:String
    ):Flow<Result<Unit>>

    suspend fun loginWithSocialMedia():Flow<Result<Unit>>

    suspend fun signUpWithEmail(
        firstName:String,
        lastName:String,
        email:String,
        password:String
    ):Flow<Result<Unit>>

    suspend fun logOut()

}