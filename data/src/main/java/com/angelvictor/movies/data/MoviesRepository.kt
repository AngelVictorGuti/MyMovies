package com.angelvictor.movies.data

import arrow.core.Either
import com.angelvictor.movies.data.datasource.MovieLocalDataSource
import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val locationRepository: LocationRepository
) {

    suspend fun requestPopularMovies(): Either<Error, List<Movie>> = remoteDataSource.getPopularMovies(getRegion())


    suspend fun requestNowPlayingMovies(): Either<Error, List<Movie>>  = remoteDataSource.getNowPlayingMovies(getRegion())


    suspend fun requestTopRatedMovies(): Either<Error, List<Movie>>  = remoteDataSource.getTopRatedMovies(getRegion())


    suspend fun requestUpcomingMovies(): Either<Error, List<Movie>>  = remoteDataSource.getUpcomingMovies(getRegion())


    suspend fun getFavoriteMovies(): Either<Error, List<Movie>> = movieLocalDataSource.getMovies()

    suspend fun databaseIsEmpty() = movieLocalDataSource.isEmpty()

    suspend fun checkMovieIsFavorite(id: Int): Boolean =
        movieLocalDataSource.checkMovieIsFavorite(id)

    suspend fun changeFavorite(movie: Movie): Error? {
        return if (movie.favorite) {
            movieLocalDataSource.saveMovie(movie)
        } else {
            movieLocalDataSource.deleteMovie(movie)
        }
    }

    private suspend fun getRegion(): String = locationRepository.findRegion()

}