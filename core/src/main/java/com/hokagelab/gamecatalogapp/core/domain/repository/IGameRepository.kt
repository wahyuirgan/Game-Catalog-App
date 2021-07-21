package com.hokagelab.gamecatalogapp.core.domain.repository

import com.hokagelab.gamecatalogapp.core.data.Resource
import com.hokagelab.gamecatalogapp.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getGames(): Flow<Resource<List<Game>>>
    fun getGameDetail(id: Int): Flow<Resource<Game>>
    fun getBookmarkGames(): Flow<List<Game>>
    fun setBookmarkGames(game: Game, state: Boolean)
}