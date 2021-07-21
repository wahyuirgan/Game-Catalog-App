package com.hokagelab.gamecatalogapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_game_entities")
data class GameEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "background_image")
    var backgroundImage: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "ratings")
    var ratings: String,

    @ColumnInfo(name = "rating_top")
    var ratingTop: Int,

    @ColumnInfo(name = "ratings_count")
    var ratingsCount: Int,

    @ColumnInfo(name = "description_raw")
    var descriptionRaw: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "isBookmark")
    var isBookmark: Boolean = false
)