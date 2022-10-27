package com.angelvictor.movies.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int = 1
    ): RemoteResult

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int = 1
    ): RemoteResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int = 1
    ): RemoteResult

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int = 1
    ): RemoteResult

}