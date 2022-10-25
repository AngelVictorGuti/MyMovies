package com.angelvictor.movies.data.remote

import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import com.angelvictor.movies.di.ApiKey
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
) :
    MovieRemoteDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
         return try {
             remoteService
                 .getPopularMovies(apiKey)
                 .results
                 .toDomainModel()
         } catch (e: Exception){
             emptyList()
         }
    }
}