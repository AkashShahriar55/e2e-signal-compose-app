package com.nsa.network.services

import com.nsa.network.model.ApiResponse
import com.nsa.network.model.response.AuthResponse
import com.nsa.network.model.request.LoginRequest
import com.nsa.network.model.request.RefreshTokenRequest
import com.nsa.network.model.request.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    companion object{
        const val AUTH_PATH = "/auth"
    }

    @POST("${AUTH_PATH}/sign-in")
    suspend fun signIn(@Body loginRequest: LoginRequest):Response<ApiResponse<AuthResponse>>

    @POST("${AUTH_PATH}/sign-up")
    suspend fun signUp(@Body registrationRequest: RegistrationRequest):Response<ApiResponse<AuthResponse>>

    @POST("${AUTH_PATH}/password/reset")
    suspend fun resetPassword(@Body loginRequest: LoginRequest):Response<ApiResponse<Any?>>

    @POST("${AUTH_PATH}/sign-out")
    suspend fun signOut(@Body loginRequest: LoginRequest):Response<ApiResponse<Any?>>

//    @POST("${AUTH_PATH}/password/new/")
//    suspend fun newPassword(@Body loginRequest: LoginRequest):Response<ApiResponse<Any?>>

    @POST("/refresh-tokens")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
    ): ApiResponse<AuthResponse>?



}