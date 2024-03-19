package com.nsa.network.model

import com.google.gson.annotations.SerializedName

data class PaginationMeta(

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("total_page")
    val totalPage: Int? = null
)