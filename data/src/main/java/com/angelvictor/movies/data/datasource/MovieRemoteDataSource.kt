package com.angelvictor.movies.data.datasource

import com.angelvictor.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(region: String): List<Movie>
    suspend fun getNowPlayingMovies(region: String): List<Movie>
    suspend fun getTopRatedMovies(region: String): List<Movie>
    suspend fun getUpcomingMovies(region: String): List<Movie>
}