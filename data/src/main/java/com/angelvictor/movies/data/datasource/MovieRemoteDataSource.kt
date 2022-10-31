package com.angelvictor.movies.data.datasource

import arrow.core.Either
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(region: String): Either<Error, List<Movie>>
    suspend fun getNowPlayingMovies(region: String): Either<Error, List<Movie>>
    suspend fun getTopRatedMovies(region: String): Either<Error, List<Movie>>
    suspend fun getUpcomingMovies(region: String): Either<Error, List<Movie>>
}