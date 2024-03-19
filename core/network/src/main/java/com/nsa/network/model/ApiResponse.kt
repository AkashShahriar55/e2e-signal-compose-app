package com.nsa.network.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status")
    val status: Boolean = false,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("errors")
    val errors: List<String>? = null,

    @SerializedName("statusCode")
    val statusCode: Int? = null,

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("meta")
    var meta: PaginationMeta? = null,
){

    companion object{
        fun <T> error(statusCode:Int,message: String,errors: List<String>? = null): ApiResponse<T> {
            return ApiResponse(false,message,errors,statusCode)
        }
    }

}