package com.hokagelab.gamecatalogapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("released")
    val released: String,

    @SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("ratings")
    val ratings: List<RatingsItem>,

    @field:SerializedName("rating_top")
    val ratingTop: Int,

    @field:SerializedName("ratings_count")
    val ratingsCount: Int,

    @SerializedName("description_raw")
    val descriptionRaw: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>
)