package com.angelvictor.movies.ui

import com.angelvictor.movies.FakeLocationDataSource
import com.angelvictor.movies.FakeMovieDao
import com.angelvictor.movies.FakePermissionChecker
import com.angelvictor.movies.FakeRemoteService
import com.angelvictor.movies.data.LocationRepository
import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.data.database.MovieDataSource
import com.angelvictor.movies.data.database.MovieT
import com.angelvictor.movies.data.remote.RemoteDataSource
import com.angelvictor.movies.data.remote.RemoteMovie

fun buildRepositoryWith(
    localData: List<MovieT>,
    remoteData: List<RemoteMovie>
): MoviesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = LocationRepository(locationDataSource, permissionChecker)
    val localDataSource = MovieDataSource(FakeMovieDao(localData))
    val remoteDataSource = RemoteDataSource("1234", FakeRemoteService(remoteData))
    return MoviesRepository(remoteDataSource, localDataSource, regionRepository)
}

fun buildDatabaseMovies(vararg id: Int) = id.map {
    MovieT(
        id = it,
        adult = false,
        title = "Title $it",
        overview = "Overview $it",
        releaseDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        popularity = 5.0,
        voteAverage = 5.1,
        voteCount = 1500,
        favorite = false
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}