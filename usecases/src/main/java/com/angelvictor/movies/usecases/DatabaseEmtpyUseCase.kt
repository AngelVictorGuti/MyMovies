package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import com.angelvictor.movies.domain.Movie
import javax.inject.Inject

class DatabaseEmtpyUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Boolean {
        return moviesRepository.databaseIsEmpty()
    }
}