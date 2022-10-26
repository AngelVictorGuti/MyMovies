package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class ChangeMovieFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie) = repository.changeFavorite(movie)
}