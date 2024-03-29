package com.angelvictor.movies.domain

data class Movie(
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
)