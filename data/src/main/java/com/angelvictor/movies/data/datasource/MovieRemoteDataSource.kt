package com.angelvictor.movies.data.datasource

import com.angelvictor.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(): List<Movie>
}