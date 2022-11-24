package com.angelvictor.movies.data.database

import arrow.core.Either
import com.angelvictor.movies.data.datasource.MovieLocalDataSource
import com.angelvictor.movies.data.safeCallWithError
import com.angelvictor.movies.data.safeCall
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val movieDao: MovieDao) : MovieLocalDataSource {

    override suspend fun getMovies(): Either<Error, List<Movie>> =
        safeCallWithError { movieDao.getAll().map { it.toDomainModel() } }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override suspend fun checkMovieIsFavorite(id: Int): Boolean =
        movieDao.checkMovieIsFavorite(id) ?: false

    override suspend fun saveMovie(movie: Movie): Error? = safeCall {
        movieDao.insertMovie(movie.fromDomainModel())
    }

    override suspend fun deleteMovie(movie: Movie): Error? = safeCall {
        movieDao.deleteMovie(movie.fromDomainModel())
    }
}

