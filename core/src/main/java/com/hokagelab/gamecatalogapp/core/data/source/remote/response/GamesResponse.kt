package com.hokagelab.gamecatalogapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("results")
    val results: List<GamesItem>
)