package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ChangeMovieFavoriteUseCaseTest {


    @Test
    fun `Change state movie if check or uncheck favorite movie`(): Unit = runBlocking {
        val movie = sampleMovie.copy(id = 1)
        val moviesRepository = mock<MoviesRepository>()
        val changeMovieFavoriteUseCase = ChangeMovieFavoriteUseCase(moviesRepository)

        changeMovieFavoriteUseCase(movie)

        verify(moviesRepository).changeFavorite(movie)

    }

}