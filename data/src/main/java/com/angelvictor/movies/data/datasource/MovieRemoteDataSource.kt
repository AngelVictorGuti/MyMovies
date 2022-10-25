package com.angelvictor.movies.data.datasource

import com.angelvictor.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
}