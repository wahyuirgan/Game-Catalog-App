package com.hokagelab.gamecatalogapp.core.utils

import com.hokagelab.gamecatalogapp.core.data.source.local.entity.GameEntity
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GameDetailResponse
import com.hokagelab.gamecatalogapp.core.data.source.remote.response.GamesItem
import com.hokagelab.gamecatalogapp.core.domain.model.Game

object DataMapper {
    fun mapResponsesToEntities(input: List<GamesItem>): List<GameEntity> {
        val movieList = ArrayList<GameEntity>()
        input.map {
            with(it) {
                val movie =
                    GameEntity(
                        id,
                        name,
                        released = "",
                        backgroundImage,
                        rating,
                        ratings = "",
                        ratingTop,
                        ratingsCount = 0,
                        descriptionRaw = "",
                        genres = "",
                        false
                    )
                movieList.add(movie)
            }
        }
        return movieList
    }

    fun mapResponsesToEntities(input: GameDetailResponse): GameEntity {
        val listRatings = StringBuilder().append("")
        val listGenres = StringBuilder().append("")
        with(input) {
            for (ratingGame in ratings.indices) {
                if (ratingGame < ratings.size - 1) {
                    listRatings.append("${input.ratings[ratingGame].title} ${input.ratings[ratingGame].count}: ${input.ratings[ratingGame].percent}%, ")
                } else {
                    listRatings.append("${input.ratings[ratingGame].title} ${input.ratings[ratingGame].count}: ${input.ratings[ratingGame].percent}%")
                }
            }
            for (genre in genres.indices) {
                if (genre < genres.size - 1) {
                    listGenres.append("${input.genres[genre].name}, ")
                } else {
                    listGenres.append(input.genres[genre].name)
                }
            }
        }
        with(input) {
            return GameEntity(
                id,
                name,
                released,
                backgroundImage,
                rating,
                listRatings.toString(),
                ratingTop,
                ratingsCount,
                descriptionRaw,
                listGenres.toString(),
                false
            )
        }

    }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            with(it) {
                Game(
                    id,
                    name,
                    released,
                    backgroundImage,
                    rating,
                    ratings,
                    ratingTop,
                    ratingsCount,
                    descriptionRaw,
                    genres,
                    isBookmark
                )
            }
        }

    fun mapEntitiesToDomain(input: GameEntity): Game =
        with(input) {
            Game(
                id,
                name,
                released,
                backgroundImage,
                rating,
                ratings,
                ratingTop,
                ratingsCount,
                descriptionRaw,
                genres,
                isBookmark
            )
        }

    fun mapDomainToEntity(input: Game) =
        with(input) {
            GameEntity(
                id,
                name,
                released,
                backgroundImage,
                rating,
                ratings,
                ratingTop,
                ratingsCount,
                descriptionRaw,
                genres,
                isBookmark
            )
        }
}