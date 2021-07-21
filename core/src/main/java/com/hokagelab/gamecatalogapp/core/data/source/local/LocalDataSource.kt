package com.hokagelab.gamecatalogapp.core.data.source.local

import com.hokagelab.gamecatalogapp.core.data.source.local.entity.GameEntity
import com.hokagelab.gamecatalogapp.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {
    fun getGames(): Flow<List<GameEntity>> = gameDao.getGames()
    fun getGameDetail(id: Int): Flow<GameEntity> = gameDao.getGameDetail(id)
    fun getBookmarkGames(): Flow<List<GameEntity>> = gameDao.getBookmarkGames()
    suspend fun insertGames(gameList: List<GameEntity>) = gameDao.insertGames(gameList)
    suspend fun updateGames(games: GameEntity, newState: Boolean) {
        games.isBookmark = newState
        gameDao.updateGames(games)
    }
    fun setBookmarkGames(games: GameEntity, newState: Boolean) {
        games.isBookmark = newState
        gameDao.updateBookmarkGames(games)
    }
}