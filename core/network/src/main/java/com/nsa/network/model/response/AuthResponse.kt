package com.nsa.network.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null,

    @field:SerializedName("expieryTime")
    val accessTokenExpireAt: Long,

    @field:SerializedName("userId")
    val userId: String,
)
