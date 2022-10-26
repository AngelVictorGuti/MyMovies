package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): List<Movie> = moviesRepository.getFavoriteMovies()

}