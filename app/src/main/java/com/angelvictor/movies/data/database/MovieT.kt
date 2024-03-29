package com.angelvictor.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieT(
    @PrimaryKey(autoGenerate = true) val id: Int,
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