package com.angelvictor.movies

import com.angelvictor.movies.data.PermissionsChecker
import com.angelvictor.movies.data.database.MovieDao
import com.angelvictor.movies.data.database.MovieT
import com.angelvictor.movies.data.datasource.LocationDataSource
import com.angelvictor.movies.data.remote.RemoteMovie
import com.angelvictor.movies.data.remote.RemoteResult
import com.angelvictor.movies.data.remote.RemoteService

class FakeMovieDao(private val movies: List<MovieT> = emptyList()) : MovieDao {

    override suspend fun getAll(): List<MovieT> = movies

    override suspend fun movieCount(): Int = movies.size

    override suspend fun checkMovieIsFavorite(id: Int): Boolean? {
        movies.firstOrNull() {
            it.id == id
        }?.let {
            return it.favorite
        }
        return null
    }

    override suspend fun insertMovies(movie: MovieT) {

    }

    override suspend fun deleteMovie(movie: MovieT) {

    }


}

class FakeRemoteService(private val movies: List<RemoteMovie> = emptyList()) : RemoteService {

    override suspend fun getPopularMovies(apiKey: String, region: String, page: Int) = RemoteResult(
        page = 1, results = movies, totalPages = 1, totalResults = movies.size
    )

    override suspend fun getNowPlayingMovies(
        apiKey: String, region: String, page: Int
    ) = RemoteResult(
        page = 1, results = movies, totalPages = 1, totalResults = movies.size
    )

    override suspend fun getTopRatedMovies(
        apiKey: String, region: String, page: Int
    ) = RemoteResult(
        page = 1, results = movies, totalPages = 1, totalResults = movies.size
    )

    override suspend fun getUpcomingMovies(
        apiKey: String, region: String, page: Int
    ) = RemoteResult(
        page = 1, results = movies, totalPages = 1, totalResults = movies.size
    )


}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionsChecker {
    var permissionGranted = true

    override fun checkLocation() = permissionGranted
}