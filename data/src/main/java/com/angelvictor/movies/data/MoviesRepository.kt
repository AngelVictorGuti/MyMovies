package com.angelvictor.movies.data

import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) {
    suspend fun requestPopularMovies(): List<Movie> {
        return remoteDataSource.getPopularMovies()
    }

    suspend fun requestNowPlayingMovies(): List<Movie> {
        return remoteDataSource.getNowPlayingMovies()
    }

    suspend fun requestTopRatedMovies(): List<Movie> {
        return remoteDataSource.getTopRatedMovies()
    }

    suspend fun requestUpcomingMovies(): List<Movie> {
        return remoteDataSource.getUpcomingMovies()
    }
}