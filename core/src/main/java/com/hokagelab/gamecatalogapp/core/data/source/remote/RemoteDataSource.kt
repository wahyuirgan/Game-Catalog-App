package com.hokagelab.gamecatalogapp.core.data.source.remote

import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiInfo.API_KEY
import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiService
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GameDetailResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GamesItem
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getGames(): Flow<ApiResponse<List<GamesItem>>> {
        return flow {
            try {
                val res = apiService.getGames(API_KEY)
                val dataArray = res.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(res.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGameDetail(id: String): Flow<ApiResponse<GameDetailResponse>> {
        return flow {
            try {
                val res = apiService.getGameDetail(id, API_KEY)
                emit(ApiResponse.Success(res))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}