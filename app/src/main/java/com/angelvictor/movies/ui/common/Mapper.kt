package com.angelvictor.movies.ui.common

import com.angelvictor.movies.domain.Movie

fun Movie.toUiModel(favorite: Boolean): MovieUi =
    MovieUi(
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


fun MovieUi.fromUiModel(): Movie =
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