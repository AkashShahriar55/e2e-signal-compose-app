package com.nsa.network.model.request

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)
