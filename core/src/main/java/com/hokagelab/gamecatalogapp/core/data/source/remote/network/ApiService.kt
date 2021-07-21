package com.hokagelab.gamecatalogapp.core.data.source.remote.network

import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GameDetailResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: String,
        @Query("key") key: String
    ): GameDetailResponse
}