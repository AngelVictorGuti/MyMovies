package com.angelvictor.movies.usecases

import arrow.core.Either
import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Either<Error, List<Movie>> = moviesRepository.getFavoriteMovies()

}