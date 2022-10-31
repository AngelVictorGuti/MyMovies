package com.angelvictor.movies.data.datasource

import arrow.core.Either
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.domain.Error

interface MovieLocalDataSource {
    suspend fun getMovies(): Either<Error, List<Movie>>

    suspend fun isEmpty(): Boolean
    suspend fun checkMovieIsFavorite(id: Int): Boolean
    suspend fun saveMovie(movie: Movie): Error?
    suspend fun deleteMovie(movie: Movie): Error?
}