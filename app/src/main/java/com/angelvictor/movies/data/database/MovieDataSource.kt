package com.angelvictor.movies.data.database

import com.angelvictor.movies.data.datasource.MovieLocalDataSource
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val movieDao: MovieDao) : MovieLocalDataSource {

    override suspend fun getMovies(): List<Movie> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override suspend fun checkMovieIsFavorite(id: Int): Boolean = movieDao.checkMovieIsFavorite(id) ?: false

    override suspend fun saveMovie(movie: Movie) = movieDao.insertMovies(movie.fromDomainModel())

    override suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie.fromDomainModel())
}

private fun MovieT.toDomainModel(): Movie =
    Movie(
        id,
        adult,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        voteCount,
        favorite
    )

fun Movie.fromDomainModel(): MovieT = MovieT(
    id,
    adult,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    voteCount,
    favorite
)