package com.nsa.network.model.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(

    @SerializedName("access_token")
    val accessToken: String? = null,

    @SerializedName("refresh_token")
    val refreshToken: String? = null,

    @SerializedName("user_id")
    val userId: String? = null
)