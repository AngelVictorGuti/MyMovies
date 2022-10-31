package com.angelvictor.movies.data.remote

import arrow.core.Either
import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import com.angelvictor.movies.data.safeCallWithError
import com.angelvictor.movies.di.ApiKey
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
) :
    MovieRemoteDataSource {

    override suspend fun getPopularMovies(region: String): Either<Error, List<Movie>> =
        safeCallWithError {
            remoteService
                .getPopularMovies(apiKey, region)
                .results
                .toDomainModel()
        }

    override suspend fun getNowPlayingMovies(region: String): Either<Error, List<Movie>> =
        safeCallWithError {
            remoteService
                .getNowPlayingMovies(apiKey, region)
                .results
                .toDomainModel()
        }


    override suspend fun getTopRatedMovies(region: String): Either<Error, List<Movie>> =
        safeCallWithError {
            remoteService
                .getTopRatedMovies(apiKey, region)
                .results
                .toDomainModel()
        }

    override suspend fun getUpcomingMovies(region: String): Either<Error, List<Movie>> =
        safeCallWithError {
            remoteService
                .getUpcomingMovies(apiKey, region)
                .results
                .toDomainModel()
        }
}