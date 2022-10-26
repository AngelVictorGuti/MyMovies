package com.angelvictor.movies.data.datasource

import com.angelvictor.movies.domain.Movie

interface MovieLocalDataSource {
    suspend fun getMovies(): List<Movie>

    suspend fun isEmpty(): Boolean
    suspend fun checkMovieIsFavorite(id: Int): Boolean
    suspend fun saveMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
}