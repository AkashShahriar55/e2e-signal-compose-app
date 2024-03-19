package com.nsa.setting.model

data class Token(
    val accessToken: String?,
    val refreshToken: String?,
    val accessTokenExpireAt: Long,
)
