package com.hokagelab.gamecatalogapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class GamesItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("rating_top")
    val ratingTop: Int,
)