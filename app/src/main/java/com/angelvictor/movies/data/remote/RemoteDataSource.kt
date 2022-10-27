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

    override suspend fun getPopularMovies(region: String): List<Movie> {
         return try {
             remoteService
                 .getPopularMovies(apiKey, region)
                 .results
                 .toDomainModel()
         } catch (e: Exception){
             emptyList()
         }
    }

    override suspend fun getNowPlayingMovies(region: String): List<Movie> {
         return try {
             remoteService
                 .getNowPlayingMovies(apiKey, region)
                 .results
                 .toDomainModel()
         } catch (e: Exception){
             emptyList()
         }
    }

    override suspend fun getTopRatedMovies(region: String): List<Movie> {
         return try {
             remoteService
                 .getTopRatedMovies(apiKey, region)
                 .results
                 .toDomainModel()
         } catch (e: Exception){
             emptyList()
         }
    }

    override suspend fun getUpcomingMovies(region: String): List<Movie> {
         return try {
             remoteService
                 .getUpcomingMovies(apiKey, region)
                 .results
                 .toDomainModel()
         } catch (e: Exception){
             emptyList()
         }
    }
}