package com.nsa.network.datasources

import android.util.Log
import com.nsa.network.model.ApiResponse
import com.nsa.network.model.request.LoginRequest
import com.nsa.network.model.response.AuthResponse
import com.nsa.network.model.request.RegistrationRequest
import com.nsa.network.services.AuthService
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val authService:AuthService

) : BaseRemoteDataSource() {


    suspend fun signUpWithEmail(registrationRequest: RegistrationRequest): ApiResponse<AuthResponse>{
        return callApi { authService.signUp(registrationRequest) }
    }

    suspend fun loginWithEmail(loginRequest: LoginRequest): ApiResponse<AuthResponse> {
        return callApi { authService.signIn(loginRequest) }
    }

}