package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.domain.Error
import javax.inject.Inject

class ChangeMovieFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = repository.changeFavorite(movie)
}