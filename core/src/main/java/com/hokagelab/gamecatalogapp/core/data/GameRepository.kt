package com.hokagelab.gamecatalogapp.core.data

import com.hokagelab.gamecatalogapp.core.data.source.NetworkBoundResource
import com.hokagelab.gamecatalogapp.core.data.source.local.LocalDataSource
import com.hokagelab.gamecatalogapp.core.data.source.remote.RemoteDataSource
import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GameDetailResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GamesItem
import com.hokagelab.gamecatalogapp.core.domain.repository.IGameRepository
import com.hokagelab.gamecatalogapp.core.domain.model.Game
import com.hokagelab.gamecatalogapp.core.utils.AppExecutors
import com.hokagelab.gamecatalogapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
    ) : IGameRepository {
    override fun getGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GamesItem>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getGames().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GamesItem>>> =
                remoteDataSource.getGames()

            override suspend fun saveCallResult(data: List<GamesItem>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(movieList)
            }
        }.asFlow()

    override fun getGameDetail(id: Int): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, GameDetailResponse>() {
            override fun loadFromDB(): Flow<Game> {
                return localDataSource.getGameDetail(id).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Game?): Boolean = data == null || data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<GameDetailResponse>> =
                remoteDataSource.getGameDetail(id.toString())

            override suspend fun saveCallResult(data: GameDetailResponse) {
                val game = DataMapper.mapResponsesToEntities(data)
                localDataSource.updateGames(game, false)
            }

        }.asFlow()

    override fun getBookmarkGames(): Flow<List<Game>> {
        return localDataSource.getBookmarkGames().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setBookmarkGames(game: Game, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.executor().execute { localDataSource.setBookmarkGames(movieEntity, state) }
    }
}