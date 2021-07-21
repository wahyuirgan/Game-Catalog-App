package com.hokagelab.gamecatalogapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val ratings: String,
    val ratingTop: Int,
    val ratingsCount: Int,
    val descriptionRaw: String,
    val genres: String,
    val isBookmark: Boolean
) : Parcelable