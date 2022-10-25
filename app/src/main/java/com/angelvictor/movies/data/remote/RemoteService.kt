package com.angelvictor.movies.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): RemoteResult

}