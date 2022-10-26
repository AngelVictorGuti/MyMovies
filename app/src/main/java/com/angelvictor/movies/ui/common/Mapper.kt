package com.angelvictor.movies.ui.common

import com.angelvictor.movies.domain.Movie

fun List<Movie>.toUiModel(): List<MovieUi> = map { it.toUiModel() }

private fun Movie.toUiModel(): MovieUi =
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
        false
    )