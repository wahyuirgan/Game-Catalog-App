package com.hokagelab.gamecatalogapp.core.data.source.local.room

import com.hokagelab.gamecatalogapp.core.data.source.local.entity.GameEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM tbl_game_entities")
    fun getGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM tbl_game_entities WHERE id = :id")
    fun getGameDetail(id: Int): Flow<GameEntity>

    @Query("SELECT * FROM tbl_game_entities WHERE isBookmark = 1")
    fun getBookmarkGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Update
    suspend fun updateGames(games: GameEntity)

    @Update
    fun updateBookmarkGames(games: GameEntity)
}