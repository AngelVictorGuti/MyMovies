package com.angelvictor.movies.data.database

import com.angelvictor.movies.domain.Movie

fun List<MovieT>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

fun MovieT.toDomainModel(): Movie =
    Movie(
        id,
        adult,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        voteCount,
        favorite
    )

fun Movie.fromDomainModel(): MovieT = MovieT(
    id,
    adult,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    voteCount,
    favorite
)