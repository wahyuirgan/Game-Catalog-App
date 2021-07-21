package com.hokagelab.gamecatalogapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RatingsItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("count")
    val count: Int,

    @SerializedName("percent")
    val percent: Double
)