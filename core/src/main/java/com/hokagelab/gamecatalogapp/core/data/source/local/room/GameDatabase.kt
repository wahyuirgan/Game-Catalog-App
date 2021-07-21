package com.hokagelab.gamecatalogapp.core.data.source.local.room

import com.hokagelab.gamecatalogapp.core.data.source.local.entity.GameEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}