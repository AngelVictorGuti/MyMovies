package com.angelvictor.movies.data

import com.angelvictor.movies.data.datasource.MovieLocalDataSource
import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val locationRepository: LocationRepository
) {

    suspend fun requestPopularMovies(): List<Movie> = remoteDataSource.getPopularMovies(getRegion())


    suspend fun requestNowPlayingMovies(): List<Movie> = remoteDataSource.getNowPlayingMovies(getRegion())


    suspend fun requestTopRatedMovies(): List<Movie> = remoteDataSource.getTopRatedMovies(getRegion())


    suspend fun requestUpcomingMovies(): List<Movie> = remoteDataSource.getUpcomingMovies(getRegion())


    suspend fun getFavoriteMovies() = movieLocalDataSource.getMovies()

    suspend fun databaseIsEmpty() = movieLocalDataSource.isEmpty()

    suspend fun checkMovieIsFavorite(id: Int): Boolean =
        movieLocalDataSource.checkMovieIsFavorite(id)

    suspend fun changeFavorite(movie: Movie) {
        if (movie.favorite) {
            movieLocalDataSource.saveMovie(movie)
        } else {
            movieLocalDataSource.deleteMovie(movie)
        }
    }

    private suspend fun getRegion(): String = locationRepository.findRegion()

}