package com.nsa.network.model.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("profilePicture")
    val profilePicture: Long,

    @field:SerializedName("userId")
    val userId: String,
)