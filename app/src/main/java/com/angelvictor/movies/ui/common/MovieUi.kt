package com.angelvictor.movies.ui.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUi(
    val id: Int,
    val adult: Boolean,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val favorite: Boolean
): Parcelable